package org.pojotester.test.values;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class TestValuesFactory {

	public static TestConfigurations<?> createTestValuesObject(final Class<?> typeClass){
		 TestConfigurations<?>  testValues = null;
		 if(typeClass == Boolean.class || typeClass == boolean.class 
				 || typeClass == Boolean[].class || typeClass == boolean[].class){
			 testValues = new TestConfigurations<Boolean>();
		 } else  if(typeClass == Byte.class || typeClass == byte.class 
				 || typeClass == Byte[].class || typeClass == byte[].class){
			 testValues = new TestConfigurations<Byte>();
		 } else  if(typeClass == Character.class || typeClass == char.class 
				 || typeClass == Character[].class || typeClass == char[].class){
			 testValues = new TestConfigurations<Character>();
		 }  else  if(typeClass == Double.class || typeClass == double.class
				 || typeClass == Double[].class || typeClass == double[].class){
			 testValues = new TestConfigurations<Double>();
		 } else  if(typeClass == Float.class || typeClass == float.class
				 || typeClass == Float[].class || typeClass == float[].class){
			 testValues = new TestConfigurations<Float>();
		 } else  if(typeClass == Integer.class || typeClass == int.class
				 || typeClass == Integer[].class || typeClass == int[].class){
			 testValues = new TestConfigurations<Integer>();
		 } else  if(typeClass == Long.class || typeClass == long.class
				 || typeClass == Long[].class || typeClass == long[].class){
			 testValues = new TestConfigurations<Long>();
		 } else  if(typeClass == Short.class || typeClass == short.class
				 || typeClass == Short[].class || typeClass == short[].class){
			 testValues = new TestConfigurations<Short>();
		 } else  if(typeClass == String.class || typeClass == String[].class){
			 testValues = new TestConfigurations<String>();
		 } else  if(typeClass == java.util.Date.class || typeClass == java.util.Date[].class){
			 testValues = new TestConfigurations<java.util.Date>();
		 } else  if(typeClass == java.sql.Date.class || typeClass == java.sql.Date[].class){
			 testValues = new TestConfigurations<java.sql.Date>();
		 } else  if(typeClass == java.util.Calendar.class || typeClass == java.util.Calendar[].class){
			 testValues = new TestConfigurations<java.util.Calendar>();
		 } else  if(typeClass == LocalDate.class || typeClass == LocalDate[].class){
			 testValues = new TestConfigurations<LocalDate>();
		 } else  if(typeClass == LocalTime.class || typeClass == LocalTime[].class){
			 testValues = new TestConfigurations<LocalTime>();
		 } else  if(typeClass == LocalDateTime.class || typeClass == LocalDateTime[].class){
			 testValues = new TestConfigurations<LocalDateTime>();
		 } else  if(typeClass == Instant.class || typeClass == Instant[].class){
			 testValues = new TestConfigurations<Instant>();
		 } else  if(typeClass == BigDecimal.class || typeClass == BigDecimal[].class){
			 testValues = new TestConfigurations<BigDecimal>();
		 } else  if(typeClass == BigInteger.class || typeClass == BigInteger[].class){
			 testValues = new TestConfigurations<BigInteger>();
		 } else  if(typeClass == Set.class || typeClass.getSuperclass() == Set.class){
			 testValues = new TestConfigurations<Set<?>>();
		 } else  if(typeClass == List.class || typeClass.getSuperclass() == List.class){
			 testValues = new TestConfigurations<List<?>>();
		 } else  if(typeClass == Map.class || typeClass.getSuperclass() == Map.class){
			 testValues = new TestConfigurations<Map<?, ?>>();
		 } else {
			 testValues = new TestConfigurations<Object>();
		 }
		 return testValues;
	}
}
