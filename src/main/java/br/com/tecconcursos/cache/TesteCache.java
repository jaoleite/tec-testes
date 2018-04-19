package br.com.tecconcursos.cache;

import br.com.tecconcursos.memcached.TecMemcached;
import br.com.tecconcursos.util.TimedMap;
import br.com.tecconcursos.util.cache.CacheUsuarioValue;

public class TesteCache {

	public TesteCache() {
	}
	
	public static void main(String[] args) {
		TecMemcached memcached = TecMemcached.of();
		TimedMap<String, CacheUsuarioValue> map = memcached.get("MEMCACHED_TIMED_MAP_KEY");
		System.out.println(map.get("jaoleite@gmail.com").getSessionId());
		System.out.println("FA65302BD829CF430E41F65B850502C7");
	}

}
