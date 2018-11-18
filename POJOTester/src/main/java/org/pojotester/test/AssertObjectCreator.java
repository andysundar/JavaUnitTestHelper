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

package org.pojotester.test;

import org.pojotester.pack.scan.LoadClassIfAskedFor;
import org.pojotester.pack.scan.LoadClassIfNotIgnored;
import org.pojotester.pack.scan.PackageScan;
import org.pojotester.reflection.PropertyFinder;
import org.pojotester.test.override.method.AbstractTester;
import org.pojotester.test.override.method.EqualsTester;
import org.pojotester.test.override.method.HashCodeTester;
import org.pojotester.test.override.method.ToStringTester;
import org.pojotester.test.values.AssertObject;
import org.pojotester.test.values.TestConfiguration;
import org.pojotester.utils.ClassUtilities;
import org.pojotester.utils.FieldUtilities;
import org.pojotester.utils.MethodUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class create assert object for unit testing. 
 * @author Anindya Bandopadhyay
 * @since 1.0
 */
public class AssertObjectCreator implements IAssertObjectCreator {

	private static final Logger LOGGER = LoggerFactory.getLogger(AssertObjectCreator.class);
	private static final String EQUALS = "equals";
	private static final String HASH_CODE = "hashCode";
	private static final String TO_STRING = "toString";

	private boolean loadClassesAskedFor;
//	private boolean testToStringMethod;
//	private boolean testHashCodeMethod;
//	private boolean testEqualsMethod;

	/**
	 * Object created using this constructor will consider all classes in a package for unit testing
	 * if and only if {@code @IgnoreClass} is declared.
	 *
	 * @since 1.0
	 */
	public AssertObjectCreator() {
		this(false);
	}

	/**
	 * Object created using this constructor with parameter as {@code true} will consider only {@code @TestThisClass}
	 * declared classes in a package for unit testing and if it is used with parameter {@code false} it is act same
	 * as default constructor.
	 *
	 * @since 1.0
	 */
	public AssertObjectCreator(boolean loadClassesAskedFor) {
		this.loadClassesAskedFor = loadClassesAskedFor;
	}

	/**
	 * This method return list of assertion objects created from the classes which are present in parameterised packages.
	 *
	 * @param packagesToScan
	 * @return list of {@code AssertObject} objects
	 * @since 1.0
	 */
	public List<AssertObject<?>> getAssertObjects(final String... packagesToScan) {
		List<AssertObject<?>> assertObjectList = new LinkedList<>();
		PackageScan packageScan = loadClassesAskedFor ? new LoadClassIfAskedFor() : new LoadClassIfNotIgnored();
		Set<Class<?>> uniqueClasses = packageScan.getClasses(packagesToScan);
		for (Class<?> clazz : uniqueClasses) {
			Method[] methods = ClassUtilities.getDeclaredMethods(clazz);
			Method createObjectMethod = MethodUtilities.findCreateObjectMethod(methods);

			Object object = ClassUtilities.createObjectUsingAnnotated(clazz, createObjectMethod);
			PropertyFinder propertyFinder = new PropertyFinder(clazz, methods, object);
			Collection<TestConfiguration<?>> testConfigurations = propertyFinder.getTestConfigurationList();
			for (TestConfiguration<?> testConfiguration : testConfigurations) {
				assertObjectList.addAll(testConfiguration.assertAssignedValues());
			}

			System.gc();

			Class<?>[] args = {};
			Method toStringMethod = ClassUtilities.getDeclaredMethod(clazz, TO_STRING, args);
			Method hashCodeMethod = ClassUtilities.getDeclaredMethod(clazz, HASH_CODE, args);
			Method equalsMethod = ClassUtilities.getDeclaredMethod(clazz, EQUALS, Object.class);

			List<Field> fieldList = testConfigurations.stream().map(
					TestConfiguration::getField).collect(Collectors.toList());
			Object sameObject1 = null;
			Object sameObject2 = null;
			Object sameObject3 = null;
			Object differentObject = null;

			if(toStringMethod != null || equalsMethod != null || hashCodeMethod != null) {
				sameObject1 = object;
				sameObject2 = ClassUtilities.createObjectUsingAnnotated(clazz, createObjectMethod);
				sameObject3 = ClassUtilities.createObjectUsingAnnotated(clazz, createObjectMethod);
				differentObject = ClassUtilities.createObjectUsingAnnotated(clazz, createObjectMethod);

				for(Field field : fieldList) {
					Object fieldValue = FieldUtilities.getFieldValue(field, sameObject1);
					FieldUtilities.setFieldValue(field, sameObject2, fieldValue);
					FieldUtilities.setFieldValue(field, sameObject3, fieldValue);
				}
			}

			AbstractTester tester = null;
			if (toStringMethod != null) {
                tester = new ToStringTester(sameObject1, sameObject2, sameObject3, differentObject);
                List<AssertObject<?>> toStringTests = tester.createTests();
                assertObjectList.addAll(toStringTests);
			}

			if(equalsMethod != null) {
                tester = new EqualsTester(sameObject1, sameObject2, sameObject3, differentObject, fieldList);
                List<AssertObject<?>> equalsTests = tester.createTests();
                assertObjectList.addAll(equalsTests);
			}

			if(hashCodeMethod != null) {
                tester = new HashCodeTester(sameObject1, sameObject2, sameObject3, differentObject);
                List<AssertObject<?>> hashCodeTests = tester.createTests();
                assertObjectList.addAll(hashCodeTests);
			}

			}

			return assertObjectList;
		}



}
