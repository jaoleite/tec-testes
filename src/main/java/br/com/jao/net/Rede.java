package br.com.jao.net;

import java.io.IOException;
import java.net.InetAddress;

public class Rede {

	public static void main(String[] args) throws IOException, InterruptedException {
		InetAddress localaddr = InetAddress.getLocalHost();
		
		System.out.println ("Local IP Address : " + localaddr );
		System.out.println ("Local hostname   : " + localaddr.getHostName());
		
	}

}
