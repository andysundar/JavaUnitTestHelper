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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.pojotester.type.convertor.ObjectToPrimitiveArray;
import org.pojotester.type.convertor.PrimitiveToObjectArray;
import org.pojotester.utils.ClassUtilities;
import org.pojotester.utils.DefaultValueUtilities;
import org.pojotester.utils.FieldUtilities;
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
	private Method createObjectMethod;
	private Field field;
	private T[] assignedValues;
	private T[] expectedValues;
	private Method readMethod;
	private Method writeMethod;

	/**
	 * This method create list of {@link AssertObject} objects which contains the return value from the field and expected value. 
	 * @param clazz
	 * @return list of {@code AssertObject} objects
	 */
	public List<AssertObject<?>> assertAssignedValues(Class<?> clazz) {


		createObject(clazz);
		List<AssertObject<?>> values = Collections.emptyList();
		if(object != null){
			if(assignedValues != null){
				values = populateValues();
			} else {
				Object fieldValueObject = DefaultValueUtilities.getValueFromMap(field.getType());
				Class<?> fieldType = FieldUtilities.getFieldType(field);
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
				} else {
					AssertObject<T> assertObject = null;
					if (fieldValueObject == null) {
						fieldValueObject = ClassUtilities.createObjectUsingOtherConstructor(fieldType);
						assignedValues = (T[])Array.newInstance(fieldType, 1);
						if(isArray){
							assignedValues[0] = (T) fieldValueObject;	
							assertObject = invokeReadWriteMethod((T) assignedValues,	(T) expectedValues);
							assignedValues = null;
						} else {
							assertObject = invokeReadWriteMethod((T) fieldValueObject,	(T) fieldValueObject);
						}
						
					} else {
						assertObject = invokeReadWriteMethod((T) fieldValueObject,	(T) fieldValueObject);
					}

					if (assertObject != null) {
						values = new LinkedList<>();
						values.add(assertObject);
					}
				}
				
			}
		}
		return values;
	}

	private void createObject(Class<?> clazz) {
		if(createObjectMethod == null) {
			object = ClassUtilities.createObject(clazz);
		} else {
			object = ClassUtilities.createObjectUsingStaticMethod(clazz, createObjectMethod);
		}
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
				try {
					writeMethod.invoke(object, args);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					LOGGER.debug(writeMethod.getName() + " invoke fail.", e);
					e.printStackTrace();
				}
			} else {
				FieldUtilities.setFieldValue(field, object, args[0]);
			}
		}
		
		
	}

	
	@SuppressWarnings("unchecked")
	private T readValue() {
		T returnValue = null;
		if (readMethod != null) {
			try {
				Object[] args = {};
				returnValue = (T) readMethod.invoke(object, args);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				LOGGER.debug(readMethod.getName() + " method invoke fail.", e);
				e.printStackTrace();
			}
		} else {
			returnValue = (T) FieldUtilities.getFieldValue(field, object);
		}
		returnValue = convertPrimitiveToObjectForReadMethod(returnValue);
		return returnValue;
	}

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
					try {
						writeMethod.invoke(object, args);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						LOGGER.debug(writeMethod.getName() + " method invoke fail.", e);
						e.printStackTrace();
					}
				} else {
					FieldUtilities.setFieldValue(field, object, primitives);
				}
			} else if (fieldType == byte.class) {
				Byte[] valueArray = { (Byte) value };
				byte[] primitives = ObjectToPrimitiveArray.convertObjectToPrimitiveArray(valueArray);
				args[0] = isArray ? primitives : primitives[0];
				if (writeMethod != null) {
					try {
						writeMethod.invoke(object, args);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						LOGGER.debug(writeMethod.getName() + " method invoke fail.", e);
						e.printStackTrace();
					}
				} else {
					FieldUtilities.setFieldValue(field, object, primitives);
				}
			} else if (fieldType == char.class) {
				Character[] valueArray = { (Character) value };
				char[] primitives = ObjectToPrimitiveArray.convertObjectToPrimitiveArray(valueArray);
				args[0] = isArray ? primitives : primitives[0];
				if (writeMethod != null) {
					try {
						writeMethod.invoke(object, args);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						LOGGER.debug(writeMethod.getName() + " method invoke fail.", e);
						e.printStackTrace();
					}
				} else {
					FieldUtilities.setFieldValue(field, object, primitives);
				}
			} else if (fieldType == double.class) {
				Double[] valueArray = { (Double) value };
				double[] primitives = ObjectToPrimitiveArray.convertObjectToPrimitiveArray(valueArray);
				args[0] = isArray ? primitives : primitives[0];
				if (writeMethod != null) {
					try {
						writeMethod.invoke(object, args);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						LOGGER.debug(writeMethod.getName() + " method invoke fail.", e);
						e.printStackTrace();
					}
				} else {
					FieldUtilities.setFieldValue(field, object, primitives);
				}
			} else if (fieldType == float.class) {
				Float[] valueArray = { (Float) value };
				float[] primitives = ObjectToPrimitiveArray.convertObjectToPrimitiveArray(valueArray);
				args[0] = isArray ? primitives : primitives[0];
				if (writeMethod != null) {
					try {
						writeMethod.invoke(object, args);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						LOGGER.debug(writeMethod.getName() + " method invoke fail.", e);
						e.printStackTrace();
					}
				} else {
					FieldUtilities.setFieldValue(field, object, primitives);
				}
			} else if (fieldType == int.class) {
				Integer[] valueArray = { (Integer) value };
				int[] primitives = ObjectToPrimitiveArray.convertObjectToPrimitiveArray(valueArray);
				args[0] = isArray ? primitives : primitives[0];
				if (writeMethod != null) {
					try {
						writeMethod.invoke(object, args);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						LOGGER.debug(writeMethod.getName() + " method invoke fail.", e);
						e.printStackTrace();
					}
				} else {
					FieldUtilities.setFieldValue(field, object, primitives);
				}
			} else if (fieldType == long.class) {
				Long[] valueArray = { (Long) value };
				long[] primitives = ObjectToPrimitiveArray.convertObjectToPrimitiveArray(valueArray);
				args[0] = isArray ? primitives : primitives[0];
				if (writeMethod != null) {
					try {
						writeMethod.invoke(object, args);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						LOGGER.debug(writeMethod.getName() + " method invoke fail.", e);
						e.printStackTrace();
					}
				} else {
					FieldUtilities.setFieldValue(field, object, primitives);
				}
			} else if (fieldType == short.class) {
				Short[] valueArray = { (Short) value };
				short[] primitives = ObjectToPrimitiveArray.convertObjectToPrimitiveArray(valueArray);
				args[0] = isArray ? primitives : primitives[0];
				if (writeMethod != null) {
					try {
						writeMethod.invoke(object, args);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						LOGGER.debug(writeMethod.getName() + " method invoke fail.", e);
						e.printStackTrace();
					}
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

	public Method getCreateObjectMethod() {
		return createObjectMethod;
	}

	public void setCreateObjectMethod(Method createObjectMethod) {
		this.createObjectMethod = createObjectMethod;
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

}
