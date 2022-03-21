package com.ushaqi.zhuishushenqi.model;

import android.text.TextUtils;

import com.google.gson.Gson;

/**
 * <p>
 *
 * @ClassName: CpsDeviceInfo
 * @Date: 2019-11-01 16:11
 * @Author: jared
 * @Org: 上海元聚网络科技有限公司
 * @Description: 用于获取cps信息的参数封装
 * </p>
 */
public class CpsDeviceInfo {

    private String product_line;
    private String platform;
    private String device_imei;
    private String oaid;
    private String android_id;

    public String getProduct_line() {
        return product_line;
    }

    public void setProduct_line(String product_line) {
        this.product_line = product_line;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getDevice_imei() {
        return device_imei;
    }

    public void setDevice_imei(String device_imei) {
        this.device_imei = device_imei;
    }

    public String getOaid() {
        return oaid;
    }

    public void setOaid(String oaid) {
        this.oaid = oaid;
    }

    public String getAndroid_id() {
        return android_id;
    }

    public void setAndroid_id(String android_id) {
        this.android_id = android_id;
    }

    public static CpsDeviceInfo init(String imei, String oaid, String androidId) {
        CpsDeviceInfo info = new CpsDeviceInfo();
        info.setProduct_line("26");
        info.setPlatform("2");
        if (!TextUtils.isEmpty(imei)) {
            info.setDevice_imei(imei);
        }
        if (!TextUtils.isEmpty(oaid)) {
            info.setOaid(oaid);
        }
        info.setAndroid_id(androidId);
        return info;
    }

    public static String toJson(CpsDeviceInfo info) {
        if (info == null) {
            return "";
        }
        try {
            Gson gson = new Gson();
            return gson.toJson(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
