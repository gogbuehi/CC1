/*
 * CCAppletFrame.java
 *
 * Created on June 14, 2006, 1:37 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package cc1;

import CCInfo.CCFileBrowser;
import CCInfo.InfoUI;
import cc1.ccTextEditor.CCFind;
import ccMenus.CCMainMenu;
import java.awt.BorderLayout;
import java.awt.Frame;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 *
 * @author goodwin.ogbuehi
 */
public class CCAppletFrame extends Frame {
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
    /** Creates a new instance of CCAppletFrame */
    public CCAppletFrame() {
    }
    public CCAppletFrame(String title) {
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
    
    public void CCFrameInit(Frame mainFrame) {
        //mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //mainFrame.addWindowListener(this);
        //mainFrame.setJMenuBar(ccMM);
        //mainFrame.getContentPane().add(ccFB.getBrowser(), BorderLayout.WEST);
        //mainFrame.getContentPane().add(ccTT, BorderLayout.CENTER);
        mainFrame.add(makeNorth(), BorderLayout.NORTH);
        mainFrame.add(CCSplitPane(), BorderLayout.CENTER);
        mainFrame.add(iUI.getUI(), BorderLayout.SOUTH);
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
