package br.com.tecconcursos.mercadopago;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import br.com.tecconcursos.to.mercadopago.CollectionMercadoPagoDto;
import br.com.tecconcursos.to.mercadopago.RetornoMercadoPagoDto;
import br.com.tecconcursos.util.MercadoPagoProperties;
import br.com.tecconcursos.util.gson.MercadoPagoGsonHelper;

import com.google.gson.Gson;
import com.mercadopago.MP;

public class NovaBusca {

	public NovaBusca() {
	}

public static void main(String[] args) {
		
		try {
			Pagina pagina = new Pagina();
			RetornoMercadoPagoDto dto = buscarRetornoMercadoPagoPorFiltro(pagina);
			int total = dto.getResponse().getPaging().getTotal();
			System.out.println(total);
			trartarRetornoMP(dto);
			
			/*while(pagina.offset < total) {
				trartarRetornoMP(dto);
				pagina.changeOffset();
				dto = buscarRetornoMercadoPagoPorFiltro(pagina);
			}*/
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	protected static void trartarRetornoMP(RetornoMercadoPagoDto dto) {
		dto.getResponse().getResults().stream().forEach(p -> {
			CollectionMercadoPagoDto mp = p.getCollection();
			impressao(mp);
		});
	}

	protected static void impressao(CollectionMercadoPagoDto mp) {
		impressaoNaTela(mp);
		//impressaoParaSql(mp);
	}
	
	protected static void impressaoNaTela(CollectionMercadoPagoDto mp) {
		System.out.println();
		System.out.print(mp.getExternalReference() + "\t");
		System.out.print(mp.getId() + "\t");
		System.out.println(mp.getPayer());
	}
	
	protected static void impressaoParaSql(CollectionMercadoPagoDto mp) {
		System.out.print(mp.getExternalReference() + ", ");
	}

	protected static RetornoMercadoPagoDto buscarRetornoMercadoPagoPorFiltro(Pagina pagina) throws JSONException, Exception {
		String clientId = MercadoPagoProperties.clientId();
		String clientSecret = MercadoPagoProperties.clientSecret();
		
		MP mp = new MP (clientId, clientSecret);
		Map<String, Object> filters = new HashMap<String, Object> ();
		filters.put("range", "date_created");
		filters.put("begin_date", "NOW-4DAY");
		filters.put("end_date", "NOW");
		filters.put("status", "pending");
		filters.put("payment_type", "ticket");
		
		JSONObject searchResult = mp.searchPayment(filters, pagina.offset, pagina.limit);
		Gson gson = MercadoPagoGsonHelper.buildGson();
		RetornoMercadoPagoDto dto = gson.fromJson(searchResult.toString(), RetornoMercadoPagoDto.class);
		
		return dto;
	}
	
}
