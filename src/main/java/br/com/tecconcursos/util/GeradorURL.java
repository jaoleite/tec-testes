package br.com.tecconcursos.util;

public class GeradorURL {

	private String titulo;
	private String urlGerada;
	
	public GeradorURL comTitulo(String titulo) {
		this.titulo = titulo;
		return this;
	}
	
	public GeradorURL gerar() {
		falharSeTituloVazio();
		this.urlGerada = Util.gerarUrl(this.titulo);
		return this;
	}
	
	public String getUrlGerada() {
		return urlGerada;
	}
	
	private void falharSeTituloVazio() {
		if(Util.vazio(titulo)) {
			throw new RuntimeException("titulo is null");
		}
	}
	
	public static void main(String[] args) {
		String url = new GeradorURL().comTitulo("Profissional de Suporte Técnico (CFM)").gerar().getUrlGerada();
		System.out.println(url);
	}

}
