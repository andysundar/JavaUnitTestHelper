package org.pojotester.testing.mypack.dto;

import java.math.BigDecimal;

import org.pojotester.annotation.field.IgnoreField;
import org.pojotester.annotation.field.IntTestValue;
import org.pojotester.annotation.method.ReadMethod;
import org.pojotester.testing.mypack.MyClassWithDefaultConstructor;

public class Test01 {

	@IgnoreField
	private int num1;
	@IntTestValue(assignValues={1,2}, expectedValues = { 1,2 })
	private int[] nums1;
	private BigDecimal bigDecimal1;
	private boolean flag;
	private char ch;
	private long lg;
	
	public Test01(MyClassWithDefaultConstructor myClass03){
		num1 = myClass03.getNum1();
	}

	public int getNum1() {
		return num1;
	}

	public void setNum1(int num1) {
		this.num1 = num1;
	}

	public int[] getNums1() {
		return nums1;
	}

	public void setNums1(int[] nums1) {
		this.nums1 = nums1;
	}

	public BigDecimal getBigDecimal1() {
		return bigDecimal1;
	}

	public void setBigDecimal1(BigDecimal bigDecimal1) {
		this.bigDecimal1 = bigDecimal1;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	@ReadMethod(fieldName="flag")
	public boolean hasFlag(){
		return flag;
	}
}
