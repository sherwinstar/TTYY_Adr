package com.ushaqi.zhuishushenqi.plugin.social.wechat.model;

import com.ushaqi.zhuishushenqi.plugin.social.AccessToken;

/**
 * Created by JackHu on 2019/4/24
 */
public class SocialLoginUserInfo implements AccessToken {
    private String token;
    private String uid;
    private String nickName;


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "SocialLoginUserInfo{" +
                "token='" + token + '\'' +
                ", uid='" + uid + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
