package org.pojotester.mock;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.pojotester.utils.ClassUtilities;

public class MockObject implements InvocationHandler {

	private Object proxy;
	
	public MockObject(Class<?> clazz){
		this.proxy = Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
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
