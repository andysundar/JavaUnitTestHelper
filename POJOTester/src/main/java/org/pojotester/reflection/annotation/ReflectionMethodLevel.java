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
		
	public static String getFieldNameOfReadMethod(final Method method){
		boolean thisIsReadMethod = method.isAnnotationPresent(ReadMethod.class);
		String fieldName = null;
		if(thisIsReadMethod){
			ReadMethod readMethod = method.getDeclaredAnnotation(ReadMethod.class);
			fieldName = readMethod.fieldName();
		}
		return fieldName;
	}
	
	public static String getFieldNameOfWriteMethod(final Method method){
		boolean thisIsWriteMethod = method.isAnnotationPresent(WriteMethod.class);
		String fieldName = null;
		if(thisIsWriteMethod){
			WriteMethod writeMethod = method.getDeclaredAnnotation(WriteMethod.class);
			fieldName = writeMethod.fieldName();
		}
		return fieldName;
	}
}
