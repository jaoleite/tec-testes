package br.com.tecconcursos.html;

import java.awt.image.RenderedImage;
import java.net.URI;

public class UrlFotos {

	private String stringUrl;
	
	private URI uri;
	
	private String imageFileName;
	
	private RenderedImage image;
	
	public UrlFotos() {
	}

	public UrlFotos(String stringUrl) {
		super();
		this.stringUrl = stringUrl;
		replaceHttps();
		createURI();
		createImageFileName();
	}
	
	private void replaceHttps() {
		this.stringUrl = this.stringUrl.replace("https", "http");
	}
	
	private void createURI() {
		this.uri = URI.create(this.stringUrl);
	}
	
	private void createImageFileName() {
		this.imageFileName = this.stringUrl.substring(this.stringUrl.lastIndexOf("/") + 1);
	}
	
	public URI getUri() {
		return uri;
	}

	public String getImageFileName() {
		return imageFileName;
	}
	
	public RenderedImage getImage() {
		return image;
	}

	public void setImage(RenderedImage image) {
		this.image = image;
	}

}
