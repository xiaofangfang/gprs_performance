/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.xlkj.test;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class TestLog {
    private static Logger logger = Logger.getLogger(TestLog.class);
    private static Logger comm1 = Logger.getLogger("thread1");
    private static Logger comm2 = Logger.getLogger("thread2");
    public static void main(String[] args) {  

        System.out.println("hello world");  // 记录debug级别的信息          
        logger.debug("This is debug message.");           // 记录info级别的信息          
        logger.info("This is info message.");           // 记录error级别的信息          
        logger.error("This is error message.");
        comm1.error("hello");
        comm1.info("comm1 begin to log");
        comm2.info("comm2 begin to log");
    }
}
