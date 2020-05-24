package com.dc.module_bbs.projshow;

import android.content.Context;
import android.support.annotation.Nullable;

import com.dc.commonlib.common.BaseRecyclerAdapter;
import com.dc.commonlib.common.BaseViewHolder;
import com.dc.commonlib.common.MultiTypeSupport;
import com.dc.module_bbs.R;

import java.util.List;

public class ProjectShowAdapter extends BaseRecyclerAdapter<AbsProjectInfo> implements MultiTypeSupport<AbsProjectInfo> {
    /**
     * @param context
     * @param list
     * @param itemLayoutId
     */
    public ProjectShowAdapter(Context context, @Nullable List<AbsProjectInfo> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
    }

    @Override
    protected void convert(BaseViewHolder holder, AbsProjectInfo item, int position, List<Object> payloads) {
        if (item instanceof BannerProjectInfo) {
        } else if (item instanceof ProjectInfoDetail) {
        } else if (item instanceof ProjectInvestmentInfo) {
        } else if (item instanceof ProjectInvestmentItems) {
        }
    }

    @Override
    public int getLayoutId(AbsProjectInfo item, int position) {
        if (item instanceof BannerProjectInfo) {
            return R.layout.proj_show_item_banner;
        } else if (item instanceof ProjectInfoDetail) {
            return R.layout.proj_show_projinfo;
        } else if (item instanceof ProjectInvestmentInfo) {
            return R.layout.proj_show_projj_pie;
        } else if (item instanceof ProjectInvestmentItems) {
            return R.layout.proj_item_progress_items;
        }
        return 0;
    }
}
