package org.pojotester.type.convertor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PrimitiveToObjectArrayTest {

	@Test
	public void testConvertPrimitiveToObjectArrayBooleanArray()  {
		 boolean primitives[] = {true};
		 Boolean result[] = PrimitiveToObjectArray.convertPrimitiveToObjectArray(primitives);
		assertEquals(primitives[0] , result[0]);
	}

	@Test
	public void testConvertPrimitiveToObjectArrayByteArray()  {
		byte primitives[] = {Byte.MIN_VALUE};
		Byte result[] = PrimitiveToObjectArray.convertPrimitiveToObjectArray(primitives);
		assertEquals(Byte.valueOf(primitives[0]),result[0]);
	}

	@Test
	public void testConvertPrimitiveToObjectArrayCharacterArray()  {
		char primitives[] = {'A'};
		Character result[] = PrimitiveToObjectArray.convertPrimitiveToObjectArray(primitives);
		assertEquals(Character.valueOf(primitives[0]), result[0]);
	}

	@Test
	public void testConvertPrimitiveToObjectArrayDoubleArray()  {
		double primitives[] = {Double.MAX_VALUE};
		Double result[] = PrimitiveToObjectArray.convertPrimitiveToObjectArray(primitives);
		assertEquals(Double.valueOf(primitives[0]), result[0]);
	}

	@Test
	public void testConvertPrimitiveToObjectArrayFloatArray()  {
		float primitives[] = {Float.MIN_VALUE};
		Float result[] = PrimitiveToObjectArray.convertPrimitiveToObjectArray(primitives);
		assertEquals(Float.valueOf(primitives[0]), result[0]);
	}

	@Test
	public void testConvertPrimitiveToObjectArrayIntegerArray()  {
		int primitives[] = {Integer.MIN_VALUE};
		Integer result[] = PrimitiveToObjectArray.convertPrimitiveToObjectArray(primitives);
		assertEquals(Integer.valueOf(primitives[0]), result[0]);
	}

	@Test
	public void testConvertPrimitiveToObjectArrayLongArray()  {
		long primitives[] = {Long.MIN_VALUE};
		Long result[] = PrimitiveToObjectArray.convertPrimitiveToObjectArray(primitives);
		assertEquals(Long.valueOf(primitives[0]), result[0]);
	}

	@Test
	public void testConvertPrimitiveToObjectArrayShortArray()  {
		short primitives[] = {(short) 1};
		Short result[] = PrimitiveToObjectArray.convertPrimitiveToObjectArray(primitives);
		assertEquals(Short.valueOf(primitives[0]), result[0]);
	}

}
