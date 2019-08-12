package br.com.tecconcursos.to.arquivo.download;

public class DownloadDto {

	private static final String INDEX_OF_CONCURSO = "concurso";
	private static final String SPLIT_BARRA = "/";
	private static final int ARRAY_POSICAO_ID_CONCURSO = 1;
	private static final int ARRAY_POSICAO_TIPO = ARRAY_POSICAO_ID_CONCURSO + 1;
	
	private Long codigoConcurso;
	
	private TipoDownloadEnum tipo;
	
	public DownloadDto(String url) {
		url = url.substring(url.indexOf(INDEX_OF_CONCURSO));
		String[] strings = url.split(SPLIT_BARRA);
		this.codigoConcurso = arrayToLong(strings);
		this.tipo = arrayToTipo(strings);
	}
	
	public Long getCodigoConcurso() {
		return codigoConcurso;
	}

	public TipoDownloadEnum getTipo() {
		return tipo;
	}

	private Long arrayToLong(String[] strings) {
		String stringIdConcurso = strings[ARRAY_POSICAO_ID_CONCURSO];
		return new Long(stringIdConcurso);
	}
	
	private TipoDownloadEnum arrayToTipo(String[] strings) {
		String stringTipo = strings[ARRAY_POSICAO_TIPO];
		return TipoDownloadEnum.buscarPorUrl(stringTipo);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DownloadDto [codigoConcurso=");
		builder.append(codigoConcurso);
		builder.append(", tipo=");
		builder.append(tipo);
		builder.append("]");
		return builder.toString();
	}

}
