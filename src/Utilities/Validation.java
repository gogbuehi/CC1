/*
 * Validation.java
 *
 * Created on July 18, 2005, 12:38 PM
 */

package Utilities;

import java.io.File;

/**
 *
 * @author  goodwin.ogbuehi
 */
public class Validation {
    private final static String [] EXT2 = {".js",".cc"};
    private final static String [] EXT3 = {".txt",".asp",".php",".xml",".htm",".nfo",".css",".sql"};
    private final static String [] EXT4 = {".html",".java"};
    /** Creates a new instance of Validation */
    public Validation() {
    }
    
    public static boolean isValidArrayInt(int num) {
        if (num > 0)
            return true;
        return false;
    }
    public static boolean isNumeric(String num) {
        try {
            Integer.valueOf(num);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }
    public static boolean isValidFile(File file) {
        try {
            if (file.exists())
                return file.isFile();
            return false;
        }
        catch (NullPointerException e) {
            return false;
        }
    }
    public static boolean isValidFile(File dir, String file) {
        File newFile = new File(dir.getAbsolutePath() + "\\" + file);
        if (newFile.exists())
            return newFile.isFile();
        return false;
    }
    public static boolean isValidFile(String file) {
        File newFile = new File(file);
        if (newFile.exists())
            return newFile.isFile();
        return false;
    }
    public static boolean isValidDirectory(File dir) {
        try {
            if (dir.exists())
                return dir.isDirectory();
        }
        catch (NullPointerException e) {
            return false;
        }
        return false;
    }
    public static boolean isValidDirectory(File dir, String subDir) {
        File newDir = new File(dir.getAbsolutePath() + "\\" + subDir);
        if (newDir.exists())
            return newDir.isDirectory();
        return false;
    }
    public static boolean isValidDirectory(String dir) {
        File newDir = new File(dir);
        if (newDir.exists())
            return newDir.isDirectory();
        return false;
    }
    
    public static boolean isReadOnly(File file) {
        try {
            if (file.canWrite())
                return false;
            return true;
        }
        catch (NullPointerException e) {
            return false;
        }
    }
    
    public static boolean isReadOnly(String strFile) {
        try {
            File file = new File(strFile);
            if (file.canWrite()) {
                System.out.println("Can Write!\n"+file.getName());
                return false;
            }
            System.out.println("Can't Write!\n"+file.getName());
            return true;
        }
        catch (NullPointerException e) {
            System.out.println("NullPointer!\n");
            return false;
        }
        
    }
    
    public static boolean isTextFile(File file) {
        String fileName = file.getName();
        int lastPeriod = fileName.lastIndexOf(".");
        if (lastPeriod != -1)
            if (fileName.length() == (lastPeriod + 3)) {
                String extension = fileName.substring(lastPeriod).toLowerCase();
                for (int i = 0; i < EXT2.length; i++) {
                    if (EXT2[i].equals(extension))
                        return true;
                }
            }
            else if (fileName.length() == (lastPeriod + 4)) {
                String extension = fileName.substring(lastPeriod).toLowerCase();
                for (int i = 0; i < EXT3.length; i++) {
                    if (EXT3[i].equals(extension))
                        return true;
                }
            }
            else if (fileName.length() > 5) {
                String extension = fileName.substring(lastPeriod).toLowerCase();
                for (int i = 0; i < EXT4.length; i++) {
                    if (EXT4[i].equals(extension))
                        return true;
                }
            }
        return false;
    }
    
    public static boolean isRootDir(File dir) {
        try {
            File [] roots = File.listRoots();
            for (int i = 0; i < roots.length; i++) {
                if (dir.equals(roots[i])) {
                    return true;
                }
            }
            return false;
        }
        catch (NullPointerException e) {
            return false;
        }
    }
    
}
