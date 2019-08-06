package br.com.tecconcursos.url.updater;

import java.util.List;

import com.google.inject.Inject;

import br.com.tecconcursos.entity.Cargo;
import br.com.tecconcursos.service.CadastroCargoService;
import br.com.tecconcursos.url.UrlDto;

public class CargoUpdater extends AbstractURLUpdater<Cargo> {
	
	@Inject CadastroCargoService service;

	@Override
	public UrlDto<Cargo> buscarPorCodigo(long codigo) {
		Cargo cargo = service.buscarCargoPorCodigo(codigo);
		UrlDto<Cargo> dto = new UrlDto<Cargo>(cargo);
		return dto;
	}

	@Override
	public List<UrlDto<Cargo>> listar() {
		List<Cargo> cargos = service.listarCargo();
		return transform(cargos);
	}

	@Override
	public void atualizar(UrlDto<Cargo> urlDto) {
		Cargo cargo = service.buscarCargoPorCodigo(urlDto.getId());
		cargo.setUrl(urlDto.getUrlAlterada());
		//service.salvar(cargo);
		System.out.println("CargoUpdater.atualizar()");
	}

	@Override
	public void atualizar(List<UrlDto<Cargo>> dtos) {
		dtos.stream().forEach(dto -> {
			atualizar(dto);
		});
	}

	@Override
	public UrlDto<Cargo> buscarPorNome(String nome) {
		return null;
	}

}
