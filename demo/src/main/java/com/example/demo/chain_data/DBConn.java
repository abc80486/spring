package chain_data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//8.6
public class DBConn{
	public static Connection con;
	public static ResultSet rs;
	
 	public static int db_op(String sql){
		Statement sm ;
        int re = 0;
        try{
        	sm = con.createStatement();
            re = sm.executeUpdate(sql);//·µ»ØÐÐÊý£»
            if (re < 0){
                con.rollback();
                sm.close();
                return re;
            }
        }catch (Exception e){
        	System.out.println("DATABASE OPERATION ERROR!");
        	return -1;
        }
        try {
			sm.close();
		} catch (SQLException e) {
        	System.out.println("DATABASE STATEMENT CLOSE ERROR!");
        	return -1;
		}
        return re;
    }
	
	public static ResultSet db_sl(String select){
		//Statement sm ;

        try{
            rs = con.createStatement().executeQuery(select);
             if (!rs.next()){
             return null;
             }
        }catch (Exception e){
        	System.out.println("DATABASE QUERY ERROR!");
            return null;
        }
         return rs;
    }
	
	public static Connection db_con() { 
	        String driver = "com.mysql.jdbc.Driver";
	        
	        String url = "jdbc:mysql://47.75.73.95:3306/ok";
	        
	        String user = "root";
	        
	        String password = "123456";	
	        
	        try {
                Class. forName(driver);
            
            } catch (ClassNotFoundException e ) {
            	con = null;
                System.out.println("DATABASE CONNECT ERROR CLASS NOT FOUND EXCEPTION!");
            }
	        
	        try {
	               con = DriverManager. getConnection(url, user, password); 
	        } catch (SQLException e ) {
	                      con = null;
	                      System.out.println("DATABASE CONNECT ERROR SQLEXCEPTION!");
	        }
	         return con ;   
	}
}