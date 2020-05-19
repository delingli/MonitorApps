package com.dc.module_me.personinfo;

import android.app.Application;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.dc.baselib.mvvm.AbsViewModel;

public class PersonInfoViewModel extends AbsViewModel<PersonInfoRespository> {
    public PersonInfoViewModel(@NonNull Application application) {
        super(application);
    }

    public void getUserInfo(String uid) {
        if(!TextUtils.isEmpty(uid)){
            mRepository.getUserInfo(uid);
        }
    }
}
