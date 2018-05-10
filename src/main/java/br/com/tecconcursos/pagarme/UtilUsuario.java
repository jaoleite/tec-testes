package br.com.tecconcursos.pagarme;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.IOUtils;

import br.com.tecconcursos.pagarme.enumeration.AmbienteEnum;
import br.com.tecconcursos.util.Util;
import me.pagar.SubscriptionStatus;
import me.pagar.model.Customer;
import me.pagar.model.PagarMe;
import me.pagar.model.PagarMeException;
import me.pagar.model.Subscription;

public class UtilUsuario {

	private List<UsuarioEfetivacao> list;
	private String pathToFileUsuario = "d:\\usuario-efetivacao.txt";
	private String apiKey = null;
	private AmbienteEnum ambiente = AmbienteEnum.getAmbiente();

	public static void main(String[] args) throws Exception {
		new UtilUsuario().listarUsuarios();
	}
	
	public void listarUsuarios() throws PagarMeException {
		Customer customer = new Customer();
		customer.setEmail("jaoleite@gmail.com");
		Collection<Customer> customers = customer.findCollection(10, 1);
		System.out.println(customers);
	}
	
	public UtilUsuario() {
		apiKey = ambiente.getApiKey();
		PagarMe.init(apiKey);
	}
	
	public void initPagarme() throws PagarMeException {
		System.out.println("Iniciando a PagarMe...");
		LocalTime inicio = LocalTime.now();
		
		list = listarUsuariosComProblemaEfetivcacao();
		pagarMe();
		imprime();
		
		LocalTime fim = LocalTime.now();
		LocalTime t = fim.minus(inicio.toNanoOfDay(), ChronoUnit.NANOS);
		System.out.println("Fim: " + t);
	}
	
	public UsuarioEfetivacao getUsuarioEfetivacaoByCustomer(Customer customer) {
		for (UsuarioEfetivacao usuarioEfetivacao : list) {
			if(usuarioEfetivacao.getUsuario().getEmail().equals(customer.getEmail())) {
				return usuarioEfetivacao;
			}
		}
		return null;
	}
	
	public void imprime() {
		for (UsuarioEfetivacao usuarioEfetivacao : list) {
			usuarioEfetivacao.imprime();
		}
	}
	
	public void pagarMe() throws PagarMeException {
		
		boolean condition = true;
		int pagina = 1;
		int quantidadePorPagina = 100;
		while (condition) {
			Collection<Subscription> subscriptions = new Subscription().findCollection(quantidadePorPagina, pagina);
			System.out.println("Página " + pagina);
			pagina++;
			for (Subscription sub : subscriptions) {
				Customer customer = sub.getCustomer();
				UsuarioEfetivacao efetivacao = getUsuarioEfetivacaoByCustomer(customer);
				if(sub.getStatus().equals(SubscriptionStatus.PAID) && efetivacao != null) {
					efetivacao.setSubscription(sub);
				}
			}
			if(Util.vazio(subscriptions)) {
				condition = false;
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<UsuarioEfetivacao> listarUsuariosComProblemaEfetivcacao() {
		try {
			List<String> linhas = IOUtils.readLines(new FileInputStream(new File(pathToFileUsuario)));
			List<UsuarioEfetivacao> list = new ArrayList<>();
			for (String linha : linhas) {
				list.add(new UsuarioEfetivacao(linha));
			}
			return list;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
