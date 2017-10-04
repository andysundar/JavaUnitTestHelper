package org.pojotester.pack.scan.mypack.dto;

import org.pojotester.annotation.field.BooleanTestValue;
import org.pojotester.annotation.field.StringTestValue;
import org.pojotester.annotation.method.CreateObjectMethod;
import org.pojotester.annotation.method.WriteMethod;

public class Test03 {

	private static Test03 test03 = new Test03();
	private int num1;
	@StringTestValue(assignValues={"Andy", "Andy"},expectedValues = "Andy")
	private String str;
	@BooleanTestValue(assignValues={true},expectedValues = {true,true})
	private boolean flag;
	
	private Test03(){
		
	}
	
	public int getNum1() {
		return num1;
	}
	@WriteMethod(fieldName = "num")
	public void setNum1(int num1) {
		this.num1 = num1;
	}
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
	public boolean isFlag() {
		return flag;
	}
	@WriteMethod(fieldName="flag")
	public void writeFlag(boolean flag) {
		this.flag = flag;
	}
	
	@CreateObjectMethod
	public static Test03 createTest03(){
		return test03;
	}
}
