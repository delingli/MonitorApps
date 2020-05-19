package com.dc.module_home.hometabsfragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dc.baselib.mvvm.BaseFragment;
import com.dc.commonlib.utils.ArounterManager;

//器件工程
@Route(path = ArounterManager.HOME_DEVICEENGINEERINGFRAGMENT_URL)
public class DeviceEngineeringFragment extends BaseFragment {



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView tv = new TextView(getContext());
        tv.setText("器件工程");
        tv.setTextColor(Color.parseColor("#FFE50120"));
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(24f);
        return tv;
    }

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
