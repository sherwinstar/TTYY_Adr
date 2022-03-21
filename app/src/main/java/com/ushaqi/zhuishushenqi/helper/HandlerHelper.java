package com.ushaqi.zhuishushenqi.helper;

import android.os.Handler;
import android.os.Looper;

/**
 * 切换handler 线程
 * Created by fly on 2018/8/16.
 */

public class HandlerHelper {

    private static Handler handler = new Handler(Looper.getMainLooper());

    /**
     * post 主线程方法
     * add fly
     * @param runnable 执行方法
     */
    public static void post(Runnable runnable) {
        handler.post(runnable);
    }

    /**
     * post 延迟执行主线程方法
     * add fly
     * @param runnable 执行方法
     */
    public static void postDelay(Runnable runnable,long millis) {
        handler.postDelayed(runnable,millis);
    }

    /**
     * Remove any pending posts of Runnable r that are in the message queue
     *
     * @param r
     */
    public static void removeCallbacks(Runnable r) {
        if (r != null) {
            handler.removeCallbacks(r);
        }
    }

}
