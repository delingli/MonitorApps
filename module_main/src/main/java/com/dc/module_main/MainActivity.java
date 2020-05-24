package com.dc.module_main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dc.baselib.mvvm.BaseActivity;
import com.dc.baselib.statusBar.StarusBarUtils;
import com.dc.baselib.utils.UserManager;
import com.dc.commonlib.utils.ArounterManager;
import com.dc.commonlib.utils.UIUtils;

import org.yczbj.ycvideoplayerlib.manager.VideoPlayerManager;

@Route(path = ArounterManager.MAIN_MAIN_HOME)
public class MainActivity extends BaseActivity {

    private BottomNavigationView mNavView;
    private NavController mNavController;


    protected void initData() {

    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        if (null != getSupportActionBar()) {
            getSupportActionBar().hide();
        }
        setmToolBarlheadHide(true);
        mNavView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,
                R.id.navigation_bbs,
                R.id.navigation_me)
                .build();
        mNavController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, mNavController, appBarConfiguration);
        NavigationUI.setupWithNavController(mNavView, mNavController);
        StarusBarUtils.setRootViewFitsSystemWindows(this, true);
        StarusBarUtils.setStatusBarColor(this, Color.parseColor("#3476f9"));
        mNavView.setItemIconTintList(null);
//        mNavView.setLabelVisibilityMode(1);
        mNavController.navigate(R.id.navigation_home);
        mNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                if (itemId == R.id.navigation_home) {
                    mNavController.navigate(R.id.navigation_home);
                    StarusBarUtils.setStatusBarColor(MainActivity.this, Color.parseColor("#3476f9"));
                    return true;
                } else if (itemId == R.id.navigation_bbs) {
                    mNavController.navigate(R.id.navigation_bbs);
                    StarusBarUtils.setStatusBarDarkTheme(MainActivity.this, true);
                    return true;
                } else if (itemId == R.id.navigation_me) {
                    mNavController.navigate(R.id.navigation_me);
                    StarusBarUtils.setStatusBarDarkTheme(MainActivity.this, true);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (mNavController != null) {
            mNavController.navigate(R.id.navigation_home);
        }
    }

    @Override
    public void onBackPressed() {
        if (VideoPlayerManager.instance().onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

}
