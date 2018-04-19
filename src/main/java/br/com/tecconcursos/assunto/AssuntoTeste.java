package br.com.tecconcursos.assunto;

import java.util.List;

import br.com.tecconcursos.entity.Assunto;
import br.com.tecconcursos.to.questoes.HierarquiaDto;
import br.com.tecconcursos.util.HierarquiaUtil;

public class AssuntoTeste {

	public AssuntoTeste() {
		
	}
	
	public void listarAssuntos() {
		assuntos().stream().forEach(p -> System.out.println("Id assunto: " + p.getId() + 
				", Descendentes: " + p.getDescendentes() + ", Hierarquia:  " + p.getHierarquia() + ", Materia: " + p.getMateria().getId()));
	}
	
	public void testeHierarquia() {
		List<HierarquiaDto> dtos = HierarquiaUtil.construirIndiceHierarquico(assuntos());
		System.out.println(dtos);
	}
	
	public static List<Assunto> assuntos() {
		return AssuntoHelper.listarAssuntosDoArquivo();
	}
	
	public static void main(String[] args) {
		new AssuntoTeste().listarAssuntos();
	}
	
}
