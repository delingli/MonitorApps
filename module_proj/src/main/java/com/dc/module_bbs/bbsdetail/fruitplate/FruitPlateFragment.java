package com.dc.module_bbs.bbsdetail.fruitplate;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.dc.commonlib.common.BaseRecyclerAdapter;
import com.dc.module_bbs.R;
import com.dc.module_bbs.bbsdetail.BBSDetailActivity;
import com.dc.module_bbs.bbsdetail.BBsDetails;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class FruitPlateFragment extends BottomSheetDialogFragment {
    private int topOffset = 200;
    private BottomSheetBehavior<FrameLayout> behavior;
    private RecyclerView mRecyclerView;
    private FruitPlateAdapter mFruitPlateAdapter;
    private List<BBsDetails> bBsDetailsList;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getContext() == null) {
            return super.onCreateDialog(savedInstanceState);
        }
        return new BottomSheetDialog(getContext(), R.style.TransparentBottomSheetStyle);
    }

    public static String KEY_FRUITPLATE = "adslmasmnda123378990231";

    public static FruitPlateFragment newInstance(ArrayList<BBsDetails> bBsDetails) {
        FruitPlateFragment fruitPlateFragment = new FruitPlateFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(KEY_FRUITPLATE, bBsDetails);
        fruitPlateFragment.setArguments(bundle);
        return fruitPlateFragment;
    }


    @Override
    public void onStart() {
        super.onStart();
        // 设置软键盘不自动弹出
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
        FrameLayout bottomSheet = dialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet);
        if (bottomSheet != null) {
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomSheet.getLayoutParams();
            layoutParams.height = getHeight();
            behavior = BottomSheetBehavior.from(bottomSheet);
            // 初始为展开状态
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View dialogView = inflater.inflate(R.layout.bbs_detai_fruitplatefragment, container, false);
        return dialogView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SmartRefreshLayout refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableLoadMore(false);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        if (getArguments() != null) {
            bBsDetailsList = getArguments().getParcelableArrayList(KEY_FRUITPLATE);
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (null != bBsDetailsList) {
            mFruitPlateAdapter = new FruitPlateAdapter(getContext(), bBsDetailsList, 0);
            mRecyclerView.setAdapter(mFruitPlateAdapter);
        }
        mFruitPlateAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                if (mFruitPlateAdapter.getList() != null && mFruitPlateAdapter.getList().get(position) != null) {
                    BBsDetails bBsDetails = mFruitPlateAdapter.getList().get(position);
                    BBSDetailActivity.startActivity(getActivity(), bBsDetails.forumid);
                    dismiss();
                    getActivity().finish();
                }

            }
        });
    }

    /**
     * 获取屏幕高度
     *
     * @return height
     */
    private int getHeight() {
        int height = 1920;
        if (getContext() != null) {
            WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            Point point = new Point();
            if (wm != null) {
                // 使用Point已经减去了状态栏高度
                wm.getDefaultDisplay().getSize(point);
                height = point.y - getTopOffset();
            }
        }
        return height;
    }

    public int getTopOffset() {
        return topOffset;
    }

    public void setTopOffset(int topOffset) {
        this.topOffset = topOffset;
    }

    public BottomSheetBehavior<FrameLayout> getBehavior() {
        return behavior;
    }

    public void close() {
        if (getBehavior() != null) {
            getBehavior().setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }

}
