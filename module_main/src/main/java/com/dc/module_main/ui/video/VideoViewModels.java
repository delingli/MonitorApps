package com.dc.module_main.ui.video;

import android.app.Application;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.dc.baselib.mvvm.AbsViewModel;
import com.dc.baselib.utils.UserManager;
import com.dc.commonlib.utils.UIUtils;
import com.dc.commonlib.utils.UUIDUtils;

import java.util.HashMap;
import java.util.Map;

public class VideoViewModels extends AbsViewModel<VideoViewRespository> {

    public VideoViewModels(@NonNull Application application) {
        super(application);
    }
    public void getSignStatus(String uid) {
        if (!TextUtils.isEmpty(uid)) {
            mRepository.getSignStatus(uid);
        }

    }
    public void categorys() {
        mRepository.categorys("1");
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
