package com.dc.module_bbs.labordata;

import android.os.Bundle;

import com.dc.baselib.mvvm.AbsLifecycleActivity;
import com.dc.module_bbs.R;

/**
 * "劳务数据"
 * proj_labordata_item
 * proj_labordata_item_progress.xml
 * proj_labordata_item_lab
 */
public class LaborDataActivity extends AbsLifecycleActivity<LaborDataViewModel> {
    @Override
    protected int getLayout() {
        return R.layout.common_refresh_layout;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setTitle(R.string.labor_data);
    }

    @Override
    protected void initData() {

    }
}
