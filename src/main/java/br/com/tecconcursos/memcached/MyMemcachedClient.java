package br.com.tecconcursos.memcached;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.util.List;

import net.spy.memcached.ConnectionFactory;
import net.spy.memcached.MemcachedClient;

public class MyMemcachedClient extends MemcachedClient {

	public MyMemcachedClient(ConnectionFactory cf, List<InetSocketAddress> addrs) throws IOException {
		super(cf, addrs);
	}
	
	private void writeObject(ObjectOutputStream os) throws IOException, ClassNotFoundException {
        os.defaultWriteObject();
    }
	

	
}
