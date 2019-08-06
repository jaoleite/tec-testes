package br.com.tecconcursos.url.updater;

import java.util.List;

import br.com.tecconcursos.url.UrlDto;

public interface URLUpdater<T> {

	UrlDto<T> buscarPorCodigo(long codigo);
	
	List<UrlDto<T>> listar();
	
	void atualizar(UrlDto<T> urlDto);
	
	void atualizar(List<UrlDto<T>> dtos);
	
	List<UrlDto<T>> verificar();
	
	UrlDto<T> buscarPorNome(String nome);
}
