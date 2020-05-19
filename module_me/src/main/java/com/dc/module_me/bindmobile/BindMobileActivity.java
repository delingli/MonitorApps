package com.dc.module_me.bindmobile;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dc.baselib.mvvm.AbsLifecycleActivity;
import com.dc.baselib.utils.ToastUtils;
import com.dc.baselib.utils.UserManager;
import com.dc.commonlib.utils.KeyBoardUtils;
import com.dc.commonlib.utils.UIUtils;
import com.dc.commonlib.weiget.CountDownButton;
import com.dc.module_me.R;
import com.dc.module_me.changeinfo.ChangeInfoActivity;
import com.dc.module_me.personinfo.FlushPersonInfo;

import org.greenrobot.eventbus.EventBus;

public class BindMobileActivity extends AbsLifecycleActivity<BindMobileViewModel> implements View.OnClickListener {

    private EditText mEt_mobile, mEtVerificationCode;
    private TextView mTvAreaCode;
    private CountDownButton mCountdownbutton;
    private Button mBtnSure;

    @Override
    protected int getLayout() {
        return R.layout.me_bind_phone;
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, BindMobileActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerSubscriber(mViewModel.mRepository.BIND_PHONE_EVENT, String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String registResponse) {
                ToastUtils.showToast(getResources().getString(R.string.bind_sucess));
                EventBus.getDefault().post(new FlushPersonInfo(true));
                finish();
            }
        });
        registerSubscriber(mViewModel.mRepository.VERIFICATION_CODE_EVENT, String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String registResponse) {
                ToastUtils.showToast(getResources().getString(R.string.tip_verification_sucess));
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setTitle(R.string.bind_phone);
        mEt_mobile = findViewById(R.id.et_mobile);
        mEt_mobile.setInputType(InputType.TYPE_CLASS_PHONE);
        mCountdownbutton = findViewById(R.id.btn_verification_code);
        mTvAreaCode = findViewById(R.id.tv_area_code);
        mEtVerificationCode = findViewById(R.id.et_verification_code);
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (mEt_mobile.getText().length() == 0 && mEtVerificationCode.getText().length() == 0
                ) {
                    mBtnSure.setEnabled(false);
                } else {
                    mBtnSure.setEnabled(true);
                }
                if (mEt_mobile.getText().length() > 0) {
                    mCountdownbutton.setEnabled(true);
                } else {
                    mCountdownbutton.setEnabled(false);
                }
            }
        };
        mCountdownbutton.setNormalText(getResources().getString(R.string.regist_get_verification_code))
                .setCountDownText("", "s")
                .setCloseKeepCountDown(false) // 关闭页面保持倒计时开关
                .setCountDownClickable(false) //倒计时期间点击事件是否生效开关
                .setShowFormatTime(false)//是否格式化时间
                .setOnCountDownFinishListener(new CountDownButton.OnCountDownFinishListener() {
                    @Override
                    public void onFinish() {
                        mCountdownbutton.setEnabled(mEt_mobile.getText().length() > 0);
                    }
                }).setOnClickListener(this);
        mEt_mobile.addTextChangedListener(watcher);
        mEtVerificationCode.addTextChangedListener(watcher);

        mBtnSure = findViewById(R.id.btn_sure);
        mBtnSure.setOnClickListener(this);
    }

    private void gainAuthCode() {
        KeyBoardUtils.hideSoftInputMode(this, mCountdownbutton);
        final String phone = mEt_mobile.getText().toString();
        if (phone.trim().length() == 0) {
            ToastUtils.showToast(getResources().getString(R.string.tip_mobile_format));
            return;
        }
        if (!UIUtils.checkPhone(phone)) {
            ToastUtils.showToast(getString(R.string.tip_mobile_format));
            return;
        }
        mCountdownbutton.startCountDown(60);
        mViewModel.sendSms(phone, mTvAreaCode.getText().toString());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_verification_code) {
            gainAuthCode();
        } else if (id == R.id.btn_sure) {
            mViewModel.bindPhone(UserManager.getInstance().getUserId(),
                    mTvAreaCode.getText().toString(),
                    mEt_mobile.getText().toString(),
                    mEtVerificationCode.getText().toString());
        }
    }
}
