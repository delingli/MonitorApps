package com.dc.module_bbs.searchlist;

import android.app.Application;
import android.support.annotation.NonNull;

import com.dc.baselib.mvvm.AbsViewModel;

public class SearchListViewModel extends AbsViewModel<SearchListRespository> {
    public SearchListViewModel(@NonNull Application application) {
        super(application);
    }
}
