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
package org.pojotester.test.values;

import org.pojotester.reflection.annotation.ReflectionFieldLevel;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class is the factory class which return an object {@link TestConfiguration}.
 * @author Anindya Bandopadhyay
 * @since 1.0
 */
public final class TestConfigurationFactory {

    private final Map<String, TestConfiguration<?>> cache = new ConcurrentHashMap<>();


	public TestConfiguration<?> createTestConfiguration(Object object, Field field) {
        TestConfiguration<?> testConfiguration = null;
        boolean ignore = ReflectionFieldLevel.ignoreField(field);
        if (!ignore) {
            Class<?> clazz = field.getDeclaringClass();
            String classFieldName = clazz.getName() + "." + field.getName();
            testConfiguration = cache.get(classFieldName);

            if(testConfiguration == null && object != null) {
                testConfiguration = ReflectionFieldLevel.assignValues(field);
                cache.put(classFieldName, testConfiguration);

                testConfiguration.setClassFieldName(classFieldName);
                testConfiguration.setObject(object);
                testConfiguration.setField(field);
            }
        }
        return testConfiguration;
    }

    public Collection<TestConfiguration<?>> getTestConfigurations(){
	    return cache.values();
    }

}
