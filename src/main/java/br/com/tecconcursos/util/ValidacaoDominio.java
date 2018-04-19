package br.com.tecconcursos.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import br.com.tecconcursos.arquivo.Arquivo;

public class ValidacaoDominio {

	private static final String ARQUIVO = "dominios.txt";
	private static final String PATTERN = "^(([a-zA-Z]{1})|([a-zA-Z]{1}[a-zA-Z]{1})|([a-zA-Z]{1}[0-9]{1})|([0-9]{1}[a-zA-Z]{1})|([a-zA-Z0-9][a-zA-Z0-9-_]{1,61}[a-zA-Z0-9]))\\.([a-zA-Z]{2,6}|[a-zA-Z0-9-]{2,30}\\.[a-zA-Z]{2,3})$";
	
	private Pattern pattern = null;
	private List<String> dominios = null;
	private List<String> listaFinal = null;
	
	public static ValidacaoDominio of() {
		return new ValidacaoDominio();
	}
	
	public ValidacaoDominio leitura() {
		dominios = Arquivo.of(ARQUIVO).lsitarDoAqruivo();
		return this;
	}
	
	public ValidacaoDominio pattern() {
		falharSeListaDominiosNulo();
		pattern = Pattern.compile(PATTERN);
		return this;
	}
	
	public ValidacaoDominio listarDominiosValidos() {
		falharSePatternNulo();
		listaFinal = dominios.stream().filter(p -> validarDominio(p.trim())).collect(Collectors.toList());
		return this;
	}
	
	public boolean validarDominio(String dominio) {
		Matcher matcher = pattern.matcher(dominio);
		return matcher.find();
	}
	
	public void printSqlInsert() {
		falharSeListaFinalNulo();
		String template = "insert into dominioemail(id, dominio, pendente, whitelist) values(nextval('SeqDominioEmail'), '%s', false, true);";
		listaFinal.stream().forEach(p -> {
			System.out.println(String.format(template, p));
		});
	}
	
	private void falharSeListaDominiosNulo() {
		if(Util.vazio(dominios)) {
			throw new RuntimeException("lista de dominios está nula");
		}
	}
	
	private void falharSePatternNulo() {
		falharSeListaDominiosNulo();
		if(pattern == null) {
			throw new RuntimeException("pattern é nulo");
		}
	}
	
	private void falharSeListaFinalNulo() {
		falharSePatternNulo();
		if(listaFinal == null) {
			throw new RuntimeException("lista de dominios validos está nula");
		}
	}
	
	public static void main(String[] args) {
		ValidacaoDominio.of().leitura().pattern().listarDominiosValidos().printSqlInsert();
	}

}
