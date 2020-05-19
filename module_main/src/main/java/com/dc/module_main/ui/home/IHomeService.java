package com.dc.module_main.ui.home;

import com.dc.baselib.http.response.HttpResponse;
import com.dc.commonlib.commonentity.UserInfo;
import com.dc.commonlib.utils.ApiService;

import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface IHomeService {
    @GET(ApiService.USER_PROFILE)
    Flowable<HttpResponse<UserInfo>> toGetUserInfo(@Query("uid") String uid);

    @GET(ApiService.GETSIGNSTATUS)
    Flowable<ResponseBody> getSignStatus(@Query("uid") String uid);

    @GET(ApiService.SIGN_IN)
    Flowable<ResponseBody> signIn(@QueryMap Map<String, String> map);
}
