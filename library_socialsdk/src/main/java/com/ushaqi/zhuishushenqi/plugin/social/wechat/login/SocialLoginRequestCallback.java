package com.ushaqi.zhuishushenqi.plugin.social.wechat.login;

import com.ushaqi.zhuishushenqi.plugin.social.wechat.model.SocialLoginUserInfo;

/**
 * Created by JackHu on 2019/4/24
 */
public interface SocialLoginRequestCallback {
    void onLoginSuccess(SocialLoginUserInfo userInfo);
    void onLoginFailed(String msg);
}
