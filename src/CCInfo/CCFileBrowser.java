/*
 * CCFileBrowser.java
 *
 * Created on January 31, 2006, 3:51 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package CCInfo;

import Utilities.TestInfo;
import Utilities.Validation;
import cc1.EventCatcher;
import java.awt.FlowLayout;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.Position;

/**
 *
 * @author Goodwin
 */
public class CCFileBrowser implements ListSelectionListener {
    DefaultListModel listModelDirs,listModelFiles;
    DefaultListModel listModelDirsAbs,listModelFilesAbs;
    JList dList;
    JList fList;
    
    JPanel dPanel;
    JPanel fPanel;
    
    JScrollPane dSP;
    JScrollPane fSP;
    
    JPanel mainPanel;
    
    JTextField jtfFind;
    CCDirSearch ccds;
    
    //CCFrame ccf;
    EventCatcher ec;
    boolean ignoreLists;
    
    JLabel jlCurrDir;
    /** Creates a new instance of CCFileBrowser */
    public CCFileBrowser() {
        ignoreLists = false;
    }
    
    public void makeFileBrowser(File currentDir) {
        File [] files_dirs = currentDir.listFiles();
        //String [] strFiles_dirs = currentDir.list();
        
        jtfFind = new JTextField(10);
        
        listModelDirs = new DefaultListModel();
        listModelFiles = new DefaultListModel();
        listModelDirsAbs = new DefaultListModel();
        listModelFilesAbs = new DefaultListModel();
        
        listModelDirs.addElement("...");
        File dirParent = currentDir.getParentFile();
        if (!Validation.isValidDirectory(dirParent)) {
            dirParent = currentDir;
        }
        listModelDirsAbs.addElement(dirParent);
        //boolean [] file_v_dir = new boolean [files_dirs.length];
        //File = true; Dir = false
        for (int i = 0; i < files_dirs.length; i++) {
            if (Validation.isValidDirectory(files_dirs[i])) {
                //file_v_dir[i] = false;
                //TestInfo.testWriteLn("Dir: " + strFiles_dirs[i]);
                //TestInfo.testWriteLn("DName: " + files_dirs[i].getName());
                //listModelDirs.addElement(strFiles_dirs[i]);
                listModelDirs.addElement(files_dirs[i].getName());
                listModelDirsAbs.addElement(files_dirs[i]);
            }
            else {
                //TestInfo.testWriteLn("File: " + strFiles_dirs[i]);
                //file_v_dir[i] = true;
                if (Validation.isTextFile(files_dirs[i])) {
                    if (Validation.isReadOnly(files_dirs[i])) {
                        listModelFiles.addElement("<~> " + files_dirs[i].getName());
                    }
                    else {
                        listModelFiles.addElement("< > " + files_dirs[i].getName());
                    }
                    listModelFilesAbs.addElement(files_dirs[i]);
                }
            }
        }
        
        jlCurrDir = new JLabel(currentDir.getAbsolutePath());
        
        
        dList = new JList(listModelDirs);
        fList = new JList(listModelFiles);
        dList.setFixedCellWidth(150);
        fList.setFixedCellWidth(150);
        dList.setVisibleRowCount(15);
        fList.setVisibleRowCount(20);
        
        dList.addListSelectionListener(this);
        fList.addListSelectionListener(this);
        
        //dList.setListData();
        dPanel = new JPanel();
        dPanel.add(jtfFind);
        fPanel = new JPanel();
        
        dSP = new JScrollPane(dList);
        fSP = new JScrollPane(fList);
        dSP.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        fSP.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        
        //dPanel.add(jtfFind);
        //dPanel.add(dSP);
        //fPanel.add(fSP);
        
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
        
        mainPanel.add(dPanel);
        //mainPanel.add(jlCurrDir);
        mainPanel.add(dSP);
        mainPanel.add(fSP);
        
        ccds = new CCDirSearch(this);
    }
    
    public void setLists(File newDir) {
        jlCurrDir.setText(newDir.getAbsolutePath());
        //ccf.setTitle(newDir.getAbsolutePath());
        File [] files_dirs = newDir.listFiles();
        //String [] strFiles_dirs = newDir.list();
        
        listModelDirs = new DefaultListModel();
        listModelFiles = new DefaultListModel();
        listModelDirsAbs = new DefaultListModel();
        listModelFilesAbs = new DefaultListModel();
        
        listModelDirs.addElement("...");
        //File dirParent = newDir.getParentFile();
        File dirParent = getParentDir(newDir);
        //if (!Validation.isValidDirectory(dirParent)) {
            //if (Validation.isValidFile(dirParent)) {
        //    File [] roots = newDir.listRoots();
        //    for (int i = 0; i < roots.length; i++) {
        //        TestInfo.testWriteLn("Root - " + i + " - " + roots[i].getAbsolutePath());
        //    }
        //    try {
        //        TestInfo.testWriteLn("Path: " + newDir.getCanonicalPath());
        //        //newDir.getCanonicalP
        //    }
        //    catch (IOException e) {
        //        TestInfo.testWriteLn("IOException: newDir.getCanonicalPath()");
        //    }
            //}
        //    dirParent = newDir;
        //}
        if (newDir.equals(dirParent)) {
            listModelDirsAbs.addElement(null);
        }
        else {
            listModelDirsAbs.addElement(dirParent);
        }
        //boolean [] file_v_dir = new boolean [files_dirs.length];
        //File = true; Dir = false
        for (int i = 0; i < files_dirs.length; i++) {
            if (Validation.isValidDirectory(files_dirs[i])) {
                //file_v_dir[i] = false;
                //TestInfo.testWriteLn("Dir: " + strFiles_dirs[i]);
                //TestInfo.testWriteLn("DName: " + files_dirs[i].getName());
                //listModelDirs.addElement(strFiles_dirs[i]);
                listModelDirs.addElement(files_dirs[i].getName());
                listModelDirsAbs.addElement(files_dirs[i]);
            }
            else {
                //TestInfo.testWriteLn("File: " + strFiles_dirs[i]);
                //file_v_dir[i] = true;
                if (Validation.isTextFile(files_dirs[i])) {
                    if (Validation.isReadOnly(files_dirs[i])) {
                        listModelFiles.addElement("<~> " + files_dirs[i].getName());
                    }
                    else {
                        listModelFiles.addElement("< > " + files_dirs[i].getName());
                    }
                    listModelFilesAbs.addElement(files_dirs[i]);
                }
            }
        }
        
        dList.setListData(listModelDirs.toArray());
        fList.setListData(listModelFiles.toArray());
    }
    
    public void setRoots() {
        jlCurrDir.setText("[ROOT]");
        //ccf.setTitle("[ROOT]");
        //File [] files_dirs = newDir.listFiles();
        //String [] strFiles_dirs = newDir.list();
        File [] roots = File.listRoots();
        listModelDirs = new DefaultListModel();
        listModelFiles = new DefaultListModel();
        listModelDirsAbs = new DefaultListModel();
        listModelFilesAbs = new DefaultListModel();
        
        //listModelDirs.addElement("...");
        //File dirParent = newDir.getParentFile();
        //File dirParent = getParentDir(newDir);
        //if (!Validation.isValidDirectory(dirParent)) {
            //if (Validation.isValidFile(dirParent)) {
        //    File [] roots = newDir.listRoots();
        //    for (int i = 0; i < roots.length; i++) {
        //        TestInfo.testWriteLn("Root - " + i + " - " + roots[i].getAbsolutePath());
        //    }
        //    try {
        //        TestInfo.testWriteLn("Path: " + newDir.getCanonicalPath());
        //        //newDir.getCanonicalP
        //    }
        //    catch (IOException e) {
        //        TestInfo.testWriteLn("IOException: newDir.getCanonicalPath()");
        //    }
            //}
        //    dirParent = newDir;
        //}
        /*
        if (newDir.equals(dirParent)) {
            listModelDirsAbs.addElement(null);
        }
        else {
            listModelDirsAbs.addElement(dirParent);
        }
         */
        //boolean [] file_v_dir = new boolean [files_dirs.length];
        //File = true; Dir = false
        String strRoot;
        for (int i = 0; i < roots.length; i++) {
            strRoot = roots[i].getAbsolutePath();
            listModelDirs.addElement(strRoot);
            listModelDirsAbs.addElement(roots[i]);
        }
        /*
        for (int i = 0; i < files_dirs.length; i++) {
            if (Validation.isValidDirectory(files_dirs[i])) {
                //file_v_dir[i] = false;
                //TestInfo.testWriteLn("Dir: " + strFiles_dirs[i]);
                //TestInfo.testWriteLn("DName: " + files_dirs[i].getName());
                //listModelDirs.addElement(strFiles_dirs[i]);
                listModelDirs.addElement(files_dirs[i].getName());
                listModelDirsAbs.addElement(files_dirs[i]);
            }
            else {
                //TestInfo.testWriteLn("File: " + strFiles_dirs[i]);
                //file_v_dir[i] = true;
                if (Validation.isReadOnly(files_dirs[i])) {
                    listModelFiles.addElement("<~> " + files_dirs[i].getName());
                }
                else {
                    listModelFiles.addElement("< > " + files_dirs[i].getName());
                }
                listModelFilesAbs.addElement(files_dirs[i]);
            }
        }
         */
        
        dList.setListData(listModelDirs.toArray());
        fList.setListData(listModelFiles.toArray());
    }
    
    public JPanel getBrowser() {
        return mainPanel;
    }
    
    public File getSelectedFile() {
        //listModelFiles.
        File ret;
        int index = fList.getSelectedIndex();
        if (index != -1) {
            ret = (File) listModelFilesAbs.get(index);
            return ret;
        }
        return null;
    }
    
    public File getSelectedDir() {
        //listModelFiles.
        File ret;
        int index = dList.getSelectedIndex();
        if (index != -1) {
            ret = (File) listModelDirsAbs.get(index);
            return ret;
        }
        return null;
    }
        
    public void setEventCather(EventCatcher newEC) {
        ec = newEC;
    }
    
    public void valueChanged(ListSelectionEvent e) {
        //ec.performAction(e);
        clearJTF();
        if (ignoreLists) {
            //Do nothiing
        }
        else {
            ignoreLists = true; //If clicked rapidly, will only execute again when completed executing this
            JList tList = (JList) e.getSource();
            if (tList.equals(fList)) {
                if (e.getValueIsAdjusting()) {
                    File file = getSelectedFile();
                    int index = e.getFirstIndex();
                    //TestInfo.testWriteLn("Event: " + e.toString());
                    TestInfo.testWriteLn("Index: " + index);
                    TestInfo.testWriteLn("File: " + file.getAbsolutePath());
                    ec.openListFile(file);
                }
            }
            else if (tList.equals(dList)) {
                if (e.getValueIsAdjusting()) {
                    try {
                        File file = getSelectedDir();
                        int index = e.getFirstIndex();
                        //TestInfo.testWriteLn("Event: " + e.toString());
                        TestInfo.testWriteLn("Index: " + index);
                        TestInfo.testWriteLn("Dir: " + file.getAbsolutePath());
                        //ec.openListFile(file);
                        setLists(file);
                    }
                    catch (NullPointerException ex) {
                        setRoots();
                    }
                }
            }
            ignoreLists = false;
            ec.requestFocus();
        }
    }
    public void openFirstDir(String prefix) {
        int index = dList.getNextMatch(prefix,0,Position.Bias.Forward);
        if (index != -1) {
            dList.setSelectedIndex(index);
            //ListSelectionEvent e = new ListSelectionEvent(dList,dList.getMinSelectionIndex(),dList.getMaxSelectionIndex(),false);
            //valueChanged(e);
            openSelection(dList);
        }
    }
    
    private void openSelection(JList tList) {
        clearJTF();
        if (ignoreLists) {
            //Do nothiing
        }
        else {
            ignoreLists = true; //If clicked rapidly, will only execute again when completed executing this
            //JList tList = (JList) e.getSource();
            if (tList.equals(fList)) {
                //if (e.getValueIsAdjusting()) {
                    File file = getSelectedFile();
                    //int index = e.getFirstIndex();
                    //TestInfo.testWriteLn("Event: " + e.toString());
                    //TestInfo.testWriteLn("Index: " + index);
                    TestInfo.testWriteLn("File: " + file.getAbsolutePath());
                    ec.openListFile(file);
                //}
            }
            else if (tList.equals(dList)) {
                //if (e.getValueIsAdjusting()) {
                    try {
                        File file = getSelectedDir();
                        //int index = e.getFirstIndex();
                        //TestInfo.testWriteLn("Event: " + e.toString());
                        //TestInfo.testWriteLn("Index: " + index);
                        TestInfo.testWriteLn("Dir: " + file.getAbsolutePath());
                        //ec.openListFile(file);
                        setLists(file);
                    }
                    catch (NullPointerException ex) {
                        setRoots();
                    }
                //}
            }
            ignoreLists = false;
            jtfFind.requestFocus();
            //ec.requestFocus();
        }
    }
        
    public File getParentDir(File newDir) {
        //String [] strFiles_dirs = newDir.list();
        String ps = File.separator;
        File dirParent = newDir.getParentFile();
        if (!Validation.isValidDirectory(dirParent)) {
            String curDir = newDir.getAbsolutePath();
            int pIndex = curDir.lastIndexOf(ps);
            String strDirParent = curDir.substring(0,pIndex);
            TestInfo.testWriteLn("SubString: " + strDirParent);
            //TestInfo.testWriteLn(ps);
            //if (Validation.isValidFile(dirParent)) {
            dirParent = new File(strDirParent);
            File [] roots = newDir.listRoots();
            String strRoot;
            for (int i = 0; i < roots.length; i++) {
                strRoot = roots[i].getAbsolutePath();
                if (strRoot.equals(strDirParent + ps)) {
                    dirParent = roots[i];
                    break;
                }
                TestInfo.testWriteLn("Root - " + i + " - " + strRoot);
                
                
            }
            //}
            
        }
        return dirParent;
    }
    
    
    public File setDirList(String criteria,File newDir) {
        //if (listModelDirsAbs.size() <= 1)
            //return newDir;
        //File newDir = (File) listModelDirsAbs.getElementAt(1); 
        //newDir = newDir.getParentFile(); //Parent Directory
        if (Validation.isRootDir(newDir)) {
            //return newDir;
        }
        if (criteria.equals("")) {
            setLists(newDir);
            return newDir;
        }
        try {
            File [] files_dirs = newDir.listFiles();
            //String [] strFiles_dirs = newDir.list();

            listModelDirs = new DefaultListModel();
            listModelFiles = new DefaultListModel();
            listModelDirsAbs = new DefaultListModel();
            listModelFilesAbs = new DefaultListModel();

            listModelDirs.addElement("...");
            //File dirParent = newDir.getParentFile();
            File dirParent = getParentDir(newDir);
            //if (!Validation.isValidDirectory(dirParent)) {
                //if (Validation.isValidFile(dirParent)) {
            //    File [] roots = newDir.listRoots();
            //    for (int i = 0; i < roots.length; i++) {
            //        TestInfo.testWriteLn("Root - " + i + " - " + roots[i].getAbsolutePath());
            //    }
            //    try {
            //        TestInfo.testWriteLn("Path: " + newDir.getCanonicalPath());
            //        //newDir.getCanonicalP
            //    }
            //    catch (IOException e) {
            //        TestInfo.testWriteLn("IOException: newDir.getCanonicalPath()");
            //    }
                //}
            //    dirParent = newDir;
            //}
            if (newDir.equals(dirParent)) {
                listModelDirsAbs.addElement(null);
            }
            else {
                listModelDirsAbs.addElement(dirParent);
            }
            //boolean [] file_v_dir = new boolean [files_dirs.length];
            //File = true; Dir = false
            String dFileName;
            for (int i = 0; i < files_dirs.length; i++) {
                if (Validation.isValidDirectory(files_dirs[i])) {
                    dFileName = files_dirs[i].getName().toLowerCase();
                    if (dFileName.length() >= criteria.length()) {
                        dFileName = dFileName.substring(0,criteria.length());
                        if (dFileName.equals(criteria.toLowerCase())) { 
                            //file_v_dir[i] = false;
                            //TestInfo.testWriteLn("Dir: " + strFiles_dirs[i]);
                            //TestInfo.testWriteLn("DName: " + files_dirs[i].getName());
                            //listModelDirs.addElement(strFiles_dirs[i]);
                            listModelDirs.addElement(files_dirs[i].getName());
                            listModelDirsAbs.addElement(files_dirs[i]);
                        }
                    }
                }
                /*
                else {
                    //TestInfo.testWriteLn("File: " + strFiles_dirs[i]);
                    //file_v_dir[i] = true;
                    if (Validation.isTextFile(files_dirs[i])) {
                        if (Validation.isReadOnly(files_dirs[i])) {
                            listModelFiles.addElement("<~> " + files_dirs[i].getName());
                        }
                        else {
                            listModelFiles.addElement("< > " + files_dirs[i].getName());
                        }
                        listModelFilesAbs.addElement(files_dirs[i]);
                    }
                }
                 */
            }

            dList.setListData(listModelDirs.toArray());
            return newDir;
            //fList.setListData(listModelFiles.toArray());
        }
        catch (NullPointerException e) {
            return null;
        }
    }
    
    public File getCurrentDir() {
        
        if (listModelDirsAbs.size() <= 1) {
            TestInfo.testWriteLn("size: ");
            return null;
        }
        
        File newDir = (File) listModelDirsAbs.getElementAt(1); 
        if (Validation.isRootDir(newDir)) {
            return null;
        }
        else {
            newDir = newDir.getParentFile();
            return newDir;
        }
    }
    
    public boolean currentIsRoot() {
        if (listModelDirsAbs.size() <= 1) {
            //TestInfo.testWriteLn("size: ");
            return false;
        }
        File newDir = (File) listModelDirsAbs.getElementAt(1); 
        if (Validation.isRootDir(newDir)) {
            return true;
        }
        return false;
    }
    public File setDirList(String criteria) {
        return null;
        /*
        if (listModelDirsAbs.size() <= 1)
            return;
        File newDir = (File) listModelDirsAbs.getElementAt(1); 
        newDir = newDir.getParentFile(); //Parent Directory
        if (Validation.isRootDir(newDir)) {
            return;
        }
        if (criteria.equals("")) {
            setLists(newDir);
            return;
        }
        File [] files_dirs = newDir.listFiles();
        //String [] strFiles_dirs = newDir.list();
        
        listModelDirs = new DefaultListModel();
        listModelFiles = new DefaultListModel();
        listModelDirsAbs = new DefaultListModel();
        listModelFilesAbs = new DefaultListModel();
        
        listModelDirs.addElement("...");
        //File dirParent = newDir.getParentFile();
        File dirParent = getParentDir(newDir);
        //if (!Validation.isValidDirectory(dirParent)) {
            //if (Validation.isValidFile(dirParent)) {
        //    File [] roots = newDir.listRoots();
        //    for (int i = 0; i < roots.length; i++) {
        //        TestInfo.testWriteLn("Root - " + i + " - " + roots[i].getAbsolutePath());
        //    }
        //    try {
        //        TestInfo.testWriteLn("Path: " + newDir.getCanonicalPath());
        //        //newDir.getCanonicalP
        //    }
        //    catch (IOException e) {
        //        TestInfo.testWriteLn("IOException: newDir.getCanonicalPath()");
        //    }
            //}
        //    dirParent = newDir;
        //}
        if (newDir.equals(dirParent)) {
            listModelDirsAbs.addElement(null);
        }
        else {
            listModelDirsAbs.addElement(dirParent);
        }
        //boolean [] file_v_dir = new boolean [files_dirs.length];
        //File = true; Dir = false
        String dFileName;
        for (int i = 0; i < files_dirs.length; i++) {
            if (Validation.isValidDirectory(files_dirs[i])) {
                dFileName = files_dirs[i].getName().toLowerCase();
                if (dFileName.length() >= criteria.length()) {
                    dFileName = dFileName.substring(0,criteria.length());
                    if (dFileName.equals(criteria.toLowerCase())) { 
                        //file_v_dir[i] = false;
                        //TestInfo.testWriteLn("Dir: " + strFiles_dirs[i]);
                        //TestInfo.testWriteLn("DName: " + files_dirs[i].getName());
                        //listModelDirs.addElement(strFiles_dirs[i]);
                        listModelDirs.addElement(files_dirs[i].getName());
                        listModelDirsAbs.addElement(files_dirs[i]);
                    }
                }
            }
            /*
            else {
                //TestInfo.testWriteLn("File: " + strFiles_dirs[i]);
                //file_v_dir[i] = true;
                if (Validation.isTextFile(files_dirs[i])) {
                    if (Validation.isReadOnly(files_dirs[i])) {
                        listModelFiles.addElement("<~> " + files_dirs[i].getName());
                    }
                    else {
                        listModelFiles.addElement("< > " + files_dirs[i].getName());
                    }
                    listModelFilesAbs.addElement(files_dirs[i]);
                }
            }
             *
        }
        
        dList.setListData(listModelDirs.toArray());
        //fList.setListData(listModelFiles.toArray());
         */
    }
    public JTextField getJTF() {
        return jtfFind;
    }
    
    private void clearJTF() {
        ccds.clearJTF();
        //jtfFind.setText("");
    }
    
    public JPanel getJL() {
        JPanel tPanel = new JPanel(new FlowLayout());
        tPanel.add(jlCurrDir);
        return tPanel;
    }
}
