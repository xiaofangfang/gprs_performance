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
import java.util.HashMap;
import org.apache.log4j.Logger;
import java.util.HashMap;
import java.util.Map;
import com.xllj.domain.Message;
/**
 *
 * @author yuhaifang
 */
public class MyThread {
    private HashMap<String,DeviceInfo> servers;
    private DeviceInfo dv;
    private  Message message;
     private  Logger thread_log = Logger.getLogger("thread1");
     private Socket socket;
     private  final String commId;
     private Map<String,Message> check_message=new HashMap<String,Message>();
    public MyThread(Socket socket,String commId) {
        this.socket = socket;
        this.commId=commId;
    }
    /*异步写入消息信息*/
    public synchronized  void setMessage(String str,long l){
        message.setAddtime(l);
        message.setHex(str);
         
    }
    
    /*检查设备名是否存在*/
    private  boolean checkDeviceName(){
        DeviceInfo device=servers.get(commId);
        if(device==null)
             return false;
       try
       {
           InputStream is=socket.getInputStream();
            int count=is.available();
            byte[]b=new byte[count];
            is.read(b);
            String devcie_name=new String(b);
            if(device.getDevice_flag().equals(devcie_name)){
                thread_log.info("识别设备号为:"+devcie_name);
                dv=device;
                return true;
            }  
       }catch(Exception e){
           e.printStackTrace();
        
       }
         thread_log.info("识别设备号异常");
        return false;
      
    }
    public void startRecSend(Thread t) throws Exception{
        if(!t.isAlive())
            t.start();
        /*如果设备信息匹配的话*/
        if( checkDeviceName())
        { 
          Thread t1=new Thread(new SendThread(socket.getOutputStream()));
          Thread t2=new Thread(new RecThread(socket.getInputStream()));
           t1.start();
           t2.start();
          /*启动一个Message对象检查情况对象*/
           thread_log.info("loggevent begain to log");
        }
       
    }
    
    public static void main(String ags[]){ 
        
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
            while(true){
                String hexstr=dv.getSend_message();
                try{
                    
                    os.write(Util.HexString2Bytes(hexstr));
                    os.flush();
                    thread_log.info("发送数据:"+hexstr);
                    Thread.sleep(dv.getCollection_frq());
                }
                catch(Exception e){
                    try{
                        os.close();
                        socket.close();
                        thread_log.info("发送异常+"+e.getMessage());
                        thread_log.info("设备异常，停止该设备");
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
          if(b.length<10)
          {
              thread_log.info("接受到设备发送的心跳包:"+str);
          }
     else{
             /*放入数据保存容器*/
              str=Util.Bytes2HexString(b);
              thread_log.info("接受到设备上传的数据:"+str);
              setMessage(str,System.currentTimeMillis());
          }  
         
       }
        catch(Exception e){
           try{
                is.close();
                socket.close();
                thread_log.info("接受异常:"+e.getMessage());
            }
            catch(Exception e2){
            }
            
               break;      
           }
        
      }
        }
    }
}
