package br.com.tecconcursos.util.lambda;

public class Calculadora {

	public static Double calcular(Double op1, Double op2, OperadorDouble operador) {
		return operador.aplicar(op1, op2);
	}
	
	public static void main(String[] args) {
		double soma = Calculadora.calcular(30d, 70d, (a, b) -> a + b);
		double subtracao = Calculadora.calcular(100d, 30d, (a, b) -> a - b);
		double multiplicacao = calcular(5d, 4d, (a, b) -> a * b);
		double divisao = calcular(9d, 3d, (a, b) -> a / b);
		
		System.out.println(soma);
		System.out.println(subtracao);
		System.out.println(multiplicacao);
		System.out.println(divisao);
	}
	
}
