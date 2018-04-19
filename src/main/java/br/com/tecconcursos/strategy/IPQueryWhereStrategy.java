package br.com.tecconcursos.strategy;

import org.apache.metamodel.query.builder.SatisfiedWhereBuilder;

import br.com.tecconcursos.to.RequestLogDto;

public class IPQueryWhereStrategy implements QueryWhereStrategy {
	
	private static final String COLUMN = "ip";

	@Override
	public void and(RequestLogDto request, SatisfiedWhereBuilder<?> where) {
		if(request.getIp() != null && !request.getIp().isEmpty()) {
			where.and("ip").eq(request.getIp());
		}
	}

	@Override
	public void orderBy(SatisfiedWhereBuilder<?> where) {
		where.orderBy(COLUMN).desc();
	}

}
