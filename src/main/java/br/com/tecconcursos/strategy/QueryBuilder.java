package br.com.tecconcursos.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.metamodel.query.builder.SatisfiedWhereBuilder;
import org.reflections.Reflections;

import br.com.tecconcursos.to.RequestLogDto;

public class QueryBuilder {

	private static Package pack = Package.getPackage("br.com.tecconcursos.strategy");
	
	private RequestLogDto request;
	
	private SatisfiedWhereBuilder<?> where;
	
	private List<QueryWhereStrategy> queryWhereStrategies;
	
	public QueryBuilder(RequestLogDto request, SatisfiedWhereBuilder<?> where) {
		this.request = request;
		this.where = where;
		this.queryWhereStrategies = getListQueryWhereStrategy();
	}

	public void buildWhere() {
		for (QueryWhereStrategy queryWhereStrategy : queryWhereStrategies) {
			queryWhereStrategy.and(request, where);
		}
	}
	
	public void buildOrderBy() {
		for (QueryWhereStrategy queryWhereStrategy : queryWhereStrategies) {
			queryWhereStrategy.orderBy(where);
		}
	}
	
	private List<QueryWhereStrategy> getListQueryWhereStrategy() {
		List<QueryWhereStrategy> list = new ArrayList<QueryWhereStrategy>();
		try {
			Reflections reflections = new Reflections(pack.getName());
			Set<Class<? extends QueryWhereStrategy>> allClasses = reflections.getSubTypesOf(QueryWhereStrategy.class);
			for (Class<? extends QueryWhereStrategy> clazz : allClasses) {
				list.add(clazz.newInstance());
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return list;
	}
	
}
