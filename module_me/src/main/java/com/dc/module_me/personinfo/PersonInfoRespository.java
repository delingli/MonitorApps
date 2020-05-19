package com.dc.module_me.personinfo;

import com.dc.baselib.http.newhttp.AbsHttpSubscriber;
import com.dc.baselib.mvvm.BaseRespository;
import com.dc.baselib.mvvm.EventUtils;
import com.dc.baselib.utils.ToastUtils;
import com.dc.commonlib.commonentity.UserInfo;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PersonInfoRespository extends BaseRespository {

    public String KEY_USER_EVENT = "key_user_event";

    public PersonInfoRespository() {
        KEY_USER_EVENT = EventUtils.getEventKey();
    }

    public void getUserInfo(String uid) {
        addDisposable(mRetrofit.create(IPersonInfoService.class)
                .toGetUserInfo(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new AbsHttpSubscriber<UserInfo>() {

                    @Override
                    public void onSuccess(UserInfo userInfo) {
                        postData(KEY_USER_EVENT, userInfo);
                    }

                    @Override
                    public void onFailure(String msg, String code) {
                        ToastUtils.showToast(msg);
                    }
                }));
    }
}
