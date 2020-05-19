package com.dc.module_me.personinfo;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dc.baselib.mvvm.AbsLifecycleActivity;
import com.dc.baselib.utils.UserManager;
import com.dc.commonlib.commonentity.UserInfo;
import com.dc.commonlib.utils.GlideUtils;
import com.dc.module_me.R;
import com.dc.module_me.bindemail.BindEmailActivity;
import com.dc.module_me.bindmobile.BindMobileActivity;
import com.dc.module_me.changeinfo.ChangeInfoActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class PersonInfoActivity extends AbsLifecycleActivity<PersonInfoViewModel> implements View.OnClickListener {

    private ImageView mIv_head;
    private TextView mTv_username, tv_nickname, tv_signature, tv_email, tv_phone, tv_uid;

    @Override
    protected int getLayout() {
        return R.layout.me_person_info;
    }

    public static void startActivity(Activity activity) {
        Intent intent = new Intent();
        intent.setClass(activity, PersonInfoActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setTitle(R.string.personinfo);
        mIv_head = findViewById(R.id.iv_head);
        mTv_username = findViewById(R.id.tv_username);
        tv_nickname = findViewById(R.id.tv_nickname);
        tv_signature = findViewById(R.id.tv_signature);
        tv_email = findViewById(R.id.tv_email);
        tv_phone = findViewById(R.id.tv_phone);
        tv_uid = findViewById(R.id.tv_uid);
        findViewById(R.id.ll_username).setOnClickListener(this);
        findViewById(R.id.ll_nickname).setOnClickListener(this);
        findViewById(R.id.ll_signature).setOnClickListener(this);
        findViewById(R.id.ll_email).setOnClickListener(this);
        findViewById(R.id.ll_phone).setOnClickListener(this);
        findViewById(R.id.ll_uid).setOnClickListener(this);
        mViewModel.getUserInfo(UserManager.getInstance().getUserId());
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerSubscriber(mViewModel.mRepository.KEY_USER_EVENT, UserInfo.class).observe(this, new Observer<UserInfo>() {
            @Override
            public void onChanged(@Nullable UserInfo user) {
                if (null != user) {
                    fillData(user);
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshInfo(FlushPersonInfo flushPersonInfo) {
        if (flushPersonInfo.flush) {
            mViewModel.getUserInfo(UserManager.getInstance().getUserId());
        }
    }

    private void fillData(UserInfo user) {
        GlideUtils.loadCircleUrl(this, user.getAvatar(), mIv_head);
        mTv_username.setText(user.getUsername());
        tv_nickname.setText(user.getNickname());
        tv_signature.setText(user.getSightml());
        tv_email.setText(user.getEmail());
        tv_phone.setText(user.getPhone());
        tv_uid.setText(user.getUid());
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ll_username) {
        } else if (id == R.id.ll_nickname) {
            ChangeInfoActivity.startActivity(this,ChangeInfoActivity.CHANGE_NICKNAME_FLAG);
        } else if (id == R.id.ll_signature) {
            ChangeInfoActivity.startActivity(this,ChangeInfoActivity.CHANGE_SIGNATURE_FLAG);

        } else if (id == R.id.ll_email) {
            BindEmailActivity.startActivity(this);
        } else if (id == R.id.ll_phone) {
            BindMobileActivity.startActivity(this);
        } else if (id == R.id.ll_uid) {
        }
    }
}
