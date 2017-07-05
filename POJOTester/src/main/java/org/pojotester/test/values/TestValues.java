package org.pojotester.test.values;

import java.lang.reflect.Field;

public class TestValues<T> {

	private Class<?> clazz;
	private Field field;
	private T[] assignedValues;
	private T[] expectedValues;
	
	public T[] getAssignedValues() {
		return assignedValues;
	}
	public  void setAssignedValues(T[] assignedValues) {
		this.assignedValues = assignedValues;
	}
	public T[] getExpectedValues() {
		return expectedValues;
	}
	public void setExpectedValues(T[] expectedValues) {
		this.expectedValues = expectedValues;
	}
	public Class<?> getClazz() {
		return clazz;
	}
	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}
	public Field getField() {
		return field;
	}
	public void setField(Field field) {
		this.field = field;
	}
	
}
