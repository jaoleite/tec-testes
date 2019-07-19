package br.com.tecconcursos.ngrok;

public class Config {

	private String addr;
	
	private Boolean inspect;
	
	public Config() {
	}
	
	public Config(String addr, Boolean inspect) {
		super();
		this.addr = addr;
		this.inspect = inspect;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public Boolean getInspect() {
		return inspect;
	}

	public void setInspect(Boolean inspect) {
		this.inspect = inspect;
	}

	public static Config mock() {
		return new Config("localhost:80",  true);
	}
}
