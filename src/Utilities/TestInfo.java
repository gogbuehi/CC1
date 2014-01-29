/*
 * TestInfo.java
 *
 * Created on January 19, 2006, 2:04 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package Utilities;

/**
 *
 * @author goodwin.ogbuehi
 */
public class TestInfo {
    /** Creates a new instance of TestInfo */
    public TestInfo() {
    }
    
    public static void testWrite(String text) {
        System.out.print("|~" + text + "~|");
    }
    public static void testWriteLn(String text) {
        System.out.println("|~" + text + "~|");
    }
}
