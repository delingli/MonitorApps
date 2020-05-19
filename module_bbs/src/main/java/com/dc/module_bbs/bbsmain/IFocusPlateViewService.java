package com.dc.module_bbs.bbsmain;

import com.dc.baselib.http.response.HttpResponse;
import com.dc.commonlib.utils.ApiService;
import com.dc.module_bbs.bbsdetail.BBsDetails;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IFocusPlateViewService {
    @GET(ApiService.moduleList)
    Flowable<HttpResponse<List<FocusPlateItem>>> moduleList();

    @GET(ApiService.userModuleList)
    Flowable<HttpResponse<List<FocusPlateItem>>> userModuleList(@Query("uid") String uid);
}
