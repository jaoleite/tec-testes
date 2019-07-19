package br.com.tecconcursos.ssh;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class Host {

	private String ip;
	
	private int port;
	
	private String user;
	
	private String privateKey;
	
	private Properties properties;
	
	private Properties config;
	
	public Host() {}
	
	public Host(Properties properties) {
		this.properties = properties;
		generateConfigs();
	}
	
	public List<Host> hosts() {
		falharSe();
		String stringHosts = properties.getProperty(SessionProperties.HOSTS_PROPERTY);
		String[] hosts = stringHosts.split(";");
		List<Host> list = new ArrayList<>();
		for (String host : hosts) {
			list.add(new Host(host));
		}
		return list;
	}
	
	private void falharSe() {
		if(properties == null) {
			throw new RuntimeException("As propriedades não foram carregadas");
		}
	}
	
	private void generateConfigs() {
		Set<Object> set = properties.keySet();
		config = new Properties();
		for (Object object : set) {
			String key = object.toString();
			if(key.contains("config.")) {
				String newKey = key.substring(key.indexOf(".") + 1);
				String value = properties.getProperty(key);
				config.put(newKey, value);
			}
		}
	}
	
	public Host(String host) {
		int arroba = host.indexOf("@");
		int virgula = host.indexOf(",");
		int inicio = 0;
		
		String ipPort = host.substring(inicio, arroba);
		String user = host.substring(arroba + 1, virgula);
		String privateKey = host.substring(virgula + 1);
		
		int doisPontos = ipPort.indexOf(":");
		String ip = ipPort.substring(inicio, doisPontos);
		Integer port = new Integer(ipPort.substring(doisPontos + 1));
		
		this.ip = ip;
		this.port = port;
		this.privateKey = privateKey;
		this.user = user;
	}

	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

	public String getUser() {
		return user;
	}

	public String getPrivateKey() {
		return privateKey;
	}
	
	public Properties getConfig() {
		return config;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Host [ip=");
		builder.append(ip);
		builder.append(", port=");
		builder.append(port);
		builder.append(", user=");
		builder.append(user);
		builder.append(", privateKey=");
		builder.append(privateKey);
		builder.append("]");
		return builder.toString();
	}
	
}
