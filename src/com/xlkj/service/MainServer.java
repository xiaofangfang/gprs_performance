/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.xlkj.service;
import com.xlkj.thread.MyThread;
import com.xllj.domain.DeviceInfo;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.HashMap;
import org.apache.log4j.Logger;
import java.util.ArrayList;
import com.xlkj.thread.CommThread;
import com.xlkj.util.ConfigUtil;

/**
 *
 * @author yuhaifang
 */
/*主服务启动类*/
public class MainServer {
    /*加载所有设备信息*/
   
   /**/
   private static final HashMap<String,DeviceInfo> servers=new HashMap<String,DeviceInfo>();
   /*设备信息与相对应的线程ID*/
  private static final HashMap<String,CommThread> threads=new HashMap<String,CommThread>();
  /**/
  private static final HashMap<String,List<DeviceInfo>> comm_device=new HashMap<String,List<DeviceInfo>>();
    
  public static HashMap<String, DeviceInfo> getServers() {
        return servers;
    }
   /*commId 生成器*/
   private List<String> comId=new ArrayList<String>();
   /*每个设备线程类定义*/
   private CommThread commThread;
   /*每一个服务定义一个Id标示*/
   private int countId=0;
   /*系统日志记录类*/
   private static final Logger system_log =Logger.getLogger(MainServer.class);
   /*所有的设备信息*/
   private List<DeviceInfo> devices=ConfigUtil.getDevices();
   //private static Logger thread_log = Logger.getLogger("thread1");
    private Logger getLogger(){
        countId++;
      return Logger.getLogger("thread"+countId);
    }
    
    /*启动所有服务*/
    public void startServer() {
        List<DeviceInfo> lists=new ArrayList<DeviceInfo>();
        for(DeviceInfo d:devices){
           String port=d.getPort()+"";
            if(d.getProtecol().equals("tcp")){
       try{     
              ServerSocket server=null;
              /*commId为comm线程识别标识码*/
              String commId=d.getDevice_flag();
           if(!comm_device.containsKey(port))
             {
                server=new ServerSocket(d.getPort());
                MyServer s=new MyServer(server);
                s.start();
             }
                
                /*初始化comm线程*/
                commThread=new CommThread(d);
                commThread.setDaemon(true);
              //  commThread.setDv(d);
                commThread.setThread_log(getLogger());
                  commThread.start();
                /*comm线程初始化完毕*/
                /*线程对象添加到线程集合*/
                threads.put(commId, commThread);
                /**/
                system_log.info("设备型号："+d.getDevice_flag()+",设备端口："+d.getPort()+" --启动成功");
               // lists.add(d);(暂时不知道这个有什么用)
                comm_device.put(port,lists );
                /*启动一个任务检查器不定时检查*/
                }catch(Exception e){
                   // e.printStackTrace();
                 system_log.info(d.getPort()+"设备启动异常："+e.getMessage());
                }
            }
            if(d.getProtecol().equals("UDP"))
            {
                
            }
            
        }
        
        
    }
    
    /*接受socke线程*/
    class MyServer extends Thread{
         private ServerSocket ss;
        public MyServer(ServerSocket ss) {
            this.ss = ss;
        } 
        public void run(){
          while(true){  
          try{
              Socket socket=ss.accept();
              system_log.info("新客户端连接");
               MyThread mt=new MyThread(socket);
               mt.startRecSend(threads);
              }catch(Exception e){
                system_log.info("新客户端连接异常："+e.getMessage());
              }
            
            
        }
        }
    }
}
