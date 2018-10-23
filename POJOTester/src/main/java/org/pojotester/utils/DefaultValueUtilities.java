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
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.UUID;
import java.util.Vector;
import java.util.stream.Stream;

/**
 * This class has a global map of most widely used data types.
 * @author Anindya Bandopadhyay
 * @since 1.0
 */
public abstract class DefaultValueUtilities {
	private static final Map<Class<?>,Object> CLASS_VALUE_MAP = new HashMap<Class<?>,Object>(100);
	
	static {
		CLASS_VALUE_MAP.put(boolean.class, Boolean.TRUE);
		CLASS_VALUE_MAP.put(Boolean.class, Boolean.TRUE);
		CLASS_VALUE_MAP.put(boolean[].class, new boolean[]{Boolean.TRUE , Boolean.FALSE});
		CLASS_VALUE_MAP.put(Boolean[].class, new Boolean[]{Boolean.TRUE , Boolean.FALSE});
		
		CLASS_VALUE_MAP.put(byte.class, Byte.MAX_VALUE);
		CLASS_VALUE_MAP.put(Byte.class, Byte.MAX_VALUE);
		CLASS_VALUE_MAP.put(byte[].class, new byte[]{Byte.MIN_VALUE , Byte.MAX_VALUE});
		CLASS_VALUE_MAP.put(Byte[].class, new Byte[]{Byte.MIN_VALUE , Byte.MAX_VALUE});
		
		CLASS_VALUE_MAP.put(char.class, 'A');
		CLASS_VALUE_MAP.put(Character.class, 'A');
		CLASS_VALUE_MAP.put(char[].class, new char[]{'A' , 'B'});
		CLASS_VALUE_MAP.put(Character[].class, new Character[]{'A' , 'B'});
		
		CLASS_VALUE_MAP.put(double.class, Double.MAX_VALUE);
		CLASS_VALUE_MAP.put(Double.class, Double.MAX_VALUE);
		CLASS_VALUE_MAP.put(double[].class, new double[]{Double.MIN_VALUE , Double.MAX_VALUE});
		CLASS_VALUE_MAP.put(Double[].class, new Double[]{Double.MIN_VALUE , Double.MAX_VALUE});
		
		CLASS_VALUE_MAP.put(float.class, Float.MAX_VALUE);
		CLASS_VALUE_MAP.put(Float.class, Float.MAX_VALUE);
		CLASS_VALUE_MAP.put(float[].class, new float[]{Float.MIN_VALUE , Float.MAX_VALUE});
		CLASS_VALUE_MAP.put(Float[].class, new Float[]{Float.MIN_VALUE , Float.MAX_VALUE});
		
		CLASS_VALUE_MAP.put(int.class, Integer.MAX_VALUE);
		CLASS_VALUE_MAP.put(Integer.class, Integer.MAX_VALUE);
		CLASS_VALUE_MAP.put(int[].class, new int[]{Integer.MIN_VALUE , Integer.MAX_VALUE});
		CLASS_VALUE_MAP.put(Integer[].class, new Integer[]{Integer.MIN_VALUE , Integer.MAX_VALUE});
		
		CLASS_VALUE_MAP.put(long.class, Long.MAX_VALUE);
		CLASS_VALUE_MAP.put(Long.class, Long.MAX_VALUE);
		CLASS_VALUE_MAP.put(long[].class, new long[]{Long.MIN_VALUE , Long.MAX_VALUE});
		CLASS_VALUE_MAP.put(Long[].class, new Long[]{Long.MIN_VALUE , Long.MAX_VALUE});
		
		CLASS_VALUE_MAP.put(short.class, Short.MAX_VALUE);
		CLASS_VALUE_MAP.put(Short.class, Short.MAX_VALUE);
		CLASS_VALUE_MAP.put(short[].class, new short[]{Short.MIN_VALUE , Short.MAX_VALUE});
		CLASS_VALUE_MAP.put(Short[].class, new Short[]{Short.MIN_VALUE , Short.MAX_VALUE});
		
		CLASS_VALUE_MAP.put(String.class, "String");
		CLASS_VALUE_MAP.put(String[].class, new String[]{"String" , "" });
		
		java.util.Date utilDate = new java.util.Date();
		CLASS_VALUE_MAP.put(java.util.Date.class, utilDate);
		CLASS_VALUE_MAP.put(java.util.Date[].class, new java.util.Date[]{utilDate});
		
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		CLASS_VALUE_MAP.put(java.sql.Date.class, sqlDate);
		CLASS_VALUE_MAP.put(java.sql.Date[].class, new java.sql.Date[]{sqlDate});
		
		java.util.Calendar calendar = java.util.Calendar.getInstance();
		CLASS_VALUE_MAP.put(java.util.Calendar.class, calendar);
		CLASS_VALUE_MAP.put(java.util.Calendar[].class, new java.util.Calendar[]{calendar});
		
		LocalDate localDate = LocalDate.now();
		CLASS_VALUE_MAP.put(LocalDate.class, localDate);
		CLASS_VALUE_MAP.put(LocalDate[].class, new LocalDate[]{localDate});
		
		LocalTime localTime = LocalTime.now();
		CLASS_VALUE_MAP.put(LocalTime.class, localTime);
		CLASS_VALUE_MAP.put(LocalTime[].class, new LocalTime[]{localTime});
		
		LocalDateTime localDateTime = LocalDateTime.now();
		CLASS_VALUE_MAP.put(LocalDateTime.class, localDateTime);
		CLASS_VALUE_MAP.put(LocalDateTime[].class, new LocalDateTime[]{localDateTime});
		
		Instant instant = Instant.now();
		CLASS_VALUE_MAP.put(Instant.class, instant);
		CLASS_VALUE_MAP.put(Instant[].class, new Instant[]{instant, instant});
		
		ZonedDateTime zonedDateTime = ZonedDateTime.now();
		CLASS_VALUE_MAP.put(ZonedDateTime.class, zonedDateTime);
		CLASS_VALUE_MAP.put(ZonedDateTime[].class, new ZonedDateTime[]{zonedDateTime, zonedDateTime});
		
		CLASS_VALUE_MAP.put(BigDecimal.class, BigDecimal.TEN);
		CLASS_VALUE_MAP.put(BigDecimal[].class, new BigDecimal[]{BigDecimal.TEN , BigDecimal.ONE });
		
		CLASS_VALUE_MAP.put(BigInteger.class, BigInteger.TEN);
		CLASS_VALUE_MAP.put(BigInteger[].class, new BigInteger[]{BigInteger.TEN , BigInteger.ONE });
		
		Object object = new Object();
		CLASS_VALUE_MAP.put(Object.class, new Object());
		CLASS_VALUE_MAP.put(Object[].class, new Object[]{object});
		
		CLASS_VALUE_MAP.put(Set.class, Collections.emptySet());
		CLASS_VALUE_MAP.put(TreeSet.class, Collections.emptySet());
		CLASS_VALUE_MAP.put(HashSet.class, Collections.emptySet());
		CLASS_VALUE_MAP.put(LinkedHashSet.class, Collections.emptySet());
		CLASS_VALUE_MAP.put(SortedSet.class, Collections.emptySet());
		
		CLASS_VALUE_MAP.put(List.class, Collections.emptyList());
		CLASS_VALUE_MAP.put(ArrayList.class, Collections.emptyList());
		CLASS_VALUE_MAP.put(LinkedList.class, Collections.emptyList());
		CLASS_VALUE_MAP.put(Deque.class, Collections.emptyList());
		CLASS_VALUE_MAP.put(Queue.class, Collections.emptyList());
		CLASS_VALUE_MAP.put(Stack.class, Collections.emptyList());
		CLASS_VALUE_MAP.put(Vector.class, Collections.emptyList());
		
		CLASS_VALUE_MAP.put(Map.class, Collections.emptyMap());
		CLASS_VALUE_MAP.put(HashMap.class, Collections.emptyMap());
		CLASS_VALUE_MAP.put(Hashtable.class, Collections.emptyMap());
		CLASS_VALUE_MAP.put(LinkedHashMap.class, Collections.emptyMap());
		CLASS_VALUE_MAP.put(SortedMap.class, Collections.emptyMap());
		CLASS_VALUE_MAP.put(TreeMap.class, Collections.emptyMap());
		
		
		CLASS_VALUE_MAP.put(Stream.class, Stream.empty());
		
		CLASS_VALUE_MAP.put(UUID.class, UUID.randomUUID());
	}
	
	/**
	 * This method return default value of parameter class type. 
	 * @param clazz
	 * @return default object value
	 * @since 1.0
	 */
	public static Object getValueFromMap(final Class<?> clazz){
		return CLASS_VALUE_MAP.get(clazz);
	}
		
}
