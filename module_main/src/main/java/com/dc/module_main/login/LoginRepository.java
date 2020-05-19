package com.dc.module_main.login;

import com.dc.baselib.baseEntiry.User;
import com.dc.baselib.http.newhttp.AbsHttpSubscriber;
import com.dc.baselib.http.newhttp.StrAbsHttpSubscriber;
import com.dc.baselib.mvvm.BaseRespository;
import com.dc.baselib.mvvm.EventUtils;
import com.dc.baselib.utils.ToastUtils;
import com.dc.commonlib.utils.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginRepository extends BaseRespository {
    public String EVENT_USER_KEY;
    public String EVENT_USEROTHERLOGIN_SUCESS_KEY;
    public String EVENT_TOBINDOTHER_KEY;

    public LoginRepository() {
        this.EVENT_USER_KEY = EventUtils.getEventKey();
        this.EVENT_USEROTHERLOGIN_SUCESS_KEY = EventUtils.getEventKey();
        this.EVENT_TOBINDOTHER_KEY = EventUtils.getEventKey();
    }

    public void login(Map<String, String> request) {
        addDisposable(mRetrofit.create(ILoginService.class)
                .login(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new AbsHttpSubscriber<User>() {

                    @Override
                    public void onSuccess(User user) {
                        postData(EVENT_USER_KEY, user);
                    }

                    @Override
                    public void onFailure(String msg, String code) {
                        ToastUtils.showToast(msg);
                    }
                }));

    }

    public void loginBindQQ(Map<String, String> request) {
        addDisposable(mRetrofit.create(ILoginService.class)
                .login(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new AbsHttpSubscriber<User>() {
                    @Override
                    public void onSuccess(User user) {
                        postData(EVENT_USER_KEY, user);
                    }

                    @Override
                    public void onFailure(String msg, String code) {
                        ToastUtils.showToast(msg);
                    }
                }));

    }

    public void loginBindWechar(Map<String, String> request) {
        addDisposable(mRetrofit.create(ILoginService.class)
                .login(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new AbsHttpSubscriber<User>() {
                    @Override
                    public void onSuccess(User user) {
                        postData(EVENT_USER_KEY, user);
                    }

                    @Override
                    public void onFailure(String msg, String code) {
                        ToastUtils.showToast(msg);
                    }
                }));

    }

    public void userOtherLogin(Map<String, String> request) {
        addDisposable(mRetrofit.create(ILoginService.class)
                .userOtherLogin(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new StrAbsHttpSubscriber(true) {


                    @Override
                    public void onSuccess(String str) {
                        JSONObject obj = null;
                        try {
                            obj = new JSONObject(str);
                            int code = obj.optInt("code");
                            String msg = obj.optString("msg");
                            String data = obj.optString("data");
                            if (code == 0) {
                                User user = JsonUtil.fromJson(data, User.class);
                                postData(EVENT_USEROTHERLOGIN_SUCESS_KEY, user);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onFailure(String msg, String code) {
                        //-11第三方id必须填写一个-12第三方未绑定，请登陆-13获取用户信息失败
                        int codez = Integer.valueOf(code);
                        if (codez == -11) {
                            ToastUtils.showToast(msg);
                        } else if (codez == -12) {
                            postData(EVENT_TOBINDOTHER_KEY, msg);

                        } else if (codez == -13) {
                            ToastUtils.showToast(msg);
                        }else {
                            ToastUtils.showToast(msg);
                        }
                    }
                }));

    }
}
