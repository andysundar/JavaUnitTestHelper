package org.pojotester.test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.pojotester.test.values.AssertObject;

import static org.junit.Assert.assertEquals;

public class AssertObjectCreatorTest {

	private AssertObjectCreator assertObjectCreator;
	
	@Before 
	public void setup(){
		assertObjectCreator = new AssertObjectCreator();
	}
	@Test
	public void testGetAssertObjects1() {
		String []packagesToScan = {"org.*tester.pack.**.mypack"};
		List<AssertObject<?>> assertObjects = assertObjectCreator.getAssertObjects(packagesToScan);
		for(AssertObject<?> assertObject : assertObjects) {
			assertEquals(assertObject.getExpectedValue(), assertObject.getReturnedValue());
		}
	}
	
	@Test
	public void testGetAssertObjects_WhenAnotherClassInjected() {
		String []packagesToScan = {"org.pojotester.pack.scan.mypack.dto.Test01.class"};
		List<AssertObject<?>> assertObjects = assertObjectCreator.getAssertObjects(packagesToScan);
		for(AssertObject<?> assertObject : assertObjects) {
			assertEquals(assertObject.getExpectedValue(), assertObject.getReturnedValue());
		}
	}
	
	@Test
	public void testGetAssertObjects_WhenCreateObjectMethod() {
		String []packagesToScan = {"org.pojotester.testing.mypack.dto.Test03.class"};
		List<AssertObject<?>> assertObjects = assertObjectCreator.getAssertObjects(packagesToScan);
		for(AssertObject<?> assertObject : assertObjects) {
			assertEquals(assertObject.getExpectedValue(), assertObject.getReturnedValue());
		}
	}
	
	@Test
	public void testGetAssertObjects_WhenEnumArgument() {
		String []packagesToScan = {"org.pojotester.testing.mypack.dto.Test04.class"};
		List<AssertObject<?>> assertObjects = assertObjectCreator.getAssertObjects(packagesToScan);
		for(AssertObject<?> assertObject : assertObjects) {
			assertEquals(assertObject.getExpectedValue(), assertObject.getReturnedValue());
		}
	}
	
	@Test
	public void testGetAssertObjects_WhenArrayArgument() {
		String []packagesToScan = {"org.pojotester.pack.scan.mypack.dto.Test05.class"};
		List<AssertObject<?>> assertObjects = assertObjectCreator.getAssertObjects(packagesToScan);
		for(AssertObject<?> assertObject : assertObjects) {
			assertEquals(assertObject.getExpectedValue(), assertObject.getReturnedValue());
		}
	}
	
	@Test
	public void testGetAssertObjects_WhenInterfaceArgument() {
		String []packagesToScan = {"org.pojotester.pack.scan.mypack.dto.Test06.class"};
		List<AssertObject<?>> assertObjects = assertObjectCreator.getAssertObjects(packagesToScan);
		for(AssertObject<?> assertObject : assertObjects) {
			assertEquals(assertObject.getExpectedValue(), assertObject.getReturnedValue());
		}
	}
}
