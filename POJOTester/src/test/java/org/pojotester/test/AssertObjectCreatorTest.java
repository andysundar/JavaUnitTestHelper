package org.pojotester.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.pojotester.test.values.AssertObject;

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
		assertFalse(assertObjects.isEmpty());
		for(AssertObject<?> assertObject : assertObjects) {
			assertEquals(assertObject.getMessage(),assertObject.getExpectedValue(), assertObject.getReturnedValue());
		}
	}
	
	@Test
	public void testGetAssertObjects_WhenAnotherClassInjected() {
		String []packagesToScan = {"org.pojotester.testing.mypack.dto.Test01.class"};
		List<AssertObject<?>> assertObjects = assertObjectCreator.getAssertObjects(packagesToScan);
		assertFalse(assertObjects.isEmpty());
		for(AssertObject<?> assertObject : assertObjects) {
			assertEquals(assertObject.getMessage(),assertObject.getExpectedValue(), assertObject.getReturnedValue());
		}
	}
	
	@Test
	public void testGetAssertObjects_WhenCreateObjectMethod() {
		String []packagesToScan = {"org.pojotester.testing.mypack.dto.Test03.class"};
		List<AssertObject<?>> assertObjects = assertObjectCreator.getAssertObjects(packagesToScan);
		assertFalse(assertObjects.isEmpty());
		for(AssertObject<?> assertObject : assertObjects) {
			assertEquals(assertObject.getMessage(),assertObject.getExpectedValue(), assertObject.getReturnedValue());
		}
	}
	
	@Test
	public void testGetAssertObjects_WhenEnumArgument() {
		String []packagesToScan = {"org.pojotester.testing.mypack.dto.Test04.class"};
		List<AssertObject<?>> assertObjects = assertObjectCreator.getAssertObjects(packagesToScan);
		assertFalse(assertObjects.isEmpty());
		for(AssertObject<?> assertObject : assertObjects) {
			assertEquals(assertObject.getMessage(),assertObject.getExpectedValue(), assertObject.getReturnedValue());
		}
	}
	
	@Test
	public void testGetAssertObjects_WhenObjectCreationFail() {
		String []packagesToScan = {"org.pojotester.testing.mypack.another.MyClass04.class"};
		List<AssertObject<?>> assertObjects = assertObjectCreator.getAssertObjects(packagesToScan);
		System.out.println(assertObjects.toString());
		assertTrue(assertObjects.isEmpty());
	}
	
	@Test
	public void testGetAssertObjects_WhenObjectCreationMethodFail() {
		String []packagesToScan = {"org.pojotester.testing.mypack.another.MyClass05.class"};
		List<AssertObject<?>> assertObjects = assertObjectCreator.getAssertObjects(packagesToScan);
		System.out.println(assertObjects.toString());
		assertTrue(assertObjects.isEmpty());
	}
	
	@Test
	public void testGetAssertObjects_WhenArrayArgument() {
		String []packagesToScan = {"org.pojotester.testing.mypack.dto.Test05.class"};
		List<AssertObject<?>> assertObjects = assertObjectCreator.getAssertObjects(packagesToScan);
		assertFalse(assertObjects.isEmpty());
		for(AssertObject<?> assertObject : assertObjects) {
			assertEquals(assertObject.getMessage(), assertObject.getExpectedValue(), assertObject.getReturnedValue());
		}
	}
	
	@Test
	public void testGetAssertObjects_WhenInterfaceArgument() {
		String []packagesToScan = {"org.pojotester.testing.mypack.dto.Test06.class"};
		List<AssertObject<?>> assertObjects = assertObjectCreator.getAssertObjects(packagesToScan);
		assertFalse(assertObjects.isEmpty());
		for(AssertObject<?> assertObject : assertObjects) {
			assertEquals(assertObject.getMessage(), assertObject.getExpectedValue(), assertObject.getReturnedValue());
		}
	}
	
	@Test
	public void testGetAssertObjects_WhenArray() {
		String []packagesToScan = {"org.pojotester.testing.mypack.MyClass01.class"};
		List<AssertObject<?>> assertObjects = assertObjectCreator.getAssertObjects(packagesToScan);
		assertFalse(assertObjects.isEmpty());
		for(AssertObject<?> assertObject : assertObjects) {
			assertEquals(assertObject.getMessage(), assertObject.getExpectedValue(), assertObject.getReturnedValue());
		}
	}
	
	
	@Test
	public void testGetAssertObjects_WhenMethodLevelAnnotationUsed() {
		String []packagesToScan = {"org.pojotester.testing.pack.dto"};
		List<AssertObject<?>> assertObjects = assertObjectCreator.getAssertObjects(packagesToScan);
		assertFalse(assertObjects.isEmpty());
		for(AssertObject<?> assertObject : assertObjects) {
			assertEquals(assertObject.getMessage(), assertObject.getExpectedValue(), assertObject.getReturnedValue());
		}
	}
	
	
	@Test
	public void testGetAssertObjects_WhenQuestionPattern() {
		String []packagesToScan = {"org.pojotester.testing.pack.d?o", "org.pojo?ester.testing.mypack.dto"};
		List<AssertObject<?>> assertObjects = assertObjectCreator.getAssertObjects(packagesToScan);
		assertFalse(assertObjects.isEmpty());
		for(AssertObject<?> assertObject : assertObjects) {
			assertEquals(assertObject.getMessage(), assertObject.getExpectedValue(), assertObject.getReturnedValue());
		}
	}
	
	@Test
	public void testGetAssertObjects_WhenDefineClassNotFound() {
		String []packagesToScan = {"org.pojoTester.testing.mypack.dto.MyAbc.class"};
		List<AssertObject<?>> assertObjects = assertObjectCreator.getAssertObjects(packagesToScan);
		assertTrue(assertObjects.isEmpty());
	}
	
	@Test
	public void testGetAssertObjects_WhenDefineToStringMethod() {
		String []packagesToScan = {"org.pojotester.testing.mypack.MyClass02.class"};
		List<AssertObject<?>> assertObjects = assertObjectCreator.getAssertObjects(packagesToScan);
		assertFalse(assertObjects.isEmpty());
		for(AssertObject<?> assertObject : assertObjects) {
			assertEquals(assertObject.getMessage(), assertObject.getExpectedValue(), assertObject.getReturnedValue());
		}
	}

	@Test 
	public void testDoTestAllConstructors(){
		String []packagesToScan = {"org.pojotester.testing.mypack.dto.Test01.class"};
		assertObjectCreator.doTestAllConstructors();
		List<AssertObject<?>> assertObjects = assertObjectCreator.getAssertObjects(packagesToScan);
		assertFalse(assertObjects.isEmpty());
		for(AssertObject<?> assertObject : assertObjects) {
			assertEquals(assertObject.getMessage(), assertObject.getExpectedValue(), assertObject.getReturnedValue());
		}
	}
	
	
	@Test 
	public void testGetAssertObjects_whenBeanMethodNameDoesNotMatchVariableName(){
		String []packagesToScan = {"org.pojotester.testing.mypack.dto.Test07.class"};
		List<AssertObject<?>> assertObjects = assertObjectCreator.getAssertObjects(packagesToScan);
		assertTrue(assertObjects.isEmpty());
	}
	
	@Test 
	public void testIgnoreMethodForTest(){
		String []packagesToScan = {"org.pojotester.testing.mypack.dto.Test01.class"};
		
		List<AssertObject<?>> assertObjects = assertObjectCreator.getAssertObjects(packagesToScan);
		assertFalse(assertObjects.isEmpty());
		assertTrue(assertObjects.size() < 5);
	}
	
}
