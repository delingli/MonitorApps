package com.dc.module_bbs.bbsdetail.fruitplate;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.dc.commonlib.common.BaseRecyclerAdapter;
import com.dc.commonlib.common.BaseViewHolder;
import com.dc.commonlib.utils.GlideUtils;
import com.dc.module_bbs.R;
import com.dc.module_bbs.bbsdetail.BBsDetails;

import java.util.List;

public class FruitPlateAdapter extends BaseRecyclerAdapter<BBsDetails> {
    /**
     * @param context
     * @param list
     * @param itemLayoutId
     */
    public FruitPlateAdapter(Context context, @Nullable List<BBsDetails> list, int itemLayoutId) {
        super(context, list, R.layout.bbs_detail_fruit_plate);
    }

    @Override
    protected void convert(BaseViewHolder holder, BBsDetails bBsDetails, int position, List<Object> payloads) {
        if (null != bBsDetails) {
            ImageView iv_icon = holder.getView(R.id.iv_icon);
            TextView tv_title = holder.getView(R.id.tv_title);
            TextView tv_desc = holder.getView(R.id.tv_desc);
            GlideUtils.loadRoundUrl(getContext(),bBsDetails.pic,iv_icon);
            tv_title.setText(bBsDetails.forumname);
            tv_desc.setText(bBsDetails.description);
        }

    }
}
