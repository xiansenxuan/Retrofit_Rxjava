package rx.xuan.com.retrofit_rxjava.utils;

import android.util.Log;

import java.util.Hashtable;

/** 
 * The class for print log 
 * @author kesenhoo 
 * 
 */  
public class MyLogger  
{  
    private final static boolean                logFlag         = true;  //调试模式  true  还是上线模式 false
      
    public final static String                  tag             = "PrintSystem";//tag标记  
    private final static int                    logLevel        = Log.INFO; //日志级别
    //集合
    private static Hashtable<String, MyLogger>    sLoggerTable    = new Hashtable<String, MyLogger>();  
    private String                              mClassName;  
    
    //不同开发人员的日志使用对象
    private static MyLogger                     systemlog;  //系统log
    private static MyLogger                     xuanlog;  //我的log
      //开发人员的名字  
    private static final String                 system           = "@system@ ";  
    private static final String                 xiansenxuan           = "@xiansenxuan@ ";  
      
    private MyLogger(String name)  
    {  
        mClassName = name;  
    }  
      
    /** 
     *  
     * @param className 
     * @return 
     */  
    @SuppressWarnings("unused")  
    private static MyLogger getLogger(String className)  
    {  
        MyLogger classLogger = (MyLogger) sLoggerTable.get(className);  
        if(classLogger == null)  
        {  
            classLogger = new MyLogger(className);  
            sLoggerTable.put(className, classLogger);  
        }  
        return classLogger;  
    }  
      
    /** 
     * Purpose:Mark user one 
     * @return 
     */  
    public static MyLogger systemlLog()  
    {  
        if(systemlog == null)  
        {  
        	systemlog = new MyLogger(system);  
        }  
        return systemlog;  
    }  
    /** 
     * Purpose:Mark user two 
     * @return 
     */  
    public static MyLogger xuanLog()  
    {  
        if(xuanlog == null)  
        {  
        	xuanlog = new MyLogger(xiansenxuan);  
        }  
        return xuanlog;  
    }  
      
    /** 
     * Get The Current Function Name 
     * @return 
     */  
    private String getFunctionName()  
    {  
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();  
        if(sts == null)  
        {  
            return null;  
        }  
        for(StackTraceElement st : sts)  
        {  
            if(st.isNativeMethod())  
            {  
            	//本地方法native  jni
                continue;  
            }  
            if(st.getClassName().equals(Thread.class.getName()))  
            {  
            	//线程  
                continue;  
            }  
            if(st.getClassName().equals(this.getClass().getName()))  
            {  
            	//构造方法
                continue;  
            }  
            return mClassName + "[ " + Thread.currentThread().getName() + ": "  
                    + st.getFileName() + ":" + st.getLineNumber() + " "  
                    + st.getMethodName() + " ]";  
        }  
        return null;  
    }  
      
    /** 
     * The Log Level:i 
     * @param str 
     */  
    public void i(Object str)  
    {  
        if(logFlag)  
        {  
            if(logLevel <= Log.INFO)  
            {  
                String name = getFunctionName();  
                if(name != null)  
                {  
                    Log.i(tag, name + " - " + str);  
                }  
                else  
                {  
                    Log.i(tag, str.toString());  
                }  
            }  
        }  
          
    }  
      
    /** 
     * The Log Level:d 
     * @param str 
     */  
    public void d(Object str)  
    {  
        if(logFlag)  
        {  
            if(logLevel <= Log.DEBUG)  
            {  
                String name = getFunctionName();  
                if(name != null)  
                {  
                    Log.d(tag, name + " - " + str);  
                }  
                else  
                {  
                    Log.d(tag, str.toString());  
                }  
            }  
        }  
    }  
      
    /** 
     * The Log Level:V 
     * @param str 
     */  
    public void v(Object str)  
    {  
        if(logFlag)  
        {  
            if(logLevel <= Log.VERBOSE)  
            {  
                String name = getFunctionName();  
                if(name != null)  
                {  
                    Log.v(tag, name + " - " + str);  
                }  
                else  
                {  
                    Log.v(tag, str.toString());  
                }  
            }  
        }  
    }  
      
    /** 
     * The Log Level:w 
     * @param str 
     */  
    public void w(Object str)  
    {  
        if(logFlag)  
        {  
            if(logLevel <= Log.WARN)  
            {  
                String name = getFunctionName();  
                if(name != null)  
                {  
                    Log.w(tag, name + " - " + str);  
                }  
                else  
                {  
                    Log.w(tag, str.toString());  
                }  
            }  
        }  
    }  
      
    /** 
     * The Log Level:e 
     * @param str 
     */  
    public void e(Object str)  
    {  
        if(logFlag)  
        {  
            if(logLevel <= Log.ERROR)  
            {  
                String name = getFunctionName();  
                if(name != null)  
                {  
                    Log.e(tag, name + " - " + str);  
                }  
                else  
                {  
                    Log.e(tag, str.toString());  
                }  
            }  
        }  
    }  
      
    /** 
     * The Log Level:e 
     * @param ex 
     */  
    public void e(Exception ex)  
    {  
        if(logFlag)  
        {  
            if(logLevel <= Log.ERROR)  
            {  
                Log.e(tag, "error", ex);  
            }  
        }  
    }  
      
    /** 
     * The Log Level:e 
     * @param log 
     * @param tr 
     */  
    public void e(String log, Throwable tr)  
    {  
        if(logFlag)  
        {  
            String line = getFunctionName();  
            Log.e(tag, "{Thread:" + Thread.currentThread().getName() + "}"  
                    + "[" + mClassName + line + ":] " + log + "\n", tr);  
        }  
    }  
}  



