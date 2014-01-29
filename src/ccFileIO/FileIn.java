/*
 * FileIn.java
 *
 * Created on January 6, 2006, 12:43 PM
 */

package ccFileIO;

import Utilities.Validation;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author  goodwin.ogbuehi
 */
public class FileIn {
    File file;
    FileReader fileReader;
    boolean isOpen;
    /** Creates a new instance of FileIn */
    public FileIn() {
        isOpen = false;
    }
    public FileIn(File iFile) {
        file = iFile;
        isOpen = true;
    }
    
    public boolean setFile(FileAccess fa) {
        File file = fa.getFile(null);
        if (Validation.isValidFile(file)) {
            this.file = file;
            isOpen = true;
            return isOpen;
        }
        return false;
    }
    public boolean setFile(File file) {
        if (Validation.isValidFile(file)) {
            this.file = file;
            isOpen = true;
            return isOpen;
        }
        return false;
    }
    public File getFile() {
        return file;
    }
    private boolean openFile(String filename) {
        if (!isOpen) {
            try {
                file = new File(filename);
                isOpen = file.exists();
                return isOpen;
            }
            catch (NullPointerException e) {
                isOpen = false;
                return false;
            }
        }
        return false;
    }
    private boolean openReader() {
        try {
             fileReader = new FileReader(file);
             return true;
         }
         catch (FileNotFoundException e) {
             return false;
         }
    }
    
    public int getBufferedLineCount() {
        int counter = 0;
        if (openReader()) {
            BufferedReader br = new BufferedReader(fileReader);
            try {
                while (br.ready()){
                    try {
                        br.readLine();
                        counter++;
                    }
                    catch (IOException e) {

                    }
                }
                br.close();
            }
            catch (IOException e) {
                
            }
            closeReader();
        }
        return counter;
    }
    public String [] readLines() {
        int lines = getBufferedLineCount();
        //int bufferSize = Integer.valueOf(size/100);
        String [] fileText = {""};
        int counter = 0;
        if (lines != 0) {
            fileText = new String [lines];
            if (openReader()) {
                BufferedReader br = new BufferedReader(fileReader);
                try {
                    while (br.ready()){
                        try {
                            fileText[counter] = br.readLine();
                            counter++;
                        }
                        catch (IOException e) {

                        }
                    }
                    br.close();
                }
                catch (IOException e) {

                }
                closeReader();
            }
        }
        return fileText;
    }
    
    private void closeReader() {
        isOpen = false;
        try {
            fileReader.close();
        }
        catch (IOException e) {
            return;
        }
    }
    
    public String getFileText(File textFile) {
        if (setFile(textFile)) {
            String [] linesArray = readLines();
            String returnText = "";
            for (int i = 0; i < linesArray.length; i++) {
                returnText += linesArray[i];
            }
        }
        return "";
    }
}
