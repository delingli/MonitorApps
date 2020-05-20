package com.dc.module_bbs.bbsdetail.bbsdetailfragment;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.dc.baselib.mvvm.AbsLifecycleFragment;
import com.dc.baselib.utils.UserManager;
import com.dc.module_bbs.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

public class BBSDetailFragment extends AbsLifecycleFragment<BBSDetailFragmentViewModel> implements OnRefreshLoadMoreListener {

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private BBSDetailAdapter mBBSDetailAdapter;

    @Override
    public void dataObserver() {
        registerSubscriber(mViewModel.mRepository.KEY_NODATAEVENT, String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String ll) {
                if (mRefreshLayout.getState() == RefreshState.Loading) {
                    mRefreshLayout.finishLoadMoreWithNoMoreData();
                } else {
                    mRefreshLayout.finishRefreshWithNoMoreData();
                }
            }
        });
        registerSubscriber(mViewModel.mRepository.KEY_THEMEINFORUM_DATA, List.class).observe(this, new Observer<List>() {
            @Override
            public void onChanged(@Nullable List ll) {
                if (mRefreshLayout.getState() == RefreshState.Loading) {
                    if (null != mBBSDetailAdapter) {
                        mBBSDetailAdapter.addList(ll);
                    }

                } else {
                    mRecyclerView.setAdapter(mBBSDetailAdapter = new BBSDetailAdapter(getContext(), ll, 0));
                }

                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
            }
        });
    }

    public static String KEY_BBS = "ffpeg";
    public static String KEY_FORUMID = "dforumidffpeg";

    public static String BBS_NEWS_RTEPLY = "233dd";
    public static String BBS_NEWS_PUBLISHED = "abbns233dd";
    public static String BBS_NEWS_ESSENCE = "bndxmd233dd";
    private String forumid;
    public String BBS_TAG = BBS_NEWS_RTEPLY;

    public static BBSDetailFragment newInstance(String forumid, String tag) {
        BBSDetailFragment bbsDetailFragment = new BBSDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_BBS, tag);
        bundle.putString(KEY_FORUMID, forumid);
        bbsDetailFragment.setArguments(bundle);
        return bbsDetailFragment;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void initView(View view) {
        super.initView(view);
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            BBS_TAG = bundle.getString(KEY_BBS);
            forumid = bundle.getString(KEY_FORUMID);
        }
        mRefreshLayout = view.findViewById(R.id.refreshLayout);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBBSDetailAdapter = new BBSDetailAdapter(getContext(), null, 0);
        mRefreshLayout.autoRefresh();
    }

    @Override
    protected int getLayout() {
        return R.layout.common_refresh_layout;
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (TextUtils.equals(BBS_TAG, BBS_NEWS_RTEPLY)) {
            mViewModel.listLearnRecord(forumid, mBBSDetailAdapter.getItemCount(), "lastpost", UserManager.getInstance().getUserId());
        } else if (TextUtils.equals(BBS_TAG, BBS_NEWS_PUBLISHED)) {
            mViewModel.listLearnRecord(forumid, mBBSDetailAdapter.getItemCount(), "dateline", UserManager.getInstance().getUserId());

        } else if (TextUtils.equals(BBS_TAG, BBS_NEWS_ESSENCE)) {
            mViewModel.listLearnRecord(forumid, mBBSDetailAdapter.getItemCount(), "hot", UserManager.getInstance().getUserId());
        }
    }
    /*    排序字段（lastpost最新回复时间 dateline最新发布时间 hot 精华帖子 默认最新回复）*/

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (TextUtils.equals(BBS_TAG, BBS_NEWS_RTEPLY)) {
            mViewModel.listLearnRecord(forumid, 0, "lastpost", UserManager.getInstance().getUserId());
        } else if (TextUtils.equals(BBS_TAG, BBS_NEWS_PUBLISHED)) {
            mViewModel.listLearnRecord(forumid, 0, "dateline", UserManager.getInstance().getUserId());
        } else if (TextUtils.equals(BBS_TAG, BBS_NEWS_ESSENCE)) {
            mViewModel.listLearnRecord(forumid, 0, "hot", UserManager.getInstance().getUserId());
        }

    }
}
