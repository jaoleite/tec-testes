package br.com.tecconcursos.rps.barueri;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.IOUtils;

import br.com.tecconcursos.to.nfe.barueri.RetornoRpsBarueri;
import br.com.tecconcursos.util.Util;

public class Zipper {

	private List<InputStream> list = new ArrayList<>();
	private List<String> linhas = new ArrayList<>();
	private List<RetornoRpsBarueri> retornos = new ArrayList<>();
	
	public Zipper read(String zip) {
		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(zip);
			Enumeration<? extends ZipEntry> entries = zipFile.entries();
			while (entries.hasMoreElements()) {
				ZipEntry zipEntry = entries.nextElement();
				InputStream stream = zipFile.getInputStream(zipEntry);
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				IOUtils.copy(stream, outputStream);
				InputStream tmp = new ByteArrayInputStream(outputStream.toByteArray());
				list.add(tmp);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (zipFile != null) {
				try {
					zipFile.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public Zipper listar() throws IOException {
		for (InputStream inputStream : this.list) {
			List<String> strings = IOUtils.readLines(inputStream);
			if(!Util.vazio(strings)) {
				this.linhas.addAll(strings);
			}
		}
		return this;
	}
	
	public Zipper listarRetornoRps() {
		if(!Util.vazio(this.linhas)) {
			for (String linha : this.linhas) {
				if(RetornoRpsBarueri.isLinhaValida(linha)) {
					RetornoRpsBarueri retorno = new RetornoRpsBarueri(linha);
					this.retornos.add(retorno);
				}
			}
		}
		return this;
	}
	
	public void sqlSelect() {
		String template = "select * from notafiscal where tipoprefeitura = 'BARUERI' and numerorps in%s;";
		if(!Util.vazio(this.retornos)) {
			List<Long> longs = this.retornos.stream().map(p -> p.getRps()).collect(Collectors.toList());
			String replacement = longs.toString().replace("[", "(").toString().replace("]", ")").toString().replaceAll(", ", ",\n");
			System.out.println(String.format(template, replacement));
		}
	}
	
	public void sqlUpdate(String file) {
		String template = "update notafiscal set numero=%d, status='PROCESSADA', codigoverificacao='%s' where numerorps=%d and tipoprefeitura='BARUERI' and assinatura_id is not null and numero is null;";
		StringBuffer data = new StringBuffer();
		if(!Util.vazio(this.retornos)) {
			for (RetornoRpsBarueri retorno : this.retornos) {
				String update = String.format(template, retorno.getNumeroNota(), retorno.getCodigoAutenticidade(), retorno.getRps());
				data.append(update).append("\n");
			}
		}
		
		if(!Util.vazio(file)) {
			try {
				IOUtils.write(data, new FileOutputStream(new File(file)));
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
	}
	
	public static void main(String[] args) throws IOException {
		//new Zipper().read("D:\\erronorpsarquivosderetorno.zip").listar().listarRetornoRps().sqlUpdate("D:\\update-notafiscal.sql");
		new Zipper().read("D:\\erronorpsarquivosderetorno.zip").listar().listarRetornoRps().sqlSelect();
		//new Zipper().read("D:\\fwdrpsarquivosderetorno.zip").listar().listarRetornoRps().sqlUpdate("D:\\update-notafiscal.sql");
		//fwdrpsarquivosderetorno.zip
	}
	
	

}
