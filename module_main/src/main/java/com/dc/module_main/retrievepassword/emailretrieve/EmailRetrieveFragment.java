package com.dc.module_main.retrievepassword.emailretrieve;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dc.baselib.mvvm.AbsLifecycleFragment;
import com.dc.baselib.utils.ToastUtils;
import com.dc.commonlib.utils.KeyBoardUtils;
import com.dc.commonlib.utils.UIUtils;
import com.dc.commonlib.weiget.CountDownButton;
import com.dc.module_main.R;
import com.dc.module_main.regist.RegistResponse;

public class EmailRetrieveFragment extends AbsLifecycleFragment<EmailRetrieveViewModel> implements View.OnClickListener {

    private TextView mTvAreaCode;
    private Button mBtnOver;
    private EditText mEtMobile;
    private EditText mEtPassword;
    private EditText mEtVerification_code;
    private CountDownButton mCountdownVerificationCode;
    private ImageView mIvPasswordHide;

    @Override
    public void dataObserver() {
        registerSubscriber(mViewModel.mRepository.EVENT_SENDEMAIL_KEY, String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String registResponse) {
                ToastUtils.showToast(registResponse);
            }
        });
        registerSubscriber(mViewModel.mRepository.EVENT_RETRIEVEWITHEMAIL_KEY, String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String registResponse) {
                ToastUtils.showToast(getResources().getString(R.string.resect_sucess));
                getActivity().finish();
            }
        });
    }

    public static EmailRetrieveFragment newInstance() {
        EmailRetrieveFragment fragment = new EmailRetrieveFragment();
        return fragment;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void initView(View view) {
        super.initView(view);
        mTvAreaCode = view.findViewById(R.id.tv_area_code);
        mTvAreaCode.setVisibility(View.GONE);
        mIvPasswordHide = view.findViewById(R.id.iv_password_hide);
        mBtnOver = view.findViewById(R.id.btn_over);
        mEtMobile = view.findViewById(R.id.et_mobile);
        mEtMobile.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        mEtMobile.setHint(getResources().getString(R.string.input_email));
        mEtPassword = view.findViewById(R.id.et_password);
        mEtVerification_code = view.findViewById(R.id.et_verification_code);
        mCountdownVerificationCode = view.findViewById(R.id.countdown_verification_code);
        mBtnOver.setOnClickListener(this);
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (mEtMobile.getText().length() == 0 && mEtPassword.getText().length() == 0
                        && mEtVerification_code.getText().length() == 0) {
                    mBtnOver.setEnabled(false);
                } else {
                    mBtnOver.setEnabled(true);
                }
                if (mEtMobile.getText().length() > 0) {
                    mCountdownVerificationCode.setEnabled(true);
                } else {
                    mCountdownVerificationCode.setEnabled(false);
                }
            }
        };

        mCountdownVerificationCode.setNormalText(getResources().getString(R.string.regist_get_verification_code))
                .setCountDownText("", "s")
                .setCloseKeepCountDown(false) // 关闭页面保持倒计时开关
                .setCountDownClickable(false) //倒计时期间点击事件是否生效开关
                .setShowFormatTime(false)//是否格式化时间
                .setOnCountDownFinishListener(new CountDownButton.OnCountDownFinishListener() {
                    @Override
                    public void onFinish() {
                        mCountdownVerificationCode.setEnabled(mEtMobile.getText().length() > 0);
                    }
                }).setOnClickListener(this);
        mEtMobile.addTextChangedListener(watcher);
        mEtPassword.addTextChangedListener(watcher);
        mEtVerification_code.addTextChangedListener(watcher);

    }

    @Override
    protected int getLayout() {
        return R.layout.main_tab_mobile_email_back;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.countdown_verification_code) {
            gainAuthCode();
        } else if (v.getId() == R.id.btn_over) {
            if (UIUtils.checkResectEmail(getContext(), mEtMobile.getText().toString(), mEtVerification_code.getText().toString(),
                    mEtPassword.getText().toString())) {
                mViewModel.retrieveWithEmail(mEtMobile.getText().toString(), mEtVerification_code.getText().toString(),
                        mEtPassword.getText().toString());

            }
        } else if (v.getId() == R.id.iv_password_hide) {
            isDisplayPwd();
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

    private void gainAuthCode() {
        KeyBoardUtils.hideSoftInputMode(getContext(), mCountdownVerificationCode);
        final String email = mEtMobile.getText().toString();
        if (email.trim().length() == 0) {
            ToastUtils.showToast(getResources().getString(R.string.tip_email));
            return;
        }
        if (!UIUtils.checkEmail(email)) {
            ToastUtils.showToast(getString(R.string.tip_email_format));
            return;
        }
        mCountdownVerificationCode.startCountDown(60);
        mViewModel.sendEmail(mEtMobile.getText().toString());
    }
}
