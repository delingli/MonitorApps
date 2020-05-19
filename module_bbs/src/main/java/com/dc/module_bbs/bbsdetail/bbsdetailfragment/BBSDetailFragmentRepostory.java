package com.dc.module_bbs.bbsdetail.bbsdetailfragment;

import com.dc.baselib.http.newhttp.AbsHttpSubscriber;
import com.dc.baselib.mvvm.BaseRespository;
import com.dc.baselib.mvvm.EventUtils;
import com.dc.commonlib.utils.LogUtil;
import com.dc.module_bbs.bbsdetail.BBsDetails;
import com.dc.module_bbs.bbsdetail.IBBSDetailService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BBSDetailFragmentRepostory extends BaseRespository {
    public String KEY_NODATAEVENT;
    public String KEY_THEMEINFORUM_DATA;

    public BBSDetailFragmentRepostory() {
        KEY_NODATAEVENT = EventUtils.getEventKey();
        KEY_THEMEINFORUM_DATA = EventUtils.getEventKey();
    }

    public void listLearnRecord(String forumid, int start, int limit, String order, String uid) {
        addDisposable(mRetrofit.create(IBBSDetailFragmentService.class)
                .getThemeInForum(forumid, start, limit, order, uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new AbsHttpSubscriber<List<ThemeInForumItem>>() {


                    @Override
                    public void onSuccess(List<ThemeInForumItem> themeInForumItems) {
                        conversionData(themeInForumItems);

                    }

                    @Override
                    public void onFailure(String msg, String code) {
                        if (Integer.parseInt(code) == -2) {
                            postData(KEY_NODATAEVENT, msg);
                        }
                        LogUtil.e(code + msg);
                    }
                }));
    }

    private void conversionData(List<ThemeInForumItem> themeInForumItems) {
        List<ThemeInForumItemWrapper> themeInForumItemWrappers = new ArrayList<>();
        ThemeInForumItemWrapper themeWrapper;
        for (ThemeInForumItem item : themeInForumItems) {
            themeWrapper = new ThemeInForumItemWrapper();
            themeWrapper.themeInForumItem = item;
            if (item.thread_pics == null || item.thread_pics.isEmpty()) {
                themeWrapper.type = ThemeInForumItemWrapper.TYPE_NO_PIC;
            } else if (item.thread_pics.size() == 1) {
                themeWrapper.type = ThemeInForumItemWrapper.TYPE_ONE_PIC;
            } else {
                themeWrapper.type = ThemeInForumItemWrapper.TYPE_MORE_PIC;
            }
            themeInForumItemWrappers.add(themeWrapper);
        }


        postData(KEY_THEMEINFORUM_DATA, themeInForumItemWrappers);
    }
}
