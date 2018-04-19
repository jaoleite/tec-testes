package br.com.tecconcursos.arquivo.excel;

import java.util.ArrayList;
import java.util.List;

import br.com.tecconcursos.arquivo.Arquivo;
import br.com.tecconcursos.entity.Redirecionamento;
import br.com.tecconcursos.to.redirecionamento.Redirect;
import br.com.tecconcursos.to.redirecionamento.enumeration.RedirectType;

public class CrawlErrors {

	private Arquivo arquivo = null;;
	private List<FromToDto> dtos = null;
	private List<Redirect> redirects = null;
	private String json = null;
	
	public CrawlErrors() {
		arquivo = Arquivo.of("excel\\seo-redirecionamento.csv");
	}
	
	public CrawlErrors listar() {
		this.dtos = new ArrayList<FromToDto>();
		List<String> list = arquivo.lsitarDoAqruivo();
		list.stream().forEach(row -> {
			FromToDto dto = new FromToDto(row);
			this.dtos.add(dto);
		});
		return this;
	}
	
	public CrawlErrors transform() {
		this.redirects = new ArrayList<Redirect>();
		this.dtos.forEach(dto -> {
			Redirect redirect = new Redirect();
			redirect.setFrom(dto.getFrom());
			redirect.setTo(dto.getTo());
			redirect.setType(RedirectType.PERMANENTLY_REDIRECT);
			this.redirects.add(redirect);
		});
		System.out.println(this.redirects.size());
		return this;
	}
	
	public CrawlErrors toJson() {
		Redirecionamento redirecionamento = new Redirecionamento();
		String json = redirecionamento.toJson(this.redirects);
		this.json = json;
		return this;
	}
	
	public void print() {
		System.out.println(this.json);
	}
	public static void main(String[] args) {
		new CrawlErrors().listar().transform().toJson().print();
	}

}
