package org.pojotester.testing.pack.dto;

import org.pojotester.testing.mypack.Test;

public class TestPrimitiveArrayBean {

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
	public void setBools(boolean[] bools) {
		this.bools = bools;
	}
	public byte[] getBytes() {
		return bytes;
	}
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	public char[] getCharaters() {
		return charaters;
	}
	public void setCharaters(char[] charaters) {
		this.charaters = charaters;
	}
	public double[] getDoubleNums() {
		return doubleNums;
	}
	public void setDoubleNums(double[] doubleNums) {
		this.doubleNums = doubleNums;
	}
	public float[] getFloatNums() {
		return floatNums;
	}
	public void setFloatNums(float[] floatNums) {
		this.floatNums = floatNums;
	}
	public int[] getIntNums() {
		return intNums;
	}
	public void setIntNums(int[] intNums) {
		this.intNums = intNums;
	}
	public long[] getLongNums() {
		return longNums;
	}
	public void setLongNums(long[] longNums) {
		this.longNums = longNums;
	}
	public short[] getShortNums() {
		return shortNums;
	}
	public void setShortNums(short[] shortNums) {
		this.shortNums = shortNums;
	}
	public TestPrimitiveBean[] getBeans() {
		return beans;
	}
	public void setBeans(TestPrimitiveBean[] beans) {
		this.beans = beans;
	}
	public Test[] getTests() {
		return tests;
	}
	public void setTests(Test[] tests) {
		this.tests = tests;
	}
}
