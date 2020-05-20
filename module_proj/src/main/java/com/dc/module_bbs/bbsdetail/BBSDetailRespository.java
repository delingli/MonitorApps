package com.dc.module_bbs.bbsdetail;

import com.dc.baselib.http.newhttp.AbsHttpSubscriber;
import com.dc.baselib.http.newhttp.StrAbsHttpSubscriber;
import com.dc.baselib.mvvm.BaseRespository;
import com.dc.baselib.mvvm.EventUtils;
import com.dc.baselib.utils.ToastUtils;
import com.dc.commonlib.utils.LogUtil;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BBSDetailRespository extends BaseRespository {
    public String KEY_NODATAEVENT, KEY_BBS_DETAIL, KEY_NO_PLATE,KEY_PLATE_LIST;

    public BBSDetailRespository() {
        this.KEY_NODATAEVENT = EventUtils.getEventKey();
        this.KEY_BBS_DETAIL = EventUtils.getEventKey();
        this.KEY_NO_PLATE = EventUtils.getEventKey();
        this.KEY_PLATE_LIST = EventUtils.getEventKey();
    }

    public void submoduleList(String fid) {
        addDisposable(mRetrofit.create(IBBSDetailService.class)
                .submoduleList(fid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new AbsHttpSubscriber<List<BBsDetails>>() {


                    @Override
                    public void onSuccess(List<BBsDetails> bbsdetails) {
                        postData(KEY_PLATE_LIST, bbsdetails);

                    }

                    @Override
                    public void onFailure(String msg, String code) {
                        if (Integer.parseInt(code) == -2) {
                            postData(KEY_NO_PLATE, msg);
                        }

                    }
                }));
    }

    public void followMember(String uid, String fid, int state) {
        addDisposable(mRetrofit.create(IBBSDetailService.class)
                .userPlateOneModule(uid, fid, state)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new StrAbsHttpSubscriber() {


                    @Override
                    public void onSuccess(String s) {
//                        postData(KEY_FOLLOWMEMBER, s);
                        ToastUtils.showToast(s);
                    }

                    @Override
                    public void onFailure(String msg, String code) {
                        ToastUtils.showToast(msg);

                    }
                }));
    }

    public void listLearnRecord(String fuid, String uid) {
        addDisposable(mRetrofit.create(IBBSDetailService.class)
                .listLearnRecord(fuid, uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new AbsHttpSubscriber<BBsDetails>() {


                    @Override
                    public void onSuccess(BBsDetails bBsDetails) {
                        postData(KEY_BBS_DETAIL, bBsDetails);
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
}
