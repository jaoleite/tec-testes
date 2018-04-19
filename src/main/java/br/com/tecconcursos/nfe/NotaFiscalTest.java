package br.com.tecconcursos.nfe;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import br.com.tecconcursos.util.Util;

public class NotaFiscalTest {

	public NotaFiscalTest() {
		
	}

	@SuppressWarnings("unchecked")
	public List<Long> listRpsFromFile(String file) {
		try {
			List<Long> longs = new ArrayList<Long>();
			List<String> list = IOUtils.readLines(new FileInputStream(new File(file)));
			for (String line : list) {
				if(!Util.vazio(line) && StringUtils.isNumeric(line)) {
					longs.add(new Long(line));
				}
			}
			return longs;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void sqlPedidoPorRps() {
		List<Long> rps = listRpsFromFile("D:\\desenvolvimento\\arquivos\\rps-dezembro.txt");
		StringBuilder builder = new StringBuilder("select * from pedido where numeroNotaFiscal in (");
		int i = 0;
		System.out.println("--" + rps.size());
		for (Long r : rps) {
			if(i < rps.size() - 1) {
				builder.append("'").append(r).append("',").append("\n");
			} else {
				builder.append("'").append(r).append("'");
			}
			i++;
		}
		builder.append(")");
		builder.append("--and status != 'APROVADA'");
		System.out.println(builder.toString());
	}
	
	public static void main(String[] args) {
		new NotaFiscalTest().sqlPedidoPorRps();
	}
}
