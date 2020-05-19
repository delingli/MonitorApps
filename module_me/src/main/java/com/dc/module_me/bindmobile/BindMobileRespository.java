package com.dc.module_me.bindmobile;

import com.dc.baselib.http.newhttp.StrAbsHttpSubscriber;
import com.dc.baselib.mvvm.BaseRespository;
import com.dc.baselib.mvvm.EventUtils;
import com.dc.baselib.utils.ToastUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BindMobileRespository extends BaseRespository {
    public String BIND_PHONE_EVENT;
    public String VERIFICATION_CODE_EVENT;

    public BindMobileRespository() {
        this.BIND_PHONE_EVENT = EventUtils.getEventKey();
        this.VERIFICATION_CODE_EVENT = EventUtils.getEventKey();
    }
    public void sendPhoneSmsCode(String phone, String client,String phone_head, int type) {
        addDisposable(mRetrofit.create(IBindMobileService.class)
                .getPhoneSmsCode(phone,client, phone_head, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new StrAbsHttpSubscriber() {

                    @Override
                    public void onSuccess(String smsCode) {
                        postData(VERIFICATION_CODE_EVENT, smsCode);

                    }

                    @Override
                    public void onFailure(String msg, String code) {
                        ToastUtils.showToast(msg);

                    }
                }));
    }

    public void bindPhone(String uid,String phone_head,String phone, String sms_code) {
        addDisposable(mRetrofit.create(IBindMobileService.class)
                .bindMobile(uid, phone_head, phone,sms_code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new StrAbsHttpSubscriber() {

                    @Override
                    public void onSuccess(String bind) {
                        postData(BIND_PHONE_EVENT, bind);

                    }

                    @Override
                    public void onFailure(String msg, String code) {
                        ToastUtils.showToast(msg);

                    }
                }));
    }
}
