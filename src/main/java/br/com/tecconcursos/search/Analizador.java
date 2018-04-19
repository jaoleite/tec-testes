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
		builder.append("Analistas, especialmente vindos da biologia, das ci�ncias da Terra e da cosmologia, nos advertem que o tempo atual se assemelha muito �s �pocas de grande ruptura no processo da evolu��o, �pocas caracterizadas por extin��es em massa. Efetivamente, a humanidade se encontra diante de uma situa��o inaudita. Deve decidir se quer continuar a viver ou se escolhe sua autodestrui��o.");
		builder.append("O risco n�o vem de alguma amea�a c�smica - o choque de algum meteoro ou aster�ide rasante - nem de algum cataclismo natural produzido pela pr�pria Terra - um terremoto sem propor��es ou algum deslocamento fenomenal de placas tect�nicas. Vem da pr�pria atividade humana. O aster�ide amea�ador se chama homo sapiens demens, surgido na �frica h� poucos milh�es de anos.");
		builder.append("Pela primeira vez no processo conhecido de hominiza��o, o ser humano se deu os instrumentos de sua autodestrui��o. Criou-se verdadeiramente um princ�pio, o de autodestrui��o, que tem sua contrapartida, o princ�pio de responsabilidade. De agora em diante, a exist�ncia da biosfera estar� � merc� da decis�o humana. Para continuar a viver, o ser humano dever� quer�-lo. Ter� que garantir as condi��es de sua sobrevida. Tudo depende de sua pr�pria responsabilidade. O risco pode ser fatal e terminal.");
		builder.append("Resumidamente, tr�s s�o os n�s problem�ticos que, urgentemente, devem ser desatados: o n� da exaust�o dos recursos naturais n�o renov�veis, o n� da suportabilidade da Terra (quanto de agress�o ela pode suportar?) e o n� da injusti�a social mundial.");
		return builder.toString();
	}
	
	public static void main(String[] args) {
		String text = getText();
		text = textTokenizer(text);
		System.out.println();
		System.out.println(text);
	}
}
