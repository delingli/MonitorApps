package com.dc.module_me.changeinfo;

import android.app.Application;
import android.support.annotation.NonNull;

import com.dc.baselib.mvvm.AbsViewModel;
import com.dc.baselib.mvvm.EventUtils;

public class ChangeInfoViewModel extends AbsViewModel<ChangeInfoRespository> {


    public ChangeInfoViewModel(@NonNull Application application) {
        super(application);
    }
    public void updateNickname(String uid ,String nickname){
        mRepository.updateNickname(uid,nickname);
    }
    public void updateSignature(String uid ,String sightml){
        mRepository.updateSignature(uid,sightml);
    }
}
