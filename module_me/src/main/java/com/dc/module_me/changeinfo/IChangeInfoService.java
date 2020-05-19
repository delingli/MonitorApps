package com.dc.module_me.changeinfo;

import com.dc.baselib.baseEntiry.User;
import com.dc.baselib.http.response.HttpResponse;
import com.dc.commonlib.utils.ApiService;

import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface IChangeInfoService {
    @FormUrlEncoded
    @POST(ApiService.UPDATEUSERNICKNAME)
    Flowable<ResponseBody> updateusernickname(@Field("uid") String uid, @Field("nickname") String nickname);
    @FormUrlEncoded
    @POST(ApiService.UPDATEUSERINFO)
    Flowable<ResponseBody> updateuserinfo(@Field("uid") String uid, @Field("sightml") String sightml);
}
