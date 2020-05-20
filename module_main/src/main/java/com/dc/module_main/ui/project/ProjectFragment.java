package com.dc.module_main.ui.project;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dc.baselib.mvvm.BaseFragment;
import com.dc.commonlib.utils.ArounterManager;
import com.dc.commonlib.utils.UIUtils;
import com.dc.module_main.R;
import com.dc.module_main.ui.home.FtViewPagerAdapter;

public class ProjectFragment extends BaseFragment {
    @Override
    public void initView(View view) {
        getChildFragmentManager().beginTransaction().replace(R.id.fl_continerz, (BaseFragment) ARouter.getInstance().build(ArounterManager.PROJ_PROJECTFRAGMENT_URL).navigation(),"ProjFragmentsTag").commit();

    }
    @Override
    protected void initData() {
    }
    protected int getLayout() {
        return R.layout.common_fragment_layout;
    }
}