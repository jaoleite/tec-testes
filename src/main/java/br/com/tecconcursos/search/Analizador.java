package br.com.tecconcursos.search;

import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.br.BrazilianAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.QueryParser;

import br.com.tecconcursos.util.Util;

public class Analizador {

	public static Analyzer getAnalyzer() {
		return new StandardAnalyzer(BrazilianAnalyzer.BRAZILIAN_STOP_WORDS);
	}
	
	public static String textTokenizer(String text) {
		try {
			Analyzer analyzer = getAnalyzer();
			String field = "enunciado";
			text = QueryParser.escape(text);
			System.out.println("Depois do escape:");
			System.out.println(text);
			TokenStream stream = analyzer.tokenStream(field, new StringReader(text));
			org.apache.lucene.analysis.Token token = new org.apache.lucene.analysis.Token();
			StringBuilder tokens = new StringBuilder();
			while(stream.next(token) != null) {
				//tokens.append(token.term()).append(" ").append(QueryParser.AND_OPERATOR).append(" ");
				tokens.append(token.term()).append("\n");
			}
			
			String s = tokens.toString();
			if(!Util.vazio(s) && s.lastIndexOf(" AND ") > -1) {
				s = s.substring(0, s.lastIndexOf(" AND "));
			}
			
			return s;
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private static String getText() {
		StringBuilder builder = new StringBuilder();
		builder.append("Analistas, especialmente vindos da biologia, das ciências da Terra e da cosmologia, nos advertem que o tempo atual se assemelha muito às épocas de grande ruptura no processo da evolução, épocas caracterizadas por extinções em massa. Efetivamente, a humanidade se encontra diante de uma situação inaudita. Deve decidir se quer continuar a viver ou se escolhe sua autodestruição.");
		builder.append("O risco não vem de alguma ameaça cósmica - o choque de algum meteoro ou asteróide rasante - nem de algum cataclismo natural produzido pela própria Terra - um terremoto sem proporções ou algum deslocamento fenomenal de placas tectônicas. Vem da própria atividade humana. O asteróide ameaçador se chama homo sapiens demens, surgido na África há poucos milhões de anos.");
		builder.append("Pela primeira vez no processo conhecido de hominização, o ser humano se deu os instrumentos de sua autodestruição. Criou-se verdadeiramente um princípio, o de autodestruição, que tem sua contrapartida, o princípio de responsabilidade. De agora em diante, a existência da biosfera estará à mercê da decisão humana. Para continuar a viver, o ser humano deverá querê-lo. Terá que garantir as condições de sua sobrevida. Tudo depende de sua própria responsabilidade. O risco pode ser fatal e terminal.");
		builder.append("Resumidamente, três são os nós problemáticos que, urgentemente, devem ser desatados: o nó da exaustão dos recursos naturais não renováveis, o nó da suportabilidade da Terra (quanto de agressão ela pode suportar?) e o nó da injustiça social mundial.");
		return builder.toString();
	}
	
	public static void main(String[] args) {
		String text = getText();
		text = textTokenizer(text);
		System.out.println();
		System.out.println(text);
	}
}
