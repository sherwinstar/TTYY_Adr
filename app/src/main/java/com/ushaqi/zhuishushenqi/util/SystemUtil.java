package com.ushaqi.zhuishushenqi.util;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

import java.util.Locale;

/**
 * 获取手机硬件信息
 * Created by fly on 2018/2/1.
 */

public class SystemUtil {

    private static String deviceBrand;
    private static String systemVersion;
    private static String systemModel;

    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return 语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }



    /**
     * 获取手机系统信息
     * add fly
     */
    public static void showSystemParameter() {
        deviceBrand = SystemUtil.getDeviceBrand();
        systemModel = SystemUtil.getSystemModel();
        systemVersion = SystemUtil.getSystemVersion();
    }

    /**
     * 判断两个手机型号 m5 和 M611D 系统6.0不能使用Https 忽略模式
     *
     * @return
     */
    public static boolean isMeizuNotEnableHttps() {
        try {
            String m5 = "m5";
            String M611D = "M611D";
            String system ="6.0";
            if ("Meizu".equals(deviceBrand) && (m5.equals(systemModel) || M611D.equals(systemModel)) && system.equals(system)) {
                return true;
            }
        }catch (Exception e){
            return false;
        }
        return false;
    }
}
