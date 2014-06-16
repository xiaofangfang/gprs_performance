/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.xlkj.util;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Administrator
 */
public class FilUtil {
    public static String createFileName() {
       
    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH点mm至");
    SimpleDateFormat format2 = new SimpleDateFormat("HH点mm");
    Date d=new Date();
    String str1=format1.format(d);
    d.setHours(d.getHours()+2);
    String str=str1+format2.format(d)+".txt";
      return str;
   }
    public static void saveCasue(String message,long connectTime,long allconnectTime,String result)throws Exception{
       BufferedWriter bw=new BufferedWriter(new FileWriter(FilUtil.createFile("F:/log/message")));
       SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH点mm");
       String str=format1.format(new Date());
       bw.write(str+"此时已断开连接，本次连接时间为:"+connectTime+" 总的连接时间为:"+allconnectTime+"原因为："+message);
       bw.newLine();
       bw.write(result);
       bw.flush();
       bw.close();
      // bw.write("断开连接的原因"+);
        
    }
  public static String createFile( String path){
		
		//path表示你所创建文件的路径
		//String path = "F:/log";
		File f = new File(path);
		if(!f.exists()){
		  f.mkdirs();
		} 
		// fileName表示你创建的文件名；为txt类型；
                Date d=new Date();
                int time=d.getHours();
		String fileName=createFileName();
		File file = new File(f,fileName);
                
		if(!file.exists()){
			try {
				file.createNewFile();
                                return file.getPath();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
                return file.getPath();
		
	}
}
