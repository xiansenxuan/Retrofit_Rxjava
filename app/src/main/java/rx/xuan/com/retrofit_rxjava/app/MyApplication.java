package rx.xuan.com.retrofit_rxjava.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;


/**
 * 整个应用程序Applicaiton
 * 
 * @author xuan
 * @version 1.0
 * 
 */
public class MyApplication extends Application {

	/**对外提供整个应用生命周期的Context**/
	private static Context context;
	/**对外提供整个应用生命周期的MyApplication**/
	private static MyApplication instance;
	/**整个应用全局可访问数据集合**/
	private static Map<String, Object> gloableData = new HashMap<String, Object>();
	/***寄存整个应用Activity**/
	private final Stack<WeakReference<Activity>> activitys = new Stack<WeakReference<Activity>>();

	/**
	 * 对外提供Application Context
	 * @return
	 */
	public static Context getMyApplication() {
		return context;
	}
	
	/**
	 * 对外提供Application对象
	 * @return
	 */
	public static MyApplication getInstance() {
		return instance;
	}

	@Override
	public void onCreate() {
//		MultiDex.install(this);

		context=this;
		instance = this;
		
		super.onCreate();
	}
	
//	/**
//	 * 获取网络是否已连接
//	 * @return
//	 */
//	public static boolean isNetworkReady(){
//		return ToolNetwork.getInstance().init(instance).isConnected();
//	}
	
	/*******************************************************Application数据操作API（开始）********************************************************/
	
	/**
	 * 往Application放置数据（最大不允许超过5个）
	 * @param strKey 存放属性Key
	 * @param strValue 数据对象
	 */
	public static void assignData(String strKey, Object strValue) {
		if (gloableData.size() > 5) {
			throw new RuntimeException("超过允许最大数");
		}
		gloableData.put(strKey, strValue);
	}

	/**
	 * 从Applcaiton中取数据
	 * @param strKey 存放数据Key
	 * @return 对应Key的数据对象
	 */
	public static Object gainData(String strKey) {
		return gloableData.get(strKey);
	}
	
	/*
	 * 从Application中移除数据
	 */
	public static void removeData(String key){
		if(gloableData.containsKey(key)) gloableData.remove(key);
	}

	/*******************************************************Application数据操作API（结束）********************************************************/
	
	
//	/***用来登录/注册成功退出之前Activity的集合**/
//	private final Stack<WeakReference<Activity>> exitStack = new Stack<WeakReference<Activity>>();
//	/**
//	 * 将Activity压入Application栈
//	 * @param task 将要压入栈的Activity对象
//	 */
//	public  void pushExitTask(WeakReference<Activity> tack) {
//		exitStack.push(tack);
//	}
//	/**
//	 * 移除全部
//	 */
//	public  void exitAll() {
//		if(exitStack==null || exitStack.size()<=0) return;
//		//finish所有的Activity
//		for (WeakReference<Activity> task : exitStack) {
//			if(task==null) continue;
//			if(task.get()==null) continue;
//			if (!task.get().isFinishing()) {     
//				task.get().finish(); 
//		    }  
//		}
//		exitStack.clear();
//	}
	/*******************************************Application中存放的Activity操作（压栈/出栈）API（开始）*****************************************/
	
	/**
	 * 将Activity压入Application栈
	 * @param task 将要压入栈的Activity对象
	 */
	public  void pushTask(WeakReference<Activity> task) {
		activitys.push(task);
	}

	/**
	 * 将传入的Activity对象从栈中移除
	 * @param task
	 */
	public  void removeTask(WeakReference<Activity> task) {
		activitys.remove(task);
	}

	/**
	 * 根据指定位置从栈中移除Activity
	 * @param taskIndex Activity栈索引
	 */
	public  void removeTask(int taskIndex) {
		if (activitys.size() > taskIndex)
			activitys.remove(taskIndex);
	}

	/**
	 * 将栈中Activity移除至栈顶
	 */
	public  void removeToTop() {
		int end = activitys.size();
		int start = 1;
		for (int i = end - 1; i >= start; i--) {
			if (!activitys.get(i).get().isFinishing()) {     
				activitys.get(i).get().finish(); 
		    }
		}
	}

	/**
	 * 移除全部（用于整个应用退出）
	 */
	public  void removeAll() {
		//finish所有的Activity
		for (WeakReference<Activity> task : activitys) {
			if(task==null) continue;
			if(task.get()==null) continue;
			if (!task.get().isFinishing()) {     
				task.get().finish(); 
		    }  
		}
        //杀死该应用进程  
	   activitys.clear();
       android.os.Process.killProcess(android.os.Process.myPid());  
       System.exit(10);
	}
	
	/*******************************************Application中存放的Activity操作（压栈/出栈）API（结束）*****************************************/
	
	
	
}
