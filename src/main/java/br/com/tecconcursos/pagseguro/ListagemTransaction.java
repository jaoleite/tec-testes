package br.com.tecconcursos.pagseguro;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;

import br.com.tecconcursos.util.Util;

public class ListagemTransaction {
	
	@SuppressWarnings("unchecked")
	public static List<String> listarCodigosDeTransacoes(File file) {
		List<String> codigos = new ArrayList<>();
		try {
			List<String> list = IOUtils.readLines(new FileInputStream(file));
			Set<String> tmp = new HashSet<>();
			int i = 0;
			for (String linha : list) {
				if(i > 0) {
					String[] strings = linha.split("\t");
					if(!Util.vazio(strings)) {
						String codigo = strings[0];
						if(!Util.vazio(codigo)) {
							tmp.add(codigo.trim());
						}
					}
				}
				i++;
			}
			
			//codigos.addAll(tmp);
			codigos.add(tmp.stream().findFirst().get());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return codigos;
	}

}
