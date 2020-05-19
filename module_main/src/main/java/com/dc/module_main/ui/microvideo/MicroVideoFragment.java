package com.dc.module_main.ui.microvideo;

import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.alibaba.android.arouter.launcher.ARouter;
import com.dc.baselib.mvvm.AbsLifecycleFragment;
import com.dc.baselib.utils.UserManager;
import com.dc.commonlib.common.ConfigUtils;
import com.dc.commonlib.share.CustomShareDialog;

import com.dc.commonlib.utils.ActivityUtils;
import com.dc.commonlib.utils.ArounterManager;
import com.dc.commonlib.utils.UIUtils;
import com.dc.module_main.R;
import com.dc.module_main.login.LoginActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.yczbj.ycvideoplayerlib.manager.VideoPlayerManager;
import org.yczbj.ycvideoplayerlib.player.VideoPlayer;

import java.util.List;

public class MicroVideoFragment extends AbsLifecycleFragment<MicroVideoViewModel> implements OnLoadMoreListener, OnRefreshListener {

    private SmartRefreshLayout mSmartRefreshLayout;
    private RecyclerView mRecyclerView;


    @Override
    public void dataObserver() {
        registerSubscriber(mViewModel.mRepository.KEY_MICRO_VIDEO, List.class).observe(this, new Observer<List>() {


            @Override
            public void onChanged(@Nullable List list) {
                if (mSmartRefreshLayout.getState() == RefreshState.Loading) {
                    mMicroVideoAdapter.addList(list);
                } else {
                    mMicroVideoAdapter.setList(list);
                }
                mSmartRefreshLayout.finishRefresh();
                mSmartRefreshLayout.finishLoadMore();
            }
        });
        registerSubscriber(mViewModel.mRepository.KEY_FINISH_VIDEO, String.class).observe(this, new Observer<String>() {


            @Override
            public void onChanged(@Nullable String list) {
                mSmartRefreshLayout.finishRefresh();
                mSmartRefreshLayout.finishLoadMore();
            }
        });

        registerSubscriber(mViewModel.mRepository.KEY_CANCELFOLLOW, String.class).observe(this, new Observer<String>() {


            @Override
            public void onChanged(@Nullable String list) {
                mViewModel.getEduWeishi(true, UserManager.getInstance().getUserId());

            }
        });
        registerSubscriber(mViewModel.mRepository.KEY_FOLLOWMEMBER, String.class).observe(this, new Observer<String>() {


            @Override
            public void onChanged(@Nullable String list) {
                mViewModel.getEduWeishi(true, UserManager.getInstance().getUserId());

            }
        });
    }

    private MicroVideoAdapterz mMicroVideoAdapter;

    @Override
    public void initView(View view) {
        super.initView(view);
        mSmartRefreshLayout = view.findViewById(R.id.refreshLayout);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mSmartRefreshLayout.setOnRefreshListener(this);
        mSmartRefreshLayout.setOnLoadMoreListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mMicroVideoAdapter = new MicroVideoAdapterz(getActivity(), null));
        mMicroVideoAdapter.addOnItemClickListener(new MicroVideoAdapterz.OnItemClickListener() {
            @Override
            public void onItem(int flag, MicroVideos microVideos) {
                if (null != microVideos) {
                    if (flag == MicroVideoAdapterz.FLAG_SHARE) {
                        CustomShareDialog.getShareDialog(microVideos.getTasktitle(), microVideos.getPic(), microVideos.getGo_url())
                                .show(getActivity().getSupportFragmentManager(), "suport");
                    } else if (flag == MicroVideoAdapterz.FLAG_DETAIL) {
                        ARouter.getInstance().build(ArounterManager.COURSEDETAILSACTIVITY_URL).withString(ConfigUtils.COURSE_ID, microVideos.getCourseid()).navigation();
                    } else if (flag == MicroVideoAdapterz.FLAG_FOCUEON) {
                        if (!UserManager.getInstance().isLogin()) {
                            LoginActivity.startActivity(getActivity());
                            return;
                        }
                        if (microVideos.getIs_focus() == 0) {//未关注 去关注
                            mViewModel.followMember(UserManager.getInstance().getUserId(), microVideos.getUid());
                        } else if (microVideos.getIs_focus() == 1) {
                            mViewModel.cancelFollow(UserManager.getInstance().getUserId(), microVideos.getUid());

                        }


                    }
                }

            }
        });
        mRecyclerView.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(@NonNull RecyclerView.ViewHolder viewHolder) {
                if (viewHolder instanceof MicroVideoAdapterz.VideoViewHolder) {
                    MicroVideoAdapterz.VideoViewHolder holder = (MicroVideoAdapterz.VideoViewHolder) viewHolder;
                    VideoPlayer niceVideoPlayer = holder.getVideoPlayer();
                    if (niceVideoPlayer == VideoPlayerManager.instance().getCurrentVideoPlayer()) {
                        VideoPlayerManager.instance().releaseVideoPlayer();
                    }
                }
            }
        });
        mViewModel.getEduWeishi(true, UserManager.getInstance().getUserId());

    }

    @Override
    public void onPause() {
        super.onPause();
        if (null != VideoPlayerManager.instance().getCurrentVideoPlayer()) {
            VideoPlayerManager.instance().getCurrentVideoPlayer().pause();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        VideoPlayerManager.instance().releaseVideoPlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        VideoPlayerManager.instance().resumeVideoPlayer();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayout() {
        return R.layout.common_refresh_layout;
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mViewModel.getEduWeishi(false, UserManager.getInstance().getUserId());
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mViewModel.getEduWeishi(true, UserManager.getInstance().getUserId());
    }
}