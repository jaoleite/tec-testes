package br.com.tecconcursos.strategy;

import org.apache.metamodel.query.builder.SatisfiedWhereBuilder;

import br.com.tecconcursos.to.RequestLogDto;

public interface QueryWhereStrategy {

	void and(RequestLogDto request, SatisfiedWhereBuilder<?> where);
	
	void orderBy(SatisfiedWhereBuilder<?> where);
	
}
