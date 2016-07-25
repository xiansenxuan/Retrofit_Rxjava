package rx.xuan.com.retrofit_rxjava.utils;

import android.text.TextUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.xuan.com.retrofit_rxjava.rxjava.ApiService;

/**
 *
 */
public class RetrofitManager {
    public static final String api="http://www.nzaom.com/api/";
    public static final long CONNECT_TIME=15000;

    private Retrofit.Builder retrofit;
    private OkHttpClient client;
    private ApiService apiService;

    private RetrofitManager(){
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder{
        private static final RetrofitManager INSTANCE = new RetrofitManager();
    }

    //获取单例
    public static RetrofitManager getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public ApiService getApiService(){
        if(apiService==null){
            apiService = RxManager.createApi(ApiService.class);
        }
        return apiService;
    }

    public Retrofit.Builder getBuilder() {
        if (retrofit == null) {
            synchronized (RetrofitManager.class) {
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder();
                }
            }
        }
        return retrofit;
    }

    public OkHttpClient getClient() {
        if (client == null) {
            synchronized (RetrofitManager.class) {
                if (client == null) {
                    client = new OkHttpClient.Builder()
                            .retryOnConnectionFailure(true) //方法为设置出现错误进行重新连接。
                            .connectTimeout(CONNECT_TIME, TimeUnit.MILLISECONDS) //设置超时时间
                            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                            .addInterceptor(new SignInterceptor())
                            .addNetworkInterceptor(new HeadInterceptor())
                            .build();
                }
            }
        }
        return client;
    }


    public Retrofit getRetrofit(String api) {
        return getBuilder()
                .baseUrl(api)
                .client(getClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Retrofit getRetrofit() {
        return getBuilder()
                .baseUrl(api)
                .client(getClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public <T> void toSubscribe(Observable<T> o, Subscriber<T> s){
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }


}


class HeadInterceptor implements  Interceptor{
    private final static String x_auth_token="x-auth-token";
    private final static String x_platform="x-platform";

    @Override public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request authorised = originalRequest.newBuilder()
                .header(x_auth_token, "3c2b0524-ba8c-49e3-a5d5-28c02295dbf5")
                .header(x_platform, "ANDROID")
                .build();
        return chain.proceed(authorised);
    }
}


class SignInterceptor implements Interceptor {
    /**
     * MD5 混合加密秘钥
     * */
    public static final String mMD5Key = "EqA6aww4ONVTHrQJZR3WOLVw6COLmSwX";
    /**
     * 请求协议参数
     */
    private final static String imeiKey="imei";
    private final static String imsiKey="imsi";
    private final static String timeKey="t";
    private final static String appkeyKey="appkey";
    private final static String latKey="lat";
    private final static String lngKey="lng";
    private final static String hciKey="hci";
    private final static String sign="sign";
    /**
     * 获取sign (md5)
     */
    public static String getMD5Sign(String params,String mMD5Key){
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] result = digest.digest((params + mMD5Key).getBytes());
            StringBuffer sb = new StringBuffer();
            for(byte b : result){
                int number = (int)(b & 0xff) ;//加盐  - 3;//10进制
                String str = Integer.toHexString(number);
                if(str.length()==1){
                    sb.append("0");
                }
                sb.append(str);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            //can't reach
            return "";
        }
    }

    /**
     * @param params
     * 	进行URLEncoder
     */
    private JSONObject setParamsURLEncoder(JSONObject params) {
        try {
            JSONObject obj=new JSONObject();
            Iterator<?> it = params.keys();
            while (it.hasNext()) {
                String key = (String) it.next();
                String value = params.getString(key);
                obj.put(key, URLEncoder.encode(value, "utf-8"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }

    /**
     * @param params
     * 	进行URLEncoder
     */
    private ArrayList<String> setParamsURLEncoder(ArrayList<String> params) {
        try {
            for (String string : params) {
                URLEncoder.encode(string,"utf-8");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }

    /**
     * 添加协议参数 跟在url后面
     */
    public static void appendUrlParams(TreeMap<String, String> paramsMap) {
        paramsMap.put(imeiKey, ToolSysEnv.IMEI==null ? "123456789" : ToolSysEnv.IMEI);
        paramsMap.put(imsiKey, ToolSysEnv.IMSI==null ? "123456789" : ToolSysEnv.IMSI);
        paramsMap.put(timeKey, System.currentTimeMillis()+"");
        paramsMap.put(appkeyKey, "746993077419667724");
        if(ToolSysEnv.getSystemLocation()!=null){
            paramsMap.put(latKey, ToolSysEnv.getSystemLocation()[0]+"");
            paramsMap.put(lngKey, ToolSysEnv.getSystemLocation()[1]+"");
        }else{
            paramsMap.put(latKey, "");
            paramsMap.put(lngKey, "");
        }
        paramsMap.put(hciKey, "nzaom_nzaom_android_15");

//        paramsMap.put(hciKey, ToolSysEnv.getChannelName()+"_nzaom_android_"+ToolSysEnv.getVersionCode());
    }


    /**
     * @param paramsMap
     * @return 协议拼接值
     * 	获取协议的md5值
     */
    private static void getParamsSign(TreeMap<String, String> paramsMap) {
        StringBuilder sb = new StringBuilder();
//        StringBuilder params = new StringBuilder();
        try {
            for(Map.Entry<String, String> entry : paramsMap.entrySet()){
                if(!TextUtils.isEmpty(entry.getValue())) {
                    sb.append(entry.getKey()).append(entry.getValue());
//                    params.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "utf-8")).append("&");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        paramsMap.put(sign,getMD5Sign(sb.toString(), mMD5Key));
    }

    private TreeMap<String, String> paramsMap;

    public SignInterceptor(){
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request oldRequest = chain.request();

        paramsMap=new TreeMap<String, String>();
        //添加协议参数
        appendUrlParams(paramsMap);

        if(oldRequest.url().queryParameterNames()!=null && oldRequest.url().queryParameterNames().size()>0){
            for (String key: oldRequest.url().queryParameterNames()) {
                paramsMap.put(key, oldRequest.url().queryParameter(key));
            }
        }

        //获取sign
        getParamsSign(paramsMap);

        // 添加新的参数
        HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                .newBuilder()
                .scheme(oldRequest.url().scheme())
                .host(oldRequest.url().host())
                .addQueryParameter(imeiKey, paramsMap.get(imeiKey))
                .addQueryParameter(imsiKey, paramsMap.get(imsiKey))
                .addQueryParameter(timeKey, paramsMap.get(timeKey))
                .addQueryParameter(appkeyKey, paramsMap.get(appkeyKey))
                .addQueryParameter(latKey, paramsMap.get(latKey))
                .addQueryParameter(lngKey,paramsMap.get(lngKey))
                .addQueryParameter(hciKey,paramsMap.get(hciKey))
                .addQueryParameter(sign, paramsMap.get(sign));

        // 新的请求
        Request newRequest = oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body())
                .url(authorizedUrlBuilder.build())
                .build();

        return chain.proceed(newRequest);
    }
}