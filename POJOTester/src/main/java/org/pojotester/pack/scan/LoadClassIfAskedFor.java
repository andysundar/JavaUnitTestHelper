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
package org.pojotester.pack.scan;

import org.pojotester.reflection.annotation.ReflectionClassLevel;
import org.pojotester.utils.ClassUtilities;

/**
 * This class extends {@link PackageScan} and load only those classes which have {@code @TestThisClass}
 * annotation declared. 
 * @author Anindya Bandopadhyay
 * @since 1.0
 */
public final class LoadClassIfAskedFor extends PackageScan {
	
	@Override
	protected Class<?> loadClass(String className){
		return loadClassIfAskedFor(className);
	}

	private Class<?> loadClassIfAskedFor(final String className){
		Class<?> clazz = ClassUtilities.loadClass(className);
		if(clazz != null) {
		    boolean testThisClass = ReflectionClassLevel.testThisClass(clazz);
			if(!testThisClass){
				clazz = null;
			}
		}
		return clazz;
	}
}
