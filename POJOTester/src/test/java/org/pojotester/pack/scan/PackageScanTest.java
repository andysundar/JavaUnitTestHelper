package org.pojotester.pack.scan;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class PackageScanTest {
	
	private PackageScan  packageScan;
	
	@Before
	public void setUp(){
		packageScan = new PackageScan();
	}
	
	@Test
	public void testDetermineRootDir() throws Exception { 
		String []packagesToScan = {
									"org.pojotester.pack.scan1.mypack1.MyClass04.class",
									"org.pojotester.pack.**.mypack.MyClass0*.class", 
									"org.pojotester.pack.scan.mypack.dto"
									};
		Set<Class<?>> classSet = packageScan.getClasses(packagesToScan);
		assertNotNull(classSet);
		assertFalse(classSet.isEmpty());
	}

}
