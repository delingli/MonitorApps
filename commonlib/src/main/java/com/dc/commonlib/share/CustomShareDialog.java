package com.dc.commonlib.share;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.dc.baselib.utils.ToastUtils;
import com.dc.commonlib.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomShareDialog extends DialogFragment {
    private boolean showpromisecer = false;
    private boolean onlyshare = false;
    private boolean showWithdraw = false;//显示撤回
    private boolean showDelete = false;
    private boolean showEdite = false;//显示编辑
    private boolean showReport = false;
    private boolean notWatch = false;//不看吃人
    private boolean showDownload = false;//不看吃人
    private boolean showValidation = false;//显示验证
    private String pictitle;

    public boolean isShowDownload() {
        return showDownload;
    }

    public boolean isShowValidation() {
        return showValidation;
    }

    public void setShowValidation(boolean showValidation) {
        this.showValidation = showValidation;
    }

    public void setShowDownload(boolean showDownload) {
        this.showDownload = showDownload;
    }

    public boolean isShowReport() {
        return showReport;
    }

    public void setShowReport(boolean showReport) {
        this.showReport = showReport;
    }

    public boolean isNotWatch() {
        return notWatch;
    }

    public void setNotWatch(boolean notWatch) {
        this.notWatch = notWatch;
    }

    public boolean isShowWithdraw() {
        return showWithdraw;
    }

    public boolean isShowEdite() {
        return showEdite;
    }

    public void setShowEdite(boolean showEdite) {
        this.showEdite = showEdite;
    }

    public boolean isShowDelete() {
        return showDelete;
    }

    public void setShowDelete(boolean showDelete) {
        this.showDelete = showDelete;
    }

    public void setShowWithdraw(boolean showWithdraw) {
        this.showWithdraw = showWithdraw;
    }

    public void setOnlyshare(boolean onlyshare) {
        this.onlyshare = onlyshare;
    }

    public void setShowpromisecer(boolean showpromisecer) {
        this.showpromisecer = showpromisecer;
    }

    public static CustomShareDialog getShareDialog(String title, String picUrl, String goUrl) {
        CustomShareDialog shareDialog = new CustomShareDialog();
        Bundle bundle = new Bundle();

        if (!TextUtils.isEmpty(title)) {
            bundle.putString(TITLE_KEY, title);
        }
        if (!TextUtils.isEmpty(picUrl)) {
            bundle.putString(PIC_URL, picUrl);
        }
        if (!TextUtils.isEmpty(goUrl)) {
            bundle.putString(URL_KEY, goUrl);
        }
        shareDialog.setArguments(bundle);
        return shareDialog;
    }


    public static String URL_KEY = "url_key";
    public static String TITLE_KEY = "title_key";
    public static String PIC_URL = "pic_key";

    private RecyclerView mRecycleView;
    private ShareAdapter mShareAdapter;


    private String url;
    private String title;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.common_share_dialog_share, null);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        return view;
    }

    private List<ShareItem> mShareItemList;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecycleView = view.findViewById(R.id.recycleView);
        mRecycleView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        Bundle bundle = getArguments();
        if (bundle == null) {
            throw new IllegalArgumentException("Please enter the item content");
        }
        initData();
        mRecycleView.setAdapter(mShareAdapter = new ShareAdapter(getContext(), mShareItemList));
        title = bundle.getString(TITLE_KEY);
        pictitle = bundle.getString(PIC_URL);
        url = bundle.getString(URL_KEY);
        mShareAdapter.addOnItemClickListener(new ShareAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ShareItem item) {
                UMImage umImage = new UMImage(getContext(), pictitle);
                UMWeb web = new UMWeb(url);
                web.setTitle(title);//标题
                web.setThumb(umImage);  //缩略图
//                        web.setDescription("my description");//描述
                switch (item.tag) {
                    case ShareItem.TAG_WEICHAR:
                        if (null != onShareItemClickListener) {
                            onShareItemClickListener.onShareItem(ShareItem.TAG_WEICHAR);
                        }
                        new ShareAction(getActivity())
                                .setPlatform(SHARE_MEDIA.WEIXIN)//传入平台
                                .withMedia(web)
                                .setCallback(mShareListener)//回调监听器
                                .share();
                        dismiss();
                        break;
                    case ShareItem.TAG_WEICHAR_FRIEND:
                        if (null != onShareItemClickListener) {
                            onShareItemClickListener.onShareItem(ShareItem.TAG_WEICHAR_FRIEND);
                        }
                        new ShareAction(getActivity())
                                .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)//传入平台
                                .withMedia(web)
                                .setCallback(mShareListener)//回调监听器
                                .share();
                        dismiss();
                        break;
                    case ShareItem.TAG_QQ:
                        if (null != onShareItemClickListener) {
                            onShareItemClickListener.onShareItem(ShareItem.TAG_QQ);
                        }
                        new ShareAction(getActivity())
                                .setPlatform(SHARE_MEDIA.QQ)//传入平台
                                .withMedia(web)
                                .setCallback(mShareListener)//回调监听器
                                .share();
                        dismiss();
                        break;
                    case ShareItem.TAG_QQ_FRIEND:
                        if (null != onShareItemClickListener) {
                            onShareItemClickListener.onShareItem(ShareItem.TAG_QQ_FRIEND);
                        }
                        new ShareAction(getActivity())
                                .setPlatform(SHARE_MEDIA.QZONE)//传入平台
                                .withMedia(web)
                                .setCallback(mShareListener)//回调监听器
                                .share();
                        dismiss();
                        break;
                    case ShareItem.TAG_GIVE_LIKE:
                        if (null != onShareItemClickListener) {
                            onShareItemClickListener.onShareItem(ShareItem.TAG_COLLECTION);
                        }
                        dismiss();
                        break;
                    case ShareItem.TAG_COLLECTION:
                        if (null != onShareItemClickListener) {
                            onShareItemClickListener.onShareItem(ShareItem.TAG_COLLECTION);
                        }
                        dismiss();
                        break;
                    case ShareItem.TAG_CANCEL_:
                        if (null != onShareItemClickListener) {
                            onShareItemClickListener.onShareItem(ShareItem.TAG_CANCEL_);
                        }
                        dismiss();
                        break;

                    default:
                        break;

                }

            }
        });


    }


    private UMShareListener mShareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            ToastUtils.showToast("成功 ");
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtils.showToast("失败");

        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtils.showToast("取消");
        }
    };

    private OnShareItemClickListener onShareItemClickListener;

    public void addOnOnShareItemClickListener(OnShareItemClickListener onShareItemClickListener) {
        this.onShareItemClickListener = onShareItemClickListener;
    }

    public interface OnShareItemClickListener {
        void onShareItem(int tag);
    }

    /**
     * 复制文本到系统剪切板
     */
    public static void copyTextToSystem(Context context, String text) {
        if (!TextUtils.isEmpty(text)) {
            ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData mClipData = ClipData.newPlainText("Label", text);
            cm.setPrimaryClip(mClipData);
            Toast.makeText(context, "复制成功", Toast.LENGTH_LONG).show();
        }

    }

    private void initData() {
        mShareItemList = new ArrayList<>();
        ShareItem wecharshareItem = new ShareItem();
        wecharshareItem.resId = R.drawable.share_wechar;
        wecharshareItem.desc = getContext().getResources().getString(R.string.wechar);
        wecharshareItem.tag = ShareItem.TAG_WEICHAR;
        mShareItemList.add(wecharshareItem);

        ShareItem wecharfriendshareItem = new ShareItem();
        wecharfriendshareItem.resId = R.drawable.share_wechar_friend;
        wecharfriendshareItem.desc = getContext().getResources().getString(R.string.friends);
        wecharfriendshareItem.tag = ShareItem.TAG_WEICHAR_FRIEND;
        mShareItemList.add(wecharfriendshareItem);

        ShareItem qq = new ShareItem();
        qq.resId = R.drawable.share_qq;
        qq.desc = getContext().getResources().getString(R.string.qq);
        qq.tag = ShareItem.TAG_QQ;
        mShareItemList.add(qq);

        ShareItem qzone = new ShareItem();
        qzone.resId = R.drawable.share_space;
        qzone.desc = getContext().getResources().getString(R.string.qq_space);
        qzone.tag = ShareItem.TAG_QQ_FRIEND;
        mShareItemList.add(qzone);

        ShareItem givelike = new ShareItem();
        givelike.resId = R.drawable.give_like;
        givelike.desc = getContext().getResources().getString(R.string.give_like);
        givelike.tag = ShareItem.TAG_GIVE_LIKE;
        mShareItemList.add(givelike);

        ShareItem collection = new ShareItem();
        givelike.resId = R.drawable.collection;
        givelike.desc = getContext().getResources().getString(R.string.collection);
        givelike.tag = ShareItem.TAG_GIVE_LIKE;
        mShareItemList.add(collection);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initParams();
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(title)) {
            dismiss();
        }
    }


    private void initParams() {
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setWindowAnimations(R.style.share_popup_menu_anim_style);
            window.setGravity(Gravity.BOTTOM);
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(null);
        }
        setCancelable(true);
    }





/*    private void share(SHARE_TYPE type) {
        if (!mThirdPartyPlatformInit.getIWXAPI().isWXAppInstalled()) {
            Toast.makeText(getContext(), "您还未安装微信客户端", Toast.LENGTH_SHORT).show();
            return;
        }
        WXWebpageObject webpageObject = new WXWebpageObject();
        webpageObject.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(webpageObject);
        msg.title = title;
        //msg.description = description;
        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        msg.thumbData = bmpToByteArray(thumb, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("Req");
        req.message = msg;
        switch (type) {
            case Type_WXSceneSession:
                req.scene = SendMessageToWX.Req.WXSceneSession;
                break;
            case Type_WXSceneTimeline:
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                break;
        }
        ThirdPartyPlatformInit.getInstance().getIWXAPI().sendReq(req);
    }*/

    private String buildTransaction(String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    private byte[] bmpToByteArray(Bitmap bmp, boolean needRecycle) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        if (needRecycle) {
            bmp.recycle();
        }
        byte[] result = outputStream.toByteArray();
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


}
