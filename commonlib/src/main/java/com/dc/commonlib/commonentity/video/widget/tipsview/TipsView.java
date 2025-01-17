package com.dc.commonlib.commonentity.video.widget.tipsview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


/**
 * 提示对话框的管理器。
 * 用于管理{@link ErrorView} ，{@link NetChangeView} , {@link ReplayView}等view的显示/隐藏等。
 */

public class TipsView extends RelativeLayout {

    private static final String TAG = TipsView.class.getSimpleName();
    //错误码
    private int mErrorCode;
    //错误提示
    private ErrorView mErrorView = null;
    //重试提示
    private ReplayView mReplayView = null;

    //网络变化提示
    private NetChangeView mNetChangeView = null;

    //提示点击事件
    private OnTipClickListener mOnTipClickListener = null;

    //网络变化监听事件。
    private NetChangeView.OnNetChangeClickListener onNetChangeClickListener = new NetChangeView.OnNetChangeClickListener() {
        @Override
        public void onContinuePlay() {
            if (mOnTipClickListener != null) {
                mOnTipClickListener.onContinuePlay();
            }
        }

        @Override
        public void onStopPlay() {
            if (mOnTipClickListener != null) {
                mOnTipClickListener.onStopPlay();
            }
        }
    };
    //错误提示的重试点击事件
    private ErrorView.OnRetryClickListener onRetryClickListener = new ErrorView.OnRetryClickListener() {
        @Override
        public void onRetryClick() {
            if (mOnTipClickListener != null) {
                mOnTipClickListener.onRetryPlay();
            }
        }
    };

    //重播点击事件
    private ReplayView.OnReplayClickListener onReplayClickListener = new ReplayView.OnReplayClickListener() {
        @Override
        public void onReplay() {
            if (mOnTipClickListener != null) {
                mOnTipClickListener.onReplay();
            }
        }
    };
    private LoadingView mLoadingView;

    public TipsView(Context context) {
        super(context);
    }

    public TipsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TipsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * 显示网络变化提示
     */
    public void showNetChangeTipView() {
        if (mNetChangeView == null) {
            mNetChangeView = new NetChangeView(getContext());
            mNetChangeView.setOnNetChangeClickListener(onNetChangeClickListener);
            addSubView(mNetChangeView);
        }

        if (mErrorView != null && mErrorView.getVisibility() == VISIBLE) {
            //显示错误对话框了，那么网络切换的对话框就不显示了。
            //都出错了，还显示网络切换，没有意义
        } else {
            mNetChangeView.setVisibility(VISIBLE);
        }

    }

    /**
     * 显示错误提示
     *
     * @param errorCode  错误码
     * @param errorEvent 错误事件
     * @param errorMsg   错误消息
     */
    public void showErrorTipView(int errorCode, int errorEvent, String errorMsg) {
        if (mErrorView == null) {
            mErrorView = new ErrorView(getContext());
            mErrorView.setOnRetryClickListener(onRetryClickListener);
            addSubView(mErrorView);
        }

        //出现错误了，先把网络的对话框关闭掉。防止同时显示多个对话框。
        //都出错了，还显示网络切换，没有意义
        hideNetChangeTipView();

        mErrorCode = errorCode;
        mErrorView.updateTips(errorCode, errorEvent, errorMsg);
        mErrorView.setVisibility(VISIBLE);


        Log.d(TAG, " errorCode = " + mErrorCode);
    }

    /**
     * 显示错误提示,不显示错误码
     * @param msg   错误信息
     */
    public void showErrorTipViewWithoutCode(String msg){
        if (mErrorView == null) {
            mErrorView = new ErrorView(getContext());
            mErrorView.updateTipsWithoutCode(msg);
            mErrorView.setOnRetryClickListener(onRetryClickListener);
            addSubView(mErrorView);
        }

        if (mErrorView.getVisibility() != VISIBLE) {
            mErrorView.setVisibility(VISIBLE);
        }
    }

    /**
     * 显示重播view
     */
    public void showReplayTipView() {
        if (mReplayView == null) {
            mReplayView = new ReplayView(getContext());
            mReplayView.setOnReplayClickListener(onReplayClickListener);
            addSubView(mReplayView);
        }

        if (mReplayView.getVisibility() != VISIBLE) {
            mReplayView.setVisibility(VISIBLE);
        }
    }



    /**
     * 显示网络加载view
     */
    public void showLoadingTipView() {
        if (mLoadingView == null) {
            mLoadingView = new LoadingView(getContext());
            mLoadingView.setOnlyLoading();
            addSubView(mLoadingView);
        }

        if (mLoadingView.getVisibility() != VISIBLE) {
            mLoadingView.setVisibility(VISIBLE);
        }
    }


    /**
     * 把新增的view添加进来，居中添加
     *
     * @param subView 子view
     */
    public void addSubView(View subView) {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(CENTER_IN_PARENT);
        addView(subView, params);
    }


    /**
     * 隐藏所有的tip
     */
    public void hideAll() {
        hideNetChangeTipView();
        hideErrorTipView();
        hideReplayTipView();
//        hideNetLoadingTipView();
    }

//    /**
//     * 隐藏缓冲加载的tip
//     */
//    public void hideBufferLoadingTipView() {
//        if (mBufferLoadingView != null && mBufferLoadingView.getVisibility() == VISIBLE) {
//            mBufferLoadingView.setVisibility(INVISIBLE);
//        }
//    }

//    /**
//     * 隐藏网络加载的tip
//     */
//    public void hideNetLoadingTipView() {
//        if (mLoadingView != null && mLoadingView.getVisibility() == VISIBLE) {
//            mLoadingView.setVisibility(INVISIBLE);
//        }
//    }

    /**
     * 隐藏重播的tip
     */
    public void hideReplayTipView() {
        if (mReplayView != null && mReplayView.getVisibility() == VISIBLE) {
            mReplayView.setVisibility(INVISIBLE);
        }
    }

    /**
     * 隐藏网络变化的tip
     */
    public void hideNetChangeTipView() {
        if (mNetChangeView != null && mNetChangeView.getVisibility() == VISIBLE) {
            mNetChangeView.setVisibility(INVISIBLE);
        }
    }

    /**
     * 隐藏错误的tip
     */
    public void hideErrorTipView() {
        if (mErrorView != null && mErrorView.getVisibility() == VISIBLE) {
            mErrorView.setVisibility(INVISIBLE);
        }
    }

    /**
     * 错误的tip是否在显示，如果在显示的话，其他的tip就不提示了。
     *
     * @return true：是
     */
    public boolean isErrorShow() {
        if (mErrorView != null) {
            return mErrorView.getVisibility() == VISIBLE;
        } else {
            return false;
        }
    }

    /**
     * 隐藏网络错误tip
     */
    public void hideNetErrorTipView() {
//        VcPlayerLog.d(TAG, " hideNetErrorTipView errorCode = " + mErrorCode);
//        if (mErrorView != null && mErrorView.getVisibility() == VISIBLE
//                && mErrorCode == AliyunErrorCode.ALIVC_ERROR_LOADING_TIMEOUT.getCode()) {
//            mErrorView.setVisibility(INVISIBLE);
//        }
    }



    /**
     * 提示view中的点击操作
     */
    public interface OnTipClickListener {
        /**
         * 继续播放
         */
        void onContinuePlay();

        /**
         * 停止播放
         */
        void onStopPlay();

        /**
         * 重试播放
         */
        void onRetryPlay();

        /**
         * 重播
         */
        void onReplay();
    }

    /**
     * 设置提示view中的点击操作 监听
     *
     * @param l 监听事件
     */
    public void setOnTipClickListener(OnTipClickListener l) {
        mOnTipClickListener = l;
    }
}
