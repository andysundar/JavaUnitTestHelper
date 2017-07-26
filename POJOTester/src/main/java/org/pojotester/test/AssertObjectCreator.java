package org.pojotester.test;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
import org.pojotester.utils.ClassUtilities;

public class AssertObjectCreator {

	public List<AssertObject> getAssertObjects(final String... packagesToScan){
		List<AssertObject> assertObjectList = new LinkedList<>();
		PackageScan packageScan = new PackageScan();
		Set<Class<?>> uniqueClasses = packageScan.getClasses(packagesToScan);
		for(Class<?> clazz : uniqueClasses){
		    Map<String, List<AssertObject>> fieldAssertObjectMap = new HashMap<String, List<AssertObject>>();
		    Method[] methods = clazz.getDeclaredMethods();
		    Method createObjectMethod = null;
			for(Method method : methods){
				if(ReflectionMethodLevel.isCreateMethod(method)){
					createObjectMethod = method;
					continue;
				}
				String fieldName = ReflectionMethodLevel.getFieldNameOfReadMethod(method);
			}
			BeanInfo beanInfo = null;
			try {
				beanInfo = Introspector.getBeanInfo(clazz, Object.class);
			} catch (IntrospectionException e) {
				e.printStackTrace();
			}
			PropertyDescriptor propertyDescriptors[] =beanInfo.getPropertyDescriptors();
			if(propertyDescriptors != null){
				for(PropertyDescriptor propertyDescriptor: propertyDescriptors){
					Method readMethod = propertyDescriptor.getReadMethod();
					Method writeMethod = propertyDescriptor.getWriteMethod();
					String fieldName = propertyDescriptor.getName();
					Field field = getField(clazz, fieldName);
					if(field != null){
						boolean ignore = ReflectionFieldLevel.ignoreField(field);
						if(!ignore){
							TestConfigurations<?> testConfigurations = ReflectionFieldLevel.assignValues(field);
							testConfigurations.setReadMethod(readMethod);
							testConfigurations.setWriteMethod(writeMethod);
							testConfigurations.setCreateObjectMethod(createObjectMethod);
							List<AssertObject> assertObjects = testConfigurations.assertAssignedValues(clazz);
							assertObjectList.addAll(assertObjects);
							fieldAssertObjectMap.put(field.getName(), assertObjects);
						}
					}
				}
			}
			
			
		}
		return assertObjectList;
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
