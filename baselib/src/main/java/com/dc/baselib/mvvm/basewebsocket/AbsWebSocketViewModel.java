package com.dc.baselib.mvvm.basewebsocket;

import android.app.Application;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.dc.baselib.livedata.LiveBus;
import com.dc.baselib.mvvm.AbsViewModel;
import com.dc.baselib.mvvm.BaseRespository;
import com.dc.baselib.utils.ToastUtils;
import com.dc.baselib.utils.UserManager;
import com.zld.websocket.SocketListener;
import com.zld.websocket.WebSocketHandler;
import com.zld.websocket.response.ErrorResponse;
import com.zld.websocket.util.WSLogUtil;

import org.java_websocket.framing.Framedata;
import org.json.JSONObject;

import java.nio.ByteBuffer;

import static android.support.constraint.Constraints.TAG;

/**
 * 封装抽象WebSocketViewModel Socket监听实现，抽离View层
 * Respository 必要时分担ViewModel的压力处理数据逻辑，
 * todo  由于历史遗留问题，后端数据格式不统一，返回原始数据手动解析以防止JSonException导致崩溃
 */
public abstract class AbsWebSocketViewModel<T extends BaseRespository> extends AbsViewModel<T> implements SocketListener {

    public AbsWebSocketViewModel(@NonNull Application application) {
        super(application);
        WebSocketHandler.getDefault().addListener(this);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        WebSocketHandler.getDefault().removeListener(this);
    }

    protected void postData(Object eventKey, String tag, Object t) {
        LiveBus.getDefault().postEvent(eventKey, tag, t);
    }

    protected void postData(Object eventKey, Object t) {
        postData(eventKey, null, t);
    }

    @Override
    public void onConnected() {
        Log.i("==onConnected", "onConnected");
    }

    @Override
    public void onConnectFailed(Throwable e) {
        Log.i("==onConnectFailed", e.toString());
    }

    @Override
    public void onDisconnect() {
        Log.i("==onDisconnect", "onDisconnect");
    }

    @Override
    public <T> void onMessage(ByteBuffer bytes, T data) {

    }

    @Override
    public <T> void onMessage(String message, T realdata) {

        try {
            JSONObject object = new JSONObject(message);
            String msg = object.optString("msg");
            int code = object.optInt("code");
            JSONObject commandObj = object.optJSONObject("command");
            String path = null;
            if (null != commandObj) {
                path = commandObj.optString("path");

            }
            if (!TextUtils.isEmpty(path)) {
                if (code == 2101) {//   todo   清掉本地登陆信息  2101: '对象未登录',
                    UserManager.getInstance().clearUser(getApplication());
                } else if (code >= 1000 && code < 2000) {
                    onProcessedMessage(path, message);
                } else {
                    onErrorMessage(path, msg);
                }

            } else {
                onErrorMessage(path, "path路径为空");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 处理完 WebSocket 消息后的回调
     *
     * @param path 响应地址
     * @param data 响应数据
     */
    protected abstract void onProcessedMessage(String path, String realData);

    protected abstract void onErrorMessage(String path, String mes);

    @Override
    public void onPing(Framedata framedata) {

    }

    @Override
    public void onPong(Framedata framedata) {

    }
}
