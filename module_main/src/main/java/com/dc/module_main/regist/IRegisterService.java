package com.dc.module_main.regist;

import com.dc.baselib.http.response.HttpResponse;
import com.dc.commonlib.utils.ApiService;

import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface IRegisterService   {

    @POST(ApiService.REGISTERED)
    Flowable<ResponseBody> register(@QueryMap Map<String, String> map);
    @GET(ApiService.SENDEMAIL)
    Flowable<ResponseBody> toGetVerificationCode(@Query("email") String email,@Query("client") String client, @Query("type") int type);

}
