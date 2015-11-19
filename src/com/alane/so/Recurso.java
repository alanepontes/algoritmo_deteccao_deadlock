package com.alane.so;

import java.util.concurrent.Semaphore;

public class Recurso {
	int id;
	//public static int recursosInstanciados = 0;
	String nome;
	private Semaphore mutexRecurso;
	static final int MAX_RECURSOS = 10;

	public Recurso(String nome, int id) {
		this.setMutexRecurso(new Semaphore(1));
		this.nome = nome;
		this.id = id;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return this.nome;
	}

	/*public static ArrayList<Recurso> recursosDefault() {
		ArrayList<Recurso> recursos = new ArrayList<Recurso>();

		recursos.add(new Recurso("Teclado"));
		recursos.add(new Recurso("Mouse"));
		recursos.add(new Recurso("Placa de video"));
		recursos.add(new Recurso("DVD"));
		//recursos.add(new Recurso("Pendrive1"));
		//recursos.add(new Recurso("Fax Molden"));
		//recursos.add(new Recurso("Impressora"));
		
		return recursos;
	}*/

	public Semaphore getMutexRecurso() {
		return mutexRecurso;
	}

	public void setMutexRecurso(Semaphore mutexRecurso) {
		this.mutexRecurso = mutexRecurso;
	}
}
