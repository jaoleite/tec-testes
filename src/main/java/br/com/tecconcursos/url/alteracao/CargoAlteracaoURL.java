package br.com.tecconcursos.url.alteracao;

import br.com.tecconcursos.entity.Cargo;
import br.com.tecconcursos.url.UrlDto;

public class CargoAlteracaoURL implements AlteracaoURL<Cargo> {

	public CargoAlteracaoURL() {
	}

	@Override
	public void execute(UrlDto<Cargo> dto) {
		// Cargo gera URL por Nome
		dto.setUrlAlterada(gerarUrl(dto.getNome()));
	}

}
