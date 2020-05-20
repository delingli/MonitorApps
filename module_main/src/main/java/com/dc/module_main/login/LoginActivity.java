package com.dc.module_main.login;

import android.os.Bundle;

import com.dc.baselib.mvvm.AbsLifecycleActivity;
import com.dc.module_main.R;

public class LoginActivity extends AbsLifecycleActivity<LoginViewModel> {
    @Override
    protected int getLayout() {
        return R.layout.main_activity_login;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setmToolBarlheadHide(true);
    }

    @Override
    protected void initData() {

    }
}
