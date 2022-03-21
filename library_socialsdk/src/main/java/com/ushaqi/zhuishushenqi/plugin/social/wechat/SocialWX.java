package com.ushaqi.zhuishushenqi.plugin.social.wechat;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.ushaqi.zhuishushenqi.plugin.social.SocialConstants;
import com.ushaqi.zhuishushenqi.plugin.social.SocialUtils;
import com.ushaqi.zhuishushenqi.plugin.social.api.PlatformActionListener;
import com.ushaqi.zhuishushenqi.plugin.social.api.SocialPlatform;
import com.ushaqi.zhuishushenqi.plugin.social.shareparam.ShareParam;
import com.ushaqi.zhuishushenqi.plugin.social.shareparam.WXShareParam;
import com.ushaqi.zhuishushenqi.plugin.social.wechat.login.WeChatLoginHelper;
import com.ushaqi.zhuishushenqi.plugin.social.wechat.share.WeChatShareHelper;


public class SocialWX extends SocialPlatform {
    public static final String NAME = SocialConstants.PLATFORM_WEIXIN;
    @Override
    public String getPlatformName() {
        return NAME;
    }

    /**
     * @param activity
     */
    @Override
    public void doAuth(final Activity activity) {
        WeChatCore.getsInstance().registerAppToWechat(activity);
        if(!SocialUtils.checkWXSupport()){
            Toast.makeText(activity, "请安装微信客户端后重试", Toast.LENGTH_SHORT).show();
            return;
        }
        WeChatLoginHelper.getInstance().requestWXLogin();
    }

    @Override
    public void doShare(Context context, ShareParam shareParam, PlatformActionListener platformActionListener) {
        WeChatCore.getsInstance().registerAppToWechat(context);
        if(!SocialUtils.checkWXSupport()){
            Toast.makeText(context, "请安装微信客户端后重试", Toast.LENGTH_SHORT).show();
            return;
        }

        WeChatShareHelper.getInstance().share(context, (WXShareParam) shareParam,platformActionListener);
    }
}
