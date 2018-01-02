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

import java.lang.reflect.Field;

import org.pojotester.annotation.field.BooleanTestValue;
import org.pojotester.annotation.field.ByteTestValue;
import org.pojotester.annotation.field.CharTestValue;
import org.pojotester.annotation.field.DoubleTestValue;
import org.pojotester.annotation.field.FloatTestValue;
import org.pojotester.annotation.field.IgnoreField;
import org.pojotester.annotation.field.IntTestValue;
import org.pojotester.annotation.field.LongTestValue;
import org.pojotester.annotation.field.ShortTestValue;
import org.pojotester.annotation.field.StringTestValue;
import org.pojotester.test.values.TestConfiguration;
import org.pojotester.test.values.TestConfigurationFactory;
import org.pojotester.type.convertor.PrimitiveToObjectArray;
 
/**
 * {@code ReflectionFieldLevel} is an utility class to take necessary actions against declared annotation at field level.
 * @author Anindya Bandopadhyay
 * @since 1.0
 */
public abstract class ReflectionFieldLevel {

	/**
	 * This method check whether {@link IgnoreField} present or not. 
	 * @param field
	 * @return {@code true} if {@code @IgnoreField} present else {@code false}
	 * @since 1.0
	 */
	public static boolean ignoreField(final Field field) {
		boolean isFieldIgnored = field.isAnnotationPresent(IgnoreField.class);
		return isFieldIgnored;
	}

	/**
	 * Based on field data type it create a {@link TestConfiguration} object and assign value to the field for test.
	 * @param field
	 * @return {@code TestConfiguration} object 
	 * @since 1.0
	 */
	public static TestConfiguration<?> assignValues(final Field field) {
		Class<?> typeClass = field.getType();
		TestConfiguration<?> testValues = null;

		if (typeClass == Boolean.class || typeClass == boolean.class || typeClass == Boolean[].class
				|| typeClass == boolean[].class) {
			BooleanTestValue booleanTestValue = field.getAnnotation(BooleanTestValue.class);
			testValues = createTestValues(booleanTestValue);
		} else if (typeClass == Byte.class || typeClass == byte.class || typeClass == Byte[].class
				|| typeClass == byte[].class) {
			ByteTestValue byteTestValue = field.getAnnotation(ByteTestValue.class);
			testValues = createTestValues(byteTestValue);
		} else if (typeClass == Character.class || typeClass == char.class || typeClass == Character[].class
				|| typeClass == char[].class) {
			CharTestValue charTestValue = field.getAnnotation(CharTestValue.class);
			testValues = createTestValues(charTestValue);
		} else if (typeClass == Double.class || typeClass == double.class || typeClass == Double[].class
				|| typeClass == double[].class) {
			DoubleTestValue doubleTestValue = field.getAnnotation(DoubleTestValue.class);
			testValues = createTestValues(doubleTestValue);
		} else if (typeClass == Float.class || typeClass == float.class || typeClass == Float[].class
				|| typeClass == float[].class) {
			FloatTestValue floatTestValue = field.getAnnotation(FloatTestValue.class);
			testValues = createTestValues(floatTestValue);
		} else if (typeClass == Integer.class || typeClass == int.class || typeClass == Integer[].class
				|| typeClass == int[].class) {
			IntTestValue intTestValue = field.getAnnotation(IntTestValue.class);
			testValues = createTestValues(intTestValue);
		} else if (typeClass == Long.class || typeClass == long.class || typeClass == Long[].class
				|| typeClass == long[].class) {
			LongTestValue longTestValue = field.getAnnotation(LongTestValue.class);
			testValues = createTestValues(longTestValue);
		} else if (typeClass == Short.class || typeClass == short.class || typeClass == Short[].class
				|| typeClass == short[].class) {
			ShortTestValue shortTestValue = field.getAnnotation(ShortTestValue.class);
			testValues = createTestValues(shortTestValue);
		} else if (typeClass == String.class || typeClass == String[].class) {
			StringTestValue stringTestValue = field.getAnnotation(StringTestValue.class);
			testValues = createTestValues(stringTestValue);
		} else {
			testValues = TestConfigurationFactory.createTestConfigurationObject(typeClass);
		}

		if (testValues != null) {
			testValues.setField(field);
		}
		return testValues;
	}

	protected static TestConfiguration<String> createTestValues(StringTestValue stringTestValue) {
		TestConfiguration<String> testValues = new TestConfiguration<String>();
		if (stringTestValue != null) {
			String[] assignValues = stringTestValue.assignValues();
			String[] expectedValues = stringTestValue.expectedValues();

			testValues.setAssignedValues(assignValues);
			testValues.setExpectedValues(expectedValues);
		}
		return testValues;
	}

	protected static TestConfiguration<Boolean> createTestValues(BooleanTestValue booleanTestValue) {
		TestConfiguration<Boolean> testValues = new TestConfiguration<Boolean>();
		if (booleanTestValue != null) {
			boolean[] assignValues = booleanTestValue.assignValues();
			boolean[] expectedValues = booleanTestValue.expectedValues();

			Boolean[] assignObjectValues = PrimitiveToObjectArray.convertPrimitiveToObjectArray(assignValues);
			Boolean[] expectedObjectValues = PrimitiveToObjectArray.convertPrimitiveToObjectArray(expectedValues);

			testValues.setAssignedValues(assignObjectValues);
			testValues.setExpectedValues(expectedObjectValues);
		}
		return testValues;
	}

	protected static TestConfiguration<Byte> createTestValues(ByteTestValue byteTestValue) {
		TestConfiguration<Byte> testValues = new TestConfiguration<Byte>();
		if (byteTestValue != null) {
			byte[] assignValues = byteTestValue.assignValues();
			byte[] expectedValues = byteTestValue.expectedValues();

			Byte[] assignObjectValues = PrimitiveToObjectArray.convertPrimitiveToObjectArray(assignValues);
			Byte[] expectedObjectValues = PrimitiveToObjectArray.convertPrimitiveToObjectArray(expectedValues);

			testValues.setAssignedValues(assignObjectValues);
			testValues.setExpectedValues(expectedObjectValues);
		}
		return testValues;
	}

	protected static TestConfiguration<Character> createTestValues(CharTestValue charTestValue) {
		TestConfiguration<Character> testValues = new TestConfiguration<Character>();
		if (charTestValue != null) {
			char[] assignValues = charTestValue.assignValues();
			char[] expectedValues = charTestValue.expectedValues();

			Character[] assignObjectValues = PrimitiveToObjectArray.convertPrimitiveToObjectArray(assignValues);
			Character[] expectedObjectValues = PrimitiveToObjectArray.convertPrimitiveToObjectArray(expectedValues);

			testValues.setAssignedValues(assignObjectValues);
			testValues.setExpectedValues(expectedObjectValues);
		}
		return testValues;
	}

	protected static TestConfiguration<Double> createTestValues(DoubleTestValue doubleTestValue) {
		TestConfiguration<Double> testValues = new TestConfiguration<Double>();
		if (doubleTestValue != null) {
			double[] assignValues = doubleTestValue.assignValues();
			double[] expectedValues = doubleTestValue.expectedValues();

			Double[] assignObjectValues = PrimitiveToObjectArray.convertPrimitiveToObjectArray(assignValues);
			Double[] expectedObjectValues = PrimitiveToObjectArray.convertPrimitiveToObjectArray(expectedValues);

			testValues.setAssignedValues(assignObjectValues);
			testValues.setExpectedValues(expectedObjectValues);
		}
		return testValues;
	}

	protected static TestConfiguration<Float> createTestValues(FloatTestValue floatTestValue) {
		TestConfiguration<Float> testValues = new TestConfiguration<Float>();
		if (floatTestValue != null) {
			float[] assignValues = floatTestValue.assignValues();
			float[] expectedValues = floatTestValue.expectedValues();

			Float[] assignObjectValues = PrimitiveToObjectArray.convertPrimitiveToObjectArray(assignValues);
			Float[] expectedObjectValues = PrimitiveToObjectArray.convertPrimitiveToObjectArray(expectedValues);

			testValues.setAssignedValues(assignObjectValues);
			testValues.setExpectedValues(expectedObjectValues);
		}
		return testValues;
	}

	protected static TestConfiguration<Integer> createTestValues(IntTestValue intTestValue) {
		TestConfiguration<Integer> testValues = new TestConfiguration<Integer>();
		if (intTestValue != null) {
			int[] assignValues = intTestValue.assignValues();
			int[] expectedValues = intTestValue.expectedValues();

			Integer[] assignObjectValues = PrimitiveToObjectArray.convertPrimitiveToObjectArray(assignValues);
			Integer[] expectedObjectValues = PrimitiveToObjectArray.convertPrimitiveToObjectArray(expectedValues);

			testValues.setAssignedValues(assignObjectValues);
			testValues.setExpectedValues(expectedObjectValues);
		}
		return testValues;
	}

	protected static TestConfiguration<Long> createTestValues(LongTestValue longTestValue) {
		TestConfiguration<Long> testValues = new TestConfiguration<Long>();
		if (longTestValue != null) {
			long[] assignValues = longTestValue.assignValues();
			long[] expectedValues = longTestValue.expectedValues();

			Long[] assignObjectValues = PrimitiveToObjectArray.convertPrimitiveToObjectArray(assignValues);
			Long[] expectedObjectValues = PrimitiveToObjectArray.convertPrimitiveToObjectArray(expectedValues);

			testValues.setAssignedValues(assignObjectValues);
			testValues.setExpectedValues(expectedObjectValues);
		}
		return testValues;
	}

	protected static TestConfiguration<Short> createTestValues(ShortTestValue shortTestValue) {
		TestConfiguration<Short> testValues = new TestConfiguration<Short>();
		if (shortTestValue != null) {
			short[] assignValues = shortTestValue.assignValues();
			short[] expectedValues = shortTestValue.expectedValues();

			Short[] assignObjectValues = PrimitiveToObjectArray.convertPrimitiveToObjectArray(assignValues);
			Short[] expectedObjectValues = PrimitiveToObjectArray.convertPrimitiveToObjectArray(expectedValues);

			testValues.setAssignedValues(assignObjectValues);
			testValues.setExpectedValues(expectedObjectValues);
		}
		return testValues;
	}

}


