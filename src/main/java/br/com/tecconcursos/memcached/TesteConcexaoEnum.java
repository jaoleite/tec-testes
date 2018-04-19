package br.com.tecconcursos.memcached;

public enum TesteConcexaoEnum {

	PRODUCAO("tecmemcached.ubxn0s.cfg.sae1.cache.amazonaws.com", 11211),
	HOMOLOGACAO("homologacaocache.ubxn0s.cfg.sae1.cache.amazonaws.com", 11211),
	DESENVOLVIMENTO("localhost", 11211),
	;
	
	private String host;
	
	private Integer port;

	private TesteConcexaoEnum(String host, Integer port) {
		this.host = host;
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public Integer getPort() {
		return port;
	}
	
}
