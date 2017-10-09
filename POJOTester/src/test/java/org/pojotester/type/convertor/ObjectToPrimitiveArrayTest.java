package org.pojotester.type.convertor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ObjectToPrimitiveArrayTest {

	@Test
	public void testConvertObjectToPrimitiveArrayBooleanArray()  {
		Boolean objects[] = {Boolean.TRUE};
		boolean result[] = ObjectToPrimitiveArray.convertObjectToPrimitiveArray(objects);
		assertEquals(objects[0] , result[0]);
	}

	@Test
	public void testConvertObjectToPrimitiveArrayByteArray()  {
		Byte objects[] = {Byte.MIN_VALUE};
		byte result[] = ObjectToPrimitiveArray.convertObjectToPrimitiveArray(objects);
		assertEquals(objects[0] , Byte.valueOf(result[0]));
	}

	@Test
	public void testConvertObjectToPrimitiveArrayCharacterArray()  {
		Character objects[] = {'A'};
		char result[] = ObjectToPrimitiveArray.convertObjectToPrimitiveArray(objects);
		assertEquals(objects[0] , Character.valueOf(result[0]));
	}

	@Test
	public void testConvertObjectToPrimitiveArrayDoubleArray()  {
		Double objects[] = {Double.MAX_VALUE};
		double result[] = ObjectToPrimitiveArray.convertObjectToPrimitiveArray(objects);
		assertEquals(objects[0] , Double.valueOf(result[0]));
	}

	@Test
	public void testConvertObjectToPrimitiveArrayFloatArray()  {
		Float objects[] = {Float.MIN_VALUE};
		float result[] = ObjectToPrimitiveArray.convertObjectToPrimitiveArray(objects);
		assertEquals(objects[0] , Float.valueOf(result[0]));
	}

	@Test
	public void testConvertObjectToPrimitiveArrayIntegerArray()  {
		Integer objects[] = {Integer.MIN_VALUE};
		int result[] = ObjectToPrimitiveArray.convertObjectToPrimitiveArray(objects);
		assertEquals(objects[0] , Integer.valueOf(result[0]));
	}

	@Test
	public void testConvertObjectToPrimitiveArrayLongArray()  {
		Long objects[] = {Long.MIN_VALUE};
		long result[] = ObjectToPrimitiveArray.convertObjectToPrimitiveArray(objects);
		assertEquals(objects[0] , Long.valueOf(result[0]));
	}

	@Test
	public void testConvertObjectToPrimitiveArrayShortArray()  {
		Short objects[] = {Short.MIN_VALUE}; 
		short result[] = ObjectToPrimitiveArray.convertObjectToPrimitiveArray(objects);
		assertEquals(objects[0] , Short.valueOf(result[0]));
	}

}
