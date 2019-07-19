package br.com.tecconcursos.nfe.rps;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import br.com.tecconcursos.context.ApplicationContext;
import br.com.tecconcursos.entity.NotaFiscal;
import br.com.tecconcursos.entity.NotaFiscalLoteRps;
import br.com.tecconcursos.entity.enumeration.NotaFiscalTipoPrefeituraEnum;
import br.com.tecconcursos.nfe.rps.NfeBarueriRpsTemplate;
import br.com.tecconcursos.nfe.rps.RpsHelper;
import br.com.tecconcursos.service.GerenciamentoNotaFiscalService;
import br.com.tecconcursos.service.RpsBarueriService;
import br.com.tecconcursos.to.nfe.TomadorDto;
import br.com.tecconcursos.to.nfe.barueri.CabecalhoRpsBarueri;
import br.com.tecconcursos.to.nfe.barueri.DetalheRpsBarueri;
import br.com.tecconcursos.to.nfe.barueri.RetornoRpsBarueri;
import br.com.tecconcursos.to.nfe.barueri.RodapeRpsBarueri;
import br.com.tecconcursos.to.nfe.barueri.RpsBarueri;
import br.com.tecconcursos.to.nfe.barueri.TomadorRpsBarueri;
import br.com.tecconcursos.to.nfe.barueri.enumeration.CodigoMotivoCancelamentoRpsEnum;
import br.com.tecconcursos.to.nfe.barueri.enumeration.LocalServicoPrestadoRpsEnum;
import br.com.tecconcursos.to.nfe.barueri.enumeration.ServicoPrestadoViasPublicasRpsEnum;
import br.com.tecconcursos.to.nfe.barueri.enumeration.SituacaoRpsEnum;
import br.com.tecconcursos.to.nfe.barueri.enumeration.TipoTomadorRpsEnum;
import br.com.tecconcursos.util.DataUtil;
import br.com.tecconcursos.util.NotaFiscalPrefeituraProperties;
import br.com.tecconcursos.util.SubList;
import br.com.tecconcursos.util.Util;

public class TestesRps {
	
	
	public void gerarVariosRps() {
		List<TomadorDto> tomadores = listarTomadoresDoArquivo();
		int divisao = 10;
		int quantidade = tomadores.size() / divisao;
		
		String templateNomeArquivo = "d:\\rps\\rps-%d.txt";
		String templateValores = "d:\\rps\\valores-%d.txt";
		for (int i = 0; i < divisao ; i++) {
			int remessa = (i + 1);
			String nomeArquivo = String.format(templateNomeArquivo, remessa);
			int fromIndex = quantidade * i;
			int toIndex = fromIndex + quantidade;
			List<TomadorDto> dtos = new ArrayList<>(tomadores.subList(fromIndex, toIndex));
			gerarMaisSalvarRps(dtos, nomeArquivo, remessa);
			lancarValores(dtos, String.format(templateValores, remessa));
		}
	}
	
	private void lancarValores(List<TomadorDto> dtos, String arquivo) {
		RodapeRpsBarueri rodape = RodapeRpsBarueri.calcularValores(dtos, true);
		StringBuilder builder = new StringBuilder();
		for (TomadorDto tomador : dtos) {
			Double value = tomador.getValorServico();
			String st = value.toString().replaceAll("\\.", ",");
			builder.append(st).append("\n");
		}
		Long value = rodape.getValorTotalServicos();
		String st = value.toString().replaceAll("\\.", ",");
		builder.append(st);
		salvarNoArquivo(builder.toString(), arquivo);
	}
	
	public void gerarMaisSalvarRps(List<TomadorDto> tomadores, String nomeArquivo, int remessa) {
		String resultado = null;
		if(!Util.vazio(tomadores)) {
			NotaFiscalTipoPrefeituraEnum tipoPrefeituraEnum = geTipoPrefeituraEnum();
			CabecalhoRpsBarueri cabecalho = new CabecalhoRpsBarueri(remessa, tipoPrefeituraEnum);
			//Para zerar as retenções o Rodapé vem antes da montagem dos detalhes
			RodapeRpsBarueri rodape = RodapeRpsBarueri.calcularValores(tomadores, true);
			List<DetalheRpsBarueri> detalhes = montarDetalhes(tomadores, tipoPrefeituraEnum, SituacaoRpsEnum.E);
			rodape.setQuantidadeLinhas(calcularQuantidadeDeLinhas(detalhes));
			RpsBarueri rps = new RpsBarueri(cabecalho, detalhes, rodape);
			
			NfeBarueriRpsTemplate template = new NfeBarueriRpsTemplate(rps);
			resultado = template.getResultadoRps();
			salvarNoArquivo(resultado, nomeArquivo);
		}
	}
	
	private List<DetalheRpsBarueri> montarDetalhes(List<TomadorDto> tomadores, NotaFiscalTipoPrefeituraEnum tipo, SituacaoRpsEnum situacao) {
		List<DetalheRpsBarueri> detalhes = new ArrayList<>();
		for (TomadorDto tomador: tomadores) {
			DetalheRpsBarueri detalhe = 
					new DetalheRpsBarueri(tomador.getNumeroRps(), tomador.getValorServico(), tomador.getValorDeducao());
			
			detalhe.setDataDoRps(tomador.getDataEmissao());
			detalhe.setSituacao(situacao);
			detalhe.setDescricaoCancelamento("");
			detalhe.setCodigoServicoPrestado(tipo.getCodigoServico());
			detalhe.setQuantidadeDeServico(DetalheRpsBarueri.QUANTIDADE_PRODUTO);
			detalhe.setDiscriminacaoServico(tipo.montarValorAproximadoDosTributosNaDiscriminacao(tomador.getValorServico()));
			
			detalhe.setTipoTomador(TipoTomadorRpsEnum.BRASILEIRO);
			detalhe.setMotivoCancelamento(CodigoMotivoCancelamentoRpsEnum.NENHUM);
			detalhe.setLocalPrestacaoServico(LocalServicoPrestadoRpsEnum.NENHUM);
			detalhe.setServicoEmViasPublicas(ServicoPrestadoViasPublicasRpsEnum.NENHUM);
			detalhe.setTomador(new TomadorRpsBarueri(tomador));
			detalhe.setTipoTomador(TipoTomadorRpsEnum.BRASILEIRO);
			
			detalhes.add(detalhe);
		}
		return detalhes;
	}

	
	public void salvarNoArquivo(String rps, String nomeArquivo) {
		try {
			FileOutputStream stream = new FileOutputStream(new File(nomeArquivo));
			IOUtils.write(rps, stream);
			stream.flush();
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	private List<TomadorDto> listarTomadoresDoArquivo() {
		try {
			List<String> list = IOUtils.readLines(new FileInputStream(new File("d:\\rps-pedidos.json")));
			String json = list.get(0);
			Type listType = new TypeToken<List<TomadorDto>>(){}.getType();
			List<TomadorDto> tomadores = new Gson().fromJson(json, listType);
			return tomadores;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private int calcularQuantidadeDeLinhas(List<DetalheRpsBarueri> detalhes) {
		short linhasCabecalhoMaisRodape = 2;
		int quantidadeLinhasDetalheLancamento = 0;
		for (DetalheRpsBarueri detalhe : detalhes) {
			if(detalhe.getDetalheLancamento() != null) {
				quantidadeLinhasDetalheLancamento++;
			}
		}
		
		return detalhes.size() + linhasCabecalhoMaisRodape + quantidadeLinhasDetalheLancamento;
	}
	

	public static void main(String[] args) {
		teste1(2018);
		//teste2();
		//teste3();
		//teste4();
		//teste5();
		//teste06();
		//teste07();
		//teste08();
		//teste10();
		
		System.out.println();
	}
	
	public static void teste10() {
		GerenciamentoNotaFiscalService service = ApplicationContext.find(GerenciamentoNotaFiscalService.class);
		NotaFiscalTipoPrefeituraEnum tipo = geTipoPrefeituraEnum();
		List<NotaFiscalLoteRps> list = service.listarRpsPorPrefeituraMaisData(tipo, null, null);
		for (NotaFiscalLoteRps notaFiscalLoteRps : list) {
			System.out.println(notaFiscalLoteRps.getId());
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void teste09() {
		String arquivo = "C:\\Users\\jaoin\\Downloads\\RET485835735420180512190760.TXT";
		try {
			List<String> linhas = IOUtils.readLines(new FileInputStream(new File(arquivo)));
			for (String linha : linhas) {
				if(RetornoRpsBarueri.isLinhaValida(linha)) {
					System.out.println(new RetornoRpsBarueri(linha));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void teste06() {
		System.out.println("inicio");
		RpsSerivceTeste service = new RpsSerivceTeste();
		service.gerarVariosRps();
		System.out.println("fim");
	}
	
	public static void teste08() {
		RpsSerivceTeste service = new RpsSerivceTeste();
		TomadorDto tomador = service.buscarDoArquivoTomadorPorRps(697);
		System.out.println(tomador.getEndereco());
	}
	
	@SuppressWarnings("unchecked")
	public static void teste07() {
		try {
			for (int i = 1; i <=10; i++) {
				String t = "d:\\rps\\rps-%d.txt";
				String file = String.format(t, i);
				List<String> list = IOUtils.readLines(new FileInputStream(new File(file)));
				int soma = 0;
				for (int j = 1; j < list.size() - 1; j++) {
					String linha = list.get(j);
					String value = linha.substring(463, 478);
					Integer integer = new Integer(value);
					soma += integer;
				}
				System.out.println(soma);
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void teste5() {
		try {
			List<String> list = IOUtils.readLines(new FileInputStream(new File("d:\\rps-pedidos.json")));
			String json = list.get(0);
			Type listType = new TypeToken<List<TomadorDto>>(){}.getType();
			List<TomadorDto> tomadores = new Gson().fromJson(json, listType);
			int divisao = 10;
			int a = tomadores.size() / divisao;
			
			Map<Integer, List<TomadorDto>> map = new HashMap<>();
			for (int i = 0; i < divisao ; i++) {
				int fromIndex = a * i;
				int toIndex = fromIndex + a;
				List<TomadorDto> dtos = new ArrayList<>(tomadores.subList(fromIndex, toIndex));
				map.put(i, dtos);
			}
			
			Set<Integer> set = map.keySet();
			for (Integer integer : set) {
				System.out.println(integer + ": " + map.get(integer).get(0).getCpfTomador());
			}
			
			System.out.println("fim");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void teste4() {
		try {
			List<String> list = IOUtils.readLines(new FileInputStream(new File("d:\\rps-pedidos-2018-05-17.json")));
			String json = list.get(0);
			Type listType = new TypeToken<List<TomadorDto>>(){}.getType();
			List<TomadorDto> tomadores = new Gson().fromJson(json, listType);
			System.out.println(tomadores.size());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void teste3() {
		try {
			List<String> lines = IOUtils.readLines(new FileInputStream(new File("d:\\teste.txt")));
			String line = lines.get(0);
			String cpf = line.substring(504, 518);
			System.out.println(cpf);
			System.out.println(line.length());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void teste2() {
		long codigo = 15;
		RpsBarueriService service = ApplicationContext.find(RpsBarueriService.class);
		NotaFiscalLoteRps loteRps = service.buscarLotePorCodigo(codigo);
		List<NotaFiscal> notas = loteRps.getNotasFiscais();
		for (NotaFiscal notaFiscal : notas) {
			System.out.println(notaFiscal.getNumeroRps());
		}
		
	}
	
	public static void teste1(int ano) {
		int diaDoMes = 1;
		int mes = 5;
		Date dataInicial = DataUtil.data(diaDoMes, mes, ano);
		RpsBarueriService service = ApplicationContext.find(RpsBarueriService.class);
		service.gerarRpsParaEnvio(dataInicial);
	}
	
	public static NotaFiscalTipoPrefeituraEnum geTipoPrefeituraEnum() {
		return NotaFiscalPrefeituraProperties.of().getGerador();
	}
	
	// =====================================================================
	
	public static class RpsSerivceTeste {
		public void salvarNoArquivo(String rps, String nomeArquivo) {
			try {
				FileOutputStream stream = new FileOutputStream(new File(nomeArquivo));
				IOUtils.write(rps, stream);
				stream.flush();
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	public TomadorDto buscarDoArquivoTomadorPorRps(long rps) {
			List<TomadorDto> tomadores = listarTomadoresDoArquivo();
			for (TomadorDto tomador : tomadores) {
				if(tomador.getNumeroRps().longValue() == rps) {
					return tomador;
				}
			}
			return null;
		}
		
		public void gerarVariosRps() {
			List<TomadorDto> tomadores = listarTomadoresDoArquivo();
			SubList<TomadorDto> subList = new SubList<>(tomadores);
			Map<Integer, List<TomadorDto>> map = subList.sub();
			Set<Integer> integers = map.keySet();
			String templateNomeArquivo = "d:\\rps\\rps-%d.txt";
			
			for (Integer key : integers) {
				List<TomadorDto> dtos = map.get(key);
				int remessa = (key + 1);
				String nomeArquivo = String.format(templateNomeArquivo, remessa);
				gerarMaisSalvarRps(dtos, nomeArquivo, remessa);
			}
		}
		
		public void gerarMaisSalvarRps(List<TomadorDto> tomadores, String nomeArquivo, int remessa) {
			String resultado = null;
			if(!Util.vazio(tomadores)) {
				NotaFiscalTipoPrefeituraEnum tipoPrefeituraEnum = geTipoPrefeituraEnum();
				CabecalhoRpsBarueri cabecalho = new CabecalhoRpsBarueri(remessa, tipoPrefeituraEnum);
				//Para zerar as retenções o Rodapé vem antes da montagem dos detalhes
				RodapeRpsBarueri rodape = RodapeRpsBarueri.calcularValores(tomadores, true);
				List<DetalheRpsBarueri> detalhes = montarDetalhes(tomadores, tipoPrefeituraEnum, SituacaoRpsEnum.E);
				RpsBarueri rps = new RpsBarueri(cabecalho, detalhes, rodape);
				
				NfeBarueriRpsTemplate template = new NfeBarueriRpsTemplate(rps);
				resultado = template.getResultadoRps();
				salvarNoArquivo(resultado, nomeArquivo);
			}
		}
		
		@SuppressWarnings("unchecked")
		private List<TomadorDto> listarTomadoresDoArquivo() {
			try {
				String template = "d:\\rps-pedidos-%s.json";
				String file = String.format(template, "2018-05-17");
				List<String> list = IOUtils.readLines(new FileInputStream(new File(file)));
				String json = list.get(0);
				Type listType = new TypeToken<List<TomadorDto>>(){}.getType();
				List<TomadorDto> tomadores = new Gson().fromJson(json, listType);
				RpsHelper.safeTomadores(tomadores);
				return tomadores;
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		
		private List<DetalheRpsBarueri> montarDetalhes(List<TomadorDto> tomadores, NotaFiscalTipoPrefeituraEnum tipo, SituacaoRpsEnum situacao) {
			RpsBarueriService service = new RpsBarueriService();
			return service.montarDetalhes(tomadores, tipo, situacao);
		}
		
	}

}
