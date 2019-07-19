package br.com.tecconcursos.ssh;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

import br.com.tecconcursos.util.CertificadoProperties;

public class SessionProperties {

	public static final String HOSTS_PROPERTY = "hosts";
	
	private static SessionProperties instance;
	
	private Properties properties;
	
	
	private SessionProperties() {
		properties = new Properties();
		InputStream inStream = this.getClass().getClassLoader().getResourceAsStream("session.properties");
		try {
			properties.load(inStream);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static SessionProperties getInstance() {
		if(instance == null) {
			synchronized (CertificadoProperties.class) {
				if(instance == null) {
					instance = new SessionProperties();
				}
			}
		}
		return instance;
	}
	
	public Properties getProperties() {
		return getInstance().properties;
	}
	
	public static void main(String[] args) {
		Properties properties = SessionProperties.getInstance().getProperties();
		Set<Object> set = properties.keySet();
		Properties config = new Properties();
		for (Object object : set) {
			String key = object.toString();
			if(key.contains("config.")) {
				String newKey = key.substring(key.indexOf(".") + 1);
				String value = properties.getProperty(key);
				config.put(newKey, value);
			}
		}
		System.out.println(config);
	}
	
}
