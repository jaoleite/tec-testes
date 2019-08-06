package br.com.tecconcursos.url.alteracao;

import br.com.tecconcursos.entity.Banca;
import br.com.tecconcursos.url.UrlDto;

public class BancaAlteracaoURL implements AlteracaoURL<Banca> {

	@Override
	public void execute(UrlDto<Banca> dto) {
		// Banca gera URL por Sigla
		dto.setUrlAlterada(gerarUrl(dto.getSigla()));
	}

}
