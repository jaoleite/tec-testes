package br.com.tecconcursos.integracao.login;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
	
	private Long id;

	private String email;
	
	private String senha;
	
	private List<UsuarioLoginIntegracao> logins;
	
	public Usuario() {}

	public Usuario(Long id, String email) {
		this.id = id;
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public List<UsuarioLoginIntegracao> getLogins() {
		return logins;
	}

	public void setLogins(List<UsuarioLoginIntegracao> logins) {
		this.logins = logins;
	}
	
	public void addLogin(UsuarioLoginIntegracao login) {
		if(this.logins == null) {
			this.logins = new ArrayList<UsuarioLoginIntegracao>();
		}
		this.logins.add(login);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Usuario [id=");
		builder.append(id);
		builder.append(", email=");
		builder.append(email);
		builder.append(", senha=");
		builder.append(senha);
		builder.append(", logins=");
		builder.append(logins);
		builder.append("]");
		return builder.toString();
	}

	
}
