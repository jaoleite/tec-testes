package br.com.tecconcursos.memcached;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import net.spy.memcached.MemcachedClient;
import br.com.tecconcursos.util.cache.CacheKey;
import br.com.tecconcursos.util.cache.CacheValue;

public class TesteMemcached {
	
	private MemcachedClient client = null;
		
	public void testeSerialization() {
		try {
			client = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
			String key = "KEY_TEST";
			ObjetoSerializado serializadoSet = new ObjetoSerializado();
			serializadoSet.setObjectTeste(new ObjectTeste("joao", 1L));
			Future<Boolean> future = client.set(key + "_NAO", 0, new ObjectTeste("joao", 1L), new MyTrancoder());
			//Future<Boolean> future = client.set(key, 0, serializadoSet);
			future.get();
			ObjectTeste objectTeste = (ObjectTeste) client.get(key + "_NAO");
			System.out.println(objectTeste);
			//ObjetoSerializado objetoSerializado = (ObjetoSerializado) client.get(key);
			//System.out.println(objetoSerializado.getObjectTeste());
			client.shutdown();
		} catch (Exception e) {
			if(client != null) {
				client.shutdown();
			}
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		new TesteMemcached().testeSerialization();
	}
	
	public static void main2(String[] args) throws IOException {
		MemcachedClient client = MemcachedUtil.currentClient();
		client.shutdown();
		client = MemcachedUtil.currentClient();
		List<String> list = Arrays.asList(CacheKey.values()).stream().map(p -> p.name()).collect(Collectors.toList());
		try {
			for (String key : list) {
				CacheValue<?> value = (CacheValue<?>) client.get(key);
				System.out.println(key + "=> " + value);
				if(value != null) {
					System.out.println(value.getValue());
				}
				
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			//client.shutdown();
		}
	}

}
