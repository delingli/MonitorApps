package com.dc.module_main.login;

import com.dc.baselib.baseEntiry.User;
import com.dc.baselib.http.response.HttpResponse;
import com.dc.commonlib.utils.ApiService;

import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ILoginService {
    @GET(ApiService.ACCOUNTLOGIN)
    Flowable<HttpResponse<User>> login(@QueryMap Map<String, String> map);
    @GET(ApiService.USEROTHERLOGIN)
    Flowable<ResponseBody> userOtherLogin(@QueryMap Map<String, String> map);

//    @GET(ApiService.SENDEMAIL)
//    Flowable<String> toGetVerificationCode(@Query("email") String email, @Query("type") int type);
}
