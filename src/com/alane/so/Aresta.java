package com.alane.so;


public class Aresta {
    private Vertice origem;
    private Vertice destino;

    Aresta(Vertice origem, Vertice destino) {
        this.setOrigem(origem);
        this.setDestino(destino);
    }

	public Vertice getDestino() {
		return destino;
	}

	public void setDestino(Vertice destino) {
		this.destino = destino;
	}

	public Vertice getOrigem() {
		return origem;
	}

	public void setOrigem(Vertice origem) {
		this.origem = origem;
	}
}
