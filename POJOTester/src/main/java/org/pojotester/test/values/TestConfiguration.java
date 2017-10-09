/*******************************************************************************
 * Copyright 2017 Anindya Bandopadhyay (anindyabandopadhyay@gmail.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.pojotester.test.values;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.pojotester.type.convertor.ObjectToPrimitiveArray;
import org.pojotester.utils.ClassUtilities;
import org.pojotester.utils.DefaultValueUtilities;


public class TestConfiguration<T> {

	private Object object;
	private Method createObjectMethod;
	private Field field;
	private T[] assignedValues;
	private T[] expectedValues;
	private Method readMethod;
	private Method writeMethod;

	public List<AssertObject<?>> assertAssignedValues(Class<?> clazz) {
		if (field != null) {
			field.setAccessible(true);
		}

		if(createObjectMethod == null) {
			object = ClassUtilities.createObject(clazz);
		} else {
			object = ClassUtilities.createObjectUsingStaticMethod(clazz, createObjectMethod);
		}
		List<AssertObject<?>> values = Collections.emptyList();
		if(object != null){
			if(assignedValues != null){
				values = populateAnnotatedValues();
			} else {
				values = new LinkedList<AssertObject<?>>();
				@SuppressWarnings("unchecked")
				T object = (T) DefaultValueUtilities.getValueFromMap(field.getType());
				AssertObject<T> assertObject = invokeReadWriteMethod(object, object);
				values.add(assertObject);
			}
		}
		return values;
	}

	private List<AssertObject<?>> populateAnnotatedValues() {
		List<AssertObject<?>> values = new LinkedList<AssertObject<?>>();
		int length = 0;
		if (assignedValues.length <= expectedValues.length) {
			length = assignedValues.length;
		} else {
			length = expectedValues.length;
		}
		for (int index = 0; index < length; index++) {
			AssertObject<T> assertObject = invokeReadWriteMethod(assignedValues[index],expectedValues[index]);
			values.add(assertObject);
		}
		return values;
	}

	private AssertObject<T> invokeReadWriteMethod(T assignedValue, T expectedValue) {
		AssertObject<T> assertObject = new AssertObject<>();
		writeValue(assignedValue);
		T returnedValue = readValue();
		assertObject.setReturnedValue(returnedValue);
		assertObject.setExpectedValue(expectedValue);
		return assertObject;
	}

	private void writeValue(T value) {
		Object args[] = { value };
		args = convertObjectToPrimitiveArray(value);
		if(args[0] == null){
			args[0] = value;
		}
		if (writeMethod != null) {
			try {
				writeMethod.invoke(object, args);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		} else {
			try {
				field.set(object, args[0]);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}

		}
	}

	
	@SuppressWarnings("unchecked")
	private T readValue() {
		T returnValue = null;
		if (readMethod != null) {
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

	private Object[] convertObjectToPrimitiveArray(T value) {
		Class<?> fieldType = field.getType();
		Object[] args = new Object[1];
		boolean isArray = fieldType.isArray();
		if(fieldType.isPrimitive()){
			if (fieldType == boolean.class) {
				Boolean[] valueArray = {(Boolean) value};
				boolean[] primitives = ObjectToPrimitiveArray.convertObjectToPrimitiveArray(valueArray);
				args[0] = primitives[0];
			} else if (fieldType == byte.class) {
				Byte[] valueArray = {(Byte) value};
				byte[] primitives = ObjectToPrimitiveArray.convertObjectToPrimitiveArray(valueArray);
				args[0] = primitives[0];
			} else if(fieldType == char.class){
				Character[] valueArray = {(Character) value};
				char[] primitives = ObjectToPrimitiveArray.convertObjectToPrimitiveArray((Character[]) valueArray);
				args[0] = primitives[0];
			} else if(fieldType == double.class){
				Double[] valueArray = {(Double) value};
				double[] primitives = ObjectToPrimitiveArray.convertObjectToPrimitiveArray((Double[]) valueArray);
				args[0] = primitives[0];
			} else if(fieldType == float.class){
				Float[] valueArray = {(Float) value};
				float[] primitives = ObjectToPrimitiveArray.convertObjectToPrimitiveArray((Float[]) valueArray);
				args[0] = primitives[0];
			} else if(fieldType == int.class){
				Integer[] valueArray = {(Integer) value};
				int[] primitives = ObjectToPrimitiveArray.convertObjectToPrimitiveArray((Integer[]) valueArray);
				args[0] = primitives[0];
			} else if(fieldType == long.class){
				Long[] valueArray = {(Long) value};
				long[] primitives = ObjectToPrimitiveArray.convertObjectToPrimitiveArray((Long[]) valueArray);
				args[0] = primitives[0];
			} else if(fieldType == short.class){
				Short[] valueArray = {(Short) value};
				short[] primitives = ObjectToPrimitiveArray.convertObjectToPrimitiveArray((Short[]) valueArray);
				args[0] = primitives[0];
			}
		} else if(isArray){
			fieldType = fieldType.getComponentType();
			if(fieldType.isPrimitive()){
				if (fieldType == boolean.class) {
					boolean[] primitives = ObjectToPrimitiveArray.convertObjectToPrimitiveArray((Boolean[]) value);
					args[0] = primitives;
				} else if (fieldType == byte.class) {
					byte[] primitives = ObjectToPrimitiveArray.convertObjectToPrimitiveArray((Byte[]) value);
					args[0] = primitives;
				} else if(fieldType == char.class){
					char[] primitives = ObjectToPrimitiveArray.convertObjectToPrimitiveArray((Character[]) value);
					args[0] = primitives;
				} else if(fieldType == double.class){
					double[] primitives = ObjectToPrimitiveArray.convertObjectToPrimitiveArray((Double[]) value);
					args[0] = primitives;
				} else if(fieldType == float.class){
					float[] primitives = ObjectToPrimitiveArray.convertObjectToPrimitiveArray((Float[]) value);
					args[0] = primitives;
				} else if(fieldType == int.class){
					int[] primitives = ObjectToPrimitiveArray.convertObjectToPrimitiveArray((Integer[]) value);
					args[0] = primitives;
				} else if(fieldType == long.class){
					long[] primitives = ObjectToPrimitiveArray.convertObjectToPrimitiveArray((Long[]) value);
					args[0] = primitives;
				} else if(fieldType == short.class){
					short[] primitives = ObjectToPrimitiveArray.convertObjectToPrimitiveArray((Short[]) value);
					args[0] = primitives;
				}
			} 
		} 
		return args;
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

	public Method getCreateObjectMethod() {
		return createObjectMethod;
	}

	public void setCreateObjectMethod(Method createObjectMethod) {
		this.createObjectMethod = createObjectMethod;
	}

}
