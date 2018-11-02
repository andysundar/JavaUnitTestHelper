package org.pojotester.test.values.changer;

public final class DefaultFieldValueChanger  {

	private static final FieldValueChanger INSTANCE = new EnumValueChanger();
	
	private DefaultFieldValueChanger() {
		
	}
	
	public static FieldValueChanger getInstance(){
		return INSTANCE;
	}

}
