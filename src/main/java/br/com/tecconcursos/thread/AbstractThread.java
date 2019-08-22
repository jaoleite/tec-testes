package br.com.tecconcursos.thread;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.inject.Inject;

public abstract class AbstractThread extends Thread {
	
	@Inject private Integer outroValor;
	
	public AbstractThread() {
		this.outroValor = 50;
		List<Field> fields = getAllFields();
		System.out.println("\n\n");
		
		for (Field field : fields) {
			if(field.isAnnotationPresent(Inject.class)) {
				System.out.println(field.getName());
			}
		}
	}
	
	private List<Field> getAllFields() {
		Class<?> current = this.getClass();
		List<Field> fields = new ArrayList<>();
		while(current.getSuperclass() != null) {
			Field[] arrayField = current.getDeclaredFields();
			fields.addAll(Arrays.asList(arrayField));
			current = current.getSuperclass();
		}
		return fields;
	}
	
	public void teste() {
		System.out.println(outroValor);
	}

}
