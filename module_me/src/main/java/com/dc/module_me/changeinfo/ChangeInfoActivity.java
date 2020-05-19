package com.dc.module_me.changeinfo;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dc.baselib.mvvm.AbsLifecycleActivity;
import com.dc.baselib.utils.ToastUtils;
import com.dc.baselib.utils.UserManager;
import com.dc.module_me.R;

public class ChangeInfoActivity extends AbsLifecycleActivity<ChangeInfoViewModel> implements View.OnClickListener {

    private EditText mEtValue;
    private Button mBtnSure;

    @Override
    protected int getLayout() {
        return R.layout.me_change_info;
    }

    private static String FLAG_KEY = "flagkey";
    public static int CHANGE_NICKNAME_FLAG = 0;
    public static int CHANGE_SIGNATURE_FLAG = 1;
    private int currentflag = CHANGE_NICKNAME_FLAG;

    public static void startActivity(Context context, int flag) {
        Intent intent = new Intent();
        intent.putExtra(FLAG_KEY, flag);
        intent.setClass(context, ChangeInfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mEtValue = findViewById(R.id.et_value);
        mBtnSure = findViewById(R.id.btn_sure);
        mBtnSure.setOnClickListener(this);
        if (getIntent() != null) {
            currentflag = getIntent().getIntExtra(FLAG_KEY, CHANGE_NICKNAME_FLAG);
        }
        if (currentflag == CHANGE_NICKNAME_FLAG) {
            setTitle(R.string.change_nickname);
            mEtValue.setHint(R.string.login_hint_nicknamme);
        } else {
            setTitle(R.string.change_signature);
            mEtValue.setHint(R.string.hint_signature);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_sure) {
            if (currentflag == CHANGE_NICKNAME_FLAG) {
                if (TextUtils.isEmpty(mEtValue.getText().toString())) {
                    ToastUtils.showToast(getResources().getString(R.string.tip_nickname_not_null));
                    return;
                }
                mViewModel.updateNickname(UserManager.getInstance().getUserId(), mEtValue.getText().toString());
            } else {
                if (TextUtils.isEmpty(mEtValue.getText().toString())) {
                    ToastUtils.showToast(getResources().getString(R.string.tip_sign_not_null));
                    return;
                }
                mViewModel.updateSignature(UserManager.getInstance().getUserId(), mEtValue.getText().toString());
            }
        }
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerSubscriber(mViewModel.mRepository.UPDATENICKNAME, String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String registResponse) {
                ToastUtils.showToast(getResources().getString(R.string.change_sucess));
                finish();
            }
        });
        registerSubscriber(mViewModel.mRepository.UPDATESIGNATURE, String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String registResponse) {
                ToastUtils.showToast(getResources().getString(R.string.change_sucess));
                finish();
            }
        });
    }
}
