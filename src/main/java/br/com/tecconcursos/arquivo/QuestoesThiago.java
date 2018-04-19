package br.com.tecconcursos.arquivo;

import java.util.List;

import com.google.common.base.Joiner;

public class QuestoesThiago {

	private List<String> questoes;
	private String template;
	private int idProfessor = 53;
	private Tipo tipo;
	
	public QuestoesThiago update() {
		this.template = "update questaoobjetiva set professor_id = %d where id in (%s);";
		tipo = Tipo.UPDATE;
		return this;
	}
	
	public QuestoesThiago select() {
		this.template = "select id, professor_id from questaoobjetiva where id in (%s);";
		tipo = Tipo.SELECT; 
		return this;
	}
	
	public QuestoesThiago print() {
		String st = Joiner.on(",").join(questoes);
		if(tipo == Tipo.UPDATE) {
			System.out.println(String.format(template, idProfessor, st));
		} else {
			System.out.println(String.format(template, st));
		}
		return this;
	}
	
	public QuestoesThiago() {
		List<String> list = Arquivo.of("questoes-thiaguera.txt").lsitarDoAqruivo();
		this.questoes = list;
	}
	
	public static void main(String[] args) {
		new QuestoesThiago().update().print().select().print();
	}
	
	enum Tipo {
		SELECT,
		UPDATE
	}

}
