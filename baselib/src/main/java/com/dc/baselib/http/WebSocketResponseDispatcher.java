package com.dc.baselib.http;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.dc.baselib.BaseApplication;
import com.dc.baselib.mvvm.basewebsocket.CommonSocketEntity;
import com.dc.baselib.utils.JsonUtil;
import com.dc.baselib.utils.ToastUtils;
import com.dc.baselib.utils.UserManager;
import com.google.gson.reflect.TypeToken;
import com.zld.websocket.dispatcher.IResponseDispatcher;
import com.zld.websocket.dispatcher.ResponseDelivery;
import com.zld.websocket.response.ErrorResponse;
import com.zld.websocket.response.Response;
import com.zld.websocket.response.ResponseFactory;
import com.zld.websocket.util.WSLogUtil;

import org.greenrobot.eventbus.EventBus;
import org.java_websocket.framing.Framedata;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.nio.ByteBuffer;

/**
 * Created by ZhangKe on 2018/6/28.
 */
public class WebSocketResponseDispatcher implements IResponseDispatcher {

    private static final String TAG = "ResponseDispatcher";

    /**
     * JSON 数据格式错误
     */
    private static final int JSON_ERROR = 11;
    /**
     * code 码错误
     */
    private static final int CODE_ERROR = 12;

    @Override
    public void onConnected(ResponseDelivery delivery) {
        delivery.onConnected();
    }

    @Override
    public void onConnectFailed(Throwable cause, ResponseDelivery delivery) {
        delivery.onConnectFailed(cause);
    }

    @Override
    public void onDisconnect(ResponseDelivery delivery) {
        delivery.onDisconnect();
    }

    @Override
    public void onMessage(ByteBuffer byteBuffer, ResponseDelivery delivery) {
        delivery.onMessage(byteBuffer, null);
    }

    @Override
    public void onPing(Framedata framedata, ResponseDelivery delivery) {
        delivery.onPing(framedata);
    }

    @Override
    public void onPong(Framedata framedata, ResponseDelivery delivery) {
        delivery.onPong(framedata);
    }

    @Override
    public void onMessage(String message, ResponseDelivery delivery) {
        try {
            //TODO 前端不相信后端，后端数据格式太乱各接口格式不统一，so 原样返回手动解析
            //统一处理成功的 发射数据
            delivery.onMessage(message, message);
            /*            if (response.getCode() >= 1000 && response.getCode() < 2000) {
             *//*          if (null != response.getCommand() && !TextUtils.isEmpty(response.getCommand().getPath())) {
                    String path = response.getCommand().getPath();
                } else {
                    ErrorResponse errorResponse = ResponseFactory.createErrorResponse();
                    errorResponse.setErrorCode(CODE_ERROR);
                    Response<String> textResponse = ResponseFactory.createTextResponse();
                    textResponse.setResponseData(message);
                    errorResponse.setResponseData(textResponse);
                    errorResponse.setDescription(response.getMsg());
                    errorResponse.setReserved(response);
                    onSendDataError(errorResponse, delivery);
                }*//*
            } else {

            }*/
        } catch (Exception e) {
            ErrorResponse errorResponse = ResponseFactory.createErrorResponse();
            Response<String> textResponse = ResponseFactory.createTextResponse();
            textResponse.setResponseData(message);
            errorResponse.setResponseData(textResponse);
            errorResponse.setErrorCode(JSON_ERROR);
            errorResponse.setCause(e);
            onSendDataError(errorResponse, delivery);
        }
    }

    @Override
    public void onSendDataError(ErrorResponse error, ResponseDelivery delivery) {
        switch (error.getErrorCode()) {
            case ErrorResponse.ERROR_NO_CONNECT:
                error.setDescription("网络错误");
                break;
            case ErrorResponse.ERROR_UN_INIT:
                error.setDescription("连接未初始化");
                break;
            case ErrorResponse.ERROR_UNKNOWN:
                error.setDescription("未知错误");
                break;
            case JSON_ERROR:
                error.setDescription("数据格式异常");
                break;
        }
        delivery.onSendDataError(error);
    }

}
