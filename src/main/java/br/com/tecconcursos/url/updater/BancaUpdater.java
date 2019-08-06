package br.com.tecconcursos.url.updater;

import java.util.List;

import com.google.inject.Inject;

import br.com.tecconcursos.entity.Banca;
import br.com.tecconcursos.service.CadastroBancaService;
import br.com.tecconcursos.url.UrlDto;

public class BancaUpdater extends AbstractURLUpdater<Banca> {
	
	@Inject CadastroBancaService service;

	@Override
	public UrlDto<Banca> buscarPorCodigo(long codigo) {
		Banca banca = service.buscarBancaPorCodigo(codigo);
		return new UrlDto<Banca>(banca);
	}

	@Override
	public List<UrlDto<Banca>> listar() {
		List<Banca> bancas = service.listarBanca();
		return transform(bancas);
	}

	@Override
	public void atualizar(UrlDto<Banca> urlDto) {
		Banca banca = service.buscarBancaPorCodigo(urlDto.getId());
		banca.setUrl(urlDto.getUrlAlterada());
		//service.salvar(banca);
		System.out.println("BancaUpdater.atualizar()");
	}

	@Override
	public void atualizar(List<UrlDto<Banca>> dtos) {
		dtos.stream().forEach(dto -> {
			atualizar(dto);
		});
	}

	@Override
	public UrlDto<Banca> buscarPorNome(String nome) {
		
		return null;
	}

}
