package org.pojotester.pack.scan;

import org.pojotester.reflection.annotation.ReflectionClassLevel;
import org.pojotester.utils.ClassUtilities;

public final class LoadClassIfAskedFor extends PackageScan{
	
	@Override
	protected Class<?> loadClass(String className){
		return loadClassIfAskedFor(className);
	}

	private Class<?> loadClassIfAskedFor(final String className){
		Class<?> clazz = ClassUtilities.loadClass(className);
		if(clazz != null) {
		    boolean testThisClass = ReflectionClassLevel.testThisClass(clazz);
			if(!testThisClass){
				clazz = null;
			}
		}
		return clazz;
	}
}
