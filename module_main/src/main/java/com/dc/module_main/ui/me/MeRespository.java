package com.dc.module_main.ui.me;

import com.dc.baselib.http.newhttp.AbsHttpSubscriber;
import com.dc.baselib.http.newhttp.StrAbsHttpSubscriber;
import com.dc.baselib.mvvm.BaseRespository;
import com.dc.baselib.mvvm.EventUtils;
import com.dc.baselib.utils.ToastUtils;
import com.dc.commonlib.commonentity.UserInfo;
import com.dc.commonlib.utils.ApiService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class MeRespository extends BaseRespository {
    public String KEY_USER_EVENT = "key_user_events";
    public String KEY_SIGNSTATE_EVENT = "key_signstate_sevent";
    public String KEY_SIGN_EVENT = "key_sign";

    public MeRespository() {
        KEY_USER_EVENT = EventUtils.getEventKey();
    }

    public void getUserInfo(String uid) {
        addDisposable(mRetrofit.create(IMeService.class)
                .toGetUserInfo(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new AbsHttpSubscriber<UserInfo>() {

                    @Override
                    public void onSuccess(UserInfo userInfo) {
                        postData(KEY_USER_EVENT, userInfo);
                    }

                    @Override
                    public void onFailure(String msg, String code) {
                        ToastUtils.showToast(msg);
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
