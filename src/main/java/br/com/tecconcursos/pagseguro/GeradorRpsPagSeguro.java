package br.com.tecconcursos.pagseguro;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;

import br.com.tecconcursos.entity.enumeration.NotaFiscalTipoPrefeituraEnum;
import br.com.tecconcursos.nfe.rps.NfeBarueriRpsTemplate;
import br.com.tecconcursos.nfe.rps.RpsHelper;
import br.com.tecconcursos.to.nfe.TomadorDto;
import br.com.tecconcursos.to.nfe.barueri.CabecalhoRpsBarueri;
import br.com.tecconcursos.to.nfe.barueri.DetalheRpsBarueri;
import br.com.tecconcursos.to.nfe.barueri.RodapeRpsBarueri;
import br.com.tecconcursos.to.nfe.barueri.RpsBarueri;
import br.com.tecconcursos.to.nfe.barueri.TomadorRpsBarueri;
import br.com.tecconcursos.to.nfe.barueri.enumeration.CodigoMotivoCancelamentoRpsEnum;
import br.com.tecconcursos.to.nfe.barueri.enumeration.LocalServicoPrestadoRpsEnum;
import br.com.tecconcursos.to.nfe.barueri.enumeration.ServicoPrestadoViasPublicasRpsEnum;
import br.com.tecconcursos.to.nfe.barueri.enumeration.SituacaoRpsEnum;
import br.com.tecconcursos.to.nfe.barueri.enumeration.TipoTomadorRpsEnum;
import br.com.tecconcursos.util.NotaFiscalPrefeituraProperties;
import br.com.tecconcursos.util.SubList;
import br.com.tecconcursos.util.Util;

public class GeradorRpsPagSeguro {

	private static final int MAXIMO_LOTE_INICIAL = 700;
	
	
	
	public static void main(String[] args) {
		System.out.println("Inicio da geração");
		new GeradorRpsPagSeguro().gerarRpsParaEnvio();
		System.out.println("Fim da geração");
	}
	
	public GeradorRpsPagSeguro() {
		
	}

	public void gerarRpsParaEnvio() {
		List<TomadorDto> tomadores = listarTomadores();
		if(!Util.vazio(tomadores)) {
			RpsHelper.safeTomadores(tomadores);
			NotaFiscalTipoPrefeituraEnum tipoPrefeituraEnum = NotaFiscalPrefeituraProperties.of().getGerador();
			// Até um tamanho máximo (estipulei 700, pois dá pouco mais de 1MB o tamanho do arquivo e a prefeitura permite fazer upload)
			// a geração será feita em UM lote apenas. Após esse tamanho o lote começa a ser quebrado de acordo com o algoritmo 
			// que está na classe SubList
			if(tomadores.size() > MAXIMO_LOTE_INICIAL) {
				SubList<TomadorDto> subList = new SubList<>(tomadores);
				Map<Integer, List<TomadorDto>> map = subList.sub();
				Set<Integer> set = map.keySet();
				for (Integer key : set) {
					List<TomadorDto> dtos = map.get(key);
					geracao(dtos, tipoPrefeituraEnum);
				}
			} else {
				geracao(tomadores, tipoPrefeituraEnum);
			}
		}
	}
	
	private void geracao(List<TomadorDto> tomadores, NotaFiscalTipoPrefeituraEnum tipoPrefeituraEnum) {
		if(!Util.vazio(tomadores)) {
			try {
				CabecalhoRpsBarueri cabecalho = new CabecalhoRpsBarueri(geradorRemessa(), tipoPrefeituraEnum);
				//Para zerar as retenções o Rodapé vem antes da montagem dos detalhes
				RodapeRpsBarueri rodape = RodapeRpsBarueri.calcularValores(tomadores, true);
				List<DetalheRpsBarueri> detalhes = montarDetalhes(tomadores, tipoPrefeituraEnum, SituacaoRpsEnum.E);
				RpsBarueri rps = new RpsBarueri(cabecalho, detalhes, rodape);
				
				NfeBarueriRpsTemplate template = new NfeBarueriRpsTemplate(rps);
				String resultado = template.getResultadoRps();
				String file = gerarNomeArquivoRps();
				salvarRPS(resultado, file);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private String gerarNomeArquivoRps() {
		String caminho = NotaFiscalPrefeituraProperties.of().getCaminhoRps();
		String template =  caminho + File.separator + "rps" + File.separator + "rps-%s.txt";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		return String.format(template, dateFormat.format(new Date()));
	}
	
	private void salvarRPS(String rps, String file) {
		try {
			FileOutputStream stream = new FileOutputStream(new File(file));
			IOUtils.write(rps, stream);
			IOUtils.closeQuietly(stream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<DetalheRpsBarueri> montarDetalhes(List<TomadorDto> tomadores, NotaFiscalTipoPrefeituraEnum tipo, SituacaoRpsEnum situacao) {
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
	
	private int geradorRemessa() {
		return 1;
	}
	
	public List<TomadorDto> listarTomadores() {
		/*String file = "D:\\PagSeguro_2018-06-04_06-12-39.txt";
		Consulta consulta = new Consulta(file);
		consulta.listarTomadores();
		List<TomadorDto> tomadores = consulta.getTomadores();*/
		
		return null;
	}

}
