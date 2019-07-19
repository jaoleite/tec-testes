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
		builder.append("\n\tq.id as \"Código da questao\",");
		builder.append("\n\tcase");
		builder.append("\n\twhen q.tipoquestao = 0 then 'Certo/Errado'");
		builder.append("\n\t	when q.tipoquestao = 1 then 'Múltipla Escolha'");
		builder.append("\n\t	when q.tipoquestao = 2 then 'Questão Discursiva'");
		builder.append("\n\t	when q.tipoquestao = 3 then 'Redação/Dissertação'");
		builder.append("\n\t	when q.tipoquestao = 4 then 'Peça Técnica'");
		builder.append("\n\t	else 'Sem tipo'");
		builder.append("\n\tend as \"Tipo da questão\"");
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
