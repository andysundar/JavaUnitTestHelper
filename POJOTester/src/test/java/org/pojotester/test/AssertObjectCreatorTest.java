package org.pojotester.test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.pojotester.test.values.AssertObject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AssertObjectCreatorTest {

	private AssertObjectCreator assertObjectCreator;
	
	@Before 
	public void setup(){
		assertObjectCreator = new AssertObjectCreator();
	}
	@Test
	public void testGetAssertObjects_WhenMultiPackage() {
		String []packagesToScan = {"org.*tester.pack.**.mypack","org.pojotester.testing.mypack.dto"};
		List<AssertObject<?>> assertObjects = assertObjectCreator.getAssertObjects(packagesToScan);
		for(AssertObject<?> assertObject : assertObjects) {
			assertEquals(assertObject.getClassFieldName(),assertObject.getExpectedValue(), assertObject.getReturnedValue());
		}
	}
	
	@Test
	public void testGetAssertObjects_WhenAnotherClassInjected() {
		String []packagesToScan = {"org.pojotester.testing.mypack.dto.Test01.class"};
		List<AssertObject<?>> assertObjects = assertObjectCreator.getAssertObjects(packagesToScan);
		for(AssertObject<?> assertObject : assertObjects) {
			assertEquals(assertObject.getClassFieldName(),assertObject.getExpectedValue(), assertObject.getReturnedValue());
		}
	}
	
	@Test
	public void testGetAssertObjects_WhenCreateObjectMethod() {
		String []packagesToScan = {"org.pojotester.testing.mypack.dto.Test03.class"};
		List<AssertObject<?>> assertObjects = assertObjectCreator.getAssertObjects(packagesToScan);
		for(AssertObject<?> assertObject : assertObjects) {
			assertEquals(assertObject.getClassFieldName(),assertObject.getExpectedValue(), assertObject.getReturnedValue());
		}
	}
	
	@Test
	public void testGetAssertObjects_WhenEnumArgument() {
		String []packagesToScan = {"org.pojotester.testing.mypack.dto.Test04.class"};
		List<AssertObject<?>> assertObjects = assertObjectCreator.getAssertObjects(packagesToScan);
		for(AssertObject<?> assertObject : assertObjects) {
			assertEquals(assertObject.getClassFieldName(),assertObject.getExpectedValue(), assertObject.getReturnedValue());
		}
	}
	
	@Test
	public void testGetAssertObjects_WhenObjectCreationFail() {
		String []packagesToScan = {"org.pojotester.testing.mypack.another.MyClass04.class"};
		List<AssertObject<?>> assertObjects = assertObjectCreator.getAssertObjects(packagesToScan);
		assertTrue(assertObjects.isEmpty());
	}
	
	@Test
	public void testGetAssertObjects_WhenObjectCreationMethodFail() {
		String []packagesToScan = {"org.pojotester.testing.mypack.another.MyClass05.class"};
		List<AssertObject<?>> assertObjects = assertObjectCreator.getAssertObjects(packagesToScan);
		assertTrue(assertObjects.isEmpty());
	}
	
	@Test
	public void testGetAssertObjects_WhenArrayArgument() {
		String []packagesToScan = {"org.pojotester.testing.mypack.dto.Test05.class"};
		List<AssertObject<?>> assertObjects = assertObjectCreator.getAssertObjects(packagesToScan);
		for(AssertObject<?> assertObject : assertObjects) {
			assertEquals(assertObject.getClassFieldName(), assertObject.getExpectedValue(), assertObject.getReturnedValue());
		}
	}
	
	@Test
	public void testGetAssertObjects_WhenInterfaceArgument() {
		String []packagesToScan = {"org.pojotester.testing.mypack.dto.Test06.class"};
		List<AssertObject<?>> assertObjects = assertObjectCreator.getAssertObjects(packagesToScan);
		for(AssertObject<?> assertObject : assertObjects) {
			assertEquals(assertObject.getClassFieldName(), assertObject.getExpectedValue(), assertObject.getReturnedValue());
		}
	}
	
	@Test
	public void testGetAssertObjects_WhenArray() {
		String []packagesToScan = {"org.pojotester.testing.mypack.MyClass01.class"};
		List<AssertObject<?>> assertObjects = assertObjectCreator.getAssertObjects(packagesToScan);
		for(AssertObject<?> assertObject : assertObjects) {
			assertEquals(assertObject.getClassFieldName(), assertObject.getExpectedValue(), assertObject.getReturnedValue());
		}
	}
}
