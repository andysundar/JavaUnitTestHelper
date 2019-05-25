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

package org.pojotester.reflection;

import org.pojotester.reflection.annotation.ReflectionMethodLevel;
import org.pojotester.test.values.TestConfiguration;
import org.pojotester.test.values.TestConfigurationFactory;
import org.pojotester.utils.ClassUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;


public final class PropertyFinder {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyFinder.class);

    private Class<?> clazz;
    private Method[] methods;
    private Object object;
    TestConfigurationFactory testConfigurationFactory;
    public PropertyFinder(Class<?> clazz, Method[] methods, Object object) {
        this.clazz = clazz;
        this.methods = methods;
        this.object = object;
        testConfigurationFactory = new TestConfigurationFactory();
    }

    public Collection<TestConfiguration<?>> getTestConfigurationList(){
        createTestConfigurationsFromIntrospection();
        createTestConfigurationsFromAnnotation();
        return testConfigurationFactory.getTestConfigurations();
    }


    private void createTestConfigurationsFromIntrospection() {
        BeanInfo beanInfo;
        PropertyDescriptor[] propertyDescriptors = null;
        try {
            beanInfo = Introspector.getBeanInfo(clazz, Object.class);
            propertyDescriptors = beanInfo.getPropertyDescriptors();
        } catch (IntrospectionException e) {
            LOGGER.debug("Not able to load properties" , e);
        }

        if (propertyDescriptors != null) {
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                Method readMethod = propertyDescriptor.getReadMethod();
                Method writeMethod = propertyDescriptor.getWriteMethod();
                String fieldName = propertyDescriptor.getName();
                Field field = ClassUtilities.getField(clazz, fieldName);
                TestConfiguration<?> testConfiguration = null;
				if (field == null) {
					String message = String.format(
							"%s class did not have a field name %s.%nPlease check the bean method name matches with respective field name.",
							clazz.getSimpleName(), fieldName);
					LOGGER.error(message);
					continue;
				}
                testConfiguration = testConfigurationFactory.createTestConfiguration(object, field);
                populateReadWriteMethodsIntoTestConfiguration(readMethod, writeMethod, testConfiguration);

                logWriteMethodMessage(writeMethod, field);
                logReadMethodMessage(readMethod, field);
            }
        }
    }

	private void populateReadWriteMethodsIntoTestConfiguration(Method readMethod, Method writeMethod,
			TestConfiguration<?> testConfiguration) {
		if(testConfiguration != null) {
		    if(writeMethod != null) {
		        testConfiguration.setWriteMethod(writeMethod);
		    }

		    if(readMethod != null) {
		        testConfiguration.setReadMethod(readMethod);
		    }
		}
	}

    private void createTestConfigurationsFromAnnotation() {
        for (Method method : methods) {
            String fieldName = ReflectionMethodLevel.getFieldNameOfReadMethod(method);
            populateMethod(method, fieldName, false);

            fieldName = ReflectionMethodLevel.getFieldNameOfWriteMethod(method);
            populateMethod(method, fieldName, true);
        }
    }

    private void populateMethod(Method method, String fieldName, boolean writeMethod) {
        if(fieldName != null && !fieldName.isEmpty()){
            Field field = ClassUtilities.getField(clazz, fieldName);
            if (field != null) {
                TestConfiguration<?> testConfiguration = testConfigurationFactory.createTestConfiguration(object, field);
                if(writeMethod) {
                    if(method.getParameterCount() > 0 && method.getParameterTypes()[0] == field.getType()
                            && testConfiguration != null){
                        testConfiguration.setWriteMethod(method);
                    }
                    logWriteMethodMessage(method, field);
                } else {
                    if(method.getReturnType() == field.getType() && testConfiguration != null){
                        testConfiguration.setReadMethod(method);
                    }
                    logReadMethodMessage(method, field);
                }

            }
        }
    }


    private void logWriteMethodMessage(Method writeMethod, Field field) {
        String message = String.format("Write method of [ %s ] is null.", field.getName());
        if (writeMethod != null) {
            if (writeMethod.getParameterCount() > 0) {
                message = String.format("%s write method's 1st parameter type %s  but field [ %s ] type is %s.",
                        writeMethod.getName(), writeMethod.getParameterTypes()[0], field.getName(), field.getType());
            } else {
                message =  String.format("%s write method did not have any parameter", writeMethod.getName());
            }
        }
        LOGGER.debug(message);
    }

    private void logReadMethodMessage(Method readMethod, Field field) {
        String message = String.format("Read method of [ %s ] is null.", field.getName());
        if (readMethod != null) {
            message = String.format("%s read method return %s but field [ %s ] type is %s.", readMethod.getName(),
                    readMethod.getReturnType(), field.getName(), field.getType());
        }
        LOGGER.debug(message);
    }

}
