package com.dc.module_bbs.bbsmain;

import android.app.Application;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.dc.baselib.mvvm.AbsViewModel;

public class FocusPlateViewModel extends AbsViewModel<FocusPlateRespository> {
    public FocusPlateViewModel(@NonNull Application application) {
        super(application);
    }

     //板块
    public void moduleList() {
        mRepository.moduleList();
    }
    //关注
    public void moduleList(String uid) {
        if(!TextUtils.isEmpty(uid)){
            mRepository.moduleList(uid);

        }
    }

}
