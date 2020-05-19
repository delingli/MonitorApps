package com.dc.module_main.bindaccount;

import com.dc.baselib.baseEntiry.User;
import com.dc.baselib.http.newhttp.AbsHttpSubscriber;
import com.dc.baselib.mvvm.BaseRespository;
import com.dc.baselib.utils.ToastUtils;
import com.dc.module_main.login.ILoginService;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BindAccountRespository extends BaseRespository {
    public String EVENT_BIND_WECHAR="event_bind_wechar";
//    public String EVENT_BIND_QQ="event_bind_qq";
    public void bindForWeCharLogin(Map<String, String> request){
        addDisposable(mRetrofit.create(ILoginService.class)
                .login(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new AbsHttpSubscriber<User>() {

                    @Override
                    public void onSuccess(User user) {
                        postData(EVENT_BIND_WECHAR, user);
                    }

                    @Override
                    public void onFailure(String msg, String code) {
                        ToastUtils.showToast(msg);
                    }
                }));
    }
/*    public void bindForQQLogin(Map<String, String> request){
        addDisposable(mRetrofit.create(ILoginService.class)
                .login(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new AbsHttpSubscriber<User>() {

                    @Override
                    public void onSuccess(User user) {
                        postData(EVENT_BIND_QQ, user);
                    }

                    @Override
                    public void onFailure(String msg, String code) {
                        ToastUtils.showToast(msg);
                    }
                }));
    }*/
}
