package com.dc.module_me.bindemail;

import com.dc.commonlib.utils.ApiService;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IBindEmailService   {
    @GET(ApiService.BANDINGEMAIL)
    Flowable<ResponseBody> bindEmail(@Query("uid") String uid, @Query("email") String email, @Query("sms_code") String sms_code);
    @GET(ApiService.SENDEMAIL)
    Flowable<ResponseBody> toGetVerificationCode(@Query("email") String email, @Query("client") String client,@Query("type") int type);
}
