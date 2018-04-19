package br.com.tecconcursos.bd;

public class DataBaseConnection {

	private ConexaoEnum conexaoEnum;
	
	public DataBaseConnection(ConexaoEnum conexaoEnum) {
		this.conexaoEnum = conexaoEnum;
	}
	
	public static DataBaseConnection instance(ConexaoEnum conexao) {
		return new DataBaseConnection(conexao);
	}
	
	public String getConnection() {
		String template = "jdbc:postgresql://%s:%d/%s";
		String result = String.format(template, conexaoEnum.getHost(), conexaoEnum.getPort(), conexaoEnum.getDatabase());
		return result;
	}
	
	public String getPassword() {
		return conexaoEnum.getPassword();
	}
	
	public String getUsername() {
		return conexaoEnum.getUsername();
	}

}
