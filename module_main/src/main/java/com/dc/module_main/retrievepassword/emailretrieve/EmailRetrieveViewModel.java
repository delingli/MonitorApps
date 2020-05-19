package com.dc.module_main.retrievepassword.emailretrieve;

import android.app.Application;
import android.support.annotation.NonNull;

import com.dc.baselib.mvvm.AbsViewModel;
import com.dc.commonlib.common.ConfigUtils;
import com.dc.commonlib.utils.UIUtils;
import com.dc.commonlib.utils.UUIDUtils;

import java.util.HashMap;
import java.util.Map;

public class EmailRetrieveViewModel extends AbsViewModel<ForgetPasswordRepository> {
    public EmailRetrieveViewModel(@NonNull Application application) {
        super(application);
    }

    //邮箱确定
    public void retrieveWithEmail(String email, String sms_code, String password) {
        Map<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("sms_code", sms_code);
        map.put("password", password);//明文
        mRepository.retrieveWithEmail(map);
    }
    public void retrieveWithPhone(String phone_head,String phone, String sms_code, String password) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("phone_head", phone_head);
        map.put("sms_code", sms_code);
        map.put("password", password);//明文
        mRepository.retrieveWithPhone(map);
    }

    //发送邮箱验证码
    public void sendEmail(String email) {
        mRepository.sendEmail(email, ConfigUtils.ANDROID,3);
    }
    public void sendPhone(String phone,String phone_head) {
        mRepository.sendPhoneSmsCode(phone, phone_head,ConfigUtils.ANDROID,3);
    }
}
