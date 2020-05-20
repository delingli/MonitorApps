package com.dc.module_bbs.bbsdetail;

import android.app.Application;
import android.support.annotation.NonNull;

import com.dc.baselib.mvvm.AbsViewModel;

public class BBSDetailViewModel extends AbsViewModel<BBSDetailRespository> {

    public BBSDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public void listLearnRecord(String fuid, String uid) {
        mRepository.listLearnRecord(fuid, uid);
    }

    public void submoduleList(String fid) {
        mRepository.submoduleList(fid);

    }

    public void follow(boolean toFocus, String uid, String fid) {
        if (toFocus) {
            mRepository.followMember(uid, fid, 1);

        } else {
            mRepository.followMember(uid, fid, 2);

        }
    }
}
