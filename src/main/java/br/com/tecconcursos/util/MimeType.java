package br.com.tecconcursos.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MimeType {

	private String pathname;
	
	public MimeType() {
	}
	
	public MimeType withFile(String pathname) {
		this.pathname = pathname;
		return this;
	}
	
	public void mimeType() throws IOException {
		falharSe();
		File file = new File(pathname);
		String mime = Files.probeContentType(file.toPath());
		System.out.println(mime);
		System.out.println(LIST.contains(mime));
	}
	
	private void falharSe() {
		if(this.pathname == null || this.pathname.trim().isEmpty()) {
			throw new RuntimeException("pathname is empty");
		}
	}
	
	private static final List<String> LIST = new ArrayList<>();
	static {
		//LIST.add("text/xml");
		LIST.add("application/xml");
	}
	
	public static void main(String[] args) throws Exception {
		List<String> arguments = Arrays.asList(args);
		if(!arguments.isEmpty()) {
			for (String arquivo : arguments) {
				new MimeType().withFile(arquivo).mimeType();
			}
		} else {
			System.out.println("para descobrir o MimeType passa o nome do arquivo como parametro (ex.: java MimeType artigos-avisos-1.xml)");
		}
	}
}
