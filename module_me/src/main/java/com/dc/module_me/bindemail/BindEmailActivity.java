package com.dc.module_me.bindemail;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dc.baselib.mvvm.AbsLifecycleActivity;
import com.dc.baselib.utils.ToastUtils;
import com.dc.baselib.utils.UserManager;
import com.dc.commonlib.utils.KeyBoardUtils;
import com.dc.commonlib.utils.UIUtils;
import com.dc.commonlib.weiget.CountDownButton;
import com.dc.module_me.R;
import com.dc.module_me.bindmobile.BindMobileActivity;
import com.dc.module_me.personinfo.FlushPersonInfo;

import org.greenrobot.eventbus.EventBus;

public class BindEmailActivity extends AbsLifecycleActivity<BindEmailViewModel> implements View.OnClickListener {

    private EditText mEtEmail, et_verification_code;
    private CountDownButton mCountDownButton;
    private Button mBtnSure;

    @Override
    protected int getLayout() {
        return R.layout.me_bind_email;
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, BindEmailActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setTitle(R.string.bind_email);
        mEtEmail = findViewById(R.id.et_email);
        mEtEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        mCountDownButton = findViewById(R.id.btn_verification_code);
        et_verification_code = findViewById(R.id.et_verification_code);
        mBtnSure = findViewById(R.id.btn_sure);
        mBtnSure.setOnClickListener(this);

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (mEtEmail.getText().length() == 0 && et_verification_code.getText().length() == 0
                ) {
                    mBtnSure.setEnabled(false);
                } else {
                    mBtnSure.setEnabled(true);
                }
                if (mEtEmail.getText().length() > 0) {
                    mCountDownButton.setEnabled(true);
                } else {
                    mCountDownButton.setEnabled(false);
                }
            }
        };
        mCountDownButton.setNormalText(getResources().getString(R.string.regist_get_verification_code))
                .setCountDownText("", "s")
                .setCloseKeepCountDown(false) // 关闭页面保持倒计时开关
                .setCountDownClickable(false) //倒计时期间点击事件是否生效开关
                .setShowFormatTime(false)//是否格式化时间
                .setOnCountDownFinishListener(new CountDownButton.OnCountDownFinishListener() {
                    @Override
                    public void onFinish() {
                        mCountDownButton.setEnabled(mEtEmail.getText().length() > 0);
                    }
                }).setOnClickListener(this);
        mEtEmail.addTextChangedListener(watcher);
        et_verification_code.addTextChangedListener(watcher);
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerSubscriber(mViewModel.mRepository.BIND_EMAIL_EVENT, String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String registResponse) {
                ToastUtils.showToast(getResources().getString(R.string.bind_sucess));
                EventBus.getDefault().post(new FlushPersonInfo(true));
                finish();
            }
        });
        registerSubscriber(mViewModel.mRepository.GET_EMAIL_SMS_EVENT, String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String registResponse) {
                ToastUtils.showToast(getResources().getString(R.string.tip_verification_sucess));
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_sure) {
            bindEmail();
        } else if (id == R.id.btn_verification_code) {
            gainAuthCode();
        }


    }

    private void bindEmail() {
        String email = mEtEmail.getText().toString();
        String code = et_verification_code.getText().toString();
        String userId = UserManager.getInstance().getUserId();
        if (!UIUtils.checkEmail(email)) {
            ToastUtils.showToast(getResources().getString(R.string.tip_email_format));
            return;
        }
        if (TextUtils.isEmpty(code)) {
            ToastUtils.showToast(getResources().getString(R.string.tip_ver_code));
            return;
        }
        mViewModel.bindEmail(userId, email, code);
    }

    private void gainAuthCode() {
        KeyBoardUtils.hideSoftInputMode(this, mCountDownButton);
        final String email = mEtEmail.getText().toString();
        if (!UIUtils.checkEmail(email)) {
            ToastUtils.showToast(getResources().getString(R.string.tip_email_format));
        }
        mCountDownButton.startCountDown(60);
        mViewModel.sendEmailCode(email);
    }
}
