package br.com.tecconcursos.url;

public enum URLEnum {

	CARGO("Cargo", CargoUpdater.class),
	ORGAO("Orgao", OrgaoUpdater.class),
	BANCA("Banca", BancaUpdater.class),
	;
	
	private String aba;
	
	private Class<? extends URLUpdater> updater;

	private URLEnum(String aba, Class<? extends URLUpdater> updater) {
		this.aba = aba;
		this.updater = updater;
	}
	
	public String getAba() {
		return aba;
	}
	
	public Class<? extends URLUpdater> getClassUpdater() {
		return updater;
	}
	
	public URLUpdater getUpdater() {
		try {
			return updater.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		URLEnum urlEnum = BANCA;
		urlEnum.getUpdater();
	}
	
}
