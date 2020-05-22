package com.dc.module_bbs.projshow;

import android.os.Bundle;

import com.dc.baselib.mvvm.AbsLifecycleActivity;
import com.dc.module_bbs.R;

/**
 * "项目展示
 */
public class ProjectShowActivity extends AbsLifecycleActivity<ProjectShowViewModel> {
    @Override
    protected int getLayout() {
        return R.layout.proj_activity_projectshow;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
    }

    @Override
    protected void initData() {

    }
}
