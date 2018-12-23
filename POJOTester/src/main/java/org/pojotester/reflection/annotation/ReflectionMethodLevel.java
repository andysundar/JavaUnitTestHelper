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

package org.pojotester.reflection.annotation;

import java.lang.reflect.Method;

import org.pojotester.annotation.method.CreateObjectMethod;
import org.pojotester.annotation.method.ReadMethod;
import org.pojotester.annotation.method.WriteMethod;

/**
 * {@code ReflectionMethodLevel} is an utility class to take necessary actions against declared annotation at method level.
 * @author Anindya Bandopadhyay
 * @since 1.0
 */
public abstract class ReflectionMethodLevel {
	
	/**
	 * This method check whether {@link CreateObjectMethod} present at method level. 
	 * @param method on which checking the annotation
	 * @return {@code true} if {@code @CreateObjectMethod} present else {@code false}
	 * @since 1.0
	 */
	public static boolean isCreateMethod(final Method method){
		boolean thisIsCreateMethod = method.isAnnotationPresent(CreateObjectMethod.class);
		return thisIsCreateMethod;
	}
	
	/**
	 * This method check if {@link ReadMethod} present and if present the get the field name.  
	 * @param method on which checking the annotation
	 * @return field name if {@code @ReadMethod} present else {@code null}.
	 * @since 1.0
	 */
	public static String getFieldNameOfReadMethod(final Method method){
		boolean thisIsReadMethod = method.isAnnotationPresent(ReadMethod.class);
		String fieldName = null;
		if(thisIsReadMethod){
			ReadMethod readMethod = method.getDeclaredAnnotation(ReadMethod.class);
			fieldName = readMethod.fieldName();
		}
		return fieldName;
	}
	
	/**
	 * This method check if {@link WriteMethod} present and if present the get the field name.  
	 * @param method on which checking the annotation
	 * @return field name if {@code @WriteMethod} present else {@code null}.
	 * @since 1.0
	 */
	public static String getFieldNameOfWriteMethod(final Method method){
		boolean thisIsWriteMethod = method.isAnnotationPresent(WriteMethod.class);
		String fieldName = null;
		if(thisIsWriteMethod){
			WriteMethod writeMethod = method.getDeclaredAnnotation(WriteMethod.class);
			fieldName = writeMethod.fieldName();
		}
		return fieldName;
	}
}
