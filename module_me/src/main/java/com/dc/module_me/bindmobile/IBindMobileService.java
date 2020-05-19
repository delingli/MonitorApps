package com.dc.module_me.bindmobile;

import com.dc.commonlib.utils.ApiService;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IBindMobileService   {
    @GET(ApiService.BANDINGPHONE)
    Flowable<ResponseBody> bindMobile(@Query("uid") String uid, @Query("phone_head") String phone_head, @Query("phone") String
            phone, @Query("sms_code") String sms_code);
    @GET(ApiService.GETPHONESMSCODE)
    Flowable<ResponseBody> getPhoneSmsCode(@Query("phone") String phone,@Query("client") String client, @Query("phone_head") String phone_head, @Query("type") int type);
}
