/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.xlkj.util;
import java.util.Properties; 
import java.io.InputStream;   
import java.io.IOException;  
import java.util.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author Administrator
 */
public class Util {
    public Util() { 
} 
    
 
  
public static double getA(List<Double> list){
     Iterator it=list.iterator();
     double l=0;
     int count=0;
     while(it.hasNext()){
         double ll=(Double)it.next();
         l=l+ll;
         count++;
     }
     if(count==0){
         return 0;
     }
     double result=l/count;
     result=(double)(Math.round(result*100))/100;
    // float b = (float)(Math.round(a*100))/100
    return result;
}
//private static =null;
    private static String param1;   
    private static String param2;   
   private static  BufferedWriter bw=null;
   private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   private static String now="";
   private  static String allvalue="";
   //private ThreadLocal<BufferedWriter> bd=new ThreadLocal<>();
   public static BufferedWriter  getBufferWriter(){
       return bw;
   }
    static {   
       
        try{ 
            //Timer t =new Timer();
                     // t.schedule(new FileTask(Util.getBufferWriter()), 20000,20000);
            bw=new BufferedWriter(new FileWriter(FilUtil.createFile("F:/log")));
           //bw.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
       // InputStream in = Util.class.getResourceAsStream("test.txt");   
        
    } 
    public static void againTongji(){
        closeWriter();
       try
       {
           bw=new BufferedWriter(new FileWriter(FilUtil.createFile("F:/log")));
       }
       catch(Exception e){
           e.printStackTrace();
       }
    }
    public static void closeWriter(){
        try{
            bw.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void setPropertie(String name,String value){
        //prop.setProperty(name, value);
    }
    
    /*保存日志情况，保存数据记录情况*/
    public static void saveValue(byte[]b){
                    /*开始统计分析*/
                     double value=Util.bytes2int(Util.getByte(b, 4))/1000.0;
                     if(value>=2147483.648){
                         value=value-4294967296.00;
                     }
                     double value1=Util.bytes2int(Util.getByte(b, 8))/1000.0;
                      if(value1>=2147483.648){
                         value1=value1-4294967296.00;
                     }
                     double value2=Util.bytes2int(Util.getByte(b, 12))/1000.0;
                      if(value2>=2147483.648){
                         value2=value2-4294967296.00;
                     }
                     double value3=Util.bytes2int(Util.getByte(b, 16))/1000.0;
                      if(value3>=2147483.648){
                         value3=value3-4294967296.00;
                     }
        
         now = format.format(new Date());
         allvalue=now+ " 第一路："+value+" 第二路："+value1+" 第三路："+value2+" 第四路："+value3;
          // System.out.println(allvalue);
          try{ 
                  bw.write(allvalue);
                  bw.newLine();
                  bw.flush();
                  
          }
          catch(Exception e){
              e.printStackTrace();
              try{
                  bw.close();
              }catch(Exception e1){
                  e1.printStackTrace();
              }
          }
    }
    
    
  public static int bytes2int(byte b[]) {
  int s = 0;
  s = ((((b[0] & 0xff) << 8 | (b[1] & 0xff)) << 8) | (b[2] & 0xff)) << 8
    | (b[3] & 0xff);
  return s;
 }
/** 
* 将指定byte数组以16进制的形式打印到控制台 
* @param hint String 
* @param b byte[] 
* @return void 
*/ 
public static void printHexString(String hint, byte[] b) { 
System.out.print(hint); 
for (int i = 0; i < b.length; i++) { 
String hex = Integer.toHexString(b[i] & 0xFF); 
if (hex.length() == 1) { 
hex = '0' + hex; 
} 
System.out.print(hex.toUpperCase() + " "); 
} 
System.out.println(""); 
} 

/** 
* 
* @param b byte[] 
* @return String 
*/ 
public static String Bytes2HexString(byte[] b) { 
String ret = ""; 
for (int i = 0; i < b.length; i++) { 
String hex = Integer.toHexString(b[i] & 0xFF); 
if (hex.length() == 1) { 
hex = '0' + hex; 

} 
ret += hex.toUpperCase(); 
} 
return ret; 
} 
public static byte[] getByte(byte[] b,int begain){
       byte[] bufferbyte= new byte[4];
   // for(int i=0;i<b.length;i++){
       // bufferbyte[]
      bufferbyte[0]=b[begain-1];
       bufferbyte[1]=b[begain];
        bufferbyte[2]=b[begain+1];
         bufferbyte[3]=b[begain+2];
   // }
    return bufferbyte;
}

/** 
* 将两个ASCII字符合成一个字节； 
* 如："EF"--> 0xEF 
* @param src0 byte 
* @param src1 byte 
* @return byte 
*/ 
public static byte uniteBytes(byte src0, byte src1) { 
byte _b0 = Byte.decode("0x" + new String(new byte[]{src0})).byteValue(); 
_b0 = (byte)(_b0 << 4); 
byte _b1 = Byte.decode("0x" + new String(new byte[]{src1})).byteValue(); 
byte ret = (byte)(_b0 ^ _b1); 
return ret; 
} 

/** 
* 将指定字符串src，以每两个字符分割转换为16进制形式 
* 如："2B44EFD9" --> byte[]{0x2B, 0x44, 0xEF, 0xD9} 
* @param src String 
* @return byte[] 
*/ 
public static byte[] HexString2Bytes(String src){ 
src=src.replace(" ", "");
byte[] ret = new byte[src.length()/2]; 
byte[] tmp = src.getBytes(); 
for(int i=0; i<ret.length; i++){ 
ret[i] = uniteBytes(tmp[i*2], tmp[i*2+1]); 
} 
return ret; 
} 

public static void main(String ars[]){
    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH点mm至");
    SimpleDateFormat format2 = new SimpleDateFormat("HH点mm");
    Date d=new Date();
    String str1=format1.format(d);
    d.setHours(d.getHours()+9);
    String str=format2.format(d);
    System.out.println(str1+str);
   // printHexString("05 01 00 00 00 06 BD 8C",HexString2Bytes("050100000006BD8C"));
}

}
