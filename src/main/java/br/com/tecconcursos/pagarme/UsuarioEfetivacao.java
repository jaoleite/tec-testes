package br.com.tecconcursos.pagarme;

import java.lang.reflect.Field;

import br.com.tecconcursos.entity.Assinatura;
import br.com.tecconcursos.entity.ItemPedido;
import br.com.tecconcursos.entity.Pedido;
import br.com.tecconcursos.entity.Produto;
import br.com.tecconcursos.entity.UsuarioSite;
import me.pagar.model.Subscription;
import me.pagar.model.Transaction;

public class UsuarioEfetivacao {

	private String templateUpdatePedido = "update pedido set codigotransacao = '%d', status = 'APROVADA', datatransacao = '%s' where id = %d;";
	private String templateUpdateAssinatura = "update assinatura set codigopagarme = '%s', paymentmethod = 'CREDIT_CARD', status = 'PAID', transactionid = '%d', cobrancas = 0 where id = %d;";
	private String templateInsertLiberacao = null;
	private String templateUpdatePedidoEfetivacao = "update pedidoefetivacao set efetivado = true where id = %d;";
	
	private UsuarioSite usuario;
	private Long idEfetivacao;
	private Subscription subscription;
	private ItemPedido item;
	private boolean isMock = false;
	
	
	public UsuarioEfetivacao() {
		StringBuilder builder = new StringBuilder();
		builder.append("insert into liberacao(id, expiracao, tipo, itempedido_id, produto_id, usuariosite_id, dataliberacao) ");
		builder.append("values(nextval('SeqLiberacao'), '2018-06-07', 'VENDA', %d, %d, %d, '2018-05-08 18:00:00');");
		this.templateInsertLiberacao = builder.toString();
		mockSubscription();
	}
	
	public UsuarioEfetivacao(String linha) {
		this();
		String[] st = linha.split("\t");
		idEfetivacao = new Long(st[0]);
		usuario = new UsuarioSite(new Long(st[2]));
		usuario.setEmail(st[1]);
		
		Assinatura assinatura = new Assinatura();
		assinatura.setId(new Long(st[4]));
		
		Pedido pedido = new Pedido();
		pedido.setId(new Long(st[3]));
		pedido.setAssinaturaAtual(assinatura);
		
		Produto produto = new Produto();
		produto.setId(new Long(st[5]));
		
		item = new ItemPedido(pedido);
		item.setProduto(produto);
		item.setId(new Long(st[6]));
	}
	
	public UsuarioEfetivacao(UsuarioSite usuario, Long idEfetivacao) {
		this();
		this.usuario = usuario;
		this.idEfetivacao = idEfetivacao;
	}
	public UsuarioSite getUsuario() {
		return usuario;
	}
	
	public Long getIdEfetivacao() {
		return idEfetivacao;
	}
	
	public Subscription getSubscription() {
		return subscription;
	}
	
	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}
	
	public ItemPedido getItem() {
		return item;
	}
	
	private void mockSubscription() {
		if (isMock) {
			Subscription subscription = new Subscription();
			subscription.setId("1");
			Transaction transaction = new Transaction();
			transaction.setId(1);
			this.subscription = subscription;
			addTransaction(transaction);
		}
	}
	
	private void addTransaction(Transaction transaction) {
		try {
			Field field = this.subscription.getClass().getDeclaredField("currentTransaction");
			field.setAccessible(true);
			field.set(this.subscription, transaction);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private boolean isSubscription() {
		return subscription != null;
	}
	
	public void imprime() {
		System.out.print(usuario.getEmail());
		if(isSubscription()) {
			System.out.println();
			String codigoAssinatura = subscription.getId();
			Integer codigoTransacao = subscription.getCurrentTransaction().getId();
			
			String hoje = "2018-05-08 18:00:00";
			
			String updatePedido = String.format(templateUpdatePedido, codigoTransacao, hoje, item.getPedido().getId());
			System.out.println(updatePedido);
			
			String updateAssinatura = String.format(templateUpdateAssinatura, codigoAssinatura, codigoTransacao, item.getPedido().getAssinaturaAtual().getId());
			System.out.println(updateAssinatura);
			
			String insertLiberacao = String.format(templateInsertLiberacao, item.getId(), item.getProduto().getId(), usuario.getId());
			System.out.println(insertLiberacao);
			
			String updatePedidoEfetivacao = String.format(templateUpdatePedidoEfetivacao, idEfetivacao);
			System.out.println(updatePedidoEfetivacao);
		} else {
			System.out.println(": usuário não possui assinatura no pagarme...");
		}
		System.out.println();
	}

}
