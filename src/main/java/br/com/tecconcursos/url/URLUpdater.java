package br.com.tecconcursos.url;

import java.util.List;

public interface URLUpdater {

	public UrlDto buscarPorCodigo(Long codigo);
	
	public List<UrlDto> listar();
	
	public void atualizar(UrlDto urlDto);
	
	public void atualizar(List<UrlDto> dtos);
	
	
}
