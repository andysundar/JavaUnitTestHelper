package org.pojotester.pack.scan;

import org.pojotester.reflection.annotation.ReflectionClassLevel;
import org.pojotester.utils.ClassUtilities;

public class LoadClassIfNotIgnored extends PackageScan {
	
	@Override
	protected Class<?> loadClass(final String className){
		return loadClassIfNotIgnored(className);
	}
	
	private Class<?> loadClassIfNotIgnored(final String className){
		Class<?> clazz = ClassUtilities.loadClass(className);
		if(clazz != null) {
		    boolean ignoreThisClass = ReflectionClassLevel.ignoreClass(clazz);
			if(ignoreThisClass){
				clazz = null;
			}
		}
		return clazz;
	}

}
