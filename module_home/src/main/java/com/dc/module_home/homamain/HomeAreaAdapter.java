package com.dc.module_home.homamain;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.dc.commonlib.common.BaseRecyclerAdapter;
import com.dc.commonlib.common.BaseViewHolder;
import com.dc.module_home.R;

import java.util.List;

public class HomeAreaAdapter extends BaseRecyclerAdapter<ProjectAreaHomeItem.ProjectAreaItems> {
    /**
     * @param context
     * @param list
     * @param itemLayoutId
     */
    public HomeAreaAdapter(Context context, @Nullable List<ProjectAreaHomeItem.ProjectAreaItems> list, int itemLayoutId) {
        super(context, list, R.layout.home_item_area_item);
    }

    @Override
    protected void convert(BaseViewHolder holder, ProjectAreaHomeItem.ProjectAreaItems projectAreaItems, int position, List<Object> payloads) {
        if (null != projectAreaItems) {
            TextView tv_area_progress = holder.getView(R.id.tv_area_progress);
            TextView tv_proj_adress = holder.getView(R.id.tv_proj_adress);
            tv_area_progress.setText(projectAreaItems.completedProjects + "/" + projectAreaItems.totalProjects);
            tv_proj_adress.setText(projectAreaItems.projectAdress);
        }
    }
}
