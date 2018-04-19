package br.com.tecconcursos.sql;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;

public class ListaDominios {

	private static final String ARQUIVO = "D:\\desenvolvimento\\arquivos\\dominios-invalidos.txt";
	
	public ListaDominios() {
		gerarSqlInsert(leituraArquivo());
	}
	
	public void gerarSqlInsert(List<String> dominios) {
		String template = "insert into dominioemail(id, dominio) values(nextval('SeqDominioEmail'), '%s');";
		
		for (String dominio : dominios) {
			String sql = String.format(template, dominio);
			System.out.println(sql);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<String> leituraArquivo() {
		try {
			List<String> list = IOUtils.readLines(new FileInputStream(new File(ARQUIVO)));
			Set<String> strings = list.stream().filter(p -> !p.trim().equals("")).collect(Collectors.toSet());
			return new ArrayList<String>(strings);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) {
		new ListaDominios();
	}

}
