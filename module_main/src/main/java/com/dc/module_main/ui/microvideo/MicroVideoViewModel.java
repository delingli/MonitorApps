package com.dc.module_main.ui.microvideo;

import android.app.Application;
import android.support.annotation.NonNull;

import com.dc.baselib.mvvm.AbsViewModel;
import com.dc.commonlib.common.ConfigUtils;

public class MicroVideoViewModel extends AbsViewModel<MicroVideoViewRespository> {


    public MicroVideoViewModel(@NonNull Application application) {
        super(application);
    }


    public void getEduWeishi(boolean refresh, String uid) {
        mRepository.getEduWeishi(refresh, ConfigUtils.LIMIT, uid);
    }
    public void cancelFollow(String uid, String fuid) {
        mRepository.cancelFollow(uid, fuid);
    }

    public void followMember(String uid, String fuid){
        mRepository.followMember(uid, fuid);

    }
}