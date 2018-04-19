package br.com.tecconcursos.integracao.login;

public class IntegracaoLoginService {
	
	private static IntegracaoLoginService INTANCE;
	
	private UsuarioDao usuarioDao;
	private UsuarioLoginIntegracaoDao usuarioLoginIntegracaoDao;

	private IntegracaoLoginService(UsuarioDao usuarioDao, UsuarioLoginIntegracaoDao usuarioLoginIntegracaoDao) {
		this.usuarioDao = usuarioDao;
		this.usuarioLoginIntegracaoDao = usuarioLoginIntegracaoDao;
	}

	public static IntegracaoLoginService instance() {
		if(INTANCE == null) {
			INTANCE = new IntegracaoLoginService(UsuarioDao.instance(), UsuarioLoginIntegracaoDao.instance());
		}
		return INTANCE;
	}
	
	public void logarMaisCadastarUsuario(IntegracaoDto integracao) {
		if(usuarioTec(integracao)) {
			UsuarioLoginIntegracao login = usuarioLoginIntegracaoDao.buscarPorEmailMaisIntegracao(integracao.getEmail(), integracao.getIntegracao());
			if(login == null) {
				login = new UsuarioLoginIntegracao();
				login.setEmail(integracao.getEmail());
				login.setIntegracao(integracao.getIntegracao());
				login.setUsuario(integracao.getUsuario());
				usuarioLoginIntegracaoDao.salvar(login);
				System.out.println("login integracao salvo");
			}
		} else {
			cadastrarIntegracaoUsuario(integracao);			
		}
		login(integracao);
	}
	
	public boolean usuarioTec(IntegracaoDto integracao) {
		Usuario usuario = usuarioDao.buscarPorEmail(integracao.getEmail());
		if(usuario != null) {
			integracao.setUsuario(usuario);
			return true;
		}
		return false;
	}

	public void cadastrarIntegracaoUsuario(IntegracaoDto integracao) {
		System.out.println(usuarioDao.listarUsuarios());
		Usuario usuario = new Usuario();
		usuario.setEmail(integracao.getEmail());
		usuarioDao.salvar(usuario);
		System.out.println("usuario salvo");
		
		UsuarioLoginIntegracao loginIntegracao = new UsuarioLoginIntegracao();
		loginIntegracao.setEmail(integracao.getEmail());
		loginIntegracao.setIntegracao(integracao.getIntegracao());
		loginIntegracao.setUsuario(usuario);
		usuarioLoginIntegracaoDao.salvar(loginIntegracao);
		System.out.println("login integracao salvo");
		
		System.out.println("enviar email com senha");
	}
	
	public void login(IntegracaoDto integracao) {
		System.out.println("Usuaorio logado");
	}
	
	public void integrarUsuarioLogado() {
		
	}
	
	public boolean usuarioIntegrado(IntegracaoDto integracao) {
		System.out.println(usuarioLoginIntegracaoDao.listarLogins());
		UsuarioLoginIntegracao loginIntegracao = usuarioLoginIntegracaoDao.buscarPorEmailMaisIntegracao(integracao.getEmail(), integracao.getIntegracao());
		if(loginIntegracao != null) {
			return true;
		}
		return false;
	}
	

}
