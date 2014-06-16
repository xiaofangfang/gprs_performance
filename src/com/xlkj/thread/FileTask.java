/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.xlkj.thread;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.TimerTask;
import java.util.Timer;
import java.util.Map;
import java.net.Socket;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.xlkj.util.Util;
/**
 *
 * @author Administrator
 */
public class FileTask extends TimerTask{
   //BufferedWriter bw= new BufferedWriter(new FileWriter("F:/test.txt"))
    private BufferedWriter bw;
    public FileTask(BufferedWriter bw){
        this.bw=bw;
    }
    public void run(){
        try{
            
            Util.againTongji();
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
   
}
