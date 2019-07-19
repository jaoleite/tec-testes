package br.com.tecconcursos.url;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class MultiplaEscolhaOuCertoErrado {

	private List<String> linhas;
	private String template;
	
	
	public MultiplaEscolhaOuCertoErrado() {
	}
	
	@SuppressWarnings("unchecked")
	public MultiplaEscolhaOuCertoErrado leitura() {
		try {
			linhas = IOUtils.readLines(new FileInputStream(new File("D:\\ids.txt")));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return this;
	}
	
	public MultiplaEscolhaOuCertoErrado template() {
		StringBuilder builder = new StringBuilder();
		builder.append("select");
		builder.append("\n\tq.id as \"C�digo da questao\",");
		builder.append("\n\tcase");
		builder.append("\n\twhen q.tipoquestao = 0 then 'Certo/Errado'");
		builder.append("\n\t	when q.tipoquestao = 1 then 'M�ltipla Escolha'");
		builder.append("\n\t	when q.tipoquestao = 2 then 'Quest�o Discursiva'");
		builder.append("\n\t	when q.tipoquestao = 3 then 'Reda��o/Disserta��o'");
		builder.append("\n\t	when q.tipoquestao = 4 then 'Pe�a T�cnica'");
		builder.append("\n\t	else 'Sem tipo'");
		builder.append("\n\tend as \"Tipo da quest�o\"");
		builder.append("\nfrom questaoobjetiva q where id in \n %s;");
		
		template = builder.toString();
		return this;
	}
	
	public void sql() {
		String ids = linhas.toString().replace("[", "(").replace("]", ")").replace(",", ",\n");
		System.out.println(String.format(template, ids));
	}
	
	public static void main(String[] args) {
		new MultiplaEscolhaOuCertoErrado().template().leitura().sql();
	}

}
