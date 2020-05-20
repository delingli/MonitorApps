package com.dc.module_bbs.bbsdetail.bbsdetailfragment;

import com.dc.baselib.http.response.HttpResponse;
import com.dc.commonlib.utils.ApiService;
import com.dc.module_bbs.bbsdetail.BBsDetails;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IBBSDetailFragmentService {
    @GET(ApiService.getThemeInForum)
    Flowable<HttpResponse<List<ThemeInForumItem>>> getThemeInForum(@Query("forumid") String forumid,
                                                           @Query("start") int start,
                                                           @Query("limit") int limit,
                                                           @Query("order") String order,
                                                           @Query("uid") String uid
    );
}
