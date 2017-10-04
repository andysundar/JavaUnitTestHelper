package org.pojotester.testing.mypack.another;

import org.pojotester.annotation.method.CreateObjectMethod;

public class MyClass05 {

	private char ch = 'F';
	
	private MyClass05(){
		
	}
	
	@CreateObjectMethod
	public static MyClass05 createObject(){
		MyClass05 myClass05 = new MyClass05();
		if(myClass05.ch == 'F'){
			throw new IllegalAccessError();
		}
		return myClass05;
	}

	public char getCh() {
		return ch;
	}

	public void setCh(char ch) {
		this.ch = ch;
	}
}
