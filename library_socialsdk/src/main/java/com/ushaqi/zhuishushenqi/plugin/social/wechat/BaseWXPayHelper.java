package com.ushaqi.zhuishushenqi.plugin.social.wechat;

import android.content.Context;

import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;

/**
 * Created by JackHu on 2019/4/24
 */
public class BaseWXPayHelper {

    protected BaseWXPayHelper() {
    }

    /**
     * 调起微信支付的方法
     **/

    public void requestWXPay(final Context context, final WXPayBuilder builder) {
        Runnable payRunnable = new Runnable() {  //这里注意要放在子线程
            @Override
            public void run() {
                PayReq request = new PayReq(); //调起微信APP的对象
                //下面是设置必要的参数，也就是前面说的参数,这几个参数从何而来请看上面说明
                request.appId = builder.getAppId();
                request.partnerId = builder.getPartnerId();
                request.prepayId = builder.getPrepayId();
                request.packageValue = builder.getPackageValue();
                request.nonceStr = builder.getNonceStr();
                request.timeStamp = builder.getTimeStamp();
                request.sign = builder.getSign();
                WeChatCore.getsInstance().registerAppToWechat(context);
                WeChatCore.getsInstance().getWxApi().sendReq(request);//发送调起微信的请求
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    public static class WXPayBuilder {
        private String appId;
        private String partnerId;
        private String prepayId;
        private String packageValue;
        private String nonceStr;
        private String timeStamp;
        private String sign;

        public WXPayBuilder(String appId, String partnerId, String prepayId, String packageValue, String nonceStr, String timeStamp, String sign) {
            this.appId = appId;
            this.partnerId = partnerId;
            this.prepayId = prepayId;
            this.packageValue = packageValue;
            this.nonceStr = nonceStr;
            this.timeStamp = timeStamp;
            this.sign = sign;
        }

        public String getAppId() {
            return appId;
        }

        public String getPartnerId() {
            return partnerId;
        }

        public String getPrepayId() {
            return prepayId;
        }

        public String getPackageValue() {
            return packageValue;
        }

        public String getNonceStr() {
            return nonceStr;
        }

        public String getTimeStamp() {
            return timeStamp;
        }

        public String getSign() {
            return sign;
        }
    }


}
