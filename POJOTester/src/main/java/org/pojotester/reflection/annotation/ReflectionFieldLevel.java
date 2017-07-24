package org.pojotester.reflection.annotation;

import java.lang.reflect.Field;

import org.pojotester.annotation.field.BooleanTestValue;
import org.pojotester.annotation.field.ByteTestValue;
import org.pojotester.annotation.field.CharTestValue;
import org.pojotester.annotation.field.DoubleTestValue;
import org.pojotester.annotation.field.FloatTestValue;
import org.pojotester.annotation.field.IgnoreField;
import org.pojotester.annotation.field.IntTestValue;
import org.pojotester.annotation.field.LongTestValue;
import org.pojotester.annotation.field.ShortTestValue;
import org.pojotester.annotation.field.StringTestValue;
import org.pojotester.test.values.TestConfigurations;
import org.pojotester.test.values.TestValuesFactory;
import org.pojotester.type.PrimitiveToObjectArray;

public  abstract class ReflectionFieldLevel {

	public static boolean ignoreField(final Field field){
		boolean isFieldIgnored = field.isAnnotationPresent(IgnoreField.class);	
		return isFieldIgnored;
	}
	
	public static TestConfigurations<?> assignValues(final Field field){
		Class<?> typeClass = field.getType();
		TestConfigurations<?> testValues = null;
		
		
		if(typeClass == Boolean.class || typeClass == boolean.class 
				 || typeClass == Boolean[].class || typeClass == boolean[].class){
			BooleanTestValue booleanTestValue = field.getAnnotation(BooleanTestValue.class);
			testValues = createTestValues(booleanTestValue);
		 } else  if(typeClass == Byte.class || typeClass == byte.class 
				 || typeClass == Byte[].class || typeClass == byte[].class){
			 ByteTestValue byteTestValue = field.getAnnotation(ByteTestValue.class);
			 testValues = createTestValues(byteTestValue);
		 } else  if(typeClass == Character.class || typeClass == char.class 
				 || typeClass == Character[].class || typeClass == char[].class){
			 CharTestValue charTestValue = field.getAnnotation(CharTestValue.class);
			 testValues = createTestValues(charTestValue);
		 }  else  if(typeClass == Double.class || typeClass == double.class
				 || typeClass == Double[].class || typeClass == double[].class){
			 DoubleTestValue doubleTestValue = field.getAnnotation(DoubleTestValue.class);
			 testValues = createTestValues(doubleTestValue);
		 } else  if(typeClass == Float.class || typeClass == float.class
				 || typeClass == Float[].class || typeClass == float[].class){
			 FloatTestValue floatTestValue = field.getAnnotation(FloatTestValue.class);
			 testValues = createTestValues(floatTestValue);
		 } else  if(typeClass == Integer.class || typeClass == int.class
				 || typeClass == Integer[].class || typeClass == int[].class){
			 IntTestValue intTestValue = field.getAnnotation(IntTestValue.class);
			 testValues = createTestValues(intTestValue);
		 } else  if(typeClass == Long.class || typeClass == long.class
				 || typeClass == Long[].class || typeClass == long[].class){
			 LongTestValue longTestValue = field.getAnnotation(LongTestValue.class);
			 testValues = createTestValues(longTestValue);
		 } else  if(typeClass == Short.class || typeClass == short.class
				 || typeClass == Short[].class || typeClass == short[].class){
			 ShortTestValue shortTestValue = field.getAnnotation(ShortTestValue.class);
			 testValues = createTestValues(shortTestValue);
		 } else  if(typeClass == String.class || typeClass == String[].class){
			 StringTestValue stringTestValue = field.getAnnotation(StringTestValue.class);
			 testValues = createTestValues(stringTestValue);
		 } else {
			 testValues = TestValuesFactory.createTestValuesObject(typeClass);
		 }
	
		if(testValues != null){
			testValues.setField(field);
		}
		return testValues;
	}



	
	protected static TestConfigurations<String> createTestValues(StringTestValue stringTestValue) {
		TestConfigurations<String> testValues = new TestConfigurations<String>();
		if(stringTestValue != null) {
			String[] assignValues = stringTestValue.assignValues();
			String[] expectedValues = stringTestValue.expectedValues();
			if(expectedValues == null || expectedValues.length == 0){
				expectedValues = assignValues;
			}
			
			testValues.setAssignedValues(assignValues);
			testValues.setExpectedValues(expectedValues);
		}
		return testValues;
	}
	
	protected static TestConfigurations<Boolean> createTestValues(BooleanTestValue booleanTestValue) {
		TestConfigurations<Boolean> testValues = new TestConfigurations<Boolean>();
		if(booleanTestValue != null) {
			boolean[] assignValues = booleanTestValue.assignValues();
			boolean[] expectedValues = booleanTestValue.expectedValues();
			if(expectedValues == null || expectedValues.length == 0){
				expectedValues = assignValues;
			}
		
			Boolean[] assignObjectValues =  PrimitiveToObjectArray.convertPrimitiveToObjectArray(assignValues);
			Boolean[] expectedObjectValues =  PrimitiveToObjectArray.convertPrimitiveToObjectArray(expectedValues);
			
			testValues.setAssignedValues(assignObjectValues);
			testValues.setExpectedValues(expectedObjectValues);
		}
		return testValues;
	}
	
	protected static TestConfigurations<Byte> createTestValues(ByteTestValue byteTestValue) {
		TestConfigurations<Byte> testValues = new TestConfigurations<Byte>();
		if(byteTestValue != null) {
			byte[] assignValues = byteTestValue.assignValues();
			byte[] expectedValues = byteTestValue.expectedValues();
			if(expectedValues == null || expectedValues.length == 0){
				expectedValues = assignValues;
			}
		
			Byte[] assignObjectValues =  PrimitiveToObjectArray.convertPrimitiveToObjectArray(assignValues);
			Byte[] expectedObjectValues =  PrimitiveToObjectArray.convertPrimitiveToObjectArray(expectedValues);
			
			testValues.setAssignedValues(assignObjectValues);
			testValues.setExpectedValues(expectedObjectValues);
		}
		return testValues;
	}
	
	protected static TestConfigurations<Character> createTestValues(CharTestValue charTestValue) {
		TestConfigurations<Character> testValues = new TestConfigurations<Character>();
		if(charTestValue != null) {
			char[] assignValues = charTestValue.assignValues();
			char[] expectedValues = charTestValue.expectedValues();
			if(expectedValues == null || expectedValues.length == 0){
				expectedValues = assignValues;
			}
		
			Character[] assignObjectValues =  PrimitiveToObjectArray.convertPrimitiveToObjectArray(assignValues);
			Character[] expectedObjectValues =  PrimitiveToObjectArray.convertPrimitiveToObjectArray(expectedValues);
			
			testValues.setAssignedValues(assignObjectValues);
			testValues.setExpectedValues(expectedObjectValues);
		}
		return testValues;
	}
	
	protected static TestConfigurations<Double> createTestValues(DoubleTestValue doubleTestValue) {
		TestConfigurations<Double> testValues = new TestConfigurations<Double>();
		if(doubleTestValue != null) {
			double[] assignValues = doubleTestValue.assignValues();
			double[] expectedValues = doubleTestValue.expectedValues();
			if(expectedValues == null || expectedValues.length == 0){
				expectedValues = assignValues;
			}
		
			Double[] assignObjectValues =  PrimitiveToObjectArray.convertPrimitiveToObjectArray(assignValues);
			Double[] expectedObjectValues =  PrimitiveToObjectArray.convertPrimitiveToObjectArray(expectedValues);
			
			testValues.setAssignedValues(assignObjectValues);
			testValues.setExpectedValues(expectedObjectValues);
		}
		return testValues;
	}
	
	protected static TestConfigurations<Float> createTestValues(FloatTestValue floatTestValue) {
		TestConfigurations<Float> testValues = new TestConfigurations<Float>();
		if(floatTestValue != null) {
			float[] assignValues = floatTestValue.assignValues();
			float[] expectedValues = floatTestValue.expectedValues();
			if(expectedValues == null || expectedValues.length == 0){
				expectedValues = assignValues;
			}
		
			Float[] assignObjectValues =  PrimitiveToObjectArray.convertPrimitiveToObjectArray(assignValues);
			Float[] expectedObjectValues =  PrimitiveToObjectArray.convertPrimitiveToObjectArray(expectedValues);
			
			testValues.setAssignedValues(assignObjectValues);
			testValues.setExpectedValues(expectedObjectValues);
		}
		return testValues;
	}

	protected static TestConfigurations<Integer> createTestValues(IntTestValue intTestValue) {
		TestConfigurations<Integer> testValues = new TestConfigurations<Integer>();
		if(intTestValue != null) {
			int[] assignValues = intTestValue.assignValues();
			int[] expectedValues = intTestValue.expectedValues();
			if(expectedValues == null || expectedValues.length == 0){
				expectedValues = assignValues;
			}
		
			Integer[] assignObjectValues =  PrimitiveToObjectArray.convertPrimitiveToObjectArray(assignValues);
			Integer[] expectedObjectValues =  PrimitiveToObjectArray.convertPrimitiveToObjectArray(expectedValues);
			
			testValues.setAssignedValues(assignObjectValues);
			testValues.setExpectedValues(expectedObjectValues);
		}
		return testValues;
	}
	
	protected static TestConfigurations<Long> createTestValues(LongTestValue longTestValue) {
		TestConfigurations<Long> testValues = new TestConfigurations<Long>();
		if(longTestValue != null) {
			long[] assignValues = longTestValue.assignValues();
			long[] expectedValues = longTestValue.expectedValues();
			if(expectedValues == null || expectedValues.length == 0){
				expectedValues = assignValues;
			}
		
			Long[] assignObjectValues =  PrimitiveToObjectArray.convertPrimitiveToObjectArray(assignValues);
			Long[] expectedObjectValues =  PrimitiveToObjectArray.convertPrimitiveToObjectArray(expectedValues);
			
			testValues.setAssignedValues(assignObjectValues);
			testValues.setExpectedValues(expectedObjectValues);
		}
		return testValues;
	}
	
	protected static TestConfigurations<Short> createTestValues(ShortTestValue shortTestValue) {
		TestConfigurations<Short> testValues = new TestConfigurations<Short>();
		if(shortTestValue != null) {
			short[] assignValues = shortTestValue.assignValues();
			short[] expectedValues = shortTestValue.expectedValues();
			if(expectedValues == null || expectedValues.length == 0){
				expectedValues = assignValues;
			}
		
			Short[] assignObjectValues =  PrimitiveToObjectArray.convertPrimitiveToObjectArray(assignValues);
			Short[] expectedObjectValues =  PrimitiveToObjectArray.convertPrimitiveToObjectArray(expectedValues);
			
			testValues.setAssignedValues(assignObjectValues);
			testValues.setExpectedValues(expectedObjectValues);
		}
		return testValues;
	}

}


