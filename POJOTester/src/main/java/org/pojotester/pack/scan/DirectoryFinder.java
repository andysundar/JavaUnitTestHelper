/*******************************************************************************
 * Copyright 2017 Anindya Bandopadhyay (anindyabandopadhyay@gmail.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package org.pojotester.pack.scan;

import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.FileVisitResult.SKIP_SIBLINGS;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.List;

class DirectoryFinder  extends SimpleFileVisitor<Path> {
	
	private boolean patternsPresent; 
	private PathMatcher matcher;
    private File directory;
    private String pattern = "*.class";
    private List<String> patterns;
    private boolean postDirLock;
    
    public DirectoryFinder(List<String> patterns) {
    	this.patterns = Collections.emptyList();
    	patternsPresent = (patterns != null && !patterns.isEmpty());
		if (patternsPresent) {
			pattern = patterns.get(0);
			matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
			patterns.remove(0);
			this.patterns = patterns;
		}
     }

    
     boolean find(Path file) {
    	 boolean flag = false;
         Path name = file.getFileName();
         if (name != null && matcher.matches(name)) {
        	 flag = true;
         }
         return flag;
     }

     public File getPackage(){
    	 return directory;
     }
       
     @Override
     public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
    	 postDirLock = false;
    	 if(patternsPresent && find(dir)){
    		 directory = dir.toFile();
    		 patternsPresent = (patterns != null && !patterns.isEmpty());
			if (patternsPresent) {
				pattern = patterns.get(0);
				matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
				patterns.remove(0);
			}
			postDirLock = true;
    	 }
    	 return CONTINUE;
     }
     @Override 
     public FileVisitResult postVisitDirectory(Path dir, IOException exc){
    	 if(patternsPresent && postDirLock){
    		 
    		 return SKIP_SIBLINGS;
    	 }
		return CONTINUE;
    	 
     }

}
