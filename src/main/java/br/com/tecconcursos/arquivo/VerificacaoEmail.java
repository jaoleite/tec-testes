package br.com.tecconcursos.arquivo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;

public class VerificacaoEmail {

	private static final String PATH = "D:\\desenvolvimento\\arquivos\\email\\";
	
	private String[] arquivos;
	
	public VerificacaoEmail() {
		arquivos = new String[]{"alunos1.txt", "alunos2.txt", "alunos3.txt", "alunos4.txt"};
	}
	
	public List<String> getArquivos() {
		List<String> files = Arrays.asList(arquivos);
		List<String> list = files.stream().map(p-> PATH + p).collect(Collectors.toList());
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> listarEmailsDoArquivo(String arquivo) {
		try {
			List<String> list = IOUtils.readLines(new FileInputStream(new File(arquivo)));
			List<String> emails = list.stream().distinct().map(p -> "'" + p.toLowerCase().toString().trim() + "'").collect(Collectors.toList());
			return emails;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void listarSql() {
		List<String> arquivos = getArquivos();
		String template = "select email from usuariosite where email in \n(%s) order by email;";
		Set<String> set = new HashSet<String>();
		//list.addAll(c)
		for (String arquivo : arquivos) {
			List<String> emails = listarEmailsDoArquivo(arquivo);
			set.addAll(emails);
		}
		String in = set.toString().replaceAll("\\[","").replaceAll("]", "").replaceAll(",", ",\n");
		String sql = String.format(template, in);
		System.out.println(sql);
	}
	
	public static void main(String[] args) {
		new VerificacaoEmail().listarSql();
	}

}
