package br.com.tecconcursos.util;

import java.nio.charset.Charset;
import java.util.Set;
import java.util.SortedMap;

public class CharsetUtil {

	public static void listAll() {
		SortedMap<String, Charset> map = Charset.availableCharsets();
		Set<String> set = map.keySet();
		for (String key : set) {
			System.out.println(key + "=>" + map.get(key));
		}
	}
	
	public static void main(String[] args) {
		listAll();
	}

}
