package com.ushaqi.zhuishushenqi.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.ushaqi.zhuishushenqi.MyApplication;
import com.ushaqi.zhuishushenqi.channel.AppChannelManager;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by shijingxing on 2018/9/11.
 * 获取埋点所需公共信息的辅助类
 */

public class AppEventCommonInfoHelper {


    private static SimpleDateFormat sEventCreatedTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static String sImei;

    private static String sMac;

    private static String sChannel;

    private static String sVersion;

    private static String sPhoneDisplay;

    private static String sIpAdress;

    private static String sPhoneVersion;

    private static String sPhoneModel;

    private static String sAndroidId;

    /**
     * 投放渠道
     */
    public static String sDeliverChannel;

    /**
     * 投放渠道Id
     */
    public static String sDeliverChannelId;

    /**
     * 通过ApplicationContext获取设备分辨率
     *
     * @return
     */
    public static String getPhoneDisplay() {
        if (sPhoneDisplay == null || "-1".equals(sPhoneDisplay) || sPhoneDisplay.isEmpty()) {
            String str = null;
            try {
                DisplayMetrics display = new DisplayMetrics();
                WindowManager windowManager =
                        ((WindowManager)MyApplication.getInstance().getSystemService(Context.WINDOW_SERVICE));
                windowManager.getDefaultDisplay().getMetrics(display);
                int wpx = display.widthPixels;
                int hpx = display.heightPixels;
                str = wpx + "*" + hpx;
            } catch (Exception e) {
                sPhoneDisplay = "-1";
            }
            if (str == null) {
                sPhoneDisplay = "-1";
            } else {
                sPhoneDisplay = str;
            }

        }
        return sPhoneDisplay;
    }

    public static String getImei() {
        if (sImei == null || sImei.isEmpty()) {
            sImei = AppHelper.getRealDeviceInfo(MyApplication.getInstance());
        }
        return sImei;
    }

    public static String getMac() {
        if (sMac == null || sMac.isEmpty()) {
            sMac = "";
        }
        return sMac;
    }

    //    public static String getChannel(){
//        if(sChannel == null || sChannel.isEmpty()){
//            sChannel = AppHelper.getUmengChannel(GlobalConfig.getInstance().getContext());
//        }
//        return sChannel;
//    }
    public static String getDeliverChannel() {
        if (sDeliverChannel == null || sDeliverChannel.isEmpty() || "-1".equals(sDeliverChannel)) {
            sDeliverChannel = AppChannelManager.getChannel();
        }
        return sDeliverChannel;
    }

    public static String getDeliverChannelId() {
        if (sDeliverChannelId == null || sDeliverChannelId.isEmpty() || "-1".equals(sDeliverChannelId)) {
            sDeliverChannelId = AppChannelManager.getChannelId();
        }
        return sDeliverChannelId;
    }


    public static String getVersion() {
        if (sVersion == null || sVersion.isEmpty()) {
            sVersion = AppHelper.getVersionName(MyApplication.getInstance());
        }
        return sVersion;
    }

    public static String getPhoneVersion() {
        if (sPhoneVersion == null || sPhoneVersion.isEmpty()) {
            sPhoneVersion = android.os.Build.VERSION.RELEASE;
        }
        return sPhoneVersion;

    }

    public static String getPhoneModel() {
        if (sPhoneModel == null || sPhoneModel.isEmpty()) {
            sPhoneModel = android.os.Build.MODEL;
        }
        return sPhoneModel;
    }



    public static String produceEventCreatedTime() {
        return sEventCreatedTimeFormat.format(new Date(System.currentTimeMillis()));
    }


    public static String getAndroidId() {
        if (sAndroidId == null || sAndroidId.isEmpty()) {
            sAndroidId = AppHelper.getAndroidId();
        }
        return sAndroidId;
    }
    public static String getNetWorkType() {
        String netWorkType = "-1";
        String type = SharedPreferencesUtil.get(MyApplication.getInstance(),
                "zhuishu_bi_netWork_type", "");
        if (!TextUtils.isEmpty(type)) {
            netWorkType = type;
        } else {
            if (NetUtil.getNetworkType(MyApplication.getInstance()) == NetUtil.NETTYPE_WIFI) {
                netWorkType = "wifi";
            } else {
                netWorkType = "4G";
            }
        }
        return netWorkType;
    }
}
