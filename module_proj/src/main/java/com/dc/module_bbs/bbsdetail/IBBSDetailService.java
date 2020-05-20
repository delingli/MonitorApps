package com.dc.module_bbs.bbsdetail;

import com.dc.baselib.http.response.HttpResponse;
import com.dc.commonlib.utils.ApiService;

import java.util.List;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IBBSDetailService {

    @GET(ApiService.ModuleHeadInfo)
    Flowable<HttpResponse<BBsDetails>> listLearnRecord(@Query("fid") String fid,
                                                             @Query("uid") String uid);
    @GET(ApiService.userPlateOneModule)
    Flowable<ResponseBody> userPlateOneModule(@Query("uid") String uid, @Query("fid") String fid,
                                              @Query("state") int state);

    @GET(ApiService.submoduleList)
    Flowable<HttpResponse<List<BBsDetails>>> submoduleList(@Query("fid") String fid);


}
