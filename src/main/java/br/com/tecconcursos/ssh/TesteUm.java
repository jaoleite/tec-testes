package br.com.tecconcursos.ssh;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.util.Enumeration;

import org.apache.commons.io.IOUtils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class TesteUm {

	public TesteUm() {
	}

	public Session connect() {
		JSch jsch = new JSch();
		Session session = null;
		String privateKeyPath = "D:\\Desenvolvimento\\putty\\techomologacao.pem";
		try {
			String host = "54.232.105.168";
			int port = 5849;
			String username = "ec2-user";

			jsch.addIdentity(privateKeyPath);
			session = jsch.getSession(username, host, port);
			session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);

			return session;

		} catch (JSchException e) {
			throw new RuntimeException("Failed to create Jsch Session object.", e);
		}
	}

	public void sendCommand() {
		String command = "echo \"Sit down, relax, mix yourself a drink and enjoy the show...\" >> /home/ec2-user/temp/test.out";
		try {
			Session session = connect();
			session.connect();
			Channel channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(command);
			((ChannelExec) channel).setPty(false);
			channel.connect();
			imprimir(channel.getInputStream());
			channel.disconnect();
			session.disconnect();
		} catch (JSchException e) {
			throw new RuntimeException("Error durring SSH command execution. Command: " + command);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void imprimir(InputStream inputStream) throws IOException {
		StringWriter writer = new StringWriter();
		String encoding = "UTF-8";
		IOUtils.copy(inputStream, writer, encoding);
		String result = writer.toString();
		System.out.println(result);
	}

	public static void main(String[] args) throws Exception {
		//new TesteUm().sendCommand();
		testes();
	}
	
	public static void testes() throws Exception {
		KeyStore ks = KeyStore.getInstance("Windows-ROOT");
		ks.load(null, null);
		Enumeration<String> enumeration = ks.aliases();
		while (enumeration.hasMoreElements()) {
			String alias = (String) enumeration.nextElement();
			System.out.println(alias);
			Certificate certificate = ks.getCertificate(alias);
			if(certificate != null && alias.contains("Brasileira")) {
				KeyStore.Entry entry = ks.getEntry(alias, null);
				//System.out.println(alias);
				System.out.println(entry);
				
			}
		}
		
		System.out.println();
	}

}
