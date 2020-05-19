package com.dc.module_main.regist;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.dc.baselib.mvvm.AbsLifecycleActivity;
import com.dc.baselib.utils.ToastUtils;
import com.dc.commonlib.common.ConfigUtils;
import com.dc.commonlib.utils.KeyBoardUtils;
import com.dc.commonlib.utils.UIUtils;
import com.dc.commonlib.weiget.CountDownButton;
import com.dc.module_main.R;
import com.dc.commonlib.web.WebViewActivity;

public class RegistActivity extends AbsLifecycleActivity<RegistViewModel> implements View.OnClickListener {

    private EditText mEtemail, et_verification_code, mEtUsername, mEtPassword;
    private Button mBtnRegist;
    private Switch mStSwitch;
    private CountDownButton mCountdownbutton;
    private ImageView mIvPasswordHide;
    private TextView mTvAgreementDesc;

    @Override
    protected int getLayout() {
        return R.layout.main_activity_regist;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mEtemail = findViewById(R.id.et_email);
        et_verification_code = findViewById(R.id.et_verification_code);
        mEtUsername = findViewById(R.id.et_username);
        mEtPassword = findViewById(R.id.et_password);
        mCountdownbutton = findViewById(R.id.btn_verification_code);
        mStSwitch = findViewById(R.id.st_Switch);
        mBtnRegist = findViewById(R.id.btn_regist);
        mTvAgreementDesc = findViewById(R.id.tv_agreement_desc);

        mBtnRegist.setOnClickListener(this);
        mTvAgreementDesc.setOnClickListener(this);
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (mEtemail.getText().length() == 0 && et_verification_code.getText().length() == 0
                        && mEtPassword.getText().length() == 0 && mEtUsername.getText().length() == 0) {
                    mBtnRegist.setEnabled(false);
                } else {
                    mBtnRegist.setEnabled(true);
                }
                if (mEtemail.getText().length() > 0) {
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
                        mCountdownbutton.setEnabled(mEtemail.getText().length() > 0);
                    }
                }).setOnClickListener(this);


        mEtemail.addTextChangedListener(watcher);
        et_verification_code.addTextChangedListener(watcher);
        mEtUsername.addTextChangedListener(watcher);
        mEtPassword.addTextChangedListener(watcher);
        mIvPasswordHide = findViewById(R.id.iv_password_hide);
        mIvPasswordHide.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerSubscriber(mViewModel.mRepository.EVENT_REGIST_KEY, RegistResponse.class).observe(this, new Observer<RegistResponse>() {
            @Override
            public void onChanged(@Nullable RegistResponse registResponse) {
                ToastUtils.showToast(getResources().getString(R.string.regist_sucess));
                finish();
            }
        });
    }

    private void gainAuthCode() {
        KeyBoardUtils.hideSoftInputMode(this, mCountdownbutton);
        final String email = mEtemail.getText().toString();
        if (email.trim().length() == 0) {
            ToastUtils.showToast(getResources().getString(R.string.tip_email));
            return;
        }
        if (!UIUtils.checkEmail(email)) {
            ToastUtils.showToast(getString(R.string.tip_email_format));
            return;
        }
        mCountdownbutton.startCountDown(60);
        mViewModel.toGetVerificationCode(mEtemail.getText().toString());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_regist) {
            boolean formatSure = UIUtils.checkEffective(this.getApplicationContext(), mEtemail.getText().toString(), et_verification_code.getText().toString(), mEtUsername.getText().toString(), mEtPassword.getText().toString()
                    , mStSwitch.isChecked());
            if (formatSure) {
                mViewModel.regist(mEtemail.getText().toString(), et_verification_code.getText().toString(), mEtUsername.getText().toString(), mEtPassword.getText().toString(), mEtPassword.getText().toString());
            }
//            ActivityUtils.switchTo(this, ImproveinfoActivity.class);
        } else if (v.getId() == R.id.btn_verification_code) {
            gainAuthCode();
        } else if (v.getId() == R.id.iv_password_hide) {
            isDisplayPwd();
        } else if (v.getId() == R.id.tv_agreement_desc) {
            WebViewActivity.startActivity(this, ConfigUtils.USER_PROTOCOL + "?temp=" + System.currentTimeMillis());
        }
    }


    private boolean isHide = true;

    private void isDisplayPwd() {
        isHide = !isHide;
        if (isHide) {
            //隐藏明文
            mEtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            mEtPassword.setSelection(mEtPassword.getText().length());
            mIvPasswordHide.setImageResource(R.drawable.password_hide);
        } else {
            //显示明文
            mEtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            mEtPassword.setSelection(mEtPassword.getText().length());
            mIvPasswordHide.setImageResource(R.drawable.password_display);
        }
    }
}
