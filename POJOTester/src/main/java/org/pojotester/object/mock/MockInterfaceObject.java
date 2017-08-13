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
package org.pojotester.object.mock;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.pojotester.utils.ClassUtilities;

public class MockInterfaceObject implements InvocationHandler {

	private Object proxy;
	
	public MockInterfaceObject(Class<?> clazz){
		ClassLoader classLoader = ClassUtilities.getDefaultClassLoader();
		this.proxy = Proxy.newProxyInstance(classLoader, new Class<?>[]{clazz}, this);
	}
	
	public Object proxy(){
		return this.proxy;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		return returnMockValue(method);
	}

	private Object returnMockValue(Method method) {
		Class<?> methodReturnType = method.getReturnType();
		Object object = ClassUtilities.getValueFromMap(methodReturnType);
		return object;
	}
}
