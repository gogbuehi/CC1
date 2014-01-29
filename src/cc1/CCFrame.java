/*
 * CCFrame.java
 *
 * Created on December 27, 2005, 3:24 PM
 */

package cc1;

import CCInfo.CCFileBrowser;
import CCInfo.InfoUI;
import cc1.ccTextEditor.CCFind;
import ccMenus.CCMainMenu;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 *
 * @author  Goodwin
 */
public class CCFrame extends JFrame {
    JList dirs,files;
    JLabel rows,cols;
    public CCTextTabs ccTT;
    CCMainMenu ccMM;
    //CCFile ccf;
    public InfoUI iUI;
    public CCFileBrowser ccFB;
    public CCFind ccf;
    EventCatcher ec;
    String title;
    /** Creates a new instance of CCFrame */
    public CCFrame() {
        /*
        super();
        ccTT = new CCTextTabs();
        ccMM = new CCMainMenu();
        ec = new EventCatcher(this);
        ccTT.setEventCatcher(ec);
        ccMM.setEventCatcher(ec);
        //tt.newTab();
        CCFrameInit(this);
         */
    }
    
    public CCFrame(String title) {
        super(title);
        this.title = title;
        
        ccTT = new CCTextTabs();
        ccMM = new CCMainMenu();
        iUI = new InfoUI();
        ccFB = new CCFileBrowser();
        ccf = new CCFind();
        ec = new EventCatcher(this);
        ccTT.setEventCatcher(ec);
        ccMM.setEventCatcher(ec);
        ccMM.makeMenus();
        //ccf = new CCFile();
        //tt.newTab();
        
        CCFrameInit(this);
    }
    
    public void CCFrameInit(JFrame mainFrame) {
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //mainFrame.addWindowListener(this);
        mainFrame.setJMenuBar(ccMM);
        //mainFrame.getContentPane().add(ccFB.getBrowser(), BorderLayout.WEST);
        //mainFrame.getContentPane().add(ccTT, BorderLayout.CENTER);
        mainFrame.getContentPane().add(makeNorth(), BorderLayout.NORTH);
        mainFrame.getContentPane().add(CCSplitPane(), BorderLayout.CENTER);
        mainFrame.getContentPane().add(iUI.getUI(), BorderLayout.SOUTH);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }
    
    public JSplitPane CCSplitPane() {
        JSplitPane jSpP = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,ccFB.getBrowser(),ccTT);
        jSpP.setOneTouchExpandable(true);
        jSpP.setDividerSize(15);
        return jSpP;
    }
    
    public JPanel makeNorth() {
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel,BoxLayout.Y_AXIS));
        northPanel.add(ccf.getUI());
        northPanel.add(ccFB.getJL());
        return northPanel;
    }
}
