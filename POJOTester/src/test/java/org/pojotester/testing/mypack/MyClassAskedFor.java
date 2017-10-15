package org.pojotester.testing.mypack;

import org.pojotester.annotation.clazz.TestThisClass;

@TestThisClass
public class MyClassAskedFor {

	private int num;
	private String[] str;
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String[] getStr() {
		return str;
	}
	public void setStr(String[] str) {
		this.str = str;
	}
}
