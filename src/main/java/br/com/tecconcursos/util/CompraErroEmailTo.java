package br.com.tecconcursos.util;


public class CompraErroEmailTo implements Comparable<CompraErroEmailTo> {

	private String email;
	
	private Long id;

	public CompraErroEmailTo(String linha) {
		buildLinha(linha);
	}
	
	private void buildLinha(String linha) {
		linha = linha.trim();
		linha = linha.substring(18);
		String email = linha.substring(0, linha.indexOf(" ("));
		String idString = linha.substring(linha.indexOf(": ") + 2, linha.indexOf(")"));
		Long id = Long.parseLong(idString.trim());
		this.email = email;
		this.id = id;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
		builder.append("CompraErroEmailTo [email=");
		builder.append(email);
		builder.append(", id=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompraErroEmailTo other = (CompraErroEmailTo) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int compareTo(CompraErroEmailTo o) {
		return this.getId().compareTo(o.getId());
	}

}
