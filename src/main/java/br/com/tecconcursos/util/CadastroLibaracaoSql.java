package br.com.tecconcursos.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;

import br.com.tecconcursos.entity.enumeration.TipoLiberacaoEnum;
import br.com.tecconcursos.to.TesteDto;

public class CadastroLibaracaoSql {
	
	private String template = null;
	
	private Map<String, String> map;
	
	private Map<Long, String> m = null;
	
	public CadastroLibaracaoSql() {}
	
	private void buildTemplate() {
		StringBuilder builder = new StringBuilder();
		builder.append("insert into");
		builder.append(" liberacao (");
		builder.append("id, ");
		builder.append("dataliberacao, ");
		builder.append("expiracao, ");
		builder.append("observacao, ");
		builder.append("produto_id, ");
		builder.append("tipo, ");
		builder.append("usuariosite_id)");
		builder.append(" values (");
		builder.append("nextval('SeqLiberacao'), ");
		builder.append("'$dataLiberacao', ");
		builder.append("'$dataExpiracao', ");
		builder.append("'$observacao', ");
		builder.append("$idProduto, ");
		builder.append("'$tipo', ");
		builder.append("$idUsuarioSite);");
		
		this.template = builder.toString();
	}
	
	private void replace() {
		if(map == null || map.isEmpty()) {
			throw new RuntimeException("Não existe nada mapeado");
		}
		Set<String> set = map.keySet();
		for (String key : set) {
			template = template.replace("$" + key, map.get(key));
		}
	}
	
	@SuppressWarnings("unchecked")
	private void lerArquivo() throws FileNotFoundException, IOException {
		File file = new File("C:\\Desenvolvimento\\tec\\arquivos\\liberacao.txt");
		List<String> lines = IOUtils.readLines(new FileInputStream(file));
		m = new HashMap<Long, String>();
		for (String line : lines) {
			String[] split = line.split("\t");
			m.put(new Long(split[0]), split[1]);
		}
	}
	
	public void executar(Long idUsuario, TipoLiberacaoEnum tipo, String observacao, String dataLiberacao) {
		try {
			buildTemplate();
			lerArquivo();
			Set<Long> set = m.keySet();
			for (Long l : set) {
				TesteDto dto = new TesteDto(dataLiberacao, m.get(l), observacao, l, idUsuario, tipo);
				map = dto.toMap();
				replace();
				System.out.println(template);
				buildTemplate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		Long idUsuarioSite = 127922L;
		TipoLiberacaoEnum tipo = TipoLiberacaoEnum.TROCA;
		String observacao = "Troca de curso";
		String dataLiberacao = "2015-05-25 18:00:00";
		new CadastroLibaracaoSql().executar(idUsuarioSite, tipo, observacao, dataLiberacao);
	}

}
