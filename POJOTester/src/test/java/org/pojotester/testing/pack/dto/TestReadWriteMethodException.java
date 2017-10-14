package org.pojotester.testing.pack.dto;

import org.pojotester.annotation.method.ReadMethod;
import org.pojotester.annotation.method.WriteMethod;

public class TestReadWriteMethodException {

	private Boolean bool;
	private Byte byts;
	private Character charater;
	private Double doubleNum;
	private Float floatNum;
	private Integer intNum;
	private Long longNum;
	private Short shortNum;
	
	private Boolean[] bools;
	private Byte[] bytes;
	private Character[] charaters;
	private Double[] doubleNums;
	private Float[] floatNums;
	private Integer[] intNums;
	private Long[] longNums;
	private Short[] shortNums;
	
	@WriteMethod(fieldName="bool")
	public Boolean gBool() {
		return bool;
	}
	@ReadMethod(fieldName="bool")
	public void sBool(Boolean bool) {
		this.bool = bool;
	}
	@ReadMethod(fieldName="byt")
	public Byte gByts() {
		return byts;
	}
	public void sByts(Byte byts) {
		this.byts = byts;
	}
	@ReadMethod(fieldName="bool")
	public Character gCharater() {
		return charater;
	}
	public void sCharater(Character charater) {
		this.charater = charater;
	}
	public Double gDoubleNum() {
		return doubleNum;
	}
	public void sDoubleNum(Double doubleNum) {
		this.doubleNum = doubleNum;
	}
	public Float gFloatNum() {
		return floatNum;
	}
	public void sFloatNum(Float floatNum) {
		this.floatNum = floatNum;
	}
	public Integer gIntNum() {
		return intNum;
	}
	public void sIntNum(Integer intNum) {
		this.intNum = intNum;
	}
	public Long gLongNum() {
		return longNum;
	}
	public void sLongNum(Long longNum) {
		this.longNum = longNum;
	}
	public Short gShortNum() {
		return shortNum;
	}
	public void sShortNum(Short shortNum) {
		this.shortNum = shortNum;
	}
	public Boolean[] gBools() {
		return bools;
	}
	public void sBools(Boolean[] bools) {
		this.bools = bools;
	}
	public Byte[] gBytes() {
		return bytes;
	}
	public void sBytes(Byte[] bytes) {
		this.bytes = bytes;
	}
	public Character[] gCharaters() {
		return charaters;
	}
	public void sCharaters(Character[] charaters) {
		this.charaters = charaters;
	}
	public Double[] gDoubleNums() {
		return doubleNums;
	}
	public void sDoubleNums(Double[] doubleNums) {
		this.doubleNums = doubleNums;
	}
	public Float[] gFloatNums() {
		return floatNums;
	}
	public void sFloatNums(Float[] floatNums) {
		this.floatNums = floatNums;
	}
	public Integer[] gIntNums() {
		return intNums;
	}
	public void sIntNums(Integer[] intNums) {
		this.intNums = intNums;
	}
	public Long[] gLongNums() {
		return longNums;
	}
	public void sLongNums(Long[] longNums) {
		this.longNums = longNums;
	}
	public Short[] gShortNums() {
		return shortNums;
	}
	public void sShortNums(Short[] shortNums) {
		this.shortNums = shortNums;
	}
	
	
}
