package rx.xuan.com.retrofit_rxjava.rxjava;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.xuan.com.retrofit_rxjava.R;
import rx.xuan.com.retrofit_rxjava.manager.HttpResultFunc;
import rx.xuan.com.retrofit_rxjava.manager.ProgressSubscriber;
import rx.xuan.com.retrofit_rxjava.manager.SubscriberOnNextListener;
import rx.xuan.com.retrofit_rxjava.modle.LoginRequest;
import rx.xuan.com.retrofit_rxjava.modle.RefreshTokenModle;
import rx.xuan.com.retrofit_rxjava.modle.RefreshTokenModleTest;
import rx.xuan.com.retrofit_rxjava.utils.RetrofitManager;
import rx.xuan.com.retrofit_rxjava.utils.RxManager;
import rx.xuan.com.retrofit_rxjava.utils.ToolMd5;

public class RxJavaActivity extends Activity{
	@OnClick(R.id.bt_get)void bt_rx_java(){

	}

	@OnClick(R.id.bt_get)void bt_get(){
		//			HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//			interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//			OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
//
//
//			Retrofit retrofit = new Retrofit.Builder()
//					.baseUrl(api)
//					.client(client)
//					.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//					.addConverterFactory(GsonConverterFactory.create())
//					.build();
//
//			ApiService apiService = retrofit.create(ApiService.class);
//			apiService.getInfo("basil2style")
//					.subscribeOn(Schedulers.io())
//					.observeOn(AndroidSchedulers.mainThread())
//					.subscribe(new Subscriber<UserModle>() {
//						@Override
//						public void onCompleted() {
//
//						}
//
//						@Override
//						public void onError(Throwable e) {
//
//						}
//
//						@Override
//						public void onNext(UserModle response) {
//							MyLogger.xuanLog().i(response.toString());
//						}
//					});

		ApiService apiService = RxManager.createApi(ApiService.class);

		apiService.refreshSystem("892634fe784ba1c7a298e3d89f49ef44")
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<RefreshTokenModle>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(RefreshTokenModle response) {

                    }
                });
	}

	@OnClick(R.id.bt_post)void bt_post(){
		ApiService apiService = RxManager.createApi(ApiService.class);

		LoginRequest login=new LoginRequest();
		login.mobile="15071414335";
		login.password= ToolMd5.MD5("123456");


		apiService.login(login)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<RefreshTokenModle>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(RefreshTokenModle response) {

                    }
                });
//.compose(RxManager.showLoading(new LoadingManager())
	}


	@OnClick(R.id.bt_test1)void bt_test1(){
		ApiService apiService = RxManager.createApi(ApiService.class);

		LoginRequest login=new LoginRequest();
		login.mobile="15071414335";
		login.password= ToolMd5.MD5("123456");

//		Observable observable = apiService.loginTest(login)
//				.map(new HttpResultFunc<RefreshTokenModleTest>());
//
//		ProgressSubscriber subscriber= new ProgressSubscriber(
//		new SubscriberOnNextListener<RefreshTokenModleTest>() {
//			@Override
//			public void onNext(RefreshTokenModleTest refreshTokenModleTest) {
//
//			}
//		},RxJavaActivity.this);
//		RetrofitManager.getInstance().toSubscribe(observable,subscriber);
				
		RetrofitManager.getInstance().toSubscribe(apiService.loginTest(login)
				.map(new HttpResultFunc<RefreshTokenModleTest>()),new ProgressSubscriber(new SubscriberOnNextListener<RefreshTokenModleTest>() {
			@Override
			public void onNext(RefreshTokenModleTest test) {

			}
		},this));

	}

	@OnClick(R.id.bt_test2)void bt_test2(){

		RetrofitManager.getInstance().toSubscribe(RetrofitManager.getInstance().getApiService().refreshSystemTest("11111111111111111111")
				.map(new HttpResultFunc<RefreshTokenModleTest>()),new ProgressSubscriber(new SubscriberOnNextListener<RefreshTokenModleTest>() {
			@Override
			public void onNext(RefreshTokenModleTest test) {

			}
		},this));

	}



	@OnClick(R.id.bt_print_names)void bt_print_names(){
		//			将字符串数组 names 中的所有字符串依次打印出来：
		String[] names=new String[]{"hello","world","good","night"};
		Observable.from(names).subscribe(new Action1<String>() {
			@Override
			public void call(String s) {
				Toast.makeText(RxJavaActivity.this, "s=" + s, Toast.LENGTH_SHORT).show();
			}
		});

	}

	@Bind(R.id.iv_pic) ImageView iv_pic;
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	@OnClick(R.id.bt_pic)void bt_pic(){
		Observable.create(new Observable.OnSubscribe<Drawable>() {
			@Override
			public void call(Subscriber<? super Drawable> subscriber) {
				//子线程获取图片资源
				Drawable drawable=getResources().getDrawable(R.drawable.splash1);
				subscriber.onNext(drawable);
				subscriber.onCompleted();
			}
		}).subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Observer<Drawable>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(RxJavaActivity.this, "error!!!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(Drawable drawable) {
                        //主线程显示
                        iv_pic.setImageDrawable(drawable);
                    }
                });


	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_rxjava);

		ButterKnife.bind(this);
	}

}
