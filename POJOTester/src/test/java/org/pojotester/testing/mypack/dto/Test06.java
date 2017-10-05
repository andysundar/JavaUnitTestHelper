package org.pojotester.testing.mypack.dto;

import org.pojotester.testing.mypack.TestInterface;

public class Test06 {

	private TestInterface list;
	
	public Test06(TestInterface list){
		this.setList(list);
	}

	public TestInterface getList() {
		return list;
	}

	public void setList(TestInterface list) {
		this.list = list;
	}
}
