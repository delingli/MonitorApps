package com.dc.module_home.homamain;

import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dc.baselib.mvvm.AbsLifecycleFragment;
import com.dc.commonlib.utils.ArounterManager;
import com.dc.module_home.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

@Route(path = ArounterManager.HOME_HOMEMAINFRAGMENT_URL)
public class HomeMainFragment extends AbsLifecycleFragment<HomeMainViewModel> implements OnRefreshListener {

    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private HomeMainAdapter mHomeMainAdapter;

    @Override
    public void dataObserver() {

        registerSubscriber(mViewModel.mRepository.EVENT_KEY_RELEASE_FAILURE, List.class).observe(this, new Observer<List>() {
            @Override
            public void onChanged(@Nullable List sstr) {
                mHomeMainAdapter.setList(sstr);
                refreshLayout.finishRefresh();
            }
        });
        registerSubscriber(mViewModel.mRepository.EVENT_FINISHREFRESH, String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String sstr) {
                refreshLayout.finishRefresh();
            }
        });
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        recyclerView = view.findViewById(R.id.recyclerView);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setBackgroundColor(getResources().getColor(R.color.white));
        recyclerView.setBackgroundColor(getResources().getColor(R.color.white));
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.autoRefresh();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mHomeMainAdapter = new HomeMainAdapter(getContext(), null, -1));
    }


    @Override
    protected void initData() {

    }

    @Override
    protected int getLayout() {
        return R.layout.common_refresh_layout;
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mViewModel.toGetownerCompanyBoard();
    }
}
