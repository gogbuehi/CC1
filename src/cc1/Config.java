/*
 * Config.java
 *
 * Created on January 24, 2005, 10:04 AM
 */

package cc1;
import cc1.ccTextEditor.StringUtil;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author  Goodwin Ogbuehi
 * email   code@gogbuehi.com
 */
public class Config {
    private final static String CONFIG = "default.cfg";
    private final static String PREV_FILES = "files.cfg";
    private final static int DEFAULTS = 1;
    
    File cnfgFile, openFiles;
    FileReader inputReader, oInputReader;
    BufferedReader inputBuffer, oInputBuffer;
    FileOutputStream out;
    PrintWriter outputWriter;
    
    String defaults [];
    String openFileConfigs [];
    // "00:[VER STRING]"
    private final static String [] dflts ={
        Main.VER
    };
    /*
    private final static String dfltTab = "0";
    private final static String dfltDir = "C:\\Documents and Settings\\goodwin.ogbuehi\\Desktop\\java\\TextChange\\";
    private final static String dfltRad = "0";
     */
    
    
    
    /** Creates a new instance of Config */
    public Config() {
        cnfgFile = new File(CONFIG);
        openFiles = new File(PREV_FILES);
        defaults = new String [DEFAULTS];
        if (openConfig()) {
            processConfig();
        }
        else {
            setDefaults();
            saveConfig();
        }
        
        if (!openOpenFiles()) {
            System.out.println("Did not have openFiles...");
        } else {
            System.out.println("Does have openFiles...");
        }
        
    }
    
    public boolean isConfigured() {
        return cnfgFile.exists();
    }
    
    private boolean hasOpenFiles() {
        return openFiles.exists();
    }
    
    public void setDefaults() {
        System.arraycopy(dflts, 0, defaults, 0, defaults.length);
    }
    
    public boolean openConfig() {
        if (isConfigured()) {
            try {
                inputReader = new FileReader(cnfgFile);
                inputBuffer = new BufferedReader(inputReader);
                return true;
            }
            catch (FileNotFoundException e) {
                return false;
            }
        }
        else {
            setDefaults();
            return false;
        }
    }
    
    private boolean openOpenFiles() {
        if (hasOpenFiles()) {
            try {
                oInputReader = new FileReader(openFiles);
                oInputBuffer = new BufferedReader(oInputReader);
                return true;
            }
            catch (FileNotFoundException e) {
                return false;
            }
            
        }
        return false;
    }
    
    public void processOpenFiles() {
        //String[] openFileConfigs;
        String temp = "";
        String nLine = "";
        try {
            while(oInputBuffer.ready()) {
                temp += nLine + oInputBuffer.readLine();
                nLine = StringUtil.newline;
            }
            openFileConfigs = temp.split(StringUtil.newline);
        } catch (IOException ex) {
            //Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
            openFileConfigs = new String[0];
        }
        
        //return openFileConfigs;
    }
    
    public void processConfig() {
        String temp;
        boolean set = false;
        boolean [] sets = new boolean [DEFAULTS];
        int i;
        for (i = 0; i < sets.length; i++) {
            sets[i] = false;
        }
        try {
            while (inputBuffer.ready()) {
                temp = inputBuffer.readLine();
                try {
                    i = Integer.valueOf(temp.substring(0,2));
                }
                catch (NumberFormatException e) {
                    i = DEFAULTS + 1;
                }
                switch (i) {
                    case 0:
                        if (!processVersion(temp.substring(3))) {
                            setDefaults();
                            return;
                        }
                        defaults[i] = dflts[i];
                        sets[i] = true;
                        break;
//                    case 1:
//                        defaults[i] = processTab(Integer.valueOf(temp.substring(3)));
//                        sets[i] = true;
//                        break;
//                    case 2:
//                        defaults[i] = processDir(temp.substring(3));
//                        sets[i] = true;
//                        break;
//                    case 3:
//                        defaults[i] = processRad(Integer.valueOf(temp.substring(3)));
//                        sets[i] = true;
//                        break;
                    default:
                        for (int j = 0; j < DEFAULTS; j++) {
                            set = sets[j] && set;
                        }
                        if (!set) {
                            setDefaults();
                        }
                        break;
                }
            }
        }
        catch (IOException e) {
            setDefaults();
        }
    }
    
    public boolean saveConfig() {
        String [] configs = new String [DEFAULTS];        
        try {
            out = new FileOutputStream(CONFIG);
            outputWriter = new PrintWriter(out);
            for (int i = 0; i < DEFAULTS; i++) {
                configs[i] = String.valueOf(Integer.valueOf(i/10))
                            + String.valueOf(i%10)
                            + ":" 
                            + defaults[i];
                outputWriter.println(configs[i]);
            }
            outputWriter.close();
            out.close();
            //writeFile();
            //closeWriter();
            return true;
        }
        catch (FileNotFoundException e) {
            //writeStatus("makeNewFile: File Not Found Exception!");
            return false;
        }
        catch (IOException e) {
            //Do Nothing.
        }
        return false;
    }
    
    public boolean saveOpenFiles(String [] openFileConfigs) {
        try {
            out = new FileOutputStream(PREV_FILES);
            outputWriter = new PrintWriter(out);
            for (int i = 0; i < openFileConfigs.length; i++) {
                outputWriter.println(openFileConfigs[i]);
            }
            outputWriter.close();
            out.close();
            //writeFile();
            //closeWriter();
            return true;
        }
        catch (FileNotFoundException e) {
            //writeStatus("makeNewFile: File Not Found Exception!");
            return false;
        }
        catch (IOException e) {
            //Do Nothing.
        }
        return false;
    }
    
    public void closeConfig() {
        try {
            inputReader.close();
        }
        catch (IOException e) {
        }
    }
    
    public boolean processVersion(String version) {
        System.out.println("Version: " + dflts[0]);
        System.out.println("Config Version: " + version);
        return version.equals(dflts[0]);
    }
    
//    public String processTab(int tab) {
//        if (tab < 0 || tab > Main.TABS) {
//            return dflts[1];
//        }
//        else {
//            return String.valueOf(tab);
//        }
//    }
//    
//    public String processDir(String dir) {
//        File temp = new File(dir);
//        if (temp.exists()) {
//            if (temp.isDirectory()) {
//                return dir;
//            }
//        }
//        return dflts[2];
//        
//    }
//    
//    public String processRad(int rad) {
//        if (rad < 0 || rad > Main.RADS) {
//            return dflts[3];
//        }
//        else {
//            return String.valueOf(rad);
//        }
//    }
    
    public void setTab(int tab) {
        defaults[1] = String.valueOf(tab);
    }
    
    public void setDir(String dir) {
        defaults[2] = dir;
    }
    
    public void setRad(int rad) {
        defaults[3] = String.valueOf(rad);
    }
    
    public int getTab() {
        return Integer.valueOf(defaults[1]);
    }
    
    public String getDir() {
        return defaults[2];
    }
    
    public int getRad() {
        return Integer.valueOf(defaults[3]);
    }
}