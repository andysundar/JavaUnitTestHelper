package org.pojotester.object.mock;

import java.lang.reflect.Method;

import org.pojotester.utils.ClassUtilities;

import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;

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