package org.pojotester.pack.scan;

import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class PackageScanTest {
	
	private PackageScan  packageScan;
	
	@Rule
	public ExpectedException throwE = ExpectedException.none();
	
	@Before
	public void setUp(){
		packageScan = new PackageScan();
	}
	
	@Test
	public void testDetermineRootDir() { 
		String []packagesToScan = {
									"org.pojotester.pack.scan1.mypack1.MyClass04.class",
									"org.*tester.pack.**.mypack.MyClass0*.class", 
									"org.pojotester.pack.scan.mypack.dto",
									"org.pojotester.pack.scan1.mypack1.*.*"
									};
		Set<Class<?>> classSet = packageScan.getClasses(packagesToScan);
		assertNotNull(classSet);
		assertFalse(classSet.isEmpty());
	}

	
	@Test
	public void testDetermineRootDir_WhenThrowIllegalArgException() { 
		String []packagesToScan = { "**" };
		throwE.expect(IllegalArgumentException.class);
		packageScan.getClasses(packagesToScan);
	}

}
