package org.pojotester.testing.mypack.another;

public class MyClass04 {

	private int num;
	
	public MyClass04(int num){
		throw new IllegalAccessError();
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
}
