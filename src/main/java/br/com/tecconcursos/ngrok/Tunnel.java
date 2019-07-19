package br.com.tecconcursos.ngrok;

import com.google.gson.annotations.SerializedName;

public class Tunnel {

	private String name;
	
	private String uri;
	
	@SerializedName("public_url")
	private String publicUrl;
	
	private String proto;
	
	private Config config;
	
	public Tunnel() {
	}
	
	public Tunnel(String name, String uri, String publicUrl, String proto) {
		super();
		this.name = name;
		this.uri = uri;
		this.publicUrl = publicUrl;
		this.proto = proto;
	}
	
	public Tunnel(String name, String uri, String publicUrl, String proto, Config config) {
		super();
		this.name = name;
		this.uri = uri;
		this.publicUrl = publicUrl;
		this.proto = proto;
		this.config = config;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getPublicUrl() {
		return publicUrl;
	}

	public void setPublicUrl(String publicUrl) {
		this.publicUrl = publicUrl;
	}

	public String getProto() {
		return proto;
	}

	public void setProto(String proto) {
		this.proto = proto;
	}
	
	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public static Tunnel mock() {
		return new Tunnel("command_line (http)", "/api/tunnels/command_line+%28http%29", "https://2a780ab6.ngrok.io", "https", Config.mock());
	}

}
