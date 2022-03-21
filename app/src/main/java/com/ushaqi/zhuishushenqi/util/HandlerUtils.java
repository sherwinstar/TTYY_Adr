package com.ushaqi.zhuishushenqi.util;

import android.os.Handler;
import android.os.Looper;

/**
 * 主线程Handler
 */

public class HandlerUtils {

    private static Handler sHandler = new Handler(Looper.getMainLooper());

    /**
     * post 主线程方法
     * @param runnable 执行方法
     */
    public static void post(Runnable runnable) {
        sHandler.post(runnable);
    }

    /**
     * post 延迟执行主线程方法
     * @param runnable 执行方法
     */
    public static void postDelay(Runnable runnable,long millis) {
        sHandler.postDelayed(runnable,millis);
    }
}
