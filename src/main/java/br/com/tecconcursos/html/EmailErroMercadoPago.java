package br.com.tecconcursos.html;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.google.common.base.Joiner;

import br.com.tecconcursos.arquivo.Arquivo;

public class EmailErroMercadoPago {

	private static final String ARQUIVO_EMAIL = "email\\erro-mercado-pago-0%d.html";
	private static final int NUMERO_ARQUIVOS = 9;
	private static final int INICIO = 8;
	
	private Arquivo arquivo;
	private List<String> conteudos;
	private Set<String> emails;
	private String templateSelect;
	
	public EmailErroMercadoPago() {
		conteudos = new ArrayList<>();
		emails = new HashSet<>();
		for (int i = INICIO; i <= NUMERO_ARQUIVOS; i++) {
			String path = String.format(ARQUIVO_EMAIL, i);
			System.out.println(path);
			arquivo = Arquivo.of(path);
			String conteudo = arquivo.leitura();
			conteudos.add(conteudo);
		}
	}
	
	public EmailErroMercadoPago extrairEmails() {
		conteudos.forEach(conteudo -> {
			Document document = Jsoup.parse(conteudo);
			Elements elements = document.select("a");
			elements.forEach(element -> {
				emails.add("'" + element.childNode(0).toString() + "'");
			});
		});
		return this;
	}
	
	public EmailErroMercadoPago select() {
		StringBuilder builder = new StringBuilder();
		builder.append("select ");
		builder.append("\n\tu.email, ");
		builder.append("\n\tmax(p.id) as id ");
		builder.append("\nfrom ");
		builder.append("\n\tpedido p ");
		builder.append("\n\tinner join usuariosite u on u.id = p.usuariosite_id ");
		builder.append("\nwhere ");
		builder.append("\n\tu.email in(%s) ");
		builder.append("\n\tand p.status != 'APROVADA' ");
		builder.append("\ngroup by u.email ");
		builder.append("\norder by u.email; ");
		
		templateSelect = builder.toString();
		return this;
	}
	
	public void imprimir() {
		String ids = Joiner.on(",\n").join(emails);
		String resultado = String.format(templateSelect, ids);
		System.out.println(resultado);
	}
	
	public static void main(String[] args) {
		new EmailErroMercadoPago().extrairEmails().select().imprimir();
		//new EmailErroMercadoPago();
	}

}
