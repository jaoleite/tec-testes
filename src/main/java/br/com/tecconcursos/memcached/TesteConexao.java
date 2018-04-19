package br.com.tecconcursos.memcached;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import net.spy.memcached.ConnectionFactory;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.FailureMode;
import net.spy.memcached.MemcachedClient;

public class TesteConexao {

	private static final String KEY = "CHAVE_TESTE_CONEXAO";
	private static final int DEZ_MINUTOS = 10 * 60;
	
	private String valor = "";
	private TesteConcexaoEnum testeConcexaoEnum;
	
	public TesteConexao(TesteConcexaoEnum testeConcexaoEnum) {
		this.testeConcexaoEnum = testeConcexaoEnum;
		this.valor = "Testando a gravação no memcached do servidor: " + testeConcexaoEnum.name();
	}
	
	public void addOrReplace() throws InterruptedException, ExecutionException {
		if(get() == null) {
			MemcachedClient client = getClient();
			Future<Boolean> future = client.set(KEY, DEZ_MINUTOS, valor + " (set)");
			future.get();
			client.shutdown();
		} else {
			MemcachedClient client = getClient();
			Future<Boolean> future = client.replace(KEY, DEZ_MINUTOS, valor + " (replace)");
			future.get();
			client.shutdown();
		}
	}
	
	public String get() {
		MemcachedClient client = getClient();
		String st = (String) client.get(KEY);
		client.shutdown();
		return st;
	}
	
	public void testar() {
		try {
			addOrReplace();
			String st = get();
			System.out.println(st);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	public MemcachedClient getClient() {
		ConnectionFactory factory = new ConnectionFactoryBuilder().setDaemon(true).setFailureMode(FailureMode.Retry).build();
		InetSocketAddress inetSocketAddress = new InetSocketAddress(testeConcexaoEnum.getHost(), testeConcexaoEnum.getPort());
		List<InetSocketAddress> list = Arrays.asList(inetSocketAddress);
		try {
			return new MemcachedClient(factory, list);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getByIdSession(String idSession) {
		MemcachedClient client = getClient();
		T t = (T) client.get(idSession);
		client.shutdown();
		return t;
	}
	
	public static void main(String[] args) {
		TesteConexao conexao = new TesteConexao(TesteConcexaoEnum.DESENVOLVIMENTO);
		Object object = conexao.getByIdSession("F92878BB80597ED2B48BB31E4CA60171");
		System.out.println(object);
		
	}

}
