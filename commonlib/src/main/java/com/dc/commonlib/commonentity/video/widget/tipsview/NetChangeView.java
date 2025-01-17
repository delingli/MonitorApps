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
 * 网络变化提示对话框。当网络由wifi变为4g的时候会显示。
 */
public class NetChangeView extends RelativeLayout {
    //结束播放的按钮
    private TextView mStopPlayBtn;
    //界面上的操作按钮事件监听
    private OnNetChangeClickListener mOnNetChangeClickListener = null;

    public NetChangeView(Context context) {
        super(context);
        init();
    }

    public NetChangeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NetChangeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resources resources = getContext().getResources();

        View view = inflater.inflate(R.layout.view_video_tips_netchange, null);
        int viewWidth = resources.getDimensionPixelSize(R.dimen.dp_280);
        int viewHeight = resources.getDimensionPixelSize(R.dimen.dp_120);

        LayoutParams params = new LayoutParams(viewWidth, viewHeight);
        addView(view, params);

        //继续播放的点击事件
        view.findViewById(R.id.continue_play).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnNetChangeClickListener != null) {
                    mOnNetChangeClickListener.onContinuePlay();
                }
            }
        });

        //停止播放的点击事件
        mStopPlayBtn = (TextView) view.findViewById(R.id.stop_play);
        mStopPlayBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnNetChangeClickListener != null) {
                    mOnNetChangeClickListener.onStopPlay();
                }
            }
        });
    }

    /**
     * 界面中的点击事件
     */
    public interface OnNetChangeClickListener {
        /**
         * 继续播放
         */
        void onContinuePlay();

        /**
         * 停止播放
         */
        void onStopPlay();
    }

    /**
     * 设置界面的点击监听
     *
     * @param l 点击监听
     */
    public void setOnNetChangeClickListener(OnNetChangeClickListener l) {
        mOnNetChangeClickListener = l;
    }

}
