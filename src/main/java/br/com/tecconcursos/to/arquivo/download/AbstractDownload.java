package br.com.tecconcursos.to.arquivo.download;

import com.google.inject.Inject;

import br.com.tecconcursos.context.ApplicationContext;
import br.com.tecconcursos.service.CadastroConcursoService;

public abstract class AbstractDownload {
	
	@Inject protected CadastroConcursoService service;
	
	protected Long codigo;
	
	protected DownloadDto download;

	public AbstractDownload() {
		this.service = ApplicationContext.find(CadastroConcursoService.class);
	}
	
	public final AbstractDownload adicionarDownload(DownloadDto download) {
		this.download = download;
		this.codigo = (download != null) ? download.getCodigoConcurso() : null;
		return this;
	}
	
	public final String getUuid() {
		falharSe();
		return buscarUuid();
	}
	
	private void falharSe() {
		if(this.codigo == null) {
			throw new NullPointerException("codigo do concurso esta nulo");
		}
	}
	
	protected abstract String buscarUuid();

}
