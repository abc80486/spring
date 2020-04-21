package com.neo.test;

import java.sql.Connection;
import java.sql.SQLException;
 
import javax.sql.DataSource;
 
import com.mchange.v2.c3p0.ComboPooledDataSource;
 
public class C3P0Test {
 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DataSource ds = new ComboPooledDataSource();
		try {
			Connection conn = ds.getConnection();
			System.out.println("get connected!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
 
}
