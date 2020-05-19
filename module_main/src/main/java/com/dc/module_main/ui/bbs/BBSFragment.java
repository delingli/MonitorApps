package com.dc.module_main.ui.bbs;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.dc.baselib.mvvm.BaseFragment;
import com.dc.commonlib.utils.UIUtils;
import com.dc.module_main.R;
import com.dc.module_main.ui.home.FtViewPagerAdapter;

public class BBSFragment extends BaseFragment {


    private TabLayout mTablayout;
    private ImageView mIvSearch;
    private ViewPager mViewpager;
    private FtViewPagerAdapter mHomeFragmentViewPagerAdapter;

    @Override
    public void initView(View view) {
        mIvSearch = view.findViewById(R.id.iv_search);
        mTablayout = view.findViewById(R.id.tablayout);
        mViewpager = view.findViewById(R.id.viewpager);
        String[] titles = UIUtils.getStringArray(getContext(), R.array.home_bbs_items);
        mHomeFragmentViewPagerAdapter = new FtViewPagerAdapter(getChildFragmentManager(), FtViewPagerAdapter.TYPE_BBS, titles);
        mViewpager.setAdapter(mHomeFragmentViewPagerAdapter);
        mTablayout.setupWithViewPager(mViewpager);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayout() {
        return R.layout.main_bbs_fragment;
    }
}
