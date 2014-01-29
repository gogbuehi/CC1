/*
 * CCDialog.java
 *
 * Created on January 30, 2006, 11:48 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ccDialogs;

import Utilities.TestInfo;
import java.awt.Component;
import java.awt.Frame;
import java.awt.HeadlessException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Goodwin
 */
public class CCDialog extends JOptionPane {
    //JFrame jf;
    Component jf;
    //Frame f;
    boolean isJFrame = false;
    /**
     * Creates a new instance of CCDialog
     */
    public CCDialog() {
    }
    
    public CCDialog(JFrame newJF) {
        jf = newJF;
        isJFrame = true;
    }
    public CCDialog(Frame newJF) {
        jf = newJF;
    }
    
    public boolean showConfirmDialog() {
        try {
            String message = "This is a test message.";
            String title = "Test Dialog";
            //TestInfo.testWriteLn("Show Dialog");
            int intDialog = showConfirmDialog(jf,message,title,JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE);
            if (intDialog == JOptionPane.YES_OPTION) {
                return true;
            }
        }
        catch (HeadlessException e) {
            TestInfo.testWriteLn("Headless Exception");
            return false;
        }
        return false;
    }
    
    public void showAlreadyOpenDialog() {
        try {
            String message = "This file is currently in use.\nPlease select another file\nor close the open file\nand try again.";
            String title = "File Status";
            showMessageDialog(jf,message,title,JOptionPane.ERROR_MESSAGE);
        }
        catch (HeadlessException e) {
            TestInfo.testWriteLn("Headless Exception");
        }
    }
    
    public boolean showAlreadyExists() {
        try {
            String message = "This file already exists.\nAre you sure you want to overwrite it?";
            String title = "File Status";
            //TestInfo.testWriteLn("Show Dialog");
            int intDialog = showConfirmDialog(jf,message,title,JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
            if (intDialog == JOptionPane.YES_OPTION) {
                return true;
            }
        }
        catch (HeadlessException e) {
            TestInfo.testWriteLn("Headless Exception");
            return false;
        }
        return false;
    }
    
    public int showSaveChanges() {
        try {
            String message = "Do you want to save your changes?";
            String title = "Save Changes";
            //TestInfo.testWriteLn("Show Dialog");
            int intDialog = showConfirmDialog(jf,message,title,JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
            return intDialog;
            /*
            if (intDialog == JOptionPane.YES_OPTION) {
                return 1;
            }
            else if (intDialog == JOptionPane.CANCEL_OPTION) {
                return 0;
            }
             */
        }
        catch (HeadlessException e) {
            TestInfo.testWriteLn("Headless Exception");
            return JOptionPane.CANCEL_OPTION;
        }
        //return -1;
    }
    
    public int showDiscardChanges() {
        try {
            String message = "All changes will be discarded.\nRevert to last save?";
            String title = "Discard Changes";
            //TestInfo.testWriteLn("Show Dialog");
            int intDialog = showConfirmDialog(jf,message,title,JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
            return intDialog;
            /*
            if (intDialog == JOptionPane.YES_OPTION) {
                return 1;
            }
            else if (intDialog == JOptionPane.CANCEL_OPTION) {
                return 0;
            }
             */
        }
        catch (HeadlessException e) {
            TestInfo.testWriteLn("Headless Exception");
            return JOptionPane.CANCEL_OPTION;
        }
        //return -1;
    }
}
