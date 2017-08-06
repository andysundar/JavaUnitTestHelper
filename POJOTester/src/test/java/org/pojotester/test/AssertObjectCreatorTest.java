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
	public void testGetAssertObjects() {
		String []packagesToScan = {"org.*tester.pack.**.mypack"};
		List<AssertObject> assertObjects = assertObjectCreator.getAssertObjects(packagesToScan);
		for(AssertObject assertObject : assertObjects) {
			assertEquals(assertObject.getExpectedValue(), assertObject.getReturnedValue());
		}
	}
}
