package org.pojotester.pack.scan;

import static java.nio.file.FileVisitResult.CONTINUE;

import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Set;
import java.util.TreeSet;

class Finder  extends SimpleFileVisitor<Path> {
	
	private final PathMatcher matcher;
    private final Set<String> classFileSet;
    
    public Finder(String pattern) {
         matcher = FileSystems.getDefault()
                 .getPathMatcher("glob:" + pattern);
         classFileSet = new TreeSet<String>();
     }

    
     boolean find(Path file) {
    	 boolean flag = false;
         Path name = file.getFileName();
         if (name != null && matcher.matches(name)) {
        	 flag = classFileSet.add(name.toString());
         }
         return flag;
     }

     public Set<String> getMactingSet(){
    	 return classFileSet;
     }
     
     @Override
     public FileVisitResult visitFile(Path file,
             BasicFileAttributes attrs) {
         find(file);
         return CONTINUE;
     }

     @Override
     public FileVisitResult preVisitDirectory(Path dir,
             BasicFileAttributes attrs) {
         find(dir);
         return CONTINUE;
     }

}
