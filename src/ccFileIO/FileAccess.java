/*
 * FileAccess.java
 *
 * Created on July 18, 2005, 12:37 PM
 */

package ccFileIO;

import Utilities.Validation;
import java.awt.Component;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author  goodwin.ogbuehi
 */
public class FileAccess {
    //File iFile,oFile;
    File dir;
    int limit;
    
    JFileChooser fileChooser;
    
    /** Creates a new instance of FileAccess */
    public FileAccess() {
        //setFileSelectionMode
        fileChooser = new JFileChooser(FileSystemView.getFileSystemView());
        dir = fileChooser.getCurrentDirectory();
    }
    public void setDefaultDirectory(String dir) {
        if (Validation.isValidDirectory(dir))
            this.dir = new File(dir);
            fileChooser.setCurrentDirectory(this.dir);
    }
    public void setDefaultDirectory(Component parent) {
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setDialogTitle("Choose Working Directory...");
        int chsrInt = fileChooser.showOpenDialog(parent);
        if (chsrInt == JFileChooser.APPROVE_OPTION) {
            dir = fileChooser.getSelectedFile();
        }
    }
    
    public File getDefaultDirectory() {
        return fileChooser.getCurrentDirectory();
    }
    
    /** Open a file chooser to choose a open file */
    public File chooseOpenFile(Component parent) {
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setDialogTitle("Open");
        int chsrInt = fileChooser.showOpenDialog(parent);
        if (chsrInt == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        else
            return null;
    }
    
    /** Open a file chooser to choose a save file */
    public File chooseSaveFile(Component parent) {
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setDialogTitle("Save...");
        int chsrInt = fileChooser.showSaveDialog(parent);
        if (chsrInt == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        else
            return null;
        
    }
    
    public File getFile(Component parent) {
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setDialogTitle("Choose File...");
        int chsrInt = fileChooser.showOpenDialog(parent);
        if (chsrInt == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        else
            return null;
    }
    
    /*
    public boolean setInFile(Component parent) {
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setDialogTitle("Choose File...");
        int chsrInt = fileChooser.showOpenDialog(parent);
        if (chsrInt == JFileChooser.APPROVE_OPTION) {
            iFile = fileChooser.getSelectedFile();
            return true;
        }
        return false;
    }
    public boolean setInFile(File file) {
        if (Validation.isValidFile(file)) {
            iFile = file;
            return true;
        }
        return false;
    }
    public File getInFile() {
        return iFile;
    }
    public File getOutFile() {
        return oFile;
    }
     */
}