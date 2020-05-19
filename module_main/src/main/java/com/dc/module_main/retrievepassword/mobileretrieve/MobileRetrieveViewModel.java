package com.dc.module_main.retrievepassword.mobileretrieve;

import android.app.Application;
import android.support.annotation.NonNull;

import com.dc.baselib.mvvm.AbsViewModel;
import com.dc.commonlib.common.ConfigUtils;
import com.dc.module_main.retrievepassword.emailretrieve.ForgetPasswordRepository;

import java.util.HashMap;
import java.util.Map;

public class MobileRetrieveViewModel extends AbsViewModel<ForgetPasswordRepository> {
    public MobileRetrieveViewModel(@NonNull Application application) {
        super(application);
    }

    //手机确定
    public void retrieveWithPhone(String phone, String sms_code, String password, String phone_head) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("sms_code", sms_code);
        map.put("password", password);//明文
        map.put("phone_head", phone_head);//手机区号
        mRepository.retrieveWithPhone(map);
    }

    //发手机验证码
    public void sendPhoneSmsCode(String phone, String phone_head) {
        mRepository.sendPhoneSmsCode(phone, phone_head, ConfigUtils.ANDROID,3);
    }
}
