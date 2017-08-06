package org.pojotester.pack.scan;

import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class PackageScanTest {
	
	private final static String CLASS_WITH_DOT = ".class"; 
	private PackageScan  packageScan;
	
	@Rule
	public ExpectedException throwE = ExpectedException.none();
	
	@Before
	public void setUp(){
		packageScan = new PackageScan();
	}
	
	@Test
	public void testDetermineRootDir_WhenClassNameIsWildcard() { 
		String []packagesToScan = { "org.pojotester.pack.scan*.mypack1.*.*" };
		Set<Class<?>> classSet = packageScan.getClasses(packagesToScan);
		assertNotNull(classSet);
		assertFalse(classSet.isEmpty());
		for(Class<?> clazz : classSet){
			String className = clazz.getName();
			assertTrue(className.contains("org.pojotester.pack.scan1.mypack1"));
		}
	}
	
	@Test
	public void testDetermineRootDir_WhenClassNameOrPatternNotGiven() { 
		String []packagesToScan = { "org.pojotester.pack.scan.mypack.dto"	};
		Set<Class<?>> classSet = packageScan.getClasses(packagesToScan);
		assertNotNull(classSet);
		assertFalse(classSet.isEmpty());
		for(Class<?> clazz : classSet){
			String className = clazz.getName();
			assertTrue(className.contains(packagesToScan[0]));
		}
	}
	
	@Test
	public void testDetermineRootDir_WhenPackageAndClassNameContainsWildchar() { 
		String []packagesToScan = { "org.*tester.pack.**.mypack.MyClass0*.class"	};
		Set<Class<?>> classSet = packageScan.getClasses(packagesToScan);
		assertNotNull(classSet);
		assertFalse(classSet.isEmpty());
		for(Class<?> clazz : classSet){
			String className = clazz.getName();
			assertTrue(className.contains("mypack.MyClass0"));
		}
	}
	
	@Test
	public void testDetermineRootDir_WhenClassNameContainsWildchar() { 
		String []packagesToScan = { "org.pojotester.pack.scan.mypack.*Class01.class"	};
		Set<Class<?>> classSet = packageScan.getClasses(packagesToScan);
		assertNotNull(classSet);
		assertFalse(classSet.isEmpty());
		for(Class<?> clazz : classSet){
			String className = clazz.getName();
			assertTrue(className.contains("org.pojotester.pack.scan.mypack.MyClass01"));
		}
	}
	@Test
	public void testDetermineRootDir_WhenFullQualifiedClassName() { 
		String []packagesToScan = { "org.pojotester.pack.scan1.mypack1.MyClass04.class" };
		Set<Class<?>> classSet = packageScan.getClasses(packagesToScan);
		assertNotNull(classSet);
		assertFalse(classSet.isEmpty());
		String className = packagesToScan[0].substring(0, (packagesToScan[0].length() - CLASS_WITH_DOT.length()));
		for(Class<?> clazz : classSet){
			assertEquals(className, clazz.getName());
		}
	}

	
	@Test
	public void testDetermineRootDir_WhenOnlyWildcard() { 
		String []packagesToScan = { "**" };
		throwE.expect(IllegalArgumentException.class);
		packageScan.getClasses(packagesToScan);
	}
	
	@Test
	public void testDetermineRootDir_WhenClassNotfound() { 
		String []packagesToScan = { "org.pojotester.pack.scan.mypack.dto.MyClass.class"	};
		Set<Class<?>> classSet = packageScan.getClasses(packagesToScan);
		assertNotNull(classSet);
		assertTrue(classSet.isEmpty());
		
	}
	
	@Test
	public void testDetermineRootDir_WhenClassMarkedIgnoredClass() { 
		String []packagesToScan = { "org.pojotester.pack.scan.mypack.MyClass03.class"	};
		Set<Class<?>> classSet = packageScan.getClasses(packagesToScan);
		assertNotNull(classSet);
		assertTrue(classSet.isEmpty());
	}

}
