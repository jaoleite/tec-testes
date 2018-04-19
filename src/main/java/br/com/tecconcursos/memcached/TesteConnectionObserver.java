package br.com.tecconcursos.memcached;

import java.net.SocketAddress;

import net.spy.memcached.ConnectionObserver;

public class TesteConnectionObserver implements ConnectionObserver {

	@Override
	public void connectionEstablished(SocketAddress address, int i) {
		System.out.println("TesteMemcached.TesteConnectionObserver.connectionEstablished()");
		System.out.println(i);
		System.out.println(address);
	}

	@Override
	public void connectionLost(SocketAddress address) {
		System.out.println("TesteMemcached.TesteConnectionObserver.connectionLost()");
		System.out.println(address);
	}

}
