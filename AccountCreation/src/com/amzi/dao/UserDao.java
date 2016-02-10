package com.amzi.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
	
	/*returns 0 if account successfully added, 1 if exception thrown, 2 if duplicate username, 3 if duplicate email*/
	public static int insertNewUser(String user, String userpass, String email){
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
            pst=conn.prepareStatement("select * from users where username like binary ?");
            pst.setString(1, user);
            rs=pst.executeQuery();
            if(rs.isBeforeFirst()){/*if this username is in use, return 2*/
            	status=2;
            	return status;
            }
            pst.close();
            rs.close();
            /*check if account with same email exists*/
            pst=conn.prepareStatement("select * from users where email like binary ?");
            pst.setString(1, email);
            rs=pst.executeQuery();
            if(rs.isBeforeFirst()){/*if this email is in use, return 2*/
            	status=3;
            	return status;
            }
            pst.close();
            rs.close();
            /*update users table with new account*/
            pst = conn
                    .prepareStatement("insert into users (username, password, email) values (?, ?, ?);");
            pst.setString(1, user);
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
	
	public static boolean loginValidate(String user, String pass) {        
        boolean status = false;
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

            pst = conn
                    .prepareStatement("select * from users where username like binary ? and password like binary ?");
            pst.setString(1, user);
            pst.setString(2, pass);

            rs = pst.executeQuery();
            status = rs.next();

        } catch (Exception e) {
            System.out.println(e);
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
	
	public static boolean deleteUser(String user, String pass) {        
        boolean status = false;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int user_id=-1;

        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "mydb";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String password = "elfwood";
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager
                    .getConnection(url + dbName, userName, password);

            pst = conn
                    .prepareStatement("select * from users where username like binary ? and password like binary ?");
            pst.setString(1, user);
            pst.setString(2, pass);

            rs = pst.executeQuery();
            status = rs.next();
            
            if(status==false)
            	return status;
            else
            	user_id=rs.getInt("user_id");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
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
        try{
            pst = conn
                    .prepareStatement("delete from users_groups where user_id=?");
            pst.setInt(1, user_id);

            pst.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        
        try{
            pst = conn
                    .prepareStatement("delete from users where username like binary ?");
            pst.setString(1, user);

            pst.executeUpdate();
            status = true;

        } catch (Exception e) {
            System.out.println(e);
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
        }
        return status;
    }
	
}
