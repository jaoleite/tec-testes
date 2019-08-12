package br.com.tecconcursos.util.questao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

public class QuestaoAssunto {

	private static final String FILE = "C:\\Users\\jaoin\\Desktop\\questao-assunto.txt";
	private String conteudo;
	QuestaoAssuntoDto questaoAssuntoDto;
	
	public QuestaoAssunto() {
		try {
			this.conteudo = IOUtils.toString(new FileInputStream(new File(FILE)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public QuestaoAssunto transform() {
		this.questaoAssuntoDto = new QuestaoAssuntoDto(this.conteudo);
		return this;
	}
	
	public void in() {
		System.out.println(this.questaoAssuntoDto.in());
	}
	
	public static void main(String[] args) {
		new QuestaoAssunto().transform().in();
	}
	

}
