package com.ushaqi.zhuishushenqi.plugin.social.api;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.ushaqi.zhuishushenqi.plugin.social.shareparam.ShareParam;

import java.util.HashMap;

/**
 * @author Andy.zhang
 * @date 2019/4/26
 */
public abstract class                            SocialPlatform implements ISocialPlatform {
    /**
     * 登录
     */
    public static final int ACTION_AUTH = 1;
    /**
     * 分享
     */
    public static final int ACTION_SHARE = 2;

    protected PlatformActionListener mPlatformActionListener;

    public abstract String getPlatformName();

    public abstract void doAuth(final Activity activity);

    public void doShare(final Context context, final ShareParam shareParam, final PlatformActionListener platformActionListener) {

    }

    public void doZoneShare(final Context context, final ShareParam shareParam, final PlatformActionListener platformActionListener) {

    }

    public void release(){
    }

    public String getToken() {
        return "";
    }

    public String getUserId() {
        return "";
    }

    public String getNickName() {
        return "";
    }

    /**
     * @param platformActionListener
     */
    @Override
    public void setPlatformActionListener(PlatformActionListener platformActionListener) {
        this.mPlatformActionListener = platformActionListener;
    }

    /**
     * @param activity
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void handleActivityResultData(final Activity activity, int requestCode, int resultCode, Intent data) {

    }

    /**
     * @param action
     * @param data
     */
    public void handleComplete(final int action, HashMap<String, Object> data) {
        if (mPlatformActionListener != null) {
            mPlatformActionListener.onComplete(this, action, data);
        }
    }

    /**
     * @param action
     * @param throwable
     */
    public void handleError(int action, Throwable throwable) {
        if (mPlatformActionListener != null) {
            mPlatformActionListener.onError(this, action, throwable);
        }
    }

    /**
     * @param action
     */
    public void handleCancel(int action) {
        if (mPlatformActionListener != null) {
            mPlatformActionListener.onCancel(this, action);
        }
    }

}
