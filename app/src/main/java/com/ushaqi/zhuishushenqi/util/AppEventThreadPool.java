package com.ushaqi.zhuishushenqi.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by shijingxing on 2018/9/11.
 */

public class AppEventThreadPool {

    private static int mThradSize = 2;

    private static ExecutorService sAppeventFixedThreadPool;

    public static ExecutorService getAppEventThreadPool() {
        if (sAppeventFixedThreadPool == null) {
            synchronized (AppEventThreadPool.class) {
                if (sAppeventFixedThreadPool == null) {
                    sAppeventFixedThreadPool = Executors.newFixedThreadPool(mThradSize);
                }
            }
        }
        return sAppeventFixedThreadPool;
    }
}
