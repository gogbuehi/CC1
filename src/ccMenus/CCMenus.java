/*
 * ccFileMenu.java
 *
 * Created on January 6, 2006, 10:12 AM
 */

package ccMenus;

import cc1.EventCatcher;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 *
 * @author  goodwin.ogbuehi
 */
public class CCMenus extends JMenu implements ActionListener {
    EventCatcher ec;
    /** Creates a new instance of ccMenus */
    public CCMenus() {
        super();
    }
    
    public CCMenus(String heading, String [] Actns, String [] Items) {
        super(heading);
        JMenuItem tempItem;
        for (int i = 0; i < Items.length; i++) {
            tempItem = new JMenuItem(Items[i]);
            tempItem.addActionListener(this);
            tempItem.setActionCommand(Actns[i]);
            add(tempItem);
        }
    }
    
    public void setEventCatcher(EventCatcher ec) {
        this.ec = ec;
    }
    public void actionPerformed(ActionEvent e) {
        ec.performAction(e);
    }
}
