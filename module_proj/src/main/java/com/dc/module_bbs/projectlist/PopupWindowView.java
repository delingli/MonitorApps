package com.dc.module_bbs.projectlist;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.dc.commonlib.common.BaseRecyclerAdapter;
import com.dc.commonlib.weiget.courselist.DlPopupWindowAdapter;
import com.dc.commonlib.weiget.horizontalrecycle.DLHorizontalItem;
import com.dc.module_bbs.R;

import java.util.List;

public class PopupWindowView {
    private PopupWindow mPopupWindow;
    DlPopupWindowAdapter mDlPopupWindowAdapter;

    public PopupWindowView(Context context) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.common_recycle_view, null, false);
        RecyclerView recyclerView = contentView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        recyclerView.setAdapter(mDlPopupWindowAdapter = new DlPopupWindowAdapter(context, null, -1));
//        mDlPopupWindowAdapter.setSelection(dl_bottom_horizontalRecycleView.getSelectPosition());
        mPopupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setTouchable(true);
//第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
//        mPopupWindow.showAsDropDown(dl_top_horizontalRecycleView, 0, 0);
        // 或者也可以调用此方法显示PopupWindow，其中：
// 第一个参数是PopupWindow的父View，第二个参数是PopupWindow相对父View的位置，
// 第三和第四个参数分别是PopupWindow相对父View的x、y偏移
// window.showAtLocation(parent, gravity, x, y)

        mDlPopupWindowAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                if (mDlPopupWindowAdapter.getList() != null) {
                    DLHorizontalItem horizontalItem = mDlPopupWindowAdapter.getList().get(position);
                    if (null != onItemClickListener) {
                        onItemClickListener.onItemCLick(horizontalItem.id);
                    }
                }
                mPopupWindow.dismiss();
            }
        });
    }

    private OnItemClickListener onItemClickListener;

    public void addOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemCLick(String id);
    }

    public void showPopupWindow(View v) {
        if (null != mPopupWindow && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
        mPopupWindow.showAsDropDown(v, 0, 0);
    }

    public void fillData(List<DLHorizontalItem> list) {
        if (null != mDlPopupWindowAdapter && null != list) {
            mDlPopupWindowAdapter.addList(list);
        }
    }
}
