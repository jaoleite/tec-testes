package br.com.tecconcursos.util;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class Java8 {

	private List<String> palavras = Arrays.asList("rodrigo", "paulo", "caelum");
	
	public void stream() {
		IntStream intStream = palavras.stream().mapToInt(String::length);
		System.out.println(intStream.average().getAsDouble());
		System.out.println((7 + 5 + 6) / 3.0);
	}
	
	public void streamFilter() {
		palavras.stream()
		  .filter(s -> s.length() < 6)
		  .forEach(System.out::println);
	}
	
	public void collections() {
		Comparator<String> comparador = (s1, s2) -> {
			return Integer.compare(s1.length(), s2.length()); 
		};
		palavras.sort(comparador);
		System.out.println(palavras);
		
		palavras.sort(Comparator.comparingInt(String::length));
		System.out.println(palavras);
	}
	
	public void testeFuncaoAnonima() {
		
	}
	
	public static void main(String[] args) {
		new Java8().stream();
	}
	
}
