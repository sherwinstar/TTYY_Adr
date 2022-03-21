package com.ushaqi.zhuishushenqi.util;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Method;

public class DisplayUtils {

    /**
     * @param density
     * @param dpValue
     * @return
     */
    public static int dp2px(final float density, final float dpValue) {
        return (int) (dpValue * density + 0.5f);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(final Context context, float dpValue) {
        if (context == null) {
            return 0;
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(final Context context, float pxValue) {
        if (context == null) {
            return 0;
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * @param density
     * @param pxValue
     * @return
     */
    public static int px2dp(final float density, float pxValue) {
        return (int) (pxValue / density + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        if (null == context) {
            return 0;
        }
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * @param scaledDensity
     * @param spValue
     * @return
     */
    public static int sp2px(final float scaledDensity, float spValue) {
        return (int) (spValue * scaledDensity + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        if (null == context) {
            return 0;
        }
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * @param scaledDensity
     * @param pxValue
     * @return
     */
    public static int px2sp(final float scaledDensity, float pxValue) {
        return (int) (pxValue / scaledDensity + 0.5f);
    }

    /**
     * @param context
     * @return
     * @方法说明:获取DisplayMetrics对象
     * @方法名称:getDisPlayMetrics
     * @返回值:DisplayMetrics
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (null != context) {
            if (Build.VERSION.SDK_INT >= 17) {
                DisplayMetrics dm = new DisplayMetrics();
                WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                windowManager.getDefaultDisplay().getRealMetrics(displayMetrics);
            } else {
                WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                Display display = windowManager.getDefaultDisplay();
                @SuppressWarnings("rawtypes") Class c;
                try {
                    c = Class.forName("android.view.Display");
                    @SuppressWarnings("unchecked") Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
                    method.invoke(display, displayMetrics);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return displayMetrics;
    }

    /**
     * @param context
     * @return
     * @方法说明:获取屏幕的宽度（像素）
     * @方法名称:getScreenWidth
     * @返回值:int
     */
    public static int getScreenWidth(Context context) {
        final DisplayMetrics dm = getDisplayMetrics(context);
        return (dm != null) ? dm.widthPixels : -1;
    }

    /**
     * @param context
     * @return
     * @方法说明:获取屏幕的高（像素）
     * @方法名称:getScreenHeight
     * @返回值:int
     */
    public static int getScreenHeight(Context context) {
        final DisplayMetrics dm = getDisplayMetrics(context);
        return (dm != null) ? dm.heightPixels : -1;
    }

    /**
     * @param context
     * @return
     * @方法说明:获取应用程序显示区域的高度（像素）（不包含导航栏高度）
     * @方法名称:getScreenHeight
     * @返回值:int
     */
    public static int getScreenDefaultHeight(Context context) {
        int screenHeight = 0;
        try {
            WindowManager windowManager = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE));
            DisplayMetrics display = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(display);
            screenHeight = display.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return screenHeight;
    }

    /**
     * @param context
     * @return
     * @方法说明:屏幕密度(0.75 / 1.0 / 1.5)
     * @方法名称:getDensity
     * @返回 float
     */
    public static float getDensity(Context context) {
        final DisplayMetrics dm = getDisplayMetrics(context);
        return (dm != null) ? dm.density : 0;
    }

    /**
     * @param context
     * @return
     * @方法说明:屏幕密度DPI(120 / 160 / 240)
     * @方法名称:getDensityDpi
     * @返回 int
     */
    public static int getDensityDpi(Context context) {
        final DisplayMetrics dm = getDisplayMetrics(context);
        return (dm != null) ? dm.densityDpi : 0;
    }

    /**
     * @param displayMetrics
     * @param value
     * @param unit
     * @return
     */
    public static float applyDimension(final DisplayMetrics displayMetrics,
                                       final float value,
                                       final int unit) {
        switch (unit) {
            case TypedValue.COMPLEX_UNIT_PX:
                return value;
            case TypedValue.COMPLEX_UNIT_DIP:
                return value * displayMetrics.density;
            case TypedValue.COMPLEX_UNIT_SP:
                return value * displayMetrics.scaledDensity;
            case TypedValue.COMPLEX_UNIT_PT:
                return value * displayMetrics.xdpi * (1.0f / 72);
            case TypedValue.COMPLEX_UNIT_IN:
                return value * displayMetrics.xdpi;
            case TypedValue.COMPLEX_UNIT_MM:
                return value * displayMetrics.xdpi * (1.0f / 25.4f);
        }
        return 0;
    }


}
