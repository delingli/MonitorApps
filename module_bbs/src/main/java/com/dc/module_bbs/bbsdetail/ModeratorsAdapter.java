package com.dc.module_bbs.bbsdetail;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.dc.commonlib.common.BaseRecyclerAdapter;
import com.dc.commonlib.common.BaseViewHolder;
import com.dc.commonlib.utils.GlideUtils;
import com.dc.module_bbs.R;

import java.util.List;

public class ModeratorsAdapter extends BaseRecyclerAdapter<String> {
    /**
     * @param context
     * @param list
     * @param itemLayoutId
     */
    public ModeratorsAdapter(Context context, @Nullable List<String> list, int itemLayoutId) {
        super(context, list, R.layout.bbs_moderators_item);
    }

    @Override
    protected void convert(BaseViewHolder holder, String s, int position, List<Object> payloads) {
        ImageView iv_moder_head = holder.getView(R.id.iv_moder_head);
        GlideUtils.loadCircleUrl(getContext(), s, iv_moder_head);

    }
}
