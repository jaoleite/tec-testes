package br.com.tecconcursos.rps.barueri;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Lista<E> extends ArrayList<E> implements List<E> {

	private static final long serialVersionUID = 1L;

	public Lista() {
		super();
		
	}

	public Lista(Collection<? extends E> c) {
		super(c);
		
	}

	public Lista(int initialCapacity) {
		super(initialCapacity);
	}

	public void teste() {
		this.stream().forEach(p -> {
			System.out.println(p);
		});
	}

}
