package com.dc.module_main.improveinfo;

import android.app.Application;
import android.support.annotation.NonNull;

import com.dc.baselib.mvvm.AbsViewModel;

public class ImproveinfoViewModel extends AbsViewModel<ImproveinfoRepository> {
    public ImproveinfoViewModel(@NonNull Application application) {
        super(application);
    }
}
