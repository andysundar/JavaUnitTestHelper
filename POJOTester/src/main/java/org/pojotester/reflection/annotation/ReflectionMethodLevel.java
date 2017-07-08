package org.pojotester.reflection.annotation;

import java.lang.reflect.Method;

import org.pojotester.annotation.method.CreateObjectMethod;
import org.pojotester.annotation.method.ReadMethod;
import org.pojotester.annotation.method.WriteMethod;

public abstract class ReflectionMethodLevel {
	
	public static boolean isCreateMethod(final Method method){
		boolean thisIsCreateMethod = method.isAnnotationPresent(CreateObjectMethod.class);
		return thisIsCreateMethod;
	}
	
	public static boolean isWriteMethod(final Method method){
		boolean thisIsCreateMethod = method.isAnnotationPresent(WriteMethod.class);
		return thisIsCreateMethod;
	}
	
	public static boolean isReadMethod(final Method method){
		boolean thisIsCreateMethod = method.isAnnotationPresent(ReadMethod.class);
		return thisIsCreateMethod;
	}
}
