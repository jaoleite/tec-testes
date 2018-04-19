package br.com.tecconcursos.to.enums;

import java.util.Arrays;
import java.util.List;

public enum TipoLogEnum {

	E("Erro"),
	S("Ok"),
	;
	
	private String descricao;
	
	private TipoLogEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public String getCodigo() {
		return name();
	}
	
	public static List<TipoLogEnum> listar() {
		return Arrays.asList(values());
	}
	
	public static TipoLogEnum buscarPorCodigo(String codigo) {
		List<TipoLogEnum> enums = listar();
		for (TipoLogEnum tipoLogEnum : enums) {
			if(tipoLogEnum.getCodigo().equalsIgnoreCase(codigo)) {
				return tipoLogEnum;
			}
		}
		return null;
	}
	
}
