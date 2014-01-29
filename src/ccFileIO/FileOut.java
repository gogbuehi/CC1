/*
 * FileOut.java
 *
 * Created on July 27, 2005, 9:26 AM
 */

package ccFileIO;

import Utilities.Validation;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author  goodwin.ogbuehi
 */
public class FileOut extends Thread {
    File file;
    /** Creates a new instance of FileOut */
    public FileOut() {
    }
    
    public FileOut(File nfile) throws NullPointerException {
        if (!nfile.exists())
            try {
                nfile.createNewFile();
                this.file = nfile;
            }
            catch (IOException e) {
                
            }
        else
            this.file = nfile;
    }
    
    public FileOut(String filename) throws NullPointerException {
        file = new File(filename);
        if (!file.exists())
            try {
                file.createNewFile();
            }
            catch (IOException e) {
                
            }
    }
    
    public String getFileName() {
        return file.getName();
    }
    
    public boolean isReadOnly() {
        return Validation.isReadOnly(file);
    }
    
    public boolean writeToFile(String text) throws NullPointerException {
        if (Validation.isValidFile(file)) {
            if (!isReadOnly()) {
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    PrintWriter pw = new PrintWriter(fos);
                    pw.print(text);
                    pw.close();
                    fos.close();
                    return true;
                }
                catch (FileNotFoundException e) {
                    return false;
                }
                catch (IOException e) {
                    return false;
                }
                /*
                try {
                    FileWriter fw = new FileWriter(file);
                    fw.write(text);
                    return true;
                }
                catch (IOException e) {
                    return false;
                }
                 */
            }
        }
        return false;
    }
    
    public boolean appendToFile(String text) throws NullPointerException {
        if (Validation.isValidFile(file)) {
            if (!isReadOnly()) {
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    PrintWriter pw = new PrintWriter(fos);
                    pw.print(text);
                    return true;
                }
                catch (FileNotFoundException e) {
                    return false;
                }
                catch (IOException e) {
                    return false;
                }
                /*
                 try {
                    FileWriter fw = new FileWriter(file);
                    fw.append(text);
                    return true;
                }
                catch (IOException e) {
                    return false;
                }
                 */
            }
        }
        return false;
    }
    
    public static void copyFile() throws IOException {
       
        File inputFile = new File("farrago.txt");
        File outputFile = new File("outagain.txt");

        FileReader in = new FileReader(inputFile);
        FileWriter out = new FileWriter(outputFile);
        int c;

        while ((c = in.read()) != -1)
           out.write(c);

        in.close();
        out.close();
       
    }
    
    public static void copyFile(File inputFile) throws IOException {
        String outFile = "Copy_of_" + inputFile.getName();
        File outputFile = new File(outFile);

        FileReader in = new FileReader(inputFile);
        FileWriter out = new FileWriter(outputFile);
        int c;

        while ((c = in.read()) != -1)
           out.write(c);

        in.close();
        out.close();
       
    }
}
