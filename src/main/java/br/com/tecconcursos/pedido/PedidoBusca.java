package br.com.tecconcursos.pedido;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class PedidoBusca {

	@SuppressWarnings("unchecked")
	public List<Long> listarCodigosDePedidos(String path) {
		try {
			List<Long> ids = new ArrayList<Long>();
			List<String> list = IOUtils.readLines(new FileInputStream(new File(path)));
			list.forEach(p -> ids.add(new Long(p)));
			return ids;
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void sqlPedidoInIds() {
		StringBuilder sql = new StringBuilder("select p.id, sum(i.valorpago) as valorPago\nfrom pedido p\n");
		sql.append("inner join itempedido i on i.pedido_id = p.id\n");
		sql.append("where p.id in(\n");
		List<Long> ids = listarCodigosDePedidos("D:\\desenvolvimento\\arquivos\\pedido-id.txt");
		ids.forEach(p -> sql.append(p).append(", \n"));
		String resultado = sql.substring(0, sql.length() - 3) + ")\n";
		resultado += "group by p.id";
		
		System.out.println(resultado);
	}
	
	public static void main(String[] args) {
		new PedidoBusca().sqlPedidoInIds();
	}
}
