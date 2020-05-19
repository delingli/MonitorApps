package com.dc.module_main.ui.home;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.dc.baselib.mvvm.AbsViewModel;
import com.dc.baselib.utils.UserManager;
import com.dc.commonlib.utils.UIUtils;
import com.dc.commonlib.utils.UUIDUtils;

import java.util.HashMap;
import java.util.Map;

public class HomeViewModel extends AbsViewModel<HomeRespository> {


    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    public void getSignStatus(String uid) {
        if (!TextUtils.isEmpty(uid)) {
            mRepository.getSignStatus(uid);
        }

    }

    public void signIn(String uid) {
        if (!TextUtils.isEmpty(uid)) {
            Map<String, String> params = new HashMap<>();
            params.put("uid", uid);
            params.put("timestamp", System.currentTimeMillis() + "");
            params.put("nonce", UUIDUtils.createUUid());
            params.put("token", UserManager.getInstance().getToken());
            params.put("sign", UIUtils.getSign(params));
            mRepository.signIn(params);
        }

    }
}