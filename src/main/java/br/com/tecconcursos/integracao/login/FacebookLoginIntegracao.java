package br.com.tecconcursos.integracao.login;

public class FacebookLoginIntegracao implements LoginIntegracao {

	@Override
	public IntegracaoDto getIntegracao() {
		//IntegracaoDto integracao = new IntegracaoDto("jaoleite@facebook.com", null, null, null, IntegracaoEnumFactory.FACEBOOK);
		IntegracaoDto integracao = new IntegracaoDto("jaoleite@gmail.com", null, null, null, IntegracaoEnumFactory.FACEBOOK);
		return integracao;
	}

}
