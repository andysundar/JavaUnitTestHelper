package org.pojotester.reflection.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.pojotester.annotation.field.IgnoreField;
import org.pojotester.annotation.fieldmethod.BooleanTestValue;
import org.pojotester.annotation.fieldmethod.ByteTestValue;
import org.pojotester.annotation.fieldmethod.CharTestValue;
import org.pojotester.annotation.fieldmethod.DoubleTestValue;
import org.pojotester.annotation.fieldmethod.FloatTestValue;
import org.pojotester.annotation.fieldmethod.IntTestValue;
import org.pojotester.annotation.fieldmethod.LongTestValue;
import org.pojotester.annotation.fieldmethod.ShortTestValue;
import org.pojotester.annotation.fieldmethod.StringTestValue;
import org.pojotester.test.values.TestValues;
import org.pojotester.test.values.TestValuesFactory;

public class ReflectionFieldLevel {

	public static boolean ignoreField(final Field field){
		boolean isFieldIgnored = field.isAnnotationPresent(IgnoreField.class);	
		return isFieldIgnored;
	}
	
	public static TestValues<?> assignValues(final Field field){
		Class<?> typeClass = field.getType();
		TestValues<?> testValues = TestValuesFactory.createTestValuesObject(typeClass, field);
		testValues.setField(field);
		
		if(typeClass == Boolean.class || typeClass == boolean.class 
				 || typeClass == Boolean[].class || typeClass == boolean[].class){
			BooleanTestValue booleanTestValue = field.getAnnotation(BooleanTestValue.class);
			if(booleanTestValue != null) {
				boolean[] assignValues = booleanTestValue.assignValues();
				boolean[] expectedValues = booleanTestValue.expectedValues();
				if(expectedValues == null){
					expectedValues = assignValues;
				}
				
				testValues.setAssignedValues(assignValues);
				testValues.setExpectedValues(expectedValues);
			}
		 } else  if(typeClass == Byte.class || typeClass == byte.class 
				 || typeClass == Byte[].class || typeClass == byte[].class){
			 ByteTestValue byteTestValue = field.getAnnotation(ByteTestValue.class);
		 } else  if(typeClass == Character.class || typeClass == char.class 
				 || typeClass == Character[].class || typeClass == char[].class){
			 CharTestValue charTestValue = field.getAnnotation(CharTestValue.class);
		 }  else  if(typeClass == Double.class || typeClass == double.class
				 || typeClass == Double[].class || typeClass == double[].class){
			 DoubleTestValue doubleTestValue = field.getAnnotation(DoubleTestValue.class);
		 } else  if(typeClass == Float.class || typeClass == float.class
				 || typeClass == Float[].class || typeClass == float[].class){
			 FloatTestValue floatTestValue = field.getAnnotation(FloatTestValue.class);
		 } else  if(typeClass == Integer.class || typeClass == int.class
				 || typeClass == Integer[].class || typeClass == int[].class){
			 IntTestValue intTestValue = field.getAnnotation(IntTestValue.class);
		 } else  if(typeClass == Long.class || typeClass == long.class
				 || typeClass == Long[].class || typeClass == long[].class){
			 LongTestValue longTestValue = field.getAnnotation(LongTestValue.class);
		 } else  if(typeClass == Short.class || typeClass == short.class
				 || typeClass == Short[].class || typeClass == short[].class){
			 ShortTestValue shortTestValue = field.getAnnotation(ShortTestValue.class);
		 } else  if(typeClass == String.class || typeClass == String[].class){
			 StringTestValue stringTestValue = field.getAnnotation(StringTestValue.class);
		 }
	
		return testValues;
	}
	
}
