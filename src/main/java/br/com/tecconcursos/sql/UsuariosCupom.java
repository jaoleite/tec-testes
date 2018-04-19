package br.com.tecconcursos.sql;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;

public class UsuariosCupom {

	private static final String ARQUIVO = "D:\\desenvolvimento\\arquivos\\usuarios-cupom.txt";
	
	public UsuariosCupom() {
		gerarSqlVerificaCupom(leituraArquivo());
	}
	
	public void gerarSqlVerificaCupom(List<String> emails) {
		for (String email : emails) {
			String sql = "'" + email.trim() + "',";
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
		new UsuariosCupom();
	}
}
