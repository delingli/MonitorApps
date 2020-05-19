package com.dc.module_me.bindemail;

import android.app.Application;
import android.support.annotation.NonNull;

import com.dc.baselib.mvvm.AbsViewModel;
import com.dc.commonlib.common.ConfigUtils;

public class BindEmailViewModel extends AbsViewModel<BindEmailRespository> {
    public BindEmailViewModel(@NonNull Application application) {
        super(application);
    }

    public void sendEmailCode(String email) {
        mRepository.sendEmailCode(email, ConfigUtils.ANDROID, 2);
    }


    public void bindEmail(String uid, String email, String sms_code) {
        mRepository.bindEmail(uid, email, sms_code);
    }
}
