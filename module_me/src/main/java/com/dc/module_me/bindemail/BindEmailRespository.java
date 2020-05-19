package com.dc.module_me.bindemail;

import com.dc.baselib.http.newhttp.StrAbsHttpSubscriber;
import com.dc.baselib.mvvm.BaseRespository;
import com.dc.baselib.mvvm.EventUtils;
import com.dc.baselib.utils.ToastUtils;
import com.dc.module_me.bindmobile.IBindMobileService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BindEmailRespository extends BaseRespository {
    public String GET_EMAIL_SMS_EVENT;
    public String BIND_EMAIL_EVENT;

    public BindEmailRespository() {
        this.GET_EMAIL_SMS_EVENT = EventUtils.getEventKey();
        this.BIND_EMAIL_EVENT = EventUtils.getEventKey();
    }

    public void sendEmailCode(String email ,String client ,int type) {
        addDisposable(mRetrofit.create(IBindEmailService.class)
                .toGetVerificationCode(email, client,type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new StrAbsHttpSubscriber() {

                    @Override
                    public void onSuccess(String smsCode) {
                        postData(GET_EMAIL_SMS_EVENT, smsCode);

                    }

                    @Override
                    public void onFailure(String msg, String code) {
                        ToastUtils.showToast(msg);

                    }
                }));
    }

    public void bindEmail(String uid,String email, String sms_code) {
        addDisposable(mRetrofit.create(IBindEmailService.class)
                .bindEmail(uid, email,sms_code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new StrAbsHttpSubscriber() {

                    @Override
                    public void onSuccess(String bind) {
                        postData(BIND_EMAIL_EVENT, bind);

                    }

                    @Override
                    public void onFailure(String msg, String code) {
                        ToastUtils.showToast(msg);

                    }
                }));
    }
}
