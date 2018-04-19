package br.com.tecconcursos.util.stream;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Teste {
	
	public static void streamMap() {
		Collection<Pessoa> pessoas = Pessoa.listarPessoas();
		List<String> strings = pessoas.parallelStream().filter(pessoa -> pessoa.getSexo().equals("F"))
				.map(pessoa -> pessoa.getNome().toUpperCase())
				.collect(Collectors.toList());
		System.out.println(strings);
	}
	
	public static void streamFilter() {
		Collection<Pessoa> pessoas = Pessoa.listarPessoas();
		Stream<Pessoa> stream = pessoas.stream();
		Long count = stream.filter(pessoa -> pessoa.getSexo().equals("F")).count();
		System.out.println(count);
	}
	
	public static void forEach() {
		Collection<Pessoa> pessoas = Pessoa.listarPessoas();
		
		//imprimir sem garantia de "ordem de encontro"
		pessoas.stream().forEach(p -> System.out.println(p.getNome()));
	}
	
	public static void forEachOrdered() {
		Collection<Pessoa> pessoas = Pessoa.listarPessoas();
		
		//imprime na ordem se for possível
		pessoas.stream().forEachOrdered(p -> System.out.println(p.getNome()));
	}
	
	public static void minMax() {
		Collection<Pessoa> pessoas = Pessoa.listarPessoas();
		//pessoa mais nova com min()
		Optional<Pessoa> nova = pessoas.stream()
		        .min((p1, p2) -> p1.getIdade().compareTo(p2.getIdade()));
		
		//mais velha com max()
		Optional<Pessoa> velha = pessoas.stream()
		        .max((p1, p2) -> p1.getIdade().compareTo(p2.getIdade()));
		
		//imprimir as idades no console
		System.out.println(nova.get().getIdade());
		System.out.println(velha.get().getIdade());
	}
	
	public static void media() {
		Collection<Pessoa> pessoas = Pessoa.listarPessoas();
		Double media = pessoas.stream().collect(Collectors.averagingDouble(p -> p.getIdade()));
		System.out.println("A média é: " + media);
	}

	public static void partitioningBy() {
		Collection<Pessoa> pessoas = Pessoa.listarPessoas();
        
		//um Map Boolean -> List<Pessoa>
		Map<Boolean, List<Pessoa>> result = pessoas.stream()
		        .collect(Collectors.partitioningBy(p -> p.getSexo().equals("M")));
		
		//homens armazenados na chave 'true'
		List<Pessoa> homens = result.get(Boolean.TRUE);
		System.out.println(homens);
		
		//mulheres na chave 'false'
		List<Pessoa> mulheres = result.get(Boolean.FALSE);
		System.out.println(mulheres);
	}
	
	public static void groupingBy() {
		Collection<Pessoa> pessoas = Pessoa.listarPessoas();
		//Map "Idade" -> "List<Pessoa>"
		Map<Integer, List<Pessoa>> result = pessoas.stream()
				.collect(Collectors.groupingBy(p -> p.getIdade()));
		
		System.out.println(result);
	}
	
	public static void main(String[] args) {
		//streamFilter();
		//streamMap();
		//forEach();
		//forEachOrdered();
		//minMax();
		//media();
		//partitioningBy();
		groupingBy();
	}
	
}
