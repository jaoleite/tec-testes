package br.com.tecconcursos.ngrok;

public enum NgrokConfiguration {

	DEV("D:\\Desenvolvimento\\ngrok\\", "2jD4aSomcG1TTJEuhh7fj_2DgMJ3g3idEzmyb5BmoK3", 80),
	;
	
	private String path;
	
	private String keyAuthentication;
	
	private int port;

	private NgrokConfiguration(String path, String keyAuthentication, int port) {
		this.path = path;
		this.keyAuthentication = keyAuthentication;
		this.port = port;
	}

	public String getPath() {
		return path;
	}

	public String getKeyAuthentication() {
		return keyAuthentication;
	}

	public int getPort() {
		return port;
	}

}
