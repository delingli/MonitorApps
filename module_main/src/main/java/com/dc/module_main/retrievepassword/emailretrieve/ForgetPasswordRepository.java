package com.dc.module_main.retrievepassword.emailretrieve;

import com.dc.baselib.http.newhttp.AbsHttpSubscriber;
import com.dc.baselib.http.newhttp.StrAbsHttpSubscriber;
import com.dc.baselib.mvvm.BaseRespository;
import com.dc.baselib.mvvm.EventUtils;
import com.dc.baselib.utils.ToastUtils;
import com.dc.module_main.retrievepassword.SmsCode;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

//忘记密码
public class ForgetPasswordRepository extends BaseRespository {
    public String EVENT_RETRIEVEWITHPHONE_KEY;
    public String EVENT_RETRIEVEWITHEMAIL_KEY;
    public String EVENT_SENDPHONESMSCODE_KEY;
    public String EVENT_SENDEMAIL_KEY;

    public ForgetPasswordRepository() {
        this.EVENT_RETRIEVEWITHPHONE_KEY = EventUtils.getEventKey();
        this.EVENT_RETRIEVEWITHEMAIL_KEY = EventUtils.getEventKey();
        this.EVENT_SENDPHONESMSCODE_KEY = EventUtils.getEventKey();
        this.EVENT_SENDEMAIL_KEY = EventUtils.getEventKey();
    }

    //phone找回
    public void retrieveWithPhone(Map<String, String> params) {
        addDisposable(mRetrofit.create(IEmailRetrieveService.class)
                .retrieveWithPhone(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new StrAbsHttpSubscriber() {
                    @Override
                    public void onSuccess(String s) {
                        postData(EVENT_RETRIEVEWITHPHONE_KEY, s);
                    }

                    @Override
                    public void onFailure(String msg, String code) {
                        ToastUtils.showToast(msg);
                    }
                }));
    }

    //email找回
    public void retrieveWithEmail(Map<String, String> params) {
        addDisposable(mRetrofit.create(IEmailRetrieveService.class)
                .retrieveWithEmail(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new StrAbsHttpSubscriber() {
                    @Override
                    public void onSuccess(String s) {
                        postData(EVENT_RETRIEVEWITHEMAIL_KEY, s);
                    }
                    @Override
                    public void onFailure(String msg, String code) {
                        ToastUtils.showToast(msg);
                    }
                }));
    }

    //获取手机验证码
    public void sendPhoneSmsCode(String phone, String phone_head, String
            client,int type) {
        addDisposable(mRetrofit.create(IEmailRetrieveService.class)
                .getPhoneSmsCode(phone, client,phone_head, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new StrAbsHttpSubscriber() {

                    @Override
                    public void onSuccess(String smsCode) {
                        postData(EVENT_SENDPHONESMSCODE_KEY, smsCode);

                    }

                    @Override
                    public void onFailure(String msg, String code) {
                        ToastUtils.showToast(msg);

                    }
                }));
    }

    //获取邮箱验证码
    public void sendEmail(String email,String client, int type) {
        addDisposable(mRetrofit.create(IEmailRetrieveService.class)
                .toGetVerificationCode(email,client, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new StrAbsHttpSubscriber() {
                    @Override
                    public void onSuccess(String msg) {
                        postData(EVENT_SENDEMAIL_KEY,msg);
                    }

                    @Override
                    public void onFailure(String msg, String code) {
                        ToastUtils.showToast(msg);
                    }
                }));

    }
}
