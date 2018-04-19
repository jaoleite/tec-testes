package br.com.tecconcursos.arquivo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.base.Joiner;

import br.com.tecconcursos.util.Util;

public class SqlSelectEmailUsuarioSite {

	private Arquivo arquivo = Arquivo.of("email\\Lista-Email-TEC.txt");
	private Set<String> emails = new HashSet<String>();
	private String select;
	
	public SqlSelectEmailUsuarioSite() {
		List<String> strings = arquivo.lsitarDoAqruivo();
		for (String string : strings) {
			emails.add("'" + string.toLowerCase() + "'");
			
		}
	}
	
	public SqlSelectEmailUsuarioSite select() {
		String emails = Joiner.on(",\n").join(this.emails);
		select = String.format(getTemplate(), emails);
		return this;
	}
	
	public void imprimir() {
		if(Util.vazio(select)) {
			throw new RuntimeException("select is null");
		}
		System.out.println(select);
	}
	
	private String getTemplate() {
		StringBuilder builder = new StringBuilder();
		builder.append("select ");
		builder.append("\n\tu.email ");
		builder.append("\nfrom ");
		builder.append("\n\tusuariosite u ");
		builder.append("\n\tleft outer join liberacao l on l.usuariosite_id = u.id ");
		builder.append("\nwhere");
		builder.append("\n\tu.email in(%s)");
		builder.append("\n\tu.ativo is true");
		builder.append("\ngroup by u.email ");
		builder.append("\nhaving max(l.expiracao) < now() ");
		String template = builder.toString();
		return template;
	}
	
	public static void main(String[] args) {
		new SqlSelectEmailUsuarioSite().select().imprimir();
	}

}
