/*
 * CCDirSearch.java
 *
 * Created on April 21, 2006, 2:02 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package CCInfo;

import Utilities.TestInfo;
import Utilities.Validation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

/**
 *
 * @author goodwin.ogbuehi
 */
public class CCDirSearch implements CaretListener, ActionListener {
    JTextField jtfFind;
    CCFileBrowser ccfb;
    File currDir;
    /** Creates a new instance of CCDirSearch */
    public CCDirSearch() {
        jtfFind = new JTextField(10);
        ccfb = new CCFileBrowser();
    }
    
    public CCDirSearch(CCFileBrowser ccfb) {
        this.jtfFind = ccfb.getJTF();
        this.ccfb = ccfb;
        File [] roots = File.listRoots();
        this.currDir = roots[0];
        
        jtfFind.addCaretListener(this);
        jtfFind.addActionListener(this);
    }
    
    public void caretUpdate(CaretEvent e) {
        //Color [] clrArray = new Color [2];
        //Color color = Color.RED;
        //jtfFind.setBackground(color);
        //searchAndSelect();
        limitDirs();
        TestInfo.testWriteLn("Dir Caret Update");
    }
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        String text = jtfFind.getText();
        if (!text.equals("")) {
            ccfb.openFirstDir(text);
        }
            
    }
    
    private void limitDirs() {
        //TestInfo.testWriteLn("Curr. Dir: " + this.currDir.getName());
        String text = jtfFind.getText();
        File currDir = ccfb.getCurrentDir();
        if (Validation.isValidDirectory(currDir) && !ccfb.currentIsRoot()) {
            this.currDir = currDir;
        }
        
        if (ccfb.currentIsRoot()) {
            ccfb.setRoots();
        }
        else {
            ccfb.setDirList(text,this.currDir);
        }
        //TestInfo.testWriteLn("Curr. Dir: " + this.currDir.getName());
    }
    
    public void clearJTF() {
        jtfFind.removeCaretListener(this);
        jtfFind.setText("");
        jtfFind.addCaretListener(this);
    }
}
