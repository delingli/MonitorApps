package com.dc.module_main.regist;

import android.app.Application;
import android.support.annotation.NonNull;

import com.dc.baselib.mvvm.AbsViewModel;
import com.dc.baselib.utils.Md5Util;
import com.dc.commonlib.common.ConfigUtils;
import com.dc.commonlib.utils.LogUtil;
import com.dc.commonlib.utils.UIUtils;
import com.dc.commonlib.utils.UUIDUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistViewModel extends AbsViewModel<RegistRepository> {
    public RegistViewModel(@NonNull Application application) {
        super(application);
    }

    public void regist(String email, String verificationCode, String userName, String password,String raw_password) {
        Map<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("sms_code", verificationCode);
        map.put("password", password);
        map.put("username", userName);
        map.put("source", ConfigUtils.SOURCE);
        map.put("timestamp", System.currentTimeMillis() + "");
        map.put("nonce", UUIDUtils.createUUid());
//        map.put("raw_password ",raw_password);
        map.put("sign", UIUtils.getSign(map));
        mRepository.regist(map);
    }

    public void toGetVerificationCode(String email) {
        mRepository.toGetVerificationCode(email, 1,ConfigUtils.ANDROID);
    }

}
