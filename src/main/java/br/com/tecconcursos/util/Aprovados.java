package br.com.tecconcursos.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class Aprovados {

	private static final String ARQUIVO = "D:\\desenvolvimento\\arquivos\\aprovados.txt";
	
	public void montarSql() {
		List<Aprovado> aprovados = listarDoArquivo();
		for (Aprovado aprovado : aprovados) {
			System.out.println(aprovado.montarSql());
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Aprovado> listarDoArquivo() {
		try {
			List<String> list = IOUtils.readLines(new FileInputStream(new File(ARQUIVO)));
			List<Aprovado> aprovados = new ArrayList<Aprovados.Aprovado>();
			for (String linha : list) {
				aprovados.add(new Aprovado(linha));
			}
			return aprovados;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public String getNome(String linha) {
		String[] strings = linha.split(",");
		return strings[0];
	}
	
	public List<Integer> getListId(String linha) {
		String[] strings = linha.split(",");
		List<Integer> integers = new ArrayList<Integer>();
		for (int i = 1; i < strings.length; i++) {
			integers.add(new Integer(strings[i].trim()));
		}
		return integers;
	}
	
	public static void main(String[] args) {
		new Aprovados().montarSql();
	}

	class Aprovado {
		public String nome;
		public List<Integer> ids;
		public Aprovado(String linha) {
			this.nome = getNome(linha);
			this.ids = getListId(linha);
		}
		
		public String montarSql() {
			String string = "select " + nome + " as \"Nome Original\", replace(to_char(cast (cpf as Bigint), '000:000:000-00'), ':', '.') as \"CPF\", email as \"E-mail\", nomecompleto || ' ' || sobrenome as \"Nome completo\" from usuariosite where id" + getSelectIn();
			return string;
		}
		
		private String getSelectIn() {
			StringBuilder builder = new StringBuilder();
			builder.append(" in(");
			for (Integer id : ids) {
				builder.append(id).append(", ");
			}
			return builder.substring(0, builder.length() - 2).concat(");");
		}
	}
	
}
