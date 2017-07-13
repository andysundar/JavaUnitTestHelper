/*******************************************************************************
 * Copyright 2017 Anindya Bandopadhyay (anindyabandopadhyay@gmail.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.pojotester.pack.scan;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.pojotester.reflection.annotation.ReflectionClassLevel;
import org.pojotester.utils.ClassUtilities;

public final class PackageScan {
	
	private static final String WILDCARD_REGX = "**";
	private static final char PATH_SEPARATOR_CHAR = '/'; //File.separatorChar;
	private static final char WILDCARD_CHAR = '*';
	private static final String PATH_SEPARATOR = "/";//File.separator;
	private static final String DOT = "\\.";
	private static final String CLASS_FILE_SUFFIX = ".class";
	private static final String CLASS_SUFFIX = "class";
	
	public static Set<Class<?>> getClasses(final String... packagesToScan){
		Set<Class<?>> classSet = Collections.emptySet();
		if(packagesToScan != null){
			classSet = new HashSet<Class<?>>();
			for(String location : packagesToScan){
				location = processLocations(location);
				String rootDirectory = determineRootDirectory(location);
				String patternString  = location.substring((rootDirectory.length() + 1));
				if(patternString.isEmpty()){
					// When exact path is given [e.g. mypack.MyClass.class]
					loadClassAndAddItToSet(classSet, rootDirectory);
				}  else {
					// Goto root directory and match pattern to search directories/files [e.g. mypack.**.My*.class, mypack.**.MyClass.class]
					String tempArray[] = patternString.split(PATH_SEPARATOR);
					List<String> patterns = new LinkedList<String>();
					for(String temp : tempArray){
						if(!WILDCARD_REGX.endsWith(temp)){
							patterns.add(temp);
						}
					}
					tempArray = null;
					Set<String> classFileSet = Collections.emptySet();
					ClassLoader classLoader = ClassUtilities.getDefaultClassLoader();
					URL url = classLoader.getResource(rootDirectory);
					
//					Path startDirectory = null;
//					try {
//						startDirectory = Paths.get(url.toURI());
//					} catch (URISyntaxException e) {
//						e.printStackTrace();
//					}
//					classFileSet = findClassFile(startDirectory, patterns);

					
					for(String className : classFileSet){
						loadClassAndAddItToSet(classSet, className);
					}
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
			rootDirectory = new String(rootDirChars);
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
	
	private static String processLocations(String location) {
		location = location.replaceAll(DOT, PATH_SEPARATOR);
		String pathSeparatorClassSuffix = PATH_SEPARATOR + CLASS_SUFFIX;
		if(location.endsWith(pathSeparatorClassSuffix)){
			int endIndex = location.length() - pathSeparatorClassSuffix.length();
			location = location.substring(0, endIndex);
			location += CLASS_FILE_SUFFIX;
		}
		if(!location.endsWith(CLASS_FILE_SUFFIX)){
			// When path end with *.* instead of *.class
			String pathSeparatorWildCard = PATH_SEPARATOR + WILDCARD_CHAR; 
			if(location.endsWith(pathSeparatorWildCard)){
				int endIndex = location.length() - pathSeparatorWildCard.length();
				location = location.substring(0, endIndex);
				location += pathSeparatorClassSuffix;
			} else {
				// When only folder is given 
				location += (PATH_SEPARATOR + WILDCARD_CHAR + CLASS_FILE_SUFFIX);
			}
		}
		return location;
	}
	
	private static Set<String> findClassFile(Path startDirectory, List<String> patterns) {
		Finder visitor = new Finder(patterns);
		
		try {
			Files.walkFileTree(startDirectory, visitor);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Set<String> classFileSet = visitor.getMactingSet();
		return classFileSet;
	}
	
	private static String getQualifiedClassName(String className) {
		int endIndex = className.length() - CLASS_FILE_SUFFIX.length();
		className = className.substring(0, endIndex);
		className = className.replaceAll(PATH_SEPARATOR, DOT);
		return className;
	}

	private static void loadClassAndAddItToSet(Set<Class<?>> classSet, String rootDirectory) {
		String className = getQualifiedClassName(rootDirectory);
		Class<?> clazz = ClassUtilities.loadClass(className);
		if(clazz != null) {
		    boolean ignoreThisClass = ReflectionClassLevel.ignoreClass(clazz);
			if(!ignoreThisClass){
				classSet.add(clazz);
			}
		}
	}
	
	public static Set<String> walk( String path , String pattern) {
		Set<String> result =  Collections.emptySet();
        File root = new File( path );
        String[] dirPaths = root.list();

        if (dirPaths != null) {
        	result = new LinkedHashSet<String>();
        	for(String dirPath : dirPaths){
        		File file = new File(dirPath);
        		if(pattern.endsWith(CLASS_FILE_SUFFIX) && file.isFile() && file.getName().matches(pattern)){
        			result.add(file.getAbsolutePath());
        		} else if(file.isDirectory()){
        			
        		}
        	}
        }

       
         return result;
    }
}
