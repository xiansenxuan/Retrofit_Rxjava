package rx.xuan.com.retrofit_rxjava.utils;

import android.app.ProgressDialog;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.xuan.com.retrofit_rxjava.app.MyApplication;
import rx.xuan.com.retrofit_rxjava.inter.ILoading;

/**
 * Created by xiansenxuan on 2016/4/11 0011.
 */
public class LoadingManager implements ILoading {
    @Override
    public void showLoading() {
        Observable.just("showLoading")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        if (loadingViewDialog == null) {
                            loadingViewDialog = new ProgressDialog(MyApplication.getMyApplication());
                            loadingViewDialog.setCanceledOnTouchOutside(false);
                        }
                        loadingViewDialog.setMessage(s);
                        loadingViewDialog.show();
                    }
                });
    }

    @Override
    public void hideLoading() {
        Observable.empty()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        if (loadingViewDialog != null && loadingViewDialog.isShowing()) {
                            loadingViewDialog.hide();
                        }
                    }
                })
                .subscribe();
    }

    private ProgressDialog loadingViewDialog;

}
