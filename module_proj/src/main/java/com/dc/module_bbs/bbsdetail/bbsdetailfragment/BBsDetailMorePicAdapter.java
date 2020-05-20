package com.dc.module_bbs.bbsdetail.bbsdetailfragment;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dc.commonlib.common.BaseRecyclerAdapter;
import com.dc.commonlib.common.BaseViewHolder;
import com.dc.commonlib.utils.GlideUtils;
import com.dc.commonlib.utils.UIUtils;
import com.dc.module_bbs.R;

import java.util.List;

public class BBsDetailMorePicAdapter extends BaseRecyclerAdapter<String> {
    /**
     * @param context
     * @param list
     * @param itemLayoutId
     */
    public BBsDetailMorePicAdapter(Context context, @Nullable List<String> list, int itemLayoutId) {
        super(context, list, R.layout.bbs_detail_pic_item);
    }

    @Override
    protected void convert(BaseViewHolder holder, String s, int position, List<Object> payloads) {
        ImageView iv_pic = holder.getView(R.id.iv_pic);
        if (!TextUtils.isEmpty(s)) {

//            ViewGroup.LayoutParams layoutParams = iv_pic.getLayoutParams();
            int screenWidth = UIUtils.getScreenWidth(getContext());
//            layoutParams.width = screenWidth / 3;
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(screenWidth / 3, LinearLayout.LayoutParams.MATCH_PARENT);
            lp.setMargins(5, 0, 5, 0);
            iv_pic.setLayoutParams(lp);
            GlideUtils.loadRoundUrl(getContext(), s, iv_pic);
        }

    }
}
