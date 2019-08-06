package br.com.tecconcursos.url.alteracao;

import br.com.tecconcursos.entity.Orgao;
import br.com.tecconcursos.url.UrlDto;

public class OrgaoAlteracaoURL implements AlteracaoURL<Orgao>{

	@Override
	public void execute(UrlDto<Orgao> dto) {
		// Órgão gera URL por Sigla
		dto.setUrlAlterada(gerarUrl(dto.getSigla()));
	}

}
