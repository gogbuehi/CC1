/*
 * WildcardSegment.java
 *
 * Created on September 10, 2006, 1:40 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package Utilities;

/**
 *
 * @author goodwin.ogbuehi
 */
public class WildcardSegment {
    public int start;
    public WildcardSegment next;
    public int type; //0 - Literal Segment; 1 - Single Character Wildcard; 2 - Multi-Character Wildcard
    public String seg;
    
    /** Creates a new instance of WildcardSegment */
    public WildcardSegment() {
        start = -1;
        type = -1;
        seg = "";
    }
    public WildcardSegment(int start) {
        this.start = start;
        this.type = 0;
        next = new WildcardSegment();
        seg = "";
    }
    public WildcardSegment(int start, int type) {
        this.start = start;
        this.type = type;
        next = new WildcardSegment();
        seg = "";
    }
    public void setStart(int start) {
        this.start = start;
        this.type = 0;
        next = new WildcardSegment();
    }
    public void setStart(int start,int type) {
        this.start = start;
        this.type = type;
        next = new WildcardSegment();
    }
    public int getStart() {
        return this.start;
    }
    public void setWildcardSegment(WildcardSegment w) {
        this.next = w;
    }
    public WildcardSegment getWildcardSegment() {
        return this.next;
    }
    public String makeFindString() {
        String ret = "";
        WildcardSegment next = this;
        while (next.start != -1) {
            ret += next.seg;
            next = next.next;
        }
        return ret;
    }
    public String makeFindType() {
        String ret = "";
        WildcardSegment next = this;
        while (next.start != -1) {
            ret += next.type + ".";
            next = next.next;
        }
        return ret;
    }
    public String makeFindStart() {
        String ret = "";
        WildcardSegment next = this;
        while (next.start != -1) {
            ret += next.start + ".";
            next = next.next;
        }
        return ret;
    }
}
