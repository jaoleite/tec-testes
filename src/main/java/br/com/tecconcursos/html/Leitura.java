package br.com.tecconcursos.html;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import br.com.tecconcursos.context.ApplicationContext;
import br.com.tecconcursos.dao.fulltext.enumeration.FullTextTypeEnum;
import br.com.tecconcursos.service.CadastroQuestaoObjetivaService;
import br.com.tecconcursos.util.FullTextProperties;
import br.com.tecconcursos.util.Util;

public class Leitura {

	
	private static final String URL_CONTEUDO_QUESTOES = "/conteudo/questoes/";
	//private static final int TAMANHO_ENUNCIADO = 50;
	private int QUANTIDADE_DE_PALAVRAS = -1;
	
	private Configuracao configuracao;
	private int i = 0;
	private CadastroQuestaoObjetivaService service = null;
	
	public Leitura() {
		configuracao = Configuracao.getProducao();
		service = ApplicationContext.find(CadastroQuestaoObjetivaService.class);
	}
	
	public void build() throws FileNotFoundException, IOException {
		Charset charset = configuracao.charset;
		Document document = Jsoup.parse(new File(configuracao.file), charset.name());
		Elements elements = document.select(".data tr");
		elements.forEach(element -> {
			if(this.i >= configuracao.iteration) {
				String enunciado = getEnunciado(element);
				/*if(enunciado.length() >= TAMANHO_ENUNCIADO) {
					enunciado = enunciado.substring(0, TAMANHO_ENUNCIADO);
				}*/
				enunciado = getPalavras(enunciado);
				Long idQuestaoDesativada = getIdQuestaoDesativada(element);
				List<Long> questoes = buscarPorEnunciado(enunciado);
				Dto dto = new Dto(enunciado, idQuestaoDesativada, questoes, element, buildRadioName());
				setQuestao(dto);
			}
			this.i ++;
		});
		IOUtils.write(document.html(), new FileOutputStream(new File(configuracao.fileSave)));
	}
	
	private String getPalavras(String enunciado) {
		String[] palavras = enunciado.split(" ");
		StringBuilder builder = new StringBuilder();
		for (String palavra : palavras) {
			if(palavra.length() >= 3) {
				builder.append(palavra).append(" ");
			}
		}
		
		palavras = builder.toString().split(" ");
		builder = new StringBuilder();
		int quantidadeDePalavras = getQuantidadeDePalavras(palavras);
		if(palavras.length >= quantidadeDePalavras) {
			for (int i=0; i<quantidadeDePalavras; i++) {
				builder.append(palavras[i]).append(" ");
			}
			return builder.toString();
		}
		return enunciado;
	}
	
	private int getQuantidadeDePalavras(String[] palavras) {
		if(QUANTIDADE_DE_PALAVRAS == -1) {
			return palavras.length;
		}
		return QUANTIDADE_DE_PALAVRAS;
	}
	
	private String buildRadioName() {
		return Util.geradorUUID();
	}
	
	public Long getIdQuestaoDesativada(Element element) {
		String value = element.childNode(1).childNode(0).toString();
		Long id = new Long(value);
		return id;
	}
	
	public String getEnunciado(Element element) {
		String value = element.childNode(3).childNode(0).toString();
		return Jsoup.parse(Jsoup.parse(value).text()).text();
	}
	
	public void setQuestao(Dto dto) {
		Element el = (Element) dto.element.childNode(5);
		el.childNode(0).remove();
		if(!Util.vazio(dto.questoes)) {
			addLink(dto, el);
			addInputText(dto, el);
		} else {
			addInputText(dto, el);
		}
	}
	
	private void addInputText(Dto dto, Element element) {
		Element div = getDivToInputText(dto);
		element.appendChild(div);
	}
	
	private Element getDivToInputText(Dto dto) {
		Tag tag = Tag.valueOf("input");
		Attributes attributes = new Attributes();
		attributes.put("type", "text");
		attributes.put("class", "questao-redirecionamento-text");
		Element text = new Element(tag, "", attributes);
		Element div = getDiv();
		div.appendChild(getInputHiddenEnunciado(dto));
		div.appendChild(getInputHidden(dto));
		div.appendChild(text);
		//div.appendChild(getLinkIrParaEnunciado());
		return div;
	}
	
	protected Element getLinkIrParaEnunciado() {
		String linkQuestao = "#";
		Tag tag = Tag.valueOf("a");
		Attributes attributes = new Attributes();
		attributes.put("href", linkQuestao);
		attributes.put("target", "_blank");
		attributes.put("class", "ir-para-enunciado");
		Element link = new Element(tag, "", attributes);
		link.appendText("Ir para enunciado");
		return link;
	}
	
	private Element getInputHiddenEnunciado(Dto dto) {
		Tag tag = Tag.valueOf("input");
		Attributes attributes = new Attributes();
		attributes.put("type", "hidden");
		attributes.put("value", dto.enunciado);
		attributes.put("class", "questao-enunciado");
		Element input = new Element(tag, "", attributes);
		return input;
	}
	
	private void addLink(Dto dto, Element element) {
		dto.questoes.forEach(idQuestao -> {
			dto.idCurrent = idQuestao;
			element.appendChild(getDivToLink(dto));
		});
	}
	
	private Element getDivToLink(Dto dto) {
		Element div = getDiv();
		div.appendChild(getRadio(dto));
		div.appendChild(getInputHidden(dto));
		div.appendChild(getLinkElement(dto));
		return div;
	}
	
	private Element getDiv() {
		Tag tag = Tag.valueOf("div");
		Attributes attributes = new Attributes();
		attributes.put("class", "questao");
		Element div = new Element(tag, "", attributes);
		return div;
	}
	
	private Element getLinkElement(Dto dto) {
		String linkQuestao = configuracao.context + URL_CONTEUDO_QUESTOES + dto.idCurrent;
		Tag tag = Tag.valueOf("a");
		Attributes attributes = new Attributes();
		attributes.put("href", linkQuestao);
		attributes.put("target", "_blank");
		Element link = new Element(tag, "", attributes);
		link.appendText(dto.idCurrent.toString());
		return link;
	}
	
	private Element getRadio(Dto dto) {
		Tag tag = Tag.valueOf("input");
		Attributes attributes = new Attributes();
		attributes.put("type", "radio");
		attributes.put("name", dto.radioName);
		attributes.put("value", dto.idCurrent.toString());
		attributes.put("class", "questao-redirecionamento");
		Element radio = new Element(tag, "", attributes);
		return radio;
	}
	
	private Element getInputHidden(Dto dto) {
		Tag tag = Tag.valueOf("input");
		Attributes attributes = new Attributes();
		attributes.put("type", "hidden");
		attributes.put("value", dto.idQuestaoDesativada.toString());
		attributes.put("class", "questao-desativada");
		Element input = new Element(tag, "", attributes);
		return input;
	}
	
	public List<Long> buscarPorEnunciado(String enunciado) {
		System.out.println("Leitura.buscarPorEnunciado() - buscando no servidor:  " + configuracao.type.getDescricao());
		return service.buscarQuestaoPorEnunciado(enunciado);
	}
	
	public static void main(String[] args) throws Exception {
		new Leitura().build();
		System.out.println("FIM");
	}
	
	static class Configuracao {
		private static final String PATH = "C:\\Users\\jaoin\\Documents\\dbvis\\";
		
		protected String fileSave;
		protected String file;
		protected int iteration;
		protected Charset charset;
		protected String context;
		protected FullTextTypeEnum type;
		
		private Configuracao(String fileSave, String file, int iteration, String context) {
			super();
			FullTextProperties properties = FullTextProperties.getInstance();
			FullTextTypeEnum type = properties.getFullTextTypeEnum();
			this.type = type;
			this.fileSave = PATH + fileSave;
			this.file = PATH + file;
			this.iteration = iteration;
			this.charset = Charset.forName("windows-1252");
			this.context = context;
		}
		public static Configuracao getDesenvolvimento() {
			return new Configuracao("seo-questoes-inativas-develop.html", "dbvis-save-develop.html", 1, "https://localhost/tec-site-web");
		}
		
		public static Configuracao getProducao() {
			return new Configuracao("seo-questoes-inativas.html", "dbvis-save.html", 1, "https://www.tecconcursos.com.br");
		}
	}
	
	static class Dto {
		protected String enunciado;
		protected Long idQuestaoDesativada;
		protected List<Long> questoes;
		protected Element element;
		protected String radioName;
		protected Long idCurrent;
		
		public Dto(String enunciado, Long idQuestaoDesativada, List<Long> questoes, Element element, String radioName) {
			super();
			this.enunciado = enunciado;
			this.idQuestaoDesativada = idQuestaoDesativada;
			this.questoes = questoes;
			this.element = element;
			this.radioName = radioName;
		}
		
	}
}
