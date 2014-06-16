/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.xlkj.thread;
import java.util.TimerTask;
import java.util.Timer;
import java.util.Map;
import java.net.Socket;
/**
 *
 * @author Administrator
 */
public class MyTask extends TimerTask{
    private Map<String,Long> maps;
    private Socket socket;
    private String devicname;
    public MyTask(Map<String,Long>maps,Socket socket,String devicname){
        this.maps=maps;
        this.socket=socket;
        this.devicname=devicname;
    }
    public void run(){
        long now=System.currentTimeMillis();
        long lastTime=maps.get(devicname);
        System.out.println("now-lasttime"+(now-lastTime));
        if(now-lastTime>20000){
          //  closeSocket(socket);
            System.out.println("对方socket已关闭");
            this.cancel();
        }
        
    }
    public void closeSocket(Socket socket){
        try{
           // socket.getInputStream().close();
           // socket.getOutputStream().close();
            socket.close();
            
        }
        catch(Exception e){
            System.out.println("关闭异常");
            e.printStackTrace();
        }
        
    }
    public static void main(String[] args){
      //  MyTask task=new MyTask();
        Timer time=new Timer();
       // time.schedule(task,1000);
    }
}
