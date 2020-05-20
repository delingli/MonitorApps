package com.dc.module_bbs.projsummary;

import android.os.Bundle;

import com.dc.baselib.mvvm.AbsLifecycleActivity;
import com.dc.module_bbs.R;
//项目概述
public class ProjectSummaryActivity extends AbsLifecycleActivity<ProjectSummaryViewModel> {
    @Override
    protected int getLayout() {
        return R.layout.proj_activity_projectsummary;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setTitle("银湖测试");
    }

    @Override
    protected void initData() {

    }
}
