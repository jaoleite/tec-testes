package br.com.tecconcursos.to;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import br.com.tecconcursos.entity.enumeration.TipoLiberacaoEnum;

public class TesteDto {

	private String dataLiberacao;
	
	private String dataExpiracao;
	
	private String observacao;
	
	private Long idProduto;
	
	private Long idUsuarioSite;
	
	private TipoLiberacaoEnum tipo;

	public TesteDto() {}
	
	public TesteDto(String dataLiberacao, String dataExpiracao, String observacao, Long idProduto, Long idUsuarioSite, TipoLiberacaoEnum tipo) {
		super();
		this.dataLiberacao = dataLiberacao;
		this.dataExpiracao = dataExpiracao;
		this.observacao = observacao;
		this.idProduto = idProduto;
		this.idUsuarioSite = idUsuarioSite;
		this.tipo = tipo;
	}

	public String getDataLiberacao() {
		return dataLiberacao;
	}

	public void setDataLiberacao(String dataLiberacao) {
		this.dataLiberacao = dataLiberacao;
	}

	public String getDataExpiracao() {
		return dataExpiracao;
	}

	public void setDataExpiracao(String dataExpiracao) {
		this.dataExpiracao = dataExpiracao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Long getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Long idProduto) {
		this.idProduto = idProduto;
	}

	public Long getIdUsuarioSite() {
		return idUsuarioSite;
	}

	public void setIdUsuarioSite(Long idUsuarioSite) {
		this.idUsuarioSite = idUsuarioSite;
	}

	public TipoLiberacaoEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoLiberacaoEnum tipo) {
		this.tipo = tipo;
	}
	
	public Map<String, String> toMap() throws IllegalArgumentException, IllegalAccessException {
		Map<String, String> map = new HashMap<String, String>();
		Field[] fields = this.getClass().getDeclaredFields();
		for (Field field : fields) {
			Object value = field.get(this);
			if(field.getType() == Date.class) {
				String string = formatDate(field.getName(), (Date) value);
				map.put(field.getName(), string);
			} else {
				map.put(field.getName(), value.toString());
			}
		}

		return map;
	}
	
	private String formatDate(String fieldName, Date date) {
		String dataFormatada = null;
		String pattern = "yyyy-MM-dd";
		if(fieldName.equals("dataLiberacao")) {
			pattern += " HH:mm:ss";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		dataFormatada = dateFormat.format(date);
		return dataFormatada;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TesteDto [dataLiberacao=");
		builder.append(dataLiberacao);
		builder.append(", dataExpiracao=");
		builder.append(dataExpiracao);
		builder.append(", observacao=");
		builder.append(observacao);
		builder.append(", idProduto=");
		builder.append(idProduto);
		builder.append(", idUsuarioSite=");
		builder.append(idUsuarioSite);
		builder.append(", tipo=");
		builder.append(tipo);
		builder.append("]");
		return builder.toString();
	}

	public static void main(String[] args) throws Exception {
		String dataLiberacao = "2015-05-25 12:00:00";
		String dataExpiracao = "2015-05-30";
		TesteDto dto = new TesteDto(dataLiberacao, dataExpiracao, "Troca de curso", 1L, 2L, TipoLiberacaoEnum.TROCA);
		dto.toMap();
	}

}
