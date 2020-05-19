package com.dc.module_main.ui.bbs;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class BBSViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public BBSViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("论坛");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
