package com.dc.module_bbs.projectoverview;

import android.os.Bundle;

import com.dc.baselib.mvvm.AbsLifecycleActivity;
import com.dc.module_bbs.R;

/**
 * "项目概况"
 */
public class ProjectOverviewActivity extends AbsLifecycleActivity<ProjectOverviewViewModel> {
    @Override
    protected int getLayout() {
        return R.layout.proj_activity_projectoverview;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
    }

    @Override
    protected void initData() {

    }
}
