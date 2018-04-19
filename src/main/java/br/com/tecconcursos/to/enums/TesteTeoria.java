package br.com.tecconcursos.to.enums;

import java.util.HashSet;
import java.util.Set;

public class TesteTeoria implements TesteProxyInterface {

	@Override
	public Set<TesteEnum> getEnums() {
		Set<TesteEnum> enums = new HashSet<TesteEnum>();
		enums.add(TesteEnum.TEORIA_A);
		enums.add(TesteEnum.TEORIA_B);
		return enums;
	}
	
}
