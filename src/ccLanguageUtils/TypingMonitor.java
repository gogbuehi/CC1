package ccLanguageUtils;

import Utilities.TestInfo;
import Utilities.Validation;
import cc1.ccTextEditor.StringUtil;
import ccFileIO.CCFile;
import java.io.File;
import java.util.HashMap;

/**
 * This class is responsible for monitoring text, as it is typed.
 * It will process special romanised words and convert them to the appropriate
 * language character.
 * This is specifically for languages that use Unicode characters
 * @author Goodwin
 */
public class TypingMonitor {
    private static final int MAXIMUM_CHAR_LENGTH = 5;
    
    protected String[] mLastCharacters;
    protected HashMap<String,String> mCharacterMap;
    protected HashMap<String,String>[] mCharacterMaps;
    protected CCFile mLanguageMap;
    protected String mLanguageMapName;
    protected boolean mUseLanguageMap;
    public TypingMonitor() {
        mCharacterMaps = new HashMap[MAXIMUM_CHAR_LENGTH];
        mLastCharacters = new String[MAXIMUM_CHAR_LENGTH];
        initialiseLastCharacters();
        initialiseCharacterMaps();
        mLanguageMap = new CCFile();
        mUseLanguageMap = false;
        mLanguageMapName = "";
        mCharacterMap = new HashMap<String,String>();
    }
    
    public void loadCharacterMapFromFile() {
        File openFile = mLanguageMap.getFileToOpen();
        loadCharacterMapFromFile(openFile);
        
    }
    
    private void loadCharacterMapFromFile(File openFile) {
        if (Validation.isValidFile(openFile)) {
            System.out.println("Valid language character map file... " + openFile.getAbsolutePath());
            if (hasLanguageMapLoaded()) {
                clearLangaugeMap();
            }
            mLanguageMap.openFile(openFile);
            String languageMapText = mLanguageMap.getText();
            String[] lines = languageMapText.split(StringUtil.newline);
            if (lines.length > 1) {
                mLanguageMapName = lines[0];
                String[] keyValuePair;
                String key,value;
                for(int i = 1; i < lines.length; i++) {
                    keyValuePair = lines[i].split(StringUtil.ATAB);
                    if (keyValuePair.length == 2) {
                        key = keyValuePair[0];
                        value = keyValuePair[1];
                        if (key.length() <= mCharacterMaps.length) {
                            mCharacterMaps[key.length()-1].put(key, value);
                        }
                        mCharacterMap.put(key,value);
                    }
                }
                mUseLanguageMap = true;
                saveLanguageMapLocally();
            }
        } else {
            System.out.println("Not a valid language character map file... " + openFile.getAbsolutePath());
        }
    }
    
    private void saveLanguageMapLocally() {
        String fileName = getLanguageMapName(mLanguageMapName); //mLanguageMapName.toLowerCase() + "_map.cc";
        String fileContents = mLanguageMapName;
        for (String i : mCharacterMap.keySet()) {
            fileContents += StringUtil.newline + i + StringUtil.ATAB + mCharacterMap.get(i);
        }
        
        CCFile langaugeFile = new CCFile();
        langaugeFile.saveFile(fileName,fileContents);
    }
    
    private String getLanguageMapName(String languageName) {
        return languageName.toLowerCase() + "_map.cc";
    }
    
    public void loadCharacterMap(String languageMapFileName) {
        System.out.println(languageMapFileName);
        File openFile = new File(languageMapFileName);
        loadCharacterMapFromFile(openFile);
    }
    
    public void loadLocalCharacterMap(String languageMapName) {
        loadCharacterMap(getLanguageMapName(languageMapName));
    }
    
    public void disableLanguageMap() {
        mUseLanguageMap = false;
    }
    
    public void enableLanguageMap() {
        mUseLanguageMap = true;
    }
    
    public void addCharacter(String character) {
        int lastCharactersLength = mLastCharacters.length;
        for(int i = lastCharactersLength-1; i > 0; i--) {
            mLastCharacters[i] = mLastCharacters[i-1];
        }
        mLastCharacters[0] = character;
    }
    
    public boolean replaceTypingWith() {
        if (!mUseLanguageMap) {
            return false;
        }
        String key = lastTyped();
        if (mCharacterMap.containsKey(key)) {
            return true;
        }
        return false;
    }
    
    public boolean replaceTypingWith(int length) {
        if (!mUseLanguageMap) {
            return false;
        }
        String key = lastTyped(length);
        if (mCharacterMaps[length-1].containsKey(key)) {
            return true;
        }
        return false;
    }
    
    public String processLineOfText(String lineOfText) {
        String result = lineOfText.toString();
        initialiseLastCharacters();
        
        String lastTyped;
        for (int i = 0; i < lineOfText.length(); i++) {
            addCharacter(String.valueOf(lineOfText.charAt(i)));
            lastTyped = lastTyped();
            TestInfo.testWriteLn("Last Typed: " + lastTyped);
            if (replaceTypingWith()) {
                TestInfo.testWriteLn("Trying to replace with: " + getReplaceChar(lastTyped));
                result = result.replaceFirst(lastTyped(), getReplaceChar(lastTyped));
                initialiseLastCharacters();
            }   
        }
        return result;
    }
    public String processLineOfTextFullPass(String lineOfText) {
        return processLineOfTextFullPass(lineOfText, MAXIMUM_CHAR_LENGTH);
        
    }
    public String processLineOfTextFullPass(String lineOfText, int length) {
        if (length == 0) {
            return lineOfText;
        }
        String result = lineOfText.toString();
        initialiseLastCharacters();
        
        String lastTyped;
        for (int i = 0; i < lineOfText.length(); i++) {
            addCharacter(String.valueOf(lineOfText.charAt(i)));
            lastTyped = lastTyped(length);
            TestInfo.testWriteLn("Last Typed: " + length + ": " + lastTyped);
            if (replaceTypingWith(length)) {
                result = result.replaceFirst(lastTyped, getReplaceChar(lastTyped));
                initialiseLastCharacters();
            }   
        }
        return processLineOfTextFullPass(result, length-1);
    }
    
    public boolean hasLanguageMapLoaded() {
        return !mLanguageMapName.equals("");
    }
    
    /**
     * Simple returns the name of the Language map in use
     * @return
     */
    @Override
    public String toString() {
        if (mUseLanguageMap) {
            return mLanguageMapName;
        }
        else {
          return "";  
        }
    }
    
    public boolean usingCharacterMap() {
        return mUseLanguageMap;
    }
    
    public final void initialiseLastCharacters() {
        for(int i = 0; i < mLastCharacters.length; i++) {
            mLastCharacters[i] = "";
        }
    }
    
    protected final void initialiseCharacterMaps() {
        for(int i = 0; i < mCharacterMaps.length; i++) {
            mCharacterMaps[i] = new HashMap<String,String>();
        }
    }
    
    protected void clearCharacterMaps() {
        for(int i = 0; i < mCharacterMaps.length; i++) {
            mCharacterMaps[i].clear();
        }
    }
    
    protected String lastTyped() {
        String result = "";
        for(int i = mLastCharacters.length -1; i >= 0 ; i--) {
            result += mLastCharacters[i];
        }
        return result;
        //return StringUtils.join(mLastCharacters,"");
    }
    
    protected String lastTyped(int length) {
        String result = "";
        for (int i = length-1; i >=0 && result.length() < length; i--) {
            if (mLastCharacters[i].matches(StringUtil.ASPACE)) {
                result = "";
            }
            result += mLastCharacters[i];
        }
        return result;
    }
    
    protected String getReplaceChar(String key) {
        return mCharacterMap.get(key);
    }
    
    protected void clearLangaugeMap() {
        mLanguageMapName = "";
        mLanguageMap = new CCFile();
        mCharacterMap.clear();
        clearCharacterMaps();
        mUseLanguageMap = false;
    }
}
