package com.dc.module_home.homamain;

import com.dc.commonlib.utils.ApiService;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IHomeMainService {
    @GET(ApiService.OWNER_COMPANY_BOARD)
    Flowable<ResponseBody> getownerCompanyBoard(@Path("id") int id);
}
