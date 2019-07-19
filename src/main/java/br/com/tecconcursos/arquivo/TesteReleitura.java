package br.com.tecconcursos.arquivo;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class TesteReleitura {

	private static final String FILE = "C:\\Users\\jaoin\\Desktop\\Jiras\\TEC-1919 - Gestao de Aprovados\\gestao de aprovados 03.txt";
	
	public TesteReleitura() {
		teste();
	}
	
	@SuppressWarnings("unchecked")
	public void teste() {
		try {
			InputStream stream = new FileInputStream(new File(FILE));
			List<String> list = IOUtils.readLines(stream);
			for (String string : list) {
				System.out.println(string);
			}
			
			System.out.println();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new TesteReleitura();
	}

}
