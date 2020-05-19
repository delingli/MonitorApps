package com.dc.module_main.bindaccount;

import android.app.Application;
import android.support.annotation.NonNull;

import com.dc.baselib.mvvm.AbsViewModel;
import com.dc.baselib.utils.Md5Util;
import com.dc.commonlib.utils.UIUtils;
import com.dc.commonlib.utils.UUIDUtils;

import java.util.HashMap;
import java.util.Map;

public class BindAccountViewModel extends AbsViewModel<BindAccountRespository> {
    public BindAccountViewModel(@NonNull Application application) {
        super(application);
    }

    public void bindAccountForWeCharLogin(String account, String password,
                                          String raw_password, String wx_openid) {
        Map<String, String> params = new HashMap<>();
        params.put("account", account);
        params.put("password", Md5Util.md5(password));
        params.put("wx_openid", wx_openid);
        params.put("raw_password", raw_password);
        params.put("timestamp", System.currentTimeMillis() + "");
        params.put("nonce", UUIDUtils.createUUid());
        params.put("sign", UIUtils.getSign(params));
        mRepository.bindForWeCharLogin(params);
    }

    public void bindAccountForQQLogin(String account, String password,
                                      String raw_password, String qq_id) {
        Map<String, String> params = new HashMap<>();
        params.put("account", account);
        params.put("password", Md5Util.md5(password));
        params.put("qq_id", qq_id);
        params.put("raw_password", raw_password);
        params.put("timestamp", System.currentTimeMillis() + "");
        params.put("nonce", UUIDUtils.createUUid());
        params.put("sign", UIUtils.getSign(params));
        mRepository.bindForWeCharLogin(params);

    }
}
