package org.pojotester.testing.pack.dto;

import org.pojotester.testing.mypack.Test;

public class TestPrimitiveArrayNoSetter {

	private boolean[] bools;
	private byte[] bytes;
	private char[] charaters;
	private double[] doubleNums;
	private float[] floatNums;
	private int[] intNums;
	private long[] longNums;
	private short[] shortNums;
	private TestPrimitiveBean[] beans;
	private Test[] tests;
	
	public boolean[] getBools() {
		return bools;
	}
	public byte[] getBytes() {
		return bytes;
	}
	public char[] getCharaters() {
		return charaters;
	}
	public double[] getDoubleNums() {
		return doubleNums;
	}
	public float[] getFloatNums() {
		return floatNums;
	}
	public int[] getIntNums() {
		return intNums;
	}
	public long[] getLongNums() {
		return longNums;
	}
	public short[] getShortNums() {
		return shortNums;
	}
	public TestPrimitiveBean[] getBeans() {
		return beans;
	}
	public Test[] getTests() {
		return tests;
	}
}
