package br.com.tecconcursos.search;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import br.com.tecconcursos.entity.QuestaoObjetiva;

public class QuestaoIndexacao extends Indexacao<QuestaoObjetiva> {
	
	//private CadastroQuestaoObjetivaService service;
	
	@Override
	public void indexarTudo() {
		//int quantidadeQuestoesPorPagina = 1000;
		//PageRequest pagina = new PageRequest(1, quantidadeQuestoesPorPagina);
		//int quantidadeQuestoes = service.quantidadeQuestoes();
		//int quantidadeIteracao = (quantidadeQuestoes / quantidadeQuestoesPorPagina) + 1;
		
		
	}
	
	@Override
	public void indexar(QuestaoObjetiva questao) {
		
	}
	
	public void indexDirectory() {
		
	}
	
	public static void main(String[] args) {
		Indexacao<QuestaoObjetiva> indexacao = new QuestaoIndexacao();
		Type mySuperclass = indexacao.getClass().getGenericSuperclass();
		Type type = ((ParameterizedType)mySuperclass).getActualTypeArguments()[0];
		System.out.println(type);
		
		System.out.println(indexacao.getClass().getGenericInterfaces()[0]);
		
		//indexacao.getClass().getGenericSuperclass().getActualTypeArguments()[0];
		
		System.out.println(indexacao.getClass().getEnclosingClass());
	}

}
