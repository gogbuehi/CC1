/*
 * Main.java
 *
 * Created on December 27, 2005, 3:21 PM
 */

package cc1;

import javax.swing.JApplet;

/**
 *
 * @author  Goodwin
 */
public class Main extends JApplet {
    /** Creates a new instance of Main */
    public final static String VER = "CC v0.3.0.0";
    public Main() {
        setContentPane(new CCAppletFrame(VER));
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new CCFrame(VER);
        //new Main();
    }
    @Override
    public void init() {
        new Main();
    }
    @Override
    public void start() {
        
    }
    @Override
    public void stop() {
        
    }
}
