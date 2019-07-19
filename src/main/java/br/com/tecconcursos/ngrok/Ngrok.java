package br.com.tecconcursos.ngrok;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Ngrok {
	
	private List<Tunnel> tunnels;
	
	public List<Tunnel> getTunnels() {
		return tunnels;
	}

	public void setTunnels(List<Tunnel> tunnels) {
		this.tunnels = tunnels;
	}

	//private static final Map<Integer, Process> map = new HashMap<>();
	
	public static void main(String[] args) throws Exception {
		String jsonAsString = getJsonAsString();
		System.out.println(jsonAsString);
		
		Gson gson = new Gson();
		Ngrok ngrok = gson.fromJson(jsonAsString, Ngrok.class);
		System.out.println(ngrok.getTunnels().get(0).getPublicUrl());
		
		teste01();
	}
	
	private static String getJsonAsString() throws Exception {
		URLConnection connection = new URL("http://localhost:4040/api/tunnels").openConnection();
		InputStream stream = connection.getInputStream();
		StringWriter writer = new StringWriter();
		IOUtils.copy(stream, writer);
		String json = writer.toString();
		stream.close();
		return json;
	}
	
	public static void teste01() {
		List<Tunnel> list = new ArrayList<>();
		list.add(Tunnel.mock());
		
		GsonBuilder builder = new GsonBuilder();
		
		Gson gson = builder.create();
		String json = gson.toJson(list);
		System.out.println(json);
	}
	
	public static void start() throws Exception {
		NgrokConfiguration configuration = NgrokConfiguration.DEV;
		String processoAutenticacao = "%sngrok authtoken %s";
		processoAutenticacao = String.format(processoAutenticacao, configuration.getPath(), configuration.getKeyAuthentication());
		System.out.println(processoAutenticacao);
		execute(processoAutenticacao);
		
		String processoStart = "%sngrok http %d";
		processoStart = String.format(processoStart, configuration.getPath(), configuration.getPort());
		execute(processoStart);
	}
	
	public static void execute(String command) throws IOException {
		Runtime rt = Runtime.getRuntime();
		Process pr = rt.exec(command);
		BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
		String line = null;
		while((line = input.readLine()) != null) {
			System.out.println(line);
		}
	}
	
	

}
