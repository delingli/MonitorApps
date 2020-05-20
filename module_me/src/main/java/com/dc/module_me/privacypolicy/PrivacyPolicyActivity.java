package com.dc.module_me.privacypolicy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dc.baselib.mvvm.BaseActivity;
import com.dc.commonlib.common.ConfigUtils;
import com.dc.commonlib.web.WebViewActivity;
import com.dc.module_me.R;

public class PrivacyPolicyActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected int getLayout() {
        return R.layout.me_privacy_policy;
    }

    @Override
    protected void initData() {

    }

    public static void startActivity(Activity activity) {
        Intent intent = new Intent();
        intent.setClass(activity, PrivacyPolicyActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle(R.string.privacy_and_policy);
        findViewById(R.id.tv_service_agreement).setOnClickListener(this);
        findViewById(R.id.tv_privacy_policy).setOnClickListener(this);
        findViewById(R.id.tv_membership_service).setOnClickListener(this);
        findViewById(R.id.tv_cancellation_agreement).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_service_agreement) {
            WebViewActivity.startActivity(PrivacyPolicyActivity.this, ConfigUtils.SERVICEAGREEMENT_URL,getResources().getString(R.string.service_agreement));

        } else if (id == R.id.tv_privacy_policy) {
            WebViewActivity.startActivity(PrivacyPolicyActivity.this, ConfigUtils.privacyPolicy_URL,getResources().getString(R.string.privacy_policy));
        } else if (id == R.id.tv_membership_service) {
            WebViewActivity.startActivity(PrivacyPolicyActivity.this, ConfigUtils.vipProtocol_URL,getResources().getString(R.string.membership_service));

        } else if (id == R.id.tv_cancellation_agreement) {
            WebViewActivity.startActivity(PrivacyPolicyActivity.this, ConfigUtils.ACCOUNTLOGOUT_URL,getResources().getString(R.string.cancellation_agreement));

        }
    }
}
