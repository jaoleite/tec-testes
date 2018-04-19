package br.com.tecconcursos.sql;

import java.util.Arrays;
import java.util.List;

public class InsertLiberacaoJMeter {

	private List<Long> ids = ListHelper.listarIds();
	private String queryTemplate;
	
	public InsertLiberacaoJMeter() {
		
	}
	
	public InsertLiberacaoJMeter build() {
		template();
		for (Long id : ids) {
			System.out.println(String.format(queryTemplate, id));
		}
		return this;
	}
	
	public InsertLiberacaoJMeter template() {
		StringBuilder builder = new StringBuilder();
		builder.append("insert into liberacao(id, expiracao, observacao, tipo, produto_id, usuariosite_id, dataliberacao) ");
		builder.append("values(nextval('SeqLiberacao'), '2018-04-30', 'Liberação para teste com JMeter', 'OUTRO', 1, %d, '2018-04-06 15:00:00');");
		queryTemplate = builder.toString();
		return this;
	}
	
	public static void main(String[] args) {
		new InsertLiberacaoJMeter().build();
	}
	
	public static class ListHelper {
		
		public static List<Long> listarIds() {
			return Arrays.asList(ids);
		}
		
		private static Long[] ids = new Long[]{
		230480L,
		230345L,
		230264L,
		230262L,
		230228L,
		230118L,
		230106L,
		229999L,
		229860L,
		229739L,
		229686L,
		229403L,
		229401L,
		229349L,
		229322L,
		229300L,
		229281L,
		229274L,
		229236L,
		229219L,
		229161L,
		229127L,
		229094L,
		229091L,
		229085L,
		229062L,
		229038L,
		229031L,
		229003L,
		228841L,
		228815L,
		228814L,
		228789L,
		228784L,
		228781L,
		228599L,
		228576L,
		228567L,
		228556L,
		228530L,
		228522L,
		228480L,
		228406L,
		228391L,
		228234L,
		228221L,
		228217L,
		228214L,
		228196L,
		228181L,
		228178L,
		228171L,
		228157L,
		228152L,
		228113L,
		228111L,
		228106L,
		228072L,
		228057L,
		227999L,
		227993L,
		227992L,
		227973L,
		227971L,
		227909L,
		227887L,
		227886L,
		227874L,
		227819L,
		227810L,
		227794L,
		227753L,
		227752L,
		227730L,
		227719L,
		227692L,
		227675L,
		227654L,
		227638L,
		227621L,
		227601L,
		227546L,
		227517L,
		227470L,
		227456L,
		227437L,
		227432L,
		227426L,
		227425L,
		227421L,
		227419L,
		227411L,
		227398L,
		227381L,
		227376L,
		227365L,
		227356L,
		227336L,
		227321L,
		227312L};
	}

}
