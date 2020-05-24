package com.dc.module_bbs.labordata;

import android.app.Application;
import android.support.annotation.NonNull;

import com.dc.baselib.mvvm.AbsViewModel;

import java.util.List;

public class LaborDataViewModel extends AbsViewModel<LaborDataRespository> {
    public LaborDataViewModel(@NonNull Application application) {
        super(application);
    }

    public List<IAbsLaborData> getData() {
        return mRepository.getData();
    }
}
