/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ccFileIO;

import java.io.File;
import javax.swing.DefaultListModel;

/**
 *
 * @author Goodwin
 */
public class CCDirectory {
    protected File mDirectory;
    protected File[] mDirectoryFiles;
    
    public CCDirectory() {
        mDirectory = new File(".");
        mDirectoryFiles = mDirectory.listFiles();
    }
    
    public File[] getFilesWithExtension(String extension) {
        DefaultListModel filesWithExtension = new DefaultListModel();
        int lastIndexOfPeriod;
        String extensionOfFile;
        for(File i : mDirectoryFiles) {
            extensionOfFile = "";
            lastIndexOfPeriod = i.getName().lastIndexOf(".");
            if (lastIndexOfPeriod > 0 && (lastIndexOfPeriod + 1 < i.getName().length())) {
                extensionOfFile = i.getName().substring(lastIndexOfPeriod+1);
            }
            //i.getName().split
            if (i.isFile() && extension.equalsIgnoreCase(extensionOfFile)) {
                filesWithExtension.addElement(i);
            }
        }
        
        File[] result = new File[filesWithExtension.size()];
        filesWithExtension.copyInto(result);
        return result;
    }
}
