package br.com.tecconcursos.util.stream;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;

public class Pessoa {

	private String nome;
	
	private Integer idade;
	
	private String sexo;
	
	public Pessoa() {}
	
	public Pessoa(String nome, Integer idade, String sexo) {
		this.nome = nome;
		this.idade = idade;
		this.sexo = sexo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	
	public static Collection<Pessoa> listarPessoas() {
		Collection<Pessoa> collection = new ArrayList<Pessoa>();
		Pessoa pessoa1 = new Pessoa("João Paulo", 37, "M");
		Pessoa pessoa2 = new Pessoa("Juliana", 33, "F");
		Pessoa pessoa3 = new Pessoa("Francine", 4, "F");
		
		collection.add(pessoa1);
		collection.add(pessoa3);
		collection.add(pessoa2);
		
		return collection;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Pessoa [nome=");
		builder.append(nome);
		builder.append(", idade=");
		builder.append(idade);
		builder.append(", sexo=");
		builder.append(sexo);
		builder.append("]");
		return builder.toString();
	}
	
	public static void main(String[] args) {
		//Random random = new Random();
		SecureRandom random = new SecureRandom();
		String string = new BigInteger(30, random).toString(32);
		System.out.println(string);
	}
}
