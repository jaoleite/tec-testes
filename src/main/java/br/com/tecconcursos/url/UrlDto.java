package br.com.tecconcursos.url;

import java.io.Serializable;
import java.lang.reflect.Field;

import br.com.tecconcursos.url.alteracao.AlteracaoURL;

public class UrlDto<T> {

	private Long id;
	
	private String url;
	
	private String nome;
	
	private String sigla;
	
	private String urlAlterada;

	private T t;
	
	public UrlDto() {
	}
	
	@SuppressWarnings("unchecked")
	public UrlDto(T t) {
		this.t = t;
		this.nome = (String) getByFieldName("nome");
		this.id = (Long) getByFieldName("id");
		this.url = (String) getByFieldName("url");
		this.sigla = (String) getByFieldName("sigla");
		
		URLEnum urlEnum = URLEnum.buscarPorEntity((Class<? extends Serializable>) t.getClass());
		AlteracaoURL<Object> alteracaoURL = urlEnum.getAlteracaoUrlInstance();
		alteracaoURL.execute((UrlDto<Object>) this);
	}
	
	private Object getByFieldName(String fieldName) {
		try {
			Field field = t.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			return field.get(t);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	
	public String getUrlAlterada() {
		return urlAlterada;
	}

	public void setUrlAlterada(String urlAlterada) {
		this.urlAlterada = urlAlterada;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UrlDto [id=");
		builder.append(id);
		builder.append(", url=");
		builder.append(url);
		builder.append(", nome=");
		builder.append(nome);
		builder.append(", sigla=");
		builder.append(sigla);
		builder.append(", urlAlterada=");
		builder.append(urlAlterada);
		builder.append("]");
		return builder.toString();
	}

}
