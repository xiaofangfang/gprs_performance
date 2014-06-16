/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.xlkj.thread;

import com.xllj.domain.DeviceInfo;
import com.xllj.domain.Message;
import java.util.HashMap;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class CommThread extends Thread{
    private HashMap<String,DeviceInfo> servers;
    private DeviceInfo dv;
    private  Message message;
    
    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
    private  Logger thread_log = Logger.getLogger("thread1");
    
    public synchronized  void changeMessage(Message message){
       long now=System.currentTimeMillis();
       long cost=now-message.getAddtime();
       if(cost>3){
           /*说明数据超时3秒中*/
           message.setHex("---");
           thread_log.info("此时未有数据上传 "+message.getHex());
       }
       else
            thread_log.info("此时未有数据上传 "+message.getHex());
          
    }
    public void run()
    {       
        while(true){
            changeMessage(message);
            try
            {
                Thread.sleep(3*10000);
            }catch(Exception e){
                thread_log.info("获取数据异常-"+e.getMessage());
            }
            
        }
    }
}
