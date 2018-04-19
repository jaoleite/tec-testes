package br.com.tecconcursos.arquivo.request;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.tecconcursos.arquivo.Arquivo;
import br.com.tecconcursos.service.CriptografiaService;

public class RequestLog {

	private static final String ARQUIVO = "requests\\requests.log";
	
	private List<String> listaArquivo = null;
	private Arquivo arquivo = null;
	
	public RequestLog() {
		arquivo = Arquivo.of(ARQUIVO);
	}
	
	public RequestLog listar() {
		listaArquivo = arquivo.lsitarDoAqruivo();
		return this;
	}
	
	public RequestLog filtrarPorIP(String ip) {
		Set<String> set = listaArquivo.stream().filter(p -> p.contains(ip)).collect(Collectors.toSet());
		listaArquivo = new ArrayList<>(set);
		return this;
	}
	
	public RequestLog listarEmails() {
		Set<String> set = new HashSet<String>();
		listaArquivo.stream().forEach(p -> {
			String email = getEmail(p);
			if(email != null) {
				boolean add = set.add(email);
				if(!add);
			}
		});
		
		listaArquivo = new ArrayList<>(set);
		
		return this;
	}
	
	public RequestLog listarDominios() {
		Set<String> set = new HashSet<String>();
		listaArquivo.stream().forEach(p -> {
			String dominio = getDominio(p);
			if(dominio != null) {
				boolean add = set.add(dominio);
				if(!add);
			}
		});
		
		listaArquivo = new ArrayList<>(set);
		
		return this;
	}
	
	public void imprimir() {
		listaArquivo.stream().forEach(p -> {
			System.out.println(p);
		});
	}
	
	public String getEmail(String linha) {
		String st = linha.substring(linha.indexOf("{") + 1, linha.lastIndexOf("}"));
		String[] array = st.split(" ");
		if(array.length == 2) {
			return array[1];
		}
		return null;
	}
	
	public String getDominio(String linha) {
		String email = getEmail(linha);
		if(email != null) {
			return email.substring(email.indexOf("@") + 1);
		}
		return null;
	}
	
	public static void main(String[] args) {
		//new RequestLog().listar().filtrarPorIP("163.172.136.101").listarEmails().imprimir();
		testeHash();
	}
	
	public static void testeHash() {
		CriptografiaService service = new CriptografiaService();
		String senha = service.gerarSenha();
		String hash = service.hashBCrypt(senha);
		boolean ok = service.checkPasswordWithBCrypt(senha, hash);
		System.out.println(senha);
		System.out.println(ok);
	}
	
}
