package com.dc.module_me.mez;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dc.baselib.mvvm.BaseFragment;
import com.dc.baselib.utils.ToastUtils;
import com.dc.commonlib.utils.ArounterManager;
import com.dc.module_me.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

@Route(path = ArounterManager.ME_MEINFO_URL)

public class MeFragment extends BaseFragment implements View.OnClickListener {

    private SmartRefreshLayout mRefreshLayout;
    private TextView tv_title, tv_phone;

    @Override
    public void initView(View view) {
        tv_title = view.findViewById(R.id.tv_title);
        tv_title.setText(R.string.me_title);
        view.findViewById(R.id.iv_left_back).setVisibility(View.GONE);
        view.findViewById(R.id.tv_login_out).setOnClickListener(this);
        tv_phone = view.findViewById(R.id.tv_phone);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayout() {
        return R.layout.me_fragment;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_login_out) {
            ToastUtils.showToast("弹出");
        }

    }
}
