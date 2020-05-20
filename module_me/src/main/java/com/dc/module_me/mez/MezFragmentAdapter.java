package com.dc.module_me.mez;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dc.commonlib.common.BaseRecyclerAdapter;
import com.dc.commonlib.common.BaseViewHolder;
import com.dc.commonlib.utils.ArounterManager;
import com.dc.module_me.R;

import java.util.List;
public class MezFragmentAdapter extends BaseRecyclerAdapter<MeDataItems> {
    /**
     * @param context
     * @param list
     * @param itemLayoutId
     */
    public MezFragmentAdapter(Context context, @Nullable List<MeDataItems> list, int itemLayoutId) {
        super(context, list, R.layout.me_fragment_items);
    }

    @Override
    protected void convert(BaseViewHolder holder, MeDataItems meDataItems, int position, List<Object> payloads) {
        if (null != meDataItems) {
            TextView tv_name_desc = holder.getView(R.id.tv_name_desc);
            TextView tv_value = holder.getView(R.id.tv_valuezz);
            tv_name_desc.setText(meDataItems.title);
            tv_value.setText(meDataItems.value);
        }
    }
}
