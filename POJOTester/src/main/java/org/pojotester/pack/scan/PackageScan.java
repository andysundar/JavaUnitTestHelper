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
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.pojotester.reflection.annotation.ReflectionClassLevel;
import org.pojotester.utils.ClassUtilities;

public final class PackageScan {
	
	private static final char PATH_SEPARATOR_CHAR = '/'; //File.separatorChar;
	private static final char WILDCARD_CHAR = '*';
	private static final String WILDCARD_REGX = "**";
	private static final String PATH_SEPARATOR = "/";//File.separator;
	private static final String DOT = "\\.";
	private static final String CLASS_FILE_SUFFIX = ".class";
	private static final String CLASS_SUFFIX = "class";
	private static final String ILLEGAL_PACKAGE = "Package cannot start with " + WILDCARD_REGX ;
	
	public Set<Class<?>> getClasses(final String... packagesToScan){
		Set<Class<?>> classSet = Collections.emptySet();
		if(packagesToScan != null){
			classSet = new HashSet<Class<?>>();
			for(String location : packagesToScan){
				if(location.startsWith(WILDCARD_REGX)){
					throw new IllegalArgumentException(ILLEGAL_PACKAGE + " [ " + location + " ]");
				}
				String startPackage = location.split(DOT)[0];
			
				location = processLocations(location);
				String rootDirectory = determineRootDirectory(location);
				String patternString  = "";
				if(!location.equals(rootDirectory)){
					patternString  = location.substring(rootDirectory.length() + 1);
				} 
				
				if(patternString.isEmpty()){
					// When exact path is given [e.g. mypack.MyClass.class]
					String binaryClassName = getQualifiedClassName(startPackage, rootDirectory);
					Class<?> clazz = loadClassAndAddItToSet(binaryClassName);
					if(clazz != null){
						classSet.add(clazz);
					}
				}  else {
					// Goto root directory and match pattern to search directories/files [e.g. mypack.**.My*.class, mypack.**.MyClass.class]
					List<String> patterns = new LinkedList<String>();
					String[] patternStrings = patternString.split(PATH_SEPARATOR);
					String classFilePattern = WILDCARD_CHAR + CLASS_FILE_SUFFIX;
					for(String pattern : patternStrings){
						if(pattern.endsWith(CLASS_FILE_SUFFIX)){
							classFilePattern = pattern;
						} else if(!WILDCARD_REGX.equals(pattern)){
							patterns.add(pattern);
						} 
					}
					File packageDirectory = findPackageDirectory(rootDirectory, patterns);
					Path path = packageDirectory.toPath();
					Set<String> classFiles = findClassFiles(path, classFilePattern);
					for(String className : classFiles){
						String binaryClassName = getQualifiedClassName(startPackage, className);
						Class<?> clazz = loadClassAndAddItToSet(binaryClassName);
						if(clazz != null){
							classSet.add(clazz);
						}
					}
				}
				
			}
		}
		return classSet;
	}

	protected String determineRootDirectory(final String location){
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

	private int indexofWildcardChar(char[] sources) {
		int indexOfWildcard = 0;
		for(char chr : sources){
			if(chr == WILDCARD_CHAR){
				break;
			}
			indexOfWildcard++;
		}
		return indexOfWildcard;
	}
	
	private String processLocations(String location) {
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
	
	private File findPackageDirectory(String rootDirectory, List<String> patterns) {
		ClassLoader classLoader = ClassUtilities.getDefaultClassLoader();
		URL url = classLoader.getResource(rootDirectory);
		URI uri = null;
		File packageDirectory = null;
		try {
			uri = url.toURI();
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		if(uri != null){
			if(!patterns.isEmpty()){
				packageDirectory = patternMaching(patterns, uri);
			} else {
				packageDirectory = new File(uri);
			}
			
		} 
		return packageDirectory;
	}

	private File patternMaching(List<String> patterns, URI uri) {
		File packageDirectory;
		DirectoryFinder visitor = new DirectoryFinder(patterns);
		
		try {
			Path startDirectory = Paths.get(uri);
			Files.walkFileTree(startDirectory, visitor);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		packageDirectory = visitor.getPackage();
		return packageDirectory;
	}
	
	private Set<String> findClassFiles(Path dir, String patternString) {
		Set<String> classFiles = Collections.emptySet();
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, patternString)){
			classFiles = new HashSet<String>();
			for(Path path : stream){
				File file = path.toFile();
				if(file.isFile()){
					classFiles.add(file.getAbsolutePath());
				}
			}
		} catch (DirectoryIteratorException | IOException ex) {
	         ex.printStackTrace();
	       }
		return classFiles;
	}
	
	private String getQualifiedClassName(String startPackage, String classNamePath) {
		int endIndex = classNamePath.length() - CLASS_FILE_SUFFIX.length();
		int startIndex = classNamePath.indexOf(startPackage);
		classNamePath = classNamePath.substring(startIndex, endIndex);
		if(classNamePath.contains(File.separator)){
			String separator = File.separator + File.separator ;
			classNamePath = classNamePath.replaceAll(separator, DOT);
		} else {
			classNamePath = classNamePath.replaceAll(PATH_SEPARATOR, DOT);
		}
		
		return classNamePath;
	}

	private Class<?> loadClassAndAddItToSet(String className) {
		Class<?> clazz = ClassUtilities.loadClass(className);
		if(clazz != null) {
		    boolean ignoreThisClass = ReflectionClassLevel.ignoreClass(clazz);
			if(ignoreThisClass){
				clazz = null;
			}
		}
		return clazz;
	}
		
}