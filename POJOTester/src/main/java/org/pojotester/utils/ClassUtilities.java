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
	
	public static Object getValueFromMap(Class<?> clazz){
		return DefaultValueUtilities.getValueFromMap(clazz);
	}

}
