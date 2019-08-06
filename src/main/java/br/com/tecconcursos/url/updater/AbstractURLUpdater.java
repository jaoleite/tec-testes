package br.com.tecconcursos.url.updater;

import java.util.List;
import java.util.stream.Collectors;

import br.com.tecconcursos.context.ApplicationContext;
import br.com.tecconcursos.url.UrlDto;

public abstract class AbstractURLUpdater<T> implements URLUpdater<T> {

	public AbstractURLUpdater() {
		ApplicationContext.injectFields(this);
	}
	
	public List<UrlDto<T>> transform(List<T> list) {
		List<UrlDto<T>> listTransform = list.stream().map(p -> {
			return new UrlDto<T>(p);
		}).collect(Collectors.toList());
		return listTransform;
	}
	
	public List<UrlDto<T>> verificar() {
		List<UrlDto<T>> dtos = listar();
		return dtos.stream().filter(p -> !p.getUrl().equals(p.getUrlAlterada())).collect(Collectors.toList());
	}
}
