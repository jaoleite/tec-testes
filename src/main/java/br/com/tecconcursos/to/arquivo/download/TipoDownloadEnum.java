package br.com.tecconcursos.to.arquivo.download;

import java.util.Arrays;
import java.util.List;

public enum TipoDownloadEnum {

	EDITAL(new EditalArquivoDownload()),
	PROVA_OBJETIVA(new ProvaObjetivalArquivoDownload()),
	PROVA_DISCURSIVA(new ProvaDiscursivaArquivoDownload()),
	GABARITO(new GabaritoArquivoDownload()),
	;
	
	private TipoDownloadEnum(AbstractDownload download) {
		this.download = download;
	}

	private AbstractDownload download;
	
	public String getCodigo() {
		return name();
	}
	
	public AbstractDownload getDownload() {
		return download;
	}
	
	public static TipoDownloadEnum buscarPorCodigo(String codigo) {
		List<TipoDownloadEnum> enums = Arrays.asList(values());
		for (TipoDownloadEnum tipo : enums) {
			if(tipo.getCodigo().equals(codigo)) {
				return tipo;
			}
		}
		return null;
	}
	
	public static TipoDownloadEnum buscarPorUrl(String url) {
		String codigo = url.replaceAll("-", "_").toUpperCase();
		return buscarPorCodigo(codigo);
	}
	
}
