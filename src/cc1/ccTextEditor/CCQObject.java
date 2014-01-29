/*
 * CCQObject.java
 *
 * Created on June 1, 2006, 10:48 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package cc1.ccTextEditor;

/**
 *
 * @author goodwin.ogbuehi
 */
public class CCQObject {
    public int qid,sid,qtype;
    public String qlabel;
    public boolean multi;
    /** Creates a new instance of CCQObject */
    public CCQObject() {
    }
    
    public void setQID(int QID) {
        qid = QID;
    }
    public void setQID(String QID) {
        qid = Integer.valueOf(QID);
    }
    
    public void setSID(int SID) {
        sid = SID;
    }
    public void setSID(String SID) {
        sid = Integer.valueOf(SID);
    }
    
    public void setQType(int QType) {
        qtype = QType;
    }
    public void setQType(String QType) {
        qtype = Integer.valueOf(QType);
    }
    
}
