package br.com.tecconcursos.util;

import java.util.Arrays;
import java.util.List;

public class Sql {

	public void buildAll() {
		List<String> names = getListNames();
		names.stream().forEach(name -> System.out.println(getSql(name)));
	}
	
	public String getSql(String name) {
		StringBuilder builder = new StringBuilder();
		builder.append("select id, nomeCompleto, sobrenome, cpf, email from usuariosite ");
		builder.append(getWhereComOr(name));
		return builder.toString();
	}
	
	public String getWhere(String name) {
		StringBuilder builder = new StringBuilder();
		builder.append("where nomeCompleto ilike '");
		builder.append(transformName(name));
		builder.append("' and sobrenome ilike '");
		builder.append(transformName(name)).append("';");
		return  builder.toString();
	}
	
	public String transformName(String name) {
		StringBuilder builder = new StringBuilder();
		builder.append("%").append(name.replaceAll(" ", "%"));
		builder.append("%");
		return builder.toString();
	}
	
	public String getWhereComOr(String name) {
		StringBuilder builder = new StringBuilder();
		builder.append("where (").append(getOr(name, "nomeCompleto")).append(")");
		builder.append(" and (").append(getOr(name, "sobrenome")).append(");");
		return builder.toString();
	}
	
	public String getOr(String name, String field) {
		StringBuilder builder = new StringBuilder();
		String[] strings =  name.split(" ");
		for (String string : strings) {
			builder.append(field).append(" ilike ").append("'%").append(string).append("%' or ");
		}
		return builder.toString().substring(0, builder.length() - 4);
	}
	
	public List<String> getListNames() {
		String[] array = {
		"Lucas Ribeiro Pereira",
		"Antonio Alves Ferreira Junior",
		"Lucas Costa Silva",
		"Matheus Santos Goncalves",
		"Priscila Leal Silva",
		"Thiago dos Santos",
		"Alessandra dos Santos Teixeira",
		"Tiago Franca dos Santos",
		"Thiago Barbosa",
		"Matheus Alves Viana",
		"Patricia Nascimento Silva",
		"Gustavo Figueiredo Pereira",
		"Daniele de Oliveira Siqueira",
		"Marcela Nunes Tavares",
		"Fernando Leite dos Santos",
		"Joelson Guedes da Silva",
		"Luiz Carlos Amaral Oliveira",
		"Luiz Antonio Campos",
		"Felipe Oliveira Cavalcante",
		"Bruno Henrique Gonçalves",
		"André Costa Chaves"};
		return Arrays.asList(array);
	}
	
	public static void main(String[] args) {
		new Sql().buildAll();
	}
	
}
