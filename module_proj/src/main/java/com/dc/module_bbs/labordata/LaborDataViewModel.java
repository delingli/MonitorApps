package com.dc.module_bbs.labordata;

import android.app.Application;
import android.support.annotation.NonNull;

import com.dc.baselib.mvvm.AbsViewModel;

public class LaborDataViewModel extends AbsViewModel<LaborDataRespository> {
    public LaborDataViewModel(@NonNull Application application) {
        super(application);
    }
}
