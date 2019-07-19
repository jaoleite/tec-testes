package br.com.tecconcursos.rps.barueri;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import br.com.tecconcursos.entity.enumeration.NotaFiscalStatusEnum;
import br.com.tecconcursos.to.nfe.barueri.RetornoRpsBarueri;
import br.com.tecconcursos.util.Util;

public class Teste {

	private File file;
	private List<String> linhas;
	private List<RetornoRpsBarueri> retornos;
	
	public Teste() {
	}
	
	public Teste file(String arquivo) {
		if(Util.vazio(arquivo)) {
			throw new RuntimeException("arquivo nao encontrado");
		}
		file = new File(arquivo);
		if(!file.exists()) {
			throw new RuntimeException("arquivo nao encontrado");
		}
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public Teste read() {
		try {
			this.linhas = IOUtils.readLines(new FileInputStream(this.file));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return this;
	}
	
	public Teste transform() {
		this.retornos = new ArrayList<>();
		for (String linha : this.linhas) {
			String[] strings = linha.split(";");
			RetornoRpsBarueri retorno = new RetornoRpsBarueri();
			retorno.setCodigoAutenticidade(strings[0]);
			retorno.setNumeroNota(Long.parseLong(strings[1]));
			retorno.setRps(Long.parseLong(strings[2]));
			this.retornos.add(retorno);
		}
		return this;
	}
	
	/*
	 * update test as t set
    column_a = c.column_a
from (values
    ('123', 1),
    ('345', 2)  
) as c(column_b, column_a) 
where c.column_b = t.column_b;
	 */
	public void sqlUpdate() {
		StringBuilder update = new StringBuilder();
		update.append("update notafiscal as nf set");
		update.append("\n\t").append("codigoverificacao=notaretorno.codigoverificacao,");
		update.append("\n\t").append("numero=notaretorno.numero,");
		update.append("\n\t").append("status=notaretorno.status");
		update.append("\n").append("from ");
		update.append("\n\t").append("(values");
		update.append("\n").append("%s");
		update.append("\t").append(") as notaretorno(codigoverificacao, numero, numerorps, status)");
		update.append("\n").append("where");
		update.append("\n\t").append("nf.numerorps = notaretorno.numerorps");
		update.append("\n\t").append("and nf.tipoprefeitura='BARUERI'");
		update.append("\n\t").append("and nf.assinatura_id is not null").append(";");
		
		StringBuilder builder = new StringBuilder();
		String STATUS = NotaFiscalStatusEnum.PROCESSADA.name();
		String template = "('%s', %d, %d, '%s')";
		int i = 0;
		int size = this.retornos.size() - 1;
		for (RetornoRpsBarueri retorno : this.retornos) {
			String result = String.format(template, retorno.getCodigoAutenticidade(), retorno.getNumeroNota(), retorno.getRps(), STATUS);
			if(i < size) {
				builder.append("\t\t").append(result).append(",").append("\n");
			} else {
				builder.append("\t\t").append(result).append("\n");
			}
			i++;
		}
		
		String result = String.format(update.toString(), builder.toString());
		System.out.println(result);
	}
	
	public static void main(String[] args) {
		/*String arquivo = "D:\\teste.txt";
		new Teste().file(arquivo).read().transform().sqlUpdate();*/
		Lista<String> list = new Lista<>();
		
		list.add("joao");
		list.add("paulo");
		list.teste();
		System.out.println();
	}

}
