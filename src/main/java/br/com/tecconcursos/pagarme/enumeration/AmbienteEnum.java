package br.com.tecconcursos.pagarme.enumeration;

public enum AmbienteEnum {

	PRODUCAO("ak_live_VBAGJI31Mt3dwi1OLvNkkw5xd57tyV", "ek_live_GJ9TRfiUj4Zzlbfe2WHcoQM6EtxZeF"),
	HOMOLOGACAO("ak_test_jBC3lM7I5vxQ3X3RlhlLlwbIfWjiit", "ek_test_2chIZxCrGnqs1DGqU5s4zKjjJNTHar"),
	DESENVOLVIMENTO("ak_test_SEon7GVHje3VeOanETZ5WCgByyJsec", "ek_test_ooXXMQW3dOJK6wL8Wnpt7rIY4ofVzI"),
	;
	
	private String apiKey;
	private String encryptionKey;

	private AmbienteEnum(String apiKey, String encryptionKey) {
		this.apiKey = apiKey;
		this.encryptionKey = encryptionKey;
	}

	public String getApiKey() {
		return apiKey;
	}

	public String getEncryptionKey() {
		return encryptionKey;
	}
	
	public static AmbienteEnum getAmbiente() {
		return DESENVOLVIMENTO;
	}
	
}
