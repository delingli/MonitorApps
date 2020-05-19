package com.dc.module_main.ui.me;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dc.baselib.mvvm.AbsLifecycleFragment;
import com.dc.baselib.utils.ToastUtils;
import com.dc.baselib.utils.UserManager;
import com.dc.commonlib.common.ConfigUtils;
import com.dc.commonlib.commonentity.UserInfo;
import com.dc.commonlib.utils.ActivityUtils;
import com.dc.commonlib.utils.ArounterManager;
import com.dc.commonlib.utils.GlideUtils;
import com.dc.commonlib.utils.UIUtils;
import com.dc.module_main.R;
import com.dc.module_main.TestActivity;

public class MeFragment extends AbsLifecycleFragment<MeViewModels> implements View.OnClickListener {

    private TextView tv_name, tv_level, tv_signature, tv_fans, tv_focus_on, tv_integral, tv_balance;
    private ImageView iv_head;
    private FrameLayout mFlSign;
    private TextView tv_sign_score;

    @Override
    public void initView(View view) {
        super.initView(view);
        view.findViewById(R.id.iv_setting).setOnClickListener(this);
        view.findViewById(R.id.tv_collection).setOnClickListener(this);
        view.findViewById(R.id.tv_sign).setOnClickListener(this);
        view.findViewById(R.id.ll_fans).setOnClickListener(this);
        view.findViewById(R.id.ll_focus_on).setOnClickListener(this);
        view.findViewById(R.id.ll_integral).setOnClickListener(this);
        view.findViewById(R.id.tv_the_theme).setOnClickListener(this);
        view.findViewById(R.id.tv_reply).setOnClickListener(this);
        view.findViewById(R.id.tv_video).setOnClickListener(this);
        tv_sign_score = view.findViewById(R.id.tv_sign_score);
        view.findViewById(R.id.tv_go_prepaid_phone).setOnClickListener(this);
        mFlSign = view.findViewById(R.id.fl_sign);
        mFlSign.setOnClickListener(this);
        tv_name = view.findViewById(R.id.tv_name);
        iv_head = view.findViewById(R.id.iv_head);
        tv_integral = view.findViewById(R.id.tv_integral);
        tv_level = view.findViewById(R.id.tv_level);
        tv_signature = view.findViewById(R.id.tv_signature);
        tv_fans = view.findViewById(R.id.tv_fans);
        tv_focus_on = view.findViewById(R.id.tv_focus_on);
        tv_balance = view.findViewById(R.id.tv_balance);
        mViewModel.toGetUserInfo(UserManager.getInstance().getUserId());

    }

    @Override
    public void dataObserver() {
        registerSubscriber(mViewModel.mRepository.KEY_USER_EVENT, UserInfo.class).observe(this, new Observer<UserInfo>() {
            @Override
            public void onChanged(@Nullable UserInfo user) {
                if (null != user) {
                    fillData(user);
                }
                mViewModel.getSignStatus(UserManager.getInstance().getUserId());
            }
        });
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
                if (data.equals("0")) {
                    tv_sign_score.setText(ConfigUtils.SCORE);
                    tv_sign_score.setVisibility(View.VISIBLE);
                } else {
                    tv_sign_score.setVisibility(View.GONE);
                }
                canClick = !canClick;

            }
        });
    }

    private void fillData(UserInfo user) {
        GlideUtils.loadCircleUrl(getContext(), user.getAvatar(), iv_head);
        tv_name.setText(user.getNickname());
        tv_signature.setText(user.getSightml());
        tv_level.setText(user.getLevel_name());
        tv_integral.setText(user.getCredits());
        tv_focus_on.setText(user.getFollowing());
        tv_balance.setText(user.getAmount() + "");
        tv_fans.setText(user.getFollower());///todo

    }

    @Override
    protected void initData() {
    }

    private boolean canClick = false;

    @Override
    protected int getLayout() {
        return R.layout.main_me_fragment;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_setting) {
            ARouter.getInstance().build(ArounterManager.ME_SETTINGACTIVITY_URL).navigation();
        } else if (v.getId() == R.id.tv_sign) {
        } else if (v.getId() == R.id.ll_fans) {

        } else if (v.getId() == R.id.ll_focus_on) {
            TestActivity.startActivity(getActivity());

        } else if (v.getId() == R.id.ll_integral) {
        } else if (v.getId() == R.id.tv_the_theme) {

            ARouter.getInstance().build(ArounterManager.ALLLECTURER_URL).navigation();

        } else if (v.getId() == R.id.tv_reply) {
            ARouter.getInstance().build(ArounterManager.PUBLICCLASS_URL).navigation();

        } else if (v.getId() == R.id.tv_video) {
            ARouter.getInstance().build(ArounterManager.MYRECORDACTIVITY_URL).navigation();
        } else if (v.getId() == R.id.tv_collection) {
            ARouter.getInstance().build(ArounterManager.BBSDETAIL_URL).withString(ConfigUtils.F_UID, "8").navigation();

        } else if (v.getId() == R.id.tv_go_prepaid_phone) {

        } else if (v.getId() == R.id.fl_sign) {
            if (canClick) {
                mViewModel.signIn(UserManager.getInstance().getUserId());
            }
        }
    }
}