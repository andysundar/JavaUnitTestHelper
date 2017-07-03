package org.pojotester.reflection.annotation;

import java.lang.reflect.Field;

import org.pojotester.annotation.field.IgnoreField;

public class ReflectionFieldLevel {

	public static boolean ignoreField(Field field){
		boolean isFieldIgnored = field.isAnnotationPresent(IgnoreField.class);	
		return isFieldIgnored;
	}
}
