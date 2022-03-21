package com.ushaqi.zhuishushenqi.util;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ushaqi.zhuishushenqi.MyApplication;



import com.ushaqi.zhuishushenqi.widget.ToastCompat;

import java.text.MessageFormat;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;

/**
 * Toast工具类
 *
 * @author Shaojie
 * @Date 2013-9-26 下午7:08:47
 */
public class ToastUtil {

    /**
     * Show the given message in a {@link Toast}
     * <p/>
     * This method may be called from any thread
     */
    public static void show(Activity activity, String message) {
        if (activity == null) {
            return;
        }
        show(activity, message, LENGTH_SHORT);
    }

    public static void show(String message) {

        show(MyApplication.getInstance(), message, LENGTH_SHORT);
    }

    public static void showLong(String message) {
        show(MyApplication.getInstance(), message, LENGTH_LONG);
    }

    /**
     * Show the message with the given resource id in a {@link Toast}
     * <p/>
     * This method may be called from any thread
     */
    public static void show(Activity activity, int resId) {
        if (activity == null) {
            return;
        }
        show(activity, activity.getString(resId));
    }

    /**
     * 在主线程显示(activity或service)
     */
    public static void showAtUI(Context context, String message) {
        showAtUI(context, message, LENGTH_SHORT);
    }

    /**
     * 在主线程显示(activity或service)
     */
    public static void showAtUI(String message) {
        showAtUI(MyApplication.getInstance(), message, LENGTH_SHORT);
    }

    public static void show(Context context, String message) {
        show(context, message, LENGTH_SHORT);
    }

    /**
     * Show message in {@link Toast} with {@link Toast#LENGTH_LONG} duration
     */
    public static void showLong(Activity activity, int resId) {
        show(activity, resId, LENGTH_LONG);
    }

    /**
     * Show message in {@link Toast} with {@link Toast#LENGTH_LONG} duration
     */
    public static void showLong(Activity activity, String message) {
        show(activity, message, LENGTH_LONG);
    }

    /**
     * Show message in {@link Toast} with {@link Toast#LENGTH_LONG} duration
     *
     * @param activity
     * @param message
     * @param args
     */
    public static void showLong(Activity activity, String message, Object... args) {
        String formatted = MessageFormat.format(message, args);
        show(activity, formatted, LENGTH_LONG);
    }

    /**
     * Show message in {@link Toast} with {@link Toast#LENGTH_SHORT} duration
     *
     * @param activity
     * @param message
     * @param args
     */
    public static void showShort(Activity activity, String message, Object... args) {
        String formatted = MessageFormat.format(message, args);
        show(activity, formatted, LENGTH_SHORT);
    }

    /**
     * Show message in {@link Toast} with {@link Toast#LENGTH_LONG} duration
     *
     * @param activity
     * @param resId
     * @param args
     */
    public static void showLong(Activity activity, int resId, Object... args) {
        if (activity == null) {
            return;
        }
        String message = activity.getString(resId);
        showLong(activity, message, args);
    }

    /**
     * Show message in {@link Toast} with {@link Toast#LENGTH_SHORT} duration
     *
     * @param activity
     * @param resId
     * @param args
     */
    public static void showShort(Activity activity, int resId, Object... args) {
        if (activity == null) {
            return;
        }
        String message = activity.getString(resId);
        showShort(activity, message, args);
    }

    private static void show(Activity activity, final int resId, final int duration) {
        if (activity == null) {
            return;
        }
        final Context context = activity.getApplication();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastCompat.makeText(context, resId, duration).show();
            }
        });
    }

    private static void show(Activity activity, final String message, final int duration) {
        if (activity == null || message == null) {
            return;
        }
        final Context context = activity.getApplication();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastCompat.makeText(context, message, duration).show();
            }
        });
    }

    /**
     * 在主线程显示(activity或service)
     */
    private static void showAtUI(Context context, String message, int duration) {
        if (context == null || message == null) {
            return;
        }
        ToastCompat.makeText(context, message, duration).show();
    }

    /**
     * 自定义的toast
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(final Context context, final String message, final int duration) {
        try {
            if (context == null || TextUtils.isEmpty(message)) {
                return;
            }
            ToastCompat.makeText(context, message, duration).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
