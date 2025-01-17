package com.dc.commonlib.commonentity.video.widget.tipsview;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dc.commonlib.R;

/**
 * 错误提示对话框。出错的时候会显示。
 */
public class ErrorView extends RelativeLayout{
    private static final String TAG = ErrorView.class.getSimpleName();
    //错误信息
    private TextView mMsgView;
    //错误码
    private TextView mCodeView;
    //重试的图片
    private View mRetryView;
    //重试的按钮
    private TextView mRetryBtn;


    private OnRetryClickListener mOnRetryClickListener = null;//重试点击事件

    public ErrorView(Context context) {
        super(context);
        init();
    }


    public ErrorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public ErrorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resources resources = getContext().getResources();

        View view = inflater.inflate(R.layout.view_video_tips, null);

        int viewWidth = resources.getDimensionPixelSize(R.dimen.dp_220);
        int viewHeight = resources.getDimensionPixelSize(R.dimen.dp_120);

        LayoutParams params = new LayoutParams(viewWidth, viewHeight);
        addView(view, params);

        mRetryBtn = (TextView) view.findViewById(R.id.retry_btn);
        mMsgView = (TextView) view.findViewById(R.id.msg);
        mRetryView = view.findViewById(R.id.retry);
        //重试的点击监听
        mRetryView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnRetryClickListener != null) {
                    mOnRetryClickListener.onRetryClick();
                }
            }
        });

    }

    /**
     * 更新提示文字
     * @param errorCode 错误码
     * @param errorEvent 错误事件
     * @param errMsg 错误码
     */
    public void updateTips(int errorCode, int errorEvent, String errMsg) {
        mMsgView.setText(errMsg);
    }

    /**
     * 更新提示文字,不包含错误码
     */
    public void updateTipsWithoutCode(String errMsg){
        mMsgView.setText(errMsg);
        mCodeView.setVisibility(View.GONE);
    }

    /**
     * 重试的点击事件
     */
    public interface OnRetryClickListener {
        /**
         * 重试按钮点击
         */
        void onRetryClick();
    }

    /**
     * 设置重试点击事件
     * @param l 重试的点击事件
     */
    public void setOnRetryClickListener(OnRetryClickListener l) {
        mOnRetryClickListener = l;
    }
}
