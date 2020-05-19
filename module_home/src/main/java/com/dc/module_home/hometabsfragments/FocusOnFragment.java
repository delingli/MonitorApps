package com.dc.module_home.hometabsfragments;

import android.view.View;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dc.baselib.mvvm.BaseFragment;
import com.dc.commonlib.utils.ArounterManager;

//关注
@Route(path = ArounterManager.HOME_FOCUSONFRAGMENT_URL)
    public class FocusOnFragment extends BaseFragment {

    @Override
    public void initView(View view) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayout() {
        return 0;
    }
}
