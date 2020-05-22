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

public class HomeFragment extends BaseFragment {




    private boolean canClick = false;


    @Override
    public void initView(View view) {

    }

    @Override
    protected void initData() {


    }

    @Override
    protected int getLayout() {
        return R.layout.common_fragment_layout;
    }
}