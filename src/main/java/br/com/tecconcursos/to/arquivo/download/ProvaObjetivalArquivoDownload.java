package br.com.tecconcursos.to.arquivo.download;

public class ProvaObjetivalArquivoDownload extends AbstractDownload{

	@Override
	protected String buscarUuid() {
		return service.buscaPorCodigo(codigo).getArquivoProvaObjetiva().getUuid();
	}
	
}
