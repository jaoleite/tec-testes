package br.com.tecconcursos.log.bbb;

import java.io.File;
import java.io.FileInputStream;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class LogBBB {

	private static final String FILE_LOG_BBB = "D:\\desenvolvimento\\arquivos\\log-bbb.log";
	
	public LogBBB() {
		
	}
	
	public static void main(String[] args) {
		listarTempos();
	}
	
	public static void listarTempos() {
		List<String> list = listarLogBBB();
		List<Long> longs = new ArrayList<Long>();
		list.stream().forEach(p -> longs.add(calcularDiferencaDeTempos(p)));
		longs.stream().filter(p -> p >= 2L).forEach(p -> System.out.println(p));
	}
	
	@SuppressWarnings("unchecked")
	public static List<String> listarLogBBB() {
		try {
			List<String> bbbs = IOUtils.readLines(new FileInputStream(new File(FILE_LOG_BBB)));
			return bbbs;
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static long calcularDiferencaDeTempos(String string) {
		Instant inicio = getTempoInicial(string);
		Instant fim = getTempoFinal(string);
		Duration duracao = Duration.between(inicio, fim);
		long d = duracao.toMillis() / 1000;
		return d;
	}
	
	public static Instant getTempoInicial(String string) {
		String tempoInicial = getTempos(string, false);
		return getInstant(tempoInicial);
	}
	
	public static Instant getTempoFinal(String string) {
		String tempoFinal = getTempos(string, true);
		return getInstant(tempoFinal);
	}
	
	private static Instant getInstant(String tempo) {
		tempo = "2016-01-21T" + tempo + ".000Z";
		return Instant.parse(tempo);
	}
	
	private static String getTempos(String string, boolean tempoFinal) {
		int posicao = 0;
		if(tempoFinal) {
			posicao = 1;
		}
		String dataMaisTempos = string.substring(string.indexOf("["), string.indexOf("]"));
		String[] strings = dataMaisTempos.split(" ");
		String[] tempos = strings[1].split("-");
		return tempos[posicao];
	}

}
