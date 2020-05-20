package com.dc.module_main;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mNavView;
    private NavController mNavController;


    protected void initData() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView(savedInstanceState);
    }

    protected void initView(Bundle savedInstanceState) {
        if (null != getSupportActionBar()) {
            getSupportActionBar().hide();
        }

        mNavView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,
                R.id.navigation_bbs,
                R.id.navigation_me)
                .build();
        mNavController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, mNavController, appBarConfiguration);
        NavigationUI.setupWithNavController(mNavView, mNavController);
        StarusBarUtils.setRootViewFitsSystemWindows(this, true);
        StarusBarUtils.setStatusBarDarkTheme(this, true);
        mNavView.setItemIconTintList(null);
//        mNavView.setLabelVisibilityMode(1);
        mNavController.navigate(R.id.navigation_home);
        mNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();

                if (itemId == R.id.navigation_home) {
                    mNavController.navigate(R.id.navigation_home);
                    return true;
                } else if (itemId == R.id.navigation_bbs) {
                    mNavController.navigate(R.id.navigation_bbs);
                    return true;
                } else if (itemId == R.id.navigation_me) {
                    mNavController.navigate(R.id.navigation_me);

          /*          if (!UserManager.getInstance().isLogin()) {
                        LoginActivity.startActivity(MainActivity.this);
                        return false;
                    } else {
                        mNavController.navigate(R.id.navigation_me);
                    }*/
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
