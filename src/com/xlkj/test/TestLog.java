

package com.xlkj.test;
import org.apache.log4j.Logger;  

  
  
public class TestLog  
{  
    // 这是主线程的Logger，这些不需独立日志的类也可以创建为普通的Logger，通过配置文件配置参数  
    static Logger logger = Logger.getLogger("xio");
  
    public TestLog() {}  
  
      
    public static void main(String[] args)  
    {  
       // logger.warn(TestLog.class + " started!");  
          
        ThreadBody threadBody = new ThreadBody();  
        for(int i=0; i<5; ++i) {  
            new Thread(threadBody).start();  
            Thread t=new TestThread();
        new Thread(t).start();
        }  
        
  
        logger.debug("this is debug");  
       // logger.info("this is info");  
       // logger.warn("this is warn");  
        //logger.error("this is error");  
    }  
  
}  
  
class ThreadBody implements Runnable  
{  
    public ThreadBody() {}  
  
      
    @Override  
    public void run()  
    {  
        // 注意线程独立的Logger实例要在run方法内实现  
        Logger logger =Logger.getLogger("test");
          
        logger.warn(Thread.currentThread().getName() + " started!");  
          
        logger.debug("this is debug");  
        logger.info("this is info");  
        logger.info("this is info");  
        logger.info("this is info");  
        logger.info("this is info");  
        logger.warn("this is warn");  
        logger.error("this is error");  
  
        logger.warn(Thread.currentThread().getName() + " finished!");  
    }  
      
}  
 