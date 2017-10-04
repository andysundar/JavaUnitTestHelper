package org.pojotester.pack.scan.mypack.dto;

import org.pojotester.pack.scan.mypack.Test;

public class Test04 {

	private Test test;
	private int num;
	
	public Test04(Test test, int num){
		this.setTest(test);
		this.setNum(num);
	}

	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	
}
