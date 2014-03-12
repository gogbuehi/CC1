/*
 * JTabTracker.java
 *
 * Created on January 8, 2006, 1:43 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package cc1.TabUtilities;

import Utilities.TestInfo;
import cc1.CCText;
import cc1.CCTextTabs;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Document;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

/**
 *
 * @author Goodwin
 */
public final class JTabTracker implements DocumentListener  {
    CCTextTabs ccTT;
    CCText cct;
    JTextArea jta;
    Document doc;
    String tabTitle;
    //boolean changed,readOnly,unsaved;
    //String languageMapName;
    
    UndoManager undo = new UndoManager();
    MyUndoableEditListener myel;
    boolean canUndo = false;
    boolean canRedo = false;
    /** Creates a new instance of JTabTracker */
    public JTabTracker() {
    }
    
    public JTabTracker(CCTextTabs ccTT) {
        this.ccTT = ccTT;
        cct = (CCText) ccTT.getSelectedComponent();
        jta = cct.getJTA();
        doc = jta.getDocument();
        doc.addDocumentListener(this);
        //doc.addMyUndoableEditListener(this);
        myel = new MyUndoableEditListener();
        cct.undo = undo;
        doc.addUndoableEditListener(myel);
        getCurrentTitle();
        //tabTitle = ccTT.getTitleAt(ccTT.getSelectedIndex());
        
    }
    
    public void setJTT(CCTextTabs ccTT) {
        if (!ccTT.defaultHold) {
            cct.undo = undo;
            
            cct = (CCText) ccTT.getSelectedComponent();
            
            jta = cct.getJTA();
            
            doc.removeDocumentListener(this);
            
            doc.removeUndoableEditListener(myel);
            doc = jta.getDocument();
            doc.addDocumentListener(this);
            if (cct.undo == null) {
                TestInfo.testWriteLn("New Undo");
                cct.undo = new UndoManager();
            } else {
                TestInfo.testWriteLn("Old Undo");
            }
            undo = (UndoManager) cct.undo;
            doc.addUndoableEditListener(myel);
            //tabTitle = ccTT.getTitleAt(ccTT.getSelectedIndex());
            getCurrentTitle();
            //tabTitle = ccTT.file.getName();
        }
    }
    public void setJTT() {
        if (ccTT.getTabCount() > 0) {
            /*
            cct = (CCText) ccTT.getSelectedComponent();
            jta = cct.getJTA();
            doc.removeDocumentListener(this);
            doc = jta.getDocument();
            doc.addDocumentListener(this);
            tabTitle = ccTT.getTitleAt(ccTT.getSelectedIndex());
            //tabTitle = ccTT.file.getName();
             */
            cct.undo = undo;
            
            cct = (CCText) ccTT.getSelectedComponent();
            jta = cct.getJTA();
            
            doc.removeDocumentListener(this);
            
            doc.removeUndoableEditListener(myel);
            doc = jta.getDocument();
            doc.addDocumentListener(this);
            undo = (UndoManager) cct.undo;
            doc.addUndoableEditListener(myel);
            //tabTitle = ccTT.getTitleAt(ccTT.getSelectedIndex());
            getCurrentTitle();
            //tabTitle = ccTT.file.getName();
        }
    }
    
    public void updateTabTitle() {
        String tempTitle = cct.title;
        if (cct.readOnly) {
            tempTitle = "[-" + tempTitle + "-]";
        }
        if (cct.unsaved) {
            tempTitle = "~" + tempTitle + "~";
        }
        if (cct.changed) {
            tempTitle = tempTitle + "*";
        }
        if (!cct.languageMapName.equals("")) {
            tempTitle = tempTitle + "(" + cct.languageMapName + ")";
        }
        ccTT.setTitleAt(ccTT.getSelectedIndex(),tempTitle);
    }
    
    public void docChanged() {
        cct.changed = true;
        updateTabTitle();
    }
    public void docUnchanged() {
        cct.changed = false;
        updateTabTitle();
    }
    public void docReadOnly() {
        cct.readOnly = true;
        updateTabTitle();
    }
    public void docReadOnly(boolean tf) {
        cct.readOnly = tf;
        updateTabTitle();
    }
    public void docUnsaved() {
        cct.unsaved = true;
        updateTabTitle();
    }
    public void docLanguageMap(String languageMapName) {
        cct.languageMapName = languageMapName;
        updateTabTitle();
    }
    
    public void getCurrentTitle() {
        tabTitle = ccTT.getTitleAt(ccTT.getSelectedIndex());
    }
    
    public void insertUpdate(DocumentEvent e) {
        //docReadOnly();
        //updateLog(e, "inserted into");
    }
    public void removeUpdate(DocumentEvent e) {
        //updateLog(e, "removed from");
    }
    public void changedUpdate(DocumentEvent e) {
        //Plain text components don't fire these events
        docChanged();
    }
    
    //This one listens for edits that can be undone.
    protected class MyUndoableEditListener
            implements UndoableEditListener {
        public void undoableEditHappened(UndoableEditEvent e) {
            //Remember the edit and update the menus.
            undo.addEdit(e.getEdit());
            docChanged();
            //undoAction.updateUndoState();
            canUndo = undo.canUndo();
            canRedo = undo.canRedo();
            
            //redoAction.updateRedoState();
        }
    }
    
    public void undo() {
        try {
            undo.undo();
        } catch (CannotUndoException e) {
            System.out.println("Unable to undo: " + e);
        }
    }
    
    public void redo() {
        try {
            undo.redo();
        } catch (CannotRedoException e) {
            System.out.println("Unable to redo: " + e);
        }
    }
    /*
    public void undoableEditHappened(UndoableEditEvent e) {
        docChanged();
    }
     */
}
