package br.com.tecconcursos.arquivo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;

import br.com.tecconcursos.service.CriptografiaService;
import br.com.tecconcursos.util.Util;

public class TesteLeituraEmail {

	private static final String CAMINHO = "D:\\desenvolvimento\\arquivos\\mercado-pago\\";
	private static final String ARQUIVO_EMAILS = "problema-compra-mp.txt";
	private static final String ARQUIVO_IDS = "usuarios-ids.txt";
	
	
	private static final String PATTERN = "\\(id: \\d+\\)";
	private String arquivoDeEmails = CAMINHO + ARQUIVO_EMAILS;
	private String arquivoDeIds = CAMINHO + ARQUIVO_IDS;
	
	public TesteLeituraEmail() {
		if(imprimirUsuarios) {
			imprimirIdsDosUsuarios();
		} else {
			imprimirUsuariosSemLiberacao();
		}
		
	}
	
	private void imprimirIdsDosUsuarios() {
		List<Long> list = listarIdsDosUsuarios();
		System.out.println(list);
		System.out.println(list.size());
	}
	
	private void imprimirUsuariosSemLiberacao() {
		List<Long> list = listarIdsSemLiberacao();
		System.out.println(list);
	}
	
	public List<Long> listarIdsDosUsuarios() {
		List<String> list = listarLinhasComIdDoUsuario();
		Set<Long> ids = new HashSet<Long>();
		list.stream().forEach(p -> {
			Long id = buscarIdDoUsuario(p);
			ids.add(id);
		});
		return new ArrayList<Long>(ids);
	}
	
	@SuppressWarnings("unchecked")
	public List<String> leituraArquivo() {
		try {
			List<String> list = IOUtils.readLines(new FileInputStream(new File(arquivoDeEmails)));
			return list;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<String> listarLinhasComIdDoUsuario() {
		List<String> linhas = null;
		List<String> list = leituraArquivo();
		linhas = list.stream().filter(p -> linhaContemIdDoUsuario(p)).collect(Collectors.toList());
		return linhas;
	}
	
	public boolean linhaContemIdDoUsuario(String linha) {
		return verificaRegex(linha);
	}
	
	public boolean verificaRegex(String string) {
		Pattern r = Pattern.compile(PATTERN);
		Matcher m = r.matcher(string);
		return m.find();
	}
	
	public Long buscarIdDoUsuario(String string) {
		String result = buscarStringIdDoUsuario(string);
		if(isNumber(result)) {
			return new Long(result);
		}
		return null;
	}
	
	public String buscarStringIdDoUsuario(String string) {
		String st = getValue(string);
		return st.substring(4, st.length() - 1).trim();
	}
	
	public String getValue(String string) {
		Pattern r = Pattern.compile(PATTERN);
		Matcher m = r.matcher(string);
		if(m.find()) {
			return m.group(0);
		}
		return null;
	}
	
	public boolean isNumber(String string) {
		if(!Util.vazio(string)) {
			return string.matches("[-+]?\\d*\\.?\\d+");
		}
		return false;
	}
	
	public List<Long> listarIdsSemLiberacao() {
		List<Long> idsUsuarios = listarIdsDosUsuarios();
		List<Long> ids = listarArquivoDeIDs();
		List<Long> nova = new ArrayList<Long>();
		
		for (Long id : idsUsuarios) {
			if(!existeNaLista(id, ids)) {
				nova.add(id);
			}
		}
		
		return nova;
	}
	
	public boolean existeNaLista(Long id, List<Long> ids) {
		return ids.stream().filter(p -> p.equals(id)).findFirst().isPresent();
	}
			
	@SuppressWarnings("unchecked")
	public List<Long> listarArquivoDeIDs() {
		try {
			List<String> list = IOUtils.readLines(new FileInputStream(new File(arquivoDeIds)));
			return list.stream().map(Long::valueOf).collect(Collectors.toList());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////
	private boolean imprimirUsuarios = false;
	
	public static void main(String[] args) {
		new TesteLeituraEmail();
		//hash();
	}
	
	public static void hash() {
		CriptografiaService service = new CriptografiaService();
		System.out.println(service.hashBCrypt("inicio123"));
	}

}
