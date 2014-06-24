/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.xlkj.util;

/**
 *
 * @author xiaoff
 */

import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import com.xllj.domain.DeviceInfo;
import org.apache.log4j.Logger;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
public class ConfigUtil {
    private static String filename=System.getProperty("user.dir") + "/conf/devices.properties";
    private static List<DeviceInfo> list=new ArrayList<DeviceInfo>();
 //   private  Logger logger = LogUtil.getLogger();
   private static Logger logger = Logger.getLogger(ConfigUtil.class);
   
    public static List<DeviceInfo> getDevices(){
        
        Properties prop = new Properties();  
        InputStream in=null;
  try {   in= new BufferedInputStream(new FileInputStream(filename));
            prop.load(in);  
          for(int i=1;i<=prop.entrySet().size()/6;i++){
            DeviceInfo device=new DeviceInfo();
            device.setPort(Integer.parseInt(prop.getProperty("port_"+i)));
            device.setCollection_frq(Integer.parseInt(prop.getProperty("collection_frq_"+i)));
            device.setProtecol(prop.getProperty("proteclo_"+i));
            device.setSend_message(prop.getProperty("send_message_"+i));
            device.setDevice_flag(prop.getProperty("device_flag_"+i));
            device.setDevice_style(Integer.parseInt(prop.getProperty("device_style_"+i)));
            //device.setDevice_flag(prop.getProperty("device_flag_"+i));
            list.add(device);
           logger.info(device);
         }
          //System.out.println("loading times");
          logger.info("加载设备配置信息成功");
       return list; 
        } 
          catch (IOException e) { 
              logger.error("加载device信息异常,请检查配置文件");
           e.printStackTrace();  
       }finally{  
          try {  
              in.close();  
             
            } catch (IOException e) {  
               e.printStackTrace();  
          }  
        }  
        return null;  
    }  
    public static void main(String arsg[]){
    // ConfigUtil.getDevices();
    }

}
