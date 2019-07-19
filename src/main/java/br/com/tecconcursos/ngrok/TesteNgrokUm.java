package br.com.tecconcursos.ngrok;

import java.util.HashMap;
import java.util.Map;

import br.com.tecconcursos.util.Util;

public class TesteNgrokUm {

	private static final Map<Integer, String> map = new HashMap<>();
	
	public static void main(String[] args) {
		for(int i=1; i<=5; i++) {
			String command = "comando numero %d";
			command = String.format(command, i);
			addProcess(command);
		}
		
	}
	
	private static void addProcess(String command) {
		int numberProcess = nextProcessNumber();
		map.put(numberProcess, command);
	}
	
	private static int nextProcessNumber() {
		if(!Util.vazio(map.keySet())) {
			Integer integer = map.keySet().parallelStream().max((p1, p2) -> p1.compareTo(p2)).get();
			System.out.println(integer);
			return ++integer;
		}
		return 1;
	}

}
