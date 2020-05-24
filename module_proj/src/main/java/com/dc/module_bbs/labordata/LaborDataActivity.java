package com.dc.module_bbs.labordata;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dc.baselib.mvvm.AbsLifecycleActivity;
import com.dc.module_bbs.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

/**
 * "劳务数据"
 * proj_labordata_item
 * proj_labordata_item_progress.xml
 * proj_labordata_item_lab
 */
public class LaborDataActivity extends AbsLifecycleActivity<LaborDataViewModel> {

    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefreshLayout;
    private LaborDataAdapter mLaborDataAdapter;

    @Override
    protected int getLayout() {
        return R.layout.common_refresh_layout;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setTitle(R.string.labor_data);
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setBackgroundColor(getResources().getColor(R.color.bg_color_f7f8f9));
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setEnableRefresh(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mLaborDataAdapter = new LaborDataAdapter(this, mViewModel.getData(), -1));
    }

    @Override
    protected void initData() {

    }
}
