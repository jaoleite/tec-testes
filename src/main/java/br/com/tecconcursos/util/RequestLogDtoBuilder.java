package br.com.tecconcursos.util;

import java.lang.reflect.Field;

import org.apache.metamodel.data.Row;
import org.apache.metamodel.query.SelectItem;

import br.com.tecconcursos.to.RequestLogDto;

public class RequestLogDtoBuilder {
	
	private SelectItem[] itens;
	
	private RequestLogDto requestLogDto = null;
	
	public RequestLogDtoBuilder() {}

	public RequestLogDtoBuilder(SelectItem[] itens) {
		this.itens = itens;
	}
	
	public RequestLogDto buildByRow(Row row) {
		if(row == null || itens == null) {
			throw new NullPointerException("Row ou SelectItem[] esta nulo");
		}
		
		requestLogDto = new RequestLogDto();
		for (SelectItem item : itens) {
			setValueField(row, item);
		}
		return requestLogDto;
	}
	
	private void setValueField(Row row, SelectItem item) {
		String columnName = item.getColumn().getName();
		Object value = row.getValue(item.getColumn());
		
		Field[] fields = requestLogDto.getClass().getDeclaredFields();
		for (Field field : fields) {
			if(field.getName().equals(columnName)) {
				field.setAccessible(true);
				try {
					field.set(requestLogDto, value);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
