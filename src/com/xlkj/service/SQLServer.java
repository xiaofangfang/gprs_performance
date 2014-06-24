/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.xlkj.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import com.xlkj.util.Util;
import com.xlkj.util.MySQLUtil;
import com.xllj.domain.DeviceInfo;
import com.xlkj.util.ValuePasrseUtl;
import java.util.ArrayList;
import java.util.List;
import com.xlkj.util.ValuePasrseUtl;
import java.sql.Timestamp;
/**
 *
 * @author yuhaifang
 * 该类主要提供数据的处理和保存
 */
public class SQLServer {
    private   Connection c=MySQLUtil.getConnection();
    private static final String sql="insert into caiji_value (flag,addtime,value1,value2,value3,value4)values(?,?,?,?,?,?)";
    private PreparedStatement ps=null;
    private  DeviceInfo device;
    private List<Float> values;
    private ValuePasrseUtl vu=new ValuePasrseUtl();
    private Timestamp date;
    private int count;
   public SQLServer(DeviceInfo device)
   {
       this.device=device;
       inintPre();
   }
   private void inintPre(){
   try{
       ps=MySQLUtil.getPstmt(c, sql);
   }catch(Exception e){
       e.printStackTrace();
   }
     
}
    public void saveValue(long time,String str)throws Exception{
        count++;
        /*获取时间*/
        date=new Timestamp(time);
       // String sql="insert into caiji_value (flag,addtime,value1,value2,value3,value4)values(?,?,?,?,?,?)";
       // c=MySQLUtil.getConnection();
        /*进行数据判断*/
        // ps=MySQLUtil.getPstmt(c, sql);
        if("--".equals(str))
        {     // System.out.println("hello");
             
            //  System.out.println("ps"+ps);
               ps.setInt(1, device.getDevice_style());
               ps.setTimestamp(2, date);
               ps.setString(3, "--");
               ps.setString(4, "--");
               ps.setString(5, "--");
               ps.setString(6, "--");
               
                      
         }
     else{  
            //System.out.println("device is "+device+"vu is "+vu);
             values=vu.getParseValue(str, device.getDevice_style());
             //  ps=MySQLUtil.getPstmt(c, sql);
               ps.setInt(1, device.getDevice_style());
               ps.setTimestamp(2, date);
                ps.setString(3, values.get(0)+"");
                 ps.setString(4,values.get(1)+"");
               if(values.size()<=2)
               {
                  
                   ps.setString(5, "--");
                   ps.setString(6, "--");
               }
               else{
                 
                   ps.setString(5, values.get(2)+"");
                   ps.setString(6, values.get(3)+"");
               }
               //System.out.println(values);
               values.clear();
        
        }
       if(count%20==0)
           ps.executeBatch();
       ps.addBatch();
       
       if(count%1000==0){
           ps.executeBatch();
           ps.close();
           c.close();
           c=MySQLUtil.getConnection();
           ps=MySQLUtil.getPstmt(c, sql);
       }
    }
    private void parseString(String str){
     
   }
}

