package com.dc.module_bbs.searchlist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.dc.baselib.mvvm.BaseActivity;
import com.dc.commonlib.utils.KeyBoardUtils;
import com.dc.commonlib.weiget.customsearch.DlSearchView;
import com.dc.module_bbs.R;
import com.dc.module_bbs.projectlist.ProjectListFragment;

//搜索项目列表
public class SearchActivity extends BaseActivity {

    private DlSearchView dlSearchView;
    private ProjectListFragment mProjectListFragment;
    private boolean mForSearch;
    @Override
    protected int getLayout() {
        return R.layout.proj_search_fragment;
    }

    @Override
    protected void initData() {

    }

    public static void startActivity(Context context, String regain, boolean forSearch) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra(ProjectListFragment.RAGIO_KEY, regain);
        intent.putExtra(KEY_FORSEARCH, forSearch);
        context.startActivity(intent);
    }

    public static String KEY_FORSEARCH = "forsearch";

    public static void startActivity(Context context, boolean forSearch) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra(KEY_FORSEARCH, forSearch);
        context.startActivity(intent);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle(R.string.project);
        mProjectListFragment = new ProjectListFragment();
        String ragin = null;
        if (getIntent() != null) {
            ragin = getIntent().getStringExtra(ProjectListFragment.RAGIO_KEY);
            mForSearch = getIntent().getBooleanExtra(KEY_FORSEARCH, false);

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
        dlSearchView.setQueryHint(getResources().getString(R.string.please_input_project));
        final InputMethodManager imm = (InputMethodManager) this
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        dlSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (!TextUtils.isEmpty(s)) {
                    search(s);
                }
                dlSearchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

    }

    private void showInputMethod(View view) {
        KeyBoardUtils.showSoftInputMode(this, view);
    }

    private void search(String s) {
        if (null != mProjectListFragment) {
            mProjectListFragment.toSearch(s);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KeyBoardUtils.closeKeybord(dlSearchView.getmEdit(), this);

    }
}
