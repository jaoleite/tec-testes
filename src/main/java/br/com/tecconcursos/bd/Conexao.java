package br.com.tecconcursos.bd;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.time.Duration;
import java.time.Instant;

public class Conexao {

	private static final String DRIVER = "org.postgresql.Driver";
	
	public static boolean testeConnection() {
		try {
			Class.forName(DRIVER);
			ConexaoEnum conexaoEnum = ConexaoEnum.PRODUCAO;
			DataBaseConnection dataBaseConnection = DataBaseConnection.instance(conexaoEnum);
			Connection conexao = DriverManager.getConnection(
					dataBaseConnection.getConnection(),
					dataBaseConnection.getUsername(),
					dataBaseConnection.getPassword());
			System.out.println(conexaoEnum);
			System.out.println("Conexão aberta: " + !conexao.isClosed());
			conexao.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public static String getRede() {
		try {
			String string = InetAddress.getLocalHost().getHostAddress();
			return string.substring(0, string.lastIndexOf("."));
		} catch (UnknownHostException e) {
			return null;
		}
	}
	
	public static void teste() {
		Instant inicio = Instant.now();
		String rede = getRede() + ".";
		for (int i = 2; i < 254; i++) {
			String ip = rede + i;
			boolean b = testeConnection();
			if(b) {
				System.out.println("Conexao ok. IP: " + ip);
				break;
			} else {
				System.out.println("Ip " + ip + " não possui conexao com banco de dados");
			}
		}
		
		Instant fim = Instant.now();
		Duration duracao = Duration.between(inicio, fim);
		long duracaoEmMilissegundos = duracao.toMillis();
		System.out.println("Tempo: " + (duracaoEmMilissegundos / 1000));
	}
	
	public static void main(String[] args) throws UnknownHostException {
		testeConnection();
	}

}
