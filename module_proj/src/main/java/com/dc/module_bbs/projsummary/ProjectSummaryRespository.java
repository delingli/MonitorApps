package com.dc.module_bbs.projsummary;

import android.text.TextUtils;

import com.dc.baselib.http.newhttp.StrAbsHttpSubscriber;
import com.dc.baselib.mvvm.BaseRespository;
import com.dc.baselib.mvvm.EventUtils;
import com.dc.baselib.utils.ToastUtils;
import com.dc.commonlib.commonentity.HomeBean;
import com.dc.commonlib.utils.JsonUtil;
import com.dc.commonlib.utils.MoneyUtils;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProjectSummaryRespository extends BaseRespository {
    public String EVENT_AREA_PROJECT;

    public ProjectSummaryRespository() {
        EVENT_AREA_PROJECT = EventUtils.getEventKey();
    }

    public void toGetownerCompanyBoard(int company_id, String region) {
        addDisposable(mRetrofit.create(IProjectSummaryService.class)
                .getownerCompanyBoard(company_id, region)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new StrAbsHttpSubscriber() {
                    @Override
                    public void onSuccess(String s) {
                        if (!TextUtils.isEmpty(s)) {
                            HomeBean homebean = JsonUtil.fromJson(s, HomeBean.class);
                            conversionData(homebean);
                        }
                    }

                    @Override
                    public void onFailure(String msg, String code) {
                        ToastUtils.showToast(msg);
                    }
                }));
    }

    private void conversionData(HomeBean homebean) {
        if (null != homebean) {
            ProjectAreaItem projectAreaItem = new ProjectAreaItem();
            projectAreaItem.construction_project_cnt = homebean.construction_project_cnt;
            projectAreaItem.invested = MoneyUtils.yuanToHundredMillion(homebean.invested + "");
            projectAreaItem.investment = MoneyUtils.yuanToHundredMillion(homebean.investment + "");
            projectAreaItem.noWorkInvestment = MoneyUtils.yuanToHundredMillion((homebean.investment - homebean.invested) + "");

            projectAreaItem.prepare_project_cnt = homebean.prepare_project_cnt;
            projectAreaItem.project_cnt = homebean.project_cnt;
            projectAreaItem.WorksPercentage = MoneyUtils.percentage(homebean.investment + "", homebean.invested + "");
            projectAreaItem.noWorksPercentage = MoneyUtils.percentage(homebean.investment + "", (homebean.investment - homebean.invested) + "");
//最后格式化并输出

            if (homebean.schedule_summary != null && !homebean.schedule_summary.isEmpty()) {
                ProjectAreaItem.ScheduleSummary scheduleSummary;
                List<ProjectAreaItem.ScheduleSummary> ll = new ArrayList<>();
                for (HomeBean.ScheduleSummaryBean item : homebean.schedule_summary) {
                    scheduleSummary = new ProjectAreaItem.ScheduleSummary();
                    scheduleSummary.count = item.count;
                    scheduleSummary.projectextrainfo__schedule = item.projectextrainfo__schedule;
                    ll.add(scheduleSummary);
                }
                projectAreaItem.schedule_summary = ll;

            }
            postData(EVENT_AREA_PROJECT, projectAreaItem);
        }
    }
}

