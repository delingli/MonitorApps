package com.dc.module_bbs.projsummary;

import android.app.Application;
import android.support.annotation.NonNull;

import com.dc.baselib.mvvm.AbsViewModel;

public class ProjectSummaryViewModel extends AbsViewModel <ProjectSummaryRespository>{
    public ProjectSummaryViewModel(@NonNull Application application) {
        super(application);
    }
}
