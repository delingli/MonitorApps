package com.dc.module_bbs;

import android.arch.lifecycle.Observer;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dc.baselib.mvvm.AbsLifecycleFragment;
import com.dc.baselib.mvvm.BaseFragment;
import com.dc.baselib.utils.UserManager;
import com.dc.commonlib.common.BaseRecyclerAdapter;
import com.dc.commonlib.common.ConfigUtils;
import com.dc.commonlib.utils.ArounterManager;
import com.dc.module_bbs.bbsdetail.BBSDetailActivity;
import com.dc.module_bbs.bbsmain.AbsFocusDiscuss;
import com.dc.module_bbs.bbsmain.BBSListAdapter;
import com.dc.module_bbs.bbsmain.FocusPlateItem;
import com.dc.module_bbs.bbsmain.FocusPlateViewModel;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

//论坛关注,论坛板块
@Route(path = ArounterManager.BBS_BBSFOCEONFRAGMENT_URL)
public class BBSFoceOnFragment extends AbsLifecycleFragment<FocusPlateViewModel> implements OnRefreshListener {

    private int current_type;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private BBSListAdapter mBBSListAdapter;

    @Override
    public void initView(View view) {
        super.initView(view);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRefreshLayout = view.findViewById(R.id.refreshLayout);
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setOnRefreshListener(this);
        if (getArguments() != null) {
            current_type = getArguments().getInt(ConfigUtils.KEY_TYPE, ConfigUtils.type_1);
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (current_type == ConfigUtils.type_1) {
            if (UserManager.getInstance().isLogin()) {
                mViewModel.moduleList(UserManager.getInstance().getUserId());
            }
        } else {
            mViewModel.moduleList();
        }
    }

    @Override
    public void dataObserver() {
        registerSubscriber(mViewModel.mRepository.KEY_PLATE_FOCUS_LIST, List.class).observe(this, new Observer<List>() {
            @Override
            public void onChanged(@Nullable List sstr) {
                mBBSListAdapter = new BBSListAdapter(getContext(), sstr, 0);
                mRecyclerView.setAdapter(mBBSListAdapter);
//                tv_fruitplate.setVisibility(View.GONE);
                mBBSListAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClickListener(View v, int position) {
                        if (mBBSListAdapter.getList() != null && mBBSListAdapter.getList().get(position) != null) {
                            AbsFocusDiscuss absFocusDiscuss = mBBSListAdapter.getList().get(position);
                            if (absFocusDiscuss instanceof FocusPlateItem.ChildBean) {
                                FocusPlateItem.ChildBean childBean= (FocusPlateItem.ChildBean) absFocusDiscuss;
                                BBSDetailActivity.startActivity(getActivity(),childBean.forumid);
                            } else if (absFocusDiscuss instanceof FocusPlateItem) {
                                FocusPlateItem focusPlateItem= (FocusPlateItem) absFocusDiscuss;
                                BBSDetailActivity.startActivity(getActivity(),focusPlateItem.forumid);

                            }
                        }

                    }
                });
            }
        });

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
        if (current_type == ConfigUtils.type_1) {
            mViewModel.moduleList(UserManager.getInstance().getUserId());
        } else {
            mViewModel.moduleList();
        }

    }
}
