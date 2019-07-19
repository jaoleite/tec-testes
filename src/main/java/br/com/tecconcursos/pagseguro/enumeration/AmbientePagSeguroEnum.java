package br.com.tecconcursos.pagseguro.enumeration;

public enum AmbientePagSeguroEnum {

	PRODUCAO("tecconcursos@uol.com.br", "8BB56C89D09C423E9E665B6CDD19740E"),
	;
	
	private String emailCobranca;
	
	private String token;

	private AmbientePagSeguroEnum(String emailCobranca, String token) {
		this.emailCobranca = emailCobranca;
		this.token = token;
	}

	public String getEmailCobranca() {
		return emailCobranca;
	}

	public String getToken() {
		return token;
	}
	
	/*public AccountCredentials accountCredentials() throws PagSeguroServiceException {
		AccountCredentials accountCredentials = new AccountCredentials(emailCobranca, token);
		PagSeguroConfig.setProductionEnvironment();
		accountCredentials.setProductionToken(token);
		return accountCredentials;
	}*/
	
}
