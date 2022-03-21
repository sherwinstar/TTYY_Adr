/*
 * 官网地站:http://www.ShareSDK.cn
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 ShareSDK.cn. All rights reserved.
 */

package com.jxjuwen.ttyy.wxapi;




import com.ushaqi.zhuishushenqi.event.BusProvider;
import com.ushaqi.zhuishushenqi.event.WXLoginResultEvent;
import com.ushaqi.zhuishushenqi.plugin.social.wechat.WeChatEntryActivity;
import com.ushaqi.zhuishushenqi.plugin.social.wechat.model.SocialLoginUserInfo;


/**
 * 微信客户端回调activity
 */
public class WXEntryActivity extends WeChatEntryActivity {
    @Override
    public void onResultReceived(SocialLoginUserInfo socialLoginUserInfo) {
        BusProvider.getInstance().post(new WXLoginResultEvent(socialLoginUserInfo));
    }

    @Override
    public void onResultFailed(String msg) {
        BusProvider.getInstance().post(new WXLoginResultEvent(msg));
    }

    @Override
    public void onShareResultSuccess() {
    }

    @Override
    public void onShareResultFailed(int errCode, String errorMsg) {
    }

}