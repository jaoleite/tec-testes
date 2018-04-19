package br.com.tecconcursos.easymock;

public class Processador {

	private FonteOrigem origem;
	
	private FonteDestino destino;

	public Processador(FonteOrigem origem, FonteDestino destino) {
		this.origem = origem;
		this.destino = destino;
	}
	
	public void processarDados() throws Exception {
		String entrada = null;
		try {
			entrada = origem.read();
		} catch (Exception ex) {
			throw new Exception("Ocorreu um problema ao ler dados da fonte de origem.");
		} if(entrada != null) {
			String saida = transformaDados(entrada);
			try {
				destino.write(saida);
			} catch (Exception e) {
				throw new Exception("Nao foi possível enviar os dados para fonte de destino.");
			}
		}
	}
	
	private String transformaDados(String entrada) {
		if( "[HoraAtual]".equals(entrada) ) {
			return "hora atual: " + System.currentTimeMillis();
		}
		return entrada;
	}
	
}
