package br.com.tecconcursos.integracao.login;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {

	private static long geradorId = 0;
	private static UsuarioDao INSTANCE = null;
	
	private static String emails[] = {"jaoleite@yahoo.com.br", "jaoleite@gmail.com", "jaoleite@pop.com.br"};
	private static List<Usuario> usuarios;
	static {
		usuarios = new ArrayList<Usuario>();
		for (String email : emails) {
			Usuario usuario = new Usuario(nextVal(), email);
			usuarios.add(usuario);
		}
		
	}
	
	public static UsuarioDao instance() {
		if(INSTANCE == null) {
			INSTANCE = new UsuarioDao();
		}
		return INSTANCE;
	}
	
	public List<Usuario> listarUsuarios() {
		return listar();
	}
	
	public Usuario buscarPorId(Long id) {
		List<Usuario> list = listar();
		for (Usuario usuario : list) {
			if(usuario.getId().longValue() == id.longValue()) {
				return usuario;
			}
		}
		return null;
	}
	
	public Usuario buscarPorEmail(String email) {
		List<Usuario> list = listar();
		for (Usuario usuario : list) {
			if(usuario.getEmail().equals(email)) {
				return usuario;
			}
		}
		return null;
	}
	
	private static List<Usuario> listar() {
		return usuarios;
	}
	
	public void salvar(Usuario usuario) {
		usuario.setId(nextVal());
		listar().add(usuario);
	}
	
	public static long nextVal() {
		return ++geradorId;
	}

}
