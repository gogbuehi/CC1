/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc1.ccTextEditor;

import Utilities.TestInfo;
import ccLanguageUtils.TypingMonitor;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

/**
 *
 * @author Goodwin
 */
public class CCTextArea extends JTextArea{
    static final int TABWIDTH = 8;
    protected TypingMonitor mTypingMonitor;
    //static final String newline = "\n";
    //static final String ATAB = String.valueOf('\u0009');
    //static final String ASPACE = " ";
    
    //int row, col;
    
    /** Creates a new instance of TabHold */
    public CCTextArea(String s1, int rows, int cols) {
        super(s1,rows,cols);
        mTypingMonitor = new TypingMonitor();
        
    }
    
    /** Get the text of the line where the cursor is at */
    public String getLine() {
        int line;
        try {
            line = this.getLineOfOffset(this.getCaretPosition());
            TestInfo.testWriteLn("Line: " + line);
            return getLine(line);
        }
        catch (BadLocationException e) {
            TestInfo.testWriteLn("Error: getLine(): Bad Location Exception");
            return "";
        }
    }
    
    /** Get the text of the line specified */
    public String getLine(int line) {
        int lineStart,lineEnd;
        String ret;
        try {
            lineStart = this.getLineStartOffset(line);
            lineEnd = this.getLineEndOffset(line);
            if (lineStart == lineEnd) {
                ret = "";
            }
            else if(String.valueOf(this.getText().charAt(lineEnd-1)).equals(StringUtil.newline)) {
                ret = this.getText().substring(lineStart,lineEnd-1);
            }
            else {
                ret = this.getText().substring(lineStart,lineEnd);
            }
        }
        catch (BadLocationException e) {
            TestInfo.testWriteLn("Error: getLine(int): Bad Location Exception");
            return "";
        }
        TestInfo.testWriteLn("Text: " + ret);
        return ret;
    }
    
    /** Get the text of the line specified */
    public static String getLine(JTextArea newJTA, int line) {
        JTextArea jTA = newJTA;
        int lineStart,lineEnd;
        String ret;
        try {
            lineStart = jTA.getLineStartOffset(line);
            lineEnd = jTA.getLineEndOffset(line);
            if (lineStart == lineEnd) {
                ret = "";
            }
            else if(String.valueOf(jTA.getText().charAt(lineEnd-1)).equals(StringUtil.newline)) {
                ret = jTA.getText().substring(lineStart,lineEnd-1);
            }
            else {
                ret = jTA.getText().substring(lineStart,lineEnd);
            }
        }
        catch (BadLocationException e) {
            TestInfo.testWriteLn("Error: getLine(int): Bad Location Exception");
            return "";
        }
        return ret;
    }
    
    private static int checkForNewline(char c) {
        if (String.valueOf(c).equals(StringUtil.newline)) {
            return -1;
        }
        return 0;
    }
    
    public void setLine(String text, int line) {
        int lineStart,lineEnd;
        try {
            lineStart = this.getLineStartOffset(line);
            lineEnd = this.getLineEndOffset(line);
            if (lineStart == lineEnd) {
                //Do nothing
                this.replaceRange(text,lineStart,lineEnd);
            }
            else if(String.valueOf(this.getText().charAt(lineEnd-1)).equals(StringUtil.newline)) {
                this.replaceRange(text,lineStart,lineEnd-1);
            }
            else {
                this.replaceRange(text,lineStart,lineEnd);
            }
        }
        catch (BadLocationException e) {
            //return "";
        }
    }
    public void setLine(String text) {
        int line;
        try {
            line = this.getLineOfOffset(this.getCaretPosition());
            setLine(text,line);
        }
        catch (BadLocationException e) {
            //return "";
        }
    }
    
    
    
    
    
    /** Get the whitespace that is the prefix of the given line of text */
    public String getIndent(int line) {
        return StringUtil.getIndent(getLine(line));
    }
    
    /** Set the whitespace that is the prefix of the line the cursor is at */
    public void setIndent(String whiteSpace) {
        int line = getRow();
        setIndent(whiteSpace,line);
    }
    
    /** Set the whitespace that is the prefix of the line the cursor is at */
    public void setIndent(String whiteSpace, int line) {
        String strLine = getLine(line);
        String strWhite = StringUtil.getIndent(strLine);
        strLine = strLine.substring(strWhite.length());
        strLine = whiteSpace + strLine;
        setLine(strLine,line);
    }
    
    public void setSpecialCharacters(int line) {
        if (line >= 0) {
            String strLine = getLine(line);
            String newLineText = mTypingMonitor.processLineOfTextFullPass(strLine);
            TestInfo.testWriteLn("New Line: "+newLineText);
            setLine(newLineText,line);
            
        }
        
    }
    
    
    /** Gets the row that the cursor is at */
    public int getRow() {
        try {
            return this.getLineOfOffset(this.getCaretPosition());
        }
        catch (BadLocationException e) {
            return -1;
        }
    }
    
    /** Gets the row that the cursor is at */
    public int getCol() {
        int pos,row,rowOffset;
        try {
            pos = this.getCaretPosition();
            row = getRow();
            rowOffset = this.getLineStartOffset(row);
            return (pos-rowOffset);
        }
        catch (BadLocationException e) {
            return -1;
        }
    }
    
    public int getCalculatedCol() {
        int pos,row,rowOffset,calcCol;
        String text = this.getText();
        try {
            calcCol = 0;
            pos = this.getCaretPosition();
            row = getRow();
            rowOffset = this.getLineStartOffset(row);
            for (int i = 0; i < (pos - rowOffset); i++) {
                if (String.valueOf(text.charAt(rowOffset + i)).equals(StringUtil.ATAB)) {
                    calcCol += (TABWIDTH - (calcCol%TABWIDTH));
                }
                else {
                    calcCol ++;
                }
            }
            return calcCol;
        }
        catch (BadLocationException e) {
            return -1;
        }
    }
    
    /** Get the JTextArea associated with this TabHold */
    public JTextArea getJTA() {
        return this;
    }
    
    /** Check for Tab Hold from previous line and apply it to the current line */
    public void processNewline() {
        //TestInfo.testWriteLn("----Testing Newline----");
        int line = getRow();
        if (line > 0) {
            //TestInfo.testWriteLn("Processing line " + line);
            String strIndent = getIndent(line-1);
            strIndent = StringUtil.formatIndent(strIndent);
            //TestInfo.testWriteLn("Indent:");
            //TestInfo.testWriteLn(strIndent);
            setIndent(strIndent,line);
            setSpecialCharacters(line-1);
            try {
                this.setCaretPosition(this.getLineStartOffset(line) + strIndent.length());
            }
            catch (BadLocationException e) {
                //Do nothing
            }
            if ((line-1) > 0) {
                String caseBreak = StringUtil.ATAB + "'****************************";
                if (getLine(line-2).equals(caseBreak)) {
                    //Do nothing
                }
                else {
                    if (getLine(line-1).length() >= 5) {
                        if (getLine(line-1).substring(0,5).toLowerCase().equals(StringUtil.ATAB + "case")) {
                            processCase();
                        }
                    }
                }
            }
            else {
                if (getLine(line-1).length() >= 5) {
                    if (getLine(line-1).substring(0,5).toLowerCase().equals(StringUtil.ATAB + "case")) {
                        processCase();
                    }
                }
            }
             
        }
    }
    public void processCase() {
        //Used in conjuction with processNewLine()
        String caseBreak = StringUtil.ATAB + "'****************************" + StringUtil.newline;
        int currentLine = getRow();
        try {
            int lineOffset = this.getLineStartOffset(getRow()-1);
            this.insert(caseBreak,lineOffset);
            this.setCaretPosition(this.getLineStartOffset(currentLine+1) + 1);
        }
        catch(BadLocationException e) {
            //Do nothing;
        }
    }
    
    public void setLanguageMap() {
        mTypingMonitor.loadCharacterMapFromFile();
    }
    
    public void setLanguageMap(String languageMapFileName) {
        mTypingMonitor.loadCharacterMap(languageMapFileName);
    }
    
    public void loadLocalCharacterMap(String languageMapName) {
        mTypingMonitor.loadLocalCharacterMap(languageMapName);
    }
    
    public void disableLanguageMap() {
        mTypingMonitor.disableLanguageMap();
    }
    
    public void enableLanguageMap() {
        mTypingMonitor.enableLanguageMap();
    }
    
    public String getLanguageMapName() {
        return mTypingMonitor.toString();
    }
    
    public boolean hasLanguageMap() {
        return mTypingMonitor.hasLanguageMapLoaded();
    }
    
    public boolean isUsingLanguageMap() {
        return mTypingMonitor.usingCharacterMap();
    }
    
}
