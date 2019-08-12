package br.com.tecconcursos.to.arquivo.download;

public class GabaritoArquivoDownload extends AbstractDownload {

	@Override
	protected String buscarUuid() {
		return service.buscaPorCodigo(codigo).getArquivoGabarito().getUuid();
	}

}
