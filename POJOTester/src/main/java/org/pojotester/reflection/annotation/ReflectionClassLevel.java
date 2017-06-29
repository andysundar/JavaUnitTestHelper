package org.pojotester.reflection.annotation;

import org.pojotester.annotation.clazz.IgnoreClass;

public class ReflectionClassLevel {

	public static boolean ignoreClass(Class<?> clazz){
	   boolean ignoreThisClass =	clazz.isAnnotationPresent(IgnoreClass.class);
	   return ignoreThisClass;
	}
}
