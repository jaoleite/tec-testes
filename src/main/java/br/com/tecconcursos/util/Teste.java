package br.com.tecconcursos.util;

import java.util.Calendar;

import org.apache.commons.lang.StringUtils;

public class Teste {

	public static String getJson() {
		StringBuilder builder = new StringBuilder();
		builder.append("{'employees':[");
		builder.append("              {'firstName':'John', 'lastName':'Doe'},");
		builder.append("              {'firstName':'Anna', 'lastName':'Smith'},");
		builder.append("              {'firstName':'Peter', 'lastName':'Jones'}");
		builder.append("          ]}");
		
		return builder.toString();
	}
	
	public static String getXml() {
		StringBuilder builder = new StringBuilder();
		builder.append("<employees>");
		builder.append("    <employee>");
		builder.append("        <firstName>John</firstName> <lastName>Doe</lastName>");
		builder.append("    </employee>");
		builder.append("    <employee>");
		builder.append("        <firstName>Anna</firstName> <lastName>Smith</lastName>");
		builder.append("    </employee>");
		builder.append("    <employee>");
		builder.append("        <firstName>Peter</firstName> <lastName>Jones</lastName>");
		builder.append("    </employee>");
		builder.append("</employees>");
		
		return builder.toString();
	}
	
	public static String cleanString(String string) {
		String st = StringUtils.trimToEmpty(string);
		st = StringUtils.deleteWhitespace(st);
		return st;
	}
	
	public static void main2(String[] args) {
		String json = cleanString(getJson());
		String xml = cleanString(getXml());
		System.out.println(xml);
		System.out.println(json);
		System.out.println(xml.length());
		System.out.println(json.length());
		
		System.out.println(xml.length() - json.length());
	}
	
	public static void main3(String[] args) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, Calendar.JANUARY);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		System.out.println(calendar.getTime());
	}
	
	public static void main(String[] args) {
		int totalPaginas = 5;
		int paginaAtual = 1;
		int quantidadeExibicao = 10;
		paginacao2(totalPaginas, paginaAtual, quantidadeExibicao);
	}
	
	public static void paginacao2(int totalPaginas, int paginaAtual, int quantidadeExibicao) {
		int inicio = (paginaAtual - quantidadeExibicao) + 1;
		int fim = paginaAtual;
		if(inicio <= 0) {
			inicio = 1;
			fim = quantidadeExibicao;
		}
		if(totalPaginas <= quantidadeExibicao) {
			fim = totalPaginas;
		}
		for (int i=inicio; i<=fim; i++) {
			if(i == paginaAtual){
				if(i == fim) {
					System.out.print("<b>" + i + "</b>");
				} else {
					System.out.print("<b>" + i + "</b>, ");
				}
			} else {
				if(i == fim) {
					System.out.print(i);
				} else {
					System.out.print(i + ", ");
				}
			}
		}
	}
	
	public static void paginacao(int totalPaginas, int paginaAtual, int quantidadeExibicao) {
		StringBuilder builder = new StringBuilder();
		
		if(quantidadeExibicao > totalPaginas) {
			for (int i=1; i<=totalPaginas; i++) {
				builder.append(i).append(", ");
				String st = builder.toString();
				st = st.substring(0, st.length() - 2);
				builder = new StringBuilder(st);
			}
		} else {
			int metade = quantidadeExibicao / 2;
			int inicio = paginaAtual - metade;
			String inicioReticencias = "...";
			if(inicio <= 0) {
				inicio = 1;
				inicioReticencias = "";
			}
			if(paginaAtual > (totalPaginas - metade)) {
				int a = (totalPaginas - paginaAtual);
				System.out.println(paginaAtual - (metade + a));
				
				
			}
			
			builder.append(inicioReticencias);
			for (int i=inicio; i<=paginaAtual; i++) {
				builder.append(i).append(", ");
			}
			
			int fim = (paginaAtual + metade);
			String fimReticencias = "...";
			if(paginaAtual < quantidadeExibicao) {
				fim = paginaAtual + (quantidadeExibicao - paginaAtual);
			}
			if(fim > totalPaginas) {
				fim = totalPaginas;
				fimReticencias = "";
			}
			for (int i=paginaAtual + 1; i<=fim; i++) {
				builder.append(i).append(", ");
			}
			
			String st = builder.toString();
			st = st.substring(0, st.length() - 2);
			builder = new StringBuilder();
			builder.append(st).append(fimReticencias);
		}
		
		System.out.println(builder.toString());
	}
}
