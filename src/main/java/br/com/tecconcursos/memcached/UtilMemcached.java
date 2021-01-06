package br.com.tecconcursos.memcached;

import br.com.tecconcursos.context.ApplicationContext;
import br.com.tecconcursos.entity.Parametrizacao;
import br.com.tecconcursos.service.CacheService;

public class UtilMemcached {

	public static void main(String[] args) {
		CacheService cacheService = ApplicationContext.find(CacheService.class);
		cacheService.zerarCache();
		Parametrizacao parametrizacao = cacheService.buscarConfiguracoesPromocao();
		System.out.println(parametrizacao.getJson());
	}
	
}
