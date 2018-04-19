package br.com.tecconcursos.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;


public class Teste2 {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		String file = "C:\\Users\\Jão\\Downloads\\ativos que nao cadastraram.txt";
		List<String> list = IOUtils.readLines(new FileInputStream(new File(file)));
		for (String email : list) {
			System.out.println("'" + email + "',");
		}
	}
	
}
