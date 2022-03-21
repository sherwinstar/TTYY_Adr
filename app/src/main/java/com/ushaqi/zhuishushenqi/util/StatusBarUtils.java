package com.ushaqi.zhuishushenqi.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;

import com.ushaqi.zhuishushenqi.R;

import java.lang.reflect.Field;

/**
 * Created by mac on 18/4/3.
 * <p>
 * 针对状态栏做的效果,由于是用老版本实现的所以fitsystemtrue方法没效果,需要手动设置view的高度
 * 兼容全部版本,就是要手动设置bar的高度
 * <p>
 */

public class StatusBarUtils {


    /**
     * Show full screen status. 全屏或者非全屏显示状态栏
     *
     * @param activity the activity
     */
    public static void showFullScreenStatus(Activity activity, SystemBarTintManager mTintManager) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        activity.getWindow().getDecorView().setSystemUiVisibility(option);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setStatusBarTintResource(R.color.black);
    }


    public static void showNormalScreenStatus(Activity activity, SystemBarTintManager mTintManager) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setStatusBarTintResource(R.color.black);

    }


    /**
     * Hide full screen status.  正常隐藏()
     *
     * @param activity the activity
     */
    public static void hideFullScreenStatus(Activity activity, SystemBarTintManager mTintManager) {
        //全屏
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //状态栏的字体是否出现
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        //主应用占据状态栏空间
        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

        activity.getWindow().getDecorView().setSystemUiVisibility(option);
        mTintManager.setStatusBarTintEnabled(false);

    }

    /**
     * Hide normal status. 切换到有状态栏非全屏的
     *
     * @param activity the activity
     */
    public static void hideNormalStatus(Activity activity, SystemBarTintManager mTintManager) {


        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setStatusBarTintResource(R.color.black);

    }

    public static void fixStatusMove(Activity activity) {

        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    /**
     * 获取状态栏高度
     *
     * @return px or 0
     */
    public static int getStatusBarHeight(Context context) {
        if (context == null) {
            return 0;
        }
        int statusBarHeight = 0;
        Class<?> c;
        Object obj;
        Field field;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        statusBarHeight= DensityUtil.px2dp(statusBarHeight);
        return statusBarHeight;
    }


    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


}
