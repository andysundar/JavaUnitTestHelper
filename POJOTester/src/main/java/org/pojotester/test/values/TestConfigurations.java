package org.pojotester.test.values;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

public class TestConfigurations<T> {

	private Object object;

	private Field field;
	private T[] assignedValues;
	private T[] expectedValues;
	private Method readMethod;
	private Method writeMethod;

	public List<AssertObject<T>> assertAssignedValues() {
		if (field != null) {
			field.setAccessible(true);
		}
		int length = 0;
		if (assignedValues.length <= expectedValues.length) {
			length = assignedValues.length;
		} else {
			length = expectedValues.length;
		}
		List<AssertObject<T>> values = new LinkedList<>();
		for (int index = 0; index < length; index++) {
			AssertObject<T> assertObject = new AssertObject<>();
			writeValue(assignedValues[index]);
			T returnedValue = readValue();
			assertObject.setReturnedValue(returnedValue);
			assertObject.setExpectedValue(expectedValues[index]);
			values.add(assertObject);
		}
		return values;
	}

	private void writeValue(T value) {
		if (writeMethod != null) {
			Object args[] = { value };
			try {
				writeMethod.invoke(object, args);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		} else {
			try {
				field.set(object, value);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}

		}
	}

	private T readValue() {
		T returnValue = null;
		if (readMethod != null) {
			Object args[] = null;
			try {
				returnValue = (T) readMethod.invoke(object, null);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		} else {
			try {
				returnValue = (T) field.get(object);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}

		}
		return returnValue;
	}

	public T[] getAssignedValues() {
		return assignedValues;
	}

	public void setAssignedValues(T[] assignedValues) {
		this.assignedValues = assignedValues;
	}

	public T[] getExpectedValues() {
		return expectedValues;
	}

	public void setExpectedValues(T[] expectedValues) {
		this.expectedValues = expectedValues;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public Method getReadMethod() {
		return readMethod;
	}

	public void setReadMethod(Method readMethod) {
		this.readMethod = readMethod;
	}

	public Method getWriteMethod() {
		return writeMethod;
	}

	public void setWriteMethod(Method writeMethod) {
		this.writeMethod = writeMethod;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

}
