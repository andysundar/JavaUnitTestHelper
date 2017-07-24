package org.pojotester.test;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.pojotester.pack.scan.PackageScan;
import org.pojotester.test.values.AssertObject;

public class AssertObjectCreator {

	public List<AssertObject> getAssertObjects(final String... packagesToScan){
		List<AssertObject> assertObjectList = new LinkedList<>();
		PackageScan packageScan = new PackageScan();
		Set<Class<?>> uniqueClasses = packageScan.getClasses(packagesToScan);
		for(Class<?> clazz : uniqueClasses){
			Field fields[] = clazz.getDeclaredFields();
			
		}
		return assertObjectList;
	}
}
