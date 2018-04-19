package br.com.tecconcursos.easymock;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

public class ProcessadorTest {

	private FonteDestino destino = null;
	
	private FonteOrigem origem = null;
	
	@Before
	public void inicializa() {
		origem = EasyMock.createMock(FonteOrigem.class);
		destino = EasyMock.createMock(FonteDestino.class);
	}
	
	@Test
	public void test() {
		EasyMock.expect(origem.read()).andReturn("DadoTeste");
		destino.write("DadoTeste");
		EasyMock.replay(origem, destino);
		
		Processador processador = new Processador(origem, destino);
		try {
			processador.processarDados();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		EasyMock.verify(origem, destino);
	}

}
