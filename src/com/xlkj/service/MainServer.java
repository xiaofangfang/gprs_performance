/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.xlkj.service;
import com.xlkj.test.TestLog;
import com.xlkj.thread.MyThread;
import com.xllj.domain.DeviceInfo;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.HashMap;
import org.apache.log4j.Logger;
import java.util.ArrayList;
import com.xlkj.thread.CommThread;
/**
 *
 * @author yuhaifang
 */
/*主服务启动类*/
public class MainServer {
    /*加载所有设备信息*/
   private List<DeviceInfo> devices;
   /*设备信息与相对应的线程ID*/
   private HashMap<String,DeviceInfo> servers;
   /*commId 生成器*/
   private List<String> comId=new ArrayList<String>();
   private Thread commThread;
   /*每一个服务定义一个Id标示*/
   private int countId=0;
   /*系统日志记录类*/
   private static Logger system_log = Logger.getLogger(MainServer.class);
    private static Logger thread_log = Logger.getLogger("thread1");
    /*启动所有服务*/
    public void startServer() {
        for(DeviceInfo d:devices){
            if(d.getProtecol().equals("tcp")){
       try{
                ServerSocket server=new ServerSocket(d.getPort());
                /*定义线程ID*/
                String commId=getCommId();
                MyServer s=new MyServer(server,commId);
                servers.put(commId, d);
                s.start();
                system_log.info(d.getDevice_flag()+"设备，"+d.getPort()+" ,启动成功");
                /*启动一个任务检查器不定时检查*/
                
                
                }catch(Exception e){
                 system_log.info(d.getPort()+"设备启动异常："+e.getMessage());
                }
            }
            if(d.getProtecol().equals("UDP"))
            {
                
            }
            
        }
        
        
    }
    
    /*获取id*/
    private String getCommId(){
        countId++;
        return "commId-"+countId;
    }
    /*接受socke线程*/
    class MyServer extends Thread{
         private ServerSocket ss;
         private String commId;
        public MyServer(ServerSocket ss,String commId) {
            this.ss = ss;
            this.commId=commId;
        } 
        public void run(){
          while(true){  
          try{
              Socket socket=ss.accept();
              system_log.info("新客户端连接");
              thread_log.info("新客户端连接");
             // System.out.println("新客户端连接 "+socket.getPort());
              /*交给新的收发线程处理*/
             // InputStream is=socket.getInputStream(); 
              /*交给新的收发线程处理*/
              commThread=new CommThread();
              MyThread mt=new MyThread(socket,commId);
              commThread.setDaemon(true);
               mt.startRecSend(commThread);
              }catch(Exception e){
                
               e.printStackTrace();
                system_log.info("新客户端连接异常："+e.getMessage());
                thread_log.info("新客户端连接异常："+e.getMessage());
              }
            
            
        }
        }
    }
}
