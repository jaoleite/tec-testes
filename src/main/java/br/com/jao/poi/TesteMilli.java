package br.com.jao.poi;

public class TesteMilli {

	public TesteMilli() {
	}
	
	public static void main(String[] args) {
		int v = 1000 * 60 * 60 * 2;
		int div = 1000 * 60;
		System.out.println(v / (div));
		System.out.println((System.currentTimeMillis() - 1480039771180L) / div);
		System.out.println(v);
	}

}
