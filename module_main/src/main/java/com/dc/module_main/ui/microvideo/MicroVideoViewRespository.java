package com.dc.module_main.ui.microvideo;

import android.text.TextUtils;

import com.dc.baselib.http.newhttp.AbsHttpSubscriber;
import com.dc.baselib.http.newhttp.StrAbsHttpSubscriber;
import com.dc.baselib.mvvm.BaseRespository;
import com.dc.baselib.mvvm.EventUtils;
import com.dc.baselib.utils.ToastUtils;
import com.dc.commonlib.utils.JsonUtil;
import com.dc.commonlib.utils.LogUtil;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MicroVideoViewRespository extends BaseRespository {
    public String KEY_MICRO_VIDEO ;
    public String KEY_FINISH_VIDEO ;
    public String KEY_CANCELFOLLOW;
    public String KEY_FOLLOWMEMBER;
    public MicroVideoViewRespository() {
        KEY_MICRO_VIDEO = EventUtils.getEventKey();
        KEY_FINISH_VIDEO = EventUtils.getEventKey();
        KEY_CANCELFOLLOW = EventUtils.getEventKey();
        KEY_FOLLOWMEMBER = EventUtils.getEventKey();
    }

    int page = 1;

    public void getEduWeishi(boolean refresh, int limit, String uid) {
        if (refresh) {
            page = 1;
        } else {
            ++page;
        }
        addDisposable(mRetrofit.create(IMicroVideoViewService.class)
                .toGetEduWeishi(page, limit, uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new StrAbsHttpSubscriber(true) {


                    @Override
                    public void onSuccess(String s) {
                        if (TextUtils.isEmpty(s)) {
                            postData(KEY_FINISH_VIDEO, "finish");

                        } else {
                            try {
                                JSONObject obj = new JSONObject(s);
                                int code = obj.optInt("code");
                                String msg = obj.optString("msg");
                                String data = obj.optString("data");
                                if (code == 0) {
                                    List<MicroVideos> microVideosList = JsonUtil.fromJson(data, new TypeToken<ArrayList<MicroVideos>>() {
                                    }.getType());
                                    if (microVideosList != null && microVideosList.isEmpty()) {
                                        checkChangePage();
                                    }
                                    postData(KEY_MICRO_VIDEO, microVideosList);
                                } else {
                                    checkChangePage();
                                    postData(KEY_FINISH_VIDEO, msg);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }

                    @Override
                    public void onFailure(String msg, String code) {
                        LogUtil.d("ldl","code:"+code+"msg:"+msg);
                        checkChangePage();
                        postData(KEY_FINISH_VIDEO, msg);
                    }
                }));
    }

    public void checkChangePage() {
        --page;
        if (page < 1) {
            page = 1;
        }
    }



    public void cancelFollow(String uid, String fuid) {
        addDisposable(mRetrofit.create(IMicroVideoViewService.class)
                .cancelFollow(uid, fuid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new StrAbsHttpSubscriber() {


                    @Override
                    public void onSuccess(String s) {
                        postData(KEY_CANCELFOLLOW, s);
                    }

                    @Override
                    public void onFailure(String msg, String code) {
                        ToastUtils.showToast(msg);

                    }
                }));
    }

    public void followMember(String uid, String fuid) {
        addDisposable(mRetrofit.create(IMicroVideoViewService.class)
                .followMember(uid, fuid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new StrAbsHttpSubscriber() {


                    @Override
                    public void onSuccess(String s) {
                        postData(KEY_FOLLOWMEMBER, s);
                    }

                    @Override
                    public void onFailure(String msg, String code) {
                        ToastUtils.showToast(msg);

                    }
                }));
    }
}
