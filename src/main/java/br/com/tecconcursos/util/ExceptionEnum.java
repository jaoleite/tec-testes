package br.com.tecconcursos.util;

import java.lang.reflect.Constructor;

import br.com.tecconcursos.service.pagamento.pagseguro.exception.PagamentoBoletoPagSeguroException;
import br.com.tecconcursos.to.pagseguro.exception.PagamentoBoletoPagSeguroServerException;

public enum ExceptionEnum {

	PAGAMENTO_BOLETO_PAGSEGURO_SERVER_EXCEPTION(PagamentoBoletoPagSeguroServerException.class),
	PAGAMENTO_BOLETO_PAGSEGURO_EXCEPTION(PagamentoBoletoPagSeguroException.class),
	;
	
	private Class<?> clazz;

	private ExceptionEnum(Class<?> clazz) {
		this.clazz = clazz;
	}
	
	public void throwable() throws Throwable {
		Constructor<?> constructor = clazz.getConstructor();
		Throwable throwable = (Throwable) constructor.newInstance();
		throw throwable;
	}

	public void throwable(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) throws Throwable {
		Constructor<?> constructor = clazz.getConstructor(String.class, Throwable.class, Boolean.class, Boolean.class);
		Throwable throwable = (Throwable) constructor.newInstance(message, cause, enableSuppression, writableStackTrace);
		throw throwable;
	}

	public void throwable(String message, Throwable cause) throws Throwable {
		Constructor<?> constructor = clazz.getConstructor(String.class, Throwable.class);
		Throwable throwable = (Throwable) constructor.newInstance(message, cause);
		throw throwable;
	}

	public void throwable(String message) throws Throwable {
		Constructor<?> constructor = clazz.getConstructor(String.class);
		Throwable throwable = (Throwable) constructor.newInstance(message);
		throw throwable;
	}

	public void throwable(Throwable cause) throws Throwable {
		Constructor<?> constructor = clazz.getConstructor(Throwable.class);
		Throwable throwable = (Throwable) constructor.newInstance(cause);
		throw throwable;
	}

	public static void main(String[] args) throws Throwable {
		ExceptionEnum exceptionEnum = ExceptionEnum.PAGAMENTO_BOLETO_PAGSEGURO_SERVER_EXCEPTION;
		exceptionEnum.throwable("Exception provacada por mim");
	}
	
	
}
