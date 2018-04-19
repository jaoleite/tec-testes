package br.com.tecconcursos.arquivo;

import java.util.List;

public class SqlUpdateAtualizada {

	public SqlUpdateAtualizada() {
		List<String> list = Arquivo.of("questoes-atualizadas.txt").lsitarDoAqruivo();
		StringBuilder builder = new StringBuilder();
		String update = "update questaoobjetiva set desatualizada = false where id in(";
		list.stream().forEach(p -> {
			builder.append(p).append(",\n");
		});
		
		update += builder.toString() + ")";
		
		System.out.println(update);
	}
	
	public static void main(String[] args) {
		new SqlUpdateAtualizada();
	}

}
