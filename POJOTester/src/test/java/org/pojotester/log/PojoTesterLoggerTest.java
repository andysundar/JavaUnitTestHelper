package org.pojotester.log;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;

public class PojoTesterLoggerTest {

	private Field modifierField;
	
	@Before
	public void setup(){
		try {
			modifierField = PojoTesterLogger.class.getDeclaredField("logger");
		} catch (NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void setStatic(Field field, Object newValue) throws Exception {
	      field.setAccessible(true);

	      Field modifiersField = Field.class.getDeclaredField("modifiers");
	      modifiersField.setAccessible(true);
	      modifiersField.setInt(field, field.getModifiers());

	      field.set(null, newValue);
	   }
	
	@Test
	public void testDebugMessage_WhenLog4J2() throws Exception {
		setStatic(modifierField,  Log4J2Logger.class.newInstance());
		PojoTesterLogger.debugMessage("Only load log4j2.", null);
	}
	
	@Test
	public void testDebugMessage_WhenLog4J() throws Exception {
		setStatic(modifierField,  Log4JLogger.class.newInstance());
		PojoTesterLogger.debugMessage("Only load log4j.", null);
	}
	
	@Test
	public void testDebugMessage_WhenSLF4J() throws Exception {
		setStatic(modifierField,  SLF4Jlogger.class.newInstance());
		PojoTesterLogger.debugMessage("Only load SLF4J.", null);
	}

	@Test
	public void testDebugMessage_WhenTinylog() throws Exception {
		setStatic(modifierField,  TinylogLogger.class.newInstance());
		PojoTesterLogger.debugMessage("Only load Tinylog.", null);
	}
	
	@Test
	public void testDebugMessage_WhenJavaLogging() throws Exception {
		setStatic(modifierField,  JavaLogger.class.newInstance());
		PojoTesterLogger.debugMessage("Only load Java logging.", null);
	}

	
}
