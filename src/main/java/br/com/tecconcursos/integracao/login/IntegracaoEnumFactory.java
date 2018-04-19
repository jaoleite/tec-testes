package br.com.tecconcursos.integracao.login;

public enum IntegracaoEnumFactory {

	FACEBOOK(new FacebookLoginIntegracao()),
	GOOGLE(new GoogleLoginIntegracao()),
	TWITTER(new TwitterLoginIntegracao()),
	LINKED_IN(new LinkedInLoginIntegracao()),
	;
	
	private LoginIntegracao integracao;

	private IntegracaoEnumFactory(LoginIntegracao integracao) {
		this.integracao = integracao;
	}

	public LoginIntegracao getIntegracao() {
		return integracao;
	}
	
}
