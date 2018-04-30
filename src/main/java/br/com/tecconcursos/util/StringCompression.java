package br.com.tecconcursos.util;

public class StringCompression {

	public String compress(String str) {
		if (str.isEmpty()) {
			return "";
		}

		char[] chars = str.toCharArray();
		StringBuilder builder = new StringBuilder();

		int count = 1;
		char prev = chars[0];
		for (int i = 1; i < chars.length; i++) {
			char current = chars[i];
			if (current == prev) {
				count++;
			} else {
				builder.append(prev).append(count);
				count = 1;
			}
			prev = current;
		}
		
		String st = builder.append(prev).append(count).toString();
		System.out.println();
		return st;
	}

	public static void main(String[] args) {
		StringCompression test = new StringCompression();

		test.compress("aabcccccaaa");
		test.compress("aaaaa");
		test.compress("aaaabbb");
		test.compress("aaabbbccc");
		test.compress("abc");
		test.compress("a");
		test.compress("");
	}

}
