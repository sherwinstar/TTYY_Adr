package com.ushaqi.zhuishushenqi.plugin.social.wechat.share;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.ushaqi.zhuishushenqi.plugin.social.SocialUtils;
import com.ushaqi.zhuishushenqi.plugin.social.api.PlatformActionListener;
import com.ushaqi.zhuishushenqi.plugin.social.api.SocialPlatform;
import com.ushaqi.zhuishushenqi.plugin.social.shareparam.WXMiniShareParam;
import com.ushaqi.zhuishushenqi.plugin.social.shareparam.WXShareParam;
import com.ushaqi.zhuishushenqi.plugin.social.wechat.WeChatCore;

/**
 * Created by JackHu on 2019/4/24
 */
public class WeChatShareHelper {

    private static WeChatShareHelper sInstance;
    private static PlatformActionListener mWxShareCallback;


    public WeChatShareHelper() {
        SocialUtils.printLog("WeChatShareHelper", "init");
    }

    public static WeChatShareHelper getInstance() {


        if (sInstance == null) {
            synchronized (WeChatShareHelper.class) {
                if (sInstance == null) {
                    sInstance = new WeChatShareHelper();
                }
            }
        }
        return sInstance;
    }

    /**
     * //        // 分享到对话:
     * //        SendMessageToWX.Req.WXSceneSession;
     * //        //  分享到朋友圈:
     * //        SendMessageToWX.Req.WXSceneTimeline;
     * //        //  分享到收藏:
     * //        SendMessageToWX.Req.WXSceneFavorite
     */
    public static final String ACTION_SHARE_RESPONSE = "action_wx_share_response";
    public static final String EXTRA_RESULT = "result";
    //    private OnResponseListener listener;
    private ResponseReceiver receiver;


    public WeChatShareHelper register(Context context) {
        receiver = new ResponseReceiver();
        IntentFilter filter = new IntentFilter(ACTION_SHARE_RESPONSE);
        context.registerReceiver(receiver, filter);
        return this;
    }

    public void unregister(Context context) {
        try {
            WeChatCore.getsInstance().getWxApi().unregisterApp();
            context.unregisterReceiver(receiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 分享文本
     *
     * @param shareScene
     * @param title
     * @param content
     * @return
     */
    public WeChatShareHelper shareTxt(int shareScene, String title, String content, PlatformActionListener wxShareCallback) {
        mWxShareCallback = wxShareCallback;
        WXTextObject textObj = new WXTextObject();
        textObj.text = content;
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;//
        msg.title = title;
        msg.description = content;
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text");
        req.message = msg;
        req.scene = shareScene == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        boolean result = WeChatCore.getsInstance().getWxApi().sendReq(req);
        Log.e("text shared: " + result, "");
        return this;
    }

    /**
     * 分享链接
     *
     * @param flag       0：好友 ,1:朋友圈
     * @param webPageUrl
     * @param title
     * @param desc
     * @param thumb
     * @return
     */
    public WeChatShareHelper shareWebPage(int flag, String webPageUrl, String title, String desc, Bitmap thumb, PlatformActionListener wxShareCallback) {
        mWxShareCallback = wxShareCallback;
        WXWebpageObject webpageObject = new WXWebpageObject(webPageUrl);
        webpageObject.webpageUrl = webPageUrl;
        WXMediaMessage msg = new WXMediaMessage(webpageObject);
        msg.title = title;
        msg.description = desc;
        msg.setThumbImage(thumb);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;

        boolean result = WeChatCore.getsInstance().getWxApi().sendReq(req);
        Log.e("text shared: " + result, "");
        return this;
    }

    /**
     * 分享图片
     *
     * @param flag     0：好友 ,1:朋友圈
     * @param imageUrl
     * @param title
     * @param desc
     * @return
     */
    public WeChatShareHelper shareImage(int flag, String imageUrl, String title, String desc, PlatformActionListener wxShareCallback) {
        mWxShareCallback = wxShareCallback;
        // 初始化一个WXWebpageObject填写url
        WXImageObject webImageObject = new WXImageObject();
        webImageObject.imagePath = imageUrl;
        //  用WXWebpageObject对象初始化一个WXMediaMessage，天下标题，描述
        WXMediaMessage msg = new WXMediaMessage(webImageObject);
        msg.title = title;
        msg.description = desc;
//        msg.setThumbImage(thumb);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        boolean result = WeChatCore.getsInstance().getWxApi().sendReq(req);
        Log.e("text shared: " + result, "");
        return this;
    }

    public IWXAPI getApi() {
        return WeChatCore.getsInstance().getWxApi();
    }


    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    public void onShareResultSuccess() {
        if (mWxShareCallback == null) {
            SocialUtils.printLog("onShareResultSuccess", "mWxShareCallback == null");
            return;
        }
        mWxShareCallback.onComplete(null, SocialPlatform.ACTION_SHARE, null);
    }

    public void onShareResultFailed(int errCode, String errStr) {
        if (mWxShareCallback == null) {
            SocialUtils.printLog("onShareResultFailed", "mWxShareCallback == null");
            return;
        }
        mWxShareCallback.onError(null, SocialPlatform.ACTION_SHARE, null);
    }

    public void share(Context context, WXShareParam shareParams, PlatformActionListener platformActionListener) {
        if (shareParams == null) {
            if (platformActionListener != null) {
                platformActionListener.onError(null, SocialPlatform.ACTION_SHARE, new NullPointerException("shareParams == null"));
            }
            return;
        }
        int type = shareParams.getShareType();
        switch (type) {
            case WXShareParam.SHARE_TYPE_TEXT:
                shareTxt(shareParams.getShareScene(), shareParams.getTitle(), shareParams.getContent(), platformActionListener);
                break;
            case WXShareParam.SHARE_TYPE_IMAGE:
                shareImage(shareParams.getShareScene(), shareParams.getImageUrl(), shareParams.getTitle(), shareParams.getContent(), platformActionListener);
                break;
            case WXShareParam.SHARE_TYPE_MUSIC:
                shareMusic();
                break;
            case WXShareParam.SHARE_TYPE_VIDEO:
                shareVideo();
                break;
            case WXShareParam.SHARE_TYPE_APP:
                shareApp();
                break;
            case WXShareParam.SHARE_TYPE_WEBPAGE:
                shareWebPage(shareParams.getShareScene(), shareParams.getTargetUrl(), shareParams.getTitle(), shareParams.getContent(), shareParams.getThumbnail(), platformActionListener);
                break;
            case WXShareParam.SHARE_TYPE_MINI:
                WXMiniShareParam wxMiniParams = (WXMiniShareParam) shareParams;
                shareMini(wxMiniParams.getTargetUrl(), wxMiniParams.getWxMiniId(), wxMiniParams.getWxPath(), wxMiniParams.getTitle(), wxMiniParams.getContent(), wxMiniParams.getThumb());
                break;
        }

    }

    private void shareMini(String webPageUrl, String originId, String path, String title, String desc, byte[] thumbData) {
        WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
        miniProgramObj.webpageUrl = webPageUrl; // 兼容低版本的网页链接
        miniProgramObj.miniprogramType = WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE;// 正式版:0，测试版:1，体验版:2
        miniProgramObj.userName = originId;     // 小程序原始id
        miniProgramObj.path = path;            //小程序页面路径
        WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
        msg.title = title;                    // 小程序消息title
        msg.description = desc;               // 小程序消息desc
        msg.thumbData = thumbData;
        // 小程序消息封面图片，小于128k
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("miniProgram");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;  // 目前只支持会话
        boolean shareMini = WeChatCore.getsInstance().getWxApi().sendReq(req);
        SocialUtils.printLog("shareMini", "" + shareMini);
    }


    /**
     * 分享APP
     */
    private void shareApp() {

    }

    /**
     * 分享视频
     */
    private void shareVideo() {

    }

    /**
     * 分享音乐
     */
    private void shareMusic() {

    }

    private class ResponseReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            WeChatShareHelper.Response response = intent.getParcelableExtra(EXTRA_RESULT);
            Log.e("type: " + response.getType(), "type: " + response.getType());
            Log.e("errCode: " + response.errCode, "errCode: " + response.errCode);
            String result;
            if (response.errCode == BaseResp.ErrCode.ERR_OK) {
            } else if (response.errCode == BaseResp.ErrCode.ERR_USER_CANCEL) {
            } else {
                switch (response.errCode) {
                    case BaseResp.ErrCode.ERR_AUTH_DENIED:
                        result = "发送被拒绝";
                        break;
                    case BaseResp.ErrCode.ERR_UNSUPPORT:
                        result = "不支持错误";
                        break;
                    default:
                        result = "发送返回";
                        break;
                }
            }
        }
    }

    public static class Response extends BaseResp implements Parcelable {
        public int errCode;
        public String errStr;
        public String transaction;
        public String openId;
        private int type;
        private boolean checkResult;

        public Response(BaseResp baseResp) {
            errCode = baseResp.errCode;
            errStr = baseResp.errStr;
            transaction = baseResp.transaction;
            openId = baseResp.openId;
            type = baseResp.getType();
            checkResult = baseResp.checkArgs();
        }

        public static final Creator<WeChatShareHelper.Response> CREATOR = new Creator<WeChatShareHelper.Response>() {
            @Override
            public WeChatShareHelper.Response createFromParcel(Parcel in) {
                return new WeChatShareHelper.Response(in);
            }

            @Override
            public WeChatShareHelper.Response[] newArray(int size) {
                return new WeChatShareHelper.Response[size];
            }
        };

        @Override
        public int getType() {
            return type;
        }

        @Override
        public boolean checkArgs() {
            return checkResult;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.errCode);
            dest.writeString(this.errStr);
            dest.writeString(this.transaction);
            dest.writeString(this.openId);
            dest.writeInt(this.type);
            dest.writeByte(this.checkResult ? (byte) 1 : (byte) 0);
        }

        protected Response(Parcel in) {
            this.errCode = in.readInt();
            this.errStr = in.readString();
            this.transaction = in.readString();
            this.openId = in.readString();
            this.type = in.readInt();
            this.checkResult = in.readByte() != 0;
        }
    }

}
