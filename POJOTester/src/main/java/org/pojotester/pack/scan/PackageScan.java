package org.pojotester.pack.scan;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.pojotester.utils.ClassUtilities;

public final class PackageScan {
	
	private static final char PATH_SEPARATOR_CHAR = File.separatorChar;
	private static final char WILDCARD_CHAR = '*';
	private static final String PATH_SEPARATOR = File.separator;
	private static final String PACKAGE_SEPERATOR = ".";
	private static final String CLASS_FILE_SUFFIX = ".class";
	
	public static Set<Class<?>> getClasses(final String... packagesToScan){
		Set<Class<?>> classSet = Collections.emptySet();
		if(packagesToScan != null){
			classSet = new HashSet<Class<?>>();
			for(String location : packagesToScan){
				location = location.replaceAll(PACKAGE_SEPERATOR, PATH_SEPARATOR);
				location += (PATH_SEPARATOR + WILDCARD_CHAR + CLASS_FILE_SUFFIX);
				String rootDirectory = determineRootDirectory(location);
				String patternString  = location.substring(rootDirectory.length());
				File rootDirectoryFile = new File(rootDirectory);
				File[] children = rootDirectoryFile.listFiles();
				for(File child : children){
					
				}
				if(rootDirectory.matches(patternString)){
					
				} 
			}
		}
		return classSet;
	}
	
	protected static String determineRootDirectory(final String location){
		char[] sources = location.toCharArray();
		int endIndex = 0;
		String rootDirectory = location;
		int indexOfWildcard = indexofWildcardChar(sources);
		
		if(indexOfWildcard < sources.length){
			for(int index = indexOfWildcard ; index > -1 ; index--){
				if(sources[index] == PATH_SEPARATOR_CHAR){
					break;
				}
				endIndex++;
			}
			endIndex = indexOfWildcard - endIndex;
			char[] rootDirChars = Arrays.copyOfRange(sources, 0, endIndex);
			rootDirectory = "" + rootDirChars;
		}
		
		return rootDirectory;
	}

	private static int indexofWildcardChar(char[] sources) {
		int indexOfWildcard = 0;
		for(char chr : sources){
			if(chr == WILDCARD_CHAR){
				break;
			}
			indexOfWildcard++;
		}
		return indexOfWildcard;
	}
}
