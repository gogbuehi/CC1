/*
 * InfoUI.java
 *
 * Created on January 29, 2006, 8:33 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package CCInfo;

import cc1.ccTextEditor.StringUtil;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Goodwin
 */
public class InfoUI {
    JPanel jp;
    JScrollPane jSP;
    JTextArea jTA;
    JPanel labelPanel;
    JLabel rowColLabel;
    int intRow,intCol;
    JLabel statusLabel,statusMsgLabel;
    /** Creates a new instance of InfoUI */
    public InfoUI() {
        intRow = 0;
        intCol = 0;
        
        //JPanel Initialization
        jp = new JPanel();
        jp.setLayout(new BoxLayout(jp,BoxLayout.Y_AXIS));
        jp.setBorder(BorderFactory.createEmptyBorder(5,10,10,10));
        
        //JTextArea Init
        jTA = new JTextArea("",3,80);
        jTA.setFont(new Font("Verdana", Font.PLAIN, 12));
        jTA.setDisabledTextColor(Color.BLUE);
        jTA.setLineWrap(false);
        jTA.setWrapStyleWord(true);
        jTA.setEnabled(false);
        
        //JScrollPane Init
        jSP = new JScrollPane(jTA);
        jSP.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        //JPanel Init for Row/Col and Status Labels
        labelPanel = new JPanel(new FlowLayout());
        
        //rowColLabel Init
        rowColLabel = new JLabel("");
        rowColLabel.setFont(new Font("Verdana", Font.BOLD, 12));
        setRow(intRow);
        
        //StatusLabel Init
        statusLabel = new JLabel("");
        statusLabel.setFont(new Font("Verdana", Font.BOLD, 12));
        statusMsgLabel = new JLabel("");
        statusMsgLabel.setFont(new Font("Verdana", Font.BOLD, 12));
        setStatus("");
        
        //Add Components to labelPanel
        labelPanel.add(statusLabel);
        labelPanel.add(rowColLabel);
        labelPanel.add(statusMsgLabel);
        
        //Add Components to jp
        jp.add(jSP);
        jp.add(labelPanel);
    }
    
    public void println(String text) {
        text = text + StringUtil.newline;
        jTA.insert(text,0);
        jTA.setCaretPosition(0);
    }
    
    public void setStatus(String text) {
        statusLabel.setText("Status: ");
        println("Status: " + String.valueOf(intCol) + ":" + String.valueOf(intRow + 1) + " | " + text);
        statusMsgLabel.setText(" | " + text);
    }
    
    public void setStatusError(String text) {
        statusLabel.setText("Error: ");
        println("Error: " + text);
        statusMsgLabel.setText(" | " + text);
    }
    
    public void setRow(int row) {
        if (row == -1)
            setStatusError("Row error");
        intRow = row;
        rowColLabel.setText(String.valueOf(intRow + 1) + ":" + String.valueOf(intCol + 1));
    }
    
    public void setCol(int col) {
        if (col == -1)
            setStatusError("Col error");
        intCol = col;
        rowColLabel.setText(String.valueOf(intRow + 1) + ":" + String.valueOf(intCol + 1));
    }
    
    public JPanel getUI() {
        return jp;
    }
}
