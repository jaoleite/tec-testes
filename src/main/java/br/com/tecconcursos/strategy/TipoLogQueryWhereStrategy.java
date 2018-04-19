package br.com.tecconcursos.strategy;

import java.util.List;

import org.apache.metamodel.query.builder.SatisfiedWhereBuilder;

import br.com.tecconcursos.to.RequestLogDto;
import br.com.tecconcursos.to.enums.TipoLogEnum;

public class TipoLogQueryWhereStrategy implements QueryWhereStrategy {
	
	private static final String COLUMN = "tipo";

	@Override
	public void and(RequestLogDto request, SatisfiedWhereBuilder<?> where) {
		List<TipoLogEnum> tipos = request.getListaTipo();
		if(tipos != null && !tipos.isEmpty()) {
			for (TipoLogEnum tipo : tipos) {
				where.and(COLUMN).eq(tipo);
			}
		}
	}

	@Override
	public void orderBy(SatisfiedWhereBuilder<?> where) {
		where.orderBy(COLUMN).desc();
	}

}
