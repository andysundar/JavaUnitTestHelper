package org.pojotester.testing.pack.dto;

import org.pojotester.testing.mypack.Test;

public class TestPrimitiveArrayNoGetter {

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
	public void setBools(boolean[] bools) {
		this.bools = bools;
	}
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	public void setCharaters(char[] charaters) {
		this.charaters = charaters;
	}
	public void setDoubleNums(double[] doubleNums) {
		this.doubleNums = doubleNums;
	}
	public void setFloatNums(float[] floatNums) {
		this.floatNums = floatNums;
	}
	public void setIntNums(int[] intNums) {
		this.intNums = intNums;
	}
	public void setLongNums(long[] longNums) {
		this.longNums = longNums;
	}
	public void setShortNums(short[] shortNums) {
		this.shortNums = shortNums;
	}
	public void setBeans(TestPrimitiveBean[] beans) {
		this.beans = beans;
	}
	public void setTests(Test[] tests) {
		this.tests = tests;
	}
	
	
}
