package br.com.tecconcursos.questoes;

import java.util.List;

import br.com.tecconcursos.context.ApplicationContext;
import br.com.tecconcursos.entity.QuestaoObjetiva;
import br.com.tecconcursos.entity.enumeration.StatusQuestaoEnum;
import br.com.tecconcursos.service.CadastroQuestaoObjetivaService;

public class QuestaoDesativada {

	private CadastroQuestaoObjetivaService service;
	
	public QuestaoDesativada() {
		service = ApplicationContext.find(CadastroQuestaoObjetivaService.class);
	}
	
	public List<QuestaoObjetiva> listar() {
		return service.listarQuestaoObjetiva(true, StatusQuestaoEnum.DESATIVADA);
	}
	
	public void teste() {
		
	}
	
	public static void main(String[] args) {
		new QuestaoDesativada().listar();
	}

}
