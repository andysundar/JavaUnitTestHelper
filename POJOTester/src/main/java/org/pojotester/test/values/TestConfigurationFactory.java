/*******************************************************************************
 * Copyright 2017 Anindya Bandopadhyay (anindyabandopadhyay@gmail.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
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

/**
 * This class is the factory class which return an object {@link TestConfiguration}.
 * @author Anindya Bandopadhyay
 * @since 1.0
 */
public abstract class TestConfigurationFactory {

	/**
	 * This method create an object of {@link TestConfiguration} based on data type.
	 * @param typeClass
	 * @return {@code TestConfiguration} type object.
	 */
	public static TestConfiguration<?> createTestConfigurationObject(final Class<?> typeClass){
		 TestConfiguration<?>  testValues = null;
		 if(typeClass == Boolean.class || typeClass == boolean.class 
				 || typeClass == Boolean[].class || typeClass == boolean[].class){
			 testValues = new TestConfiguration<Boolean>();
		 } else  if(typeClass == Byte.class || typeClass == byte.class 
				 || typeClass == Byte[].class || typeClass == byte[].class){
			 testValues = new TestConfiguration<Byte>();
		 } else  if(typeClass == Character.class || typeClass == char.class 
				 || typeClass == Character[].class || typeClass == char[].class){
			 testValues = new TestConfiguration<Character>();
		 }  else  if(typeClass == Double.class || typeClass == double.class
				 || typeClass == Double[].class || typeClass == double[].class){
			 testValues = new TestConfiguration<Double>();
		 } else  if(typeClass == Float.class || typeClass == float.class
				 || typeClass == Float[].class || typeClass == float[].class){
			 testValues = new TestConfiguration<Float>();
		 } else  if(typeClass == Integer.class || typeClass == int.class
				 || typeClass == Integer[].class || typeClass == int[].class){
			 testValues = new TestConfiguration<Integer>();
		 } else  if(typeClass == Long.class || typeClass == long.class
				 || typeClass == Long[].class || typeClass == long[].class){
			 testValues = new TestConfiguration<Long>();
		 } else  if(typeClass == Short.class || typeClass == short.class
				 || typeClass == Short[].class || typeClass == short[].class){
			 testValues = new TestConfiguration<Short>();
		 } else  if(typeClass == String.class || typeClass == String[].class){
			 testValues = new TestConfiguration<String>();
		 } else  if(typeClass == java.util.Date.class || typeClass == java.util.Date[].class){
			 testValues = new TestConfiguration<java.util.Date>();
		 } else  if(typeClass == java.sql.Date.class || typeClass == java.sql.Date[].class){
			 testValues = new TestConfiguration<java.sql.Date>();
		 } else  if(typeClass == java.util.Calendar.class || typeClass == java.util.Calendar[].class){
			 testValues = new TestConfiguration<java.util.Calendar>();
		 } else  if(typeClass == LocalDate.class || typeClass == LocalDate[].class){
			 testValues = new TestConfiguration<LocalDate>();
		 } else  if(typeClass == LocalTime.class || typeClass == LocalTime[].class){
			 testValues = new TestConfiguration<LocalTime>();
		 } else  if(typeClass == LocalDateTime.class || typeClass == LocalDateTime[].class){
			 testValues = new TestConfiguration<LocalDateTime>();
		 } else  if(typeClass == Instant.class || typeClass == Instant[].class){
			 testValues = new TestConfiguration<Instant>();
		 } else  if(typeClass == BigDecimal.class || typeClass == BigDecimal[].class){
			 testValues = new TestConfiguration<BigDecimal>();
		 } else  if(typeClass == BigInteger.class || typeClass == BigInteger[].class){
			 testValues = new TestConfiguration<BigInteger>();
		 } else  if(typeClass == Set.class || typeClass.getSuperclass() == Set.class){
			 testValues = new TestConfiguration<Set<?>>();
		 } else  if(typeClass == List.class || typeClass.getSuperclass() == List.class){
			 testValues = new TestConfiguration<List<?>>();
		 } else  if(typeClass == Map.class || typeClass.getSuperclass() == Map.class){
			 testValues = new TestConfiguration<Map<?, ?>>();
		 } else {
			 testValues = new TestConfiguration<Object>();
		 }
		 return testValues;
	}
}
