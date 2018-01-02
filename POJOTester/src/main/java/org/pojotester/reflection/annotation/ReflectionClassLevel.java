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

import org.pojotester.annotation.clazz.IgnoreClass;
import org.pojotester.annotation.clazz.TestThisClass;

/**
 * {@code ReflectionClassLevel} is an utility class to check annotation present at class level.
 * @author Anindya Bandopadhyay
 * @since 1.0
 */
public abstract class ReflectionClassLevel {

	/**
	 * This method check whether {@link IgnoreClass} annotation present on parameter class.
	 * @param clazz 
	 * @return {@code true} if {@code @IgnoreClass} present else {@code false}. 
	 * @since 1.0
	 */
	public static boolean ignoreClass(Class<?> clazz){
	   boolean ignoreThisClass =	clazz.isAnnotationPresent(IgnoreClass.class);
	   return ignoreThisClass;
	}
	
	/**
	 * This method check whether {@link TestThisClass} annotation present on parameter class.
	 * @param clazz 
	 * @return {@code true} if {@code @TestThisClass} present else {@code false}. 
	 * @since 1.0
	 */
	public static boolean testThisClass(Class<?> clazz) {
		boolean testThisClass = clazz.isAnnotationPresent(TestThisClass.class);
		return testThisClass;
	}
}
