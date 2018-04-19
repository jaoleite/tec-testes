package br.com.tecconcursos.assunto;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import br.com.tecconcursos.entity.Assunto;
import br.com.tecconcursos.entity.Materia;

public class AssuntoHelper {
	
	private static final String ARQUIVO_ASSUNTOS = "D:\\desenvolvimento\\arquivos\\assuntos.txt";

	public static Assunto transformarLinhaEmAssunto(String linha) {
		String[] strings = linha.split("\t");
		Assunto assunto = new Assunto();
		Materia materia = new Materia();
		materia.setId(new Long(strings[4].trim()));
		materia.setNome(strings[5].trim());
		assunto.setId(new Long(strings[0].trim()));
		assunto.setNome(strings[1].trim());
		assunto.setHierarquia(strings[2].trim());
		assunto.setDescendentes(strings[3].trim());
		assunto.setMateria(materia);
		
		return assunto;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Assunto> listarAssuntosDoArquivo() {
		try {
			List<String> list = IOUtils.readLines(new FileInputStream(new File(ARQUIVO_ASSUNTOS)), "UTF-8");
			List<Assunto> assuntos = new ArrayList<Assunto>();
			
			list.stream().forEach(p -> assuntos.add(transformarLinhaEmAssunto(p)));
			
			return assuntos;
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	//0 = id
	//1 = nome
	//2 = hierarquia
	//3 = descendentes
	//4 = materiaid
	//5 = materianome
}
