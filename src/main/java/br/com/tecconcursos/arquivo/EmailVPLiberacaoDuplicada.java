package br.com.tecconcursos.arquivo;

import java.util.List;

public class EmailVPLiberacaoDuplicada {

	public EmailVPLiberacaoDuplicada() {
		List<String> list = Arquivo.of("emails-vp-liberacao-dulicadas.txt").lsitarDoAqruivo();
		StringBuilder builder = new StringBuilder();
		list.stream().forEach(p -> {
			builder.append("'").append(p.toLowerCase().trim()).append("',\n");
		});
		
		String template = "select * from liberacao where usuariosite_id in (" + builder.toString() + ")";
		System.out.println(template);
	}
	
	public static void main(String[] args) {
		new EmailVPLiberacaoDuplicada();
	}

}
