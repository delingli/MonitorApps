package com.dc.module_main.retrievepassword.emailretrieve;

import com.dc.commonlib.utils.ApiService;

import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface IEmailRetrieveService   {

    @POST(ApiService.BACKPASSWORDTOEMAIL)
    Flowable<ResponseBody> retrieveWithEmail(@QueryMap Map<String, String> map);

    @GET(ApiService.BACKPASSWORDTOPHONE)
    Flowable<ResponseBody> retrieveWithPhone(@QueryMap Map<String, String> map);

    @GET(ApiService.SENDEMAIL)
    Flowable<ResponseBody> toGetVerificationCode(@Query("email") String email,@Query("client") String client, @Query("type") int type);


    @GET(ApiService.GETPHONESMSCODE)
    Flowable<ResponseBody> getPhoneSmsCode(@Query("phone") String phone,@Query("client") String client, @Query("phone_head") String phone_head, @Query("type") int type);

}
