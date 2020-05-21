package com.dc.module_bbs.projectoverview;

import android.app.Application;
import android.support.annotation.NonNull;

import com.dc.baselib.mvvm.AbsViewModel;

public class ProjectOverviewViewModel extends AbsViewModel<ProjectOverviewRespository> {
    public ProjectOverviewViewModel(@NonNull Application application) {
        super(application);
    }
}
