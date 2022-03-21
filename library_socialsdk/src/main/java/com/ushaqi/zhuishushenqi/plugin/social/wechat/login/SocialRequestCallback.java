package com.ushaqi.zhuishushenqi.plugin.social.wechat.login;


/**
 * Created by JackHu on 2019/4/24
 */
public interface SocialRequestCallback {
    void onSuccess(Object result);

    void onFailed(String msg);
}

