package br.com.tecconcursos.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.codehaus.jettison.json.JSONArray;

public class MercadoPago {

	private static final String CAMINHO_JSON = "C:\\Desenvolvimento\\tec\\arquivos\\retorno-json-mercado-pago.txt";

	@SuppressWarnings("unchecked")
	public static void testePesquisa() {
		try {
			List<String> list = IOUtils.readLines(new FileInputStream(new File(CAMINHO_JSON)));
			String linha = list.get(0);
			//JSONObject searchResult = new JSONObject(linha);
			JSONArray jsonArray = new JSONArray(linha);
			for (int i = 0; i < jsonArray.length(); i++) {
				System.out.println(jsonArray.get(0));
			}
			//System.out.println(jsonArray.toString());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		testePesquisa();
	}
	
}
