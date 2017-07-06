package org.pojotester.reflection.annotation;

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
import org.pojotester.type.PrimitiveToObjectArray;

public class ReflectionFieldLevel {

	public static boolean ignoreField(final Field field){
		boolean isFieldIgnored = field.isAnnotationPresent(IgnoreField.class);	
		return isFieldIgnored;
	}
	
	public static TestValues<?> assignValues(final Field field){
		Class<?> typeClass = field.getType();
		TestValues<?> testValues = null;
		testValues.setField(field);
		
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
	
		return testValues;
	}

	private static TestValues<String> createTestValues(StringTestValue stringTestValue) {
		TestValues<String> testValues = new TestValues<String>();
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
	
	private static TestValues<Boolean> createTestValues(BooleanTestValue booleanTestValue) {
		TestValues<Boolean> testValues = new TestValues<Boolean>();
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
	
	private static TestValues<Byte> createTestValues(ByteTestValue byteTestValue) {
		TestValues<Byte> testValues = new TestValues<Byte>();
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
	
	private static TestValues<Character> createTestValues(CharTestValue charTestValue) {
		TestValues<Character> testValues = new TestValues<Character>();
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
	
	private static TestValues<Double> createTestValues(DoubleTestValue doubleTestValue) {
		TestValues<Double> testValues = new TestValues<Double>();
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
	
	private static TestValues<Float> createTestValues(FloatTestValue floatTestValue) {
		TestValues<Float> testValues = new TestValues<Float>();
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

	private static TestValues<Integer> createTestValues(IntTestValue intTestValue) {
		TestValues<Integer> testValues = new TestValues<Integer>();
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
	
	private static TestValues<Long> createTestValues(LongTestValue longTestValue) {
		TestValues<Long> testValues = new TestValues<Long>();
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
	
	private static TestValues<Short> createTestValues(ShortTestValue shortTestValue) {
		TestValues<Short> testValues = new TestValues<Short>();
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


