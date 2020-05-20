package com.dc.module_bbs.bbsdetail.bbsdetailfragment;

import android.app.Application;
import android.support.annotation.NonNull;

import com.dc.baselib.mvvm.AbsViewModel;
import com.dc.commonlib.common.ConfigUtils;

public class BBSDetailFragmentViewModel extends AbsViewModel<BBSDetailFragmentRepostory> {
    public BBSDetailFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    public void listLearnRecord(String forumid, int start, String order, String uid) {
        mRepository.listLearnRecord(forumid,start, ConfigUtils.LIMIT, order, uid);
    }
}
