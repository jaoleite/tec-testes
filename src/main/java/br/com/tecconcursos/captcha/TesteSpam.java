package br.com.tecconcursos.captcha;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class TesteSpam {

	private static final String USER_AGENT = "Mozilla/5.0";
	
	public static void main1(String[] args) throws Exception {
		
		String url = "https://api.stopforumspam.org/api";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		
		String email = "teste@mvrht.com";
		String urlParameters = "email=" + email + "&json";
		
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
		
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
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
			
			parseXML(response.toString());
		}
	}
	
	public static void main(String[] args) {
		String string = "<response success=\"true\">	<type>email</type>	<appears>yes</appears>	<lastseen>2017-01-27 22:13:52</lastseen>	<frequency>255</frequency></response>";
		parseXML(string);
	}
	
	public static void parseXML(String string) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
		DocumentBuilder builder;  
		try {  
			builder = factory.newDocumentBuilder(); 
			Document document = builder.parse(new InputSource(new StringReader(string)));
			
			NodeList response = document.getElementsByTagName("response");
			if (response.getLength() > 0) {
				Element err = (Element)response.item(0);
				System.out.println(err.getAttribute("success"));
				System.out.println(err.getElementsByTagName("frequency")
			             .item(0).getTextContent());
			}
			
		} catch (Exception e) {  
			e.printStackTrace();  
		}
		
		/*NodeList errNodes = doc.getElementsByTagName("error");
	    if (errNodes.getLength() > 0) {
	        Element err = (Element)errNodes.item(0);
	        System.out.println(err.getElementsByTagName("errorMessage")
	             .item(0).getTextContent());
	    } else { 
	        // success
	    }*/
		
	}
}
