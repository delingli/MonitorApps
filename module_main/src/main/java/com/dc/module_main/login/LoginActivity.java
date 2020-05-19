package com.dc.module_main.login;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dc.baselib.baseEntiry.User;
import com.dc.baselib.mvvm.AbsLifecycleActivity;
import com.dc.baselib.utils.UserManager;
import com.dc.commonlib.utils.ActivityUtils;
import com.dc.commonlib.utils.ArounterManager;
import com.dc.commonlib.utils.LogUtil;
import com.dc.commonlib.utils.UIUtils;
import com.dc.module_main.MainActivity;
import com.dc.module_main.R;
import com.dc.module_main.bindaccount.BindAccountActivity;
import com.dc.module_main.regist.RegistActivity;
import com.dc.module_main.retrievepassword.RetrievePasswordActivity;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;
@Route(path = ArounterManager.LOGINACTIVITY_URL)
public class LoginActivity extends AbsLifecycleActivity<LoginViewModel> implements View.OnClickListener {

    private TextView tvFogetPassword, tvRegist;
    private Button mLogin;
    private EditText mUsername;
    private EditText mPassword;
    private UMShareAPI mUmShareAPI;
    private String id;

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public int REQUESTCODE_TOBIND = 2;

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerSubscriber(mViewModel.mRepository.EVENT_USER_KEY, User.class).observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                UserManager.getInstance().saveUserInfo(getApplicationContext(), user);
                ActivityUtils.switchTo(LoginActivity.this, MainActivity.class);
                finish();
            }
        });

        registerSubscriber(mViewModel.mRepository.EVENT_USEROTHERLOGIN_SUCESS_KEY, User.class).observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                UserManager.getInstance().saveUserInfo(getApplicationContext(), user);
                ActivityUtils.switchTo(LoginActivity.this, MainActivity.class);
                finish();
            }
        });
        registerSubscriber(mViewModel.mRepository.EVENT_TOBINDOTHER_KEY, String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String ss) {
                //todo  去绑定
                if (isQQ) {
                    if (!TextUtils.isEmpty(id)) {
                        BindAccountActivity.startActivity(LoginActivity.this, BindAccountActivity.BIND_SOURCE_QQ, id);
                    }

                } else {
                    if (!TextUtils.isEmpty(id)) {
                        BindAccountActivity.startActivity(LoginActivity.this, BindAccountActivity.BIND_SOURCE_WECHAR, id);
                    }
                }
                finish();

            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.main_activity_login;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        tvFogetPassword = findViewById(R.id.tv_foget_password);
        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        tvRegist = findViewById(R.id.tv_regist);
        mLogin = findViewById(R.id.logins);
        findViewById(R.id.iv_qq).setOnClickListener(this);
        findViewById(R.id.iv_wechar).setOnClickListener(this);
        tvFogetPassword.setOnClickListener(this);
        tvRegist.setOnClickListener(this);
        mLogin.setOnClickListener(this);
        mUmShareAPI = UMShareAPI.get(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_foget_password) {
            ActivityUtils.switchTo(this, RetrievePasswordActivity.class);
        } else if (id == R.id.tv_regist) {
            ActivityUtils.switchTo(this, RegistActivity.class);
        } else if (id == R.id.logins) {
            if (UIUtils.checkUserPassword(getApplicationContext(), mUsername.getText().toString(), mPassword.getText().toString())) {
                mViewModel.login(mUsername.getText().toString(), mPassword.getText().toString(), mPassword.getText().toString());
            }
        } else if (id == R.id.iv_qq) {
            UMShareConfig config = new UMShareConfig();
            config.isNeedAuthOnGetUserInfo(true);
            mUmShareAPI.setShareConfig(config);
            mUmShareAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, mUMAuthListener);
        } else if (id == R.id.iv_wechar) {
            UMShareConfig config = new UMShareConfig();
            config.isNeedAuthOnGetUserInfo(true);
            mUmShareAPI.setShareConfig(config);
            mUmShareAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.WEIXIN, mUMAuthListener);

        }
    }

    private boolean isQQ = false;

    private UMAuthListener mUMAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            LogUtil.d("ldl","开始");

        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
            id = map.get("uid");
            String name = map.get("name");
            String gender = map.get("gender");
            String iconurl = map.get("iconurl");
            LogUtil.d("ldl",map.toString());
            LogUtil.d("ldl","name"+name+"gender"+gender+"iconurl"+iconurl);
            if (SHARE_MEDIA.QQ.equals(share_media)) {
                isQQ = true;
                mViewModel.userOtherLogin(LoginViewModel.QQ, id);
            } else if (SHARE_MEDIA.WEIXIN.equals(share_media)) {
                isQQ = false;
                mViewModel.userOtherLogin(LoginViewModel.WECHAR, id);

            }

        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            LogUtil.e("ldl",throwable.toString());

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
