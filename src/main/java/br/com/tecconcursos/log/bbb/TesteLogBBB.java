package br.com.tecconcursos.log.bbb;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;

import br.com.tecconcursos.util.Util;

public class TesteLogBBB {

	private List<String> linhas;
	
	@SuppressWarnings("unchecked")
	public TesteLogBBB() {
		String caminho = "C:\\Users\\jaoin\\Desktop\\";
		String arquivo = "requests-diguinho.txt";
		try {
			linhas = IOUtils.readLines(new FileInputStream(new File(caminho + arquivo)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void teste() {
		for (String linha : linhas) {
			if(!Util.vazio(linha)) {
				int inicio = linha.indexOf("[") + 7;
				int fim = linha.indexOf("]");
				String string = linha.substring(inicio, fim);
				String[] array = string.split("-");
				
				DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
				try {
					Date tempoInicial = dateFormat.parse(array[0]);
					Date tempoFinal = dateFormat.parse(array[1]);
					
					long i = (tempoFinal.getTime() - tempoInicial.getTime()) / 1000;
					if(i > 1) {
						System.out.println(linha);
						System.out.println(i);
					}
					
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("FIM");
	}
	
	public static void main(String[] args) {
		new TesteLogBBB().teste();
	}

}
