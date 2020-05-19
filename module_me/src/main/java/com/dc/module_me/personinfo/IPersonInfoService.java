package com.dc.module_me.personinfo;

import com.dc.baselib.http.response.HttpResponse;
import com.dc.commonlib.commonentity.UserInfo;
import com.dc.commonlib.utils.ApiService;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IPersonInfoService {
    @GET(ApiService.USER_PROFILE)
    Flowable<HttpResponse<UserInfo>> toGetUserInfo(@Query("uid") String uid);
}
