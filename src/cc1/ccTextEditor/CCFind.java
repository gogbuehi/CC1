/*
 * CCFind.java
 *
 * Created on April 20, 2006, 2:24 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package cc1.ccTextEditor;

import Utilities.TestInfo;
import Utilities.WildcardSegment;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.Highlight;

/**
 *
 * @author goodwin.ogbuehi
 */
public class CCFind implements CaretListener,ActionListener {
    JButton btnFind,btnReplace,btnReplaceAll;
    JTextField jtfFind,jtfReplace;
    JLabel jlFind;
    JTextArea jta;
    JPanel jp;
    WildcardSegment ws;
    
    int currIndex;
    Highlighter.HighlightPainter oldHighlightPainter;
    int [] lastHighlight;
    
    boolean blnFind;
    
    /** Creates a new instance of CCFind */
    public CCFind() {
        jp = new JPanel(new FlowLayout());
        btnFind = new JButton("Find Next");
        btnReplace = new JButton("Replace");
        btnReplaceAll = new JButton("Relpace All");
        jtfFind = new JTextField(20);
        jtfReplace = new JTextField(20);
        jlFind = new JLabel("Find:");
        jta = new JTextArea();
        
        jtfFind.addCaretListener(this);
        jtfFind.addActionListener(this);
        
        jtfReplace.addActionListener(this);
        jtfReplace.setActionCommand("BTNREPLACE");
        jtfReplace.setVisible(false);
        
        btnFind.addActionListener(this);
        btnFind.setActionCommand("BTNFIND");
        
        btnReplace.addActionListener(this);
        btnReplace.setActionCommand("BTNREPLACE");
        btnReplace.setVisible(false);
        
        btnReplaceAll.addActionListener(this);
        btnReplaceAll.setActionCommand("BTNREPLACEALL");
        btnReplaceAll.setVisible(false);
        
        jp.add(jlFind);
        jp.add(jtfFind);
        jp.add(jtfReplace);
        jp.add(btnFind);
        jp.add(btnReplace);
        jp.add(btnReplaceAll);
        
        
        currIndex = 0;
        lastHighlight = new int [2];
        
        blnFind = true;
    }
    
    public void setJTA(JTextArea jta) {
        this.jta = jta;
    }
    
    public JTextField getJTF() {
        return jtfFind;
    }
    
    public void requestFocus() {
        jtfFind.requestFocus();
    }
    
    public void caretUpdate(CaretEvent e) {
        //Color [] clrArray = new Color [2];
        //Color color = Color.RED;
        //jtfFind.setBackground(color);
        //searchAndSelect();
        
        processWildcards();
        TestInfo.testWriteLn("Caret Update");
    }
    
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if (actionCommand.equals("BTNFIND")) {
            currIndex = jta.getCaretPosition();
            //searchAndSelectNext();
            processWildcardsNext();
        }
        else if (actionCommand.equals("BTNREPLACE")) {
            //currIndex = jta.getSelectionStart();
            //searchAndSelectNext();
            processWildcardsNext();
            replace();
        }
        else if (actionCommand.equals("BTNREPLACEALL")) {
            listReplace();
        }
        else {
            //searchAndSelectNext();
            processWildcardsNext();
        }
    }
    private void listReplace() {
        String [] oldArray;
        String [] newArray;
        String strOld = jtfFind.getText();
        String strNew = jtfReplace.getText();
        if (strOld.equals("") || strNew.equals("")) {
            return;
        }
        newArray = strNew.split("\\Q,\\E");
        oldArray = strOld.split("\\Q,\\E");
        String inText = jta.getText();
        String regex;
        if (newArray.length != oldArray.length) {
            TestInfo.testWriteLn("ERROR: Length does not match.");
            return;
        }
        for (int i = 0; i < oldArray.length; i++) {
            regex = "\\Q" + oldArray[i] + "\\E";
            inText = inText.replaceAll(regex,newArray[i]);
        }
        jta.setText(inText);
    }
    private void searchAndSelect() {
        //TestInfo.testWriteLn("Index: " + currIndex);
        currIndex = 0;
        Highlighter h = jta.getHighlighter();
        h.removeAllHighlights();
        String text = jtfFind.getText();
        
        String jText = jta.getText();
        if (text.equals("")) {
            jtfFind.setBackground(Color.WHITE);
        }
        else {
            //TestInfo.testWriteLn("Plain Text: " + text);
            text = processEscapeChars(text);
            //TestInfo.testWriteLn("Escape Text: " + text);
            int index = jText.toLowerCase().indexOf(text.toLowerCase());
            if (index == -1) {
                jtfFind.setBackground(Color.RED);
            }
            else {
                try {
                    currIndex = index + 1;
                    // An instance of the private subclass of the default highlight painter
                    Highlighter.HighlightPainter myHighlightPainter = new MyHighlightPainter(Color.YELLOW);
                    oldHighlightPainter = myHighlightPainter;
                    h.addHighlight(index,index+text.length(),myHighlightPainter);
                    jtfFind.setBackground(Color.WHITE);
                    jta.setSelectionColor(Color.CYAN);
                    //jta.selectAll();
                    jta.select(index,index+text.length());
                    lastHighlight[0] = index;
                    lastHighlight[1] = index + text.length();
                    //jta.setSelectionEnd(index + text.length());
                }
                catch (BadLocationException e) {
                    //Do nothing
                }
            }
        }
    }
    
    private void searchAndSelectNext() {
        TestInfo.testWriteLn("Next Index: " + currIndex);
        Highlighter h = jta.getHighlighter();
        //h.removeAllHighlights();
        String text = jtfFind.getText();
        String jText = jta.getText();
        if (text.equals("")) {
            jtfFind.setBackground(Color.WHITE);
        }
        else if (currIndex != jText.length()) {
            //TestInfo.testWriteLn("Plain Text: " + text);
            text = processEscapeChars(text);
            //TestInfo.testWriteLn("Escape Text: " + text);
            int index = jText.toLowerCase().indexOf(text.toLowerCase(),currIndex);
            if (index == -1) {
                jtfFind.setBackground(Color.RED);
                currIndex = 0;
            }
            else {
                try {
                    currIndex = index + 1;
                    TestInfo.testWriteLn("Index test: " + currIndex);
                    // An instance of the private subclass of the default highlight painter
                    Highlighter.HighlightPainter oldHighlightPainter = new MyHighlightPainter(Color.LIGHT_GRAY);
                    Highlighter.HighlightPainter myHighlightPainter = new MyHighlightPainter(Color.YELLOW);
                    //oldHighlightPainter
                    Highlight [] hls = h.getHighlights();
                    int offsetStart,offsetEnd;
                    for (int i = 0; i < hls.length; i++) {
                        offsetStart = hls[i].getStartOffset();
                        offsetEnd = hls[i].getEndOffset();
                        if ((offsetStart == index) && (offsetEnd == (index+text.length()))) {
                            h.removeHighlight(hls[i]);
                        }
                        if (i == (hls.length - 1)) {
                            
                            h.removeHighlight(hls[i]);
                            h.addHighlight(lastHighlight[0],lastHighlight[1],oldHighlightPainter);
                        }
                        //oldHighlightPainter = hls[i].getPainter();
                        //oldHighlightPainter.paint(Color.GRAY);
                    }
                    
                    //h.removeHighlight(oldHighlightPainter);
                    //h.changeHighlight(oldHighlightPainter,lastHighlight[0],lastHighlight[1]);
                    
                    h.addHighlight(index,index+text.length(),myHighlightPainter);
                    jtfFind.setBackground(Color.WHITE);
                    jta.setSelectionColor(Color.CYAN);
                    
                    //jta.selectAll();
                    jta.select(index,index+text.length());
                    //jta.setSelectionEnd(index + text.length());
                    lastHighlight[0] = index;
                    lastHighlight[1] = index + text.length();
                    //oldHighlightPainter = myHighlightPainter;
                }
                catch (BadLocationException e) {
                    //Do nothing
                }
            }
        }
    }
    
    public JPanel getUI() {
        return jp;
    }
    
    public void setCurrIndex(int index) {
        currIndex = index;
    }
    
    public void setReplace() {
        if (blnFind) {
            //jp.remove()
            
            //jp.remove(btnFind);
            //jp.add(jtfReplace);
            jtfReplace.setVisible(true);
            //btnFind.setText("Replace:");
            jlFind.setText("Replace:");
            //btnFind.setActionCommand("BTNREPLACE");
            //jp.add(btnFind);
            btnReplace.setVisible(true);
            btnReplaceAll.setVisible(true);
            //jp.add(btnReplaceAll);
            jp.repaint();
            blnFind = false;
        }
    }
    public void setFind() {
        if (!blnFind) {
            //jp.remove()
            
            //jp.remove(jtfReplace);
            jtfReplace.setVisible(false);
            //jp.add(jtfReplace,2);
            //btnFind.setText("Find:");
            jlFind.setText("Find:");
            //btnFind.setActionCommand("BTNFIND");
            btnReplace.setVisible(false);
            btnReplaceAll.setVisible(false);
            //jp.remove(btnReplaceAll);
            jp.repaint();
            blnFind = true;
            //jp.add(btnReplaceAll);
        }
    }
    
    public boolean replace() {
        String text1 = jtfFind.getText();
        if (!text1.equals("")) {
            //text1 = processEscapeChars(text1);
            text1 = ws.makeFindString();
            String text2 = jtfReplace.getText();
            if (!text2.equals("")) {
                text2 = processEscapeChars(text2);
                TestInfo.testWriteLn("Text2: " + text2);
            }
            String mainText = jta.getText();
            int indexStart = jta.getSelectionStart();
            TestInfo.testWriteLn("Selected Text: " + jta.getSelectedText());
            String subText;
            if (text1.equals("")) {
                return false;
            }
            else {
                try {
                    //subText = mainText.substring(indexStart,text1.length()+indexStart);
                    //if (subText.equals(text1)) {
                    //    jta.setSelectionEnd(text1.length()+indexStart);
                        jta.replaceSelection(text2);
                    //}
                    return true;
                }
                catch (IndexOutOfBoundsException ex) {
                    return false;
                }
            }
        }
        return false;
    }
    
    public String processEscapeChars(String text) {
        text = text.replace("^^","~!~");
        text = text.replace("^p","\n");
        text = text.replace("^t",StringUtil.ATAB);
        text = text.replace("~!~","^");
        return text;
    }
    public String processEscapeChars_w(String text) {
        text = text.replace("^^","~!~");
        text = text.replace("^p","\n");
        text = text.replace("^t",StringUtil.ATAB);
        text = text.replace("~!~","^^");
        return text;
    }
    
    public boolean hasWildcards(String text) {
        if (!text.equals("")) {
            text = text.replace("^^","~!~");
            if (text.indexOf("^#") != -1) {
                return true;
            }
            else if (text.indexOf("^*") != -1) {
                return true;
            }
        }
        return false;
    }
    
    public String validateWildcards(String text) {
        String temp1,temp2;
        
        text = text.replaceAll("\\Q^^\\E","~!~");
        do {
            if (text.indexOf("^*^*") != -1) {
                text = text.replaceAll("\\Q^*^*\\E","^*");
            }
            if (text.indexOf("^*^#") != -1) {
                text = text.replaceAll("\\Q^*^#\\E","^*");
            }
        }
        while ((text.indexOf("^*^*") != -1) || (text.indexOf("^*^#") != -1));
        text = text.replaceAll("\\Q~!~\\E","^^");
        
        if (text.length() == 2) {
            temp1 = text.substring(0,2);
            temp2 = "";
            if (temp1.equals("^*") || temp1.equals("^#")) {
                text = temp2;
            }
        }
        else if (text.length() > 2) {
            temp1 = text.substring(0,2);
            temp2 = text.substring(2);
            if (temp1.equals("^*") || temp1.equals("^#")) {
                text = temp2;
            }
        }
        
        return text;
    }
    
    public void processWildcards() {
        //TestInfo.testWriteLn("Index: " + currIndex);
        ws = new WildcardSegment();
        WildcardSegment next;
        int sw,mw; //Single Wildcard, Multi-Wildcard
        currIndex = 0;
        int ci = -1;
        Highlighter h = jta.getHighlighter();
        h.removeAllHighlights();
        String text = jtfFind.getText();
        
        String jText = jta.getText();
        if (text.equals("")) {
            jtfFind.setBackground(Color.WHITE);
        }
        else {
            if (hasWildcards(text)) {
                int index = 0;
                int currIndex_w = currIndex;
                boolean tryAgain = false;
                text = processEscapeChars_w(text);
                text = validateWildcards(text);
                
                ws.setStart(0);
                next = ws.next;
                
                text = text.replace("^^","~!~");
                
                do {
                    sw = text.indexOf("^#");
                    mw = text.indexOf("^*");
                    
                    if ((sw != -1) && (mw != -1)) {
                        if (sw < mw) {
                            text = text.replaceFirst("\\Q^#\\E","`#");
                            if (sw == 0) {
                                ws.type = 1;
                            }
                            else {
                                next.setStart(sw,1);
                                index = next.start;
                                next = next.next;
                            }
                        }
                        else {
                            text = text.replaceFirst("\\Q^*\\E","`*");
                            if (mw == 0) {
                                ws.type = 2;
                            }
                            else {
                                next.setStart(mw,2);
                                index = next.start;
                                next = next.next;
                            }
                        }
                    }
                    else if (sw != -1) {
                        text = text.replaceFirst("\\Q^#\\E","`#");
                        if (sw == 0) {
                            ws.type = 1;
                        }
                        else {
                            next.setStart(sw,1);
                            index = next.start;
                            next = next.next;
                        }
                    }
                    else {
                        text = text.replaceFirst("\\Q^*\\E","`*");
                        if (mw == 0) {
                            ws.type = 2;
                        }
                        else {
                            next.setStart(mw,2);
                            index = next.start;
                            next = next.next;
                        }
                    }
                    
                    sw = text.indexOf("^#");
                    mw = text.indexOf("^*");
                    if ((index != text.length() - 2) && (sw != (index + 2)) && (mw != (index + 2))) {
                       next.setStart(index+2);
                       index = next.start;
                       next = next.next;
                    }
                }
                while ((sw != -1) || (mw != -1));
                
                next = ws;
                while(next.start != -1) {
                    switch (next.type) {
                        case 0:
                            if (next.next.type == -1) {
                                next.seg = text.substring(next.start);
                            }
                            else {
                                next.seg = text.substring(next.start,next.next.start);
                            }
                            break;
                        case 1:
                            next.seg = "~`~";
                            break;
                        case 2:
                            next.seg = "`~`";
                            break;
                        default:
                            //Do nothing
                            break;
                    }
                    next = next.next;
                }
                //text = text.replace("~!~","^");
                next = ws;
                boolean keepLooking = true;
                int bSpace,bTab,bNewline,bIndex;
                while(next.start != -1) {
                    switch (next.type) {
                        case 0:
                            index = jText.toLowerCase().indexOf(next.seg.toLowerCase(),currIndex_w);
                            if (index == -1) {
                                keepLooking = false;
                            }
                            else {
                                if (next.start == 0) {
                                    //currIndex = index + 1;
                                    if (ci == -1) {
                                        ci = index + 1;
                                    }
                                }
                                if (ci == -1) {
                                    ci = index + 1;
                                }
                                if (next.next.start != -1) {
                                    currIndex_w = index + next.seg.length();
                                }
                                else {
                                    currIndex_w = index + 1;
                                }
                            }
                            break;
                        case 1:
                            //index = jText.toLowerCase().indexOf(next.seg.toLowerCase(),currIndex_w);
                            if (currIndex_w != jText.length()) {
                                index = currIndex_w;
                            }
                            else {
                                index = -1;
                            }
                            if (index == -1) {
                                keepLooking = false;
                            }
                            else {
                                if (next.start == 0) {
                                    //currIndex = index + 1;
                                    if (ci == -1) {
                                        ci = index + 1;
                                    }
                                }
                                if (ci == -1) {
                                    ci = index + 1;
                                }
                                if (next.next.start != -1) {
                                    next.seg = String.valueOf(jText.charAt(currIndex_w));
                                    currIndex_w = index + next.seg.length();
                                }
                                else {
                                    next.seg = String.valueOf(jText.charAt(currIndex_w));
                                    currIndex_w = index + 1;
                                }
                            }
                            break;
                        case 2:
                            
                            if (next.next.start != -1) {
                                bIndex = jText.toLowerCase().indexOf(next.next.seg.toLowerCase(),currIndex_w);
                                bSpace = jText.toLowerCase().indexOf(StringUtil.ASPACE,currIndex_w);
                                bTab = jText.toLowerCase().indexOf(StringUtil.ATAB,currIndex_w);
                                bNewline = jText.toLowerCase().indexOf("\n",currIndex_w);
                                if ((bSpace != -1) && ((bSpace < bIndex) || (bIndex == -1))) {
                                    bIndex = -1;
                                }
                                
                                if ((bTab != -1) && ((bTab < bIndex) || (bIndex == -1))) {
                                    bIndex = -1;
                                }
                                if ((bNewline != -1) && ((bNewline < bIndex) || (bIndex == -1))) {
                                    bIndex = -1;
                                }
                                index = bIndex;
                                if ((currIndex_w != jText.length()) && (index == -1)) {
                                    tryAgain = true;
                                }
                            }
                            else {
                                bSpace = jText.toLowerCase().indexOf(StringUtil.ASPACE,currIndex_w);
                                bTab = jText.toLowerCase().indexOf(StringUtil.ATAB,currIndex_w);
                                bNewline = jText.toLowerCase().indexOf("\n",currIndex_w);
                                
                                bIndex = bSpace;
                                if ((bTab != -1) && ((bTab < bIndex) || (bIndex == -1))) {
                                    bIndex = bTab;
                                }
                                if ((bNewline != -1) && ((bNewline < bIndex) || (bIndex == -1))) {
                                    bIndex = bNewline;
                                }
                                index = bIndex;
                                if ((currIndex_w != jText.length()) && (index == -1)) {
                                    index = jText.length();
                                }
                            }
                            
                            if (index == -1) {
                                keepLooking = false;
                            }
                            else {
                                next.seg = jText.substring(currIndex_w,index);
                                if (next.start == 0) {
                                    //currIndex = index + 1;
                                    if (ci == -1) {
                                        ci = index + 1;
                                    }
                                }
                                if (ci == -1) {
                                    ci = index + 1;
                                }
                                if (next.next.start != -1) {
                                    currIndex_w = index + next.seg.length();
                                }
                                else {
                                    currIndex_w = index + next.seg.length();
                                }
                            }
                            break;
                        default:
                            //Do nothing
                            break;
                    }
                    if (keepLooking) {
                        next = next.next;
                    }
                    else {
                        if (tryAgain) {
                            next = ws;
                            tryAgain = false;
                            keepLooking = true;
                        }
                        else {
                            break;
                        }
                    }
                }
                /*
                TestInfo.testWriteLn("Find String: " + ws.makeFindString());
                TestInfo.testWriteLn("Find Type: " + ws.makeFindType());
                TestInfo.testWriteLn("Find Start: " + ws.makeFindStart());
                 */
                if (keepLooking) {
                    TestInfo.testWriteLn("String Found!");
                }
                else {
                    TestInfo.testWriteLn("String Not Found!");
                }
                
                //Do search
                text = ws.makeFindString();
                index = jText.toLowerCase().indexOf(text.toLowerCase(),currIndex);
                if (index == -1) {
                    jtfFind.setBackground(Color.RED);
                }
                else {
                    try {
                        currIndex = ci;
                        // An instance of the private subclass of the default highlight painter
                        Highlighter.HighlightPainter myHighlightPainter = new MyHighlightPainter(Color.YELLOW);
                        oldHighlightPainter = myHighlightPainter;
                        h.addHighlight(index,index+text.length(),myHighlightPainter);
                        jtfFind.setBackground(Color.WHITE);
                        jta.setSelectionColor(Color.CYAN);
                        //jta.selectAll();
                        jta.select(index,index+text.length());
                        lastHighlight[0] = index;
                        lastHighlight[1] = index + text.length();
                        //jta.setSelectionEnd(index + text.length());
                    }
                    catch (BadLocationException e) {
                        //Do nothing
                    }
                }
            }
            else {
                text = processEscapeChars(text);
            
                int index = jText.toLowerCase().indexOf(text.toLowerCase());
                if (index == -1) {
                    jtfFind.setBackground(Color.RED);
                }
                else {
                    try {
                        currIndex = index + 1;
                        // An instance of the private subclass of the default highlight painter
                        Highlighter.HighlightPainter myHighlightPainter = new MyHighlightPainter(Color.YELLOW);
                        oldHighlightPainter = myHighlightPainter;
                        h.addHighlight(index,index+text.length(),myHighlightPainter);
                        jtfFind.setBackground(Color.WHITE);
                        jta.setSelectionColor(Color.CYAN);
                        //jta.selectAll();
                        jta.select(index,index+text.length());
                        lastHighlight[0] = index;
                        lastHighlight[1] = index + text.length();
                        //jta.setSelectionEnd(index + text.length());
                    }
                    catch (BadLocationException e) {
                        //Do nothing
                    }
                }
            }
        }
    }
    public void processWildcardsNext() {
        //TestInfo.testWriteLn("Index: " + currIndex);
        ws = new WildcardSegment();
        WildcardSegment next;
        int sw,mw; //Single Wildcard, Multi-Wildcard
        //currIndex = jta.getSelectionStart();
        int ci = -1;
        Highlighter h = jta.getHighlighter();
        h.removeAllHighlights();
        String text = jtfFind.getText();
        
        String jText = jta.getText();
        if (text.equals("")) {
            jtfFind.setBackground(Color.WHITE);
        }
        else {
            if (hasWildcards(text)) {
                int index = 0;
                int currIndex_w = currIndex;
                boolean tryAgain = false;
                text = processEscapeChars_w(text);
                text = validateWildcards(text);
                
                ws.setStart(0);
                next = ws.next;
                
                text = text.replace("^^","~!~");
                
                do {
                    sw = text.indexOf("^#");
                    mw = text.indexOf("^*");
                    
                    if ((sw != -1) && (mw != -1)) {
                        if (sw < mw) {
                            text = text.replaceFirst("\\Q^#\\E","`#");
                            if (sw == 0) {
                                ws.type = 1;
                            }
                            else {
                                next.setStart(sw,1);
                                index = next.start;
                                next = next.next;
                            }
                        }
                        else {
                            text = text.replaceFirst("\\Q^*\\E","`*");
                            if (mw == 0) {
                                ws.type = 2;
                            }
                            else {
                                next.setStart(mw,2);
                                index = next.start;
                                next = next.next;
                            }
                        }
                    }
                    else if (sw != -1) {
                        text = text.replaceFirst("\\Q^#\\E","`#");
                        if (sw == 0) {
                            ws.type = 1;
                        }
                        else {
                            next.setStart(sw,1);
                            index = next.start;
                            next = next.next;
                        }
                    }
                    else {
                        text = text.replaceFirst("\\Q^*\\E","`*");
                        if (mw == 0) {
                            ws.type = 2;
                        }
                        else {
                            next.setStart(mw,2);
                            index = next.start;
                            next = next.next;
                        }
                    }
                    
                    sw = text.indexOf("^#");
                    mw = text.indexOf("^*");
                    if ((index != text.length() - 2) && (sw != (index + 2)) && (mw != (index + 2))) {
                       next.setStart(index+2);
                       index = next.start;
                       next = next.next;
                    }
                }
                while ((sw != -1) || (mw != -1));
                
                next = ws;
                while(next.start != -1) {
                    switch (next.type) {
                        case 0:
                            if (next.next.type == -1) {
                                next.seg = text.substring(next.start);
                            }
                            else {
                                next.seg = text.substring(next.start,next.next.start);
                            }
                            break;
                        case 1:
                            next.seg = "~`~";
                            break;
                        case 2:
                            next.seg = "`~`";
                            break;
                        default:
                            //Do nothing
                            break;
                    }
                    next = next.next;
                }
                //text = text.replace("~!~","^");
                next = ws;
                boolean keepLooking = true;
                int bSpace,bTab,bNewline,bIndex;
                while(next.start != -1) {
                    switch (next.type) {
                        case 0:
                            index = jText.toLowerCase().indexOf(next.seg.toLowerCase(),currIndex_w);
                            if (index == -1) {
                                keepLooking = false;
                            }
                            else {
                                if (next.start == 0) {
                                    if (ci == -1) {
                                        ci = index + 1;
                                    }
                                }
                                
                                if (next.next.start != -1) {
                                    currIndex_w = index + next.seg.length();
                                }
                                else {
                                    currIndex_w = index + 1;
                                }
                            }
                            break;
                        case 1:
                            //index = jText.toLowerCase().indexOf(next.seg.toLowerCase(),currIndex_w);
                            if (currIndex_w != jText.length()) {
                                index = currIndex_w;
                            }
                            else {
                                index = -1;
                            }
                            if (index == -1) {
                                keepLooking = false;
                            }
                            else {
                                if (next.start == 0) {
                                    if (ci == -1) {
                                        ci = index + 1;
                                    }
                                }
                                if (ci == -1) {
                                    ci = index + 1;
                                }
                                if (next.next.start != -1) {
                                    next.seg = String.valueOf(jText.charAt(currIndex_w));
                                    currIndex_w = index + next.seg.length();
                                }
                                else {
                                    next.seg = String.valueOf(jText.charAt(currIndex_w));
                                    currIndex_w = index + 1;
                                }
                            }
                            break;
                        case 2:
                            
                            if (next.next.start != -1) {
                                bIndex = jText.toLowerCase().indexOf(next.next.seg.toLowerCase(),currIndex_w);
                                bSpace = jText.toLowerCase().indexOf(StringUtil.ASPACE,currIndex_w);
                                bTab = jText.toLowerCase().indexOf(StringUtil.ATAB,currIndex_w);
                                bNewline = jText.toLowerCase().indexOf("\n",currIndex_w);
                                if ((bSpace != -1) && ((bSpace < bIndex) || (bIndex == -1))) {
                                    bIndex = -1;
                                }
                                
                                if ((bTab != -1) && ((bTab < bIndex) || (bIndex == -1))) {
                                    bIndex = -1;
                                }
                                if ((bNewline != -1) && ((bNewline < bIndex) || (bIndex == -1))) {
                                    bIndex = -1;
                                }
                                index = bIndex;
                                if ((currIndex_w != jText.length()) && (index == -1)) {
                                    tryAgain = true;
                                }
                            }
                            else {
                                bSpace = jText.toLowerCase().indexOf(StringUtil.ASPACE,currIndex_w);
                                bTab = jText.toLowerCase().indexOf(StringUtil.ATAB,currIndex_w);
                                bNewline = jText.toLowerCase().indexOf("\n",currIndex_w);
                                
                                bIndex = bSpace;
                                if ((bTab != -1) && ((bTab < bIndex) || (bIndex == -1))) {
                                    bIndex = bTab;
                                }
                                if ((bNewline != -1) && ((bNewline < bIndex) || (bIndex == -1))) {
                                    bIndex = bNewline;
                                }
                                index = bIndex;
                                if (currIndex_w != jText.length()) {
                                    index = jText.length();
                                }
                            }
                            
                            if (index == -1) {
                                keepLooking = false;
                            }
                            else {
                                next.seg = jText.substring(currIndex_w,index);
                                if (next.start == 0) {
                                    
                                }
                                if (ci == -1) {
                                    ci = index + 1;
                                }
                                if (next.next.start != -1) {
                                    currIndex_w = index + next.seg.length();
                                }
                                else {
                                    currIndex_w = index + next.seg.length();
                                }
                            }
                            break;
                        default:
                            //Do nothing
                            break;
                    }
                    if (keepLooking) {
                        next = next.next;
                    }
                    else {
                        if (tryAgain) {
                            next = ws;
                            tryAgain = false;
                            keepLooking = true;
                        }
                        else {
                            break;
                        }
                    }
                }
                TestInfo.testWriteLn("Find String: " + ws.makeFindString());
                TestInfo.testWriteLn("Find Type: " + ws.makeFindType());
                TestInfo.testWriteLn("Find Start: " + ws.makeFindStart());
                if (keepLooking) {
                    TestInfo.testWriteLn("String Found!");
                }
                else {
                    TestInfo.testWriteLn("String Not Found!");
                }
                
                //Do search
                text = ws.makeFindString();
                index = jText.toLowerCase().indexOf(text.toLowerCase(),currIndex);
                if (index == -1) {
                    jtfFind.setBackground(Color.RED);
                    currIndex = 0;
                }
                else {
                    try {
                        currIndex = ci;
                        // An instance of the private subclass of the default highlight painter
                        Highlighter.HighlightPainter myHighlightPainter = new MyHighlightPainter(Color.YELLOW);
                        oldHighlightPainter = myHighlightPainter;
                        h.addHighlight(index,index+text.length(),myHighlightPainter);
                        jtfFind.setBackground(Color.WHITE);
                        jta.setSelectionColor(Color.CYAN);
                        //jta.selectAll();
                        jta.select(index,index+text.length());
                        lastHighlight[0] = index;
                        lastHighlight[1] = index + text.length();
                        //jta.setSelectionEnd(index + text.length());
                    }
                    catch (BadLocationException e) {
                        //Do nothing
                    }
                }
            }
            else {
                text = processEscapeChars(text);
            
                int index = jText.toLowerCase().indexOf(text.toLowerCase(),currIndex);
                if (index == -1) {
                    jtfFind.setBackground(Color.RED);
                }
                else {
                    try {
                        currIndex = index + 1;
                        // An instance of the private subclass of the default highlight painter
                        Highlighter.HighlightPainter myHighlightPainter = new MyHighlightPainter(Color.YELLOW);
                        oldHighlightPainter = myHighlightPainter;
                        h.addHighlight(index,index+text.length(),myHighlightPainter);
                        jtfFind.setBackground(Color.WHITE);
                        jta.setSelectionColor(Color.CYAN);
                        //jta.selectAll();
                        jta.select(index,index+text.length());
                        lastHighlight[0] = index;
                        lastHighlight[1] = index + text.length();
                        //jta.setSelectionEnd(index + text.length());
                    }
                    catch (BadLocationException e) {
                        //Do nothing
                    }
                }
            }
        }
    }
    
    // A private subclass of the default highlight painter
    //http://javaalmanac.com/egs/javax.swing.text/style_HiliteWords.html
    class MyHighlightPainter extends DefaultHighlighter.DefaultHighlightPainter {
        public MyHighlightPainter(Color color) {
            super(color);
        }
    }
}
