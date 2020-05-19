package com.dc.commonlib.share;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dc.commonlib.R;

import java.util.List;

public class ShareAdapter extends RecyclerView.Adapter<ShareAdapter.ShareAdapterHolder> {
    private List<ShareItem> mShareItemList;
    private Context context;

    public ShareAdapter(Context context, List<ShareItem> shareItemList) {
        this.mShareItemList = shareItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public ShareAdapterHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ShareAdapterHolder(View.inflate(context, R.layout.share_custom_item, null));
    }

    public interface OnItemClickListener {
        void onItemClick(ShareItem item);
    }

    private OnItemClickListener onItemClickListener;

    public void addOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull ShareAdapterHolder shareAdapterHolder, int i) {
        final ShareItem shareItem = mShareItemList.get(i);
        shareAdapterHolder.iv_pic.setImageResource(shareItem.resId);
        shareAdapterHolder.tv_desc.setText(shareItem.desc);
        shareAdapterHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onItemClickListener) {
                    onItemClickListener.onItemClick(shareItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mShareItemList == null ? 0 : mShareItemList.size();
    }

    class ShareAdapterHolder extends RecyclerView.ViewHolder {
        ImageView iv_pic;
        TextView tv_desc;

        public ShareAdapterHolder(@NonNull View itemView) {
            super(itemView);
            tv_desc = itemView.findViewById(R.id.tv_desc);

            iv_pic = itemView.findViewById(R.id.iv_pic);
        }
    }
}
