package com.dc.module_me.setting;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dc.baselib.mvvm.BaseActivity;
import com.dc.baselib.utils.UserManager;
import com.dc.commonlib.common.ConfigUtils;
import com.dc.commonlib.utils.ActivityUtils;
import com.dc.commonlib.utils.ArounterManager;
import com.dc.commonlib.utils.VersionUtils;
import com.dc.commonlib.web.WebViewActivity;
import com.dc.module_me.R;
import com.dc.module_me.personinfo.PersonInfoActivity;
import com.dc.module_me.privacypolicy.PrivacyPolicyActivity;

@Route(path = ArounterManager.ME_SETTINGACTIVITY_URL)
public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvCersionInformation;

    @Override
    protected int getLayout() {
        return R.layout.me_setting_center;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle(getResources().getString(R.string.setting));
        findViewById(R.id.tv_editor_info).setOnClickListener(this);
        findViewById(R.id.tv_attached_download_address).setOnClickListener(this);
        findViewById(R.id.tv_my_attachment).setOnClickListener(this);
        findViewById(R.id.tv_feedback).setOnClickListener(this);
        findViewById(R.id.tv_about_us).setOnClickListener(this);
        findViewById(R.id.tv_privacy_and_policy).setOnClickListener(this);
        mTvCersionInformation = findViewById(R.id.tv_version_information);
        mTvCersionInformation.setOnClickListener(this);
        findViewById(R.id.tv_exit).setOnClickListener(this);
        mTvCersionInformation.setText("v" + VersionUtils.getCurrentVersionName(this));


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_editor_info) {
            PersonInfoActivity.startActivity(this);
        } else if (id == R.id.tv_attached_download_address) {
        } else if (id == R.id.tv_my_attachment) {
        } else if (id == R.id.tv_feedback) {
            WebViewActivity.startActivity(SettingActivity.this, ConfigUtils.FEEDBACK_URL);
        } else if (id == R.id.tv_about_us) {
            WebViewActivity.startActivity(SettingActivity.this, ConfigUtils.ABOUTUS_URL);
        } else if (id == R.id.tv_privacy_and_policy) {
            PrivacyPolicyActivity.startActivity(this);
        } else if (id == R.id.tv_version_information) {

        } else if (id == R.id.tv_exit) {
            showExit();
        }
    }

    private void showExit() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this).setIcon(R.mipmap.ic_launcher).setTitle("提示")
                .setMessage("确定退出登录?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //ToDo: 你想做的事情
                        UserManager.getInstance().clearUser(SettingActivity.this);
                        dialogInterface.dismiss();
                        ARouter.getInstance().build(ArounterManager.MAIN_MAINACTIVITY_URL).navigation();
                        finish();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //ToDo: 你想做的事情
                        dialogInterface.dismiss();
                    }
                });
        builder.create().show();
    }
}
