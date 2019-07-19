package br.com.tecconcursos.pagseguro;

public class Consulta {

	/*private String codigo = null;
	private String fileName;
	private List<TomadorDto> tomadores;
	private TomadorService tomadorService;*/
	
	public Consulta() {
		//this.codigo = "E6182D12-6EC4-483E-BBC3-DB5529A213B2";
	}
	
	/*public Consulta(String fileName) {
		super();
		this.fileName = fileName;
		this.tomadores = new ArrayList<>();
		tomadorService = ApplicationContext.find(TomadorService.class);
		// D:\\PagSeguro_2018-06-04_06-12-39.txt
	}

	public void listarTomadores() {
		try {
			File file = new File(this.fileName);
			List<String> codigos = ListagemTransaction.listarCodigosDeTransacoes(file);
			for (String codigo : codigos) {
				this.codigo = codigo;
				addTomador();
			}
		} catch (PagSeguroServiceException e) {
			e.printStackTrace();
		}
	}
	
	public void addTomador() throws PagSeguroServiceException {
		Transaction transaction = buscarTransacaoPorCodigo(this.codigo);
		TomadorHelper helper = new TomadorHelper(transaction);
		TomadorDto tomador = helper.tranactionToTomador();
		this.tomadores.add(tomador);
	}
	
	public List<TomadorDto> getTomadores() {
		return this.tomadores;
	}
	
	public Transaction buscarTransacaoPorCodigo(String codigo) throws PagSeguroServiceException {
		AccountCredentials accountCredentials = getAccountCredentials();
		System.out.println("Lendo transação: " + codigo);
		Transaction result = TransactionSearchService.searchByCode(accountCredentials, codigo);
		
		System.out.println("fim da leitura...");
		return result;
	}
	
	private AccountCredentials getAccountCredentials() throws PagSeguroServiceException {
		AccountCredentials accountCredentials = AmbientePagSeguroEnum.PRODUCAO.accountCredentials();
		return accountCredentials;
	}
	
	public static void main(String[] args) {
		//new Consulta().listarTomadores();
		
	}
	
	class TomadorHelper {
		
		private Transaction transaction;
		private Address address;
		private Sender sender;
		private Item item;
		
		public TomadorHelper(Transaction transaction) {
			super();
			this.transaction = transaction;
			if(isAddress()) {
				this.address = this.transaction.getShipping().getAddress();
			}
			if(isSender()) {
				this.sender = this.transaction.getSender();
			}
			if(isItem()) {
				this.item = this.transaction.getItems().get(0);
			}
		}

		public TomadorDto tranactionToTomador() {
			transaction.getItems().get(0).getAmount();
			Cidade cidade = addressTransactionToCidade();
			Endereco endereco = addressTransactionToEndereco();
			
			TomadorDto dto = new TomadorDto();
			Long rps = tomadorService.nextSequence();
			dto.setNumeroRps(rps);
			dto.setCidade(cidade);
			dto.setEndereco(endereco);
			dto.setCpfTomador("");
			dto.setEmail(this.sender.getEmail());
			dto.setDataEmissao(new Date());
			dto.setNomeTomador(senderToNome());
			dto.setSobrenomeTomador("");
			dto.setValorDeducao(0.0);
			dto.setValorServico(amountTransactionToValorServico());

			return dto;
		}
		
		public Cidade addressTransactionToCidade() {
			Cidade cidade = new Cidade();
			String nome = this.address.getCity();
			String codigoEstado = "SP";
			if(!Util.vazio(this.address.getState())) {
				codigoEstado = this.address.getState().toUpperCase();
			}
			EstadoEnum estado = EstadoEnum.valueOf(EstadoEnum .class, codigoEstado);
			cidade.setNome(nome);
			cidade.setEstado(estado);
			return cidade;
		}
		
		public Endereco addressTransactionToEndereco() {
			Endereco endereco = new Endereco();
			String complemento = this.address.getComplement();
			String bairro = this.address.getDistrict();
			String cep = this.address.getPostalCode();
			String logradouro = this.address.getStreet();
			String numero = this.address.getNumber();
			
			endereco.setBairro(bairro);
			endereco.setCep(cep);
			endereco.setComplemento(complemento);
			endereco.setLogradouro(logradouro);
			endereco.setNumero(numero);
			return endereco;
		}
		
		public String senderToNome() {
			if(isSender() && !Util.vazio(this.sender.getName())) {
				return this.sender.getName();
			}
			return "";
		}
		
		public Double amountTransactionToValorServico() {
			BigDecimal amount = this.item.getAmount();
			if(amount != null) {
				return amount.doubleValue();
			}
			return 0.0;
		}
		
		private boolean isShipping() {
			return transaction.getShipping() != null;
		}
		
		private boolean isAddress() {
			return isShipping() && transaction.getShipping().getAddress() != null;
		}
		
		private boolean isSender() {
			return transaction.getSender() != null;
		}
		
		private boolean isItem() {
			return !Util.vazio(this.transaction.getItems());
		}
	}*/

}
