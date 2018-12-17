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
		ClassLoader classLoader;
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
	 * @param className qualified class name
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
	 * @param clazz class whome object will be created.
	 * @return object of parameter class
	 * @since 1.0
	 */
	public static Object createObject(final Class<?> clazz){
		return createObjectUsingOtherConstructor(clazz);
	}
	
	/**
	 * This method create object of parameter class using other constructor
	 * and it the class is an interface create proxy object for use.
	 * @param clazz class whome object will be created.
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
				} else {
					args[index] = createProxy(parameterDataTypeClass);
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
		if(clazz.isInterface()) {
			object = createProxy(clazz);
		}
		return object;
	}
	
	/**
	 * This method create object of parameter class using standard static factory method like 'now', 'getInstance'.
	 * @param clazz class whome object will be created.
	 * @return object of parameter class
	 *
	 * @since 1.0.2
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
	 * This method create objects of parameter class using all constructors.
	 * @param clazz class whome object will be created.
	 * @return objects of parameter class
	 * @since 1.0.2
	 */
	public static Object[] createObjectsUsingAllConstructors(final Class<?> clazz){
		Constructor<?>[] constructors = clazz.getConstructors();
		Object[] objects = new Object[constructors.length];
		int numOfConstructors = 0;
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
					args[index] = createProxy(parameterDataTypeClass);
				}
				index++;
			}
			try {
				objects[numOfConstructors] = constructor.newInstance(args);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException ex) {
				LOGGER.debug("Not able to initialize using other constructor of " + clazz.getName(), ex);
			}

			numOfConstructors++;
		}

		return objects;
	}

	/**
	 * This method create object of parameter class using method. 
	 * @param clazz, method class whome object will be created using static method
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
	 * @param clazz class to be lookup from default value list.
	 * @return default object value
	 * @since 1.0
	 */
	public static Object getValueFromMap(Class<?> clazz){
		return DefaultValueUtilities.getValueFromMap(clazz);
	}
	
	/**
	 * This method return true if it is a concrete class else false of parameter class. 
	 * @param clazz {@code Class}
	 * @return true if it is a concrete class else false
	 * @since 1.0.2
	 */
	public static boolean isConcreteClass(Class<?> clazz) {
		return isClass(clazz) && (!clazz.isInterface()) && (!Modifier.isAbstract(clazz.getModifiers()));
	}
	
	/**
	 * This method return true if it is a class else false of parameter class. 
	 * @param clazz {@code Class}
	 * @return true if it is a class else false
	 * @since 1.0.2
	 */
	public static boolean isClass(Class<?> clazz) {
		return (clazz != null) && (!clazz.isAnnotation()) && (!clazz.isEnum() && (!clazz.isArray()));
	}

	/**
	 * This method return {@code Field} object of parameter class. 
	 * @param clazz , fieldName {@code Class} and field name
	 * @return field object if there is a declared field by that name in that class else return null
	 * @since 1.0.2
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
	 * @param clazz, methodName, parameterTypes {@code Class} , method  name looking for along with parameters.
	 * @return method object if there is a declared method by that name and parameter types in that class else return null
	 * @since 1.0.2
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
	 * This method return {@code Method} objects of the parameter class type.
	 * @param clazz {@code Class}
	 * @return all declared methods
	 * @since 1.0.2
	 */
	public static Method[] getDeclaredMethods(Class<?> clazz) {
		return clazz.getDeclaredMethods();
	}

	/**
	 * This method return an object of the parameter class type using {@code createObjectMethod}
	 * static method. If the method is null then it will try create using class constructor.
	 * @param clazz {@code Class}
	 * @param createObjectMethod static method using which object will be created.
	 * @return object of the parameter class type.
	 * @since 1.0.2
	 */
	public static Object createObjectUsingAnnotated(Class<?> clazz, Method createObjectMethod) {
		Object object ;

		if(createObjectMethod == null) {
			object = createObject(clazz);
		} else {
			object = createObjectUsingStaticMethod(clazz, createObjectMethod);
		}
		return object;
	}

	/**
	 * This method return a proxy object of the parameter class type.
	 * @param clazz {@code Class}
	 * @return a proxy object
	 * @since 1.0.2
	 */
	public static Object createProxy(final Class<?> clazz){
		MockDependencyObject mockObject = new MockDependencyObject();
		return  mockObject.getProxyObject(clazz);
	}

	/**
	 * This method return {@code true} if more than constructor present else {@code false}.
	 * @param clazz {@code Class}
	 * @return {@code true} if more than constructor present else {@code false}.
	 *
	 * @since 1.0.2
	 */
	public static  boolean isMultipleConstructorPresent(final Class<?> clazz) {
		return  clazz.getConstructors().length > 1;
	}
}
