/*
 * CCFile.java
 *
 * Created on January 6, 2006, 1:59 PM
 */

package ccFileIO;

import Utilities.Validation;
import cc1.CCText;
import cc1.CCTextTabs;
import java.io.File;

/**
 *
 * @author  goodwin.ogbuehi
 */
public class CCFile {
    FileAccess fa;
    //JTextArea jTA;
    //JFrame jF;
    String text;
    /** Creates a new instance of CCFile */
    public CCFile() {
        fa = new FileAccess();
        text = "";
    }
    
    /** Get a file to open via the file chooser and read in the text */
    public boolean openFile() {
        FileIn fi = new FileIn();
        if (fi.setFile(fa)) {
            writeLns(fi.readLines(),50);
            return true;
        }
        else {
            return false;
        }
    }
    
    /** Read in the text of a file */
    public boolean openFile(File file) {
        FileIn fi = new FileIn();
        if (fi.setFile(file)) {
            writeLns(fi.readLines(),50);
            return true;
        }
        else {
            return false;
        }
    }
    
    /** Get a file to open via the file chooser */
    public File getFileToOpen() {
        File retFile = fa.chooseOpenFile(null);
        return retFile;
    }
    
    /** Get a file to save via the file chooser and save the text to it */
    public boolean saveFile() {
        try {
            File file = fa.chooseSaveFile(null);
            FileOut fo = new FileOut(file);
            if (fo.writeToFile(text)) {
                return true;
            }
            else if (Validation.isReadOnly(file)) {
                return false;
            }
        }
        catch (NullPointerException e) {
            return false;
        }
        return false;
    }
    
    /** Save the text to a file */
    public boolean saveFile(File file) {
        try {
            FileOut fo = new FileOut(file);
            if (fo.writeToFile(text)) {
                return true;
            }
            else if (Validation.isReadOnly(file)) {
                return false;
            }
        }
        catch (NullPointerException e) {
            return false;
        }
        return false;
    }
    
    public boolean saveFile(String filename, String fileContents) {
        File file = new File(filename);
        setText(fileContents);
        return saveFile(file);
    }
    
    /** Save the text to a file */
    public boolean saveFile(CCTextTabs ccTT, File file) {
        String tempText = text;
        setText(ccTT);
        try {
            FileOut fo = new FileOut(file);
            if (fo.writeToFile(text)) {
                return true;
            }
            else if (Validation.isReadOnly(file)) {
                text = tempText;
                return false;
            }
        }
        catch (NullPointerException e) {
            text = tempText;
            return false;
        }
        text = tempText;
        return false;
    }
    
    /** Get a file to save via the file chooser */
    public File getFileToSave() {
        return fa.chooseSaveFile(null);
    }
    
    /** Set the text associated with the file */
    public void setText(String newText) {
        text = newText;
    }
    
    /** Set the text associated with the file, from the selected CCTextTabs component (CCText) */
    public void setText(CCTextTabs ccTT) {
        if (ccTT.getTabCount() != 0) {
            CCText cct = (CCText) ccTT.getSelectedComponent();
            text = cct.getJTA().getText();
        }
    }
    
    /** Gets the text associated with the CCFile */
    public String getText() {
        return text;
    }
    
    public void writeFile(File openFile) {
        FileIn fi = new FileIn();
        if (fi.setFile(openFile)) {
            writeLns(fi.readLines(),50);
        }
    }
    
    public void write(String newText) {
        text += newText;
    }
    
    public void writeLn(String newText) {
        text += newText + "\n";
    }
    
    public void writeLns(String [] text) {
        for (int i = 0; i < text.length; i++) {
            writeLn(text[i]);
        }
    }
    
    public void writeLns(String [] text, int buffer) {
        String bufferText = "";
        for (int i = 0; i < text.length; i++) {
            if ((i+1)%buffer == 0) {
                write(bufferText);
                bufferText = "";
            }
            if (i == (text.length-1)) {
                bufferText += text[i];
            }
            else {
                bufferText += text[i] + "\n";
            }
        }
        write(bufferText);
    }
    
    public void clear() {
        text = "";
    }
    
    /** Determine if a file has already been opened */
    public static int checkOpenFile(CCTextTabs ccTT, File file) {
        int tabCount = ccTT.getTabCount();
        CCText cct;
        for (int i = 0; i < tabCount; i++) {
            cct = (CCText) ccTT.getComponentAt(i);
            if (cct.hasFile) {
                if (cct.file.equals(file)) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    public File getDefaultDir() {
        return fa.getDefaultDirectory();
    }
}


/*************************************************/


/*
 * CCFile.java
 *
 * Created on January 6, 2006, 1:59 PM
 */
/*
package ccFileIO;

import Utilities.Validation;
import cc1.CCFrame;
import cc1.CCText;
import cc1.CCTextTabs;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 *
 * @author  goodwin.ogbuehi
 *
public class CCFile {
    FileAccess fa;
    JTextArea jTA;
    JFrame jF;
    /** Creates a new instance of CCFile *
    public CCFile() {
        jTA = new JTextArea();
    }
    public CCFile(JTextArea newJTA) {
        fa = new FileAccess();
        jTA = newJTA;
    }
    
    public CCFile(CCFrame ccf) {
        fa = new FileAccess();
        jTA = ccf.ccTT.getCurrentJTA();
        jF = ccf;
    }
    
    public void updateCCFile(CCTextTabs ccTT) {
        jTA = ccTT.getCurrentJTA();
    }
    
    public File getFile() {
        FileIn fi = new FileIn();
        if (fi.setFile(fa)) {
            return fi.getFile();
        }
        return null;
    }
    
    public boolean openFile() {
        FileIn fi = new FileIn();
        if (fi.setFile(fa)) {
            writeLns(fi.readLines(),50);
            return true;
        }
        else
            return false;
    }
    public File getSaveFile() {
        return fa.getSaveFile(jF);
    }
    public boolean saveFile() {
        //try {
        File saveFile = fa.getSaveFile(jF);
        return saveFile(saveFile);
            /*
            FileOut fo = new FileOut(saveFile);
            String saveText = mainTA.getText();
            if (fo.writeToFile(saveText)) {
                //String saveTime = new Date().toString();
                //cc.setMessage("\"" + file.getName() + "\"" + " last saved: " + saveTime);
                //cc.setMessage("\"" + file.getName() + "\"" + " Saved");
                //cc.setTitle(file);
                //sb.saveDocument(text,jTA.getCaretPosition());
                //cc.setMessage("FileOut Write To File Success");
                //writeLn(file.getName());
                //FileInFunction(file);
                return true;
            }
            else if (Validation.isReadOnly(saveFile)) {
                return saveFile();
            }
        }
        catch (NullPointerException e) {
            return false;
        }
        return false;
             
    }
             *
    }
    public boolean saveFile(File saveFile) {
        try {
            //File saveFile = fa.getSaveFile(mainFrame);
            FileOut fo = new FileOut(saveFile);
            String saveText = jTA.getText();
            if (fo.writeToFile(saveText)) {
                return true;
            }
            else if (Validation.isReadOnly(saveFile)) {
                return false;
            }
        }
        catch (NullPointerException e) {
            return false;
        }
        return false;
    }
    
    /*
    public void FileOutFunction(String strParent,String strFile) {
        try {
            File file = new File(strParent,strFile);
            cc.setMessage("Saving " + file.getName());
            FileOut fo = new FileOut(file);
            //cc.setTitle("FileOut Created.");
            String text = jTA.getText();
            //cc.setTitle("Text retrieved...");
            if (fo.writeToFile(text)) {
                String saveTime = new Date().toString();
                cc.setMessage("\"" + strFile + "\"" + " last saved: " + saveTime);
                cc.setTitle(file);
                sb.saveDocument(text,jTA.getCaretPosition());
                //writeLn(file.getName());
                //FileInFunction(file);
            }
            else if (Validation.isReadOnly(file)) {
                cc.setMessage("Cannot save to\"" + file.getName() + "\": Read-Only");
                FileOutFunction();
            }
            
        }
        catch (NullPointerException e) {
            cc.setMessage("Saving failed.");
        }
    }
     *
    public void writeFile(File openFile) {
        FileIn fi = new FileIn();
        if (fi.setFile(openFile)) {
            writeLns(fi.readLines(),50);
        }
    }
    
    public void write(String text) {
        //String preText = mainTA.getText();
        jTA.append(text);
    }
    
    public void writeLn(String text) {
        jTA.append(text + "\n");
    }
    
    public void writeLns(String [] text) {
        for (int i = 0; i < text.length; i++)
            writeLn(text[i]);
    }
    
    private void writeLns(String [] text, int buffer) {
        String bufferText = "";
        for (int i = 0; i < text.length; i++) {
            if ((i+1)%buffer == 0) {
                write(bufferText);
                bufferText = "";
            }
            if (i == (text.length-1))
                bufferText += text[i];
            else
                bufferText += text[i] + "\n";
        }
        write(bufferText);
    }
    
    public void clear() {
        jTA.setText("");
    }
    
    public static int checkOpenFile(CCTextTabs ccTT, File file) {
        int tabCount = ccTT.getTabCount();
        CCText cct;
        for (int i = 0; i < tabCount; i++) {
            cct = (CCText) ccTT.getComponentAt(i);
            if (cct.hasFile) {
                if (cct.file.equals(file)) {
                    return i;
                }
            }
        }
        return -1;
    }
}
*/