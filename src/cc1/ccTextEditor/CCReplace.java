/*
 * CCReplace.java
 *
 * Created on January 13, 2006, 3:42 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package cc1.ccTextEditor;

import Utilities.TestInfo;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

/**
 *
 * @author goodwin.ogbuehi
 */
public class CCReplace {
    public boolean processing;
    JTextArea jTA;
    /** Creates a new instance of CCReplace */
    public CCReplace() {
    }
    
    /** Creates a new instance of CCReplace
     *  <p>Specifies the default JTextArea to manipulate.
     */
    public CCReplace(JTextArea newJTA) {
        jTA = newJTA;
        processing = false;
    }
    
    public JTextArea getJTA() {
        return jTA;
    }
    
    public void setJTA(JTextArea newJTA) {
        jTA = newJTA;
    }
    
    /** Retrieve the bounds of the current selection.
     *  <p>Returns a 2-element int array that has the start and end position of the selection.
     */
    public int [] getSelectionBounds() {
        int [] bounds = new int [2];
        bounds[0] = jTA.getSelectionStart();
        bounds[1] = jTA.getSelectionEnd();
        return bounds;
    }
    
    /** Calculates the new bounds for a selection, after text has been replaced.
     *  <p>Returns a 2-element int array that has the new start and end position of the selection.
     *  <ul><li>String <b>text</b>: The text that will replace the old selection.
     *  <li>int <b>pos</b>: The start position of the original selection.
     *  </ul>
     */
    public int [] calcSelectionBounds(String text, int pos) {
        int [] bounds = new int [2];
        bounds[0] = pos;
        bounds[1] = pos + text.length();
        return bounds;
    }
    
    /** Sets the bounds of a selection, in the jTA.
     *  <p><b>bounds</b> must be a 2-element int array,
     *  <br>where the first element is the start position for the selection
     *  <br>and the second element is the end position for the selection.
     */
    public void setSelectionBounds(int [] bounds) {
       jTA.setSelectionStart(bounds[0]);
       jTA.setSelectionEnd(bounds[1]);
    }
    
    public int getRow(int offset) {
        int line;
        try {
            line = jTA.getLineOfOffset(offset);
            return line;
        }
        catch (BadLocationException e) {
            TestInfo.testWriteLn("Error: getRow(): Bad Location Exception");
            return 0;
        }
    }
    
    /** <b>VBString</b> changes the selected text into text formatted as a VB String
     *  <p>The 
     */
    public void vbs() {
        processing();
        int [] bounds = getSelectionBounds();
        int [] newBounds;
        if (bounds[0] != bounds[1]) {
            String text = jTA.getSelectedText();
            int row = getRow(bounds[0]);
            String fullLineText = TabHold.getLine(jTA,row);
            String firstWhitespace = StringUtil.getIndent(fullLineText);
            firstWhitespace = StringUtil.formatIndent(firstWhitespace);
            String newlineReplace = "\" & vbCrLf &_\n" + firstWhitespace + "\"";
            String output = "\"";

            text = text.replace("\"","\"\"");
            text = text.replace("&#8220;","\"\"");
            text = text.replace("&#8221;","\"\"");
            output += text.replace(StringUtil.newline,newlineReplace) + "\"";

            jTA.replaceSelection(output);
            newBounds = calcSelectionBounds(output,bounds[0]);
            setSelectionBounds(newBounds);
        }
        else {
            String text = "\"\"";
            String output = text;
            jTA.replaceSelection(output);
            newBounds = calcSelectionBounds(output,bounds[0]);
            setSelectionBounds(newBounds);
        }
        end();
    }
    public void vbsh() {
        processing();
        int [] bounds = getSelectionBounds();
        int [] newBounds;
        if (bounds[0] != bounds[1]) {
            String text = jTA.getSelectedText();
            int row = getRow(bounds[0]);
            String fullLineText = TabHold.getLine(jTA,row);
            String firstWhitespace = StringUtil.getIndent(fullLineText);
            firstWhitespace = StringUtil.formatIndent(firstWhitespace);
            String newlineReplace = "\" & vbCrLf &_\n" + firstWhitespace + "\"<br />";
            String output = "\"";
            text = text.replace("\"","\"\"");
            //text = text.replace("�","\"\""); //For Special Quotes put in by MS documents
            //text = text.replace("�","\"\"");
        
            output += text.replace(StringUtil.newline,newlineReplace) + "\"";
            output = output.replace("<br /><ul>","<ul>");
            output = output.replace("<br /><ol>","<ol>");
            output = output.replace("<br /></ul>","</ul>");
            output = output.replace("<br /></ol>","</ol>");
            output = output.replace("<br /><li>","<li>");

            jTA.replaceSelection(output);
            newBounds = calcSelectionBounds(output,bounds[0]);
            setSelectionBounds(newBounds);
        }
        else {
            String text = "\"\"";
            String output = text;
            jTA.replaceSelection(output);
            newBounds = calcSelectionBounds(output,bounds[0]);
            setSelectionBounds(newBounds);
        }
        end();
    }
    
    public void vbh() {
        processing();
        int [] bounds = getSelectionBounds();
        int [] newBounds;
        if (bounds[0] != bounds[1]) {
            String text = jTA.getSelectedText();
            int row = getRow(bounds[0]);
            String fullLineText = TabHold.getLine(jTA,row);
            String firstWhitespace = StringUtil.getIndent(fullLineText);
            firstWhitespace = StringUtil.formatIndent(firstWhitespace);
            String newlineReplace = "\" & vbCrLf &_\n" + firstWhitespace + "\"<br />";
            String output = "\"";
            text = text.replace("\"","\"\"");
            //text = text.replace("�","\"\""); //For special quotes put in by MS Word
            //text = text.replace("�","\"\"");
        
            output += text.replace(StringUtil.newline,newlineReplace) + "\"";
            output = output.replace("<br /><ul>","<ul>");
            output = output.replace("<br /><ol>","<ol>");
            output = output.replace("<br /></ul>","</ul>");
            output = output.replace("<br /></ol>","</ol>");
            output = output.replace("<br /><li>","<li>");

            jTA.replaceSelection(output);
            newBounds = calcSelectionBounds(output,bounds[0]);
            setSelectionBounds(newBounds);
        }
        else {
            String text = "\"\"";
            String output = text;
            jTA.replaceSelection(output);
            newBounds = calcSelectionBounds(output,bounds[0]);
            setSelectionBounds(newBounds);
        }
        end();
    }
    private void processing() {
        processing = true;
        TestInfo.testWriteLn("CCReplacing...");
    }
    private void end() {
        processing = false;
        TestInfo.testWriteLn("CCR Done.");
    }
}
