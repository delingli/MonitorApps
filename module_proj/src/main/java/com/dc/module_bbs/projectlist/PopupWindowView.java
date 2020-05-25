package com.dc.module_bbs.projectlist;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.dc.commonlib.common.BaseRecyclerAdapter;
import com.dc.commonlib.weiget.courselist.DlPopupWindowAdapter;
import com.dc.commonlib.weiget.horizontalrecycle.DLHorizontalItem;
import com.dc.module_bbs.R;

import java.util.List;

public class PopupWindowView {
    private PopupWindow mPopupWindow;
    DlPopupWindowAdapter mDlPopupWindowAdapter;
    private Context context;

    public PopupWindowView(Context context, int pos) {
        this.context = context;
        RecyclerView recyclerView = (RecyclerView) View.inflate(context, R.layout.dl_popup_window_view, null);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        recyclerView.setAdapter(mDlPopupWindowAdapter = new DlPopupWindowAdapter(context, null, -1));
        mDlPopupWindowAdapter.setSelection(pos);
        mPopupWindow = new PopupWindow(recyclerView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setTouchable(true);
//        mPopupWindow.setAnimationStyle(R.style.share_popup_menu_anim_style);
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
                    mDlPopupWindowAdapter.setSelection(position);
                    if (null != onItemClickListener) {
                        onItemClickListener.onItemCLick(horizontalItem.name,horizontalItem.id);
                    }
                }
            }
        });

        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
            }
        });

    }

    private OnItemClickListener onItemClickListener;

    public void addOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemCLick(String name,String id);
    }

    public void showPopupWindow(View v) {
        if (null != mPopupWindow && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        } else {
            mPopupWindow.showAsDropDown(v, 0, 0);
        }
    }
    public void dismiss(){
        mPopupWindow.dismiss();
    }

    public void fillData(List<DLHorizontalItem> list) {
        if (null != mDlPopupWindowAdapter && null != list) {
            mDlPopupWindowAdapter.addList(list);
        }
    }
}
