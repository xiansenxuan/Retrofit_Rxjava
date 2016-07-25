package rx.xuan.com.retrofit_rxjava.utils;

import com.google.gson.Gson;

/**
 * @author xiansenxuan json解析到javabean
 */
public class ToolGson {

	/**
	 * @param result
	 * @return t 解析json到bean
	 */
	public static <T> T jsonToBean(String result, Class<T> clazz) {
		Gson gson = new Gson();
		T t=null;
		try {
			t = gson.fromJson(result, clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}
	
	/** 
     * 对象转换成json字符串 
     * @param obj  
     * @return  
     */  
    public static String toJson(Object obj) {  
        Gson gson = new Gson();  
        return gson.toJson(obj);  
    }  
    
//	
//	/**
//	 * @param result
//	 * @return t 解析json到bean
//	 */
//	public static <T> T jsonToBeanFast(String result, Class<T> clazz) {
//		return JSON.parseObject(result, clazz);
//	}   
//    
//    /** 
//     * 对象转换成json字符串 
//     * @param obj  
//     * @return  
//     */  
//    public static String toJsonFast(Object obj) {  
//    	return JSON.toJSONString(obj);
//    }  
	
}