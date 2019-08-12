package br.com.tecconcursos.to.arquivo.download;

public class ProvaDiscursivaArquivoDownload extends AbstractDownload {

	@Override
	protected String buscarUuid() {
		return service.buscaPorCodigo(codigo).getArquivoProvaDiscursiva().getUuid();
	}

}
