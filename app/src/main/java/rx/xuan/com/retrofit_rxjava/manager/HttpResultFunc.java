package rx.xuan.com.retrofit_rxjava.manager;

import rx.functions.Func1;
import rx.xuan.com.retrofit_rxjava.modle.GlobleResponse;

/**
 * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
 *
 * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
 */
public class HttpResultFunc<T> implements Func1<GlobleResponse<T>, T> {

    @Override
    public T call(GlobleResponse<T> httpResult) {
        if (httpResult.status != ApiException.SUCCESSS_REQUEST) {
            throw new ApiException(httpResult.status);
        }
        return httpResult.data;
    }
}