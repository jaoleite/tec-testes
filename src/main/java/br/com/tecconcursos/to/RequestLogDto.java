package br.com.tecconcursos.to;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.tecconcursos.service.CriptografiaService;
import br.com.tecconcursos.to.enums.TipoLogEnum;

public class RequestLogDto {

	private TipoLogEnum tipo;
	
	private String descricaoTipo;
	
	private Date date;
	
	private Long requestTime;
	
	private String ip;
	
	private String email;
	
	private String url;
	
	private String linha;
	
	private List<TipoLogEnum> listaTipo;
	
	public RequestLogDto() {}

	public RequestLogDto(String linha) {
		this.linha = linha;
		if(processarLinha()) {
			tipo = buscarTipo();
			descricaoTipo = tipo.getDescricao();
			date = buscarData();
			requestTime = buscarIntervaloDeTempo();
			ip = buscarIP();
			email = buscarEmail();
			url = buscarUrl();
		}
	}
	
	public TipoLogEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoLogEnum tipo) {
		this.tipo = tipo;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Long requestTime) {
		this.requestTime = requestTime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getDescricaoTipo() {
		return descricaoTipo;
	}

	public void setDescricaoTipo(String descricaoTipo) {
		this.descricaoTipo = descricaoTipo;
	}

	public List<TipoLogEnum> getListaTipo() {
		return listaTipo;
	}

	public void setListaTipo(List<TipoLogEnum> listaTipo) {
		this.listaTipo = listaTipo;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RequestLogDto [tipo=");
		builder.append(tipo);
		builder.append(", date=");
		builder.append(date);
		builder.append(", requestTime=");
		builder.append(requestTime);
		builder.append(", ip=");
		builder.append(ip);
		builder.append(", email=");
		builder.append(email);
		builder.append(", url=");
		builder.append(url);
		builder.append("]");
		return builder.toString();
	}
	
	private boolean processarLinha() {
		if(linha.isEmpty() || linha.length() < 2) {
			return false;
		} else if(!linha.startsWith("S [") && !linha.startsWith("E [")) {
			return false;
		}
		return true;
	}
	
	private Date buscarData() {
		String colchetes = entreColchetes();
		String[] strings = colchetes.split(" ");
		Date date = null;
		SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		try {
			int ano = Calendar.getInstance().get(Calendar.YEAR);
			date = format.parse(strings[0].trim() + "-" + ano + " " + strings[1].trim());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	private long buscarIntervaloDeTempo() {
		String colchetes = entreColchetes();
		String[] strings = colchetes.split(" ");
		String[] tempos = strings[1].split("-");
		
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		try {
			Date dataInicial = format.parse(tempos[0].trim());
			Date dataFinal = format.parse(tempos[1].trim());
			long l = dataFinal.getTime() - dataInicial.getTime();
			return l;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	private String entreColchetes() {
		int abreColchete = linha.indexOf("[") + 1;
		int fechaColchete = linha.indexOf("]");
		return linha.substring(abreColchete, fechaColchete);
	}
	
	private String entreChaves() {
		int abreChave = linha.indexOf("{") + 1;
		int fechaChave = linha.indexOf("}");
		return linha.substring(abreChave, fechaChave);
	}
	
	private String buscarUrl() {
		int fechaChave = linha.indexOf("}") + 1;
		return linha.substring(fechaChave).trim();
	}
	
	private TipoLogEnum buscarTipo() {
		String codigo = linha.substring(0, 1);
		return TipoLogEnum.buscarPorCodigo(codigo);
	}
	
	private String buscarIP() {
		String chaves = entreChaves();
		String[] strings = chaves.split(" ");
		return strings[0].trim();
	}
	
	private String buscarEmail() {
		String chaves = entreChaves();
		String[] strings = chaves.split(" ");
		if(strings.length >= 2) {
			return strings[1].trim();
		}
		return null;
	}
	
	public static void main(String[] args) {
		CriptografiaService service = new CriptografiaService();
		System.out.println(service.hashBCrypt("inicio123"));
	}
}
