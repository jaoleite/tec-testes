package br.com.tecconcursos.memcached;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import net.spy.memcached.CachedData;
import net.spy.memcached.transcoders.Transcoder;

public class MyTrancoder implements Transcoder<ObjectTeste>{

	@Override
	public boolean asyncDecode(CachedData d) {
		return false;
	}

	@Override
	public CachedData encode(ObjectTeste o) {
		System.out.println("MyTrancoder.encode()");
		System.out.println(o);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream out = new ObjectOutputStream(bos);
			out.defaultWriteObject();
			out.writeObject(o);
			out.flush();
			byte[] bytes = bos.toByteArray();
			return new CachedData(0, bytes, CachedData.MAX_SIZE);
		} catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				bos.close();
			} catch (IOException e) {}
		}
		
		return null;
	}

	@Override
	public ObjectTeste decode(CachedData d) {
		System.out.println("MyTrancoder.decode()");
		ByteArrayInputStream bis = new ByteArrayInputStream(d.getData());
		try {
			ObjectInputStream in = new ObjectInputStream(bis);
			in.defaultReadObject();
			return (ObjectTeste) in.readObject();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bis.close();
			} catch (IOException e) {
				
			}
		}
		return null;
	}

	@Override
	public int getMaxSize() {
		return 0;
	}

	private void writeObject(java.io.ObjectOutputStream stream) throws IOException {
		System.out.println("ObjetoSerializado.writeObject()");
		stream.defaultWriteObject();
		//stream.writeObject(objectTeste);
    }

    private void readObject(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException {
    	System.out.println("ObjetoSerializado.readObject()");
        stream.defaultReadObject();
       // objectTeste = (ObjectTeste) stream.readObject();
    }

}
