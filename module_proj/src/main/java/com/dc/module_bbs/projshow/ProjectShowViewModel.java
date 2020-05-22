package com.dc.module_bbs.projshow;

import android.app.Application;
import android.support.annotation.NonNull;

import com.dc.baselib.mvvm.AbsViewModel;

public class ProjectShowViewModel extends AbsViewModel<ProjectShowRespository> {
    public ProjectShowViewModel(@NonNull Application application) {
        super(application);
    }
}
