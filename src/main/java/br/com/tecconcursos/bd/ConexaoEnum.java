package br.com.tecconcursos.bd;

public enum ConexaoEnum {
	
	PRODUCAO("tecconcursos_main", "localhost", 5454, "tecconcursos", "t3c51mpl35"),
	DESENVOLVIMENTO("tecconcursos_main", "localhost", 5432, "postgres", "1234"),
	;
	
	private String database;
	
	private String host;
	
	private Integer port;
	
	private String username;
	
	private String password;

	private ConexaoEnum(String database, String host, Integer port, String username, String password) {
		this.database = database;
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
	}

	public String getDatabase() {
		return database;
	}

	public String getHost() {
		return host;
	}

	public Integer getPort() {
		return port;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
	
}
