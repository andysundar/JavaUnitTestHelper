package org.pojotester.testing.pack.dto;

import org.pojotester.annotation.method.ReadMethod;
import org.pojotester.annotation.method.WriteMethod;
import org.pojotester.testing.mypack.Test;

public class TestReadWriteMethod {

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
	
	@ReadMethod(fieldName="bool")
	public boolean isBool() {
		return bool;
	}
	@WriteMethod(fieldName="bool")
	public void sBool(boolean bool) {
		this.bool = bool;
	}
	
	@ReadMethod(fieldName="byts")
	public byte gByts() {
		return byts;
	}
	@WriteMethod(fieldName="byts")
	public void sByts(byte byts) {
		this.byts = byts;
	}
	
	@ReadMethod(fieldName="charater")
	public char gCharater() {
		return charater;
	}
	@WriteMethod(fieldName="charater")
	public void sCharater(char charater) {
		this.charater = charater;
	}
	@ReadMethod(fieldName="doubleNum")
	public double gDoubleNum() {
		return doubleNum;
	}
	@WriteMethod(fieldName="doubleNum")
	public void sDoubleNum(double doubleNum) {
		this.doubleNum = doubleNum;
	}
	@ReadMethod(fieldName="floatNum")
	public float gFloatNum() {
		return floatNum;
	}
	@WriteMethod(fieldName="intNum")
	public void sFloatNum(float floatNum) {
		this.floatNum = floatNum;
	}
	@ReadMethod(fieldName="intNum")
	public int gIntNum() {
		return intNum;
	}
	@WriteMethod(fieldName="")
	public void sIntNum(int intNum) {
		this.intNum = intNum;
	}
	@ReadMethod(fieldName="longNum")
	public long gLongNum() {
		return longNum;
	}
	@WriteMethod(fieldName="longNum")
	public void sLongNum(long longNum) {
		this.longNum = longNum;
	}
	
	@ReadMethod(fieldName="shortNum")
	public short gShortNum() {
		return shortNum;
	}
	@WriteMethod(fieldName="shortNum")
	public void sShortNum(short shortNum) {
		this.shortNum = shortNum;
	}
	@ReadMethod(fieldName="bean")
	public TestPrimitiveBean gBean() {
		return bean;
	}
	@WriteMethod(fieldName="bean")
	public void sBean(TestPrimitiveBean bean) {
		this.bean = bean;
	}
	@ReadMethod(fieldName="test")
	public Test gTest() {
		return test;
	}
	@WriteMethod(fieldName="test")
	public void sTest(Test test) {
		this.test = test;
	}
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
	@ReadMethod(fieldName="intNums")
	public int[] getIntNums() {
		return intNums;
	}
	public void setIntNums(int[] intNums) {
		this.intNums = intNums;
	}
	
	public long[] getLongNums() {
		return longNums;
	}
	@WriteMethod(fieldName="longNums")
	public void sLongNums(long[] longNums) {
		this.longNums = longNums;
	}
	public short[] getShortNums() {
		return shortNums;
	}
	public void setShortNums(short[] shortNums) {
		this.shortNums = shortNums;
	}
	public TestPrimitiveBean[] gBeans() {
		return beans;
	}
	@WriteMethod(fieldName="beans")
	public void setBeans(TestPrimitiveBean[] beans) {
		this.beans = beans;
	}
	@ReadMethod(fieldName="tests")
	public Test[] getTests() {
		return tests;
	}
	public void sTests(Test[] tests) {
		this.tests = tests;
	}
	
	
}
