package com.alane.so;
import java.util.ArrayList;
import java.util.List;

public class Vertice {
    private String nome;
    private List<Aresta> adj;
    private int inDegree;
    
    Vertice(String nome) {
        this.setNome(nome);
        this.adj = new ArrayList<Aresta>();
        setInDegree(0);
    }
    
    public String toString(){
    	return nome;
    }

    public void addAdj(Aresta e) {
        adj.add(e);
    }

    public List<Aresta> getListAdj() {
    	return this.adj;
    }
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getInDegree() {
		return inDegree;
	}

	public void setInDegree(int inDegree) {
		this.inDegree = inDegree;
	}
}
