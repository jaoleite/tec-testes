package br.com.tecconcursos.assinatura;

public class AssinaturaTeste {

	/*private static final Long ID_PEDIDO = 170850L;
	private static final String CPF = "78250604075";
	private static final String API_KEY = "ak_live_VBAGJI31Mt3dwi1OLvNkkw5xd57tyV";*/
	
	/*private CompraService compraService;
	private MapperFacadeService mapperFacadeService;
	private MapperFacade mapper;
	
	public AssinaturaTeste() {
		compraService = ApplicationContext.find(CompraService.class);
		mapperFacadeService = ApplicationContext.find(MapperFacadeService.class);
		mapper = mapperFacadeService.mapper;
	}
	
	public void testes() {
		Pedido pedido = compraService.buscarPedidoPorCodigo(ID_PEDIDO);
		double valor = pedido.calcularValor();
		System.out.println(valor);
		System.out.println(FormatHelper.transformarDoubleParaInteger(valor));
	}
	
	public static void main(String[] args) throws Exception {
		new AssinaturaTeste().testeTransacaoComCartao();
	}
	
	public void testeTransacaoComCartao() throws PagarMeException {
		PagarMe.init(API_KEY);
		
		Pedido pedido = compraService.buscarPedidoPorCodigoComItens(ID_PEDIDO);
		pedido.getUsuarioSite().buildTelefone();
		
		Customer customer = getCustomer(pedido);
		
		Map<String, Object> metadata = new HashMap<String, Object>();
		metadata.put("idPedido", pedido.getId());
		
		Card card = getCard(customer);
		Integer amount = FormatHelper.transformarDoubleParaInteger(pedido.calcularValor());
		
		//String cardHash = "2629840_MSmEd1+TjmhsYFTuNdS7uTlMFpnDGOlOb1GFNdumhxakhyEqC8onOp3dS01FT/3LzqfccFgDqlsB7s2lpcpyfxfuh/ouYgJHpWKRt+KnZLI1XJeCQoLzEkMZ8I1AKsRTDFlExINFQJL11C7crvIN3lMTWzd5Dr4OztZJE1y3Vn+QyIUsCFb/+LlWYGwLXYNwm2i+ZBGi2krk3XZP3pCcwp+dL4+CM1DTl4Uegfk/oE2YgUZphHSGiUfFKq3XADVyi9e5s9H0us35M/9VjwpsRGWrr/nWsCduP3rrVDK7Uh950DFxG7IffGxZilpxd+Fy34jhcJtGopnjZrn0e3L4vA==";
		Transaction tx = new Transaction();
		tx.setCustomer(customer);
		//tx.setCardHash(cardHash);
		tx.setAmount(amount);
		tx.setCardId(card.getId());
		tx.setPaymentMethod(PaymentMethod.CREDIT_CARD);
		tx.setMetadata(metadata);
		System.out.println(tx.getAmount());
		//tx.setPostbackUrl("http://requestb.in/pkt7pgpk");
		Transaction transaction = tx.save();
		//System.out.println(transaction);
	}
	
	public Customer getCustomer(Pedido pedido) {
		Customer customer = mapper.map(pedido, Customer.class);
		customer.setDocumentNumber(CPF);
		customer.setDocumentType("cpf");
		Address address = customer.getAddress();
		customer.setAddresses(Arrays.asList(address));
		
		Phone phone = customer.getPhone();
		customer.setPhones(Arrays.asList(phone));
		
		customer.setAddress(null);
		customer.setPhone(null);
		return customer;
	}
	
	public Card getCard(Customer customer) throws PagarMeException {
		Card card = new Card();
		card.setCustomerId(customer.getId());
		card.setNumber("4929313578111996");
		card.setCvv(320);
		card.setExpiresAt("0419");
		card.setHolderName(customer.getName());
		card.save();
		
		return card;
	}*/

}
