package br.com.tecconcursos.arquivo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SqlUpdateEmailUsuario {

	private static final String ARQUIVO = "usuarios-emails-update.txt";
	
	private List<String> lista = null;
	private Arquivo arquivo = null;
	
	public SqlUpdateEmailUsuario() {
		arquivo = Arquivo.of(ARQUIVO);
	}
	
	public SqlUpdateEmailUsuario listar() {
		lista = arquivo.lsitarDoAqruivo();
		return this;
	}
	
	public SqlUpdateEmailUsuario semRepeticao() {
		Set<String> set = new HashSet<String>(lista);
		lista = new ArrayList<String>(set);
		return this;
	}
	
	public void imprimirSqlUpdate() {
		String template = "update usuariosite set email = '%s' || '.old' where id = (select id from usuariosite where email = '%s');";
		lista.stream().forEach(p -> {
			String email = p;
			String st = String.format(template, email, email);
			System.out.println(st);
		});
	}
	
	public void imprimirSqlSelect() {
		String template = "select id, email from usuariosite where email in(%s)";
		StringBuilder builder = new StringBuilder();
		lista.stream().forEach(p -> {
			builder.append("'").append(p).append("',\n").append("");
		});
		
		System.out.println(String.format(template, builder.toString()));
	}
	
	public static void main(String[] args) {
		new SqlUpdateEmailUsuario().listar().semRepeticao().imprimirSqlSelect();
	}
	
}
