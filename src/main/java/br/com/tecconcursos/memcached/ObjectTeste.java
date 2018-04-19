package br.com.tecconcursos.memcached;

public class ObjectTeste {

	private String nome;
	
	private Long id;

	public ObjectTeste(String nome, Long id) {
		super();
		this.nome = nome;
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ObjectTeste [nome=");
		builder.append(nome);
		builder.append(", id=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}
}
