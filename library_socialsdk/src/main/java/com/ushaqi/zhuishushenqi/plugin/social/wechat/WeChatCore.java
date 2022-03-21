package com.ushaqi.zhuishushenqi.plugin.social.wechat;

import android.content.Context;
import android.util.Log;

import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.ushaqi.zhuishushenqi.plugin.social.SocialConstants;

/**
 * Created by JackHu on 2019/4/24
 */
public class WeChatCore {
    private final String TAG = getClass().getSimpleName();
    private static WeChatCore sInstance;
    private IWXAPI mWxApi;

    public static WeChatCore getsInstance() {
        if (sInstance == null) {
            synchronized (WeChatCore.class) {
                if (sInstance == null) {
                    sInstance = new WeChatCore();
                }
            }
        }
        return sInstance;
    }

    /**
     * 注册微信
     *
     * @param context
     */
    public void registerAppToWechat(Context context) {
        mWxApi = WXAPIFactory.createWXAPI(context, SocialConstants.WEIXIN_APP_ID, false);
        mWxApi.registerApp(SocialConstants.WEIXIN_APP_ID);
    }

    public IWXAPI getWxApi() {
        if (mWxApi == null) {
            Log.e(TAG, "mWxApi == null");
        }
        return mWxApi;
    }


}
