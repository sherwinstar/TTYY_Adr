package com.ushaqi.zhuishushenqi.util;

import android.os.Build;
import android.os.Build.VERSION_CODES;

/**
 * SDK版本
 *
 * @author Shaojie
 * @Date 2014-05-30
 */
public class OSUtil {

    private OSUtil() {
    }

    /**
     * Can use static final constants like FROYO,declared in later versions of the OS since they are inlined at compile time.
     * This is guaranteed behavior.
     */
    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB;
    }

    public static boolean hasIcs() {
        return Build.VERSION.SDK_INT >= VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN;
    }

    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= VERSION_CODES.KITKAT;

    }public static boolean belowKitKatWatch() {
        return Build.VERSION.SDK_INT <= VERSION_CODES.KITKAT_WATCH;
    }

    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP;
    }

    public static boolean isKitKat() {
        return Build.VERSION.SDK_INT == VERSION_CODES.KITKAT;
    }

    public static boolean isXiaomiDevice() {
        return "xiaomi".equals((Build.BRAND + "").toLowerCase());
    }

//    public static boolean isMeizu() {
//        return SmartBarHelper.hasSmartBar();
//    }

}
