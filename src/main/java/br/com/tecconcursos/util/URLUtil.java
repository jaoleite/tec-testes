package br.com.tecconcursos.util;

import br.com.tecconcursos.entity.Cargo;
import br.com.tecconcursos.entity.Orgao;

public class URLUtil {

	public URLUtil() {
	}
	
	public static void gerarURL(Orgao orgao) {
		if(orgao == null || Util.vazio(orgao.getSigla())) {
			throw new NullPointerException("A sigla do orgão precisa ser preenchida (Órgão gera URL por Sigla)");
		}
		gerarURL("órgao", orgao.getSigla());
	}
	
	public static void gerarURL(Cargo cargo) {
		if(cargo == null || Util.vazio(cargo.getNome())) {
			throw new NullPointerException("A nome do cargo precisa ser preenchido (Cargo gera URL por Nome)");
		}
		gerarURL("cargo", cargo.getNome());
	}

	public static void gerarURL(String label, String nome) {
		if(Util.vazio(label)) {
			label = "";
		} else {
			label = String.format("URL do %s: ", label);
		}
		System.out.println(label + Util.gerarUrl(nome));
	}
	
	public static void main(String[] args) {
		Cargo cargo = new Cargo();
		cargo.setNome("Agente de Segurança Socioeducativo (SAP SC)");
		cargo.setSigla("Ag SS (SAP SC)");
		gerarURL(cargo);
		Orgao orgao = new Orgao();
		orgao.setNome("Secretaria de Estado da Administração Prisional e Socioeducativa de Santa Catarina");
		orgao.setSigla("SAP SC");
		gerarURL(orgao);
	}
}
