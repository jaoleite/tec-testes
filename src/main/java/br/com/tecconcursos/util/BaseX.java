package br.com.tecconcursos.util;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseX {

	public static final char[] DICTIONARY_16 =
			new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
	
	public static final char[] DICTIONARY_32 =
			new char[] { '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
			'W', 'X', 'Y', 'Z' };

	public static final char[] DICTIONARY_62 =
			new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
			'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
	
	//public static final char[] DICTIONARY_62 = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

	public static final char[] DICTIONARY_89 =
			new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
			'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '+', '"', '@',
			'*', '#', '%', '&', '/', '|', '(', ')', '=', '?', '~', '[', ']', '{', '}', '$', '-', '_', '.', ':', ',', ';', '<', '>' };

	protected char[] dictionary;

	public BaseX(char[] dictionary) {
		this.dictionary = dictionary;
	}

	public BaseX() {
		this.dictionary = DICTIONARY_62;
	}

	public String encode(BigInteger value) {
		List<Character> result = new ArrayList<>();
		BigInteger base = new BigInteger("" + dictionary.length);
		int exponent = 1;
		BigInteger remaining = value;
		while (true) {
			BigInteger a = base.pow(exponent);
			BigInteger b = remaining.mod(a);
			BigInteger c = base.pow(exponent - 1);
			BigInteger d = b.divide(c);

			result.add(dictionary[d.intValue()]);
			remaining = remaining.subtract(b);

			if (remaining.equals(BigInteger.ZERO)) {
				break;
			}

			exponent++;
		}
		
		StringBuffer sb = new StringBuffer();
		for (int i = result.size() - 1; i >= 0; i--) {
			sb.append(result.get(i));
		}
		return sb.toString();
	}

	public BigInteger decode(String str) {
		char[] chars = new char[str.length()];
		str.getChars(0, str.length(), chars, 0);

		char[] chars2 = new char[str.length()];
		int i = chars2.length - 1;
		for (char c : chars) {
			chars2[i--] = c;
		}

		Map<Character, BigInteger> dictMap = new HashMap<>();
		int j = 0;
		for (char c : dictionary) {
			dictMap.put(c, new BigInteger("" + j++));
		}

		BigInteger bi = BigInteger.ZERO;
		BigInteger base = new BigInteger("" + dictionary.length);
		int exponent = 0;
		for (char c : chars2) {
			BigInteger a = dictMap.get(c);
			BigInteger b = base.pow(exponent).multiply(a);
			bi = bi.add(new BigInteger("" + b));
			exponent++;
		}

		return bi;

	}

	public static void main(String[] args) throws ParseException {
		BaseX bx = new BaseX();
		String numero = "10000000";
		
		NumberFormat format = new DecimalFormat("00000000000000000000");
		/*System.out.println(format.format(1l));
		System.out.println(format.parse("00000000000000000001"));*/
		
		//System.out.println(bx.encode(new BigInteger(numero)));
		//System.out.println(bx.decode("BTHdqFnY1BC22FJWC"));
		
		/*String original = "123456789012345678901234567890";
		System.out.println("Original: " + original);
		BaseX bx = new BaseX(DICTIONARY_62);
		String encoded = bx.encode(new BigInteger(original));
		System.out.println("encoded: " + encoded);
		BigInteger decoded = bx.decode(encoded);
		System.out.println("decoded: " + decoded);
		if (original.equals(decoded.toString())) {
			System.out.println("Passed! decoded value is the same as the original.");
		} else {
			System.err.println("FAILED! decoded value is NOT the same as the original!!");
		}*/
		teste();
	}
	
	public static void teste() {
		long l = 1000;
		String encode = encode2(l);
		System.out.println("Encode: " + encode);
		long decode = decode2(encode);
		System.out.println("Decode: " + decode);
	}
	
	public static long decode2(String result) {
		result = stringReverse(result);
		byte[] bytes = Base64.getDecoder().decode(result);
		return Long.reverse(Long.parseLong(new String(bytes)));
	}
	
	public static String encode2(long number) {
		byte[] src = Long.toString(Long.reverse(number)).getBytes();
		String result = Base64.getEncoder().withoutPadding().encodeToString(src);
		return stringReverse(result);
	}
	
	private static String stringReverse(String string) {
		StringBuffer sb = new StringBuffer();
		for (int i = string.toCharArray().length - 1; i >= 0; i--) {
			sb.append(string.toCharArray()[i]);
		}
		return sb.toString();
	}
	
}
