package com.dc.module_main.regist;

import com.dc.baselib.http.newhttp.AbsHttpSubscriber;
import com.dc.baselib.http.newhttp.StrAbsHttpSubscriber;
import com.dc.baselib.mvvm.BaseRespository;
import com.dc.baselib.mvvm.EventUtils;
import com.dc.baselib.utils.ToastUtils;
import com.dc.commonlib.utils.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RegistRepository extends BaseRespository {
    public String EVENT_REGIST_KEY;
    public String EVENT_VERIFICATIONCODE_KEY;

    public RegistRepository() {
        this.EVENT_REGIST_KEY = EventUtils.getEventKey();
        this.EVENT_VERIFICATIONCODE_KEY = EventUtils.getEventKey();
    }

    private RequestBody toJson(RegistOption registOption) {
        String json = JsonUtil.toJson(registOption);
        return RequestBody.create(MediaType.parse("application/json;charset=utf-8"), json);
    }


    /*   public void regist(Map<String, String> registOption) {
           addDisposable(mRetrofit.create(IRegisterService.class)
                   .register(registOption)
                   .subscribeOn(Schedulers.io())
                   .observeOn(AndroidSchedulers.mainThread())
                   .subscribeWith(new AbsHttpSubscriber<RegistResponse>() {

                       @Override
                       public void onSuccess(RegistResponse rep) {
                           postData(EVENT_REGIST_KEY, rep);
                       }

                       @Override
                       public void onFailure(String msg, String code) {
                           ToastUtils.showToast(msg);
                       }
                   }));

       }*/
    public void regist(Map<String, String> registOption) {
        addDisposable(mRetrofit.create(IRegisterService.class)
                .register(registOption)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new StrAbsHttpSubscriber(true) {


                    @Override
                    public void onSuccess(String s) {
                        try {
                            JSONObject obj = new JSONObject(s);
                            int code = obj.optInt("code");
                            String msg = obj.optString("msg");
                            String data = obj.optString("data");
                            if (code == 0) {
                                RegistResponse rep = JsonUtil.fromJson(data, RegistResponse.class);
                                postData(EVENT_REGIST_KEY, rep);

                            } else {
                                ToastUtils.showToast(msg);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(String msg, String code) {
                        ToastUtils.showToast(msg);

                    }
                }));

    }

    public void toGetVerificationCode(String email, int type,String
            client) {
        addDisposable(mRetrofit.create(IRegisterService.class)
                .toGetVerificationCode(email,client, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new StrAbsHttpSubscriber() {
                    @Override
                    public void onSuccess(String s) {
                        ToastUtils.showToast(s);
                    }

                    @Override
                    public void onFailure(String msg, String code) {
                        ToastUtils.showToast(msg);

                    }
                }));

    }

}
