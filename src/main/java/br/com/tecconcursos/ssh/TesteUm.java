package br.com.tecconcursos.ssh;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class TesteUm {

	private static final Locale LOCALE_PT_BR = new Locale("pt", "br");
	
	private Session session;
	
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
			//session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			config.put("PreferredAuthentications", "publickey,keyboard-interactive,password");
			session.setConfig(config);
			
			return session;

		} catch (JSchException e) {
			throw new RuntimeException("Failed to create Jsch Session object.", e);
		}
	}
	
	public void sendCommands() {
		session = connect();
		try {
			session.connect();
			//String agora = formatDate(LocalDateTime.now());
			//String command = "sudo echo \"TESTE DE COMMANDO => agora são: " + agora +  "\" >> /home/ec2-user/temp/test.out";
			//String ip = "189.6.26.28";
			//String templateCommand = "sudo sh -c \"echo 'deny %s;' >> /etc/nginx/blockips.conf\"";
			//String templateCommand = "echo 'deny %s;' | sudo tee --append /etc/nginx/blockips.conf";
			
			//String command = String.format(templateCommand, ip);
			String[] commands = {"sudo nginx -t"};
			
			for (String cmd : commands) {
				sendCommand(cmd);
			}
			
		} catch (JSchException e) {
			e.printStackTrace();
			throw new RuntimeException("Error durring SSH command execution. Command: ");
		} catch (IOException e) {
			e.printStackTrace();
		}
		session.disconnect();
		System.out.println(session.isConnected());
	}

	public void sendCommand(String command) throws JSchException, IOException {
		System.out.println(session.isConnected());
		Channel channel = session.openChannel("exec");
		((ChannelExec) channel).setPty(true);
		((ChannelExec) channel).setCommand(command);
		
		channel.connect();
		imprimir(channel.getInputStream());
		System.out.println("ExitStatus: " + channel.getExitStatus());
		channel.disconnect();
		System.out.println("ExitStatus: " + channel.getExitStatus());
	}
	
	protected String formatDate(LocalDateTime agora) {
		DateTimeFormatter formatador = DateTimeFormatter
			.ofLocalizedDateTime(FormatStyle.MEDIUM)
			.withLocale(LOCALE_PT_BR);
		return agora.format(formatador);
	}
	
	public void imprimir(InputStream inputStream) throws IOException {
		StringWriter writer = new StringWriter();
		String encoding = "UTF-8";
		IOUtils.copy(inputStream, writer, encoding);
		String result = writer.toString();
		System.out.println(result);
		inputStream.close();
	}
	
	public void teste(OutputStream outputStream) {
		//outputStream.
	}

	public static void main(String[] args) throws Exception {
		new TesteUm().sendCommands();
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
