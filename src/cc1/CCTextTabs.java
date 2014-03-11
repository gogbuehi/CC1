/*
 * CCTextTabs.java
 *
 * Created on December 27, 2005, 3:26 PM
 */

package cc1;

import cc1.ccTextEditor.CCTextArea;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.JTabbedPane;

/**
 *
 * @author  Goodwin
 */
public class CCTextTabs extends JTabbedPane {
    EventCatcher ec;
    int tabCounter;
    public File file;
    public boolean hasFile,defaultHold;
    
    /** Creates a new instance of CCTextTabs */
    public CCTextTabs() {
        super();
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        tabCounter = 0;
        newTab();
        hasFile = false;
        defaultHold = false;
        //ec = new EventCatcher(this);
    }
    
    public CCTextTabs(int tabPlacement) {
        super(tabPlacement);
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        tabCounter = 0;
        newTab();
        hasFile = false;
        defaultHold = false;
        //ec = new EventCatcher(this);
    }
    
    public void newTab() {
        /*
        CCTab newTab = new CCTab();
        int selectedIndex = getSelectedIndex();
        tabCounter++;
        String tabTitle = "untitled-" + tabCounter + String.valueOf(this.getTabCount());
        add(tabTitle,newTab);
        this.setSelectedIndex(selectedIndex+1);
        //ec.setJTA(this);
         */
        newTabAfter();
    }
    public void newTabAfter() {
        //CCTab newTab = new CCTab();
        String tabTitle = "untitled-" + tabCounter;
        CCText newText = new CCText(tabTitle);
        int selectedIndex = getSelectedIndex();
        incrementCounter();
        
        //add(tabTitle,newTab);
        //insertTab(tabTitle, null,newTab,tabTitle,selectedIndex+1);
        insertTab(tabTitle, null,newText,tabTitle,selectedIndex+1);
        this.setSelectedIndex(selectedIndex+1);
        setCurrentFile();
    }
    
    public void newTabAfter(File openFile) {
        //CCTab newTab = new CCTab();
        CCText newText = new CCText(openFile);
        int selectedIndex = getSelectedIndex();
        incrementCounter();
        String tabTitle = openFile.getName();
        //add(tabTitle,newTab);
        //insertTab(tabTitle, null,newTab,tabTitle,selectedIndex+1);
        insertTab(tabTitle, null,newText,tabTitle,selectedIndex+1);
        this.setSelectedIndex(selectedIndex+1);
        newText.setFile(openFile);
        setCurrentFile();
    }
    
    public void newTabThis(File openFile) {
        //CCTab newTab = new CCTab();
        //CCText newText = new CCText(openFile);
        CCText newText = (CCText) getSelectedComponent();
        int selectedIndex = getSelectedIndex();
        //tabCounter++;
        String tabTitle = openFile.getName();
        this.setTitleAt(selectedIndex,tabTitle);
        //add(tabTitle,newTab);
        //insertTab(tabTitle, null,newTab,tabTitle,selectedIndex+1);
        //insertTab(tabTitle, null,newText,tabTitle,selectedIndex+1);
        //this.setSelectedIndex(selectedIndex+1);
        newText.setFile(openFile);
        setCurrentFile();
    }
    public CCTextArea getCurrentJTA() {
        //int currentIndex = getSelectedIndex();
        //CCTab currentTab = (CCTab) getSelectedComponent();
        if (!defaultHold) {
            CCText currentTab = (CCText) getSelectedComponent();
            return currentTab.getJTA();
        }
        return null;
    }
    
    public void setCurrentFile() {
        if (!defaultHold) {
            CCText currentTab = (CCText) getSelectedComponent();
            if (currentTab.hasFile) {
                file = currentTab.file;
                hasFile = true;
            } else {
                file = null;
                hasFile = false;
            }
        }
    }
    
    public void closeTab(int index) {
        //TestInfo.testWriteLn("ccTT Closing tab");
        if (getTabCount() == 1) {
            defaultHold = true;
        }
        this.remove(index);
        defaultHold = false;
    }
    
    public void checkForNoTabs() {
        if (getTabCount() < 1) {
            newTab();
        }
    }
    
    public void setEventCatcher(EventCatcher ec) {
        this.ec = ec;
    }
    
    public CCText[] getAllTextTabs() {
        int tabCount = getTabCount();
        CCText[] allTabs = new CCText[tabCount];
        for(int i = 0; i < tabCount; i++) {
            allTabs[i] = (CCText) getComponentAt(i);
        }
        
        return allTabs;
    }
    
    public String[] getAllTextTabConfigs() {
        int tabCount = getTabCount();
        String[] allTabs = new String[tabCount];
        for(int i = 0; i < tabCount; i++) {
            allTabs[i] = ((CCText) getComponentAt(i)).toConfig();
        }
        
        return allTabs;
    }
    
    public void incrementCounter() {
        tabCounter++;
    }
    
    public void setCounter(int value) {
        tabCounter = value;
    }
}
