package com.dc.module_main.ui.microvideo;

import com.dc.baselib.http.response.HttpResponse;
import com.dc.commonlib.utils.ApiService;

import java.util.List;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IMicroVideoViewService {
    @GET(ApiService.WEISHI_URL)
    Flowable<ResponseBody> toGetEduWeishi(@Query("page") int page,
                                           @Query("limit") int limit,
                                           @Query("uid") String uid);
    @GET(ApiService.FOLLOWMEMBER_URL)
    Flowable<ResponseBody> followMember(@Query("uid") String uid, @Query("fuid") String fuid);

    @GET(ApiService.CANCELFOLLOW_URL)
    Flowable<ResponseBody> cancelFollow(@Query("uid") String uid, @Query("fuid") String fuid);
}
