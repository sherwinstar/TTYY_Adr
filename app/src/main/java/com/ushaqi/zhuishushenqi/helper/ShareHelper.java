package com.ushaqi.zhuishushenqi.helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;

import androidx.core.content.FileProvider;

import com.ushaqi.zhuishushenqi.plugin.social.SocialApiClient;
import com.ushaqi.zhuishushenqi.plugin.social.api.PlatformActionListener;
import com.ushaqi.zhuishushenqi.plugin.social.api.SocialPlatform;
import com.ushaqi.zhuishushenqi.plugin.social.shareparam.ShareParam;
import com.ushaqi.zhuishushenqi.plugin.social.shareparam.WXShareParam;
import com.ushaqi.zhuishushenqi.plugin.social.wechat.SocialWX;

import java.io.File;
import java.util.HashMap;

/**
 * 分享辅助类
 *
 * @author Shaojie
 * @Date 2014-4-2 下午4:35:08
 */
public class ShareHelper {

    public static final int SHARE_WEIBO = 0;
    public static final int SHARE_WX_FRIEND = 1;
    public static final int SHARE_WX_MOMENT = 2;
    public static final int SHARE_QQ = 3;
    public static final int SHARE_QZONE = 4;
    public static final int SHARE_COPY = 5;
    public static final int SHARE_CODE = 6;
    public static final int SHARE_FACE = 7;

    /**
     * 小程序id
     */
    public static final String WX_MINI_ID = "gh_899de36ed031";
    /**
     * 小程序分享路径
     */
    public static final String WX_MINI_BOOK_DETAIL_PATH = "pages/bookDetail/index?id=";

    private static Handler mHandler = new Handler();
    private static String path;


    /**
     * 分享到指定平台
     *
     * @param imgUrl   图片的url，如果没有则传递null
     * @param platform 类型，0新浪微博，1微信好友，2微信朋友圈，3QQ，4QQ空间，5复制链接
     */

    public static void imageShare(Context context, final String imagePath, String imgUrl, int platform, PlatformActionListener onShareSuccess) {
        try {
            if (context == null) {
                return;
            }
            if (Build.VERSION.SDK_INT >= 30){
                File file = new File(imagePath);
                path = getFileUri(context, file);
            }else {
                path=imagePath;
            }
            ShareParam shareParam = null;
            switch (platform) {
                case SHARE_WX_FRIEND:
                    shareParam = new WXShareParam(WXShareParam.SHARE_TYPE_IMAGE, WXShareParam.WXSCENE_SESSION, path, null);
                    SocialApiClient.getPlatform(SocialWX.NAME).doShare(context, shareParam, onShareSuccess);
                    break;
                case SHARE_WX_MOMENT:
                    shareParam = new WXShareParam(WXShareParam.SHARE_TYPE_IMAGE, WXShareParam.WXSCENE_TIMELINE, path, null);
                    SocialApiClient.getPlatform(SocialWX.NAME).doShare(context, shareParam, onShareSuccess);
                    break;
                default:
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 安卓11部分手机分享失败问题
     * @param context
     * @param file
     * @return
     */
    public static String getFileUri(Context context, File file) {
        if (file == null || !file.exists()) {
            return null;
        }

        Uri contentUri = FileProvider.getUriForFile(context, context.getApplicationInfo().processName + ".fileProvider", file);

//        Uri contentUri= FileProvider7.getUriForFile(context, file);

        // 授权给微信访问路径
        context.grantUriPermission("com.tencent.mm",  // 这里填微信包名
                contentUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);

        return contentUri.toString();   // contentUri.toString() 即是以"content://"开头的用于共享的路径
    }

    /**
     * 分享到QQ
     *
     * @param context
     * @param title
     * @param shareLinkUrl
     * @param imgUrl
     * @param onShareSuccess
     */
    private static void shareQQ(Context context, String title, String content, String shareLinkUrl, String imgUrl, PlatformActionListener onShareSuccess) {

    }



    private static void shareImageWeixinFrients(Context context, String imgUrl, PlatformActionListener onShareSuccess) {
        final WXShareParam shareParams = new WXShareParam(WXShareParam.SHARE_TYPE_IMAGE, WXShareParam.WXSCENE_SESSION, imgUrl, null);
        SocialApiClient.getPlatform(SocialWX.NAME).doShare(context, shareParams, onShareSuccess);
    }





    public static String getPlatformName(int platform) {
        String name = null;
        switch (platform) {
            case SHARE_WEIBO:
                name = "weibo";
                break;
            case SHARE_WX_FRIEND:
                name = "weixin_friend";
                break;
            case SHARE_WX_MOMENT:
                name = "weixin_moment";
                break;
            case SHARE_QQ:
                name = "qq";
                break;
            case SHARE_QZONE:
                name = "qzone";
                break;
        }
        return name;
    }

    /***
     * qq和qqzone 延时通知 success 回掉
     */
    private static void delayNotify(final PlatformActionListener onShareSuccess) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onShareSuccess.onComplete(null, 0, null);
            }
        }, 5000);
    }

    /**
     *
     * @param platform
     * @param onShareSuccess
     * @param data
     */
    private static void delayNotify(final SocialPlatform platform, final PlatformActionListener onShareSuccess, final HashMap<String, Object> data) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (onShareSuccess != null) {
                    onShareSuccess.onComplete(platform, SocialPlatform.ACTION_SHARE, data);
                }
            }
        }, 5000);
    }


}
