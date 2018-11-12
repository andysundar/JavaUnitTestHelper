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

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;

import org.pojotester.object.mock.MockDependencyObject;
import org.pojotester.object.mock.MockInterfaceObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class provide class level reflection utilities like {@code ClassLoader}, loading class , 
 * creating object of the argument class and return default values of some widely used data types. 
 * @author Anindya Bandopadhyay
 * @since 1.0
 */
public abstract class ClassUtilities {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtilities.class);
	
	private static final String[] CREATE_METHOD = {"now", "getInstance"};
	/**
	 * This method first try to get context class loader from current thread then it try to get it from default class loader, 
	 * then it try to get it from system class loader. 
	 * @return {@code ClassLoader} object if found else {@code null}
	 * @since 1.0
	 */
	public static ClassLoader getDefaultClassLoader(){
		ClassLoader classLoader = null;
		Thread currentThread = Thread.currentThread();
		if(currentThread != null) {
			// Thread context class loader 
			classLoader = currentThread.getContextClassLoader();
		} else {
			// Default class loader 
			classLoader = ClassUtilities.class.getClassLoader();
			if(classLoader == null) {
				// boot strap loader
				classLoader = ClassLoader.getSystemClassLoader();
			}
		}
		return classLoader;
	}
	
	/**
	 * This method the system class loader. 
	 * @return {@code ClassLoader} object if found else {@code null}
	 * @since 1.0
	 */
	public static ClassLoader getSystemClassLoader(){
		
		return ClassLoader.getSystemClassLoader();
	}
	
	/**
	 * This method load the class then return the class object else return {@code null}.
	 * @param className
	 * @return {@code Class} object if loaded else {@code null}
	 * @since 1.0
	 */
	public static Class<?> loadClass(final String className){
		ClassLoader classLoader = getDefaultClassLoader();
		Class<?> clazz = null;
		try {
			clazz = (classLoader != null) ?  classLoader.loadClass(className) : Class.forName(className);
		} catch (ClassNotFoundException e) {
			LOGGER.debug("Not able to load " + className, e);
			e.printStackTrace();
		}
		return clazz;
	}
	
	/**
	 * This method create object of parameter class using default constructor. 
	 * @param clazz
	 * @return object of parameter class
	 * @since 1.0
	 */
	public static Object createObject(final Class<?> clazz){
		Object object = null;
		try {
			object = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			LOGGER.debug("Not able to initialize using default constructor of " + clazz.getName() + 
					".\n Now trying with other constructor (if any).", e);
			object =  createObjectUsingOtherConstructor(clazz);
		}
		return object;
	}
	
	/**
	 * This method create object of parameter class using other constructor. 
	 * @param clazz
	 * @return object of parameter class
	 * @since 1.0
	 */
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
				} else if(parameterDataTypeClass.isInterface()){
					MockInterfaceObject mockObject = new MockInterfaceObject(parameterDataTypeClass);
					args[index] = mockObject.proxy();
				} else {
					MockDependencyObject mockObject = new MockDependencyObject();
					args[index] = mockObject.getProxyObject(parameterDataTypeClass);
				} 
				index++;
			}
			try {
				object = constructor.newInstance(args);
				break;
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException ex) {
				LOGGER.debug("Not able to initialize using other constructor of " + clazz.getName(), ex);
				object = createObjectUsingStaticMethod(clazz);
			}
		}
		return object;
	}
	
	/**
	 * This method create object of parameter class using standard static factory method like 'now', 'getInstance'.
	 * @param clazz
	 * @return object of parameter class
	 */
	public static Object createObjectUsingStaticMethod(final Class<?> clazz){
		Object object = null;
		Class<?>[] parameterTypes = {};
		Method method = null;
		for(String name : CREATE_METHOD) {
			try {
				method = clazz.getDeclaredMethod(name, parameterTypes);
				if(method != null) {
					object = createObjectUsingStaticMethod(clazz, method);
					break;
				}
			} catch (NoSuchMethodException | SecurityException e) {
				LOGGER.debug("Not able to find standard create method in this class.", e);
			}
		}
		
		return object;
	}
	/**
	 * This method create object of parameter class using method. 
	 * @param clazz, method
	 * @return object of parameter class
	 * @since 1.0
	 */
	public static Object createObjectUsingStaticMethod(final Class<?> clazz, Method method) {
		Object object = null;
		
		int methodModifier = method.getModifiers();
		if (Modifier.isStatic(methodModifier) && Modifier.isPublic(methodModifier)) {
			try {
				Object[] varargs = null;
				object = method.invoke(null, varargs);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				LOGGER.debug("Not able to invoke " + method.getName() + " in " + clazz.getName(), e);
			}
		}

		return object;
	}
	
	/**
	 * This method return default value of parameter class type. 
	 * @param clazz
	 * @return default object value
	 * @since 1.0
	 */
	public static Object getValueFromMap(Class<?> clazz){
		return DefaultValueUtilities.getValueFromMap(clazz);
	}
	
	/**
	 * This method return true if it is a concrete class else false of parameter class. 
	 * @param clazz
	 * @return true if it is a concrete class else false
	 * @since 1.1
	 */
	public static boolean isConcreteClass(Class<?> clazz) {
		return isClass(clazz) && (!clazz.isInterface()) && (!Modifier.isAbstract(clazz.getModifiers()));
	}
	
	/**
	 * This method return true if it is a class else false of parameter class. 
	 * @param clazz
	 * @return true if it is a class else false
	 * @since 1.1
	 */
	public static boolean isClass(Class<?> clazz) {
		return (clazz != null) && (!clazz.isAnnotation()) && (!clazz.isEnum() && (!clazz.isArray()));
	}

	/**
	 * This method return {@code Field} object of parameter class. 
	 * @param clazz , fieldName
	 * @return field object if there is a declared field by that name in that class else return null
	 * @since 1.1
	 */
	public static Field getField(Class<?> clazz, String fieldName) {
		Field field = null;
		try {
			field = clazz.getDeclaredField(fieldName);
		} catch (NoSuchFieldException | SecurityException e) {
			LOGGER.debug(fieldName + " field name is not present in " + clazz.getName(), e);
		}
		return field;
	}
	
	/**
	 * This method return {@code Method} object of parameter class type. 
	 * @param clazz, methodName, parameterTypes
	 * @return method object if there is a declared method by that name and parameter types in that class else return null
	 * @since 1.1
	 */
	public static Method getDeclaredMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
		Method method = null;
		try {
			method = clazz.getDeclaredMethod(methodName, parameterTypes);
		} catch (NoSuchMethodException | SecurityException e) {
			LOGGER.error(String.format("%s method not found", methodName), e);
		}
		return method;
	}

	/**
	 * This method return {@code Method} objects of parameter class type.
	 * @param clazz
	 * @return all declared methods
	 * @since 1.1
	 */
	public static Method[] getDeclaredMethods(Class<?> clazz) {
		return clazz.getDeclaredMethods();
	}

	public static Object createObjectUsingAnnotated(Class<?> clazz) {
		Object object ;
		Method[] methods = getDeclaredMethods(clazz);
		Method createObjectMethod = MethodUtilities.findCreateObjectMethod(methods);

		if(createObjectMethod == null) {
			object = createObject(clazz);
		} else {
			object = createObjectUsingStaticMethod(clazz, createObjectMethod);
		}
		return object;
	}
}
