package rx.xuan.com.retrofit_rxjava.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.hardware.Camera;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import rx.xuan.com.retrofit_rxjava.app.MyApplication;

/**
 * 运行环境信息
 *
 * @author xuan
 * @version 1.0
 */
public final class ToolSysEnv {

	/*** Log输出标识 **/
	private static final String TAG = ToolSysEnv.class.getSimpleName();

	/*** 屏幕显示材质 **/
	private static final DisplayMetrics mDisplayMetrics = new DisplayMetrics();

	/** 上下文 **/
	private static final Context context = MyApplication.getMyApplication();

	/** 操作系统名称(GT-I9100G) ***/
	public static final String MODEL_NUMBER = Build.MODEL;

	/** 操作系统名称(I9100G) ***/
	public static final String DISPLAY_NAME = Build.DISPLAY;

	/** 操作系统版本(4.4) ***/
	public static final String OS_VERSION = Build.VERSION.RELEASE;
	;

	/** 应用程序版本 ***/
	public static final String APP_VERSION = getVersionName();

	/*** 屏幕宽度 **/
	public static final int SCREEN_WIDTH = getDisplayMetrics().widthPixels;

	/*** 屏幕高度 **/
	public static final int SCREEN_HEIGHT = getDisplayMetrics().heightPixels;

	/*** 本机手机号码 **/
	public static final String PHONE_NUMBER = ((TelephonyManager) context
			.getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number();

	// /*** 设备ID **/
	// public static final String DEVICE_ID = ((TelephonyManager) context
	// .getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();

	/*** 设备IMEI号码 **/
	public static final String IMEI = ((TelephonyManager) context
			.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();

	/** 设备ICCID号码 */
	public static final String ICCID = ((TelephonyManager) context
			.getSystemService(Context.TELEPHONY_SERVICE)).getSimSerialNumber();

	/*** 设备IMSI号码 **/
	public static final String IMSI = ((TelephonyManager) context
			.getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();

	/*** Activity之间数据传输数据对象Key **/
	public static final String ACTIVITY_DTO_KEY = "ACTIVITY_DTO_KEY";

	/*
	 * 获取渠道名
	 * 
	 * @return 如果没有获取成功，那么返回值为空
	 */
	public static String getChannelName() {

		String channelName = null;
		try {
			PackageManager packageManager = context.getPackageManager();
			if (packageManager != null) {
				// 注意此处为ApplicationInfo 而不是
				// ActivityInfo,因为友盟设置的meta-data是在application标签中，而不是某activity标签中，所以用ApplicationInfo
				ApplicationInfo applicationInfo = packageManager
						.getApplicationInfo(context.getPackageName(),
								PackageManager.GET_META_DATA);
				if (applicationInfo != null) {
					if (applicationInfo.metaData != null) {
						channelName = applicationInfo.metaData
								.getString("UMENG_CHANNEL");
					}
				}

			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return channelName;
	}

	/** 获取系统显示材质 ***/
	public static DisplayMetrics getDisplayMetrics() {
		WindowManager windowMgr = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		windowMgr.getDefaultDisplay().getMetrics(mDisplayMetrics);
		return mDisplayMetrics;
	}

	/** 获取摄像头支持的分辨率 ***/
	public static List<Camera.Size> getSupportedPreviewSizes(Camera camera) {
		Camera.Parameters parameters = camera.getParameters();
		List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();
		return sizeList;
	}

	/**
	 * 获取应用程序版本（versionName）
	 *
	 * @return 当前应用的版本号
	 */
	public static String getVersionName() {
		PackageManager manager = context.getPackageManager();
		PackageInfo info = null;
		try {
			info = manager.getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			Log.e(TAG, "获取应用程序版本失败，原因：" + e.getMessage());
			return "";
		}

		return info.versionName;
	}

	/**
	 * 获取版本号(内部识别号)
	 */
	public static int getVersionCode() {
		try {
			PackageInfo pi = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			return pi.versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 获取系统内核版本
	 *
	 * @return
	 */
	public static String getKernelVersion() {
		String strVersion = "";
		FileReader mFileReader = null;
		BufferedReader mBufferedReader = null;
		try {
			mFileReader = new FileReader("/proc/version");
			mBufferedReader = new BufferedReader(mFileReader, 8192);
			String str2 = mBufferedReader.readLine();
			strVersion = str2.split("\\s+")[2];// KernelVersion

		} catch (Exception e) {
			Log.e(TAG, "获取系统内核版本失败，原因：" + e.getMessage());
		} finally {
			try {
				mBufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return strVersion;
	}

	/***
	 * 获取MAC地址
	 *
	 * @return
	 */
	public static String getMacAddress() {
		WifiManager wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		if (wifiInfo.getMacAddress() != null) {
			return wifiInfo.getMacAddress();
		} else {
			return "";
		}
	}

	/**
	 * 获取运行时间
	 *
	 * @return 运行时间(单位/s)
	 */
	public static long getRunTimes() {
		long ut = SystemClock.elapsedRealtime() / 1000;
		if (ut == 0) {
			ut = 1;
		}
		return ut;
	}

	@TargetApi(Build.VERSION_CODES.M)
	public static double[] getSystemLocation() {
//		LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
//		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//			if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//				// TODO: Consider calling
//				//    public void requestPermissions(@NonNull String[] permissions, int requestCode)
//				// here to request the missing permissions, and then overriding
//				//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//				//                                          int[] grantResults)
//				// to handle the case where the user grants the permission. See the documentation
//				// for Activity#requestPermissions for more details.
//				return null;
//			}
//			Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//			if (location != null) {
//				return new double[]{location.getLatitude(),location.getLongitude()};
//			}
//		} else {
//			LocationListener locationListener = new LocationListener() {
//
//				// Provider被enable时触发此函数，比如GPS被打开
//				@Override
//				public void onProviderEnabled(String provider) {
//
//				}
//
//				// Provider被disable时触发此函数，比如GPS被关闭
//				@Override
//				public void onProviderDisabled(String provider) {
//
//				}
//
//				// 当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
//				@Override
//				public void onLocationChanged(Location location) {
//					if (location != null) {
//					}
//				}
//
//				// Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
//				@Override
//				public void onStatusChanged(String provider, int status,
//						Bundle extras) {
//				}
//			};
//			locationManager
//					.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
//							1000, 0, locationListener);
//			Location location = locationManager
//					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//			if (location != null) {
//				return new double[]{location.getLatitude(),location.getLongitude()};
//			}
//		}
		return null;

	}

	// /**
	// * 显示地理位置经度和纬度信息
	// *
	// * @param location
	// */
	// private static void showLocation(final Location location) {
	// new Thread(new Runnable() {
	//
	// @Override
	// public void run() {
	// try {
	// // 组装反向地理编码的接口位置
	// StringBuilder url = new StringBuilder();
	// url.append("http://maps.googleapis.com/maps/api/geocode/json?latlng=");
	// url.append(location.getLatitude()).append(",");
	// url.append(location.getLongitude());
	// url.append("&sensor=false");
	// HttpClient client = new DefaultHttpClient();
	// HttpGet httpGet = new HttpGet(url.toString());
	// httpGet.addHeader("Accept-Language", "zh-CN");
	// HttpResponse response = client.execute(httpGet);
	// if (response.getStatusLine().getStatusCode() == 200) {
	// HttpEntity entity = response.getEntity();
	// String res = EntityUtils.toString(entity);
	// // 解析
	// JSONObject jsonObject = new JSONObject(res);
	// // 获取results节点下的位置信息
	// JSONArray resultArray = jsonObject
	// .getJSONArray("results");
	// if (resultArray.length() > 0) {
	// JSONObject obj = resultArray.getJSONObject(0);
	// // 取出格式化后的位置数据
	// String address = obj.getString("formatted_address");
	//
	// }
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// }).start();
	// }

	// /**
	// * LocationListern监听器 参数：地理位置提供器、监听位置变化的时间间隔、位置变化的距离间隔、LocationListener监听器
	// */
	//
	// LocationListener locationListener = new LocationListener() {
	//
	// @Override
	// public void onStatusChanged(String provider, int status, Bundle arg2) {
	//
	// }
	//
	// @Override
	// public void onProviderEnabled(String provider) {
	//
	// }
	//
	// @Override
	// public void onProviderDisabled(String provider) {
	//
	// }
	//
	// @Override
	// public void onLocationChanged(Location location) {
	// // 如果位置发生变化,重新显示
	// showLocation(location);
	//
	// }
	// };
	//
	// @Override
	// protected void onDestroy() {
	// super.onDestroy();
	// if(locationManager!=null){
	// //移除监听器
	// locationManager.removeUpdates(locationListener);
	// }
	// }
}
