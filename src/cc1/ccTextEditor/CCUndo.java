/*
 * CCUndo.java
 *
 * Created on January 28, 2006, 5:31 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package cc1.ccTextEditor;

import javax.swing.JTextArea;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Document;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

/**
 *
 * @author Goodwin
 */
public class CCUndo {
    JTextArea jTA;
    Document doc;

    //undo helpers
    //protected UndoAction undoAction;
    //protected RedoAction redoAction;
    protected UndoManager undo = new UndoManager();
    /** Creates a new instance of CCUndo */
    public CCUndo(JTextArea newJTA) {
        jTA = newJTA;
        doc = jTA.getDocument();
        doc.addUndoableEditListener(new MyUndoableEditListener());
    }
    
    public void undo() {
        try {
            undo.undo();
        }
        catch (CannotUndoException e) {
            System.out.println("Unable to undo: " + e);
        }
    }
    
    public void redo() {
        try {
            undo.redo();
        }
        catch (CannotUndoException e) {
            System.out.println("Unable to redo: " + e);
        }
    }
    
    /*
    class UndoAction extends AbstractAction {
        public UndoAction() {
            super("Undo");
            //setEnabled(false);
        }

        public void actionPerformed(ActionEvent e) {
            try {
                undo.undo();
            } catch (CannotUndoException ex) {
                System.out.println("Unable to undo: " + ex);
                //ex.printStackTrace();
            }
            //updateUndoState();
            //redoAction.updateRedoState();
        }

        protected void updateUndoState() {
            if (undo.canUndo()) {
                //setEnabled(true);
                //putValue(Action.NAME, undo.getUndoPresentationName());
            } else {
                //setEnabled(false);
                //putValue(Action.NAME, "Undo");
            }
        }
    }

    class RedoAction extends AbstractAction {
        public RedoAction() {
            super("Redo");
            //setEnabled(false);
        }

        public void actionPerformed(ActionEvent e) {
            try {
                undo.redo();
            } catch (CannotRedoException ex) {
                System.out.println("Unable to redo: " + ex);
                //ex.printStackTrace();
            }
            //updateRedoState();
            //undoAction.updateUndoState();
        }
        protected void updateRedoState() {
            if (undo.canRedo()) {
                //setEnabled(true);
                //putValue(Action.NAME, undo.getRedoPresentationName());
            } else {
                //setEnabled(false);
                //putValue(Action.NAME, "Redo");
            }
        }
    }
    */
    //This one listens for edits that can be undone.
    protected class MyUndoableEditListener
                    implements UndoableEditListener {
        public void undoableEditHappened(UndoableEditEvent e) {
            //Remember the edit and update the menus.
            undo.addEdit(e.getEdit());
            //undoAction.updateUndoState();
            //redoAction.updateRedoState();
        }
    }
}
