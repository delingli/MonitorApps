package com.dc.module_home.homamain;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dc.baselib.mvvm.AbsLifecycleFragment;
import com.dc.module_home.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

public class HomeMainFragment extends AbsLifecycleFragment<HomeMainViewModel> {

    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    @Override
    public void dataObserver() {

    }

    @Override
    public void initView(View view) {
        super.initView(view);
        recyclerView = view.findViewById(R.id.recyclerView);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableLoadMore(false);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayout() {
        return R.layout.common_refresh_layout;
    }
}
