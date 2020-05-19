package com.dc.module_main.ui.video;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dc.baselib.mvvm.AbsLifecycleFragment;
import com.dc.baselib.mvvm.BaseFragment;
import com.dc.baselib.utils.ToastUtils;
import com.dc.baselib.utils.UserManager;
import com.dc.commonlib.common.ConfigUtils;
import com.dc.commonlib.common.CourseItemTitle;
import com.dc.commonlib.utils.ActivityUtils;
import com.dc.commonlib.utils.ArounterManager;
import com.dc.commonlib.weiget.horizontalrecycle.DLHorizontalItem;
import com.dc.commonlib.weiget.horizontalrecycle.DLHorizontalRecycleView;
import com.dc.module_main.R;
import com.dc.module_main.login.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class VideoFragment extends AbsLifecycleFragment<VideoViewModels> implements View.OnClickListener {

    private boolean canClick = false;
    private FrameLayout fl_sign;
    private TextView tv_sign_score;
    private ImageView iv_record;
    private DLHorizontalRecycleView mDlHorizontalRecycleView;


    @Override
    public void initView(View view) {
        super.initView(view);
        fl_sign = view.findViewById(R.id.fl_sign);
        iv_record = view.findViewById(R.id.iv_record);
        tv_sign_score = view.findViewById(R.id.tv_sign_score);
        mDlHorizontalRecycleView = view.findViewById(R.id.dlHorizontalRecycleView);
        fl_sign.setOnClickListener(this);
        iv_record.setOnClickListener(this);
        getChildFragmentManager().beginTransaction().replace(R.id.fl_continer, (BaseFragment) ARouter.getInstance().build(ArounterManager.RECOMMENDED_URL).navigation()).commit();
//        String[] titles = UIUtils.getStringArray(getContext(), R.array.nest_course_tab_items);
        mViewModel.getSignStatus(UserManager.getInstance().getUserId());
        mViewModel.categorys();
        mDlHorizontalRecycleView.addOnDLHorizontalRecycleViewListener(new DLHorizontalRecycleView.OnDLHorizontalRecycleViewListener() {
            @Override
            public void onItemClick(int pos, String cid) {
                ARouter.getInstance().build(ArounterManager.COURSELISTACTIVITY_URL)
                        .withString(ConfigUtils.CID, cid)
                        .withInt(ConfigUtils.BOTTOMPOSITION,pos).navigation();

            }
        });

    }

    private List<DLHorizontalItem> conversionData(List<CourseItemTitle> courseitemtitle) {
        List<DLHorizontalItem> dlList = new ArrayList<>();
        for (CourseItemTitle item : courseitemtitle) {
            dlList.add(new DLHorizontalItem(item.name, item.id, item.code));
        }
        return dlList;
    }
    @Override
    public void dataObserver() {
        registerSubscriber(mViewModel.mRepository.KEY_TITLE_LIST, List.class).observe(this, new Observer<List>() {
            @Override
            public void onChanged(@Nullable List list) {
//                List<CourseItemTitle>

                mDlHorizontalRecycleView.fillData(conversionData(list), 0);
            }
        });
        registerSubscriber(mViewModel.mRepository.KEY_SIGN_EVENT, String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String str) {
                tv_sign_score.setVisibility(View.GONE);
                ToastUtils.showToast(getResources().getString(R.string.sign_add_sucess));
            }
        });
        registerSubscriber(mViewModel.mRepository.KEY_SIGNSTATE_EVENT, String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String data) {
                //data 是0 未签到 data 是1 已签到
                if (data.equals("0")) {
                    tv_sign_score.setText(ConfigUtils.SCORE);
                    tv_sign_score.setVisibility(View.VISIBLE);
                } else {
                    tv_sign_score.setVisibility(View.GONE);

                }
                canClick = !canClick;


            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayout() {
        return R.layout.main_video_fragment;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fl_sign) {
            if (canClick) {
                mViewModel.signIn(UserManager.getInstance().getUserId());
            }
        } else if (v.getId() == R.id.iv_record) {
            if(!UserManager.getInstance().isLogin()){
                LoginActivity.startActivity(getActivity());
            }else {
                ARouter.getInstance().build(ArounterManager.MYRECORDACTIVITY_URL).navigation();
            }

        }
    }
}
