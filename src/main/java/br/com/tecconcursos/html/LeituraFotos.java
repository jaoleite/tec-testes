package br.com.tecconcursos.html;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import br.com.tecconcursos.util.Util;

public class LeituraFotos {

	public static final String PATH = "D:\\maria-laura\\";
	public static final String HTML_FILE = PATH + "fotos-marina.html";
	public static final String IMAGE_DIR = PATH + "fotos\\";
	
	private List<UrlFotos> list = null;
	private int index = 0;
	
	public LeituraFotos() {
		list = new ArrayList<>();
	}

	public LeituraFotos leituraHtml() throws IOException {
		Document document = Jsoup.parse(new File(HTML_FILE), Charset.forName("windows-1252").name());
		Elements elements = document.select("a");
		index = 0;
		elements.forEach(element -> {
			if (element.hasClass("zoom")) {
				String href = element.attr("href");
				if(!Util.vazio(href) && href.contains("jpg")) {
					UrlFotos fotos = new UrlFotos(element.attr("href"));
					list.add(fotos);
					index++;
				}
			}
		});
		System.out.println("quantidade de fotos: " + index);
		return this;

	}

	public void go() throws IOException {
		for (UrlFotos fotos : list) {
			createImage(fotos);
		}
	}
	
	public void createImage(UrlFotos fotos) throws IOException {
		readImage(fotos);
		new FotoThread(fotos).start();
	}
	
	public void readImage(UrlFotos fotos) throws IOException {
		InputStream inputStream = getImageInputStream(fotos);
		RenderedImage image = ImageIO.read(inputStream);
		fotos.setImage(image);
	}
	
	public InputStream getImageInputStream(UrlFotos fotos) throws IOException {
		HttpRequestBase httpRequest = new HttpGet(fotos.getUri());

		HttpClient httpclient = HttpClientBuilder.create().build();
		ResponseHandler<InputStream> responseHandler = new ResponseHandlerImpl(fotos);
		InputStream inputStream = httpclient.execute(httpRequest, responseHandler);
		
		return inputStream;
	}

	public static void main(String[] args) throws Exception {
		new LeituraFotos().leituraHtml().go();
	}

	public class ResponseHandlerImpl implements ResponseHandler<InputStream> {
		
		private UrlFotos fotos;
		
		public ResponseHandlerImpl(UrlFotos fotos) {
			this.fotos = fotos;
		}

		@Override
		public InputStream handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
			int status = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();
			byte[] bs = EntityUtils.toByteArray(entity);
			InputStream inputStream = IOUtils.toInputStream(new String(bs));
			System.out.println("status: " + status + ". Conectado a " + fotos.getUri());
			return inputStream;
		}

	}
}
