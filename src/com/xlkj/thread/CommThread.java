/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.xlkj.thread;


import com.xllj.domain.Message;
import java.util.HashMap;
import org.apache.log4j.Logger;
import java.util.Collections;
import java.util.Map;
import com.xllj.domain.DeviceInfo;
import java.net.Socket;
import com.xlkj.service.SQLServer;
/**
 *
 * @author Administrator
 */
public class CommThread extends Thread{
    
    public DeviceInfo getDv() {
        return dv;
    }

    public void setDv(DeviceInfo dv) {
        this.dv = dv;
    }
    
    /*超时时间*/
     private static final int socket_timeout=120;
     /*线程log对象*/
     private  Logger thread_log;
     private String commId;
     private Map<String,Message> current_message=Collections.synchronizedMap(new HashMap<String,Message>()) ;
     private DeviceInfo dv;
     private Socket socket;
     //
     private SQLServer sqlServer;
    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
     public Map<String, Message> getCurrent_message() {
        return current_message;
    }
    
    
    public Logger getThread_log() {
        return thread_log;
    }

    public void setThread_log(Logger thread_log) {
        this.thread_log = thread_log;
    }
    public String getCommId() {
        return commId;
    }

    public void setCommId(String commId) {
        this.commId = commId;
    }
    /*自定义线程ID变量*/
   
    public CommThread(DeviceInfo dv) {
        /*创建该线程实例的时候初始化变量*/
        this.dv=dv;
       // sqlServer=new SQLServer();
        initMessage();
        sqlServer=new SQLServer(dv);
    }
    
    
    private void initMessage(){
        Message message=new Message();
        message.setAddtime(System.currentTimeMillis());
        message.setHex("--");
        current_message.put(commId, message);
 
    }
  
   
    
    public synchronized  void changeMessage(Message message){
       long now=System.currentTimeMillis();
       long cost=now-message.getAddtime();
      // System.out.println("cost="+cost);
      
      if(cost>socket_timeout*1000&&socket!=null&&!socket.isClosed())
       {
          
            try{
                socket.close();
            }catch(Exception e){
                e.printStackTrace();
               thread_log.info("设备关闭异常");
            }
           thread_log.info("设备连接超时120S，关闭该设备");
           //System.out.println("设备超时异常，关闭该设备");
       }
     else  if(cost>dv.getCollection_frq()*1000+1000){
           /*说明数据超时3秒中*/
           message.setHex("--");
          // thread_log.info("此时未有数据上传 ");
           try{
               sqlServer.saveValue(now,message.getHex());
           }catch(Exception e){
               e.printStackTrace();
           }
       }
    else   
     {
         thread_log.info("此时有数据上传 "+message.getHex());
         try{
         sqlServer.saveValue(message.getAddtime(),message.getHex());
      }catch(Exception e){
          e.printStackTrace();
          thread_log.info("数据保存至数据库异常");
      }
     }
      
          
    }
    public void run()
    {       
        thread_log.info("采集数据线程启动--------------------");
       // System.out.println(commId+":"+current_message.get(commId));
        while(true){
           changeMessage(current_message.get(commId));
            try
            {
                Thread.sleep(dv.getCollection_frq()*1000);
            }catch(Exception e){
                thread_log.info("获取数据异常-"+e.getMessage());
            }
            
        }
    }
}
