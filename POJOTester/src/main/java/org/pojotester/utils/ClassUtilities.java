package org.pojotester.utils;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
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

import org.pojotester.mock.MockObject;

public abstract class ClassUtilities {
		
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
	
	public static ClassLoader getDefaultClassLoader(){
		ClassLoader classLoader = null;
		Thread currentThread = Thread.currentThread();
		if(currentThread != null) {
			// Thread context class loader 
			classLoader = currentThread.getContextClassLoader();
		} else {
			// Default class loader 
			classLoader = ClassUtilities.getDefaultClassLoader();
			if(classLoader == null) {
				// boot strap loader
				classLoader = ClassLoader.getSystemClassLoader();
			}
		}
		return classLoader;
	}
	
	public static Class<?> loadClass(final String className){
		ClassLoader classLoader = getDefaultClassLoader();
		Class<?> clazz = null;
		try {
			clazz = (classLoader != null) ?  classLoader.loadClass(className) : Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return clazz;
	}
	
	public static Object createObject(final Class<?> clazz){
		Object object = null;
		try {
			object = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			object =  createObjectUsingOtherConstructor(clazz);
		}
		return object;
	}
	
	public static Object createObjectUsingOtherConstructor(final Class<?> clazz){
		Object object = null;
		Constructor<?>[] constructors = clazz.getConstructors();
		for(Constructor<?> constructor : constructors){
			Parameter[] parameters = constructor.getParameters();
			Object[] args = new Object[parameters.length];
			int index = 0;
			for(Parameter parameter : parameters) {
				Class<?> parameterDataTypeClass = parameter.getType();
				Object obj = getValueFromMap(parameterDataTypeClass);
				if(obj != null) {
					args[index] = obj;
				} else if(parameterDataTypeClass.isEnum()) {
					args[index] = parameterDataTypeClass.getEnumConstants()[0];
				} else if(parameterDataTypeClass.isArray()){
					Class<?> typeOfArray = parameterDataTypeClass.getComponentType();
					args[index] = Array.newInstance(typeOfArray, 0);
				} else {
					MockObject mockObject = new MockObject(parameterDataTypeClass);
					args[index] = mockObject.proxy();
				}
				index++;
			}
		}
		return object;
	}
	
	public static Object getValueFromMap(final Class<?> clazz){
		return classValueMap.get(clazz);
	}
}
