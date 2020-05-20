package com.dc.module_bbs.projectlist;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dc.baselib.mvvm.AbsLifecycleFragment;
import com.dc.commonlib.utils.ArounterManager;
import com.dc.commonlib.weiget.horizontalrecycle.DLHorizontalItem;
import com.dc.module_bbs.R;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;


@Route(path = ArounterManager.PROJ_PROJECTFRAGMENT_URL)
public class ProjectListFragment extends AbsLifecycleFragment<ProjectListViewModel> implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private ProjectItemAdapter mProjectItemAdapter;
    private TextView tv_title;
    private ImageView iv_state_arrow, iv_address_arrow;
    private PopupWindowView mStatePopupWindowView, mPopupWindowView;

    @Override
    public void dataObserver() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void initView(View view) {
        super.initView(view);
        RefreshLayout refreshLayout = view.findViewById(R.id.refreshLayout);
        mPopupWindowView = new PopupWindowView(getContext());
        mStatePopupWindowView = new PopupWindowView(getContext());
        createPopupWindowData();
        tv_title = view.findViewById(R.id.tv_title);
        iv_state_arrow = view.findViewById(R.id.iv_state_arrow);
        iv_address_arrow = view.findViewById(R.id.iv_address_arrow);
        tv_title.setText(R.string.project);
        view.findViewById(R.id.iv_left_back).setVisibility(View.GONE);
        view.findViewById(R.id.ll_address).setOnClickListener(this);
        view.findViewById(R.id.ll_state).setOnClickListener(this);
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableLoadMore(false);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mProjectItemAdapter = new ProjectItemAdapter(getContext(), createData(), -1));
        view.findViewById(R.id.ll_to_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void createPopupWindowData() {
        DLHorizontalItem horizontalItem = new DLHorizontalItem("全部", "1", "a");
        List<DLHorizontalItem> list = new ArrayList<>();
        List<DLHorizontalItem> list2 = new ArrayList<>();
        for (int i = 0; i < 6; ++i) {
            list.add(horizontalItem = new DLHorizontalItem("全部" + i + 1, "1" + i, "a" + i));
            list2.add(horizontalItem = new DLHorizontalItem("状态" + i + 1, "1" + i, "a" + i));
        }
        mPopupWindowView.fillData(list);

        mStatePopupWindowView.fillData(list2);
    }

    private List<ProjItems> createData() {
        List<ProjItems> ll = new ArrayList<>();
        ll.add(new ProjItems("测试数据", "未开工", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1589997439869&di=47ee48fe14bc50a7b9e8d14bcfef9cb6&imgtype=0&src=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D1288886308%2C3395827368%26fm%3D214%26gp%3D0.jpg"));
        ll.add(new ProjItems("测试数据", "未开工", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1589997439869&di=47ee48fe14bc50a7b9e8d14bcfef9cb6&imgtype=0&src=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D1288886308%2C3395827368%26fm%3D214%26gp%3D0.jpg"));
        ll.add(new ProjItems("测试数据", "未开工", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1589997439869&di=47ee48fe14bc50a7b9e8d14bcfef9cb6&imgtype=0&src=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D1288886308%2C3395827368%26fm%3D214%26gp%3D0.jpg"));
        return ll;
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
        }
    }
}
