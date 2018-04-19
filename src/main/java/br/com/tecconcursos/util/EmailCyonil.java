package br.com.tecconcursos.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class EmailCyonil {

	private static final String ARQUIVO = "D:\\desenvolvimento\\arquivos\\email\\cyonil.txt";
	
	private List<String> emails;
	
	public EmailCyonil() {
		leitura();
		select();
	}
	
	@SuppressWarnings("unchecked")
	public void leitura() {
		try {
			List<String> list = IOUtils.readLines(new FileInputStream(new File(ARQUIVO)));
			emails = list;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void select() {
		StringBuilder builder = new StringBuilder();
		builder.append("select * from usuariosite where email in (");
		for (String email : emails) {
			builder.append("'").append(email.trim().toLowerCase()).append("',\n");
		}
		builder.append(");");
		System.out.println(builder);
	}
	
	public static void main(String[] args) {
		new EmailCyonil();
	}
}
