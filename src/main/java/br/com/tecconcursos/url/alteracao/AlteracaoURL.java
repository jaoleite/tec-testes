package br.com.tecconcursos.url.alteracao;

import br.com.tecconcursos.url.UrlDto;
import br.com.tecconcursos.util.Util;

public interface AlteracaoURL<T> {

	void execute(UrlDto<T> urlDto);
	
	default String gerarUrl(String string) {
		return Util.gerarUrl(string);
	}
	
}
