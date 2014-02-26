package ccLanguageUtils;

import Utilities.TestInfo;
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
    public TypingMonitor() {
        mLastCharacters = new String[2];
        initialiseLastCharacters();
        mCharacterMap = new HashMap<String,String>();
        mCharacterMap.put("hã", "ሀ");
        mCharacterMap.put("le", "ለ");
        mCharacterMap.put("me", "መ");
        mCharacterMap.put("se", "ሠ");
        
    }
    
    public void addCharacter(String character) {
        int lastCharactersLength = mLastCharacters.length;
        for(int i = lastCharactersLength-1; i > 0; i--) {
            mLastCharacters[i] = mLastCharacters[i-1];
        }
        mLastCharacters[0] = character;
    }
    
    public boolean replaceTypingWith() {
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
}
