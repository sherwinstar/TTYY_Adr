package com.ushaqi.zhuishushenqi.plugin.social.wechat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.ushaqi.zhuishushenqi.plugin.social.SocialConstants;
import com.ushaqi.zhuishushenqi.plugin.social.SocialUtils;
import com.ushaqi.zhuishushenqi.plugin.social.wechat.login.SocialLoginRequestCallback;
import com.ushaqi.zhuishushenqi.plugin.social.wechat.login.WeChatLoginHelper;
import com.ushaqi.zhuishushenqi.plugin.social.wechat.model.SocialLoginUserInfo;
import com.ushaqi.zhuishushenqi.plugin.social.wechat.share.WeChatShareHelper;

/**
 * Created by JackHu on 2019/4/24
 */
public class WeChatEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;


    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, SocialConstants.WEIXIN_APP_ID);
        api.handleIntent(getIntent(), this);
    }


    @Override

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onResp(BaseResp resp) {
        //微信6.7.2分享新版本去掉了分享取消事件
        if (resp.getType() == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX) {//分享
            SocialUtils.printLog("WeChatEntryActivity", "微信分享操作.....");
            SendMessageToWX.Resp subscribeMsgResp = (SendMessageToWX.Resp) resp;
            if (subscribeMsgResp.errCode == 0) {
                onShareResultSuccess();
                WeChatShareHelper.getInstance().onShareResultSuccess();
            } else {
                WeChatShareHelper.getInstance().onShareResultFailed(subscribeMsgResp.errCode, subscribeMsgResp.errStr);
            }
        } else if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {//登陆
            SocialUtils.printLog("WeChatEntryActivity", "微信登录操作.....");
            SendAuth.Resp authResp = (SendAuth.Resp) resp;
//            WeiXin weiXin = new WeiXin(1, resp.errCode, authResp.code);
            WeChatLoginHelper.getInstance().getAccessToken(authResp.code, new SocialLoginRequestCallback() {
                @Override
                public void onLoginSuccess(SocialLoginUserInfo userInfo) {
                    SocialUtils.printLog("WeChatEntryActivity", "onLoginSuccess " + userInfo);
                    try {
                        onResultReceived(userInfo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onLoginFailed(String msg) {
                    SocialUtils.printLog("WeChatEntryActivity", "onLoginFailed " + msg);
                    try {
                        onResultFailed(msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == 0) {
                SocialUtils.showToast(this, "支付成功");
            } else {
                SocialUtils.printLog("WeChatEntryActivity", "onResp: " + resp.errCode);
                SocialUtils.showToast(this, "支付失败");
            }
        }
        finish();
    }


    @Override
    public void onReq(BaseReq baseReq) {
        SocialUtils.printLog("WeChatEntryActivity", "onReq baseReq" + baseReq.openId);
    }

    /**
     * 微信登录成功回调
     *
     * @param socialLoginUserInfo
     */
    public void onResultReceived(SocialLoginUserInfo socialLoginUserInfo) {

    }

    /**
     * 微信登录失败回调
     *
     * @param msg
     */
    public void onResultFailed(String msg) {

    }

    /**
     * 微信分享成功回调
     */
    public void onShareResultSuccess() {

    }

    /**
     * 微信分享失败回调
     *
     * @param errCode
     * @param errorMsg
     */
    public void onShareResultFailed(int errCode, String errorMsg) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        WeChatShareHelper.getInstance().unregister(this);
    }
}
