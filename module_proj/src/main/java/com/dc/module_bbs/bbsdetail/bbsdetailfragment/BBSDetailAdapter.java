package com.dc.module_bbs.bbsdetail.bbsdetailfragment;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.dc.commonlib.common.BaseRecyclerAdapter;
import com.dc.commonlib.common.BaseViewHolder;
import com.dc.commonlib.common.MultiTypeSupport;
import com.dc.commonlib.utils.GlideUtils;
import com.dc.commonlib.weiget.TextViewHelper;
import com.dc.module_bbs.R;

import java.util.List;

public class BBSDetailAdapter extends BaseRecyclerAdapter<ThemeInForumItemWrapper> implements MultiTypeSupport<ThemeInForumItemWrapper> {
    /**
     * @param context
     * @param list
     * @param itemLayoutId
     */
    public BBSDetailAdapter(Context context, @Nullable List<ThemeInForumItemWrapper> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
        this.multiTypeSupport = this;
    }

    @Override
    protected void convert(BaseViewHolder holder, ThemeInForumItemWrapper item, int position, List<Object> payloads) {
        if (TextUtils.equals(item.type, ThemeInForumItemWrapper.TYPE_MORE_PIC)) {
            ImageView iv_head = holder.getView(R.id.iv_head);
            TextView tv_name = holder.getView(R.id.tv_name);
            TextView tv_tag = holder.getView(R.id.tv_tag);
            RecyclerView recyclerView = holder.getView(R.id.recyclerView);
            TextView tv_comments = holder.getView(R.id.tv_comments);
            TextView tv_collection = holder.getView(R.id.tv_collection);
            TextView tv_time = holder.getView(R.id.tv_time);


            GlideUtils.loadCircleUrl(getContext(), item.themeInForumItem.avatar, iv_head);
            tv_name.setText(item.themeInForumItem.author);
            tv_tag.setText(item.themeInForumItem.title);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(layoutManager);
            BBsDetailMorePicAdapter bBsDetailMorePicAdapter = new BBsDetailMorePicAdapter(getContext(), item.themeInForumItem.thread_pics, 0);
            recyclerView.setAdapter(bBsDetailMorePicAdapter);
            tv_comments.setText(item.themeInForumItem.replies + getContext().getResources().getString(R.string.comment_count));
            tv_collection.setText(item.themeInForumItem.favtimes + getContext().getResources().getString(R.string.collection));
            tv_time.setText(item.themeInForumItem.show_time);
//            标题前标志（1置顶，2精华，3下载，0没有标志）
            resetTags(item, tv_tag);

        } else if (TextUtils.equals(item.type, ThemeInForumItemWrapper.TYPE_NO_PIC)) {
            ImageView iv_head = holder.getView(R.id.iv_head);
            TextView tv_name = holder.getView(R.id.tv_name);
            TextView tv_tag = holder.getView(R.id.tv_tag);
            TextView tv_comments = holder.getView(R.id.tv_comments);
            TextView tv_collection = holder.getView(R.id.tv_collection);
            TextView tv_time = holder.getView(R.id.tv_time);
            GlideUtils.loadCircleUrl(getContext(), item.themeInForumItem.avatar, iv_head);
            tv_name.setText(item.themeInForumItem.author);
            tv_comments.setText(item.themeInForumItem.replies + getContext().getResources().getString(R.string.comment_count));
            tv_collection.setText(item.themeInForumItem.favtimes + getContext().getResources().getString(R.string.collection));
            tv_time.setText(item.themeInForumItem.show_time);

            resetTags(item, tv_tag);


        } else if (TextUtils.equals(item.type, ThemeInForumItemWrapper.TYPE_ONE_PIC)) {
            ImageView iv_head = holder.getView(R.id.iv_head);
            TextView tv_name = holder.getView(R.id.tv_name);
            TextView tv_tag = holder.getView(R.id.tv_tag);
            TextView tv_comments = holder.getView(R.id.tv_comments);
            TextView tv_collection = holder.getView(R.id.tv_collection);
            TextView tv_time = holder.getView(R.id.tv_time);
            ImageView iv_right_pic = holder.getView(R.id.iv_right_pic);

            GlideUtils.loadCircleUrl(getContext(), item.themeInForumItem.avatar, iv_head);
            tv_name.setText(item.themeInForumItem.author);
            tv_comments.setText(item.themeInForumItem.replies + getContext().getResources().getString(R.string.comment_count));
            tv_collection.setText(item.themeInForumItem.favtimes + getContext().getResources().getString(R.string.collection));
            tv_time.setText(item.themeInForumItem.show_time);
            GlideUtils.loadRoundUrl(getContext(), item.themeInForumItem.avatar, iv_right_pic);
            resetTags(item, tv_tag);


        }
    }

    private void resetTags(ThemeInForumItemWrapper item, TextView tv_tag) {
        if (item.themeInForumItem.show_marker == 1) {
            tv_tag.setText(TextViewHelper.setLeftImage(getContext(), R.drawable.placedtop, item.themeInForumItem.title));
        } else if (item.themeInForumItem.show_marker == 2) {
            tv_tag.setText(TextViewHelper.setLeftImage(getContext(), R.drawable.essence, item.themeInForumItem.title));
        } else if (item.themeInForumItem.show_marker == 3) {
            tv_tag.setText(TextViewHelper.setLeftImage(getContext(), R.drawable.ivon_download, item.themeInForumItem.title));
        } else {
            //没有标志
            tv_tag.setText(TextViewHelper.setLeftImage(getContext(), 0, item.themeInForumItem.title));
        }
    }

    @Override
    public int getLayoutId(ThemeInForumItemWrapper item, int position) {
        if (TextUtils.equals(item.type, ThemeInForumItemWrapper.TYPE_MORE_PIC)) {
            return R.layout.bbs_detail_item_three_pic;
        } else if (TextUtils.equals(item.type, ThemeInForumItemWrapper.TYPE_NO_PIC)) {
            return R.layout.bbs_detail_item_no_pic;
        } else if (TextUtils.equals(item.type, ThemeInForumItemWrapper.TYPE_ONE_PIC)) {
            return R.layout.bbs_detail_item_one_pic;
        }
        return 0;
    }
}
