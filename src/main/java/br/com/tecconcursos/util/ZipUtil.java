package br.com.tecconcursos.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPOutputStream;

public class ZipUtil {

	private static Charset charset = StandardCharsets.UTF_8;

	public static byte[] compress(String data) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length());
		GZIPOutputStream gzip = new GZIPOutputStream(bos);
		gzip.write(data.getBytes(charset));
		gzip.close();

		byte[] compressed = bos.toByteArray();
		bos.close();
		return compressed;
	}

	public static void main(String[] args) throws IOException {
		String string = "admin";
		System.out.println("after compress:");
		byte[] bs = compress(string);
		 System.out.println(new String(bs, charset));
	}
}
