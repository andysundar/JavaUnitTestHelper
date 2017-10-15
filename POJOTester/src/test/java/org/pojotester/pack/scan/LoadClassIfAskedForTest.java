package org.pojotester.pack.scan;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LoadClassIfAskedForTest {

	private PackageScan packageScan;
	
	@Before
	public void setUp(){
		packageScan = new LoadClassIfAskedFor();
	}
	
	@Test
	public void testLoadOnlyIfAskedFor(){
		Set<Class<?>> classes = packageScan.getClasses("org.pojotester.testing.mypack");
		assertFalse(classes.isEmpty());
		assertEquals(1, classes.size());
		assertTrue(classes.stream().allMatch(clazz -> clazz.getName().contains("MyClassAskedFor")));
	}
}
