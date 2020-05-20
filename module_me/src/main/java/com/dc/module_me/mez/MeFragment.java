package com.dc.module_me.mez;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dc.baselib.mvvm.BaseFragment;
import com.dc.commonlib.utils.ArounterManager;
import com.dc.module_me.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

@Route(path = ArounterManager.ME_MEINFO_URL)

public class MeFragment extends BaseFragment {

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private List<MeDataItems> mList;
    private MezFragmentAdapter mMezFragmentAdapter;
    private TextView tv_title;

    @Override
    public void initView(View view) {
        tv_title = view.findViewById(R.id.tv_title);
        tv_title.setText(R.string.me_title);
        mRefreshLayout = view.findViewById(R.id.refreshLayout);
        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setEnableLoadMore(false);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mList = new ArrayList<>();

        MeDataItems meDataItems = new MeDataItems(getResources().getString(R.string.name), "张三");
        MeDataItems meDataItems2 = new MeDataItems(getResources().getString(R.string.mobile_phone_desc), "张三");
        mList.clear();
        mList.add(meDataItems);
        mList.add(meDataItems2);
        mRecyclerView.setAdapter(mMezFragmentAdapter = new MezFragmentAdapter(getContext(), mList, -1));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayout() {
//        me_fragment_items.xml
        return R.layout.me_fragment;
    }
}
