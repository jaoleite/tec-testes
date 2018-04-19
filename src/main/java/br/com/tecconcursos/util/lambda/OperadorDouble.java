package br.com.tecconcursos.util.lambda;

@FunctionalInterface
public interface OperadorDouble {

	Double aplicar(Double a, Double b);
	
	default public void teste() {
		System.out.println("OperadorDouble.teste()");
	}
	
}
