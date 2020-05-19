package com.dc.module_main.ui.video;

import com.dc.baselib.http.newhttp.AbsHttpSubscriber;
import com.dc.baselib.http.newhttp.StrAbsHttpSubscriber;
import com.dc.baselib.mvvm.BaseRespository;
import com.dc.baselib.mvvm.EventUtils;
import com.dc.baselib.utils.ToastUtils;
import com.dc.commonlib.common.CourseItemTitle;
import com.dc.commonlib.utils.LogUtil;
import com.dc.module_main.ui.me.IMeService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class VideoViewRespository extends BaseRespository {
    public String KEY_SIGNSTATE_EVENT ;
    public String KEY_SIGN_EVENT,KEY_TITLE_LIST;

    public VideoViewRespository() {
        KEY_SIGNSTATE_EVENT= EventUtils.getEventKey();
        KEY_SIGN_EVENT= EventUtils.getEventKey();
        KEY_TITLE_LIST= EventUtils.getEventKey();
    }
    public void categorys(String all) {
        addDisposable(mRetrofit.create(IVideoServices.class)
                .categorys(all)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new AbsHttpSubscriber<List<CourseItemTitle>>() {


                    @Override
                    public void onSuccess(List<CourseItemTitle> courseItemTitles) {
                        postData(KEY_TITLE_LIST, courseItemTitles);
                    }

                    @Override
                    public void onFailure(String msg, String code) {
                        if (Integer.parseInt(code) == -2) {
                        }
                        LogUtil.e(code + msg);
                    }
                }));
    }
    public void getSignStatus(String uid) {
        addDisposable(mRetrofit.create(IMeService.class)
                .getSignStatus(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new StrAbsHttpSubscriber(true) {

                    @Override
                    public void onSuccess(String s) {
                        try {
                            JSONObject obj = new JSONObject(s);
                            int data = obj.optInt("data");
                            postData(KEY_SIGNSTATE_EVENT, data+"");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onFailure(String msg, String code) {
                        ToastUtils.showToast(msg);

                    }
                }));
    }

    public void signIn(Map<String ,String > params) {
        addDisposable(mRetrofit.create(IMeService.class)
                .signIn(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new StrAbsHttpSubscriber() {


                    @Override
                    public void onSuccess(String s) {
                        postData(KEY_SIGN_EVENT, s);
                    }

                    @Override
                    public void onFailure(String msg, String code) {
                        ToastUtils.showToast(msg);
                    }
                }));
    }
}
