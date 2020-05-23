package com.dc.module_home.homamain;

import android.app.Application;
import android.support.annotation.NonNull;

import com.dc.baselib.mvvm.AbsViewModel;

public class HomeMainViewModel<HomeMainRespository> extends AbsViewModel {
    public HomeMainViewModel(@NonNull Application application) {
        super(application);
    }
}
