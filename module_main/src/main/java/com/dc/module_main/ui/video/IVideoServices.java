package com.dc.module_main.ui.video;

import com.dc.baselib.http.response.HttpResponse;
import com.dc.commonlib.common.CourseItemTitle;
import com.dc.commonlib.utils.ApiService;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IVideoServices {
    @GET(ApiService.categorys)
    Flowable<HttpResponse<List<CourseItemTitle>>> categorys(@Query("all") String all);
}
