package com.dc.module_main.ui.microvideo;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dc.baselib.utils.ToastUtils;
import com.dc.commonlib.utils.GlideUtils;
import com.dc.commonlib.utils.LogUtil;
import com.dc.commonlib.utils.UIUtils;
import com.dc.commonlib.utils.UUIDUtils;
import com.dc.module_main.R;

import org.yczbj.ycvideoplayerlib.constant.ConstantKeys;
import org.yczbj.ycvideoplayerlib.controller.VideoPlayerController;
import org.yczbj.ycvideoplayerlib.manager.VideoPlayerManager;
import org.yczbj.ycvideoplayerlib.player.VideoPlayer;

import java.util.ArrayList;
import java.util.List;

public class MicroVideoAdapterz extends RecyclerView.Adapter<MicroVideoAdapterz.VideoViewHolder> {
    private List<MicroVideos> microVideosList;
    private Context mContext;

    public MicroVideoAdapterz(Context context, List<MicroVideos> microVideosList) {
        this.microVideosList = microVideosList;
        this.mContext = context;
    }

    public void setList(List<MicroVideos> dataList) {
        if (null != dataList) {
            microVideosList = dataList;
            notifyDataSetChanged();
        }

    }

    public void addList(List<MicroVideos> itemlist) {
        if (microVideosList == null) {
            microVideosList = new ArrayList<>();
        }
        if (null != itemlist) {
            microVideosList.addAll(itemlist);
            notifyDataSetChanged();
        }

    }

    @NonNull
    @Override
    public MicroVideoAdapterz.VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.main_item_layouts, parent, false);
        //创建视频播放控制器，主要只要创建一次就可以呢
        VideoViewHolder holder = new VideoViewHolder(itemView);
        VideoPlayerController controller = new VideoPlayerController(mContext);
        holder.setController(controller);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MicroVideoAdapterz.VideoViewHolder viewHolder, int position) {
        final MicroVideos microVideo = microVideosList.get(position);
        if (null != microVideo) {
            GlideUtils.loadCircleUrl(mContext, microVideo.getAvatar(), viewHolder.iv_head);
            GlideUtils.loadUrl(mContext, microVideo.getPic(), viewHolder.mController.imageView());
            viewHolder.tv_userName.setText(microVideo.getUsername());
            viewHolder.getController().addOnCenterStareListen(new VideoPlayerController.OnCenterStareListen() {
                @Override
                public void onCenterClick() {
                    String realPlay = UIUtils.getWeishiUrl(microVideo.getPlay_url(), System.currentTimeMillis() + "", UUIDUtils.createUUid());
                    LogUtil.d("ldl", "'realPlay:" + realPlay);
                    viewHolder.mVideoPlayer.setUp(realPlay, null);
                }
            });
            if (microVideo.getIs_focus() == 0) {
                //0未关注,1已关注
                viewHolder.tv_focus_on.setText(R.string.no_focus_on);
                Drawable drawable = mContext.getResources().getDrawable(R.drawable.add);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                viewHolder.tv_focus_on.setCompoundDrawables(drawable, null, null, null);
                viewHolder.tv_focus_on.setBackgroundResource(R.drawable.bg_login_bg);
            } else if (microVideo.getIs_focus() == 1) {
                //是
                viewHolder.tv_focus_on.setText(R.string.focus_on);
                viewHolder.tv_focus_on.setCompoundDrawables(null, null, null, null);
                viewHolder.tv_focus_on.setBackgroundResource(R.drawable.bg_gray_bg);
            }
            viewHolder.tv_msg.setText(microVideo.getPostnum());
            viewHolder.bindData(microVideo);
            viewHolder.ll_weishi_column.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != onItemClickListener) {
                        onItemClickListener.onItem(FLAG_DETAIL, microVideo);
                    }
                }
            });
            viewHolder.tv_focus_on.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != onItemClickListener) {
                        onItemClickListener.onItem(FLAG_FOCUEON, microVideo);
                    }
                }
            });
            viewHolder.iv_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != onItemClickListener) {
                        onItemClickListener.onItem(FLAG_SHARE, microVideo);
                    }

                }
            });

        }

    }

    public static int FLAG_SHARE = 1;
    public static int FLAG_DETAIL = 2;
    public static int FLAG_FOCUEON = 3;


    public void addOnItemClickListener(MicroVideoAdapterz.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    private MicroVideoAdapterz.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {

        void onItem(int flag, MicroVideos microVideos);
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {


        public VideoPlayerController mController;
        public VideoPlayer mVideoPlayer;
        public ImageView iv_share, iv_head;
        public TextView tv_userName, tv_focus_on, tv_msg;
        public LinearLayout ll_weishi_column;

        VideoViewHolder(View itemView) {
            super(itemView);
            mVideoPlayer = itemView.findViewById(R.id.vplayer);
            iv_head = itemView.findViewById(R.id.iv_head);
            iv_share = itemView.findViewById(R.id.iv_share);
            tv_userName = itemView.findViewById(R.id.tv_userName);
            tv_focus_on = itemView.findViewById(R.id.tv_focus_on);
            tv_msg = itemView.findViewById(R.id.tv_msg);
            ll_weishi_column = itemView.findViewById(R.id.ll_weishi_column);


            // 将列表中的每个视频设置为默认16:9的比例
            ViewGroup.LayoutParams params = mVideoPlayer.getLayoutParams();
            // 宽度为屏幕宽度
            params.width = itemView.getResources().getDisplayMetrics().widthPixels;
            // 高度为宽度的9/16
            params.height = (int) (params.width * 9f / 16f);
            mVideoPlayer.setLayoutParams(params);

            // 将列表中的每个视频设置为默认16:9的比例
//            ViewGroup.LayoutParams params = mVideoPlayer.getLayoutParams();
//            // 宽度为屏幕宽度
//            params.width = itemView.getResources().getDisplayMetrics().widthPixels;
//            // 高度为宽度的9/16
//            params.height = (int) (params.width * 9f / 16f);
//            mVideoPlayer.setLayoutParams(params);
        }

        /**
         * 设置视频控制器参数
         *
         * @param controller 控制器对象
         */
        void setController(VideoPlayerController controller) {
            mController = controller;
            mController.setCenterPlayer(true, 0);
            mController.setLoadingType(ConstantKeys.Loading.LOADING_RING);
            mController.setHideTime(3000);
            mVideoPlayer.setPlayerType(ConstantKeys.IjkPlayerType.TYPE_IJK);
            mVideoPlayer.setController(mController);
            VideoPlayerManager.instance().setCurrentVideoPlayer(mVideoPlayer);
            VideoPlayerManager.instance().resumeVideoPlayer();
        }

        public VideoPlayer getVideoPlayer() {
            return mVideoPlayer;
        }

        public VideoPlayerController getController() {
            return this.mController;
        }

        void bindData(MicroVideos microvideos) {
            mController.setTitle(microvideos.getTitle());
            mController.setLength(microvideos.getLength());
        }
    }

    @Override
    public int getItemCount() {
        return microVideosList == null ? 0 : microVideosList.size();
    }

}
