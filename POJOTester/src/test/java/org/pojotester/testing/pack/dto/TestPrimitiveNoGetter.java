package org.pojotester.testing.pack.dto;

import org.pojotester.testing.mypack.Test;

public class TestPrimitiveNoGetter {

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
	
	public void setBool(boolean bool) {
		this.bool = bool;
	}
	public void setByts(byte byts) {
		this.byts = byts;
	}
	public void setCharater(char charater) {
		this.charater = charater;
	}
	public void setDoubleNum(double doubleNum) {
		this.doubleNum = doubleNum;
	}
	public void setFloatNum(float floatNum) {
		this.floatNum = floatNum;
	}
	public void setIntNum(int intNum) {
		this.intNum = intNum;
	}
	public void setLongNum(long longNum) {
		this.longNum = longNum;
	}
	public void setShortNum(short shortNum) {
		this.shortNum = shortNum;
	}
	public void setBean(TestPrimitiveBean bean) {
		this.bean = bean;
	}
	public void setTest(Test test) {
		this.test = test;
	}
	
	
}
