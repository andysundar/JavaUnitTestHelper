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

import java.lang.reflect.Method;

import org.pojotester.utils.ClassUtilities;

import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;

/**
 * This class is acting as a proxy interceptor.
 * @author Anindya Bandopadhyay
 * @since 1.0
 */
public class MyInterceptor {
	  @RuntimeType
	  public static Object intercept(@Origin Method method) {
		  Object returnObject = null;
		  if(method != null){
			  Class<?> methodReturnType = method.getReturnType();
			  returnObject = ClassUtilities.getValueFromMap(methodReturnType);
		  }
		return returnObject;
	  }
	}
