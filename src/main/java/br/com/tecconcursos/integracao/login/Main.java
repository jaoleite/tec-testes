package br.com.tecconcursos.integracao.login;

public class Main {

	public static void testLoginOuCadastroUsuario() {
		System.out.println("primeira intera��o");
		testeLogarUsuario(IntegracaoEnumFactory.FACEBOOK);
		
		System.out.println();
		System.out.println("segunda intera��o");
		testeLogarUsuario(IntegracaoEnumFactory.FACEBOOK);
		
		System.out.println();
		System.out.println("terceira intera��o");
		testeLogarUsuario(IntegracaoEnumFactory.FACEBOOK);
	}
	
	public static void testeLogarUsuario(IntegracaoEnumFactory factory) {
		IntegracaoDto integracao = factory.getIntegracao().getIntegracao();
		IntegracaoLoginService service = IntegracaoLoginService.instance();
		service.logarMaisCadastarUsuario(integracao);
	}
	
	//public static void 
	
	public static void main(String[] args) {
		testLoginOuCadastroUsuario();
	}

}
