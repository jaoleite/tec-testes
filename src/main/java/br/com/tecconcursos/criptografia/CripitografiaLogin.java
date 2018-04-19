package br.com.tecconcursos.criptografia;

import br.com.tecconcursos.service.CriptografiaService;
import br.com.tecconcursos.util.Util;

public class CripitografiaLogin {

	private CriptografiaService serivice;
	private String senha;
	private String hash;
	
	private CripitografiaLogin() {}
	
	public static CripitografiaLogin of() {
		return new CripitografiaLogin();
	}
	
	public CripitografiaLogin instaceService() {
		serivice = new CriptografiaService();
		return this;
	}
	
	public CripitografiaLogin gerarSenha() {
		falharSeServiceNull();
		senha = Util.geradorUUID();
		return this;
	}
	
	public CripitografiaLogin gerarHash() {
		falharSeSenhaNull();
		hash = serivice.hash(senha);
		return this;
	}
	
	public boolean verificarSenhaCriptografada() {
		falharSeHashNull();
		return serivice.compararSenhaCriptografadaModoAntigo(hash, senha);
	}
	
	private void falharSeServiceNull() {
		if(serivice == null) {
			throw new RuntimeException("Service is null");
		}
	}
	
	private void falharSeSenhaNull() {
		falharSeServiceNull();
		if(Util.vazio(senha)) {
			throw new RuntimeException("Senha is null");
		}
	}
	
	private void falharSeHashNull() {
		falharSeSenhaNull();
		if(Util.vazio(hash)) {
			throw new RuntimeException("Hash is null");
		}
	}
	
	public static void main(String[] args) {
		boolean b = of().instaceService().gerarSenha().gerarHash().verificarSenhaCriptografada();
		System.out.println(b);
	}

}
