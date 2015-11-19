package com.alane.so;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Semaphore;

public class SistemaOperacional extends Thread {
	private ArrayList<Processo> processos;
	private ArrayList<Processo> processosDeadlock;
	private int tempoDeVerificacao;
	private ArrayList<String> envolvidosDeadlock;
	private ArrayList<Updatable> observadoresDeProcessos;
	private ArrayList<Recurso> recursos;
	private Semaphore mutexVerificarDeadlock;
	private ArrayList<Recurso> recursosEmUso;
	private Map<Processo, ArrayList<Recurso>> solicitacoes;
	private Map<Processo, ArrayList<Recurso>> utilizacoes;
	private Grafo grafo;
	TelaDeLog telaDeLog;
	private String[] nomesRecursos;
	private int quantidadeDeProcessos;
	private int quantidadeDeRecursos;

	
	@Override
	public void run() {
		super.run();
		inicializarRecursos(quantidadeDeRecursos);
		inicializarProcessos();
		
		while(true){
			try {
				sleep(tempoDeVerificacao*1000);
			} catch (InterruptedException e) {}
			
			verificarDeadlock();
		}
	}
	

	public SistemaOperacional(int quantidadeDeProcessos, int quantidadeDeRecursos, int tempoDeVerificacao) {
		this.tempoDeVerificacao = tempoDeVerificacao;
		this.observadoresDeProcessos = new ArrayList<Updatable>();
		this.recursosEmUso = new ArrayList<Recurso>();
		this.processos = new ArrayList<Processo>();
		this.processosDeadlock = new ArrayList<Processo>();
		this.setEnvolvidosDeadlock(new ArrayList<String>());
		listasNomesRecursos();
		this.quantidadeDeProcessos = quantidadeDeProcessos;
		this.quantidadeDeRecursos = quantidadeDeRecursos;
		this.recursos = new ArrayList<Recurso>();
		this.setSolicitacoes(Collections.synchronizedMap(new HashMap<Processo, ArrayList<Recurso>>()));
		this.setUtilizacoes(Collections.synchronizedMap(new HashMap<Processo, ArrayList<Recurso>>()));
		
		mutexVerificarDeadlock = new Semaphore(1);

	}
	
	public void addObservadorDeProcesso(Updatable updatable) {
		this.observadoresDeProcessos.add(updatable);
	}
	public void listasNomesRecursos() {
		this.nomesRecursos = new String[] {"recurso_joy_stick", "recurso_webcam", "recurso_microfone", "recurso_hd",
				"recurso_pendrive", "recurso_mouse", "recurso_teclado", "recurso_fax_modem", "recurso_dvd_cd", "recurso_impressora"};
	}
	
	public void criarRecursos(String nome, int id) {
		Recurso recurso = new Recurso(nome, id );
		this.recursos.add(recurso);
	}
	
	public List<Recurso> getSolicitacoesProcesso(Processo processo){
		return solicitacoes.get(processo);
	}

	public boolean alocarRecurso(Processo processo, Recurso recurso) {
		try {
			
			mutexVerificarDeadlock.acquire();
			
			if(this.getSolicitacoes().get(processo)==null) {
				this.getSolicitacoes().put(processo, new ArrayList<Recurso>());
			}
			if(this.getUtilizacoes().get(processo)==null) {
				this.getUtilizacoes().put(processo, new ArrayList<Recurso>());
			}
			
			this.getSolicitacoes().get(processo).add(recurso);
			
			this.grafo = gerarGrafoProcessoRecurso();
			//update();
			
			mutexVerificarDeadlock.release();
			
			if(recurso.getMutexRecurso().availablePermits() == 0) {
				System.out.println("processo " + processo.getId() + "está bloqueado");
				Log.println("processo " + processo.getId() + "está bloqueado : " + recurso.getNome());
			}
			
			recurso.getMutexRecurso().acquire();
			update();
			mutexVerificarDeadlock.acquire();
			Log.println(processo.getId() + " Oba, to usando recurso " + recurso.getNome() + " - "
					+ recurso.getId());
			
			this.getSolicitacoes().get(processo).remove(recurso);
			update();
			this.getUtilizacoes().get(processo).add(recurso);
			
			
			getRecursosEmUso().add(recurso);
			
						return true;
		} catch (InterruptedException e) {
			e.printStackTrace();
			recurso.getMutexRecurso().release();
		}
		finally{
			this.grafo = gerarGrafoProcessoRecurso();
			mutexVerificarDeadlock.release();
		}
		return false;
	}
	
	public void update() {
		for (Updatable updatable : this.observadoresDeProcessos) {
			updatable.update();
		}
	}
	public void liberarRecurso(Processo processo, Recurso recurso) {
		try {
			mutexVerificarDeadlock.acquire();
		
			if(this.getUtilizacoes().get(processo)==null) {
				this.getUtilizacoes().put(processo, new ArrayList<Recurso>());
			}
			getRecursosEmUso().remove(recurso);
			
			this.getUtilizacoes().get(processo).remove(recurso);
			update();
			if(processo.isProcessoParando()) {
				System.out.println(processo.getId() + " Eliminando - Liberar o recurso:  " + recurso.getNome());
				Log.println(processo.getId() + " Eliminando - Liberar o recurso:  " + recurso.getNome());
			}
			
			Log.println(processo.getId() + " Liberar o recurso:  " + recurso.getNome());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			this.grafo = gerarGrafoProcessoRecurso();
			
			recurso.getMutexRecurso().release();
			update();
			mutexVerificarDeadlock.release();
		}
	}
	public void inicializarRecursos(int quantidadeDeRecursos) {
		for(int i = 0; i < quantidadeDeRecursos; i++) {
			criarRecursos(this.nomesRecursos[i], i);
		}
	}
	
	public void inicializarProcessos() {
		for(int i = 0; i < this.processos.size(); i++) {
			processos.get(i).start();
		}
	}
	
	public void criarProcesso(SistemaOperacional sistemaOperacional, int tempoSolicitacao,
			int tempoUtilizacao) {
		Processo processo = new Processo(sistemaOperacional, tempoSolicitacao, tempoUtilizacao); 
		this.processos.add(processo);
		
		
	}
	
	public void eliminarProcesso(int id) {
		Processo processo = this.getProcessos().get(id);
		
		processo.stopSignal();
		
		System.out.println(id + " - Eliminando processo...");
		Log.println(id + " - Eliminando processo...");
		
		processo.interrupt();
		
		solicitacoes.remove(processo);
		
		for(Recurso recurso : utilizacoes.get(processo)){
			recursosEmUso.remove(recurso);
		}
		
		utilizacoes.remove(processo);
		
		envolvidosDeadlock = new ArrayList<String>();
		grafo = gerarGrafoProcessoRecurso();
		
		update();
	}
	
	public Recurso retornandoRecurso(int number) {
		return this.getRecursos().get(number);
	}

	public boolean verificarRecursoDisponivel(Recurso recurso) {
		if (this.getRecursosEmUso().contains(recurso)) {
			return false;
		}
		return true;
	}


	public synchronized Grafo gerarGrafoProcessoRecurso() {
		Grafo grafo = new Grafo();
		Vertice [] vertices = new Vertice[21];
		for (Processo processo : this.processos) {
			vertices[(int)processo.getId()] = grafo.addVertice("P"+processo.getId());
		}
		
		for(Recurso recurso : this.recursos) {
			vertices[(int)recurso.getId()+10] = grafo.addVertice("R"+recurso.getId());
		}
		
		for(Entry<Processo, ArrayList<Recurso>> entry : solicitacoes.entrySet()){
			for(Recurso recurso : entry.getValue()) { 
				grafo.addAresta(vertices[(int)((entry.getKey()).getId())], 
						vertices[((int)recurso.getId()+10)]);
			}
		}
		for(Entry<Processo, ArrayList<Recurso>> entry : utilizacoes.entrySet()) {
			for(Recurso recurso : entry.getValue()) {
				grafo.addAresta(vertices[((int)recurso.getId()+10)],
						vertices[(int)((entry.getKey()).getId())]);
			}
		}
		
		System.out.println(grafo);
		
		return grafo;
	}
	
	public synchronized boolean verificarAlocacao(Processo processo, Recurso recurso){
		List<Recurso> recursos = utilizacoes.get(processo);
		return recursos == null ? false : recursos.contains(recurso);
	}
	
	public synchronized boolean verificarSolicitacao(Processo processo, Recurso recurso){
		List<Recurso> recursos = solicitacoes.get(processo);
		return recursos == null ? false : recursos.contains(recurso);
	}
	
	public void verificarDeadlock() {
		try {
			mutexVerificarDeadlock.acquire();
		
		
			this.grafo = gerarGrafoProcessoRecurso();
			if(this.grafo.hasCycle()){
				this.setEnvolvidosDeadlock(this.grafo.getEnvolvidosCiclo());
			} else {
				this.setEnvolvidosDeadlock(new ArrayList<String>());
			}
			
			update();
			if(this.envolvidosDeadlock.size() > 0){
				System.out.println("DEADLOCK!: " + envolvidosDeadlock);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		finally{
			mutexVerificarDeadlock.release();
			update();
		}
	}
	public ArrayList<Processo> getProcessos() {
		return processos;
	}

	public void setProcessos(ArrayList<Processo> processos) {
		this.processos = processos;
	}

	public ArrayList<Processo> getProcessosDeadlock() {
		return processosDeadlock;
	}

	public void setProcessosDeadlock(ArrayList<Processo> processosDeadlock) {
		this.processosDeadlock = processosDeadlock;
	}

	public int getTempoDeVerificacao() {
		return tempoDeVerificacao;
	}

	public void setTempoDeVerificacao(int tempoDeVerificacao) {
		this.tempoDeVerificacao = tempoDeVerificacao;
	}

	public ArrayList<Recurso> getRecursos() {
		return recursos;
	}

	public void setRecursos(ArrayList<Recurso> recursosDisponiveis) {
		this.recursos = recursosDisponiveis;
	}
	

	public ArrayList<Recurso> getRecursosEmUso() {
		return recursosEmUso;
	}

	public void setRecursosEmUso(ArrayList<Recurso> recursosEmUso) {
		this.recursosEmUso = recursosEmUso;
	}


	public Map<Processo, ArrayList<Recurso>> getUtilizacoes() {
		return utilizacoes;
	}


	public void setUtilizacoes(Map<Processo, ArrayList<Recurso>> utilizacoes) {
		this.utilizacoes = utilizacoes;
	}


	public Map<Processo, ArrayList<Recurso>> getSolicitacoes() {
		return solicitacoes;
	}


	public void setSolicitacoes(Map<Processo, ArrayList<Recurso>> solicitacoes) {
		this.solicitacoes = solicitacoes;
	}


	public ArrayList<String> getEnvolvidosDeadlock() {
		return envolvidosDeadlock;
	}


	public void setEnvolvidosDeadlock(ArrayList<String> envolvidosDeadlock) {
		this.envolvidosDeadlock = envolvidosDeadlock;
	}

	public Grafo getGrafo() {
		return grafo;
	}
	
	public int getQuantidadeDeProcessos() {
		return quantidadeDeProcessos;
	}


	public int getQuantidadeDeRecursos() {
		return quantidadeDeRecursos;
	}


}
