/*
 * CCText.java
 *
 * Created on January 6, 2006, 4:30 PM
 */

package cc1;

import Utilities.Validation;
import cc1.ccTextEditor.TabHold;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author  goodwin.ogbuehi
 */
public class CCText extends JScrollPane {
    JTextArea jTA;
    //JScrollPane mainSP;
    public File file;
    public boolean hasFile,changed,readOnly;
    public String title;
    public TabHold th;
    
    public Object undo;

    public CCText() {
        super();
        jTA = new JTextArea("",20,80);
        //jTA = new JTextArea("");
        jTA.setFont(new Font("Courier New", Font.PLAIN, 14));
        jTA.setDisabledTextColor(Color.GREEN);
        jTA.setLineWrap(false);
        jTA.setWrapStyleWord(false);
        //mainSP = new JScrollPane(mainArea);
        this.setViewportView(jTA);
        th = new TabHold(this);
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        hasFile = false;
        changed = false;
        readOnly = false;
    }
    public CCText(String title) {
        super();
        jTA = new JTextArea("",20,80);
        //jTA = new JTextArea("");
        jTA.setFont(new Font("Courier New", Font.PLAIN, 14));
        jTA.setLineWrap(false);
        jTA.setWrapStyleWord(false);
        //mainSP = new JScrollPane(mainArea);
        this.setViewportView(jTA);
        th = new TabHold(this);
        this.title = title;
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        hasFile = false;
        changed = false;
        readOnly = false;
    }
    public CCText(int defaultTab) {
        super();
        jTA = new JTextArea("",20,80);
        //jTA = new JTextArea("");
        jTA.setFont(new Font("Courier New", Font.PLAIN, 14));
        jTA.setLineWrap(false);
        jTA.setWrapStyleWord(false);
        //mainSP = new JScrollPane(mainArea);
        this.setViewportView(jTA);
        th = new TabHold(this);
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        hasFile = false;
        changed = false;
        readOnly = false;
    }
    public CCText(File textFile) {
        super();
        jTA = new JTextArea("",20,80);
        //jTA = new JTextArea("");
        jTA.setFont(new Font("Courier New", Font.PLAIN, 14));
        jTA.setLineWrap(false);
        jTA.setWrapStyleWord(false);
        //mainSP = new JScrollPane(mainArea);
        this.setViewportView(jTA);
        th = new TabHold(this);
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        file = textFile;
        hasFile = true;
        changed = false;
        readOnly = Validation.isReadOnly(file);
        setReadOnly(readOnly);
    }

    public void setReadOnly(boolean isReadOnly) {
        jTA.setEditable(!isReadOnly);
        
    }

    /*
    public JScrollPane getScrollPane() {
        return mainSP;
    }
     */
    
    public JTextArea getJTA() {
        return jTA;
    }
    
    public TabHold getTabHold() {
        return th;
    }
    
    public void setFile(File file) {
        if (Validation.isValidFile(file)) {
            this.file = file;
            title = file.getName();
            hasFile = true;
            changed = false;
            readOnly = Validation.isReadOnly(file);
            setReadOnly(readOnly);
        }
    }
    public void clear() {
        jTA.setText("");
    }
    
    public File getDefaultDir() {
        return file.getParentFile();
    }
    
    public void setLocale(Locale l) {
        jTA.setDefaultLocale(l);
    }
}
