package br.com.tecconcursos.util;

public class Teste03 {


	private static final String INDEX = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	public String encode(long number) {
		StringBuilder ret = new StringBuilder();
		char[] indexArray = indexToArray();
		
		System.out.println("log: " + Math.floor(Math.log(number) / Math.log(indexArray.length)));
		
		int init = (int) Math.floor(Math.log(number) / Math.log(indexArray.length));
		for(int i = init; i >= 0; i--) {
			
			double floor = Math.floor(number / bcpow(indexArray.length, i));
			int b = (int) (floor % indexArray.length);
			System.out.println(b);
			
			//ret = ret + AlphabeticID.index.substr((Math.floor(parseInt(_number) / AlphabeticID.bcpow(AlphabeticID.index.length, i)) % AlphabeticID.index.length), 1);
		}
		
		
		
		System.out.println("Aqui: " + Math.floor(Math.log(number) / Math.log(INDEX.length())));
		for (short i = (short) Math.floor(Math.log(number) / Math.log(INDEX.length())); i >= 0; i--) {
			ret.append(INDEX.substring(1, (int) (Math.floor(number / bcpow(INDEX.length(), i)) % INDEX.length())));
		}

		return reverse(ret.toString());
	}
	
	public long decode(String string) {
		String str = reverse(string);
		char[] array = str.toCharArray();
		long ret = 0;
		char[] indexArray = indexToArray();
		for (int i = 0; i <= array.length; i++) {
			double a = (bcpow(indexArray.length, (array.length - 1) - i));
			//String index = array[i];
			ret = (long) (ret + i * a);
		}
		return ret;
	}
	
	private char[] indexToArray() {
		return INDEX.toCharArray();
	}
	
	private double bcpow(double _a, double _b) {
		return Math.floor(Math.pow(_a, _b));
	}

	private String reverse(String st) {
		StringBuffer sb = new StringBuffer();
		for (int i = st.toCharArray().length - 1; i >= 0; i--) {
			sb.append(st.charAt(i));
		}
		return sb.toString();
	}

	
	public static void main(String[] args) {
		long number = 1000;
		String result = new Teste03().encode(number);
		System.out.println("Encode: " + result);
		System.out.println();
		System.out.println("Decode: " + new Teste03().decode(result));
		
	}


}
