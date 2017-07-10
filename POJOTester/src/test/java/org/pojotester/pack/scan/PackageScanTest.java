package org.pojotester.pack.scan;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Set;

import org.junit.Test;

public class PackageScanTest {

	@Test
	public void testDetermineRootDir() throws Exception {
		Set<Class<?>> classSet = PackageScan.getClasses(packagesToScan);
		assertNotNull(classSet);
		assertFalse(classSet.isEmpty());
	}

}
