/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.xlkj.thread;
import com.xlkj.util.Util;
import com.xllj.domain.DeviceInfo;
import java.io.*;
import java.net.Socket;
import org.apache.log4j.Logger;
import java.util.HashMap;
import java.util.Map;
import com.xllj.domain.Message;
import com.xlkj.service.MainServer;

/**
 *
 * @author yuhaifang
 */
public class MyThread {
    private HashMap<String,DeviceInfo> servers=MainServer.getServers();
    private DeviceInfo dv;
    private  Message message;
     private  Logger thread_log;
     private Socket socket;
     private String commId;
     /*每个设备号对应一个Commthread*/
     private static final Logger system_logger=Logger.getLogger(MyThread.class);
     private static  HashMap<String,CommThread> threads=null;
     private CommThread commThread;
   //  private  final String commId;
    private Map<String,Message> check_message=null;
    public MyThread(Socket socket) {
        this.socket = socket;
       // this.commId=commId;
    }
    /*异步写入消息信息*/
    public synchronized  void setMessage(String str,long l){
        message=check_message.get(commId);
        message.setAddtime(l);
        message.setHex(str);
        check_message.put(commId, message);
    }
    
    /*检查设备名是否存在*/
    private  boolean checkDeviceName(){
      //  DeviceInfo device=servers.get(commId);
     //   if(device==null)
           //  return false;
        String devcie_name="";
       try
       {
           InputStream is=socket.getInputStream();
           int count=0;
            while(count==0)
          count=is.available();
            byte[]b=new byte[count];
            is.read(b);
            devcie_name=new String(b);
          //  System.out.println("the device_name is"+devcie_name);
            devcie_name= devcie_name.replaceAll("[\\t\\n\\r]", "");//
            if(threads.containsKey(devcie_name)){
                 commThread=threads.get(devcie_name);
                 thread_log=commThread.getThread_log();
                 commThread.setSocket(socket);
                 commId=commThread.getCommId();
                 thread_log.info("识别设备号为:"+devcie_name);
                 system_logger.info("识别设备号为:"+devcie_name);
                 dv=commThread.getDv();
                 check_message=commThread.getCurrent_message();
                return true;
            }  
            
       }catch(Exception e){
           e.printStackTrace();
        
       }
         system_logger.info("无法识别该设备号:"+devcie_name);
        return false;
      
    }
    public void startRecSend(HashMap<String,CommThread> comms) throws Exception{
        if(threads==null)
          threads=comms;
        if( checkDeviceName())
        { 
          Thread t1=new Thread(new SendThread(socket.getOutputStream()));
          Thread t2=new Thread(new RecThread(socket.getInputStream()));
           t1.start();
           t2.start();
          /*启动一个Message对象检查情况对象*/
           thread_log.info("收发线程启动");
        }
       
    }
    class SendThread implements Runnable{

        public OutputStream getOs() {
            return os;
        }

        public void setOs(OutputStream os) {
            this.os = os;
        }
        private OutputStream os;
        public SendThread(OutputStream os){
            this.os=os;
        }
        public void run(){
             String hexstr="";
            while(true){
                hexstr=dv.getSend_message();
                try{
                    
                    os.write(Util.HexString2Bytes(hexstr));
                    os.flush();
                    thread_log.info("向DTU设备发送数据: "+hexstr);
                   // System.out.println("发送数据为:"+hexstr);
                    Thread.sleep(dv.getCollection_frq()*1000);
                }
                catch(Exception e){
                    try{
                        os.close();
                        socket.close();
                        thread_log.info("发送异常+"+e.getMessage());
                        thread_log.info("设备连接异常，停止该设备");
                    }catch(Exception e2){
                    }
                   break;
                    
                }
            }
        }
    }
    /*接受线程*/
    class RecThread implements Runnable{

        public InputStream getIs() {
            return is;
        }

        public void setIs(InputStream is) {
            this.is = is;
        }

        public RecThread(InputStream is) {
            this.is = is;
        }
        private InputStream is;
        
        public void run(){
            while(true){
            int count=0;
     try{
            while(count==0)
          {
                 count=is.available();
          }
          byte[] b=new byte[count];  
          is.read(b);
          String str=new String(b);
         // Util.Bytes2HexString(b)
          if(b.length<=3)
          {
              thread_log.info("接收DTU设备发送的心跳包: "+Util.hex2Hex(Util.Bytes2HexString(b)));
           }
     else{
             /*放入数据保存容器*/
              str=Util.Bytes2HexString(b);
              thread_log.info("接收到DTU设备上传的数据: "+Util.hex2Hex(str));
             // System.out.println("接收到DTU设备上传的数据: "+str);
              setMessage(str,System.currentTimeMillis());
          }  
         
       }
        catch(Exception e){
           try{
                is.close();
                socket.close();
                thread_log.info("服务器接收异常:"+e.getMessage());
            }
            catch(Exception e2){
            }
            
               break;      
           }
        
      }
        }
    }
}
