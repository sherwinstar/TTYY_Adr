package com.ushaqi.zhuishushenqi.plugin.social.wechat.login;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.ushaqi.zhuishushenqi.plugin.social.HttpWorker;
import com.ushaqi.zhuishushenqi.plugin.social.SocialConstants;
import com.ushaqi.zhuishushenqi.plugin.social.SocialUtils;
import com.ushaqi.zhuishushenqi.plugin.social.api.PlatformActionListener;
import com.ushaqi.zhuishushenqi.plugin.social.api.SocialPlatform;
import com.ushaqi.zhuishushenqi.plugin.social.wechat.SocialWX;
import com.ushaqi.zhuishushenqi.plugin.social.wechat.WeChatCore;
import com.ushaqi.zhuishushenqi.plugin.social.wechat.ZSSocialHttpWorker;
import com.ushaqi.zhuishushenqi.plugin.social.wechat.model.SocialLoginToken;
import com.ushaqi.zhuishushenqi.plugin.social.wechat.model.SocialLoginUserInfo;
import com.ushaqi.zhuishushenqi.plugin.social.wechat.share.WeChatShareHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by JackHu on 2019/4/24
 */
public class WeChatLoginHelper {
    private static WeChatLoginHelper sInstance;

    public WeChatLoginHelper() {
        SocialUtils.printLog("WeChatLoginHelper", "init");
    }

    public static WeChatLoginHelper getInstance() {
        if (sInstance == null) {
            synchronized (WeChatLoginHelper.class) {
                if (sInstance == null) {
                    sInstance = new WeChatLoginHelper();
                }
            }
        }
        return sInstance;
    }

    /**
     * 微信登录
     */
    public void requestWXLogin() {
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "zs_wx_login";
        //像微信发送请求
        WeChatCore.getsInstance().getWxApi().sendReq(req);
    }

    /**
     * https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
     *
     * @param code
     */
    public void getAccessToken(String code, final SocialLoginRequestCallback socialLoginRequestCallback) {
        final HttpWorker httpWorker = ZSSocialHttpWorker.newInstance();
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=" + SocialConstants.WEIXIN_APP_ID + "&secret=" + SocialConstants.WEIXIN_APP_SECRET +
                "&code=" + code + "&grant_type=authorization_code";

        httpWorker.param(url);
        httpWorker.request(new SocialRequestCallback() {
            @Override
            public void onSuccess(Object result) {
                SocialUtils.printLog("getAccessToken", "" + result.toString());
                try {
                    getWeiXinUserInfo(httpWorker, result, socialLoginRequestCallback);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(String msg) {
                if (socialLoginRequestCallback == null) {
                    return;
                }
                socialLoginRequestCallback.onLoginFailed(msg);
            }
        });
    }

    /**
     * {"openid":"ojmpj536r1ggSVsSbRL6QFW_E1cc",
     * "nickname":"äº®",
     * "sex":1,
     * "language":"zh_CN",
     * "city":"Songjiang",
     * "province":"Shanghai",
     * "country":"CN",
     * "headimgurl":"http:\/\/thirdwx.qlogo.cn\/mmopen\/vi_32\/Q0j4TwGTfTKfBgs5MGiaEl6p9hBluPestibaKb5mBmFlF9qKDfC5INztszXLVpoHBxO2cee33SoYrsF6b3Jba4uA\/132",
     * "privilege":[],
     * "unionid":"o0xQDwQZ3Qs-EhoHohswYJnjumkc"}
     *
     * @param httpWorker
     * @param result
     */
    private void getWeiXinUserInfo(HttpWorker httpWorker, Object result, final SocialLoginRequestCallback socialLoginRequestCallback) {
        final SocialLoginToken socialLoginToken = getJsonValue(result);
        if (socialLoginToken == null && socialLoginRequestCallback != null) {
            socialLoginRequestCallback.onLoginFailed("授权取消");
            return;
        }
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" +
                socialLoginToken.getAccessToken() + "&openid=" + socialLoginToken.getOpenId();
        httpWorker.param(url);
        httpWorker.request(new SocialRequestCallback() {

            @Override
            public void onSuccess(Object result) {
                SocialUtils.printLog("getWeiXinUserInfo", "" + result.toString());
                SocialLoginUserInfo socialLoginUserInfo = getUserInfo((String) result, socialLoginToken.getAccessToken());
                if (socialLoginRequestCallback == null) {
                    SocialUtils.printLog("getWeiXinUserInfo", "socialLoginRequestCallback is null");
                    return;
                }
                socialLoginRequestCallback.onLoginSuccess(socialLoginUserInfo);
            }

            @Override
            public void onFailed(String msg) {
                if (socialLoginRequestCallback == null) {
                    return;
                }
                socialLoginRequestCallback.onLoginFailed(msg);
            }
        });
    }

    /**
     * {"access_token":"20_ZLVlYtIye2uzJ95Unz-E-EP_6RXlFtw3jfmoIrU3tjBPlMIVCm4pzB3dlodeCHnnJw6Ulpp_iDAp89ebGZJe2Yem3m9sJAUc6aq7WXcasSk",
     * "expires_in":7200,
     * "refresh_token":"20_rPSgz6DrfQhKL_T1srd-UP7uSq0eQTdGbZ94Q2p1QhKeAR9y1P0zn2J375s_jyFQwGJ2i6EC_xC6p7yZBm7GwSyJth4zzQI-BMbKNC4OTN0",
     * "openid":"ojmpj536r1ggSVsSbRL6QFW_E1cc",
     * "scope":"snsapi_userinfo",
     * "unionid":"o0xQDwQZ3Qs-EhoHohswYJnjumkc"}
     *
     * @param result
     * @return
     */
    private SocialLoginToken getJsonValue(Object result) {
        try {
            JSONObject json = new JSONObject((String) result);
            String openId = json.getString("openid");
            String accessToken = json.getString("access_token");
            String refreshToken = json.getString("refresh_token");
            String scope = json.getString("scope");
            String unionid = json.getString("unionid");
            return new SocialLoginToken(openId, accessToken, refreshToken, scope, unionid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public SocialLoginUserInfo getUserInfo(String result, String token) {
        SocialLoginUserInfo socialLoginUserInfo = null;
        try {
            socialLoginUserInfo = new SocialLoginUserInfo();
            JSONObject json = new JSONObject(result);
            String openId = json.getString("openid");
            String nickName = json.getString("nickname");
            socialLoginUserInfo.setToken(token);
            socialLoginUserInfo.setUid(openId);
            socialLoginUserInfo.setNickName(nickName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return socialLoginUserInfo;
    }

}
