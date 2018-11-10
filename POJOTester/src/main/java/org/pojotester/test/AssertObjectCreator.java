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

package org.pojotester.test;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.pojotester.pack.scan.LoadClassIfAskedFor;
import org.pojotester.pack.scan.LoadClassIfNotIgnored;
import org.pojotester.pack.scan.PackageScan;
import org.pojotester.reflection.annotation.ReflectionFieldLevel;
import org.pojotester.reflection.annotation.ReflectionMethodLevel;
import org.pojotester.test.values.AssertObject;
import org.pojotester.test.values.TestConfiguration;
import org.pojotester.test.values.changer.FieldValueChanger;
import org.pojotester.utils.ClassUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class create assert object for unit testing. 
 * @author Anindya Bandopadhyay
 * @since 1.0
 */
public class AssertObjectCreator implements IAssertObjectCreator {

	private static final Logger LOGGER = LoggerFactory.getLogger(AssertObjectCreator.class);
	private static final String EQUALS = "equals";
	private static final String HASH_CODE = "hashCode";
	private static final String TO_STRING = "toString";
	
	private boolean loadClassesAskedFor;
//	private boolean testToStringMethod;
//	private boolean testHashCodeMethod;
//	private boolean testEqualsMethod;

	/**
	 * Object created using this constructor will consider all classes in a package for unit testing 
	 * if and only if {@code @IgnoreClass} is declared. 
	 * @since 1.0
	 */
	public AssertObjectCreator(){
		this(false);
	}
	
	/**
	 * Object created using this constructor with parameter as {@code true} will consider only {@code @TestThisClass} 
	 * declared classes in a package for unit testing and if it is used with parameter {@code false} it is act same 
	 * as default constructor. 
	 * @since 1.0
	 */
	public AssertObjectCreator(boolean loadClassesAskedFor){
		this.loadClassesAskedFor = loadClassesAskedFor;
	}
	
	/**
	 * This method return list of assertion objects created from the classes which are present in parameterised packages.   
	 * @param packagesToScan
	 * @return list of {@code AssertObject} objects
	 * @since 1.0
	 */
	public List<AssertObject<?>> getAssertObjects(final String... packagesToScan) {
		List<AssertObject<?>> assertObjectList = new LinkedList<>();
		PackageScan packageScan = loadClassesAskedFor ? new LoadClassIfAskedFor():new LoadClassIfNotIgnored();
		Set<Class<?>> uniqueClasses = packageScan.getClasses(packagesToScan);
		for (Class<?> clazz : uniqueClasses) {
			Method[] methods = clazz.getDeclaredMethods();
			Method createObjectMethod = findCreateObjectMethod(methods);
			Map<String, TestConfiguration<?>> fieldTestConfigurationMap = createFieldAndTestConfiguration(clazz, methods, createObjectMethod);
			List<AssertObject<?>> assertObjects = createAssertObjectList(clazz, fieldTestConfigurationMap);
			assertObjectList.addAll(assertObjects);
	
			Class<?>[] args = {};
			Method toStringMethod = getDeclaredMethod(clazz, TO_STRING, args);
			Method hashCodeMethod = getDeclaredMethod(clazz, HASH_CODE, args);	
			Method equalsMethod = getDeclaredMethod(clazz, EQUALS, Object.class);

			LinkedList<TestConfiguration<?>> testConfigurationsX1 = null;
			LinkedList<TestConfiguration<?>> testConfigurationsX2 = null;
			LinkedList<TestConfiguration<?>> testConfigurationsY2 = null;

			
			Object objectX1 = null;
			Object objectX2 = null;
			Object objectY1 = null;
			Object objectY2 = null;
			if(toStringMethod != null || hashCodeMethod != null || equalsMethod != null) {
				Map<String, TestConfiguration<?>> fieldTestConfigurationMapX1 =  createFieldAndTestConfiguration(clazz, methods, createObjectMethod);
				Map<String, TestConfiguration<?>> fieldTestConfigurationMapY2 =  createFieldAndTestConfiguration(clazz, methods, createObjectMethod);
				Set<Entry<String, TestConfiguration<?>>> setX1 = fieldTestConfigurationMap.entrySet();
				Set<Entry<String, TestConfiguration<?>>> setX2 = fieldTestConfigurationMapX1.entrySet();
				Set<Entry<String, TestConfiguration<?>>> setY2 = fieldTestConfigurationMapY2.entrySet();
				testConfigurationsX1 = getTestConfigurations(setX1, clazz);
				testConfigurationsX2 = getTestConfigurations(setX2, clazz);
				testConfigurationsY2 = getTestConfigurations(setY2, clazz);

				objectX1 = testConfigurationsX1.getLast().getObject();
				objectX2 = testConfigurationsX2.getLast().getObject();
				objectY1 = createObject(clazz, createObjectMethod);
				objectY2 = testConfigurationsY2.getLast().getObject();

			}
			
			createTestForToString(assertObjectList, toStringMethod, objectX1, objectX2, objectY1, objectY2);
			

			if (equalsMethod != null) {
				// object.equals(null) should return false
				AssertObject<Boolean> objectEqualsNull = createAssertObject(objectX1.equals(null), false,
						"object.equals(null) must return false");
				assertObjectList.add(objectEqualsNull);

				// object.equals(objectEqualsOtherObject) should return false
				AssertObject<Boolean> objectEqualsOtherObject = createAssertObject(objectX1.equals(new Object()), false,
						"object.equals(otherObject) must return false");
				assertObjectList.add(objectEqualsOtherObject);
				
				// Reflexive
				AssertObject<Boolean> reflexiveEquals = createAssertObject(objectX1.equals(objectX1), true,
						"Reflexive Test: x.equals(x) return true.");
				assertObjectList.add(reflexiveEquals);
				if (hashCodeMethod != null) {
					AssertObject<Integer> reflexiveHashCode = createAssertObject(objectX1.hashCode(),
							objectX1.hashCode(), "Reflexive Test: x.hashCode() == x.hashCode()");
					assertObjectList.add(reflexiveHashCode);

					Boolean reflexiveEqualsMaintained = reflexiveEquals.getExpectedValue()
							.equals(reflexiveEquals.getReturnedValue());
					Boolean reflexiveHashCodeMaintained = reflexiveHashCode.getExpectedValue()
							.equals(reflexiveHashCode.getReturnedValue());
					AssertObject<Boolean> reflexiveHashCodeEquals = createAssertObject(reflexiveEqualsMaintained,
							reflexiveHashCodeMaintained, "Both hashCode & equals must maintained reflexive property ");
					assertObjectList.add(reflexiveHashCodeEquals);
				}
				// Symmetric
				AssertObject<Boolean> symmetricEquals = createAssertObject(objectX1.equals(objectX2),
						objectX2.equals(objectX1), "Symmetric Test: x.equals(y) == y.equals(x)");
				assertObjectList.add(symmetricEquals);

				if (hashCodeMethod != null) {
					AssertObject<Integer> symmetricHashCode = createAssertObject(objectX1.hashCode(),
							objectX2.hashCode(),
							"Symmetric Test: If values of x & y are same then x.hashCode() == y.hashCode()");
					assertObjectList.add(symmetricHashCode);

					Boolean symmetricEqualsMaintained = symmetricEquals.getExpectedValue()
							.equals(symmetricEquals.getReturnedValue());
					Boolean symmetricHashCodeMaintained = symmetricHashCode.getExpectedValue()
							.equals(symmetricHashCode.getReturnedValue());
					AssertObject<Boolean> symmetricHashCodeEquals = createAssertObject(symmetricEqualsMaintained,
							symmetricHashCodeMaintained, "Both hashCode & equals must maintained symmetric property");
					assertObjectList.add(symmetricHashCodeEquals);
				}

				// Transitive
				AssertObject<Boolean> transitiveEquals = createAssertObject(
						objectX1.equals(objectX2) && objectX2.equals(objectY2), objectY2.equals(objectX1),
						"Transitive Test: if x.equals(y) return true and y.equals(z) return true then \n z.equals(x) also return true");
				assertObjectList.add(transitiveEquals);

				if (hashCodeMethod != null) {
					AssertObject<Boolean> transitiveHashCode = createAssertObject(
							(objectX1.hashCode() == objectX2.hashCode() && objectY2.hashCode() == objectX1.hashCode()),
							(objectY2.hashCode() == objectX2.hashCode()),
							"Transitive Test: If values of x & y are same then x.hashCode() == y.hashCode()");
					assertObjectList.add(transitiveHashCode);

					Boolean transitiveEqualsMaintained = transitiveEquals.getExpectedValue()
							.equals(transitiveEquals.getReturnedValue());
					Boolean transitiveHashCodeMaintained = transitiveHashCode.getExpectedValue()
							.equals(transitiveHashCode.getReturnedValue());
					AssertObject<Boolean> transitiveHashCodeEquals = createAssertObject(transitiveEqualsMaintained,
							transitiveHashCodeMaintained, "Both hashCode & equals must maintained transitive property");
					assertObjectList.add(transitiveHashCodeEquals);
				}

				// Consistent
				AssertObject<Boolean> consistentEquals = createAssertObject(objectX1.equals(objectY1),
						objectY1.equals(objectX1),
						"Consistent Test: If x1 and x2 different object and different value(s) \n x1.equals(x2) return false and x2.equals(x1) also return false.");
				assertObjectList.add(consistentEquals);
				if (hashCodeMethod != null) {
					AssertObject<Boolean> consistentHashCode = createAssertObject(
							(objectX1.hashCode() == objectY1.hashCode()), false,
							"Consistent Test: If x1 and x2 different object and different value(s) \n x1.hashCode() !+ x2.hashCode()");
					assertObjectList.add(consistentHashCode);

					Boolean consistentEqualsMaintained = consistentEquals.getExpectedValue()
							.equals(consistentEquals.getReturnedValue());
					Boolean consistentHashCodeMaintained = consistentHashCode.getExpectedValue()
							.equals(consistentHashCode.getReturnedValue());
					AssertObject<Boolean> consistentHashCodeEquals = createAssertObject(consistentEqualsMaintained,
							consistentHashCodeMaintained, "Both hashCode & equals must maintained consistent property");
					assertObjectList.add(consistentHashCodeEquals);
				}
				
				// Equals coverage
				
				FieldValueChanger fieldValueChanger = FieldValueChanger.getInstance();
				for (TestConfiguration<?> testConfiguration : testConfigurationsX1) {
					Object tempObject = testConfiguration.getObject();
				    fieldValueChanger.changeValue(testConfiguration.getField(), tempObject);
					AssertObject<Boolean> coverageEquals = createAssertObject(objectX1.equals(tempObject),
							tempObject.equals(objectX1), "Symmetric Test: x.equals(y) == y.equals(x)");
					assertObjectList.add(coverageEquals);
				}


			} else if (hashCodeMethod != null) {

				AssertObject<Integer> reflexiveHashCode = createAssertObject(objectX1.hashCode(), objectX1.hashCode(),
						"Reflexive Test: x.hashCode() == x.hashCode()");
				assertObjectList.add(reflexiveHashCode);
				
				AssertObject<Integer> symmetricHashCode = createAssertObject(objectX1.hashCode(), objectX2.hashCode(),
						"Symmetric Test: If values of x & y are same then x.hashCode() == y.hashCode()");
				assertObjectList.add(symmetricHashCode);
				
				AssertObject<Boolean> transitiveHashCode = createAssertObject(
						(objectX1.hashCode() == objectX2.hashCode() && objectY2.hashCode() == objectX1.hashCode() ), 
						(objectY2.hashCode() == objectX2.hashCode()),
						"Transitive Test: If values of x & y are same then x.hashCode() == y.hashCode()");
				assertObjectList.add(transitiveHashCode);
				
				AssertObject<Boolean> consistentHashCode = createAssertObject((objectX1.hashCode() == objectY1.hashCode()), false,
						"Consistent Test: If x1 and x2 different object and different value(s) \n x1.hashCode() !+ x2.hashCode()");
				assertObjectList.add(consistentHashCode);
			}
		}
		return assertObjectList;
	}

	private void createTestForToString(List<AssertObject<?>> assertObjectList, Method toStringMethod, Object objectX1,
			Object objectX2, Object objectY1, Object objectY2) {
		if(toStringMethod != null) {
			
			String x1Str = objectX1.toString();
			String x2Str = objectX2.toString();
			AssertObject<String> whenBothSameValues = createAssertObject(x1Str, x2Str, 
					"toString method when different objects but same value(s).");
			assertObjectList.add(whenBothSameValues);
			
			
			
			String y1Str = objectY1.toString();
			String y2Str = objectY2.toString();
			AssertObject<Boolean> whenDifferentValues = createAssertObject(y2Str.equals(y1Str), false, 
					"toString method when different objects and different value(s).");
			assertObjectList.add(whenDifferentValues);
		}
	}

	private <T> AssertObject<T> createAssertObject(T returnedValue, T expectedValue, String message) {
		AssertObject<T> assertObject = new AssertObject<>();
		assertObject.setMessage(message);
		assertObject.setReturnedValue(returnedValue);
		assertObject.setExpectedValue(expectedValue);
		return assertObject;
	}

	private Method getDeclaredMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
		Method method = null;
		try {
			method = clazz.getDeclaredMethod(methodName, parameterTypes);
		} catch (NoSuchMethodException | SecurityException e) {
			LOGGER.error(String.format("%s method not found", methodName), e);
		}
		return method;
	}

	private Object createObject(Class<?> clazz, Method createObjectMethod) {
		Object object = null;
		if(createObjectMethod == null) {
			object = ClassUtilities.createObject(clazz);
		} else {
			object = ClassUtilities.createObjectUsingStaticMethod(clazz, createObjectMethod);
		}
		return object;
	}
	
	private LinkedList<TestConfiguration<?>> getTestConfigurations(Set<Entry<String, TestConfiguration<?>>> set, Class<?> clazz) {
		LinkedList<TestConfiguration<?>> testConfigurations = new LinkedList<>();
		for(Entry<String, TestConfiguration<?>> x : set) {
			TestConfiguration<?> testConfiguration = x.getValue();
			if(testConfiguration != null) {
				testConfiguration.assertAssignedValues(clazz);
				testConfigurations.add(testConfiguration);
			}
		}
		return testConfigurations;
	}


	private Map<String, TestConfiguration<?>> createFieldAndTestConfiguration(Class<?> clazz, Method[] methods, Method createObjectMethod) {
		Map<String, TestConfiguration<?>> fieldTestConfigurationMap = new HashMap<>();
		
		createTestConfigurationsFromIntrospection(clazz, fieldTestConfigurationMap, createObjectMethod);
		createTestConfigurationsFromAnnotations(clazz, fieldTestConfigurationMap, methods, createObjectMethod);
		return fieldTestConfigurationMap;
	}

	private void createTestConfigurationsFromIntrospection(Class<?> clazz,
														   Map<String, TestConfiguration<?>> fieldAssertObjectMap, Method createObjectMethod) {
		BeanInfo beanInfo = null;
		PropertyDescriptor[] propertyDescriptors = null;
		try {
			beanInfo = Introspector.getBeanInfo(clazz, Object.class);
			propertyDescriptors = beanInfo.getPropertyDescriptors();
		} catch (IntrospectionException e) {
			LOGGER.debug("Not able to load properties" , e);
		}
		 
		if (propertyDescriptors != null) {
			for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
				Method readMethod = propertyDescriptor.getReadMethod();
				Method writeMethod = propertyDescriptor.getWriteMethod();
				String fieldName = propertyDescriptor.getName();
				TestConfiguration<?> testConfiguration = createTestConfiguration(clazz, createObjectMethod,
						readMethod, writeMethod, fieldName);
				if (testConfiguration != null) {
					fieldAssertObjectMap.put(testConfiguration.getClassFieldName(), testConfiguration);
				}
			}
		}
	}

	private void createTestConfigurationsFromAnnotations(Class<?> clazz,
			Map<String, TestConfiguration<?>> fieldAssertObjectMap, Method[] methods, Method createObjectMethod) {
		for (Method method : methods) {
			String fieldName = ReflectionMethodLevel.getFieldNameOfReadMethod(method);
			boolean writeMethod = false;
			if (fieldName == null  || fieldName.isEmpty()) {
				fieldName = ReflectionMethodLevel.getFieldNameOfWriteMethod(method);
				writeMethod = true;
			}
			if(fieldName != null && !fieldName.isEmpty()){
				setAnnotatedReadOrWriteMethod(clazz, fieldAssertObjectMap, createObjectMethod, method, fieldName,
						writeMethod);
			}
		}
	}

	private void setAnnotatedReadOrWriteMethod(Class<?> clazz, Map<String, TestConfiguration<?>> fieldAssertObjectMap,
											   Method createObjectMethod, Method method, String fieldName, boolean writeMethod) {
		if(fieldName != null && !fieldName.isEmpty()) {
			String classFieldName = clazz.getName() + "." + fieldName;
			TestConfiguration<?> testConfiguration = fieldAssertObjectMap.get(classFieldName);
			if (testConfiguration != null) {
				testConfiguration.setCreateObjectMethod(createObjectMethod);
				Field field = testConfiguration.getField();
				if(!writeMethod){
					if(method.getReturnType() == field.getType()){
						testConfiguration.setReadMethod(method);
					} else {
						logReadMethodMessage(method, field);
					}
				} else {
					if(method.getParameterCount() > 0 && method.getParameterTypes()[0] == field.getType()){
						testConfiguration.setWriteMethod(method);
					} else {
						logWriteMethodMessage(method, field);
					}
				}
			} else {
				testConfiguration = createTestConfiguration(clazz, createObjectMethod, method, null, fieldName);
				if (testConfiguration != null) {
					fieldAssertObjectMap.put(testConfiguration.getClassFieldName(), testConfiguration);
				}
			}
		}
	}

	private List<AssertObject<?>> createAssertObjectList( Class<?> clazz,
			Map<String, TestConfiguration<?>> fieldAssertObjectMap) {
		List<AssertObject<?>> assertObjectList = Collections.emptyList();
		Set<String> classFieldNameSet = fieldAssertObjectMap.keySet();
		if (classFieldNameSet != null && !classFieldNameSet.isEmpty()) {
			assertObjectList = new LinkedList<>();
			for (String classFieldName : classFieldNameSet) {
				TestConfiguration<?> testConfiguration = fieldAssertObjectMap.get(classFieldName);
				if(testConfiguration != null){
					List<AssertObject<?>> assertObjects = testConfiguration.assertAssignedValues(clazz);
					assertObjectList.addAll(assertObjects);
				}
			}
		}
		return assertObjectList;
	}

	private TestConfiguration<?> createTestConfiguration(Class<?> clazz, Method createObjectMethod, Method readMethod,
			Method writeMethod, String fieldName) {
		TestConfiguration<?> testConfiguration = null;
		Field field = getField(clazz, fieldName);
		if (field != null) {
			boolean ignore = ReflectionFieldLevel.ignoreField(field);
			if (!ignore) {
				testConfiguration = ReflectionFieldLevel.assignValues(field);
				if (testConfiguration != null) {
					testConfiguration.setClassFieldName(clazz.getName() +"." + field.getName());
					if (readMethod != null && readMethod.getReturnType() == field.getType()) {
						testConfiguration.setReadMethod(readMethod);
					} else {
						logReadMethodMessage(readMethod, field);
					}
					if (writeMethod != null && writeMethod.getParameterCount() > 0
							&& writeMethod.getParameterTypes()[0] == field.getType()) {
						testConfiguration.setWriteMethod(writeMethod);
					} else {
						logWriteMethodMessage(writeMethod, field);
					}
					testConfiguration.setCreateObjectMethod(createObjectMethod);
				}
			}
		} else {
			String message = String.format(" %s not found in %s", fieldName, clazz.getName());
			LOGGER.debug(message);
		}
		return testConfiguration;
	}

	private void logWriteMethodMessage(Method writeMethod, Field field) {
		String message = "Write method of [" + field.getName() + "] is null.";
		if (writeMethod != null) {
			if (writeMethod.getParameterCount() > 0) {
				message = writeMethod.getName() + " write method's 1st parametertype "
						+ writeMethod.getParameterTypes()[0] + " but field [" + field.getName()
						+ "] type is " + field.getType();
			} else {
				message = writeMethod.getName() + " write method did not have any paramter";
			}
		}
		LOGGER.debug(message);
	}

	private void logReadMethodMessage(Method readMethod, Field field) {
		String message = "Read method of [" + field.getName() + "] is null.";
		if (readMethod != null) {
			message = readMethod.getName() + " read method return " + readMethod.getReturnType()
					+ " but field [" + field.getName() + "] type is " + field.getType();
		}
		LOGGER.debug(message);
	}


	private Method findCreateObjectMethod(Method[] methods) {
		Method createObjectMethod = null;
		for (Method method : methods) {
			if (ReflectionMethodLevel.isCreateMethod(method)) {
				createObjectMethod = method;
				break;
			}
		}
		return createObjectMethod;
	}

	private Field getField(Class<?> clazz, String fieldName) {
		Field field = null;
		try {
			field = clazz.getDeclaredField(fieldName);
		} catch (NoSuchFieldException | SecurityException e) {
			LOGGER.debug(fieldName + " field name is not present in " + clazz.getName(), e);
		}
		return field;
	}
}
