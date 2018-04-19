package br.com.tecconcursos.to.enums;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Teste {

	private Set<TesteEnum> set = new HashSet<TesteEnum>();
	
	public Teste add(TesteProxyInterface testeProxyInterface) {
		set.addAll(testeProxyInterface.getEnums());
		return this;
	}
	
	public List<TesteEnum> listar() {
		return new ArrayList<TesteEnum>(set);
	}
	
	public static void main(String[] args) {
		Teste teste = new Teste();
		teste.add(new TesteCatalogador()).add(new TesteTeoria());
		System.out.println(teste.listar());
	}

}
