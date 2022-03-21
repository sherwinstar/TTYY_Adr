package com.ushaqi.zhuishushenqi.thread;

/**
 * Created by JackHu on 2018/4/21.
 */



import com.ushaqi.zhuishushenqi.util.DeviceUtils;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by JackHu on 2018/4/12.
 */
public class ThreadPoolFactory {
    private static final int CORE_CPU_CORE_SIZE = DeviceUtils.getCpuCores();
    private static final int CORE_POOL_SIZE = 8;
    private static final int MAXIMUM_POOL_SIZE = 32;
    private static final int KEEP_ALIVE = 2;
    private static final RejectedExecutionHandler sRejectedExecutionHandler = new ThreadPoolExecutor.DiscardOldestPolicy();
    private static int SCAN_POOL_SIZE = (int) (Math.floor(CORE_CPU_CORE_SIZE * 0.5f) + 1);
    private static final Object sThreadPoolLockObject = new Object();
    private static final Object sCompromiseThreadPoolLockObject = new Object();
    private static final Object sScanThreadPoolLockObject = new Object();
    /**
     * An {@link Executor} that can be used to execute tasks in parallel.
     */
    private volatile static ThreadPoolExecutor sThreadPool;
    /**
     * 针对社区设计不合理的折中处理
     * 并且采用懒加载
     */
    private volatile static ThreadPoolExecutor sCompromiseThreadPool;
    /**
     * 本地书文件扫描专用
     */
    private volatile static ThreadPoolExecutor sScanThreadPool;
    /**
     * 数据库操作
     */
    private volatile static ExecutorService sDBThreadPool;
    /**
     * 书架
     */
    private volatile static ExecutorService sBookShelfThreadPool;

    private static final Executor sSensorsExposurePool = Executors.newFixedThreadPool(2);

    @Deprecated
    public static ThreadPoolExecutor getThreadPool() {
        if (sThreadPool == null) {
            synchronized (sThreadPoolLockObject) {
                if (sThreadPool == null) {
                    sThreadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(64), sRejectedExecutionHandler);
                    sThreadPool.setThreadFactory(ZSThreadFactory.common());
                }
            }
        }
        return sThreadPool;
    }

    /**
     * 针对社区页面特殊处理，设计不合理，这个方法迟早要去掉，折中处理
     */
    @Deprecated
    public static ThreadPoolExecutor getFixedCountThreadPool() {
        if (sCompromiseThreadPool == null) {
            synchronized (sCompromiseThreadPoolLockObject) {
                if (sCompromiseThreadPool == null) {
                    sCompromiseThreadPool = new ThreadPoolExecutor(0, MAXIMUM_POOL_SIZE, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(256), sRejectedExecutionHandler);
                    sCompromiseThreadPool.setThreadFactory(ZSThreadFactory.fix());
                }
            }
        }
        return sCompromiseThreadPool;
    }


    public static ThreadPoolExecutor getScanThreadPool() {
        if (sScanThreadPool == null) {
            synchronized (sScanThreadPoolLockObject) {
                if (sScanThreadPool == null) {
                    sScanThreadPool = new ZSThreadPoolExecutor(SCAN_POOL_SIZE, SCAN_POOL_SIZE, 10, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), sRejectedExecutionHandler);
                    sScanThreadPool.setThreadFactory(ZSThreadFactory.scan());
                }
            }
        }
        return sScanThreadPool;
    }

    public static void resetScanPool() {
        if (sScanThreadPool != null && sScanThreadPool.isShutdown() && sScanThreadPool.isTerminated()) {
            synchronized (sScanThreadPoolLockObject) {
                if (sScanThreadPool != null && sScanThreadPool.isShutdown() && sScanThreadPool.isTerminated()) {
                    sScanThreadPool = null;
                }
            }
        }
    }

    /**
     * 获取数据库线程池
     * 适合单线程
     */
    public static ExecutorService getDBThreadPool() {
        if (sDBThreadPool == null) {
            sDBThreadPool = Executors.newSingleThreadExecutor(ZSThreadFactory.db());
        }
        return sDBThreadPool;
    }

    public static void shutdownScanNow() {
        if (sScanThreadPool != null && !sScanThreadPool.isShutdown() && !sScanThreadPool.isTerminated()) {
            try {
                sScanThreadPool.shutdownNow();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取开屏初始化线程池
     */
    public ExecutorService getAppStartThreadPool() {
        if (sBookShelfThreadPool == null) {
            sBookShelfThreadPool = Executors.newFixedThreadPool(3, ZSThreadFactory.appStart());
        }
        return sBookShelfThreadPool;
    }

    /**
     * 获取书架加载线程池
     */
    public static ExecutorService getBookShelfThreadPool() {
        return Executors.newCachedThreadPool(ZSThreadFactory.bookshelf());
    }

    /**
     * 获取书城加载线程池
     */
    public ThreadPoolExecutor getBookCityThreadPool() {
        return null;
    }

    /**
     * 获取阅读器加载线程池
     */
    public ThreadPoolExecutor getReaderThreadPool() {
        return null;
    }

    /**
     * 获取社区线程池
     */
    public ThreadPoolExecutor getCommunityThreadPool() {
        return null;
    }

    /**
     * 获取广告线程池
     */
    public ThreadPoolExecutor getAdvertThreadPool() {
        return null;
    }

    /**
     * 获取埋点线程池
     */
    public ThreadPoolExecutor geRecordThreadPool() {
        return null;
    }

    /**
     * 神策曝光使用的线程池
     * @return
     */
    public static Executor getSensorsExposurePool(){
        return sSensorsExposurePool;
    }
}

