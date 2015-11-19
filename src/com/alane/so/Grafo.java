package com.alane.so;

//Este é um exemplo simples de implementação de grafo representado por lista
//de adjacências

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Grafo {

	List<Vertice> vertices;
	List<Aresta> arestas;
	private ArrayList<String> envolvidosCiclo;

	public Grafo() {
		vertices = new ArrayList<Vertice>();
		arestas = new ArrayList<Aresta>();
	}

	Vertice addVertice(String nome) {
		Vertice v = new Vertice(nome);
		vertices.add(v);
		return v;
	}

	public int size() { // quantidade de vértices no grafo
		return vertices.size();
	}

	Aresta addAresta(Vertice origem, Vertice destino) {
		Aresta e = new Aresta(origem, destino);
		origem.addAdj(e);
		arestas.add(e);
		return e;
	}

	public String toString() {
		String r = "";
		for (Vertice u : vertices) {
			r += u.getNome() + " -> ";
			for (Aresta e : u.getListAdj()) {
				Vertice v = e.getDestino();
				r += v.getNome() + ", ";
			}
			r += "\n";
		}
		return r;
	}

	public void criaGrafo() {
		// Processos
		Vertice P1 = addVertice("P1");
		Vertice P2 = addVertice("P2");
		Vertice P3 = addVertice("P3");
		// Vertice PC = addVertice("P3");

		// Recursos
		Vertice R1 = addVertice("R1");
		Vertice R2 = addVertice("R2");
		Vertice R3 = addVertice("R3");
		Vertice R4 = addVertice("R4");
		Vertice R5 = addVertice("R5");
		// Vertice RT = addVertice("R3");

		// Arestas
		Aresta A = addAresta(P1, R2);
		Aresta B = addAresta(R2, P2);
		Aresta C = addAresta(P2, R3);
		Aresta D = addAresta(R3, P3);
		Aresta E = addAresta(P3, R1);
		Aresta F = addAresta(R1, P1);
		Aresta G = addAresta(P1, R4);
	    Aresta H = addAresta(P1, R5);
		// Aresta A = addAresta(R2, P2);
		// Aresta B = addAresta(R1, P1);
		// Aresta C = addAresta(P1, R2);
		// Aresta D = addAresta(P2, R1);
		// Aresta E = addAresta(R3, P3);

		// Aresta PARS = addAresta(PA, RS);
		// Aresta PBRT = addAresta(PB, RT);
		// Aresta PCRR = addAresta(PC, RR);

	}
	
	public boolean hasCycle() {
		if (this.size() == 0)
			return false;
		List<Vertice> vertexCollect = new ArrayList<Vertice>();
		List<Vertice> visual = new ArrayList<Vertice>();
		Queue<Vertice> q; // Queue will store vertices that have in-degree of zero
		
		for(Aresta edge : arestas){ 
			if(!vertexCollect.contains(edge.getOrigem())){
				vertexCollect.add(edge.getOrigem()); 
			} 
			if(!vertexCollect.contains(edge.getDestino())){
					vertexCollect.add(edge.getDestino()); 
			}  
		}
		
		for(Aresta edge : arestas){ 
			
			visual.add(edge.getOrigem()); 
			visual.add(edge.getDestino()); 
		}
		
		System.out.println("lista : " + vertexCollect);
		System.out.println("visual : " + vertexCollect);
		
		System.out.println(this);
		
		/* Calculate the in-degree of all vertices and store on the Vertex */
		for (Vertice v : vertexCollect) {
			v.setInDegree(0);
//			System.out.println(" - " + v.getNome());
		}
		
		Aresta inicia = null;
		int i = 0;
		int j = 0;
		envolvidosCiclo = new ArrayList<String>();
		while(i != vertexCollect.size()) {
			j = 0;
			while(j != vertexCollect.get(i).getListAdj().size()) {
				inicia = vertexCollect.get(i).getListAdj().get(j);
				for (Vertice v : vertexCollect) {
						for (Aresta linha : v.getListAdj()) {
							
							if(inicia.getOrigem().getNome().equals(linha.getDestino().getNome())								)
							 {
								
								if(!envolvidosCiclo.contains(inicia.getOrigem().getNome())){
									envolvidosCiclo.add(inicia.getOrigem().getNome());
								}
								if(!envolvidosCiclo.contains(linha.getDestino().getNome())){
									envolvidosCiclo.add(linha.getDestino().getNome());
								}
								if(envolvidosCiclo.size() >= 4) {
									break;
								}
								
							}
							linha.getDestino().setInDegree(linha.getDestino()
									.getInDegree() + 1);
						}
				}
				j++;
			}
			i++;
		}
		
		System.out.println("no grafo: " + envolvidosCiclo.size());
		
		/* Find all vertices with in-degree == 0 and put in queue */
		/*q = new LinkedList<Vertice>();
		for (Vertice v : vertexCollect) {
			if (v.getInDegree() == 0)
				q.offer(v);
		}*/

		//System.out.println("TAMANHO DE Q: " + q.size());
		if (this.envolvidosCiclo.size() >= 4) {
			return true;

		}
		return false;
	}
	
	public ArrayList<String> getEnvolvidosCiclo() {
		return envolvidosCiclo;
	}
	
	

}

	/*public boolean hasDeadlock() {
		if (this.size() == 0)
			return false;
		List<Vertice> vertexCollect = this.vertices;
		Queue<Vertice> q; // Queue will store vertices that have in-degree of zero

		// Calculate the in-degree of all vertices and store on the Vertex 
		for (Vertice v : vertexCollect) {
			v.setInDegree(0);
//			System.out.println(" - " + v.getNome());
		}
		
		Aresta inicia = null;
		int i = 0;
		int j = 0;
		ArrayList<String> envolvidosDeadlock = new ArrayList<String>();
		while(i != vertexCollect.size()) {
			j = 0;
			while(j != vertexCollect.get(i).getListAdj().size()) {
//				System.out.println("tamanho: " + vertexCollect.get(i).getListAdj().size());
//				System.out.println("J: " + j );
				inicia = vertexCollect.get(i).getListAdj().get(j);
				for (Vertice v : vertexCollect) {
						for (Aresta linha : v.getListAdj()) {
//							System.out.println("origem" + inicia.getOrigem().getNome());
//							System.out.println("destino" + linha.getDestino().getNome());
							
							if(inicia.getOrigem().getNome().equals(linha.getDestino().getNome())) {
								System.out.println("Deadlock");
								envolvidosDeadlock.add(inicia.getOrigem().getNome());
								envolvidosDeadlock.add(linha.getDestino().getNome());
								break;
							}
							linha.getDestino().setInDegree(linha.getDestino()
									.getInDegree() + 1);
						}
				}
				j++;
			}
			i++;
		}
		for(int i1 = 0; i1 < envolvidosDeadlock.size(); i1++) {
			System.out.println(envolvidosDeadlock.get(i1));
		}
		// Find all vertices with in-degree == 0 and put in queue 
		q = new LinkedList<Vertice>();
		for (Vertice v : vertexCollect) {
			if (v.getInDegree() == 0)
				q.offer(v);
		}

		if (q.size() == 0) {
			return true;

		}
		return false;
	}
}*/

/*
 * public List<String> hasDeadlock() { List<Vertice> vertexCollect = new
 * ArrayList<Vertice>(); Stack<Way> stack = new Stack<Way>();
 * LinkedList<Vertice> deadlock = new LinkedList<Vertice>(); List<String>
 * strList = null;
 * 
 * for(Aresta edge : arestas){ if(!vertexCollect.contains(edge.origem)){
 * vertexCollect.add(edge.origem); } if(!vertexCollect.contains(edge.destino)){
 * vertexCollect.add(edge.destino); } }
 * 
 * if(vertexCollect.size() < 4){ return null; }
 * 
 * while(vertexCollect.size() > 0 && deadlock.size() == 0){ List<Vertice>
 * wayVertices = new ArrayList<Vertice>(); Vertice vertice =
 * vertexCollect.get(0);
 * 
 * Way newWay = new Way(null, vertice); stack.push(newWay);
 * wayVertices.add(vertice); vertexCollect.remove(vertice);
 * 
 * while(!stack.isEmpty()){ Way way = stack.pop();
 * 
 * for(Aresta edge : way.vertice.adj){ System.out.println("edge destino: " +
 * edge.destino.nome); if(wayVertices.contains(edge.destino)){ do{
 * System.out.println("deadlock way: " + way.vertice.nome);
 * deadlock.add(way.vertice); way = way.superWay; }while(way != null);
 * 
 * stack.clear(); break; } else{ System.out.println("stacked"); newWay = new
 * Way(way, edge.destino); stack.push(newWay); wayVertices.add(edge.destino);
 * vertexCollect.remove(edge.destino); } } } }
 * 
 * if(deadlock.size() > 0 ){ strList = new ArrayList<String>();
 * 
 * Iterator<Vertice> iterator = deadlock.descendingIterator();
 * 
 * while(iterator.hasNext()){ strList.add(iterator.next().nome); } } else{
 * strList = null; }
 * 
 * return strList; }
 * 
 * private class Way{ private Way superWay; private Vertice vertice;
 * 
 * public Way(Way superWay, Vertice vertice){ this.superWay = superWay;
 * this.vertice = vertice; } }
 */

