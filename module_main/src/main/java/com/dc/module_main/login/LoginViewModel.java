package com.dc.module_main.login;

import android.app.Application;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.dc.baselib.mvvm.AbsViewModel;
import com.dc.baselib.utils.Md5Util;
import com.dc.commonlib.utils.UIUtils;
import com.dc.commonlib.utils.UUIDUtils;

import java.util.HashMap;
import java.util.Map;

public class LoginViewModel extends AbsViewModel<LoginRepository> {
    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public static String QQ = "qq";
    public static String WECHAR = "wechar";

    //原始密码
    public void login(String account, String password,
                      String raw_password) {
        Map<String, String> params = new HashMap<>();
        params.put("account", account);
        params.put("password", Md5Util.md5(password));
        params.put("raw_password", raw_password);
        params.put("timestamp", System.currentTimeMillis() + "");
        params.put("nonce", UUIDUtils.createUUid());
        params.put("sign", UIUtils.getSign(params));
        mRepository.login(params);
    }

/*    public void loginWithQQ(String account, String password,
                            String qq_id, String raw_password) {
        Map<String, String> params = new HashMap<>();
        params.put("account", account);
        params.put("password", password);
        params.put("qq_id", qq_id);
        params.put("raw_password", raw_password);
        mRepository.loginBindQQ(params);
    }

    public void loginWithWechar(String account, String password,
                                String wx_openid, String raw_password) {
        Map<String, String> params = new HashMap<>();
        params.put("account", account);
        params.put("password", password);
        params.put("wx_openid", wx_openid);
        params.put("raw_password", raw_password);
        mRepository.loginBindWechar(params);
    }*/

    public void userOtherLogin(String come, String id) {
        if (TextUtils.isEmpty(id)) {
            return;
        }
        Map<String, String> params = new HashMap<>();
        if (come.equals(QQ)) {
            params.put("qq_id", id);

        } else if (come.equals(WECHAR)) {
            params.put("wx_openid", id);
        }
        mRepository.userOtherLogin(params);
    }
}
