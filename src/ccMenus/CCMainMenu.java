/*
 * ccMainMenu.java
 *
 * Created on January 6, 2006, 10:10 AM
 */

package ccMenus;

import cc1.EventCatcher;
import javax.swing.JMenuBar;

/**
 *
 * @author  goodwin.ogbuehi
 */
public class CCMainMenu extends JMenuBar {
    EventCatcher ec;
    /** Creates a new instance of ccMainMenu */
    public CCMainMenu() {
        super();
        //makeMenus();
    }
    
    public void makeMenus() {
        String fHead = "File";
        String [] fActns = {"NEW","OPEN","SAVE","SAVEAS","QUIT"};
        String [] fItems = {"New","Open...","Save","Save As...","Quit"};
        String eHead = "Edit";
        String [] eActns = {"UNDO","REDO","CUT","COPY","PASTE","FIND","REPLACE"};
        String [] eItems = {"Undo","Redo","Cut","Copy","Paste","Find","Replace"};
        String rHead = "Replace";
        String [] rActns = { "SELECT_LANGUAGE_MAP","REMOVE_LANGUAGE_MAP", "ENABLE_LAST_LANGUAGE_MAP"};//{"VBS","VBSH","VBH","VBHSA","VBA","VBHOL","VBHUL","VBHU","VBHI","VBHB","VBHEMAILIN","VBHCEMAIL","VBHCNAME","VBHCPDMSM","VBHEMAILLINK","VBHWEBLINK"};
        String [] rItems = { "Select Language...", "Disable Language", "Enable Last Language" };//{"VB-String","VB-String + HTML","VB-HTML","VB-String Array","VB-Array","Ordered List \"<ol>\"","Unordered List \"<ul>\"","Underline \"<u>\"","Italics \"<i>\"","Bold \"<b>\"","Email Input","Contact Email","Contact Name","Contact PD/MSM","Email Link","Web Link"};
        String hHead = "Help";
        String [] hActns = {"DOCU","ABOUT"};
        String [] hItems = {"Documentation","About"};
        
        CCMenus ccMenu = new CCMenus(fHead,fActns,fItems);
        ccMenu.setEventCatcher(ec);
        add(ccMenu);
        ccMenu = new CCMenus(eHead,eActns,eItems);
        ccMenu.setEventCatcher(ec);
        add(ccMenu);
        ccMenu = new CCMenus(rHead,rActns,rItems);
        ccMenu.setEventCatcher(ec);
        add(ccMenu);
        ccMenu = new CCMenus(hHead,hActns,hItems);
        ccMenu.setEventCatcher(ec);
        add(ccMenu);
    }
    
    public void setEventCatcher(EventCatcher ec) {
        this.ec = ec;
    }
    
}
