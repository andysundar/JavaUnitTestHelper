package org.pojotester.testing.pack.dto;

import org.pojotester.testing.mypack.Test;

public class TestPrimitiveNoSetter {

	private boolean bool;
	private byte byts;
	private char charater;
	private double doubleNum;
	private float floatNum;
	private int intNum;
	private long longNum;
	private short shortNum;
	private TestPrimitiveBean bean;
	private Test test;
	
	public boolean isBool() {
		return bool;
	}
	public byte getByts() {
		return byts;
	}
	public char getCharater() {
		return charater;
	}
	public double getDoubleNum() {
		return doubleNum;
	}
	public float getFloatNum() {
		return floatNum;
	}
	public int getIntNum() {
		return intNum;
	}
	public long getLongNum() {
		return longNum;
	}
	public short getShortNum() {
		return shortNum;
	}
	public TestPrimitiveBean getBean() {
		return bean;
	}
	public Test getTest() {
		return test;
	}
}
