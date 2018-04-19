package br.com.tecconcursos.arquivo.excel;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import br.com.tecconcursos.util.Util;

public class FromToDto {

	private static final String SPLIT = ";";
	
	private String from;
	
	private String to;
	
	public FromToDto() {}

	public FromToDto(String from, String to) {
		super();
		this.from = from;
		this.to = to;
	}

	public FromToDto(String row) {
		if(!Util.vazio(row)) {
			String[] array = row.split(SPLIT);
			this.from = tratarURI(array[0]);
			this.to = tratarURI(array[array.length - 1]);
			System.out.println(this.to);
		}
	}
	
	private String tratarURI(String uri) {
		try {
			URL url = URI.create(uri).toURL();
			if(Util.vazio((url.getPath()))) {
				return "/";
			}
			return url.getPath();
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}
	
	public boolean isDto() {
		if(!Util.vazio(this.from) && !Util.vazio(this.to)) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ToFromDto [from=");
		builder.append(from);
		builder.append(", to=");
		builder.append(to);
		builder.append("]");
		return builder.toString();
	}
	
}
