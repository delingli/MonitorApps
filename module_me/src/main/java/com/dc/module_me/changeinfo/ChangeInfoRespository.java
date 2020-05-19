package com.dc.module_me.changeinfo;

import com.dc.baselib.http.newhttp.StrAbsHttpSubscriber;
import com.dc.baselib.mvvm.BaseRespository;
import com.dc.baselib.mvvm.EventUtils;
import com.dc.baselib.utils.ToastUtils;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ChangeInfoRespository extends BaseRespository {
    public String UPDATENICKNAME;
    public String UPDATESIGNATURE;

    public ChangeInfoRespository() {
        this.UPDATENICKNAME = EventUtils.getEventKey();
        this.UPDATESIGNATURE = EventUtils.getEventKey();
    }
    public void updateNickname(String uid ,String nickname) {
        addDisposable(mRetrofit.create(IChangeInfoService.class)
                .updateusernickname(uid,nickname)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new StrAbsHttpSubscriber() {
                    @Override
                    public void onSuccess(String s) {
                        postData(UPDATENICKNAME, s);
                    }

                    @Override
                    public void onFailure(String msg, String code) {
                        ToastUtils.showToast(msg);
                    }
                }));
    }
    public void updateSignature(String uid ,String sightml) {
        addDisposable(mRetrofit.create(IChangeInfoService.class)
                .updateuserinfo(uid,sightml)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new StrAbsHttpSubscriber() {
                    @Override
                    public void onSuccess(String s) {
                        postData(UPDATESIGNATURE, s);
                    }

                    @Override
                    public void onFailure(String msg, String code) {
                        ToastUtils.showToast(msg);
                    }
                }));
    }
}
