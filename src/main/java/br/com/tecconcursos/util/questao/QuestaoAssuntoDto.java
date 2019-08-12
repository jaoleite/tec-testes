package br.com.tecconcursos.util.questao;

import java.util.ArrayList;
import java.util.List;

public class QuestaoAssuntoDto {

	private Long idAssunto;
	
	private List<Long> questoes;
	
	public QuestaoAssuntoDto(String conteudo) {
		conteudo = conteudo.replaceAll(" ", ";");
		questoes = new ArrayList<>();
		String[] strings = conteudo.split(";");
		for (int i = 1; i < strings.length; i++) {
			questoes.add(new Long(strings[i].trim()));
		}
		this.idAssunto = new Long(strings[0].trim());
	}

	public Long getIdAssunto() {
		return idAssunto;
	}

	public void setIdAssunto(Long idAssunto) {
		this.idAssunto = idAssunto;
	}

	public List<Long> getQuestoes() {
		return questoes;
	}

	public void setQuestoes(List<Long> questoes) {
		this.questoes = questoes;
	}
	
	public String in() {
		return this.questoes.toString().replace("[", "(").replace("]", ")").replaceAll(",", ",\n");
	}
	
}
