package com.dc.module_home.homamain;

import android.app.Application;
import android.support.annotation.NonNull;

import com.dc.baselib.mvvm.AbsViewModel;

public class HomeMainViewModel extends AbsViewModel<HomeMainRespositorys> {
    public HomeMainViewModel(@NonNull Application application) {
        super(application);
    }
}
