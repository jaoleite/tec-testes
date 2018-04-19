package br.com.tecconcursos.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class Cagada {
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		String file = "D:\\desenvolvimento\\cagada.txt";
		
		List<String> list = IOUtils.readLines(new FileInputStream(new File(file)));
		for (String string : list) {
			String[] strings = string.split(",");
			System.out.println("update concurso set areaconcurso_id = " + strings[1] + " where id = " + strings[0] + " and areaconcurso_id isnull;");
		}
		
	}

}
