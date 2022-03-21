package com.ushaqi.zhuishushenqi.plugin.social;

import com.ushaqi.zhuishushenqi.plugin.social.wechat.login.SocialRequestCallback;

/**
 * 网络请求接口，自行实现/拓展
 * Created by JackHu on 2019/4/24
 */
public interface HttpWorker {
    String GET = "GET";
    String POST = "POST";

    void request(SocialRequestCallback chatLoginRequestCallback);

    void param(String url);

    void allowRedirect(boolean allowRedirect);

    void allowUserInteraction(boolean allowUserInteraction);

    void setMethod(String method);
}
