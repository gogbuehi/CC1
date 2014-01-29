/*
 * ProcessPaste.java
 *
 * Created on August 24, 2006, 9:14 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package cc1.ccTextEditor;

/**
 *
 * @author goodwin.ogbuehi
 */
public class ProcessPaste {
    
    /** Creates a new instance of ProcessPaste */
    public ProcessPaste() {
    }
    
    public static String basicLine(String text,String indent) {
        text = text.replaceAll(StringUtil.newline,StringUtil.newline + indent);
        //text = text.replace(StringUtil.newline,newIndent);
        return text;
    }
    
    public static String formatLine(String text,String indent) {
        String ret = text.replace(StringUtil.newline,StringUtil.newline + indent + StringUtil.ATAB);
        return ret;
    }
}
