package br.com.tecconcursos.mercadopago;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONObject;

import br.com.tecconcursos.to.mercadopago.CollectionMercadoPagoDto;
import br.com.tecconcursos.to.mercadopago.ResultsBuscaMercadoPagoDto;
import br.com.tecconcursos.to.mercadopago.RetornoMercadoPagoDto;
import br.com.tecconcursos.util.Util;
import br.com.tecconcursos.util.gson.MercadoPagoGsonHelper;

import com.google.gson.Gson;
import com.mercadopago.MP;

public class Busca {

	private String clientId = null;
	
	private String clientSecret = null;
	
	private MP mp = null;
	
	private String path = "D:\\desenvolvimento\\arquivos\\RetornoMercadoPagoDto.txt";
	
	public Busca() {
		this.clientId = "2989894347999525";
		this.clientSecret = "9FM241PzTTSEBOec7gDT7P2T8Xqgv0Pa";
		this.mp = new MP (this.clientId, this.clientSecret);
		this.path = "D:\\desenvolvimento\\arquivos\\RetornoMercadoPagoDto.txt";
	}
	
	@SuppressWarnings("unchecked")
	public List<Long> listarIds(String file) {
		try {
			List<Long> longs = new ArrayList<Long>();
			List<String> list = IOUtils.readLines(new FileInputStream(new File(file)));
			for (String line : list) {
				if(!Util.vazio(line) && StringUtils.isNumeric(line)) {
					longs.add(new Long(line));
				}
			}
			return longs;
		} catch (Exception e) {
			throw new RuntimeException("Erro no arquivo", e);
		}
	}
	
	public RetornoMercadoPagoDto buscarMovimentacaoPorId(Long id) {
		try {
			Map<String, Object> filters = new HashMap<String, Object> ();
			filters.put("id", id);
			
			JSONObject searchResult = mp.searchPayment (filters);
			Gson gson = MercadoPagoGsonHelper.buildGson();
			RetornoMercadoPagoDto dto = gson.fromJson(searchResult.toString(), RetornoMercadoPagoDto.class);
			return dto;
		} catch(Exception e) {
			throw new RuntimeException("Erro na pesquisa no MP", e);
		}
	}
	
	public void run() {
		try {
			Busca busca = new Busca();
			System.out.println("Id do Pedido\tData de aprovação\tId MP\tForma de pagamento\tStatus");
			List<Long> ids = listarIds("D:\\desenvolvimento\\arquivos\\codigo-transacao.txt");
			for (Long id : ids) {
				RetornoMercadoPagoDto dto = busca.buscarMovimentacaoPorId(id);
				imprimir(dto);
			}
		} catch(Exception e) {
			throw new RuntimeException("Erro na excução", e);
		}
	}
	
	public void imprimir(RetornoMercadoPagoDto dto) {
		List<ResultsBuscaMercadoPagoDto> list = dto.getResponse().getResults();
		for (ResultsBuscaMercadoPagoDto resultsBuscaMercadoPagoDto : list) {
			CollectionMercadoPagoDto result = resultsBuscaMercadoPagoDto.getCollection();
			StringBuilder builder = new StringBuilder();
			builder.append(result.getExternalReference()).append("\t");
			builder.append(formatDate(result.getDateApproved())).append("\t");
			builder.append(result.getId()).append("\t");
			builder.append(result.getPaymentMethod().getNome()).append("\t");
			builder.append(result.getStatus().getStatusPedido().getDescricao());
			
			System.out.println(builder.toString());
		}
	}
	
	public void gravarParaArquivo() {
		List<Long> ids = listarIds("D:\\desenvolvimento\\arquivos\\codigo-transacao.txt");
		List<RetornoMercadoPagoDto> dtos = new ArrayList<RetornoMercadoPagoDto>();
		Busca busca = new Busca();
		for (Long id : ids) {
			System.out.print("Buscando id = " + id);
			RetornoMercadoPagoDto dto = busca.buscarMovimentacaoPorId(id);
			dtos.add(dto);
			System.out.println(" [ok]");
		}
		gravacao(dtos);
	}
	
	private void gravacao(List<RetornoMercadoPagoDto> list) {
		try {
			OutputStream fout = new FileOutputStream(this.path);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			for (RetornoMercadoPagoDto retornoMercadoPagoDto : list) {
				oos.writeObject(retornoMercadoPagoDto);
				oos.flush();
			}
			oos.close();
			fout.close();
		} catch (IOException e) {
			throw new RuntimeException("Erro ao gravar no arquivo", e);
		}
	}
	
	public List<RetornoMercadoPagoDto> listarDoArquivo() {
		try {
			InputStream fout = new FileInputStream(this.path);
			ObjectInputStream stream = new ObjectInputStream(fout);
			List<RetornoMercadoPagoDto> dtos = new ArrayList<RetornoMercadoPagoDto>();
			while (true) {
				try {
					RetornoMercadoPagoDto dto = (RetornoMercadoPagoDto) stream.readObject();
					dtos.add(dto);
				} catch(EOFException e) {
					stream.close();
					fout.close();
					return dtos;
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			throw new RuntimeException("Erro ao ler arquivo", e);
		}
	}

	public void teste() {
		List<RetornoMercadoPagoDto> dtos = new ArrayList<RetornoMercadoPagoDto>();
		for (int i = 0; i < 10; i++) {
			RetornoMercadoPagoDto dto = new RetornoMercadoPagoDto();
			dto.setStatus(i);
			dtos.add(dto);
		}
		gravacao(dtos);
	}
	
	private static String formatDate(Date date) {
		return new SimpleDateFormat("dd/MM/YYYY").format(date);
	}
	
	public void imprimirIdMP(List<RetornoMercadoPagoDto> dtos) {
		for (RetornoMercadoPagoDto dto : dtos) {
			List<ResultsBuscaMercadoPagoDto> list = dto.getResponse().getResults();
			if(!Util.vazio(list)) {
				System.out.println(list.get(0).getCollection().getId());
			}
		}
	}
	
	public void intersecaoCodigoTrancacao() {
		List<Long> todos = listarIds("D:\\desenvolvimento\\arquivos\\codigo-transacao.txt");
		List<Long> validados = listarIds("D:\\desenvolvimento\\arquivos\\codigo-transacao-validado.txt");
		List<Long> intersecao = todos.stream().filter(l -> !validados.contains(l)).collect(Collectors.toList());
		List<RetornoMercadoPagoDto> dtos = new ArrayList<RetornoMercadoPagoDto>();
		System.out.println(dtos);
		intersecao.forEach(l -> System.out.println((buscarMovimentacaoPorId(l)).getResponse().getResults().size()));
		//dtos.stream().forEach(dto -> System.out.println(dto.getResponse().getResults().size()));
		
	}
	
	public void transacaoPlanilhaVitor() {
		List<Long> ids = listarIds("D:\\desenvolvimento\\arquivos\\codigo-transacao-planilha-vitor.txt");
		for (Long id : ids) {
			StringBuilder builder = new StringBuilder();
			builder.append(id);
			RetornoMercadoPagoDto dto = buscarMovimentacaoPorId(id);
			if(isResults(dto)) {
				CollectionMercadoPagoDto collectionMercadoPagoDto = dto.getResponse().getResults().get(0).getCollection();
				builder.append("\t").append(collectionMercadoPagoDto.getStatus().getStatusPedido().getDescricao());
				builder.append("\t").append(collectionMercadoPagoDto.getStatusDetail().getDescricao());
			}
			System.out.println(builder.toString());
		}
	}
	
	public void transacoesCanceladas() {
		Long[] ids = {1737109019L, 1760137975L, 1761638528L};
		for (Long id : ids) {
			RetornoMercadoPagoDto dto = buscarMovimentacaoPorId(id);
			if(isResults(dto)) {
				CollectionMercadoPagoDto mp = dto.getResponse().getCollection();
				if(mp != null) {
					System.out.println("Pedido: " + mp.getExternalReference());
					System.out.println("Id MP: " + mp.getId());
					System.out.println("Money Release Date: " + mp.getMoneyReleaseDate());
					System.out.println("Date Approved: " + mp.getDateApproved());
					System.out.println("Date Created: " + mp.getDateCreated());
				}
			}
		}
	}
	
	public boolean isResults(RetornoMercadoPagoDto dto) {
		return dto != null && dto.getResponse() != null && !Util.vazio(dto.getResponse().getResults());
	}
	
	public void descontoMercadoPago() {
		try {
			String accessToken = mp.getAccessToken();
			String valor = "29.90";
			String email = "jaoleite@yahoo.com.br";
			StringBuilder builder = new StringBuilder();
			
			builder.append("/discount_campaigns?access_token=");
			builder.append(accessToken).append("&transaction_amount=").append(valor);
			builder.append("&email=").append(email);
			
			JSONObject jsonObject = mp.get(builder.toString());
			
			System.out.println(jsonObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		//System.out.println("Início");
		//System.out.println("Id do Pedido\tData de aprovação\tId MP\tForma de pagamento\tStatus");
		Busca busca = new Busca();
		busca.descontoMercadoPago();
		//List<RetornoMercadoPagoDto> dtos = busca.listarDoArquivo();
		//busca.imprimirIdMP(dtos);
		//busca.gravarParaArquivo();
		//System.out.println("\nFim");
		
		//busca.intersecaoCodigoTrancacao();
		//busca.transacaoPlanilhaVitor();
		//busca.transacoesCanceladas();
		
		//RetornoMercadoPagoDto dto = busca.buscarMovimentacaoPorId(1755082567L);
		//System.out.println(dto);
		//System.out.println(dto.getResponse().getCollection());
	}
	

}
