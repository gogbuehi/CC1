/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cc2.IDEs.PHP;

/**
 *
 * @author goodwin
 */
public class LookupItem {
    /**
     * The String to look for in the provided source file
     */
    protected String[] mLookupString;
    /**
     * The last found index of the lookup string
     */
    protected int mIndex;
    
    public LookupItem(String lookupString) {
        mLookupString = new String[1];
        mLookupString[0] = lookupString;
        mIndex = -2; //Default to -2, to indicate this has not been run, yet
    }
    public LookupItem(String[] lookupStrings) {
        mLookupString = lookupStrings;
        mIndex = -2;
    }

    public int getIndex() {
        return mIndex;
    }

    public void setIndex(int mIndex) {
        this.mIndex = mIndex;
    }

    public String[] getLookupString() {
        return mLookupString;
    }

    public void setLookupString(String[] mLookupString) {
        this.mLookupString = mLookupString;
    }
    
    public int findNextIndex(String searchString) {
        int tempIndex = searchString.length();
        int lastIndex;
        for (int i = 0; i < mLookupString.length; i++) {
            lastIndex = findNextItem(searchString,i);
            if (lastIndex != -1) {
                if (lastIndex < tempIndex) {
                    tempIndex = lastIndex;
                }
            }
        }
        return -1;
    }
    
    private int findNextItem(String searchString, int lookupIndex) {
        if (mIndex == -2) {
            return searchString.indexOf(mLookupString[lookupIndex]);
        } else if (mIndex != -1) {
            //mIndex has been found before, so start from right after that
            return searchString.indexOf(mLookupString[lookupIndex],mIndex+1);
        }
        return -1;
    }
    
    

}
