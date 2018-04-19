package br.com.tecconcursos.arquivo;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class Arquivo {

	private static final String CAMINHO = "D:\\desenvolvimento\\arquivos\\";
	
	private String arquivo;
	
	private Arquivo(String arquivo) {
		this.arquivo = arquivo;
	}

	public static Arquivo of(String arquivo) {
		return new Arquivo(arquivo);
	}
	
	@SuppressWarnings("unchecked")
	public List<String> lsitarDoAqruivo() {
		try {
			List<String> list = IOUtils.readLines(new FileInputStream(new File(CAMINHO + arquivo)));
			return list;
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public String leitura() {
		try {
			String string = IOUtils.toString(new FileInputStream(new File(CAMINHO + arquivo)));
			return string;
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
		
	}

}
