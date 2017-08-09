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

import org.pojotester.pack.scan.PackageScan;
import org.pojotester.reflection.annotation.ReflectionFieldLevel;
import org.pojotester.reflection.annotation.ReflectionMethodLevel;
import org.pojotester.test.values.AssertObject;
import org.pojotester.test.values.TestConfigurations;

public class AssertObjectCreator {

	public List<AssertObject<?>> getAssertObjects(final String... packagesToScan) {
		List<AssertObject<?>> assertObjectList = new LinkedList<AssertObject<?>>();
		PackageScan packageScan = new PackageScan();
		Set<Class<?>> uniqueClasses = packageScan.getClasses(packagesToScan);
		for (Class<?> clazz : uniqueClasses) {
			Map<String, TestConfigurations<?>> fieldAssertObjectMap = new HashMap<String, TestConfigurations<?>>();
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
			Map<String, TestConfigurations<?>> fieldAssertObjectMap, Method createObjectMethod) {
		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(clazz, Object.class);
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		PropertyDescriptor propertyDescriptors[] = beanInfo.getPropertyDescriptors();
		if (propertyDescriptors != null) {
			for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
				Method readMethod = propertyDescriptor.getReadMethod();
				Method writeMethod = propertyDescriptor.getWriteMethod();
				String fieldName = propertyDescriptor.getName();
				TestConfigurations<?> testConfigurations = createTestConfigurations(clazz, createObjectMethod,
						readMethod, writeMethod, fieldName);
				if (testConfigurations != null) {
					fieldAssertObjectMap.put(fieldName, testConfigurations);
				}
			}
		}
	}

	private void createTestConfigurationsFromAnnotations(Class<?> clazz,
			Map<String, TestConfigurations<?>> fieldAssertObjectMap, Method[] methods, Method createObjectMethod) {
		for (Method method : methods) {
			String fieldName = ReflectionMethodLevel.getFieldNameOfReadMethod(method);
			boolean writeMethod = false;
			if (fieldName == null  || fieldName.isEmpty()) {
				fieldName = ReflectionMethodLevel.getFieldNameOfWriteMethod(method);
				writeMethod = true;
			}
			setAnnotedReadOrWriteMethod(clazz, fieldAssertObjectMap, createObjectMethod, method, fieldName,
					writeMethod);
		}
	}

	private void setAnnotedReadOrWriteMethod(Class<?> clazz, Map<String, TestConfigurations<?>> fieldAssertObjectMap,
			Method createObjectMethod, Method method, String fieldName, boolean writeMethod) {
		if(fieldName != null && !fieldName.isEmpty()) {
			TestConfigurations<?> testConfigurations = fieldAssertObjectMap.get(fieldName);
			if (testConfigurations != null) {
				testConfigurations.setCreateObjectMethod(createObjectMethod);
				if(!writeMethod){
					testConfigurations.setReadMethod(method);
				} else {
					testConfigurations.setWriteMethod(method);
				}
			} else {
				testConfigurations = createTestConfigurations(clazz, createObjectMethod, method, null, fieldName);
				if (testConfigurations != null) {
					fieldAssertObjectMap.put(fieldName, testConfigurations);
				}
			}
		}
	}

	private List<AssertObject<?>> createAssertObject( Class<?> clazz,
			Map<String, TestConfigurations<?>> fieldAssertObjectMap) {
		List<AssertObject<?>> assertObjectList = Collections.emptyList();
		Set<String> fieldNameSet = fieldAssertObjectMap.keySet();
		if (fieldNameSet != null && !fieldNameSet.isEmpty()) {
			assertObjectList = new LinkedList<AssertObject<?>>();
			for (String fieldName : fieldNameSet) {
				TestConfigurations<?> testConfigurations = fieldAssertObjectMap.get(fieldName);
				if(testConfigurations != null){
					List<AssertObject<?>> assertObjects = testConfigurations.assertAssignedValues(clazz);
					assertObjectList.addAll(assertObjects);
				}
			}
		}
		return assertObjectList;
	}

	private TestConfigurations<?> createTestConfigurations(Class<?> clazz, Method createObjectMethod, Method readMethod,
			Method writeMethod, String fieldName) {
		TestConfigurations<?> testConfigurations = null;
		Field field = getField(clazz, fieldName);
		if (field != null) {
			boolean ignore = ReflectionFieldLevel.ignoreField(field);
			if (!ignore) {
				testConfigurations = ReflectionFieldLevel.assignValues(field);
				if (testConfigurations != null) {
					if (readMethod != null) {
						testConfigurations.setReadMethod(readMethod);
					}
					if (writeMethod != null) {
						testConfigurations.setWriteMethod(writeMethod);
					}
					testConfigurations.setCreateObjectMethod(createObjectMethod);
				}
			}
		}
		return testConfigurations;
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
			e.printStackTrace();
		}
		return field;
	}
}
