/*
 * CCText.java
 *
 * Created on January 6, 2006, 4:30 PM
 */

package cc1;

import Utilities.Validation;
import cc1.ccTextEditor.CCTextArea;
import cc1.ccTextEditor.StringUtil;
import ccFileIO.CCFile;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

/**
 *
 * @author  goodwin.ogbuehi
 */
public class CCText extends JScrollPane {
    //JTextArea jTA;
    CCTextArea jTA;
    //JScrollPane mainSP;
    public File file;
    public boolean hasFile,changed,readOnly,unsaved;
    public String languageMapName;
    //boolean changed,readOnly,unsaved;
    public String title;
    
    public Object undo;
    
    protected String changesFileName = "";

    public CCText() {
        super();
        jTA = new CCTextArea("",20,80);
        //jTA = new JTextArea("");
        //jTA.setFont(new Font("Courier New", Font.PLAIN, 14));
        jTA.setFont(new Font("Euphemia", Font.PLAIN, 14));
        jTA.setDisabledTextColor(Color.GREEN);
        jTA.setLineWrap(false);
        jTA.setWrapStyleWord(false);
        //mainSP = new JScrollPane(mainArea);
        this.setViewportView(jTA);
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        hasFile = false;
        changed = false;
        readOnly = false;
        languageMapName = "";
    }
    public CCText(String title) {
        super();
        jTA = new CCTextArea("",20,80);
        //jTA = new JTextArea("");
        //jTA.setFont(new Font("Courier New", Font.PLAIN, 14));
        jTA.setFont(new Font("Euphemia", Font.PLAIN, 14));
        jTA.setLineWrap(false);
        jTA.setWrapStyleWord(false);
        //mainSP = new JScrollPane(mainArea);
        this.setViewportView(jTA);
        this.title = title;
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        hasFile = false;
        changed = false;
        readOnly = false;
        languageMapName = "";
    }
    public CCText(int defaultTab) {
        super();
        jTA = new CCTextArea("",20,80);
        //jTA = new JTextArea("");
        //jTA.setFont(new Font("Courier New", Font.PLAIN, 14));
        jTA.setFont(new Font("Euphemia", Font.PLAIN, 14));
        jTA.setLineWrap(false);
        jTA.setWrapStyleWord(false);
        //mainSP = new JScrollPane(mainArea);
        this.setViewportView(jTA);
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        hasFile = false;
        changed = false;
        readOnly = false;
        languageMapName = "";
    }
    public CCText(File textFile) {
        super();
        jTA = new CCTextArea("",20,80);
        //jTA = new JTextArea("");
        //jTA.setFont(new Font("Courier New", Font.PLAIN, 14));
        jTA.setFont(new Font("Euphemia", Font.PLAIN, 14));
        jTA.setLineWrap(false);
        jTA.setWrapStyleWord(false);
        //mainSP = new JScrollPane(mainArea);
        this.setViewportView(jTA);
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        file = textFile;
        hasFile = true;
        changed = false;
        readOnly = Validation.isReadOnly(file);
        setReadOnly(readOnly);
        languageMapName = "";
    }

    public void setReadOnly(boolean isReadOnly) {
        jTA.setEditable(!isReadOnly);
        
    }

    /*
    public JScrollPane getScrollPane() {
        return mainSP;
    }
     */
    
    public CCTextArea getJTA() {
        return jTA;
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
    
    private void saveTextToFile(String text) {
        if (changesFileName.equals("")) {
            changesFileName = StringUtil.generateRandomString(16)+".tmp";
        }
        createTempFile(text);
    }
    
    private void createTempFile(String text) {
        CCFile tempFile = new CCFile();
        String tempFilename = changesFileName;
        File temporaryFile = new File(tempFilename);
        try {
            if (!temporaryFile.exists()) {
                temporaryFile.createNewFile();
            }
        } catch (IOException ex) {
            Logger.getLogger(CCText.class.getName()).log(Level.SEVERE, null, ex);
        }
        tempFile.openFile(temporaryFile);
        tempFile.setText(text);
        tempFile.saveFile(temporaryFile);
    }
    
    private void cleanupTempFiles() {
        
    }
    
    private final static int BITMASK_1=1;
    private final static int BITMASK_HAS_FILE=          BITMASK_1;
    private final static int BITMASK_IS_CHANGED=        BITMASK_1 << 1;
    private final static int BITMASK_HAS_LANGUAGE_MAP=  BITMASK_1 << 2;
    
    public void processConfig(String toString) {
        if(toString.equals("")) {
            return;
        }
        String[] infoFields = toString.split(StringUtil.ATAB);
        int bitValues = Integer.valueOf(infoFields[0]);
        title = infoFields[1];
        
        File fileToOpen;
        if (BITMASK_HAS_FILE == (bitValues & BITMASK_HAS_FILE)) {
            fileToOpen = new File(infoFields[2]);
            if (!fileToOpen.isFile()) {
                //File no longer where it was, so skip restoring this entry
                return;
            }
            
            CCFile currentFile = new CCFile();
            currentFile.openFile(fileToOpen);
            
            jTA.setText(currentFile.getText());
            setFile(fileToOpen);
            changed = false;
            unsaved = false;
        }
        
        
        if (BITMASK_IS_CHANGED == (bitValues & BITMASK_IS_CHANGED)) {
            String changedText;
            File changeFile = new File(infoFields[3]);
            CCFile changedFile = new CCFile();
            changedFile.openFile(changeFile);
            
            changedText = changedFile.getText();
            
            jTA.setText(changedText);
        }
        
        if (BITMASK_HAS_LANGUAGE_MAP == (bitValues & BITMASK_HAS_LANGUAGE_MAP)) {
            if (infoFields[4].length() > 0) {
                jTA.loadLocalCharacterMap(infoFields[4]);
                jTA.disableLanguageMap();
            }
        }
        
    }
    
    @Override
    public void setLocale(Locale l) {
        CCTextArea.setDefaultLocale(l);
    }
    
    public String toConfig() {
        String result = "";
        int bitmask = 0;
        boolean hasLanguageMap = jTA.hasLanguageMap();
        
        bitmask |= hasFile ? BITMASK_HAS_FILE : 0;
        bitmask |= changed ? BITMASK_IS_CHANGED : 0;
        bitmask |= hasLanguageMap ? BITMASK_HAS_LANGUAGE_MAP : 0;
        
        result += bitmask + StringUtil.ATAB + title;
        result += hasFile ? StringUtil.ATAB + file.getAbsolutePath() : StringUtil.ATAB;
        //result += changed ? StringUtil.ATAB + createTempFile(jTA.getText()) : StringUtil.ATAB;
        
        
        if (changed) {
            saveTextToFile(jTA.getText());
            result += StringUtil.ATAB + changesFileName;
        } else {
            result += StringUtil.ATAB;
        }
        
        result += hasLanguageMap ? StringUtil.ATAB + jTA.getLanguageMapName() : StringUtil.ATAB;
        
        return result;
    }
    
    public String getTemporaryChangesFileName() {
        return changesFileName;
    }
}