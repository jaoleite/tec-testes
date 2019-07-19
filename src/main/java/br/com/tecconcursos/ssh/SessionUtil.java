package br.com.tecconcursos.ssh;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SessionUtil {

	private Host host;
	private Session session;
	
	public SessionUtil() {}
	
	public SessionUtil(Host host) {
		this.host = host;
	}
	
	public Session connect() {
		try {
			JSch jsch = new JSch();
			session = null;
			String privateKeyPath = host.getPrivateKey();
			String ip = host.getIp();
			int port = host.getPort();
			String username = host.getUser();

			jsch.addIdentity(privateKeyPath);
			session = jsch.getSession(username, ip, port);
			session.connect();
			return session;
		} catch (JSchException e) {
			throw new RuntimeException("Failed to create Jsch Session object.", e);
		}
	}
	
	public void disconnect() {
		if(session != null && session.isConnected()) {
			session.disconnect();
		}
	}

}
