package br.com.tecconcursos.thread;

import com.google.inject.Inject;

public class ThreadUm extends AbstractThread {

	@Inject protected String teste;
	
	public ThreadUm(Integer algo) {
		super();
	}

	@Override
	public void run() {
		
	}
	
	public static void main(String[] args) {
		new ThreadUm(1).start();
	}
	
}
