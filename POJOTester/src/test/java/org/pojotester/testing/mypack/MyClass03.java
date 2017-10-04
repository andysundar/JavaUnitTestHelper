package org.pojotester.testing.mypack;

import java.util.List;

import org.pojotester.annotation.clazz.IgnoreClass;

@IgnoreClass
public class MyClass03 {

	private int num1;


	
	public MyClass03(List<Integer> nums){
		this.num1 = nums.size();
	}
	public int getNum1() {
		return num1;
	}

	public void setNum1(int num1) {
		this.num1 = num1;
	}
	
	
}
