package br.com.tecconcursos.memcached;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Map;
import java.util.Set;

import javax.swing.UIManager;

import net.spy.memcached.MemcachedClient;

public class MemcachedJava {

	public static void teste() { 
		try{ 
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
		} catch(Exception e){ 
			e.printStackTrace(); 
			return; 
		}
		System.out.println("teste");
		//String s = null; 
		//JFileChooser chooser = new JFileChooser(s); 
	}
	
	public static void main(String[] args) throws IOException {
		teste();
		/*MemcachedClient mcc = new MemcachedClient(new InetSocketAddress("tecmemcached.ubxn0s.cfg.sae1.cache.amazonaws.com", 11211));
		System.out.println(mcc.getStats());
		mcc.shutdown();*/
		//System.out.println(mcc.get("56E36361B0B64A7FFDAD7B86A2CBC2B1-n1"));
		//mcc.shutdown();
		//stats();
	}
	
	public static void stats() {
		try{
			// Connecting to Memcached server on localhost
			MemcachedClient mcc = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
			//System.out.println("Memcached Statistics - " + mcc.getStats());
			Set<SocketAddress> set = mcc.getStats().keySet();
			for (SocketAddress socketAddress : set) {
				StringBuilder builder = new StringBuilder();
				Map<String, String> map = mcc.getStats().get(socketAddress);
				builder.append(socketAddress).append("\n\n");
				Set<String> strings = map.keySet();
				for (String string : strings) {
					builder.append(string).append(" = ").append(map.get(string)).append("\n");
				}
				System.out.println(builder.toString());
			}
			
			// Shutdowns the memcached client
			mcc.shutdown();
			
		} catch(Exception e){
			System.out.println(e.getMessage());
		}

	}

}
