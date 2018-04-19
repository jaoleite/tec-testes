package br.com.tecconcursos.strategy;

import org.apache.metamodel.query.builder.SatisfiedWhereBuilder;

import br.com.tecconcursos.to.RequestLogDto;

public class RequestTimeQueryWhereStrategy implements QueryWhereStrategy {

	private static final String COLUMN = "requestTime";
	
	@Override
	public void and(RequestLogDto request, SatisfiedWhereBuilder<?> where) {
		if(request.getRequestTime() != null) {
			where.and(COLUMN).greaterThanOrEquals(request.getRequestTime());
		}
	}

	@Override
	public void orderBy(SatisfiedWhereBuilder<?> where) {
		where.orderBy(COLUMN).desc();
	}

}
