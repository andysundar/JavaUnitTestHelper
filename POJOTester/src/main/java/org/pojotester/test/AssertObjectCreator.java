package org.pojotester.test;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.pojotester.pack.scan.PackageScan;
import org.pojotester.reflection.annotation.ReflectionFieldLevel;
import org.pojotester.test.values.AssertObject;
import org.pojotester.test.values.TestConfigurations;
import org.pojotester.utils.ClassUtilities;

public class AssertObjectCreator {

	public List<AssertObject> getAssertObjects(final String... packagesToScan){
		List<AssertObject> assertObjectList = new LinkedList<>();
		PackageScan packageScan = new PackageScan();
		Set<Class<?>> uniqueClasses = packageScan.getClasses(packagesToScan);
		for(Class<?> clazz : uniqueClasses){
			Field fields[] = clazz.getDeclaredFields();
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
					try {
						Field field = clazz.getDeclaredField(propertyDescriptor.getName());
						boolean ignore = ReflectionFieldLevel.ignoreField(field);
								if(!ignore){
									TestConfigurations<?> testConfigurations = ReflectionFieldLevel.assignValues(field);
									testConfigurations.setReadMethod(readMethod);
									testConfigurations.setWriteMethod(writeMethod);
									Object object = ClassUtilities.createObject(clazz);
									testConfigurations.setObject(object);
									List<AssertObject> assertObjects = testConfigurations.assertAssignedValues();
									assertObjectList.addAll(assertObjects);
								}
					} catch (NoSuchFieldException | SecurityException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return assertObjectList;
	}
}
