package com.dc.module_bbs.bbsmain;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dc.commonlib.common.BaseRecyclerAdapter;
import com.dc.commonlib.common.BaseViewHolder;
import com.dc.commonlib.utils.GlideUtils;
import com.dc.commonlib.weiget.FocusOnTextView;
import com.dc.module_bbs.R;
import com.dc.module_bbs.bbsdetail.BBSDetailActivity;

import java.util.List;

public class BBSListAdapter extends BaseRecyclerAdapter<AbsFocusDiscuss> {
    /**
     * @param context
     * @param list
     * @param itemLayoutId
     */
    public BBSListAdapter(Context context, @Nullable List<AbsFocusDiscuss> list, int itemLayoutId) {
        super(context, list, R.layout.bbs_item_focus_or_bbs);
    }


    @Override
    protected void convert(BaseViewHolder holder, AbsFocusDiscuss focusPlateItemzz, int position, List<Object> payloads) {
        if (null != focusPlateItemzz) {
            ImageView iv_leftimg = holder.getView(R.id.iv_leftimg);

            TextView tv_title = holder.getView(R.id.tv_title);
            TextView tv_desc = holder.getView(R.id.tv_desc);
            TextView tv_members = holder.getView(R.id.tv_members);

            final ImageView iv_roll_out = holder.getView(R.id.iv_roll_out);
            final ImageView iv_pack_up = holder.getView(R.id.iv_pack_up);

            ImageView iv_right_arrow = holder.getView(R.id.iv_right_arrow);
            FocusOnTextView tv_focus_ons = holder.getView(R.id.tv_focus_ons);
            final RecyclerView recycleViews = holder.getView(R.id.recycleViews);
            iv_roll_out.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recycleViews.setVisibility(View.VISIBLE);
                    iv_roll_out.setVisibility(View.GONE);
                    iv_pack_up.setVisibility(View.VISIBLE);
                }
            });
            iv_pack_up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recycleViews.setVisibility(View.GONE);
                    iv_pack_up.setVisibility(View.GONE);
                    iv_roll_out.setVisibility(View.VISIBLE);
                }
            });
            recycleViews.setLayoutManager(new LinearLayoutManager(getContext()));

            if (focusPlateItemzz instanceof FocusPlateItem) {
                FocusPlateItem focusPlateItem = (FocusPlateItem) focusPlateItemzz;
                //关注
                GlideUtils.loadRoundUrl(getContext(), focusPlateItem.pic, iv_leftimg);
                tv_title.setText(focusPlateItem.forumname);
                tv_desc.setText(focusPlateItem.description);
                tv_members.setText(getContext().getString(R.string.tiez) + focusPlateItem.posts);
                iv_right_arrow.setVisibility(View.VISIBLE);
                tv_focus_ons.setVisibility(View.GONE);
                if (focusPlateItem.child == null || focusPlateItem.child.isEmpty()) {
                    recycleViews.setVisibility(View.GONE);
                    iv_roll_out.setVisibility(View.GONE);
                    iv_pack_up.setVisibility(View.GONE);
                } else {
                    //setAdapter
                    recycleViews.setVisibility(View.GONE);
                    iv_pack_up.setVisibility(View.GONE);
                    iv_roll_out.setVisibility(View.VISIBLE);
                }
                if (null != focusPlateItem.child) {
                    final BBsItemAdapter bBsItemAdapter = new BBsItemAdapter(getContext(), focusPlateItem.child, 0);
                    recycleViews.setAdapter(bBsItemAdapter);
                    bBsItemAdapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClickListener(View v, int position) {
                            if (null != bBsItemAdapter.getList() && bBsItemAdapter.getList().get(position) != null) {
                                FocusPlateItem.ChildBean childBean = bBsItemAdapter.getList().get(position);

                                BBSDetailActivity.startActivity(getContext(), childBean.forumid);

                            }
                        }
                    });
                }


            } else if (focusPlateItemzz instanceof FocusPlateItem.ChildBean) {
                //板块
                iv_right_arrow.setVisibility(View.GONE);
                tv_focus_ons.setVisibility(View.VISIBLE);
                FocusPlateItem.ChildBean childBean = (FocusPlateItem.ChildBean) focusPlateItemzz;
                GlideUtils.loadRoundUrl(getContext(), childBean.pic, iv_leftimg);

                tv_focus_ons.setVisibility(View.VISIBLE);
                tv_title.setText(childBean.forumname);
                tv_desc.setText(childBean.description);
                tv_members.setText(getContext().getString(R.string.tiez) + childBean.posts);
//                tv_focus_ons.setFocusOn(childBean.is_focus == 1);


                if (childBean.child == null || childBean.child.isEmpty()) {
                    recycleViews.setVisibility(View.GONE);
                    iv_roll_out.setVisibility(View.GONE);
                    iv_pack_up.setVisibility(View.GONE);
                } else {
                    //setAdapter
                    recycleViews.setVisibility(View.GONE);
                    iv_pack_up.setVisibility(View.GONE);
                    iv_roll_out.setVisibility(View.VISIBLE);
                }
                if (null != childBean.child) {
                 final    BBsItemAdapter bBsItemAdapter = new BBsItemAdapter(getContext(), childBean.child, 0);
                    recycleViews.setAdapter(bBsItemAdapter);
                    bBsItemAdapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClickListener(View v, int position) {
                            if (null != bBsItemAdapter.getList() && bBsItemAdapter.getList().get(position) != null) {
                                FocusPlateItem.ChildBean childBean = bBsItemAdapter.getList().get(position);

                                BBSDetailActivity.startActivity(getContext(), childBean.forumid);

                            }
                        }
                    });
                }

            }
        }


    }
}
