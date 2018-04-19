package br.com.tecconcursos.integracao.login;

import java.util.Date;

public class IntegracaoDto {

	private String email;
	
	private String nome;
	
	private String sobrenome;
	
	private Date dataNascimento;
	
	private Usuario usuario;
	
	private IntegracaoEnumFactory integracao;
	
	public IntegracaoDto(String email, String nome, String sobrenome, Date dataNascimento, IntegracaoEnumFactory integracao) {
		super();
		this.email = email;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.dataNascimento = dataNascimento;
		this.integracao = integracao;
	}

	public String getEmail() {
		return email;
	}

	public String getNome() {
		return nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public IntegracaoEnumFactory getIntegracao() {
		return integracao;
	}
	
}
