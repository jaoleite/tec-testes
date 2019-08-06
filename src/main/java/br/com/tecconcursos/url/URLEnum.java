package br.com.tecconcursos.url;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import br.com.tecconcursos.entity.Banca;
import br.com.tecconcursos.entity.Cargo;
import br.com.tecconcursos.entity.Orgao;
import br.com.tecconcursos.url.alteracao.AlteracaoURL;
import br.com.tecconcursos.url.alteracao.BancaAlteracaoURL;
import br.com.tecconcursos.url.alteracao.CargoAlteracaoURL;
import br.com.tecconcursos.url.alteracao.OrgaoAlteracaoURL;
import br.com.tecconcursos.url.updater.BancaUpdater;
import br.com.tecconcursos.url.updater.CargoUpdater;
import br.com.tecconcursos.url.updater.OrgaoUpdater;
import br.com.tecconcursos.url.updater.URLUpdater;

public enum URLEnum {

	CARGO("Cargo", CargoUpdater.class, Cargo.class, CargoAlteracaoURL.class),
	ORGAO("Orgao", OrgaoUpdater.class, Orgao.class, OrgaoAlteracaoURL.class),
	BANCA("Banca", BancaUpdater.class, Banca.class, BancaAlteracaoURL.class),
	;
	
	private String aba;
	
	private Class<? extends URLUpdater<? extends Serializable>> updater;
	
	private Class<? extends Serializable> entity;
	
	private Class<? extends AlteracaoURL<?>> alteracaoUrl;
	
	private URLEnum(String aba, Class<? extends URLUpdater<? extends Serializable>> updater, Class<? extends Serializable> entity, Class<? extends AlteracaoURL<?>> alteracaoUrl) {
		this.aba = aba;
		this.updater = updater;
		this.entity = entity;
		this.alteracaoUrl = alteracaoUrl;
	}

	public String getAba() {
		return aba;
	}
	
	public Class<? extends URLUpdater<? extends Serializable>> getClassUpdater() {
		return updater;
	}
	
	public Class<? extends Serializable> getEntity() {
		return entity;
	}

	public Class<? extends AlteracaoURL<?>> getAlteracaoUrl() {
		return alteracaoUrl;
	}

	public URLUpdater<? extends Serializable> getUpdater() {
		try {
			return updater.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public AlteracaoURL<Object> getAlteracaoUrlInstance() {
		try {
			return (AlteracaoURL<Object>) alteracaoUrl.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static URLEnum buscarPorEntity(Class<? extends Serializable> entity) {
		return Arrays.asList(values()).stream().filter(p -> p.getEntity().equals(entity)).findFirst().get();
	}

	public <T> void teste() {
		
	}
	
	public static void main(String[] args) {
		// Órgão e Banca geram URL por Sigla
		// Cargo gera URL por Nome
		URLEnum urlEnum = URLEnum.CARGO;
		URLUpdater<? extends Serializable> updater = urlEnum.getUpdater();
		List<?> dtos = updater.verificar();
		updater.atualizar((List)dtos);
	}
	
}
