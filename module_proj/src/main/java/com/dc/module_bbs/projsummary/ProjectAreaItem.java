package com.dc.module_bbs.projsummary;

import java.util.List;

public class ProjectAreaItem {
    public int project_cnt;
    public int prepare_project_cnt;//未开工工程总数
    public int construction_project_cnt;//在建工程总数
    public float invested;//已投资总额  单位：元
    public float investment;//总投资总额  单位：元
    public float noWorkInvestment;//未开工投资额  单位：元
    public float construction_investment;//在建
    public double noWorksPercentage;
    public double WorksPercentage;
    public List<ScheduleSummary> schedule_summary;

    public static class ScheduleSummary {
        public String projectextrainfo__schedule;
        public int count;
    }

}
