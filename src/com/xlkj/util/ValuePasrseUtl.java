/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.xlkj.util;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

/**
 *
 * @author yuhaifang
 */
public class ValuePasrseUtl {
   // private int device_flag;
    private  List<Float> values=new ArrayList<Float>();
    public static void  main(String arsg[]){
         List<Float> values=new ArrayList<Float>();
        int device_flag=6;
        String str="05 03 08 1c be 1c 27 1b c3 07 b1 dd 72";
        String str2="01 03 04 09 69 04 c2 aa e2";
         System.out.println(new ValuePasrseUtl().getParseValue(str2, device_flag));
        
    }
    public  List<Float> getParseValue(String str,int device_flag)       
    {      
        if(device_flag==5)
            return setValues5(str);
        if(device_flag==6)
             return setValues6(str);
         if(device_flag==7)
             return setValues7(str);
        return null;
    }
    /*网视科技解析方法*/
    private  List<Float> setValues5(String str){
        str=str.trim().replace(" ","");//01 03 05 00 00 00 00
        
        float value;
       // String hex_str=str.substring(6, 10);//10 14 14 18
        //value=Integer.parseInt(str,16);
       // value=value/100-40;
     //   values.add(value);
        String hex_str="";
        for(int i=0;i<4;i++)
        {
            int j=4*i+6;
            hex_str=str.substring(j,j+4);
            value=Integer.parseInt(hex_str,16);
            value=value/100-40;
        //  int c=  Math.round(value*100)/100;
            values.add((float)(Math.round(value*100))/100);
        }
        return values;
    }
    /*电表线电流-相电流计算*/
    private  List<Float> setValues6(String str)
    {
          str=str.trim().replace(" ","");//01 03 05 00 00 00 00
          float value;
          String hex_str="";
        for(int i=0;i<2;i++)
        {
            int j=4*i+6;
            hex_str=str.substring(j,j+4);
            value=Integer.parseInt(hex_str,16);
            value=value/10;
             values.add((float)(Math.round(value*100))/100);
        }
        return values;
        
        //return null;
    }
    private List<Float>setValues7(String str)
    {
           str=str.trim().replace(" ","");//01 03 05 00 00 00 00
          float value;
          String hex_str="";
        for(int i=0;i<2;i++)
        {
            int j=4*i+6;
            hex_str=str.substring(j,j+4);
            value=Integer.parseInt(hex_str,16);
            value=value/100;
             values.add((float)(Math.round(value*100))/100);
        }
        
        return values;
        
    }
}
