package com.dc.module_me.bindmobile;

import android.app.Application;
import android.support.annotation.NonNull;

import com.dc.baselib.mvvm.AbsViewModel;
import com.dc.commonlib.common.ConfigUtils;

public class BindMobileViewModel extends AbsViewModel<BindMobileRespository> {
    public BindMobileViewModel(@NonNull Application application) {
        super(application);
    }

    public void sendSms(String phone, String phone_head ) {
        mRepository.sendPhoneSmsCode(phone, ConfigUtils.ANDROID, phone_head, 2);
    }
    public void bindPhone(String uid,String phone_head,String phone, String sms_code) {
        mRepository.bindPhone( uid, phone_head, phone,  sms_code);
    }
}
