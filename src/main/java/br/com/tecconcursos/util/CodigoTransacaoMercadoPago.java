package br.com.tecconcursos.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class CodigoTransacaoMercadoPago {

	@SuppressWarnings("unchecked")
	public CodigoTransacaoMercadoPago() throws Exception {
		String arquivo = "D:\\desenvolvimento\\arquivos\\codigo-transacao.txt";
		File file = new File(arquivo);
		FileInputStream stream = new FileInputStream(file);
		List<String> list = IOUtils.readLines(stream);
		StringBuilder sql = new StringBuilder("select * from pedido where codigotransacao in(");
		for (String codigo : list) {
			sql.append("'").append(codigo).append("'").append(",\n");
		}
		String st = sql.substring(0, sql.length() - 2);
		st += ")";
		
		System.out.println(st);
	}
	
	public static void main(String[] args) throws Exception {
		new CodigoTransacaoMercadoPago();
	}

}
