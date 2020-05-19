package com.dc.module_main.ui.home;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dc.baselib.mvvm.AbsLifecycleFragment;
import com.dc.baselib.mvvm.BaseFragment;
import com.dc.baselib.utils.ToastUtils;
import com.dc.baselib.utils.UserManager;
import com.dc.commonlib.common.ConfigUtils;
import com.dc.commonlib.utils.KeyBoardUtils;
import com.dc.commonlib.utils.UIUtils;
import com.dc.module_main.R;

public class HomeFragment extends AbsLifecycleFragment<HomeViewModel> implements View.OnClickListener {

    private TabLayout mTablayout;
    private ViewPager mViewpager;
    private FtViewPagerAdapter mHomeFragmentViewPagerAdapter;
    private TextView tv_sign_score;
    private FrameLayout mFlSign;

    @Override
    public void initView(View view) {
        super.initView(view);
        mTablayout = view.findViewById(R.id.tablayout);
        mViewpager = view.findViewById(R.id.viewpager);

        String[] titles = UIUtils.getStringArray(getContext(), R.array.home_tab_items);
        mHomeFragmentViewPagerAdapter = new FtViewPagerAdapter(getChildFragmentManager(), FtViewPagerAdapter.TYPE_HOME, titles);
        mViewpager.setAdapter(mHomeFragmentViewPagerAdapter);
        mTablayout.setupWithViewPager(mViewpager);
        tv_sign_score = view.findViewById(R.id.tv_sign_score);
        mFlSign = view.findViewById(R.id.fl_sign);
        mFlSign.setOnClickListener(this);
        mViewModel.getSignStatus(UserManager.getInstance().getUserId());
    }

    private boolean canClick = false;

    @Override
    public void dataObserver() {
        registerSubscriber(mViewModel.mRepository.KEY_SIGN_EVENT, String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String str) {
                tv_sign_score.setVisibility(View.GONE);
                ToastUtils.showToast(getResources().getString(R.string.sign_add_sucess));
            }
        });
        registerSubscriber(mViewModel.mRepository.KEY_SIGNSTATE_EVENT, String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String data) {
                //data 是0 未签到 data 是1 已签到
                if (data .equals("0") ) {
                    tv_sign_score.setText(ConfigUtils.SCORE);
                    tv_sign_score.setVisibility(View.VISIBLE);
                } else {
                    tv_sign_score.setVisibility(View.GONE);

                }
                canClick = !canClick;


            }
        });
    }

    @Override
    protected void initData() {


    }

    @Override
    protected int getLayout() {
        return R.layout.main_fragment_home;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fl_sign) {
            if (canClick) {
                mViewModel.signIn(UserManager.getInstance().getUserId());
            }
        }
    }
}