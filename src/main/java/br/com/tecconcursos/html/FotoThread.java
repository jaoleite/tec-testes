package br.com.tecconcursos.html;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FotoThread extends Thread {
	
	private static final String EXTENSION = "jpg";
	
	private UrlFotos fotos;

	public FotoThread(UrlFotos fotos) {
		this.fotos = fotos;
	}
	
	@Override
	public void run() {
		try {
			String fileJpg = LeituraFotos.IMAGE_DIR + fotos.getImageFileName();
			FileOutputStream outputStream = new FileOutputStream(new File(fileJpg));
			ImageIO.write(fotos.getImage(), EXTENSION, outputStream);
			System.out.println("Imagem " + fotos.getImageFileName() + " gravada com sucesso.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
