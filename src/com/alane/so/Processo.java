package com.alane.so;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

public class Processo extends Thread {
	private int id;
	private int tempoSolicitacao;
	private int tempoUtilizacao;
	private SistemaOperacional sistemaOperacional;
	private static Semaphore mutex = new Semaphore(1);
	public static int processosInstanciados = 0;
	private volatile boolean processoRodando = true;
	private volatile boolean stopSignal = false;
	//private public ArrayList<Recurso> recursoUsoNoProcesso;
	
	private Map<Recurso, Long> recursoUtilizacaoMap;
	private long tempoProximaSolicitacao;
	
	// public static final int MAX_PROCESSOS = 10;
	// private static Semaphore totalDeProcessos = new Semaphore(MAX_PROCESSOS);
	// private ArrayList<Recurso> recursosEmUsoNoProcesso;

	public Processo(SistemaOperacional so, int tempoSolicitacao,
			int tempoUtilizacao) {
		this.id = id;
		this.mutex = new Semaphore(1);
		this.sistemaOperacional = so;
		this.tempoSolicitacao = tempoSolicitacao;
		this.tempoUtilizacao = tempoUtilizacao;
		// this.recursosEmUsoNoProcesso = new ArrayList<Recurso>();
		this.id = processosInstanciados;
		processosInstanciados++;
		
		recursoUtilizacaoMap = new ConcurrentHashMap<Recurso, Long>();
	}

	public void gerenciarSolicitacaoRecurso(final Processo processo) {
		Recurso recurso = null;
		boolean flag = true;
		while (flag) {
			recurso = solicitandoRecurso();
			if(!sistemaOperacional.verificarAlocacao(processo, recurso)){
				flag = false; // atribui false para flag e sai do while
			}		
		}

		alocarRecurso(processo, recurso);
		
		recursoUtilizacaoMap.put(recurso, System.currentTimeMillis() + tempoUtilizacao*1000);
	}
	@Override
	public void run() {
		super.run();
		
		try{
			while (!stopSignal) {
				sleep(1000);
				
				synchronized(this) {
					for(Recurso recurso : recursoUtilizacaoMap.keySet()){
						if(System.currentTimeMillis() > recursoUtilizacaoMap.get(recurso)){
							recursoUtilizacaoMap.remove(recurso);
							liberarRecurso(this, recurso);
						}
					}
				}
				if(System.currentTimeMillis() > tempoProximaSolicitacao){
					tempoProximaSolicitacao = System.currentTimeMillis() + this.tempoSolicitacao*1000;
					gerenciarSolicitacaoRecurso(this);
				}
			}
		}
		catch(InterruptedException ie){}
		
		for(Recurso recurso : recursoUtilizacaoMap.keySet()){
			liberarRecurso(this, recurso);
		}
		
		processoRodando = false;
	}
	
	public void stopSignal(){
		stopSignal = false;
	}
	
	public boolean isProcessoParando(){
		return stopSignal;
	}
	
	public boolean isProcessoRodando(){
		return processoRodando;
	}

	public void alocarRecurso(Processo processo, Recurso recurso) {
		this.sistemaOperacional.alocarRecurso(processo, recurso);
		

	}

	public void liberarRecurso(Processo processo, Recurso recurso) {
		this.sistemaOperacional.liberarRecurso(processo, recurso);
		
	}

	public Recurso solicitandoRecurso() {
		int idRecurso = new Random().nextInt(this.sistemaOperacional
				.getRecursos().size());
		return this.sistemaOperacional.retornandoRecurso(idRecurso);
	}

	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTempoSolicitacao() {
		return tempoSolicitacao;
	}

	public void setTempoSolicitacao(int tempoSolicitacao) {
		this.tempoSolicitacao = tempoSolicitacao;
	}

	public int getTempoUtilizacao() {
		return tempoUtilizacao;
	}

	public void setTempoUtilizacao(int tempoUtilizacao) {
		this.tempoUtilizacao = tempoUtilizacao;
	}

	public SistemaOperacional getSistemaOperacional() {
		return sistemaOperacional;
	}

	public void setSistemaOperacional(SistemaOperacional sistemaOperacional) {
		this.sistemaOperacional = sistemaOperacional;
	}
}