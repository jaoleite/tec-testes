package br.com.tecconcursos.util;

public class TesteUm extends AbstractTeste {

	@SuppressWarnings("unchecked")
	public static <T> T map(Object object, Class<T> clazz) {
		return (T) "teste";
	}
	
	public static void main(String[] args) {
		System.out.println(map(new Object(), Object.class));
	}

}
