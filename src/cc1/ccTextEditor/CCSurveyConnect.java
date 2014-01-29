/*
 * CCSurveyConnect.java
 *
 * Created on May 30, 2006, 12:33 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package cc1.ccTextEditor;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author goodwin.ogbuehi
 */
public class CCSurveyConnect {
    private final static int [] QUES_TYPE_ID_ARRAY = {10,15,20,25,30,35,40,45,50,60,70,80};
    private final static String [] QUES_TYPE_STRING_ARRAY = {"SIMPLE CHOICE","GROUP CHOICE","SIMPLE TEXT","GROUP TEXT","SIMPLE NUMERIC","GROUP NUMERIC","SIMPLE SCALE","GROUP SCALE","ALLOCATION","RANKING","STATEMENT","CELL"};
    Connection con;
    Statement stm;
    
    String strDB; //e.x. "SWS2_123"
    CCQObject [] qArray;
    /** Creates a new instance of CCSurveyConnect */
    public CCSurveyConnect() {
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String url = "jdbc:odbc:staging";
            con = DriverManager.getConnection(url,"sa", "tlv-844");
            
            stm = con.createStatement();
            //String sql = "SELECT * FROM [STAGING].[SWS2_123].[dbo].vwRespondents WHERE email = 'tst1001'";
            //sql = "SELECT";
            //ResultSet rs = stm.executeQuery(sql);
            //String strResult = "";
            //while (rs.next()) {
            //    strResult = rs.getString("ass_id_1");
            //    println(strResult);
            //}
            println("Connection Established.");
            String projectNumber = "165121";
            setSWS2DB(projectNumber);
            String surveyID = getSQLSurveyID(projectNumber);
            println(surveyID);
            getSQLQuestionInfo(surveyID);
            //rs.close();
            stm.close();
            con.close();
        }
        catch (ClassNotFoundException e) {
            System.out.println("Class not found.");
        }
        catch (SQLException f) {
            System.out.println("SQL Exception.");
        }
    }
    private String getSQLSurveyID(String projectNumber) throws SQLException {
        boolean hasSurveyID = false;
        String ret = "";
        String sql = "SELECT SURVEY_ID FROM [STAGING].[Mastersurvey].[dbo].SURVEY WHERE PROJ_NBR = '"+ projectNumber +"'";
        ResultSet rs = stm.executeQuery(sql);
        while(rs.next()) {
            hasSurveyID = true;
            ret = String.valueOf(rs.getInt("SURVEY_ID"));
        }
        rs.close();
        return ret;
    }
    
    private String [] getSQLQuestionInfo(String surveyID) throws SQLException {
        String [] ret = new String [3];
        int rowCount = 0;
        
        /*
         *select a.screen_id,a.question_id,b.question_label
        from SCREEN_QUESTION a
        inner join QUESTION b
        on a.question_id = b.question_id
        where a.survey_id = 11636
        and a.screen_id > 0
         */
        String sql = "SELECT count(a.SCREEN_ID) FROM [STAGING].[" + strDB + "].[dbo].SCREEN_QUESTION a INNER JOIN [STAGING].[" + strDB + "].[dbo].QUESTION b ON a.QUESTION_ID = b.QUESTION_ID WHERE a.SURVEY_ID = " + 
                surveyID +
                "AND a.SCREEN_ID > 0";
        ResultSet rs = stm.executeQuery(sql);
        
        while (rs.next()) {
            rowCount = Integer.valueOf(rs.getInt(1));
        }
        println("Row Count = " + String.valueOf(rowCount));
        rs.close();
        
        qArray = new CCQObject [rowCount];
        
        sql = "SELECT a.SCREEN_ID,a.QUESTION_ID,b.QUESTION_LABEL,b.QUES_TYPE_ID " +
                "FROM [STAGING].[" + strDB + "].[dbo].SCREEN_QUESTION a " +
                "INNER JOIN [STAGING].[" + strDB + "].[dbo].QUESTION b " +
                "ON a.QUESTION_ID = b.QUESTION_ID " +
                "WHERE a.SURVEY_ID = " + 
                surveyID +
                "AND a.SCREEN_ID > 0";
        rs = stm.executeQuery(sql);
        int i = 0;
        String SQL_QIDs = "";
        String tempComma = "";
        while(rs.next()) {
            qArray[i].sid = rs.getInt(1);
            qArray[i].qid = rs.getInt(2);
            qArray[i].qlabel = rs.getString(3);
            qArray[i].qtype = rs.getInt(4);
            qArray[i].multi = false;
            
            SQL_QIDs += tempComma + rs.getString(2);
            tempComma = ",";
            
            i++;
        }
        rs.close();
        /* Check If Question is a Multi-Select */
        sql = "SELECT count(*)" +
                "FROM [STAGING].[" + strDB + "].[dbo].CHOICES a" +
                "INNER JOIN [STAGING].[" + strDB + "].[dbo].QUESTION b" +
                "ON a.QUESTION_ID = b.QUESTION_ID" +
                "WHERE b.QUESTION_ID in (" +
                SQL_QIDs +
                ")" +
                "AND a.MAX_SELECTION > 1";
        rs = stm.executeQuery(sql);       
        while (rs.next()) {
            rowCount = Integer.valueOf(rs.getInt(1));
        }
        rs.close();
        
        sql = "SELECT b.QUESTION_ID,a.MAX_SELECTION" +
                "FROM [STAGING].[" + strDB + "].[dbo].CHOICES a" +
                "INNER JOIN [STAGING].[" + strDB + "].[dbo].QUESTION b" +
                "ON a.QUESTION_ID = b.QUESTION_ID" +
                "WHERE b.QUESTION_ID in (" +
                SQL_QIDs +
                ")" +
                "AND a.MAX_SELECTION > 1";
        rs = stm.executeQuery(sql);
        int tempQID;
        int tempMulti;
        while(rs.next()) {
            tempQID = rs.getInt(1);
            tempMulti = rs.getInt(2);
            for (int j = 0; j < qArray.length; j++) {
                if (qArray[j].qid == tempQID) {
                    qArray[j].multi = true;
                    break;
                }
            }
        }
        rs.close();
        return ret;
    }
    
    private int getQIndex(String QLabel) {
        int index = -1;
        for (int i = 0; i < qArray.length; i++) {
            if (QLabel.equalsIgnoreCase(qArray[i].qlabel)) {
                index = i;
                break;
            }
        }
        return index;
    }
    private String getQID(int index) {
        if (index != -1)
            return String.valueOf(qArray[index].qid);
        return "";
    }
    
    private String getSID(int index) {
        if (index != -1)
            return String.valueOf(qArray[index].sid);
        return "";
    }
    
    private String getQLabel(int index) {
        if (index != -1)
            return "\"" + qArray[index].qlabel + "\"";
        return "";
    }
    
    public String getQID(String QLabel) {
        return getQID(getQIndex(QLabel));
    }
    public String getSID(String QLabel) {
        return getSID(getQIndex(QLabel));
    }
    public String getQLabel(String QLabel) {
        return getQLabel(getQIndex(QLabel));
    }
    
    public CCQObject getQObject(String QLabel) {
        return qArray[getQIndex(QLabel)];
    }
    
    private void setSWS2DB(String projNum) {
        setDB("SWS2_" + projNum.substring(0,3));
    }
    private void setDB(String dbName) {
        strDB = dbName;
    }
    private static void println(String text) {
        System.out.println(text);
    }
    
}
