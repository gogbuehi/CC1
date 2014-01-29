/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cc2.IDEs.PHP;

/**
 * Class to represent a variable usage in a PHP document
 * 
 * @author goodwin
 */
public class PhpVariable {
    public final static int TYPE_MIXED=1;
    public final static int TYPE_ARRAY=2;
    public final static int TYPE_OBJECT=3;
    public final static int TYPE_CONSTANT=4;
    protected String mName;
    protected int mType;

}
