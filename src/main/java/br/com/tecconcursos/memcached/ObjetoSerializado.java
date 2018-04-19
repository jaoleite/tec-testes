package br.com.tecconcursos.memcached;

import java.io.IOException;
import java.io.Serializable;

public class ObjetoSerializado implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private transient ObjectTeste objectTeste;

	public ObjectTeste getObjectTeste() {
		return objectTeste;
	}

	public void setObjectTeste(ObjectTeste objectTeste) {
		this.objectTeste = objectTeste;
	}

	private void writeObject(java.io.ObjectOutputStream stream) throws IOException {
		stream.defaultWriteObject();
		//ByteArrayOutputStream bos = new ByteArrayOutputStream();
		//bos.
		//stream.write(buf);
		//stream.writeObject(objectTeste);
		System.out.println("ObjetoSerializado.writeObject()");
    }

    private void readObject(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException {
    	System.out.println("ObjetoSerializado.readObject()");
        stream.defaultReadObject();
        //objectTeste = (ObjectTeste) stream.readObject();
    }

}
