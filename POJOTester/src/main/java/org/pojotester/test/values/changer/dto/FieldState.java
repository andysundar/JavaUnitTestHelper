package org.pojotester.test.values.changer.dto;

import java.lang.reflect.Field;

public class FieldState<T> {
	
	private T previousValue;
	private T currentValue;
	private Field field;
	private Object obj;	
	
	public FieldState(T previousValue, T currentValue, Field field, Object obj) {
		this.previousValue = previousValue;
		this.currentValue = currentValue;
		this.field = field;
		this.obj = obj;
	}
	
	public T getPreviousValue() {
		return previousValue;
	}
	
	public T getCurrentValue() {
		return currentValue;
	}

	public Field getField() {
		return field;
	}

	public Object getObj() {
		return obj;
	}

}
