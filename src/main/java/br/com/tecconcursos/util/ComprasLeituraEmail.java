package br.com.tecconcursos.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;

public class ComprasLeituraEmail {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		String arquivo = "C:\\Users\\Jão\\Desktop\\teste.txt";
		String contains = "Email do usuário";
		List<String> list = IOUtils.readLines(new FileInputStream(new File(arquivo)));
		//Set<CompraErroEmailTo> emails = new HashSet<CompraErroEmailTo>();
		List<CompraErroEmailTo> emails = new ArrayList<CompraErroEmailTo>();
		for (String linha : list) {
			if(linha.contains(contains)) {
				CompraErroEmailTo compraErroEmailTo = new CompraErroEmailTo(linha);
				emails.add(compraErroEmailTo);
			}
		}
		
		Collections.sort(emails);
		Set<CompraErroEmailTo> set = new HashSet<CompraErroEmailTo>();
		for (CompraErroEmailTo compraErroEmailTo : emails) {
			set.add(compraErroEmailTo);
		}
		imprimirIdsParaSelect(set);
		//imprimirEmails(set);
	}
	
	public static void imprimirEmails(Collection<CompraErroEmailTo> emails) {
		for (CompraErroEmailTo compraErroEmailTo : emails) {
			System.out.println(compraErroEmailTo.getEmail());
		}
	}
	
	public static void imprimirIdsParaSelect(Collection<CompraErroEmailTo> emails) {
		for (CompraErroEmailTo compraErroEmailTo : emails) {
			System.out.println(compraErroEmailTo.getId() + ", ");
		}
	}
}
