package org.pojotester.test.values;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.pojotester.annotation.fieldmethod.BooleanTestValue;
import org.pojotester.annotation.fieldmethod.ByteTestValue;
import org.pojotester.annotation.fieldmethod.CharTestValue;
import org.pojotester.annotation.fieldmethod.DoubleTestValue;
import org.pojotester.annotation.fieldmethod.FloatTestValue;
import org.pojotester.annotation.fieldmethod.IntTestValue;
import org.pojotester.annotation.fieldmethod.LongTestValue;
import org.pojotester.annotation.fieldmethod.ShortTestValue;
import org.pojotester.annotation.fieldmethod.StringTestValue;

public abstract class TestValuesFactory {

	public static TestValues<?> createTestValuesObject(final Class<?> typeClass){
		 TestValues<?>  testValues = null;
		 if(typeClass == Boolean.class || typeClass == boolean.class 
				 || typeClass == Boolean[].class || typeClass == boolean[].class){
			 TestValues<Boolean> testValuesBoolean = new TestValues<Boolean>();
			
		 } else  if(typeClass == Byte.class || typeClass == byte.class 
				 || typeClass == Byte[].class || typeClass == byte[].class){
			 testValues = new TestValues<Byte>();
		 } else  if(typeClass == Character.class || typeClass == char.class 
				 || typeClass == Character[].class || typeClass == char[].class){
			 testValues = new TestValues<Character>();
		 }  else  if(typeClass == Double.class || typeClass == double.class
				 || typeClass == Double[].class || typeClass == double[].class){
			 testValues = new TestValues<Double>();
		 } else  if(typeClass == Float.class || typeClass == float.class
				 || typeClass == Float[].class || typeClass == float[].class){
			 testValues = new TestValues<Float>();
		 } else  if(typeClass == Integer.class || typeClass == int.class
				 || typeClass == Integer[].class || typeClass == int[].class){
			 testValues = new TestValues<Integer>();
		 } else  if(typeClass == Long.class || typeClass == long.class
				 || typeClass == Long[].class || typeClass == long[].class){
			 testValues = new TestValues<Long>();
		 } else  if(typeClass == Short.class || typeClass == short.class
				 || typeClass == Short[].class || typeClass == short[].class){
			 testValues = new TestValues<Short>();
		 } else  if(typeClass == String.class || typeClass == String[].class){
			 testValues = new TestValues<String>();
		 } else  if(typeClass == java.util.Date.class || typeClass == java.util.Date[].class){
			 testValues = new TestValues<java.util.Date>();
		 } else  if(typeClass == java.sql.Date.class || typeClass == java.sql.Date[].class){
			 testValues = new TestValues<java.sql.Date>();
		 } else  if(typeClass == java.util.Calendar.class || typeClass == java.util.Calendar[].class){
			 testValues = new TestValues<java.util.Calendar>();
		 } else  if(typeClass == LocalDate.class || typeClass == LocalDate[].class){
			 testValues = new TestValues<LocalDate>();
		 } else  if(typeClass == LocalTime.class || typeClass == LocalTime[].class){
			 testValues = new TestValues<LocalTime>();
		 } else  if(typeClass == LocalDateTime.class || typeClass == LocalDateTime[].class){
			 testValues = new TestValues<LocalDateTime>();
		 } else  if(typeClass == Instant.class || typeClass == Instant[].class){
			 testValues = new TestValues<Instant>();
		 } else  if(typeClass == BigDecimal.class || typeClass == BigDecimal[].class){
			 testValues = new TestValues<BigDecimal>();
		 } else  if(typeClass == BigInteger.class || typeClass == BigInteger[].class){
			 testValues = new TestValues<BigInteger>();
		 } else  if(typeClass == Set.class || typeClass.getSuperclass() == Set.class){
			 testValues = new TestValues<Set<?>>();
		 } else  if(typeClass == List.class || typeClass.getSuperclass() == List.class){
			 testValues = new TestValues<List<?>>();
		 } else  if(typeClass == Map.class || typeClass.getSuperclass() == Map.class){
			 testValues = new TestValues<Map<?, ?>>();
		 } else {
			 testValues = new TestValues<Object>();
		 }
		 return testValues;
	}
}
