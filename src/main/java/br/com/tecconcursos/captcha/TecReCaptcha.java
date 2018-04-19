package br.com.tecconcursos.captcha;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import br.com.tecconcursos.arquivo.Arquivo;

public class TecReCaptcha {

	private static final String USER_AGENT = "Mozilla/5.0";
	
	public static void main(String[] args) throws Exception {
		
		List<String> list = Arquivo.of("reCaptcha\\response.txt").lsitarDoAqruivo();
		
		String url = "https://www.google.com/recaptcha/api/siteverify";
		String secret = "6LfzkAcUAAAAAEPPL51H845QSl7F1Ak_Ovk86eSe";
		String reCaptchaResponse = list.get(0);
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		String urlParameters = "secret=" + secret + "&response=" + reCaptchaResponse;
		
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
		
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);
		
		if(responseCode == 200) {
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			System.out.println(response.toString());
		}
	}

}
