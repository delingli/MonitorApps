package com.dc.module_home.homamain;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dc.baselib.mvvm.AbsLifecycleFragment;
import com.dc.commonlib.utils.ArounterManager;
import com.dc.module_home.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

@Route(path = ArounterManager.HOME_HOMEMAINFRAGMENT_URL)
public class HomeMainFragment extends AbsLifecycleFragment<HomeMainViewModel> {

    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private HomeMainAdapter mHomeMainAdapter;

    @Override
    public void dataObserver() {

    }

    @Override
    public void initView(View view) {
        super.initView(view);
        recyclerView = view.findViewById(R.id.recyclerView);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setBackgroundColor(getResources().getColor(R.color.white));
        recyclerView.setBackgroundColor(getResources().getColor(R.color.white));
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableLoadMore(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mHomeMainAdapter = new HomeMainAdapter(getContext(), getData(), -1));
    }

    private List<IAbsHomeItem> getData() {
        List<IAbsHomeItem> list = new ArrayList<>();
        ProjectOverviewHomeItem projectOverviewHomeItem = new ProjectOverviewHomeItem();

        ProjectOverviewHomeItem.ProjectOverviewHomeItemBean allPro = new ProjectOverviewHomeItem.ProjectOverviewHomeItemBean();
        allPro.projectCount = 101;
        allPro.projectTitle = "项目总数(个)";

        ProjectOverviewHomeItem.ProjectOverviewHomeItemBean projectUnderConstruction = new ProjectOverviewHomeItem.ProjectOverviewHomeItemBean();
        projectUnderConstruction.projectCount = 110;
        projectUnderConstruction.projectTitle = "在建(个)";

        ProjectOverviewHomeItem.ProjectOverviewHomeItemBean noWorkProject = new ProjectOverviewHomeItem.ProjectOverviewHomeItemBean();
        noWorkProject.projectCount = 1000;
        noWorkProject.projectTitle = "未开工(个)";


        projectOverviewHomeItem.projectAll = allPro;
        projectOverviewHomeItem.projectUnderConstruction = projectUnderConstruction;
        projectOverviewHomeItem.noWorkProject = noWorkProject;
        list.add(projectOverviewHomeItem);
        LabHomeItem labHomeItem = new LabHomeItem();
        labHomeItem.title = "项目区域";
        list.add(labHomeItem);

        List<ProjectAreaHomeItem.ProjectAreaItems> llAreas = new ArrayList<>();
        ProjectAreaHomeItem.ProjectAreaItems areaItems1 = new ProjectAreaHomeItem.ProjectAreaItems();
        areaItems1.totalProjects = 20;
        areaItems1.completedProjects = 15;
        areaItems1.projectAdress = "新疆";
        llAreas.add(areaItems1);
        ProjectAreaHomeItem.ProjectAreaItems areaItems2 = new ProjectAreaHomeItem.ProjectAreaItems();
        areaItems2.totalProjects = 20;
        areaItems2.completedProjects = 15;
        areaItems2.projectAdress = "北京";
        llAreas.add(areaItems2);

        ProjectAreaHomeItem.ProjectAreaItems areaItems3 = new ProjectAreaHomeItem.ProjectAreaItems();
        areaItems3.totalProjects = 20;
        areaItems3.completedProjects = 15;
        areaItems3.projectAdress = "日本";
        llAreas.add(areaItems3);

        ProjectAreaHomeItem.ProjectAreaItems areaItems4 = new ProjectAreaHomeItem.ProjectAreaItems();
        areaItems4.totalProjects = 20;
        areaItems4.completedProjects = 15;
        areaItems4.projectAdress = "美国";
        llAreas.add(areaItems4);

        ProjectAreaHomeItem areaHomeItem = new ProjectAreaHomeItem();
        areaHomeItem.projectAreas = llAreas;
        list.add(areaHomeItem);

        LabHomeItem labHomeItem2 = new LabHomeItem();
        labHomeItem2.title = "视频监控";
        list.add(labHomeItem2);

        VideoMonitoringHomeItem videoMonitoringHomeItem = new VideoMonitoringHomeItem();
        videoMonitoringHomeItem.videoTitle = "银湖开发区南区全景";
        videoMonitoringHomeItem.videoUrl = "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2192316936,131704123&fm=26&gp=0.jpg";
        list.add(videoMonitoringHomeItem);

        VideoMonitoringHomeItem videoMonitoringHomeItem2 = new VideoMonitoringHomeItem();
        videoMonitoringHomeItem2.videoTitle = "银湖开发区北区全景";
        videoMonitoringHomeItem2.videoUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1590308415771&di=589f399c15ef4746ba03440ef3419dd4&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2Fd35b7c9a440242a1cf03685e7eb269086f727a3cf6ebc-fmByMh_fw658";
        list.add(videoMonitoringHomeItem2);
        return list;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayout() {
        return R.layout.common_refresh_layout;
    }
}
