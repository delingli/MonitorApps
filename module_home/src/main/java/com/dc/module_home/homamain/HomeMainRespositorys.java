package com.dc.module_home.homamain;

import android.text.TextUtils;

import com.dc.baselib.http.newhttp.StrAbsHttpSubscriber;
import com.dc.baselib.mvvm.BaseRespository;
import com.dc.baselib.mvvm.EventUtils;
import com.dc.commonlib.commonentity.HomeBean;
import com.dc.commonlib.utils.JsonUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomeMainRespositorys extends BaseRespository {
    public static String EVENT_KEY_RELEASE_FAILURE;
    public static String EVENT_FINISHREFRESH;

    public HomeMainRespositorys() {
        EVENT_KEY_RELEASE_FAILURE = EventUtils.getEventKey();
        EVENT_FINISHREFRESH = EventUtils.getEventKey();
    }

    public void toGetownerCompanyBoard(int company_id) {
        addDisposable(mRetrofit.create(IHomeMainService.class)
                .getownerCompanyBoard(company_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new StrAbsHttpSubscriber() {
                    @Override
                    public void onSuccess(String s) {
                        if (!TextUtils.isEmpty(s)) {
                            HomeBean homebean = JsonUtil.fromJson(s, HomeBean.class);
                            conversionData(homebean);
                        } else {
                            postData(EVENT_FINISHREFRESH, "finsh");
                        }

                    }

                    @Override
                    public void onFailure(String msg, String code) {
//                        ToastUtils.showToast(msg);
                        postData(EVENT_FINISHREFRESH, "finsh");
                    }
                }));
    }


    private void conversionData(HomeBean homebean) {
        if (null != homebean) {
            List<IAbsHomeItem> list = new ArrayList<>();
            ProjectOverviewHomeItem projectOverviewHomeItem = new ProjectOverviewHomeItem();

            ProjectOverviewHomeItem.ProjectOverviewHomeItemBean allPro = new ProjectOverviewHomeItem.ProjectOverviewHomeItemBean();
            allPro.projectCount = homebean.project_cnt;
            allPro.projectTitle = "项目总数(个)";

            ProjectOverviewHomeItem.ProjectOverviewHomeItemBean projectUnderConstruction = new ProjectOverviewHomeItem.ProjectOverviewHomeItemBean();
            projectUnderConstruction.projectCount = homebean.construction_project_cnt;
            projectUnderConstruction.projectTitle = "在建(个)";

            ProjectOverviewHomeItem.ProjectOverviewHomeItemBean noWorkProject = new ProjectOverviewHomeItem.ProjectOverviewHomeItemBean();
            noWorkProject.projectCount = homebean.prepare_project_cnt;
            noWorkProject.projectTitle = "未开工(个)";


            projectOverviewHomeItem.projectAll = allPro;
            projectOverviewHomeItem.projectUnderConstruction = projectUnderConstruction;
            projectOverviewHomeItem.noWorkProject = noWorkProject;
            list.add(projectOverviewHomeItem);
            LabHomeItem labHomeItem = new LabHomeItem();
            labHomeItem.title = "项目区域";
            list.add(labHomeItem);
            if (homebean.region_projects != null) {
                List<ProjectAreaHomeItem.ProjectAreaItems> llAreas = new ArrayList<>();
                if (null != homebean.region_projects.yinhu) {
                    ProjectAreaHomeItem.ProjectAreaItems areaItems1 = new ProjectAreaHomeItem.ProjectAreaItems();
                    areaItems1.completedProjects = homebean.region_projects.yinhu.get(0);
                    areaItems1.totalProjects = homebean.region_projects.yinhu.get(1);
                    areaItems1.projectAdress = "银湖";
                    llAreas.add(areaItems1);
                }
                if (null != homebean.region_projects.xindeng) {
                    ProjectAreaHomeItem.ProjectAreaItems areaItems1 = new ProjectAreaHomeItem.ProjectAreaItems();
                    areaItems1.completedProjects = homebean.region_projects.xindeng.get(0);
                    areaItems1.totalProjects = homebean.region_projects.xindeng.get(1);
                    areaItems1.projectAdress = "新登";
                    llAreas.add(areaItems1);
                }
                if (null != homebean.region_projects.changkou) {
                    ProjectAreaHomeItem.ProjectAreaItems areaItems1 = new ProjectAreaHomeItem.ProjectAreaItems();
                    areaItems1.completedProjects = homebean.region_projects.changkou.get(0);
                    areaItems1.totalProjects = homebean.region_projects.changkou.get(1);
                    areaItems1.projectAdress = "场口";
                    llAreas.add(areaItems1);
                }

                if (null != homebean.region_projects.dongzhou) {
                    ProjectAreaHomeItem.ProjectAreaItems areaItems1 = new ProjectAreaHomeItem.ProjectAreaItems();
                    areaItems1.completedProjects = homebean.region_projects.dongzhou.get(0);
                    areaItems1.totalProjects = homebean.region_projects.dongzhou.get(1);
                    areaItems1.projectAdress = "东洲";
                    llAreas.add(areaItems1);
                }

                if (null != homebean.region_projects.jinqiao) {
                    ProjectAreaHomeItem.ProjectAreaItems areaItems1 = new ProjectAreaHomeItem.ProjectAreaItems();
                    areaItems1.completedProjects = homebean.region_projects.jinqiao.get(0);
                    areaItems1.totalProjects = homebean.region_projects.jinqiao.get(1);
                    areaItems1.projectAdress = "金桥";
                    llAreas.add(areaItems1);
                }
                if (null != homebean.region_projects.lushan) {
                    ProjectAreaHomeItem.ProjectAreaItems areaItems1 = new ProjectAreaHomeItem.ProjectAreaItems();
                    areaItems1.completedProjects = homebean.region_projects.lushan.get(0);
                    areaItems1.totalProjects = homebean.region_projects.lushan.get(1);
                    areaItems1.projectAdress = "鹿山";
                    llAreas.add(areaItems1);
                }
                ProjectAreaHomeItem areaHomeItem = new ProjectAreaHomeItem();
                areaHomeItem.projectAreas = llAreas;
                list.add(areaHomeItem);
            }

            LabHomeItem labHomeItem2 = new LabHomeItem();
            labHomeItem2.title = "视频监控";
            list.add(labHomeItem2);

            if (homebean.video_urls != null && !homebean.video_urls.isEmpty()) {
                VideoMonitoringHomeItem videoMonitoringHomeItem;
                for (HomeBean.VideoUrlsBean item : homebean.video_urls) {
                    videoMonitoringHomeItem = new VideoMonitoringHomeItem();
                    videoMonitoringHomeItem.name = item.name;
                    videoMonitoringHomeItem.project = item.project + "";
                    videoMonitoringHomeItem.url = item.url;
                    list.add(videoMonitoringHomeItem);
                }
            }
            postData(EVENT_KEY_RELEASE_FAILURE, list);
        } else {
            postData(EVENT_FINISHREFRESH, "finish");
        }
    }
}
