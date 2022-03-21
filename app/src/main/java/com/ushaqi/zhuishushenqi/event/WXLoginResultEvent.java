package com.ushaqi.zhuishushenqi.event;


import com.ushaqi.zhuishushenqi.plugin.social.wechat.model.SocialLoginUserInfo;

/**
 * Created by JackHu on 2019/4/25
 */
public class WXLoginResultEvent {
    private SocialLoginUserInfo socialLoginUserInfo;
    private String errorMsg;

    public SocialLoginUserInfo getSocialLoginUserInfo() {
        return socialLoginUserInfo;
    }

    public void setSocialLoginUserInfo(SocialLoginUserInfo socialLoginUserInfo) {
        this.socialLoginUserInfo = socialLoginUserInfo;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public WXLoginResultEvent(SocialLoginUserInfo socialLoginUserInfo) {
        this.socialLoginUserInfo = socialLoginUserInfo;
    }

    public WXLoginResultEvent(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
