package com.dc.module_bbs.projectoverview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.dc.baselib.mvvm.AbsLifecycleActivity;
import com.dc.module_bbs.R;
import com.dc.module_bbs.projshow.ProjectItemBean;

/**
 * "项目概况"
 */
public class ProjectOverviewActivity extends AbsLifecycleActivity<ProjectOverviewViewModel> implements View.OnClickListener {

    private TextView tv_land_area;
    private TextView tv_construction_area;
    private TextView tv_construction_unit;
    private TextView tv_name;
    private TextView tv_phone;
    private TextView tv_general_contractor;
    private TextView tv_general_contractor_name;
    private TextView tv_general_contractor_phone;
    private TextView tv_supervision_unit;
    private TextView tv_design_units;
    public static String KEY_PROJECTITEMBEAN = "projectitembean";
    private ProjectItemBean mItemBean;
    private ProjectOverviewItem projectOverviewItem;

    public static void startActivity(Context context, ProjectItemBean itemBean) {
        Intent intent = new Intent(context, ProjectOverviewActivity.class);
        intent.putExtra(KEY_PROJECTITEMBEAN, itemBean);
        context.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.proj_activity_projectoverview;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setTitle(R.string.project_overview);
        if (getIntent() != null) {
            mItemBean = getIntent().getParcelableExtra(KEY_PROJECTITEMBEAN);
        }
        tv_land_area = findViewById(R.id.tv_land_area);
        tv_construction_area = findViewById(R.id.tv_construction_area);
        tv_construction_unit = findViewById(R.id.tv_construction_unit);
        tv_name = findViewById(R.id.tv_name);
        tv_phone = findViewById(R.id.tv_phone);
        tv_general_contractor = findViewById(R.id.tv_general_contractor);
        tv_general_contractor_name = findViewById(R.id.tv_general_contractor_name);
        tv_general_contractor_phone = findViewById(R.id.tv_general_contractor_phone);
        tv_supervision_unit = findViewById(R.id.tv_supervision_unit);
        tv_design_units = findViewById(R.id.tv_design_units);
        projectOverviewItem = mViewModel.getProjectOverviewItem(mItemBean);
        fillData(projectOverviewItem);
        tv_phone.setOnClickListener(this);
        tv_general_contractor_phone.setOnClickListener(this);
    }

    private void fillData(ProjectOverviewItem projectOverviewItem) {
        if (null != projectOverviewItem) {
            tv_land_area.setText(projectOverviewItem.landArea);
            tv_construction_area.setText(projectOverviewItem.constructionArea);
            if(!TextUtils.isEmpty(projectOverviewItem.constructionunit.company)){
                tv_construction_unit.setText(projectOverviewItem.constructionunit.company);
            }
            if(!TextUtils.isEmpty(projectOverviewItem.constructionunit.contact)){
                tv_name.setText(projectOverviewItem.constructionunit.contact);

            }
            if(!TextUtils.isEmpty(projectOverviewItem.constructionunit.contactPhone)){
                tv_phone.setText(projectOverviewItem.constructionunit.contactPhone);
            }
            if (!TextUtils.isEmpty(projectOverviewItem.contractors.company)) {
                tv_general_contractor.setText(projectOverviewItem.contractors.company);

            }
            if (!TextUtils.isEmpty(projectOverviewItem.contractors.contact)) {
                tv_general_contractor_name.setText(projectOverviewItem.contractors.contact);
            }
            if (!TextUtils.isEmpty(projectOverviewItem.contractors.contactPhone)) {
                tv_general_contractor_phone.setText(projectOverviewItem.contractors.contactPhone);

            }
            if (!TextUtils.isEmpty(projectOverviewItem.supervisionunit.company)) {
                tv_supervision_unit.setText(projectOverviewItem.supervisionunit.company);
            }
            if (!TextUtils.isEmpty(projectOverviewItem.designunit.company)) {
                tv_design_units.setText(projectOverviewItem.designunit.company);
            }

        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_phone) {
            if (projectOverviewItem != null && null != projectOverviewItem.constructionunit && projectOverviewItem.constructionunit.contactPhone != null) {
                Intent myCallIntent = new Intent(Intent.ACTION_DIAL,
                        Uri.parse("tel:" + tv_phone.getText().toString()));
                startActivity(myCallIntent);

            }
        } else if (id == R.id.tv_general_contractor_phone) {
            if (projectOverviewItem != null && null != projectOverviewItem.contractors && projectOverviewItem.contractors.contactPhone != null) {
                Intent myCallIntent = new Intent(Intent.ACTION_DIAL,
                        Uri.parse("tel:" + tv_general_contractor_phone.getText().toString()));
                startActivity(myCallIntent);
            }

        }
    }
}
