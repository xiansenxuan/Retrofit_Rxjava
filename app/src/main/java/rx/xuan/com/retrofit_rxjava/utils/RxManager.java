package rx.xuan.com.retrofit_rxjava.utils;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 *
 */
public class RxManager {

    private RxManager() {

    }

    public static void unsubscribeIfNotNull(Subscription subscription) {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    public static CompositeSubscription getCompositeSubIfUnsubscribed(CompositeSubscription subscription) {
        if (subscription == null || subscription.isUnsubscribed()) {
            return new CompositeSubscription();
        }

        return subscription;
    }

    public static <T> T createApi(Class<T> c) {
        return RetrofitManager.getInstance().getRetrofit().create(c);
    }

    public static <T> T createApi(Class<T> c,String api) {
        return RetrofitManager.getInstance().getRetrofit(api).create(c);
    }

//    /**
//     * 显示并隐藏loading
//     */
//    public static <T> Observable.Transformer<T, T> showLoading(final ILoading loading) {
//        return new Observable.Transformer<T, T>() {
//            @Override
//            public Observable<T> call(Observable<T> observable) {
//                return observable
//                        .doOnSubscribe(new Action0() {
//                            @Override
//                            public void call() {
//                                //before subscribe and can set a thread
//                                Log.i("RxThread", "showLoading:" + ", run In :" + Thread.currentThread().getName());
//                                loading.showLoading();
//                            }
//                        }).subscribeOn(AndroidSchedulers.mainThread())
//                        .doOnTerminate(new Action0() {
//                            @Override
//                            public void call() {
//                                //before complete or error, but can't set a thread
//                                Log.i("RxThread", "hideLoading:" + ", run In :" + Thread.currentThread().getName());
//                                loading.hideLoading();
//                            }
//                        }).observeOn(AndroidSchedulers.mainThread());
//            }
//        };
//    }
}
