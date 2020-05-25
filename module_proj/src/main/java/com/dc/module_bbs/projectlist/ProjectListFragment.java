package com.dc.module_bbs.projectlist;

import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dc.baselib.http.TokenUtil;
import com.dc.baselib.mvvm.AbsLifecycleFragment;
import com.dc.baselib.utils.ToastUtils;
import com.dc.commonlib.utils.ArounterManager;
import com.dc.commonlib.weiget.horizontalrecycle.DLHorizontalItem;
import com.dc.module_bbs.R;
import com.dc.module_bbs.searchlist.SearchActivity;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;


@Route(path = ArounterManager.PROJ_PROJECTFRAGMENT_URL)
public class ProjectListFragment extends AbsLifecycleFragment<ProjectListViewModel> implements View.OnClickListener, OnRefreshListener, OnLoadMoreListener {

    private RecyclerView mRecyclerView;
    private ProjectItemAdapter mProjectItemAdapter;
    private TextView tv_title;
    private ImageView iv_state_arrow, iv_address_arrow;
    private PopupWindowView mStatePopupWindowView, mPopupWindowView;
    private String getProject__status;
    private String region;
    private RefreshLayout refreshLayout;

    private TextView tv_state;
    private TextView tv_area;
    private ImageView iv_state_arrow1;
    private ImageView iv_address_arrow1;
    public static String LIST_TYPE = "key_project_list";
    public static String SEARCH_TYPE = "key_project_search";
    public static String TYPE_KEY = "type_key_project";
    private String currentType = LIST_TYPE;
    private Toolbar rl_head;
    private LinearLayout ll_to_search;

    @Override
    public void dataObserver() {
        registerSubscriber(mViewModel.mRepository.EVENT_PROJECTLIST_SUCESS, List.class).observe(this, new Observer<List>() {
            @Override
            public void onChanged(@Nullable List sstr) {
                if (refreshLayout.getState() == RefreshState.Loading) {
                    mProjectItemAdapter.addList(sstr);
                } else {
                    mProjectItemAdapter.setList(sstr);
                }
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
            }
        });
        registerSubscriber(mViewModel.mRepository.EVENT_FINISH_REFRESH_LOAD, String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String sstr) {
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
//                if(refreshLayout.getState()==refreshLayout.get)
//                mProjectItemAdapter.addList(sstr);
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void initView(View view) {
        super.initView(view);
        if (null != getArguments() && !TextUtils.isEmpty(getArguments().getString(TYPE_KEY))) {
            currentType = getArguments().getString(TYPE_KEY);
        }
        rl_head = view.findViewById(R.id.rl_head);
        ll_to_search = view.findViewById(R.id.ll_to_search);
        if (currentType.equals(SEARCH_TYPE)) {
            rl_head.setVisibility(View.GONE);
            ll_to_search.setVisibility(View.GONE);
        } else {
            rl_head.setVisibility(View.VISIBLE);
            ll_to_search.setVisibility(View.VISIBLE);
        }
        tv_state = view.findViewById(R.id.tv_state);
        tv_area = view.findViewById(R.id.tv_area);
        iv_state_arrow1 = view.findViewById(R.id.iv_state_arrow);
        iv_address_arrow1 = view.findViewById(R.id.iv_address_arrow);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        mPopupWindowView = new PopupWindowView(getContext(), 0);
        mStatePopupWindowView = new PopupWindowView(getContext(), 0);
        createPopupWindowData();
        tv_title = view.findViewById(R.id.tv_title);
        iv_state_arrow1 = view.findViewById(R.id.iv_state_arrow);
        iv_address_arrow1 = view.findViewById(R.id.iv_address_arrow);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        tv_title.setText(R.string.project);
        view.findViewById(R.id.iv_left_back).setVisibility(View.GONE);
        view.findViewById(R.id.ll_address).setOnClickListener(this);
        view.findViewById(R.id.ll_state).setOnClickListener(this);
        view.findViewById(R.id.ll_to_search).setOnClickListener(this);
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadMore(true);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mProjectItemAdapter = new ProjectItemAdapter(getContext(), null, -1));
        mPopupWindowView.addOnItemClickListener(new PopupWindowView.OnItemClickListener() {
            @Override
            public void onItemCLick(String name, String id) {
                tv_area.setText(name);
                region = name;
                if (TextUtils.equals(region, "全部")) {
                    region = null;
                }
                refresh = true;
                toGetProjectList();

            }
        });
        mStatePopupWindowView.addOnItemClickListener(new PopupWindowView.OnItemClickListener() {
            @Override
            public void onItemCLick(String name, String id) {
                tv_state.setText(name);
                getProject__status = id;
                if (TextUtils.equals(getProject__status, "000")) {
                    getProject__status = null;
                }
                refresh = true;
                toGetProjectList();
            }
        });
//        initDefaultArea();
        toGetProjectList();


    }

    private void initDefaultArea() {
        DLHorizontalItem defaultArea = mViewModel.getDefaultArea();
        DLHorizontalItem defaultState = mViewModel.getDefaultState();
        region = null;
        getProject__status = null;
        tv_area.setText(defaultArea.name);
        tv_state.setText(defaultState.name);
    }


    private void createPopupWindowData() {
        mPopupWindowView.fillData(mViewModel.getAreaList());
        mStatePopupWindowView.fillData(mViewModel.getAreaStateList());
    }

    public void toSearch(String searchKey) {
        refresh = true;
        if (TextUtils.isEmpty(searchKey)) {
            ToastUtils.showToast(getContext().getResources().getString(R.string.tip_input_key));
            return;
        }
        mViewModel.getSearchProjectList(refresh, getProject__status, region, searchKey);
    }

    private boolean refresh = true;

    private void toGetProjectList() {
        mViewModel.getProjectList(refresh, getProject__status, region);
    }

    @Override
    protected int getLayout() {
        return R.layout.proj_project_fragment;
//        proj_item_list
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_address) {
            mPopupWindowView.showPopupWindow(v);
        } else if (v.getId() == R.id.ll_state) {
            mStatePopupWindowView.showPopupWindow(v);
        } else if (v.getId() == R.id.ll_to_search) {
            SearchActivity.startActivity(getContext());

        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        refresh = true;
        toGetProjectList();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        refresh = false;
        toGetProjectList();
    }
}
