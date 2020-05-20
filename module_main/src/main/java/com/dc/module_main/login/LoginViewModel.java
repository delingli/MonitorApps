package com.dc.module_main.login;

import android.app.Application;
import android.support.annotation.NonNull;

import com.dc.baselib.mvvm.AbsViewModel;

public class LoginViewModel extends AbsViewModel <LoginRespository>{
    public LoginViewModel(@NonNull Application application) {
        super(application);
    }
}
