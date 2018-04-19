package br.com.tecconcursos.serializacao;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringWriter;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.codec.binary.Base64;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class TesteSerializacao<T> {

	static final Base64 base64 = new Base64();
	
	public String serializar(T t) {
		try {
			ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
			GZIPOutputStream gzipOutputStream = new GZIPOutputStream(arrayOutputStream);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(gzipOutputStream);
			objectOutputStream.writeObject(t);
			objectOutputStream.flush();
			return new String(base64.encode(arrayOutputStream.toByteArray()));
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public T deserializar(String string) {
		try {
			ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(base64.decode(string.getBytes()));
			GZIPInputStream gzipInputStream = new GZIPInputStream(arrayInputStream);
			ObjectInputStream objectInputStream = new ObjectInputStream(gzipInputStream);
			return (T) objectInputStream.readObject();
        } catch(Exception e) {
        	throw new RuntimeException(e);
        }
	}
	
	public static void main(String[] args) throws IOException {
		ObjetoParaSerializacao serializacao = new ObjetoParaSerializacao(1L, "Joao paulo");
		String string = beanToString(serializacao);
		System.out.println(string);
		System.out.println(stringToBean(string, ObjetoParaSerializacao.class));
		/*TesteSerializacao<ObjetoParaSerializacao> testeSerializacao = new TesteSerializacao<>();
		String serializado = testeSerializacao.serializar(serializacao);
		System.out.println(serializado);
		ObjetoParaSerializacao serializacaoNovo = testeSerializacao.deserializar(serializado);
		System.out.println(serializacaoNovo);*/
	}
	
	public static String beanToString(Object object) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		StringWriter stringEmp = new StringWriter();
		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		objectMapper.writeValue(stringEmp, object);
		return stringEmp.toString();
	}
	
	public static <T> T stringToBean(String content, Class<T> valueType) throws IOException {
	    return new ObjectMapper().readValue(content, valueType);
	}

}
