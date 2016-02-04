package com.amzi.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignUpDao {
	
	/*returns 0 if account successfully added, 1 if exception thrown, 2 if duplicate username, 3 if duplicate email*/
	public static int addAccount(String username, String userpass, String email){
		int status = 1;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "mydb";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String password = "elfwood";
		
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager
                    .getConnection(url + dbName, userName, password);
            /*check if account with same username exists*/
            pst=conn.prepareStatement("select * from users where username=?");
            pst.setString(1, username);
            rs=pst.executeQuery();
            if(rs.isBeforeFirst()){/*if this username is in use, return 2*/
            	status=2;
            	return status;
            }
            pst.close();
            rs.close();
            /*check if account with same email exists*/
            pst=conn.prepareStatement("select * from users where email=?");
            pst.setString(1, email);
            rs=pst.executeQuery();
            if(rs.isBeforeFirst()){/*if this username is in use, return 2*/
            	status=2;
            	return status;
            }
            pst.close();
            rs.close();
            /*update users table with new account*/
            pst = conn
                    .prepareStatement("insert into users (username, password, email) values (?, ?, ?);");
            pst.setString(1, username);
            pst.setString(2, userpass);
            pst.setString(3, email);

            pst.executeUpdate();
            status = 0;

        } catch (Exception e) {
            System.out.println(e);
            status=1;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return status;
    }

}
