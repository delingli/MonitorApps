package com.dc.module_bbs.projectlist;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.dc.commonlib.common.BaseRecyclerAdapter;
import com.dc.commonlib.common.BaseViewHolder;
import com.dc.commonlib.utils.GlideUtils;
import com.dc.module_bbs.R;

import java.util.List;

public class ProjectItemAdapter extends BaseRecyclerAdapter<ProjItems> {
    /**
     * @param context
     * @param list
     * @param itemLayoutId
     */
    public ProjectItemAdapter(Context context, @Nullable List<ProjItems> list, int itemLayoutId) {
        super(context, list, R.layout.proj_item_list);
    }

    @Override
    protected void convert(BaseViewHolder holder, ProjItems projItems, int position, List<Object> payloads) {
        if (null != projItems) {
            ImageView iv_left_img = holder.getView(R.id.iv_left_img);
            TextView tv_title = holder.getView(R.id.tv_title);
            TextView tv_state = holder.getView(R.id.tv_state);
            tv_title.setText(projItems.title);
            tv_state.setText(projItems.state);
            GlideUtils.loadRoundUrl(getContext(), projItems.pic_url, iv_left_img);
        }
    }

}
