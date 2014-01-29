/*
 * TabHold.java
 *
 * Created on June 23, 2005, 2:28 PM
 */

package cc1.ccTextEditor;

import Utilities.TestInfo;
import cc1.CCText;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

/**
 *
 * @author  goodwin.ogbuehi
 */
public class TabHold {
    static final int TABWIDTH = 8;
    //static final String newline = "\n";
    //static final String ATAB = String.valueOf('\u0009');
    //static final String ASPACE = " ";
    
    //int row, col;
    JTextArea jTA;
    
    /** Creates a new instance of TabHold */
    public TabHold() {
        
    }
    
    public TabHold(CCText cct) {
        jTA = cct.getJTA();
        //row = 1;
        //col = 1;
    }
    
    /** Get the text of the line where the cursor is at */
    public String getLine() {
        /*
         int line,lineStart,lineEnd;
        String ret;
        try {
            line = jTA.getLineOfOffset(jTA.getCaretPosition());
            lineStart = jTA.getLineStartOffset(line);
            lineEnd = jTA.getLineEndOffset(line);
            //lineEnd += checkForNewline(jTA.getText().charAt(lineEnd-1));
            ret = jTA.getText().substring(lineStart,lineEnd);
            TestInfo.testWriteLn("getLine(): Success");
        }
        catch (BadLocationException e) {
            TestInfo.testWriteLn("Error: getLine(): Bad Location Exception");
            return "";
        }
        return ret;
         */
        int line;
        try {
            line = jTA.getLineOfOffset(jTA.getCaretPosition());
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
            lineStart = jTA.getLineStartOffset(line);
            lineEnd = jTA.getLineEndOffset(line);
            //lineEnd += checkForNewline(jTA.getText().charAt(lineEnd));
            if (lineStart == lineEnd) {
                ret = "";
            }
            else if(String.valueOf(jTA.getText().charAt(lineEnd-1)).equals(StringUtil.newline)) {
                ret = jTA.getText().substring(lineStart,lineEnd-1);
            }
            else {
                ret = jTA.getText().substring(lineStart,lineEnd);
            }
            //TestInfo.testWriteLn("getLine(): Success");
            //TestInfo.testWriteLn(ret);
            //TestInfo.testWriteLn("Start: " + lineStart);
            //TestInfo.testWriteLn("End: " + lineEnd);
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
            //lineEnd += checkForNewline(jTA.getText().charAt(lineEnd));
            if (lineStart == lineEnd) {
                ret = "";
            }
            else if(String.valueOf(jTA.getText().charAt(lineEnd-1)).equals(StringUtil.newline)) {
                ret = jTA.getText().substring(lineStart,lineEnd-1);
            }
            else {
                ret = jTA.getText().substring(lineStart,lineEnd);
            }
            //TestInfo.testWriteLn("getLine(): Success");
            //TestInfo.testWriteLn(ret);
            //TestInfo.testWriteLn("Start: " + lineStart);
            //TestInfo.testWriteLn("End: " + lineEnd);
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
            lineStart = jTA.getLineStartOffset(line);
            lineEnd = jTA.getLineEndOffset(line);
            if (lineStart == lineEnd) {
                //ret = "";
                //Do nothing
                jTA.replaceRange(text,lineStart,lineEnd);
            }
            else if(String.valueOf(jTA.getText().charAt(lineEnd-1)).equals(StringUtil.newline)) {
                //ret = jTA.getText().substring(lineStart,lineEnd-1);
                jTA.replaceRange(text,lineStart,lineEnd-1);
            }
            else {
                //ret = jTA.getText().substring(lineStart,lineEnd);
                jTA.replaceRange(text,lineStart,lineEnd);
            }
            
            
            //jTA.replaceRange(text+newline,lineStart,lineEnd);
        }
        catch (BadLocationException e) {
            //return "";
        }
    }
    public void setLine(String text) {
        int line;
        try {
            line = jTA.getLineOfOffset(jTA.getCaretPosition());
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
        /*String strLine = getLine();
        String strWhite = getIndent(strLine);
        strLine = strLine.substring(strWhite.length());
        strLine = whiteSpace + strLine;
        setLine(strLine);
         */
    }
    
    /** Set the whitespace that is the prefix of the line the cursor is at */
    public void setIndent(String whiteSpace, int line) {
        //int line = getRow();
        String strLine = getLine(line);
        String strWhite = StringUtil.getIndent(strLine);
        //TestInfo.testWriteLn("Indent for line " + String.valueOf(line) + " |~" + strWhite);
        //TestInfo.testWriteLn("Indent from line " + String.valueOf(line-1) + " |~" + whiteSpace);
        strLine = strLine.substring(strWhite.length());
        //TestInfo.testWriteLn("Text for line " + String.valueOf(line) + " |~" + strLine);
        strLine = whiteSpace + strLine;
        setLine(strLine,line);
    }
    
    
    /** Gets the row that the cursor is at */
    public int getRow() {
        try {
            return jTA.getLineOfOffset(jTA.getCaretPosition());
        }
        catch (BadLocationException e) {
            return -1;
        }
    }
    
    /** Gets the row that the cursor is at */
    public int getCol() {
        int pos,row,rowOffset;
        try {
            pos = jTA.getCaretPosition();
            row = getRow();
            rowOffset = jTA.getLineStartOffset(row);
            return (pos-rowOffset);
        }
        catch (BadLocationException e) {
            return -1;
        }
    }
    
    public int getCalculatedCol() {
        int pos,row,rowOffset,calcCol;
        String text = jTA.getText();
        try {
            calcCol = 0;
            pos = jTA.getCaretPosition();
            row = getRow();
            rowOffset = jTA.getLineStartOffset(row);
            //calcCol = pos - rowOffset;
            /*
            for (col = 0; col < thisLine.length(); col++) {
                if (ATAB.equals(String.valueOf(thisLine.charAt(col)))) {
                    colCount = colCount + (tabWidth - colCount%tabWidth) + 1;
                }
                else
                    colCount++;
            }
             */
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
    
    /** Set the JTextArea associated with this TabHold */
    public void setJTA(JTextArea newJTA) {
        jTA = newJTA;
    }
    
    /** Get the JTextArea associated with this TabHold */
    public JTextArea getJTA() {
        return jTA;
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
            try {
                jTA.setCaretPosition(jTA.getLineStartOffset(line) + strIndent.length());
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
            /*
             try {
                jTA.setCaretPosition(jTA.getLineStartOffset(line)+strIndent.length());
            }
            catch (BadLocationException e) {
                //Do nothing
            }
             */
             
        }
        /*
        if (row > -1) {
            thisLine = getLine(text,row);
            pTabs = countSomething(thisLine);
            if (thisLine.length() >= 5) {
                if (thisLine.substring(0,5).toLowerCase().equals(caseText)) {
                    if (row > 0) {
                        if (prevLineCheck(getLine(text,row-1))) {
                            lineText.setText("Process Case");
                            processCase();
                        }
                        else
                            lineText.setText("Don't Process Case");
                    }
                    else {
                        lineText.setText("Process Case");
                        processCase();
                    }
                }
            }
         */
        //jTA.append("|+|" + String.valueOf(line) + "|+|");
    }
    public void processCase() {
        //Used in conjuction with processNewLine()
        String caseBreak = StringUtil.ATAB + "'****************************" + StringUtil.newline;
        int currentLine = getRow();
        try {
            int lineOffset = jTA.getLineStartOffset(getRow()-1);
            jTA.insert(caseBreak,lineOffset);
            //jTA.setCaretPosition(lineOffset + caseBreak.length());
            jTA.setCaretPosition(jTA.getLineStartOffset(currentLine+1) + 1);
            //pos += caseBreak.length();
        }
        catch(BadLocationException e) {
            //Do nothing;
        }
    }
    
    /*
    public void setRowCol() {
        int pos = jTA.getCaretPosition();
        row = getRow();
        col = getCol();
        if (row == -1)
            row = 0;
        if (col == -1)
            col = 0;
    }
    
    public void testText() {
        int lineCount;
        processNewline();
        lineCount = jTA.getLineCount();
        //TestInfo.testWriteLn("----Testing Text----");
        for (int i = 0; i < lineCount; i++) {
            //setIndent(ATAB,i);
            
            //TestInfo.testWriteLn("Line-" + String.valueOf(i));
            //TestInfo.testWriteLn(getLine(i));
        }
    }
    /*
    public TabHold() {
        jTA = new JTextArea("",20,80);
        //jTA.addKeyListener(this);
        tabs = 0;
        tabWidth = 8;
    }
    
    /*
    public TabHold(JTextArea newJTA) {
        jTA = newJTA;
        //jTA.addKeyListener(this);
        tabs = 0;
        tabWidth = 8;
        
        //jTA = cc.mainTA;
        //testTabs = new JLabel();
        rows = new JLabel();
        cols = new JLabel();;
        row = 1;
        col = 1;
        pos = 0;
        lineText = new JLabel();
        tabWidth = 8;
    }
     *
    
    public TabHold(CCText newJTA) {
        jTA = newJTA.getJTA();
        //jTA.addKeyListener(this);
        tabs = 0;
        tabWidth = 8;
        
        //jTA = cc.mainTA;
        //testTabs = new JLabel();
        rows = new JLabel();
        cols = new JLabel();;
        row = 1;
        col = 1;
        pos = 0;
        lineText = new JLabel();
        tabWidth = 8;
    }
    
    public int countTabs(String lineStr) {
        int tabs = 0;
        for (int i = 0; i < lineStr.length(); i++) {
            if (ATAB.equals(String.valueOf(lineStr.charAt(i))))
                tabs++;
        }
        return tabs;
    }
    public int countSomething(String thisLine) {
        //int pos = jTA.getCaretPosition();
        //int offset = getRowOffset(text,row);
        //int tempCol = pos - offset;
        //String thisLine = text.substring(offset,pos);
        int colCount=0;
        for (int i = 0; i < thisLine.length(); i++) {
            if (ATAB.equals(String.valueOf(thisLine.charAt(i))))
                colCount = colCount + (tabWidth - colCount%tabWidth);
            else if (ASPACE.equals(String.valueOf(thisLine.charAt(i))))
                colCount++;
            else
                break;
        }
        return colCount;
    }
    
    public void processTabs(String text) {
        tabs = countTabs(text);
        String tString = "Tabs: " + String.valueOf(tabs);
        //testTabs.setText(tString);
    }
    
    public void processCols(String text) {
        int colCount = getCurrentCol(text);
        //col = pos - getRowOffset(text,row) + ((tabWidth-1)*(countTabs(getLine(text.substring(0,pos),row)))) + 1;
        col = colCount;
        String cLabel = "Col: " + String.valueOf(col);
        cols.setText(cLabel);
    }
    private int getCurrentCol(String text) {
        pos = jTA.getCaretPosition();
        int offset = getRowOffset(text,row);
        int tempCol = pos - offset;
        String thisLine = text.substring(offset,pos);
        int colCount=1;
        for (col = 0; col < thisLine.length(); col++) {
            if (ATAB.equals(String.valueOf(thisLine.charAt(col)))) {
                colCount = colCount + (tabWidth - colCount%tabWidth) + 1;
            }
            else
                colCount++;
        }
        return colCount;
    }
    public void processRows(String text) {
        pos = jTA.getCaretPosition();
        String temp = text.substring(0,pos);
        row = rowCounter(temp);
        String rLabel = "Row: " + String.valueOf(row+1);
        rows.setText(rLabel);
    }
    private int rowCounter(String lineStr) {
        int rows = 0;
        for (int i = 0; i < lineStr.length(); i++) {
            if (newline.equals(String.valueOf(lineStr.charAt(i))))
                rows++;
        }
        return rows;
    }
    
    private int getRowOffset(String lineStr, int row) {
        int i;
        int rows = 0;
        if (row < 1) {
            i = 0;
        }
        else {
            for (i = 0; (i < lineStr.length()) && rows < row; i++) {
                if (newline.equals(String.valueOf(lineStr.charAt(i))))
                    rows++;
            }
        }
        if (i == 0)
            i = 0;
        return i;
    }
    
    private String getLine(String lineStr, int row) {
        int start, end;
        start = getRowOffset(lineStr,row);
        end = getRowOffset(lineStr,row + 1);
        if (start == end)
            return "";
        return lineStr.substring(start,end);
    }
    
    private int countStartTabs(String rowStr) {
        int whiteSpaceCount = 0;
        int finalCount = 0;
        String wsStr = "";
        for (int i = 0; i < rowStr.length(); i++) {
            if (ATAB.equals(String.valueOf(rowStr.charAt(i))))
                ;
            else if (ASPACE.equals(String.valueOf(rowStr.charAt(i))))
                ;
            else {
                if (i == 0)
                    wsStr = "";
                else
                    wsStr = rowStr.substring(0,(i-1));
                break;
            }
        }
        finalCount = getCurrentCol(wsStr);
        return finalCount;
    }
    
    private String addTabs(int finalCount) {
        int tabCount,spaceCount;
        String spaceStr = "";
        spaceCount = finalCount%tabWidth;
        tabCount = (finalCount-spaceCount)/tabWidth;
        for (int i = 0; i < tabCount; i++)
            spaceStr += ATAB;
        for (int i = 0; i < spaceCount; i++)
            spaceStr += ASPACE;
        return spaceStr;
    }
    
    public void processNewline(String text) {
        //String thisLine = getLine(text,row);
        //int row = rowCounter(text) + 1;
        //row++;
        pos = jTA.getCaretPosition();
        String thisLine;
        int pTabs = 0;
        String caseText = ATAB + "case";
        if (row > -1) {
            thisLine = getLine(text,row);
            pTabs = countSomething(thisLine);
            if (thisLine.length() >= 5) {
                if (thisLine.substring(0,5).toLowerCase().equals(caseText)) {
                    if (row > 0) {
                        if (prevLineCheck(getLine(text,row-1))) {
                            lineText.setText("Process Case");
                            processCase();
                        }
                        else
                            lineText.setText("Don't Process Case");
                    }
                    else {
                        lineText.setText("Process Case");
                        processCase();
                    }
                }
            }
            
            jTA.insert(addTabs(pTabs),pos);
        }
        
        /*thisLine = "Tabs: " + rowCounter(text);
        if (thisLine.length() == 0)
            thisLine = " ";
         *
        lineText.setText(thisLine); *
    }
    public void processCase() {
        //Used in conjuction with processNewLine()
        String caseBreak = ATAB + "'****************************" + newline;
        try {
            int lineOffset = jTA.getLineStartOffset(row);
            jTA.insert(caseBreak,lineOffset);
            //jTA.setCaretPosition(pos + caseBreak.length() - 1);
            pos += caseBreak.length();
        }
        catch(BadLocationException e) {
            //Do nothing;
        }
    }
    
    public boolean prevLineCheck(String prevLine) {
        String apostrophe = "'";
        String star = "*";
        String thisChar;
        boolean comment = false;
        for (int i = 0; i < prevLine.length(); i++) {
            thisChar = String.valueOf(prevLine.charAt(i));
            if (
                    ((ATAB.equals(thisChar)) || 
                    (ASPACE.equals(thisChar))) &&
                !comment)
                ;
            else if ( (apostrophe.equals(thisChar)) &&
                    !comment)
                comment = true;
            else if (comment)
                if (
                    (!star.equals(thisChar)) &&
                    (!ASPACE.equals(thisChar)) &&
                    (!ATAB.equals(thisChar)) &&
                    (!apostrophe.equals(thisChar))
                )
                    return false;
        }
        return true;
    }
    
    public JTextArea getJTA() {
        return jTA;
    }
    
    public void setJTA(JTextArea newJTA) {
        jTA = newJTA;
    }
     */
}