package com.dc.module_main.retrievepassword.mobileretrieve;

import android.arch.lifecycle.Observer;
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
import com.dc.module_main.retrievepassword.emailretrieve.EmailRetrieveViewModel;

public class MobileRetrieveFragment extends AbsLifecycleFragment<EmailRetrieveViewModel> implements View.OnClickListener {
    private TextView mTvAreaCode;
    private Button mBtnOver;
    private EditText mEtMobile;
    private EditText mEtPassword;
    private EditText mEtVerification_code;
    private CountDownButton mCountdownVerificationCode;
    private ImageView mIvPasswordHide;

    @Override
    public void dataObserver() {
        registerSubscriber(mViewModel.mRepository.EVENT_RETRIEVEWITHPHONE_KEY, String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String registResponse) {
                ToastUtils.showToast(getResources().getString(R.string.resect_sucess));
                getActivity().finish();
            }
        });
        registerSubscriber(mViewModel.mRepository.EVENT_SENDPHONESMSCODE_KEY, String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String registResponse) {
                ToastUtils.showToast(registResponse);
            }
        });
    }

    public static MobileRetrieveFragment newInstance() {
        MobileRetrieveFragment fragment = new MobileRetrieveFragment();
        return fragment;
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

    @Override
    protected void initData() {

    }

    private TextWatcher watcher = new TextWatcher() {
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

    private void gainAuthCode() {
        KeyBoardUtils.hideSoftInputMode(getContext(), mCountdownVerificationCode);
        final String phone = mEtMobile.getText().toString().trim();
        if (phone.length() == 0) {
            ToastUtils.showToast(getResources().getString(R.string.tip_mobile_phone));
            return;
        }
        if (UIUtils.isMobileNO(phone)) {
            ToastUtils.showToast(getString(R.string.tip_mobile_format));
            return;
        }
        mCountdownVerificationCode.startCountDown(60);
        mViewModel.sendPhone(mEtMobile.getText().toString(), "86");
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        mTvAreaCode = view.findViewById(R.id.tv_area_code);
        mTvAreaCode.setVisibility(View.VISIBLE);
        mBtnOver = view.findViewById(R.id.btn_over);
        mEtMobile = view.findViewById(R.id.et_mobile);
        mEtMobile.setInputType(InputType.TYPE_CLASS_PHONE);
        mEtMobile.setHint(getResources().getString(R.string.input_mobile_phone));
        mEtPassword = view.findViewById(R.id.et_password);
        mEtVerification_code = view.findViewById(R.id.et_verification_code);
        mCountdownVerificationCode = view.findViewById(R.id.countdown_verification_code);
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
        mBtnOver.setOnClickListener(this);
        mEtMobile.addTextChangedListener(watcher);
        mEtPassword.addTextChangedListener(watcher);
        mEtVerification_code.addTextChangedListener(watcher);
        mIvPasswordHide = view.findViewById(R.id.iv_password_hide);
        mIvPasswordHide.setOnClickListener(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.main_tab_mobile_email_back;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_over) {
            mViewModel.retrieveWithPhone("86", mEtMobile.getText().toString(), mEtVerification_code.getText().toString(), mEtPassword.getText().toString());
        } else if (v.getId() == R.id.countdown_verification_code) {
            gainAuthCode();
        } else if (v.getId() == R.id.iv_password_hide) {
            isDisplayPwd();
        }

    }
}
