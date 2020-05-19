package com.dc.module_main.retrievepassword;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dc.baselib.mvvm.AbsLifecycleActivity;
import com.dc.baselib.mvvm.BaseActivity;
import com.dc.commonlib.utils.UIUtils;
import com.dc.module_main.R;
import com.dc.module_main.ui.home.FtViewPagerAdapter;

public class RetrievePasswordActivity extends BaseActivity implements TabLayout.BaseOnTabSelectedListener {

    private TabLayout mTabLayout;
    private ViewPager mViewpager;

    @Override
    protected int getLayout() {
        return R.layout.main_activity_retrieve_password;
    }


    @Override
    protected void initData() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTabLayout = findViewById(R.id.tablayout);
        mViewpager = findViewById(R.id.viewpager);
        String[] titles = UIUtils.getStringArray(this, R.array.retrievepassword);
        FtViewPagerAdapter ViewPagerAdapter = new FtViewPagerAdapter(getSupportFragmentManager(), FtViewPagerAdapter.TYPE_RETRIEVEPASSWORD, titles);
        mViewpager.setAdapter(ViewPagerAdapter);
        mTabLayout.addOnTabSelectedListener(this);
        mTabLayout.setupWithViewPager(mViewpager);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mViewpager.setCurrentItem(tab.getPosition());
        tab.setCustomView(getTextView(tab));
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        tab.setCustomView(null);

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private View getTextView(TabLayout.Tab tab) {
//        TextView textView = new TextView(getActivity());
//        float selectedSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics());
//        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, selectedSize);
//        textView.setGravity(Gravity.CENTER);
//        textView.setTextColor(getResources().getColor(R.color.text_color9_000000));
//        textView.setText(tab.getText());
//        textView.setPadding(0, 0, 0, 0);
//        textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        View view = LayoutInflater.from(this).inflate(R.layout.main_tab_text,null,false);
        TextView tv_tab = view.findViewById(R.id.tv_tab);
        tv_tab.setText(tab.getText());
        return view;
    }
}
