package br.com.tecconcursos.integracao.login;


public class UsuarioLoginIntegracao {

	private IntegracaoEnumFactory integracao;
	
	private String email;
	
	private Usuario usuario;

	public IntegracaoEnumFactory getIntegracao() {
		return integracao;
	}

	public void setIntegracao(IntegracaoEnumFactory integracao) {
		this.integracao = integracao;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UsuarioLoginIntegracao [integracao=");
		builder.append(integracao);
		builder.append(", email=");
		builder.append(email);
		builder.append(", usuario=");
		builder.append(usuario);
		builder.append("]");
		return builder.toString();
	}
	
}
