package br.com.tecconcursos.integracao.login;

import java.util.ArrayList;
import java.util.List;

public class UsuarioLoginIntegracaoDao {
	
	private static UsuarioLoginIntegracaoDao INSTANCE = null;
	private static List<UsuarioLoginIntegracao> list = new ArrayList<UsuarioLoginIntegracao>();
	static {
		UsuarioLoginIntegracao integracao = new UsuarioLoginIntegracao();
		integracao.setEmail("jaoleite@linkedin.com");
		integracao.setIntegracao(IntegracaoEnumFactory.LINKED_IN);
		Usuario usuario = new Usuario();
		usuario.setId(1L);
		integracao.setUsuario(usuario);
		list.add(integracao);
	}
	
	public static UsuarioLoginIntegracaoDao instance() {
		if(INSTANCE == null) {
			INSTANCE = new UsuarioLoginIntegracaoDao();
		}
		return INSTANCE;
	}
	
	public List<UsuarioLoginIntegracao> listarLogins() {
		return listar();
	}
	
	private static List<UsuarioLoginIntegracao> listar() {
		return list;
	}
	
	public UsuarioLoginIntegracao buscarPorEmailMaisIntegracao(String email, IntegracaoEnumFactory integracao) {
		List<UsuarioLoginIntegracao> list = listar();
		for (UsuarioLoginIntegracao usuarioLoginIntegracao : list) {
			if(usuarioLoginIntegracao.getEmail().equals(email)
					&& usuarioLoginIntegracao.getIntegracao().equals(integracao)) {
				return usuarioLoginIntegracao;
			}
		}
		return null;
	}
	
	public void salvar(UsuarioLoginIntegracao integracao) {
		List<UsuarioLoginIntegracao> list = listar();
		list.add(integracao);
	}

}
