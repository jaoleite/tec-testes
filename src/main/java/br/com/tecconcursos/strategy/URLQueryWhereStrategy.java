package br.com.tecconcursos.strategy;

import org.apache.metamodel.query.builder.SatisfiedWhereBuilder;

import br.com.tecconcursos.to.RequestLogDto;

public class URLQueryWhereStrategy implements QueryWhereStrategy {

	private static final String COLUMN = "url"; 
	
	@Override
	public void and(RequestLogDto request, SatisfiedWhereBuilder<?> where) {
		if(request.getUrl() != null && !request.getUrl().isEmpty()) {
			where.and(COLUMN).like("%" + request.getUrl() + "%");
		}
	}

	@Override
	public void orderBy(SatisfiedWhereBuilder<?> where) {
		where.orderBy(COLUMN).desc();
	}

}
