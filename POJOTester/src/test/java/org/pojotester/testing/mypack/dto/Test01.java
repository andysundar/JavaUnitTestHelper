package org.pojotester.testing.mypack.dto;

import java.math.BigDecimal;
import java.util.Arrays;

import org.pojotester.annotation.field.IgnoreField;
import org.pojotester.annotation.field.IntTestValue;
import org.pojotester.annotation.method.IgnoreMethodForTest;
import org.pojotester.annotation.method.ReadMethod;
import org.pojotester.testing.mypack.MyClassWithDefaultConstructor;

public class Test01 {

	@IgnoreField
	@IntTestValue(assignValues=1,expectedValues=1)
	private int num1;
	@IntTestValue(assignValues={1,2}, expectedValues = { 1,2 })
	private int[] nums1;
	private BigDecimal bigDecimal1;
	private boolean flag;
	private char ch;
	private long lg;

	public Test01() {}

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

	@IgnoreMethodForTest
	@Override
	public String toString() {
		return "Test01 [num1=" + num1 + ", nums1=" + Arrays.toString(nums1) + ", bigDecimal1=" + bigDecimal1 + ", flag="
				+ flag + ", ch=" + ch + ", lg=" + lg + "]";
	}

	@IgnoreMethodForTest
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bigDecimal1 == null) ? 0 : bigDecimal1.hashCode());
		result = prime * result + ch;
		result = prime * result + (flag ? 1231 : 1237);
		result = prime * result + (int) (lg ^ (lg >>> 32));
		result = prime * result + num1;
		result = prime * result + Arrays.hashCode(nums1);
		return result;
	}

	@IgnoreMethodForTest
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Test01 other = (Test01) obj;
		if (bigDecimal1 == null) {
			if (other.bigDecimal1 != null)
				return false;
		} else if (!bigDecimal1.equals(other.bigDecimal1))
			return false;
		if (ch != other.ch)
			return false;
		if (flag != other.flag)
			return false;
		if (lg != other.lg)
			return false;
		if (num1 != other.num1)
			return false;
		if (!Arrays.equals(nums1, other.nums1))
			return false;
		return true;
	}
	
	
}
