package com.dc.module_bbs.bbsmain;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.dc.commonlib.common.BaseRecyclerAdapter;
import com.dc.commonlib.common.BaseViewHolder;
import com.dc.commonlib.utils.GlideUtils;
import com.dc.module_bbs.R;

import java.util.List;

public class BBsItemAdapter extends BaseRecyclerAdapter<FocusPlateItem.ChildBean> {
    /**
     * @param context
     * @param list
     * @param itemLayoutId
     */
    public BBsItemAdapter(Context context, @Nullable List<FocusPlateItem.ChildBean> list, int itemLayoutId) {
        super(context, list, R.layout.bbs_item_focus_item);
    }

    @Override
    protected void convert(BaseViewHolder holder, FocusPlateItem.ChildBean childBean, int position, List<Object> payloads) {
        if (null != childBean) {
          ImageView iv_leftImg= holder.getView(R.id.iv_leftImg);
          TextView tv_title= holder.getView(R.id.tv_title);
          TextView tv_desc= holder.getView(R.id.tv_desc);
            GlideUtils.loadRoundUrl(getContext(),childBean.pic,iv_leftImg);
            tv_title.setText(childBean.forumname);
//            tv_desc.setText(childBean.forumname);
        }
    }
}
