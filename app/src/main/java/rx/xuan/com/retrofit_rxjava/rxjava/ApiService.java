package rx.xuan.com.retrofit_rxjava.rxjava;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;
import rx.xuan.com.retrofit_rxjava.modle.GlobleResponse;
import rx.xuan.com.retrofit_rxjava.modle.LoginRequest;
import rx.xuan.com.retrofit_rxjava.modle.RefreshTokenModle;
import rx.xuan.com.retrofit_rxjava.modle.RefreshTokenModleTest;

public interface ApiService {

    @GET("system")
    Observable<RefreshTokenModle> refreshSystem(@Query("clientId") String clientId);

    @POST("login")
    Observable<RefreshTokenModle> login(@Body LoginRequest login);


    @GET("system")
    Observable<GlobleResponse<RefreshTokenModleTest>> refreshSystemTest(@Query("clientId") String clientId);

    @POST("login")
    Observable<GlobleResponse<RefreshTokenModleTest>> loginTest(@Body LoginRequest login);
}
