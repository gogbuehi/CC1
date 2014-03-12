/*
 * EventCatcher.java
 *
 * Created on January 5, 2006, 9:21 AM
 */

package cc1;

import CCInfo.CCFileBrowser;
import CCInfo.InfoUI;
import Utilities.TestInfo;
import Utilities.Validation;
import cc1.TabUtilities.JTabTracker;
import cc1.ccTextEditor.CCFind;
import cc1.ccTextEditor.CCReplace;
import cc1.ccTextEditor.CCTextArea;
import cc1.ccTextEditor.ProcessPaste;
import cc1.ccTextEditor.StringUtil;
import ccDialogs.CCDialog;
import ccFileIO.CCFile;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author  goodwin.ogbuehi
 */
public class EventCatcher implements KeyListener, MouseListener, ChangeListener, ClipboardOwner, WindowListener {
    static final String newline = "\n";
    
    CCTextTabs ccTT;
    CCTextArea jTA;
    CCFile ccf;
    
    String text;
    
    JTabTracker jtt;
    CCReplace ccr;
    
    InfoUI iUI;
    CCDialog ccd;
    CCFileBrowser ccFB;
    
    CCFind ccFind;
    
    Config config;
    //CCFrame jf;
    
    boolean enterPressed;
    /** Creates a new instance of EventCatcher */
    public EventCatcher() {
    }
    
    public EventCatcher(CCFrame mainFrame) {
        //jf = mainFrame;
        mainFrame.addKeyListener(this);
        mainFrame.addWindowListener(this);
        ccTT = mainFrame.ccTT;
        CCText cct = (CCText) ccTT.getSelectedComponent();
        ccTT.addKeyListener(this);
        ccf = new CCFile();
        jTA = ccTT.getCurrentJTA();
        jTA.addKeyListener(this);
        jTA.addMouseListener(this);
        ccr = new CCReplace(jTA);
        ccTT.addChangeListener(this);
        jtt = new JTabTracker(ccTT);
        iUI = mainFrame.iUI;
        ccd = new CCDialog(mainFrame);
        ccFB = mainFrame.ccFB;
        if (cct.hasFile) {
            ccFB.makeFileBrowser(cct.getDefaultDir());
        }
        else {
            ccFB.makeFileBrowser(ccf.getDefaultDir());
        }
        //ccFB.setCCFrame(mainFrame);
        ccFB.setEventCather(this);
        
        ccFind = mainFrame.ccf;
        ccFind.setJTA(jTA);
        enterPressed = false;
        
        
    }
    public EventCatcher(CCAppletFrame mainFrame) {
        //jf = mainFrame;
        mainFrame.addKeyListener(this);
        ccTT = mainFrame.ccTT;
        CCText cct = (CCText) ccTT.getSelectedComponent();
        ccTT.addKeyListener(this);
        ccf = new CCFile();
        jTA = ccTT.getCurrentJTA();
        jTA.addKeyListener(this);
        jTA.addMouseListener(this);
        ccr = new CCReplace(jTA);
        ccTT.addChangeListener(this);
        jtt = new JTabTracker(ccTT);
        iUI = mainFrame.iUI;
        ccd = new CCDialog(mainFrame);
        ccFB = mainFrame.ccFB;
        if (cct.hasFile) {
            ccFB.makeFileBrowser(cct.getDefaultDir());
        }
        else {
            ccFB.makeFileBrowser(ccf.getDefaultDir());
        }
        //ccFB.setCCFrame(mainFrame);
        ccFB.setEventCather(this);
        
        ccFind = mainFrame.ccf;
        ccFind.setJTA(jTA);
        enterPressed = false;
        
        
    }
    public void setJTA(CCTextTabs ccTT) {
        
        if (!ccTT.defaultHold) {
            CCText cct = (CCText) ccTT.getSelectedComponent();
            jTA.removeKeyListener(this);
            jTA.removeMouseListener(this);

            jTA = ccTT.getCurrentJTA();
            
            ccTT.setCurrentFile();
           
            jtt.setJTT(ccTT);
            
            //th.setJTA(jTA);
            ccr.setJTA(jTA);
            
            ccFind.setJTA(jTA);

            jTA.addKeyListener(this);
            jTA.addMouseListener(this);
            jTA.requestFocus();
            try {
               //cct.title.toString().toLowerCase();
               if (cct.hasFile) {
                   iUI.setStatus(cct.file.getAbsolutePath());
               }
               else {
                    iUI.setStatus(cct.title);
                }
            }
           catch (NullPointerException e) {
               //Do nothing
           }
        }
        else {
            
        }
    }
    /** Handle the key typed event from the text field. */
    public void keyTyped(KeyEvent e) {
        //text = jTA.getText();
        runTabHold02(e);
    }

    /** Handle the key pressed event from the text field. */
    public void keyPressed(KeyEvent e) {
        String keyString, modString, tmpString,
               actionString, locationString;
        int keyCode = 0;
        int id = e.getID();
        if (id == KeyEvent.KEY_TYPED) {
            char c = e.getKeyChar();
        } else {
            keyCode = e.getKeyCode();
        }
        if (e.isControlDown() && e.isShiftDown()) {
            if (keyCode == KeyEvent.VK_Z) {
                doAction("REDO");
            }
            else if (keyCode == KeyEvent.VK_S) {
                doAction("SAVEAS");
            }
            else if (keyCode == KeyEvent.VK_O) {
                doAction("OPENAS");
            }
        }
        else if (e.isMetaDown()) {
            //TestInfo.testWriteLn("Control down");
            if (keyCode == KeyEvent.VK_N) {
                doAction("NEW");
            }
            else if (keyCode == KeyEvent.VK_S) {
                doAction("SAVE");

            }
            else if (keyCode == KeyEvent.VK_O) {
                doAction("OPEN");
            }
            else if (keyCode == KeyEvent.VK_ESCAPE) {
                //TestInfo.testWriteLn("Should close");
                doAction("CLOSETAB");
            }
            else if (keyCode == KeyEvent.VK_L) {
                if (jTA.hasLanguageMap()) {
                    if (jTA.isUsingLanguageMap()) {
                        doAction("REMOVE_LANGUAGE_MAP");
                    }
                    else {
                        doAction("ENABLE_LAST_LANGUAGE_MAP");
                    }
                }
                else {
                    doAction("SELECT_LANGUAGE_MAP");
                }
            }
            else if (keyCode == KeyEvent.VK_Z) {
                doAction("UNDO");
            }
            else if (keyCode == KeyEvent.VK_F) {
                doAction("FIND");
            }
            else if (keyCode == KeyEvent.VK_R) {
                doAction("REPLACE");
            }
            else if (keyCode == KeyEvent.VK_V) {
                //doAction("VBS");
                //outpArea.setText("Testing VK_V");
                getClipboardContents();
            }

        }
        else if (e.isAltDown() && e.isShiftDown()) {
            if (keyCode == KeyEvent.VK_C) {
                doAction("VBUNCOMMENT");
            }
        }
        else if (e.isAltDown()) {
            if (keyCode == KeyEvent.VK_C) {
                doAction("VBCOMMENT");
            }
        }
        else if (keyCode == KeyEvent.VK_ESCAPE) {
                    //TestInfo.testWriteLn("Should close");
            doAction("CLOSETAB");
        }
        else if (keyCode == KeyEvent.VK_F5) {
            doAction("BASICINDENT");
        }
        else if (keyCode == KeyEvent.VK_F7) {
            doAction("BASICINDENTSELECT");
        }
        else if (keyCode == KeyEvent.VK_F9) {
            doAction("FORMATINDENT");
        }
        else if (keyCode == KeyEvent.VK_F11) {
            doAction("FORMATINDENTSELECT");
        }
        
        
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            //th.processNewline();
            TestInfo.testWriteLn("Entered");
            enterPressed = true;
        }
    }

    /** Handle the key released event from the text field. */
    public void keyReleased(KeyEvent e) {
        
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            //th.processNewline();
            enterPressed = false;
            TestInfo.testWriteLn("Entered:Exit");
        }
        runTabHold01();
    }
    
    public void mousePressed(MouseEvent e) {
       
    }

    public void mouseReleased(MouseEvent e) {
        runTabHold01();
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
       
    }

    public void mouseClicked(MouseEvent e) {
    }
    
    public void stateChanged(ChangeEvent e) {
            setJTA(ccTT);
    }
    private void runTabHold01() {
        //iUI.setRow(th.getRow());
        //iUI.setCol(th.getCalculatedCol());
        
        iUI.setRow(jTA.getRow());
        iUI.setCol(jTA.getCalculatedCol());
    }
    private void runTabHold02(KeyEvent e) {
        int id = e.getID();
        
        if (id == KeyEvent.KEY_TYPED) {
            char c = e.getKeyChar();
            if (newline.equals(String.valueOf(c))) {
                TestInfo.testWriteLn("Status");
                if (enterPressed) {
                    //th.processNewline();
                    jTA.processNewline();
                }
            }
        }
    }
    
    public void performAction(ActionEvent e) {
        String action = e.getActionCommand();
        doAction(action);
    }
    
    public void openListFile(File file) {
        openFunction(file);
    }
    
    public void doAction(String action) {
        if (action.equals("NEW")) {
            ccTT.newTab();
        }
        else if (action.equals("OPEN")) {
            File openFile = ccf.getFileToOpen();
            openFunction(openFile);
        }
        else if (action.equals("OPENAS")) {
            openAsFunction();
        }
        else if (action.equals("SAVEAS")) {
            //ccFIO.FileOutFunction();
            saveAsFunction();
        }
        else if (action.equals("SAVE")) {
            CCText cct = (CCText) ccTT.getSelectedComponent();
            if (cct.hasFile) {
                File saveFile = cct.file;
                saveFunction(saveFile);
            }
            else {
                saveAsFunction();
            }
        }
        else if (action.equals("CLOSETAB")) {
            int tabCount = ccTT.getTabCount();        
            if (tabCount == 1) {
                CCText cct = (CCText) ccTT.getSelectedComponent();
                if (!cct.hasFile && !cct.changed) {
                    //Do nothing if only 1 unsaved, blank tab is left
                }
                else {
                    closeFunction();
                }
            }
            else {
                closeFunction();
            }
        }
        else if (action.equals("SELECT_LANGUAGE_MAP")) {
            jTA.setLanguageMap();
            jtt.docLanguageMap(jTA.getLanguageMapName());
        }
        else if (action.equals("REMOVE_LANGUAGE_MAP")) {
            jTA.disableLanguageMap();
            jtt.docLanguageMap("");
        }
        else if (action.equals("ENABLE_LAST_LANGUAGE_MAP")) {
            jTA.enableLanguageMap();
            jtt.docLanguageMap(jTA.getLanguageMapName());
        }
        else if (action.equals("VBSH")) {
            ccr.vbsh();
        }
        else if (action.equals("VBH")) {
            ccr.vbh();
        }
        else if (action.equals("UNDO")) {
            jtt.undo();
        }
        else if (action.equals("REDO")) {
            jtt.redo();
        }
        else if (action.equals("FIND")) {
            //jtt.redo();
            ccFind.setJTA(jTA);
            ccFind.setFind();
            ccFind.requestFocus();
        }
        else if (action.equals("REPLACE")) {
            //jtt.redo();
            ccFind.setJTA(jTA);
            ccFind.setReplace();
            ccFind.requestFocus();
        }
        else if (action.equals("BASICINDENT")) {
            setClipboardBasicIndent();
        }
        else if (action.equals("FORMATINDENT")) {
            setClipboardFormatIndent();
        }
        else if (action.equals("BASICINDENTSELECT")) {
            setBasicIndent();
        }
        else if (action.equals("FORMATINDENTSELECT")) {
            setFormatIndent();
        }
        else if (action.equals("VBCOMMENT")) {
            vbComment();
        }
        else if (action.equals("VBUNCOMMENT")) {
            vbUnComment();
        }
    }
    
    public void saveFunction(File saveFile) {
        CCText cct = (CCText) ccTT.getSelectedComponent();
        if (ccf.saveFile(ccTT,saveFile)) {
            /*
            jtt.docUnchanged();
            iUI.setStatus(cct.title + " Saved");
             */
            int pos = jTA.getCaretPosition();
            ccTT.newTabThis(saveFile);
            jtt.getCurrentTitle();
            //ccf.updateCCFile(ccTT);
            ccf.setText(ccTT);
            //ccf.writeFile(openFile);
            jtt.docUnchanged();
            jTA.setCaretPosition(pos);
            iUI.setStatus(saveFile.getAbsolutePath() + " Saved");
        }
        else if (Validation.isReadOnly(saveFile)) {
            iUI.setStatusError("Read Only File; Cannot Save");
            saveAsFunction();
        }
        else {
            saveAsFunction();
        }
    }
    
    public void saveAsFunction() {
        File saveFile = ccf.getFileToSave();
        int goToTab = CCFile.checkOpenFile(ccTT,saveFile);
        if (goToTab != -1) {
            //File is already open
            ccd.showAlreadyOpenDialog();
        }
        else if (Validation.isValidFile(saveFile)) {
            if(ccd.showAlreadyExists()) {
                if (ccf.saveFile(saveFile)) {
                    saveFunction(saveFile);
                }
            }
        }
        else {
            if (ccf.saveFile(saveFile)) {
                saveFunction(saveFile);
            }
            
        }
    }
    
    public void openFunction(File openFile) {
        //File openFile = ccf.getFileToOpen();
        if (Validation.isValidFile(openFile)) {
            //Check if this file has already been open
            int goToTab = CCFile.checkOpenFile(ccTT,openFile);
            if (goToTab == -1) {
                //Check if the current tab is a "New" and "Unchanged" text area
                int tabCount = ccTT.getTabCount();
                boolean opened = false;
                if (tabCount > 0) {
                    CCText cct = (CCText) ccTT.getSelectedComponent();
                    if (tabCount == 1) {
                        if (!cct.hasFile && !cct.changed) {
                            openThisFunction(openFile);
                            opened = true;
                        }
                    }
                    if (!opened) {
                        openNewFunction(openFile);
                    }
                }
                jTA.setCaretPosition(0);
            }
            else {
                //Switch tab to goToTab
                goToTab(goToTab);
            }
        }
    }
    
    public void openThisFunction(File openFile) {
        CCText cct = (CCText) ccTT.getSelectedComponent();
        cct.clear();
        ccTT.newTabThis(openFile);
        jtt.getCurrentTitle();
        //ccf.updateCCFile(ccTT);
        ccf.setText(ccTT);
        ccf.writeFile(openFile);
        cct.getJTA().setText(ccf.getText());
        jtt.docUnchanged();
        jtt.docReadOnly(Validation.isReadOnly(ccTT.file));
        setJTA(ccTT);
        ccFB.setLists(openFile.getParentFile());
        //opened = true;
    }
    
    public void openNewFunction(File openFile) {
        ccTT.newTabAfter(openFile);
        //ccf.updateCCFile(ccTT);
        CCText cct = (CCText) ccTT.getSelectedComponent();
        ccf.setText(ccTT);
        ccf.writeFile(openFile);
        cct.getJTA().setText(ccf.getText());
        jtt.docUnchanged();
        jtt.docReadOnly(Validation.isReadOnly(ccTT.file));
        setJTA(ccTT);
        ccFB.setLists(openFile.getParentFile());
        //opened = true;
    }
    
    public void goToTab(int goToTab) {
        ccTT.setSelectedIndex(goToTab);
        ccTT.setCurrentFile();
        setJTA(ccTT);
        ccf.setText(ccTT);
    }
    
    public void openAsFunction() {
        File openFile = ccf.getFileToOpen();
        if (Validation.isValidFile(openFile)) {
            //Check if this file has already been open
            int goToTab = CCFile.checkOpenFile(ccTT,openFile);
            if (goToTab == -1) {
                //Check if the current tab is a "New" and "Unchanged" text area
                int tabCount = ccTT.getTabCount();
                boolean opened = false;
                if (tabCount > 0) {
                    CCText cct = (CCText) ccTT.getSelectedComponent();
                    if (tabCount == 1) {
                        if (!cct.hasFile && !cct.changed) {
                            openThisFunction(openFile);
                            opened = true;
                        }
                    }
                    if (!opened) {
                        openNewFunction(openFile);
                    }
                }
            }
            else {
                goToTab(goToTab);
                CCText cct = (CCText) ccTT.getSelectedComponent();
                if (!cct.changed) {
                    //Do nothing
                }
                else {
                    if (ccd.showDiscardChanges() == CCDialog.YES_OPTION) {
                        openThisFunction(openFile);
                    }
                }
            }
        }
    }
    
    public void closeFunction() {
        CCText cct = (CCText) ccTT.getSelectedComponent();
        if (cct.changed) {
            int intSave = ccd.showSaveChanges();
            if (intSave == CCDialog.NO_OPTION) {
                closeTabFunction();
                ccTT.checkForNoTabs();
            }
            else if (intSave == CCDialog.YES_OPTION) {
                if (cct.hasFile) {
                    File saveFile = cct.file;
                    saveFunction(saveFile);
                    closeTabFunction();
                    ccTT.checkForNoTabs();
                }
                else {
                    saveAsFunction();
                    closeTabFunction();
                    ccTT.checkForNoTabs();
                }
            }
            else {
                //Do nothing
            }
        }
        else {
            closeTabFunction();
            ccTT.checkForNoTabs();
        }
    }
    
    public void closeTabFunction() {
        TestInfo.testWriteLn("Closing Tab");
        int currentTab = ccTT.getSelectedIndex();
        ccTT.closeTab(currentTab);
        //Do not checkForNoTabs, when closing
        //the application
        //ccTT.checkForNoTabs();
    }
    
    public void requestFocus() {
        jTA.requestFocus();
    }
    
    //------------------------------------------
    //Tab Hold Extensions
    public void lostOwnership( Clipboard aClipboard, Transferable aContents) {
     //do nothing
   }

  /**
  * Place a String on the clipboard, and make this class the
  * owner of the Clipboard's contents.
  */
  public void setClipboardContents( String aString ){
    StringSelection stringSelection = new StringSelection( aString );
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    clipboard.setContents( stringSelection, this );
  }
    public String getClipboardContents() {
      String result = "";
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    //odd: the Object param of getContents is not currently used
    Transferable contents = clipboard.getContents(null);
    boolean hasTransferableText =
      (contents != null) &&
      contents.isDataFlavorSupported(DataFlavor.stringFlavor)
    ;
    if ( hasTransferableText ) {
      try {
        result = (String)contents.getTransferData(DataFlavor.stringFlavor);
      }
      catch (UnsupportedFlavorException ex){
        //highly unlikely since we are using a standard DataFlavor
        System.out.println(ex);
      }
      catch (IOException ex) {
        System.out.println(ex);
      }
    }
    //result = StringUtil.processUnicodeToASCII(result);
    String indent = StringUtil.getIndent(jTA.getLine());
    //result = ProcessPaste.formatLine(result,indent);
    //result = ProcessPaste.basicLine(result,indent);
    setClipboardContents(result);
    return result;
  }
    public String setClipboardBasicIndent() {
      String result = "";
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    //odd: the Object param of getContents is not currently used
    Transferable contents = clipboard.getContents(null);
    boolean hasTransferableText =
      (contents != null) &&
      contents.isDataFlavorSupported(DataFlavor.stringFlavor)
    ;
    if ( hasTransferableText ) {
      try {
        result = (String)contents.getTransferData(DataFlavor.stringFlavor);
      }
      catch (UnsupportedFlavorException ex){
        //highly unlikely since we are using a standard DataFlavor
        System.out.println(ex);
      }
      catch (IOException ex) {
        System.out.println(ex);
      }
    }
    //result = StringUtil.processUnicodeToASCII(result);
    String indent = StringUtil.getIndent(jTA.getLine());
    //result = ProcessPaste.formatLine(result,indent);
    result = ProcessPaste.basicLine(result,indent);
    setClipboardContents(result);
    return result;
  }
    public String setClipboardFormatIndent() {
      String result = "";
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    //odd: the Object param of getContents is not currently used
    Transferable contents = clipboard.getContents(null);
    boolean hasTransferableText =
      (contents != null) &&
      contents.isDataFlavorSupported(DataFlavor.stringFlavor)
    ;
    if ( hasTransferableText ) {
      try {
        result = (String)contents.getTransferData(DataFlavor.stringFlavor);
      }
      catch (UnsupportedFlavorException ex){
        //highly unlikely since we are using a standard DataFlavor
        System.out.println(ex);
      }
      catch (IOException ex) {
        System.out.println(ex);
      }
    }
    //result = StringUtil.processUnicodeToASCII(result);
    String indent = StringUtil.getIndent(jTA.getLine());
    result = ProcessPaste.formatLine(result,indent);
    //result = ProcessPaste.basicLine(result,indent);
    setClipboardContents(result);
    return result;
  }
    public void setFormatIndent() {
        if (jTA.getSelectionStart() != jTA.getSelectionEnd()) {
            String result = jTA.getSelectedText();
            String indent = StringUtil.getIndent(jTA.getLine());
            result = ProcessPaste.formatLine(result,indent);
            jTA.replaceSelection(result);
            //result = ProcessPaste.basicLine(result,indent);
        }
    }
    public void setBasicIndent() {
        if (jTA.getSelectionStart() != jTA.getSelectionEnd()) {
            String result = jTA.getSelectedText();
            String indent = StringUtil.getIndent(jTA.getLine());
            result = ProcessPaste.basicLine(result,indent);
            jTA.replaceSelection(result);
        }
    }
    public void vbComment() {
        int startPos = jTA.getSelectionStart();
        if (jTA.getSelectionStart() != jTA.getSelectionEnd()) {
            String result = jTA.getSelectedText();
            result = "'" + result;
            result = result.replace("\n","\n'");
            jTA.replaceSelection(result);
            jTA.setSelectionStart(startPos);
            jTA.setSelectionEnd(startPos + result.length());
        }
        else {
            jTA.insert("'",jTA.getSelectionStart());
            jTA.setSelectionStart(startPos);
            jTA.setSelectionEnd(startPos + 1);
        }
        
    }
    public void vbUnComment() {
        if (jTA.getSelectionStart() != jTA.getSelectionEnd()) {
            int startPos = jTA.getSelectionStart();
            String result = jTA.getSelectedText();
            int firstApos = result.indexOf("'");
            String temp;
            TestInfo.testWriteLn("First Apos: " + firstApos);
            if (firstApos == 0) {
                 result = result.replaceFirst("\\Q'\\E","");
            }
            else if (firstApos > 0) {
                temp = result.substring(0,firstApos);
                if (StringUtil.isWhitespace(temp)) {
                    result = result.replaceFirst("\\Q'\\E","");
                }
            }
            result = result.replace("\n'","\n");
            jTA.replaceSelection(result);
            jTA.setSelectionStart(startPos);
            jTA.setSelectionEnd(startPos + result.length());
        }
        else {
            //jTA.insert("'",jTA.getSelectionStart());
        }
        TestInfo.testWriteLn("Uncommenting...");
    }
    /*
    private void VH4() {
        //Italics
        String txt = getSelectedText();
        String entrText,entrText_1,entrText_2;
        int SStart = entrArea.getSelectionStart();
        int SEnd = entrArea.getSelectionEnd();
        entrText = entrArea.getText();
        if (SStart == SEnd) {
            entrText_1 = entrText.substring(0,SStart);
            entrText_2 = entrText.substring(SStart);
            entrText = entrText_1 + "<i></i>" + entrText_2;
        }
        else {
            entrText_1 = entrText.substring(0,SStart);
            entrText_2 = entrText.substring(SEnd);
            entrText = entrText_1 + "<i>" + txt + "</i>" + entrText_2;
        }
        entrArea.setText(entrText);
    }
     */
    /*
    private String makeHex(int val) {
        String ret = "";
        String [] hexChars = new String [16];
        for (int i = 0; i < 10; i++) {
            hexChars[i] = String.valueOf(i);
        }
        hexChars[10] = "A";
        hexChars[11] = "B";
        hexChars[12] = "C";
        hexChars[13] = "D";
        hexChars[14] = "E";
        hexChars[15] = "F";
        int main,remain;
        while (val > 0) {
            main = val / 16;
            remain = val % 16;
            ret = hexChars[remain] + ret;
            val = main;
        }
        return ret;
    }
    
    private int makeInt(String hex) {
        int ret = 0;
        String [] hexChars = new String [16];
        for (int i = 0; i < 10; i++) {
            hexChars[i] = String.valueOf(i);
        }
        hexChars[10] = "A";
        hexChars[11] = "B";
        hexChars[12] = "C";
        hexChars[13] = "D";
        hexChars[14] = "E";
        hexChars[15] = "F";
        int j;
        int k = 1;
        int l = hex.length() - 1;
        int m = hex.length() - 1;
        for (int i = 0; i < hex.length(); i++) {
            l = m - i;
            for (j = 0; j < hexChars.length; j++) {
                if (String.valueOf(hex.charAt(l)).equals(hexChars[j])) {
                    break;
                }
            }
            ret += k * j;
            k *= 16;
        }
        return ret;
    }
    
    private String processUnicodeToASCII(String input) {
        //String input = entrArea.getText();
        String output = "";
        char [] cArray = input.toCharArray();
        int temp,temp1,temp2,temp3,temp4;
        char cTemp,cTemp1,cTemp2,cTemp3,cTemp4;
        int intVal = 256;
        String strTemp,strGB;
        for (int i = 0; i < cArray.length; i++) {
            strTemp = "";
            temp = Integer.valueOf(cArray[i]);
            temp1 = 0;
            temp2 = 0;
            temp3 = 0;
            temp4 = 0;
            
            if (temp >= intVal) {
                temp1 = temp / intVal;
                temp2 = temp % intVal;
                if (temp1 > intVal) {
                    temp3 = temp1 /intVal;
                    temp1 = temp1 % intVal;
                    if (temp3 > intVal) {
                        temp4 = temp3 / intVal;
                        temp3 = temp3 % intVal;
                    }
                }
                
                /*
                if (temp1 >= 0)
                    //output += String.valueOf(cTemp1);
                    output += makeHex(temp1);
                if (temp2 >= 0)
                    //output += String.valueOf(cTemp2);
                    output += makeHex(temp2);
                 *
                strTemp += makeHex(temp);
                //output += strTemp + "\n";
                //strGB = getGB(strTemp);
                //output += strGB + "\n";
                //temp1 = makeInt(strGB.substring(0,2));
                //temp2 = makeInt(strGB.substring(2));
                temp3 = makeInt(strTemp);
                //cTemp1 =  (char) temp1;
                //cTemp2 = (char) temp2;
                cTemp3 =  (char) temp3;
                cTemp4 = (char) temp4;
                //if (temp1 >= 0)
                    ;//output += String.valueOf(cTemp1);
                    //output += makeHex(temp1);
                //if (temp2 >= 0)
                    ;//output += String.valueOf(cTemp2);
                output += "&#" + String.valueOf(temp3) + ";";
                    //output += makeHex(temp2);
            }
            else {
                cTemp = (char) temp;
                output += String.valueOf(cTemp);
                //output += makeHex(temp) + "\n";
            }
        }
         // *
        //output = Convert.toBase64String(Convert.fromBase64String(input));
        //entrArea.setText(output);
        return output;
    }
     */

    public void windowOpened(WindowEvent we) {
        //throw new UnsupportedOperationException("Not supported yet.");
        config = new Config();
        config.processOpenFiles();
        String[] fileConfigs = config.openFileConfigs;
        CCText tempCCT;
        for(int i = 0; i < fileConfigs.length; i++) {
            TestInfo.testWriteLn("FILE CONFIG " + i + ": " + fileConfigs[i]);
            ccTT.newTab();
            tempCCT = (CCText) ccTT.getSelectedComponent();
            tempCCT.processConfig(fileConfigs[i]);
            //TestInfo.testWriteLn("Split name? " + tempCCT.title);
            if (!tempCCT.changed) {
                jtt.docUnchanged();
            }
            if (tempCCT.jTA.hasLanguageMap()) {
                jTA.enableLanguageMap();
                jtt.docLanguageMap(jTA.getLanguageMapName());
            }
            if (!tempCCT.hasFile) {
                int tabCounter = Integer.parseInt(tempCCT.title.replaceAll("untitled-", ""));
                ccTT.setCounter(tabCounter+1);
                
            }
        }
        ccTT.closeTab(0);
    }

    public void windowClosing(WindowEvent we) {
        config.saveConfig();
        config.saveOpenFiles(ccTT.getAllTextTabConfigs());
        config.cleanupTempFiles(ccTT.getAllChangesFiles());
        //ccTT.getAllTextTabConfigs()
    }

    public void windowClosed(WindowEvent we) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void windowIconified(WindowEvent we) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void windowDeiconified(WindowEvent we) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void windowActivated(WindowEvent we) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void windowDeactivated(WindowEvent we) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
}
