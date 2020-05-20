package com.dc.module_bbs.projectlist;

import android.app.Application;
import android.support.annotation.NonNull;

import com.dc.baselib.mvvm.AbsViewModel;

public class ProjectListViewModel extends AbsViewModel<ProjectListRespository> {
    public ProjectListViewModel(@NonNull Application application) {
        super(application);
    }
}
