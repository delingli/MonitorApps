package com.dc.module_bbs.bbsmain;

import com.dc.baselib.http.newhttp.AbsHttpSubscriber;
import com.dc.baselib.mvvm.BaseRespository;
import com.dc.baselib.mvvm.EventUtils;
import com.dc.module_bbs.bbsdetail.BBsDetails;
import com.dc.module_bbs.bbsdetail.IBBSDetailService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FocusPlateRespository extends BaseRespository {
    public String KEY_PLATE_FOCUS_LIST;
    public String KEY_NO_PLATE;

    public FocusPlateRespository() {
        this.KEY_PLATE_FOCUS_LIST = EventUtils.getEventKey();
        this.KEY_NO_PLATE = EventUtils.getEventKey();
    }

    public void moduleList() {
        addDisposable(mRetrofit.create(IFocusPlateViewService.class)
                .moduleList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new AbsHttpSubscriber<List<FocusPlateItem>>() {


                    @Override
                    public void onSuccess(List<FocusPlateItem> focusplateitem) {
                        List<FocusPlateItem.ChildBean> childBeanList=new ArrayList<>();
                        for(FocusPlateItem focusPlateItem:focusplateitem){
                            childBeanList.addAll(focusPlateItem.child);
                        }
                        postData(KEY_PLATE_FOCUS_LIST, childBeanList);

                    }

                    @Override
                    public void onFailure(String msg, String code) {
                        if (Integer.parseInt(code) == -2) {
                            postData(KEY_NO_PLATE, msg);
                        }
                    }
                }));
    }

    public void moduleList(String uid) {
        addDisposable(mRetrofit.create(IFocusPlateViewService.class)
                .userModuleList(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new AbsHttpSubscriber<List<FocusPlateItem>>() {


                    @Override
                    public void onSuccess(List<FocusPlateItem> focusplateitem) {
                        postData(KEY_PLATE_FOCUS_LIST, focusplateitem);

                    }

                    @Override
                    public void onFailure(String msg, String code) {
                        if (Integer.parseInt(code) == -2) {
                            postData(KEY_NO_PLATE, msg);
                        }
                    }
                }));
    }

}
