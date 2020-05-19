package com.dc.module_bbs.bbsdetail;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dc.baselib.mvvm.AbsLifecycleActivity;
import com.dc.baselib.mvvm.BaseFragment;
import com.dc.baselib.statusBar.StarusBarUtils;
import com.dc.baselib.utils.UserManager;
import com.dc.commonlib.common.AppBarStateChangeListener;
import com.dc.commonlib.common.ConfigUtils;
import com.dc.commonlib.common.RecordPagerAdapter;
import com.dc.commonlib.utils.ArounterManager;
import com.dc.commonlib.utils.GlideUtils;
import com.dc.commonlib.utils.UIUtils;
import com.dc.commonlib.weiget.FocusOnTextView;
import com.dc.module_bbs.R;
import com.dc.module_bbs.bbsdetail.bbsdetailfragment.BBSDetailFragment;
import com.dc.module_bbs.bbsdetail.fruitplate.FruitPlateFragment;
import com.scwang.smartrefresh.layout.constant.RefreshState;

import java.util.ArrayList;
import java.util.List;

@Route(path = ArounterManager.BBSDETAIL_URL)
public class BBSDetailActivity extends AbsLifecycleActivity<BBSDetailViewModel> implements AppBarLayout.OnOffsetChangedListener, View.OnClickListener {

    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsing;
    private ViewPager mViewpager;
    private TabLayout mTabs;
    private RecyclerView mRecycle_moderators;
    private FocusOnTextView tv_focus_ons;
    private String fid = "8";//板块id
    private ImageView iv_big_icon;
    private TextView tv_name, tv_bbs_number_posts, tv_fruitplate;
    private ImageView iv_bbs_head;
    private TextView tv_title;
    private List<BBsDetails> mFruitPlates;

    public static void startActivity(Context context, String fuid) {
        Intent intent = new Intent();
        intent.putExtra(ConfigUtils.F_UID, fuid);
        intent.setClass(context, BBSDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.bbs_activity_bbsdetail;
    }

    private List<BaseFragment> mList;
    private RecordPagerAdapter mRecordPagerAdapter;

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        StarusBarUtils.setRootViewFitsSystemWindows(this, false);
        StarusBarUtils.setTranslucentStatus(this);
        setmToolBarlheadHide(true);
        mAppBarLayout = findViewById(R.id.appBarlayout);
        mCollapsing = findViewById(R.id.collapsing);
        tv_name = findViewById(R.id.tv_name);
        tv_fruitplate = findViewById(R.id.tv_fruitplate);
        tv_bbs_number_posts = findViewById(R.id.tv_bbs_number_posts);
        mViewpager = findViewById(R.id.viewpager);
        iv_big_icon = findViewById(R.id.iv_big_icon);
        mToolbar = findViewById(R.id.toolbar);
        tv_fruitplate.setOnClickListener(this);
        findViewById(R.id.iv_left_back).setOnClickListener(this);
        mTabs = findViewById(R.id.tabs);
        iv_bbs_head = findViewById(R.id.iv_bbs_head);
        tv_focus_ons = findViewById(R.id.tv_focus_ons);
        tv_title = findViewById(R.id.tv_title);
        mRecycle_moderators = findViewById(R.id.recycle_moderators);
        if (getIntent() != null) {
            fid = getIntent().getStringExtra(ConfigUtils.F_UID);
        }
        mViewModel.listLearnRecord(fid, UserManager.getInstance().getUserId());
        mViewModel.submoduleList(fid);
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerSubscriber(mViewModel.mRepository.KEY_NO_PLATE, String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String sstr) {
                //
                tv_fruitplate.setVisibility(View.GONE);
            }
        });
        registerSubscriber(mViewModel.mRepository.KEY_PLATE_LIST, List.class).observe(this, new Observer<List>() {
            @Override
            public void onChanged(@Nullable List list) {
                tv_fruitplate.setVisibility(View.VISIBLE);
                mFruitPlates = list;
            }
        });

        registerSubscriber(mViewModel.mRepository.KEY_BBS_DETAIL, BBsDetails.class).observe(this, new Observer<BBsDetails>() {
            @Override
            public void onChanged(@Nullable BBsDetails bbsdetails) {
                fillBBsDetail(bbsdetails);

                mList = new ArrayList<>();
                mList.add(BBSDetailFragment.newInstance(bbsdetails.forumid, BBSDetailFragment.BBS_NEWS_RTEPLY));
                mList.add(BBSDetailFragment.newInstance(bbsdetails.forumid, BBSDetailFragment.BBS_NEWS_PUBLISHED));
                mList.add(BBSDetailFragment.newInstance(bbsdetails.forumid, BBSDetailFragment.BBS_NEWS_ESSENCE));
                mViewpager.setAdapter(mRecordPagerAdapter = new RecordPagerAdapter(UIUtils.getStringArray(BBSDetailActivity.this, R.array.bbs_detail), mList, getSupportFragmentManager()));
                mTabs.setupWithViewPager(mViewpager);

            }
        });
    }

    private void fillBBsDetail(BBsDetails bbsdetails) {
        if (null != bbsdetails) {
            GlideUtils.loadRoundUrl(this, bbsdetails.pic, iv_big_icon);
            GlideUtils.loadRoundUrl(this, bbsdetails.pic, iv_bbs_head);
            tv_name.setText(bbsdetails.forumname);
            tv_title.setText(bbsdetails.forumname);
            tv_bbs_number_posts.setText("主题" + bbsdetails.threads + "帖数" + bbsdetails.posts);
            tv_focus_ons.setFocusOn(bbsdetails.is_focus == 1);
            tv_focus_ons.addOnItemClickListener(new FocusOnTextView.OnItemClickListener() {
                @Override
                public void onClick(boolean toFocusOn) {
                    mViewModel.follow(toFocusOn, UserManager.getInstance().getUserId(), fid);
                }
            });
            if (bbsdetails.moderator_avatar != null) {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                mRecycle_moderators.setLayoutManager(linearLayoutManager);
                ModeratorsAdapter moderatorsAdapter = new ModeratorsAdapter(getApplicationContext(), bbsdetails.moderator_avatar, 0);
                mRecycle_moderators.setAdapter(moderatorsAdapter);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAppBarLayout.addOnOffsetChangedListener(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        mAppBarLayout.removeOnOffsetChangedListener(this);

    }

    @Override
    protected void initStatusBar() {
//        super.initStatusBar();
//            StarusBarUtils.setStatusBarColor(this,R.drawable.bg_corner_bbs_detail_bg);

    }

/*    @Override
    protected void initStatusBar() {
//        super.initStatusBar();
        StarusBarUtils.setRootViewFitsSystemWindows(this, false);
        StarusBarUtils. setTranslucentStatus(this);
        StarusBarUtils.setStatusBarColor(this,R.drawable.bg_corner_bbs_detail_bg);

    }*/

    @Override
    protected void initData() {

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (i == 0) {
            mToolbar.setVisibility(View.GONE);
            //展开
//            mCollapsing.setContentScrim(null);
            mCollapsing.setContentScrim(getResources().getDrawable(R.drawable.bg_corner_bbs_detail_bg));

        } else if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
            mToolbar.setVisibility(View.VISIBLE);
            mCollapsing.setContentScrim(getResources().getDrawable(R.drawable.bg_corner_bbs_detail_bg));

        } else {
//State.IDLE;
            mToolbar.setVisibility(View.GONE);
            //展开
            mCollapsing.setContentScrim(getResources().getDrawable(R.drawable.bg_corner_bbs_detail_bg));
//            mCollapsing.setContentScrim(null);
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_fruitplate) {
            FruitPlateFragment.newInstance((ArrayList<BBsDetails>) mFruitPlates).show(getSupportFragmentManager(), "FruitPlateFragment");
        }

    }
}
