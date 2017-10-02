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
package org.pojotester.object.mock;


import org.pojotester.log.PojoTesterLogger;
import org.pojotester.utils.ClassUtilities;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType.Builder;
import net.bytebuddy.dynamic.DynamicType.Unloaded;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

public class MockDependencyObject {

	public Object getProxyObject(Class<?> clazz){
		Object object = null;
		if(clazz != null){
			ByteBuddy byteBuddy = new ByteBuddy();
			Builder<?> builder = byteBuddy.subclass(clazz);
			Unloaded<?> uploaded = builder.method(ElementMatchers.any())
					.intercept(MethodDelegation.to(MyInterceptor.class)).make();
			ClassLoader classLoader = ClassUtilities.getDefaultClassLoader();
			Class<?> subclazz = uploaded.load(classLoader).getLoaded();
		    try {
				object = subclazz.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				PojoTesterLogger.debugMessage("Not able to create proxy object of dependent's proxy.\n"
						+ "If this is happend during constructor invoke then if possible please use @CreateObjectMethod annotation.\n", e);
			}
		}
		return object;
	}
}
