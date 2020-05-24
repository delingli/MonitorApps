package com.dc.module_bbs.projectoverview;

import com.dc.baselib.mvvm.BaseRespository;

public class ProjectOverviewRespository extends BaseRespository {

    public ProjectOverviewItem getProjectOverviewItem() {
        ProjectOverviewItem projectOverviewItem = new ProjectOverviewItem();
        projectOverviewItem.landArea = "16.66";
        projectOverviewItem.constructionArea = "324545.45";
        ProjectOverviewItem.ConstructionUnit constructionUnit = new ProjectOverviewItem.ConstructionUnit();
        constructionUnit.company = "测试公司";
        constructionUnit.contact = "达令";
        constructionUnit.contactPhone = "18001332871";
        projectOverviewItem.constructionunit = constructionUnit;

        ProjectOverviewItem.Contractors contractors = new ProjectOverviewItem.Contractors();
        contractors.company = "测试公司";
        contractors.contact = "达令";
        contractors.contactPhone = "18001332871";
        projectOverviewItem.contractors = contractors;


        ProjectOverviewItem.SupervisionUnit supervisionunit = new ProjectOverviewItem.SupervisionUnit();
        supervisionunit.company = "测试公司";
        supervisionunit.contact = "达令";
        supervisionunit.contactPhone = "18001332871";
        projectOverviewItem.supervisionunit = supervisionunit;


        ProjectOverviewItem.DesignUnit designunit = new ProjectOverviewItem.DesignUnit();
        designunit.company = "设计单位";
        designunit.contact = "达令";
        designunit.contactPhone = "18001332871";
        projectOverviewItem.designunit = designunit;
        return projectOverviewItem;
    }
}
