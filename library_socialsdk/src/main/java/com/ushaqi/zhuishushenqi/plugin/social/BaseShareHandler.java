package com.ushaqi.zhuishushenqi.plugin.social;

import android.app.Activity;
import android.content.Intent;

import com.ushaqi.zhuishushenqi.plugin.social.api.PlatformActionListener;
import com.ushaqi.zhuishushenqi.plugin.social.api.SocialPlatform;
import com.ushaqi.zhuishushenqi.plugin.social.shareparam.ImageShareParam;
import com.ushaqi.zhuishushenqi.plugin.social.shareparam.TextShareParam;
import com.ushaqi.zhuishushenqi.plugin.social.shareparam.WebPageShareParam;

/**
 * 
 * @author Andy.zhang
 * @date 2019/8/20
 */
public abstract class BaseShareHandler {
    private SocialPlatform mSocialPlatform;
    private PlatformActionListener mPlatformActionListener;

    /**
     * 分享文本
     *
     * @param activity
     * @param params
     */
    public abstract void shareText(final Activity activity, TextShareParam params);

    /**
     * 分享图片
     * @param activity
     * @param params
     */
    public abstract void shareImage(final Activity activity, ImageShareParam params);

    /**
     * 分享网页
     * @param activity
     * @param params
     */
    public abstract void shareWebPage(final Activity activity, WebPageShareParam params);

    /**
     *
     * @param activity
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent
            data) {
    }

    public SocialPlatform getSocialPlatform() {
        return mSocialPlatform;
    }

    public void setSocialPlatform(SocialPlatform socialPlatform) {
        this.mSocialPlatform = socialPlatform;
    }

    public PlatformActionListener getPlatformActionListener() {
        return mPlatformActionListener;
    }

    public void setPlatformActionListener(PlatformActionListener platformActionListener) {
        this.mPlatformActionListener = platformActionListener;
    }

    /**
     *
     * @param activity
     * @param runnable
     */
    public void doInMainThread(final Activity activity, final Runnable runnable) {
        if (activity != null) {
            activity.runOnUiThread(runnable);
        }
    }
}
