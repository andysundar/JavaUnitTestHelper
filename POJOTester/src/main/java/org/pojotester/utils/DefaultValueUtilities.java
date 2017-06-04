/*******************************************************************************
 * Copyright 2017 Anindya Bandopadhyay (anindyabandopadhyay@gmail.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.pojotester.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class DefaultValueUtilities {
	private static final Map<Class<?>,Object> classValueMap = new HashMap<Class<?>,Object>();
	
	static {
		classValueMap.put(boolean.class, Boolean.TRUE);
		classValueMap.put(Boolean.class, Boolean.TRUE);
		classValueMap.put(boolean[].class, new boolean[]{Boolean.TRUE , Boolean.FALSE});
		classValueMap.put(Boolean[].class, new Boolean[]{Boolean.TRUE , Boolean.FALSE});
		
		classValueMap.put(byte.class, Byte.MAX_VALUE);
		classValueMap.put(Byte.class, Byte.MAX_VALUE);
		classValueMap.put(byte[].class, new byte[]{Byte.MIN_VALUE , Byte.MAX_VALUE});
		classValueMap.put(Byte[].class, new Byte[]{Byte.MIN_VALUE , Byte.MAX_VALUE});
		
		classValueMap.put(char.class, 'A');
		classValueMap.put(Character.class, 'A');
		classValueMap.put(char[].class, new char[]{'A' , 'B'});
		classValueMap.put(Character[].class, new Character[]{'A' , 'B'});
		
		classValueMap.put(double.class, Double.MAX_VALUE);
		classValueMap.put(Double.class, Double.MAX_VALUE);
		classValueMap.put(double[].class, new double[]{Double.MIN_VALUE , Double.MAX_VALUE});
		classValueMap.put(Double[].class, new Double[]{Double.MIN_VALUE , Double.MAX_VALUE});
		
		classValueMap.put(float.class, Float.MAX_VALUE);
		classValueMap.put(Float.class, Float.MAX_VALUE);
		classValueMap.put(float[].class, new float[]{Float.MIN_VALUE , Float.MAX_VALUE});
		classValueMap.put(Float[].class, new Float[]{Float.MIN_VALUE , Float.MAX_VALUE});
		
		classValueMap.put(int.class, Integer.MAX_VALUE);
		classValueMap.put(Integer.class, Integer.MAX_VALUE);
		classValueMap.put(int[].class, new int[]{Integer.MIN_VALUE , Integer.MAX_VALUE});
		classValueMap.put(Integer[].class, new Integer[]{Integer.MIN_VALUE , Integer.MAX_VALUE});
		
		classValueMap.put(long.class, Long.MAX_VALUE);
		classValueMap.put(Long.class, Long.MAX_VALUE);
		classValueMap.put(long[].class, new long[]{Long.MIN_VALUE , Long.MAX_VALUE});
		classValueMap.put(Long[].class, new Long[]{Long.MIN_VALUE , Long.MAX_VALUE});
		
		classValueMap.put(short.class, Short.MAX_VALUE);
		classValueMap.put(Short.class, Short.MAX_VALUE);
		classValueMap.put(short[].class, new short[]{Short.MIN_VALUE , Short.MAX_VALUE});
		classValueMap.put(Short[].class, new Short[]{Short.MIN_VALUE , Short.MAX_VALUE});
		
		classValueMap.put(String.class, "String");
		classValueMap.put(String[].class, new String[]{"String" , "" });
		
		classValueMap.put(java.util.Date.class, new java.util.Date());
		classValueMap.put(java.util.Date[].class, new java.util.Date[]{new java.util.Date() , new java.util.Date() });
		
		classValueMap.put(java.sql.Date.class, new java.sql.Date(new java.util.Date().getTime()));
		classValueMap.put(java.sql.Date[].class, new java.sql.Date[]{new java.sql.Date(new java.util.Date().getTime()) , new java.sql.Date(new java.util.Date().getTime()) });
		
		classValueMap.put(java.util.Calendar.class, new java.util.Calendar.Builder().build());
		classValueMap.put(java.util.Calendar[].class, new java.util.Calendar[]{new java.util.Calendar.Builder().build(), new java.util.Calendar.Builder().build()});
		
		classValueMap.put(LocalDate.class, LocalDate.now());
		classValueMap.put(LocalDate[].class, new LocalDate[]{LocalDate.now() , LocalDate.now()});
		
		classValueMap.put(LocalTime.class, LocalTime.now());
		classValueMap.put(LocalTime[].class, new LocalTime[]{LocalTime.now() , LocalTime.now()});
		
		classValueMap.put(LocalDateTime.class, LocalDateTime.now());
		classValueMap.put(LocalDateTime[].class, new LocalDateTime[]{LocalDateTime.now() , LocalDateTime.now()});
		
		classValueMap.put(Instant.class, Instant.now());
		classValueMap.put(Instant[].class, new Instant[]{Instant.now() , Instant.now()});
		
		
		classValueMap.put(BigDecimal.class, BigDecimal.TEN);
		classValueMap.put(BigDecimal[].class, new BigDecimal[]{BigDecimal.TEN , BigDecimal.ONE });
		
		classValueMap.put(BigInteger.class, BigInteger.TEN);
		classValueMap.put(BigInteger[].class, new BigInteger[]{BigInteger.TEN , BigInteger.ONE });
		
		classValueMap.put(Object.class, new Object());
		classValueMap.put(Object[].class, new Object[]{new Object() , new Object()});
		
		classValueMap.put(Set.class, Collections.emptySet());
		
		classValueMap.put(List.class, Collections.emptyList());
		
		classValueMap.put(Map.class, Collections.emptyMap());
	}
	
	public static Object getValueFromMap(final Class<?> clazz){
		return classValueMap.get(clazz);
	}
	
}
