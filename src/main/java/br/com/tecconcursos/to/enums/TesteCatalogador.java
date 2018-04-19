package br.com.tecconcursos.to.enums;

import java.util.HashSet;
import java.util.Set;

public class TesteCatalogador implements TesteProxyInterface {

	@Override
	public Set<TesteEnum> getEnums() {
		Set<TesteEnum> enums = new HashSet<TesteEnum>();
		enums.add(TesteEnum.CATALOGADOR_A);
		enums.add(TesteEnum.CATALOGADOR_B);
		return enums;
	}
	
}
