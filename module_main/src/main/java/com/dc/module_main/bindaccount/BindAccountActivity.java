package com.dc.module_main.bindaccount;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dc.baselib.baseEntiry.User;
import com.dc.baselib.mvvm.AbsLifecycleActivity;
import com.dc.baselib.mvvm.AbsLifecycleFragment;
import com.dc.baselib.utils.ToastUtils;
import com.dc.baselib.utils.UserManager;
import com.dc.commonlib.utils.ActivityUtils;
import com.dc.module_main.MainActivity;
import com.dc.module_main.R;
import com.dc.module_main.login.LoginActivity;
import com.dc.module_main.regist.RegistActivity;

public class BindAccountActivity extends AbsLifecycleActivity<BindAccountViewModel> implements View.OnClickListener {

    private EditText et_account, et_password;
    private Button btn_regist_new_account;
    private String sourceFrom;

    @Override
    protected void dataObserver() {
        super.dataObserver();
        //微信绑定成功
        registerSubscriber(mViewModel.mRepository.EVENT_BIND_WECHAR, User.class).observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                UserManager.getInstance().saveUserInfo(getApplicationContext(), user);
                ActivityUtils.switchTo(BindAccountActivity.this, MainActivity.class);
                finish();
            }
        });
    }

    @Override
    protected void initData() {

    }

    private static String BIND_SOURCE_KEY = "bind_source_key";
    public static String BIND_SOURCE_QQ = "bind_source_qq";
    public static String BIND_SOURCE_WECHAR = "bind_source_wechar";
    public static String QQ_OR_WECHARID_KEY = "ids";
    public static String QQ_OR_WECHARID;

    public static void startActivity(Activity activity, String bindSource, String id) {
        Intent intent = new Intent();
        intent.setClass(activity, BindAccountActivity.class);
        intent.putExtra(BIND_SOURCE_KEY, bindSource);
        intent.putExtra(QQ_OR_WECHARID_KEY, id);
        activity.startActivity(intent);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setTitle(R.string.bind_account);
        et_account = findViewById(R.id.et_account);
        btn_regist_new_account = findViewById(R.id.btn_regist_new_account);
        et_password = findViewById(R.id.password);
        findViewById(R.id.btn_sure).setOnClickListener(this);
        findViewById(R.id.btn_regist_new_account).setOnClickListener(this);
        if (getIntent() != null) {
            sourceFrom = getIntent().getStringExtra(BIND_SOURCE_KEY);
            QQ_OR_WECHARID = getIntent().getStringExtra(QQ_OR_WECHARID_KEY);
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.main_bind_account;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_sure) {
            submite();
        } else if (v.getId() == R.id.btn_regist_new_account) {
            ActivityUtils.switchTo(this, RegistActivity.class);
        }
    }

    private void submite() {
        String account = et_account.getText().toString();
        String password = et_password.getText().toString();
        if (TextUtils.isEmpty(account)) {
            ToastUtils.showToast(getResources().getString(R.string.tip_account));
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtils.showToast(getResources().getString(R.string.tip_password));
            return;
        }
        if (TextUtils.equals(sourceFrom, BIND_SOURCE_QQ)) {
            mViewModel.bindAccountForQQLogin(account, password, password, QQ_OR_WECHARID);

        } else if (TextUtils.equals(sourceFrom, BIND_SOURCE_WECHAR)) {
            mViewModel.bindAccountForWeCharLogin(account, password, password, QQ_OR_WECHARID);

        }
    }
}
