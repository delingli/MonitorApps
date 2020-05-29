package com.dc.module_bbs.searchlist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.View;

import com.dc.baselib.mvvm.BaseActivity;
import com.dc.commonlib.weiget.customsearch.DlSearchView;
import com.dc.module_bbs.R;
import com.dc.module_bbs.projectlist.ProjectListFragment;

//搜索项目列表
public class SearchActivity extends BaseActivity {

    private DlSearchView dlSearchView;
    private ProjectListFragment mProjectListFragment;

    @Override
    protected int getLayout() {
        return R.layout.proj_search_fragment;
    }

    @Override
    protected void initData() {

    }
    public static void startActivity(Context context,String regain) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra(ProjectListFragment.RAGIO_KEY,regain);
        context.startActivity(intent);
    }
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle(R.string.project);
        mProjectListFragment = new ProjectListFragment();
        String ragin = null;
        if (getIntent() != null) {
            ragin = getIntent().getStringExtra(ProjectListFragment.RAGIO_KEY);
        }
        Bundle bundle = new Bundle();
        dlSearchView = findViewById(R.id.dlSearchView);
        if (!TextUtils.isEmpty(ragin)) {
            bundle.putString(ProjectListFragment.RAGIO_KEY, ragin);
        }
        bundle.putString(ProjectListFragment.TYPE_KEY, ProjectListFragment.SEARCH_TYPE);
        mProjectListFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_continers, mProjectListFragment, "").commit();
        initSearch();
    }

    private void initSearch() {
        dlSearchView.setQueryHint("请输入您要查询的内容");
        dlSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (!TextUtils.isEmpty(s)) {
                    search(s);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    private void search(String s) {
        if (null != mProjectListFragment) {
            mProjectListFragment.toSearch(s);
        }
    }
}
