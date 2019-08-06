package br.com.tecconcursos.url.updater;

import java.util.List;

import com.google.inject.Inject;

import br.com.tecconcursos.entity.Orgao;
import br.com.tecconcursos.service.CadastroOrgaoService;
import br.com.tecconcursos.url.UrlDto;

public class OrgaoUpdater extends AbstractURLUpdater<Orgao> {

	@Inject CadastroOrgaoService service;
	
	@Override
	public UrlDto<Orgao> buscarPorCodigo(long codigo) {
		Orgao orgao = service.buscarOrgaoPorCodigo(codigo);
		return new UrlDto<Orgao>(orgao);
	}

	@Override
	public List<UrlDto<Orgao>> listar() {
		List<Orgao> orgaos = service.listarOrgao();
		return transform(orgaos);
	}

	@Override
	public void atualizar(UrlDto<Orgao> urlDto) {
		Orgao orgao = service.buscarOrgaoPorCodigo(urlDto.getId());
		orgao.setUrl(urlDto.getUrlAlterada());
		//service.salvar(orgao);
		System.out.println("OrgaoUpdater.atualizar()");
	}

	@Override
	public void atualizar(List<UrlDto<Orgao>> dtos) {
		dtos.stream().forEach(dto -> {
			atualizar(dto);
		});
	}
	
	@Override
	public UrlDto<Orgao> buscarPorNome(String nome) {
		return null;
	}

}
