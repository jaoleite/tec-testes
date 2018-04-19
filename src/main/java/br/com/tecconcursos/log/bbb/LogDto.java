package br.com.tecconcursos.log.bbb;

import java.time.LocalDate;
import java.time.LocalTime;

public class LogDto {

	//S [09-02 12:01:05-12:01:05] {189.6.18.196 lillian.haina@gmail.com} /api/cadernos/2614506/questoes/6?numeroQuestao=6&atualizarCronometro=true&idCaderno=2614506
	private LocalDate data;
	
	private LocalTime inicioRequisicao;
	
	private LocalTime fimRequisicao;
	
	private String email;
	
	private String ip;
	
	private String url;
	
	public static void toLogDto(String row) {
		
	}
	
	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public LocalTime getInicioRequisicao() {
		return inicioRequisicao;
	}

	public void setInicioRequisicao(LocalTime inicioRequisicao) {
		this.inicioRequisicao = inicioRequisicao;
	}

	public LocalTime getFimRequisicao() {
		return fimRequisicao;
	}

	public void setFimRequisicao(LocalTime fimRequisicao) {
		this.fimRequisicao = fimRequisicao;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public static void main(String[] args) {
		String st = "S [09-02 12:01:05-12:01:05] {189.6.18.196 lillian.haina@gmail.com} /api/cadernos/2614506/questoes/6?numeroQuestao=6&atualizarCronometro=true&idCaderno=2614506";
		if(st.contains("@")) {
			System.out.println("Status: " + st.substring(0, 1));
			System.out.println("data mais hora: " + st.substring(st.indexOf("["), st.indexOf("]")));
			String ipMaisEmail = st.substring(st.indexOf("{"), st.indexOf("}"));
			String email = null;
			String ip = null;
			String[] array = ipMaisEmail.split(" ");
			if(array.length == 2) {
				ip = array[0];
				email = array[1];
			}
			System.out.println("e-mail: " + email + ", ip: " + ip);
			System.out.println("url: " + st.substring(st.lastIndexOf(" "), st.length()));
		}
	}

}
