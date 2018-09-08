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
import java.util.Set;

import org.pojotester.pack.scan.LoadClassIfAskedFor;
import org.pojotester.pack.scan.LoadClassIfNotIgnored;
import org.pojotester.pack.scan.PackageScan;
import org.pojotester.reflection.annotation.ReflectionFieldLevel;
import org.pojotester.reflection.annotation.ReflectionMethodLevel;
import org.pojotester.test.values.AssertObject;
import org.pojotester.test.values.TestConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class create assert object for unit testing. 
 * @author Anindya Bandopadhyay
 * @since 1.0
 */
public class AssertObjectCreator {

	private static final Logger LOGGER = LoggerFactory.getLogger(AssertObjectCreator.class);
	
	private boolean loadClassesAskedFor;


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
			Map<String, TestConfiguration<?>> fieldAssertObjectMap = new HashMap<>();
			Method[] methods = clazz.getDeclaredMethods();
			Method createObjectMethod = findCreateObjectMethod(methods);
			createTestConfigurationsFromIntospection(clazz, fieldAssertObjectMap, createObjectMethod);
			createTestConfigurationsFromAnnotations(clazz, fieldAssertObjectMap, methods, createObjectMethod);
			List<AssertObject<?>> assertObjects = createAssertObject(clazz, fieldAssertObjectMap);
			assertObjectList.addAll(assertObjects);
		}
		return assertObjectList;
	}

	private void createTestConfigurationsFromIntospection(Class<?> clazz,
			Map<String, TestConfiguration<?>> fieldAssertObjectMap, Method createObjectMethod) {
		BeanInfo beanInfo = null;
		PropertyDescriptor propertyDescriptors[] = null;
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
				setAnnotedReadOrWriteMethod(clazz, fieldAssertObjectMap, createObjectMethod, method, fieldName,
						writeMethod);
			}
		}
	}

	private void setAnnotedReadOrWriteMethod(Class<?> clazz, Map<String, TestConfiguration<?>> fieldAssertObjectMap,
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

	private List<AssertObject<?>> createAssertObject( Class<?> clazz,
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
			LOGGER.debug(fieldName + " not found in " + clazz.getName());
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
