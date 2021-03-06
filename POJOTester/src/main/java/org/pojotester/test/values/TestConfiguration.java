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

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.pojotester.type.convertor.ObjectToPrimitiveArray;
import org.pojotester.type.convertor.PrimitiveToObjectArray;
import org.pojotester.utils.ClassUtilities;
import org.pojotester.utils.DefaultValueUtilities;
import org.pojotester.utils.FieldUtilities;
import org.pojotester.utils.MethodUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is responsible to hold all configuration value to do reflection call of methods. 
 * @author Anindya Bandopadhyay
 * @since 1.0
 */
public class TestConfiguration<T> {

	private static final Logger LOGGER = LoggerFactory.getLogger(TestConfiguration.class);
	
	private String classFieldName;
	private Object object;
	private Field field;
	private T[] assignedValues;
	private T[] expectedValues;
	private Method readMethod;
	private Method writeMethod;

	/**
	 * This method create list of {@link AssertObject} objects which contains the return value from the field and expected value.
	 * @return list of {@code AssertObject} objects
	 */
	@SuppressWarnings("unchecked")
	public List<AssertObject<?>> assertAssignedValues() {
		LOGGER.debug("Start assertAssignedValues");
		List<AssertObject<?>> values = Collections.emptyList();
		if(object != null){
			if(assignedValues != null){
				values = populateValues();
			} else {
				Class<?> fieldType = FieldUtilities.getFieldType(field);
				Object fieldValueObject = DefaultValueUtilities.getValueFromMap(fieldType);
				boolean isArray = fieldType.isArray();
				fieldType = isArray ? fieldType.getComponentType() : fieldType;
				if(isArray && fieldType.isPrimitive()){

					if (fieldType == boolean.class) {
						Boolean[] valueArray = PrimitiveToObjectArray.convertPrimitiveToObjectArray((boolean[]) fieldValueObject) ;
						assignedValues = (T[]) valueArray;
					} else if (fieldType == byte.class) {
						Byte[] valueArray = PrimitiveToObjectArray.convertPrimitiveToObjectArray((byte[]) fieldValueObject) ;
						assignedValues = (T[]) valueArray;
					} else if (fieldType == char.class) {
						Character[] valueArray = PrimitiveToObjectArray.convertPrimitiveToObjectArray((char[]) fieldValueObject);
						assignedValues = (T[]) valueArray;
					} else if (fieldType == double.class) {
						Double[] valueArray = PrimitiveToObjectArray.convertPrimitiveToObjectArray((double[]) fieldValueObject);
						assignedValues = (T[]) valueArray;
					} else if (fieldType == float.class) {
						Float[] valueArray = PrimitiveToObjectArray.convertPrimitiveToObjectArray((float[]) fieldValueObject);
						assignedValues = (T[]) valueArray;
					} else if (fieldType == int.class) {
						Integer[] valueArray = PrimitiveToObjectArray.convertPrimitiveToObjectArray((int[]) fieldValueObject);
						assignedValues = (T[]) valueArray;
					} else if (fieldType == long.class) {
						Long[] valueArray = PrimitiveToObjectArray.convertPrimitiveToObjectArray((long[]) fieldValueObject);
						assignedValues = (T[]) valueArray;							
					} else if (fieldType == short.class) {
						Short[] valueArray =  PrimitiveToObjectArray.convertPrimitiveToObjectArray((short[]) fieldValueObject);
						assignedValues = (T[]) valueArray;
					}
				
				
					values = populateValues();
				} else if(fieldType.isEnum()) { 
					int length = fieldType.getEnumConstants().length;
					fieldValueObject = fieldType.getEnumConstants()[length-1];
					AssertObject<T> assertObject = null;
					assertObject = createAssertObject(fieldType, fieldValueObject, isArray);
					
					values = addAssertObject(assertObject);
					
				} else {
					AssertObject<T> assertObject = null;
					if (fieldValueObject == null) {
						fieldValueObject = ClassUtilities.createObjectUsingOtherConstructor(fieldType);
						
						assertObject = createAssertObject(fieldType, fieldValueObject, isArray);
						
					} else {
						assertObject = invokeReadWriteMethod((T) fieldValueObject,	(T) fieldValueObject);
					}

					values = addAssertObject(assertObject);
				}
				
			}
		}
		return values;
	}


	private List<AssertObject<?>> addAssertObject(AssertObject<T> assertObject) {
		List<AssertObject<?>> values = new LinkedList<>();
		if (assertObject != null) {
			values.add(assertObject);
		}
		return values;
	}


	@SuppressWarnings("unchecked")
	private AssertObject<T> createAssertObject(Class<?> fieldType, Object fieldValueObject, boolean isArray) {
		AssertObject<T> assertObject;
		if(isArray){
			assignedValues = (T[])Array.newInstance(fieldType, 1);
			assignedValues[0] = (T) fieldValueObject;	
			assertObject = invokeReadWriteMethod((T) assignedValues,	(T) expectedValues);
			assignedValues = null;
		} else {
			assertObject = invokeReadWriteMethod((T) fieldValueObject,	(T) fieldValueObject);
		}
		return assertObject;
	}


	private List<AssertObject<?>> populateValues() {
		List<AssertObject<?>> values = new LinkedList<>();
		int length = 0;
		if(expectedValues == null){
			expectedValues = assignedValues;
		}
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
		if(returnedValue != expectedValue){
			assertObject.setExpectedValue(returnedValue);
		} else {
			assertObject.setExpectedValue(expectedValue);
		}
		assertObject.setReturnedValue(returnedValue);
		assertObject.setMessage(classFieldName);
		return assertObject;
	}

	private void writeValue(T value) {
		Class<?> fieldType = field.getType();
		boolean isArray = fieldType.isArray();
		fieldType = isArray ? fieldType.getComponentType() : fieldType;
		if(isArray && fieldType.isPrimitive()){
			convertObjectToPrimitiveForWriteMethod(value);
		} else {
			Object args[] = { value };
			
			if (writeMethod != null) {
				MethodUtilities.methodInvocation(writeMethod, object, args);
			} else {
				FieldUtilities.setFieldValue(field, object, value);
			}
		}
		
		
	}

	
	@SuppressWarnings("unchecked")
	private T readValue() {
		T returnValue = null;
		if (readMethod != null) {
			Object[] args = {};
			returnValue = (T) MethodUtilities.methodInvocation(readMethod, object, args);
		} else {
			returnValue = (T) FieldUtilities.getFieldValue(field, object);
		}
		returnValue = convertPrimitiveToObjectForReadMethod(returnValue);
		return returnValue;
	}

	@SuppressWarnings("unchecked")
	private T convertPrimitiveToObjectForReadMethod(T returnValue) {
		Class<?> fieldType = field.getType();
		boolean isArray = fieldType.isArray();
		fieldType = isArray ? fieldType.getComponentType() : fieldType;
		if(isArray && fieldType.isPrimitive()){
			if (fieldType == boolean.class) {
				Boolean[] valueArray = PrimitiveToObjectArray.convertPrimitiveToObjectArray((boolean[]) returnValue) ;
				returnValue = (T) valueArray[0];
			} else if (fieldType == byte.class) {
				Byte[] valueArray = PrimitiveToObjectArray.convertPrimitiveToObjectArray((byte[]) returnValue) ;
				returnValue = (T) valueArray[0];
			} else if (fieldType == char.class) {
				Character[] valueArray = PrimitiveToObjectArray.convertPrimitiveToObjectArray((char[]) returnValue);
				returnValue = (T) valueArray[0];
			} else if (fieldType == double.class) {
				Double[] valueArray = PrimitiveToObjectArray.convertPrimitiveToObjectArray((double[]) returnValue);
				returnValue = (T) valueArray[0];
			} else if (fieldType == float.class) {
				Float[] valueArray = PrimitiveToObjectArray.convertPrimitiveToObjectArray((float[]) returnValue);
				returnValue = (T) valueArray[0];
			} else if (fieldType == int.class) {
				Integer[] valueArray = PrimitiveToObjectArray.convertPrimitiveToObjectArray((int[]) returnValue);
				returnValue = (T) valueArray[0];
			} else if (fieldType == long.class) {
				Long[] valueArray = PrimitiveToObjectArray.convertPrimitiveToObjectArray((long[]) returnValue);
				returnValue = (T) valueArray[0];								
			} else if (fieldType == short.class) {
				Short[] valueArray =  PrimitiveToObjectArray.convertPrimitiveToObjectArray((short[]) returnValue);
				returnValue = (T) valueArray[0];
			}
		
		}
		return returnValue;
	}

	private void convertObjectToPrimitiveForWriteMethod(T value) {
		Class<?> fieldType = field.getType();
		Object[] args = new Object[1];
		boolean isArray = fieldType.isArray();
		fieldType = isArray ? fieldType.getComponentType() : fieldType;
		if (fieldType.isPrimitive()) {
			if (fieldType == boolean.class) {
				Boolean[] valueArray = { (Boolean) value };
				boolean[] primitives = ObjectToPrimitiveArray.convertObjectToPrimitiveArray(valueArray);
				args[0] = isArray ? primitives : primitives[0];
				if (writeMethod != null) {
					MethodUtilities.methodInvocation(writeMethod, object, args);
				} else {
					FieldUtilities.setFieldValue(field, object, primitives);
				}
			} else if (fieldType == byte.class) {
				Byte[] valueArray = { (Byte) value };
				byte[] primitives = ObjectToPrimitiveArray.convertObjectToPrimitiveArray(valueArray);
				args[0] = isArray ? primitives : primitives[0];
				if (writeMethod != null) {
					MethodUtilities.methodInvocation(writeMethod, object, args);
				} else {
					FieldUtilities.setFieldValue(field, object, primitives);
				}
			} else if (fieldType == char.class) {
				Character[] valueArray = { (Character) value };
				char[] primitives = ObjectToPrimitiveArray.convertObjectToPrimitiveArray(valueArray);
				args[0] = isArray ? primitives : primitives[0];
				if (writeMethod != null) {
					MethodUtilities.methodInvocation(writeMethod, object, args);
				} else {
					FieldUtilities.setFieldValue(field, object, primitives);
				}
			} else if (fieldType == double.class) {
				Double[] valueArray = { (Double) value };
				double[] primitives = ObjectToPrimitiveArray.convertObjectToPrimitiveArray(valueArray);
				args[0] = isArray ? primitives : primitives[0];
				if (writeMethod != null) {
					MethodUtilities.methodInvocation(writeMethod, object, args);
				} else {
					FieldUtilities.setFieldValue(field, object, primitives);
				}
			} else if (fieldType == float.class) {
				Float[] valueArray = { (Float) value };
				float[] primitives = ObjectToPrimitiveArray.convertObjectToPrimitiveArray(valueArray);
				args[0] = isArray ? primitives : primitives[0];
				if (writeMethod != null) {
					MethodUtilities.methodInvocation(writeMethod, object, args);
				} else {
					FieldUtilities.setFieldValue(field, object, primitives);
				}
			} else if (fieldType == int.class) {
				Integer[] valueArray = { (Integer) value };
				int[] primitives = ObjectToPrimitiveArray.convertObjectToPrimitiveArray(valueArray);
				args[0] = isArray ? primitives : primitives[0];
				if (writeMethod != null) {
					MethodUtilities.methodInvocation(writeMethod, object, args);
				} else {
					FieldUtilities.setFieldValue(field, object, primitives);
				}
			} else if (fieldType == long.class) {
				Long[] valueArray = { (Long) value };
				long[] primitives = ObjectToPrimitiveArray.convertObjectToPrimitiveArray(valueArray);
				args[0] = isArray ? primitives : primitives[0];
				if (writeMethod != null) {
					MethodUtilities.methodInvocation(writeMethod, object, args);
				} else {
					FieldUtilities.setFieldValue(field, object, primitives);
				}
			} else if (fieldType == short.class) {
				Short[] valueArray = { (Short) value };
				short[] primitives = ObjectToPrimitiveArray.convertObjectToPrimitiveArray(valueArray);
				args[0] = isArray ? primitives : primitives[0];
				if (writeMethod != null) {
					MethodUtilities.methodInvocation(writeMethod, object, args);
				} else {
					FieldUtilities.setFieldValue(field, object, primitives);
				}
			}
		}

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

	public String getClassFieldName() {
		return classFieldName;
	}

	public void setClassFieldName(String classFieldName) {
		this.classFieldName = classFieldName;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) { this.object = object;}

}
