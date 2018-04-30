package br.com.tecconcursos.util;

public class Teste02 {
	
	public static void main(String[] args) {
		System.out.println(alphaID("1234567890", false));
	}

	public static String alphaID(String in, boolean to_num){
		String out = "";
		String index = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int base  = index.length();

		
		if (to_num) {
			// Digital number  <<--  alphabet letter code
			int len = in.length() - 1;

			for (int t = len; t >= 0; t--) {
				double bcp = Math.pow(base, len - t);
				//in.substring(t, 1).indexOf(index)
				//out = out + strpos(index, in.substring(t, 1)) * bcp;
				out = out + in.substring(t, 1).indexOf(index) * bcp;
			}
		} else {
			double number = new Long(in);
			
			for (int t = (int) (number != 0 ? (double)Math.floor(log(number, base)) : 0); t >= 0; t--) {
				double bcp = Math.pow(base, t);
				double a = Math.floor(number / bcp) % base;
				out = out + index.substring((int)a, 1);
				number  = number - (a * bcp);
			}
		}

		return out;
	}
	
	private static double log(double number, double base) {
		return Math.log(number) / Math.log(base);
	}

}
