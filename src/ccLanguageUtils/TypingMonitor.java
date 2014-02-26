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
    protected String[] mLastCharacters;
    protected HashMap<String,String> mCharacterMap;
    protected CCFile mLanguageMap;
    protected String mLanguageMapName;
    protected boolean mUseLanguageMap;
    public TypingMonitor() {
        mLastCharacters = new String[2];
        initialiseLastCharacters();
        mLanguageMap = new CCFile();
        mUseLanguageMap = false;
        mLanguageMapName = "";
        mCharacterMap = new HashMap<String,String>();
    }
    
    public void loadCharacterMapFromFile() {
        File openFile = mLanguageMap.getFileToOpen();
        if (Validation.isValidFile(openFile)) {
            if (hasLanguageMapLoaded()) {
                clearLangaugeMap();
            }
            mLanguageMap.openFile(openFile);
            String languageMapText = mLanguageMap.getText();
            String[] lines = languageMapText.split(StringUtil.newline);
            if (lines.length > 1) {
                mLanguageMapName = lines[0];
                String[] keyValuePair;
                for(int i = 1; i < lines.length; i++) {
                    keyValuePair = lines[i].split(StringUtil.ATAB);
                    if (keyValuePair.length == 2) {
                        mCharacterMap.put(keyValuePair[0], keyValuePair[1]);
                    }
                }
                mUseLanguageMap = true;
            }
        }
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
    
    protected String lastTyped() {
        String result = "";
        for(int i = mLastCharacters.length -1; i >= 0 ; i--) {
            result += mLastCharacters[i];
        }
        return result;
        //return StringUtils.join(mLastCharacters,"");
    }
    
    protected String getReplaceChar(String key) {
        return mCharacterMap.get(key);
    }
    
    protected void clearLangaugeMap() {
        mLanguageMapName = "";
        mLanguageMap = new CCFile();
        mCharacterMap.clear();
        mUseLanguageMap = false;
    }
}
