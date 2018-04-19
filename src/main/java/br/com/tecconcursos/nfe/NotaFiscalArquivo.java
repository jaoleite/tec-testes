package br.com.tecconcursos.nfe;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

public class NotaFiscalArquivo {

	private static final String ARQUIVO_CSV = "D:\\desenvolvimento\\arquivos\\nfe\\NFSe_E_42951585_20160101_20160131.csv";
	
	private File csvFile = null;
	private List<Map<String, String>> maps;
	
	public NotaFiscalArquivo() {
		csvFile = new File(ARQUIVO_CSV);
	}
	
	@SuppressWarnings("unchecked")
	public void leituraCsv() {
		try {
			List<String> list = IOUtils.readLines(new FileInputStream(this.csvFile));
			String[] cabecalho = list.get(0).split(";");
			List<Map<String, String>> listNova = new ArrayList<>();
			for (int i = 1; i < list.size() - 1; i++) {
				Map<String, String> map = new HashMap<String, String>();
				String string = list.get(i);
				String[] array = string.split(";");
				for (int j = 0; j < array.length; j++) {
					map.put(cabecalho[j], array[j]);
				}
				listNova.add(map);
			}
			this.maps = listNova;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Map<String, String> pesquisaPorRps(String rps) {
		for (Map<String, String> map : maps) {
			if(map.get("Número do RPS").equals(rps)) {
				System.out.println(map.get("Nº NFS-e"));
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		NotaFiscalArquivo nota = new NotaFiscalArquivo();
		nota.leituraCsv();
		nota.pesquisaPorRps("181609");
		System.out.println("fim");
	}

}
