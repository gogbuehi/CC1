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
    public Main() {
        setContentPane(new CCAppletFrame("CC v0.2.4.1"));
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new CCFrame("CC v0.2.4.2");
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
