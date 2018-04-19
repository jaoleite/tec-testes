package br.com.tecconcursos.mercadopago;

public class Pagina {
	public int offset = 0;
	public int limit = 20;
	
	public void changeOffset() {
		offset += limit;
	}
}
