/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.xlkj.util;
import java.sql.Connection;
import java.sql.DriverManager;
//import java.util.Date;
import java.sql.Date;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import java.sql.ResultSet;
/**
 *
 * @author yuhaifang
 */
public class MySQLUtil {
private static final  String driver = "com.mysql.jdbc.Driver";
private static final String url = "jdbc:mysql://192.168.1.137:3306/gprs";
private static final String user = "root";
private static final String password = "yuhaifang";
private static  Connection c=null;
//private static final int connection_size=6;
private static List<Connection> list=new ArrayList<Connection>();
private boolean isUser=false;
public static void  main(String args[])throws Exception{
     java.sql.Date  date=new Date(System.currentTimeMillis());
        String sql="insert into caiji_value(flag,addtime,value1,value2,value3,value4) values(6,?,'--','--','--','--')";
        c=MySQLUtil.getConnection();
        /*进行数据判断*/
         //  System.out.println("hello");
        PreparedStatement      ps=MySQLUtil.getPstmt(c, sql);
              System.out.println("ps"+ps);
           //    ps.setInt(1, device.getDevice_style());
               ps.setDate(1, date);
               ps.execute();
               ps.close();
}
public static synchronized Connection getConnection(){
    try{
        Class.forName(driver);
        c=DriverManager.getConnection(url, user, password);
    }catch(ClassNotFoundException e){
        e.printStackTrace();
    }catch(SQLException e1){
        e1.printStackTrace();
    }
      
    return c;
}
public static void close(Connection c){
    try{
        if(c!=null)
           c.close();
    }catch(Exception e){
        e.printStackTrace();
    }
}

public static void close(PreparedStatement ps){
    try{
        if(ps!=null)
           ps.close();
    }catch(Exception e){
        e.printStackTrace();
    }
}
public static void close(ResultSet rs){
    try{
        if(rs!=null)
           rs.close();
    }catch(Exception e){
        e.printStackTrace();
    }
}
 public static PreparedStatement getPstmt(Connection conn, String sql) throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = conn.prepareStatement(sql);
        return pstmt;
  }
public static void saveDebug0(String str){
     PreparedStatement statement;
   try{ 
       String sql="insert into gprs_debug1(d,biaoshi) values(?,?)";
       statement= c.prepareStatement(sql);
       //statement.setDate(0, data);
       Date d=new Date(System.currentTimeMillis());
       statement.setDate(1, d);
       statement.setString(2, str);
       statement.execute();
       statement.close();
   }catch(Exception e){
       
       e.printStackTrace();
   }
    
}

    
}
