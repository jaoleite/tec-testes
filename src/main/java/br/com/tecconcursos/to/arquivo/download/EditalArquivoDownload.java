package br.com.tecconcursos.to.arquivo.download;

public class EditalArquivoDownload extends AbstractDownload {
	
	@Override
	protected String buscarUuid() {
		return service.buscaPorCodigo(codigo).getEdital().getArquivo().getUuid();
	}
	
}
