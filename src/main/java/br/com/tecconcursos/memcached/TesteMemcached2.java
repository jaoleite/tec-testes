package br.com.tecconcursos.memcached;

import java.util.HashMap;
import java.util.Map;

import br.com.tecconcursos.entity.Banca;
import br.com.tecconcursos.entity.Depoimento;
import br.com.tecconcursos.util.TecMemcachedProperties;
import br.com.tecconcursos.util.Util;

public class TesteMemcached2 {
	
	private String key = null;
	
	public TesteMemcached2() {
		key = TecMemcachedProperties.getInstance().getMultipleRequestMapKey();
		System.out.println(key);
	}
	
	public void leituraMemcached() {
		Map<String, Boolean> map = TecMemcached.of().get(key);
		System.out.println("leitura: " + map);
	}
	
	public void gravacaoMemcached(String ip, Boolean b) {
		Map<String, Boolean> map = TecMemcached.of().get(key);
		if(map == null) {
			map = new HashMap<>();
			TecMemcached.of().add(key, map, TecMemcached.DEFAULT_TEC_TIMEOUT).isDone();
		}
		if(map.containsKey(ip)) {
			map.replace(ip, b);
		} else {
			map.put(ip, b);
		}
		System.out.println(map);
		
		TecMemcached.of().replace(key, map, TecMemcached.DEFAULT_TEC_TIMEOUT).isDone();
	}
	
	public void removerMemcached(String ip) {
		Map<String, Boolean> map = TecMemcached.of().get(key);
		if(map != null && map.containsKey(ip)) {
			map.remove(ip);
			TecMemcached.of().replace(key, map, TecMemcached.DEFAULT_TEC_TIMEOUT).isDone();
		}
	}
	

	public static void main(String[] args) {
		/*String ip = "0:0:0:0:0:0:0:1";
		//new TesteMemcached2().gravacaoMemcached(ip, true);
		//new TesteMemcached2().gravacaoMemcached("joaopaulo@auroid.com", true);
		new TesteMemcached2().removerMemcached(ip);
		new TesteMemcached2().leituraMemcached();*/
		//gerarURL();
		gerarUrlBanca();
		//System.out.println(Util.geradorUUID().replaceAll("-", ""));
	}
	
	protected static void gerarURL() {
		String url = Util.gerarUrl("");
		System.out.println(url);
	}
	
	public static void gerarUrlBanca() {
		// a url da banca é gerada a partir da sigla
		Banca banca = new Banca();
		banca.setSigla("SELECON");
		System.out.println(Util.gerarUrl(banca.getSigla()));
	}
	
	protected static void geracaoUrlDepoimento() {
		Depoimento depoimento = new Depoimento();
		depoimento.setNome("Carolina Carneiro de Castro Prates de Sá");
		depoimento.setConcurso("Técnico do MPU - Administração - DF");
		depoimento.setPosicaoAprovacao("5º");
		String autor = depoimento.getAutor();
		String url = Util.gerarUrl(autor);
		
		System.out.println(url);
		
		// url antiga no banco: carolina-carneiro-de-castro-prates-de-sa-5-analista-do-mpu-direito-am
		// id do depoimento alterado: 144
	}

}
