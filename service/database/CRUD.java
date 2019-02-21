
package pl.polsl.lab.service.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * Class which is responsible for basic operation on database.
 * 
 * @author Dawid Kampa
 * @version 5.1
 */
public class CRUD {    
    
        /**
         * Insert new record to database
         * 
         * @param con connection to DataBase
         * @param palindrome palindrome which is send to database
         * @param isCreated information is palindrome created
         * @param isCorrect information is palindrome correct
         * @throws SQLException
        */
        public void create(Connection con,String palindrome, Boolean isCreated, Boolean isCorrect) throws SQLException{
            
        Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        ResultSet rs = statement.executeQuery("SELECT * FROM PALINDROMHISTORY"); 
        
        if(rs.next()){
            rs.last();
            int nextId =rs.getInt("ID") + 1;
            rs.moveToInsertRow();
            rs.updateInt("ID", nextId);
            rs.updateTimestamp("Date", timestamp);
            rs.updateString("PALINDROME", palindrome);
            rs.updateBoolean("isCreated", isCreated);
            rs.updateBoolean("isCorrect", isCorrect);
            rs.insertRow();
            rs.moveToCurrentRow();
        }else{
            rs.moveToInsertRow();
            rs.updateInt("ID", 0);
            rs.updateTimestamp("Date", timestamp);
            rs.updateString("PALINDROME", palindrome);
            rs.updateBoolean("isCreated", isCreated);
            rs.updateBoolean("isCorrect", isCorrect);
            rs.insertRow();
            rs.moveToCurrentRow();
        } 
    }
        
        /**
         * Read all records from database
         * 
         * @param con connection to DataBase
         * @param responseFromDataBase responseFromDataBase
         * @throws SQLException
        */
        public void read(Connection con,StringBuilder responseFromDataBase) throws SQLException{
        
        Statement statement = con.createStatement();    
        ResultSet rs = statement.executeQuery("SELECT * FROM PALINDROMHISTORY"); 
        while (rs.next()) {
            responseFromDataBase.append("<tr>");
            responseFromDataBase.append("<td>").append(rs.getInt("id")).append("</td>");
            responseFromDataBase.append("<td>").append(rs.getTimestamp("Date")).append("</td>");
            responseFromDataBase.append("<td>").append(rs.getString("Palindrome")).append("</td>");
            responseFromDataBase.append("<td>").append(rs.getBoolean("isCorrect")).append("</td>");
            responseFromDataBase.append("<td>").append(rs.getBoolean("isCreated")).append("</td>");
            responseFromDataBase.append("</tr>");
        }            
    }
    
        /**
         * delete record from database
         * 
         * @param con connection to DataBase
         * @param id which recod will be delete
         * @throws SQLException
        */
        public void delete(Connection con,int id) throws SQLException{
        Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

        String command = "DELETE FROM PALINDROMHISTORY WHERE ID=" + id;
        statement.executeUpdate(command);
        id++;
        ResultSet rs = statement.executeQuery("SELECT * FROM PALINDROMHISTORY WHERE ID="+id);
        
            do{
                rs.updateInt("id", rs.getInt("id")- 1);
                rs.updateRow();
            }while (rs.next());
        }
       
        
}
