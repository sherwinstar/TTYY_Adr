package com.ushaqi.zhuishushenqi.httpcore;


import com.ushaqi.zhuishushenqi.thread.ThreadPoolFactory;

import java.util.concurrent.ExecutorService;

/**
 * 获取线程池
 * Created by fly on 2016/10/28 0028.
 */
public class ThreadPool {
    /**
     * 线程管理服务
     * add fly
     */
    private static ExecutorService fixedThreadPool;
    private static ExecutorService singleThreadPool;

    /**
     * 功能方法
     * add fly
     */
    private static ExecutorService functionThreadPool;

    /**
     * 获取线程池线程执行对象
     * <p>单例</p>
     * add fly
     *
     * @return
     */
    public static ExecutorService getExecutorServiceInstance() {
        return ThreadPoolFactory.getThreadPool();
    }

    /**
     * 获取单线程池线程执行对象
     * <p>单例</p>
     * add fly
     *
     * @return
     */
    public static ExecutorService getSingleExecutorServiceInstance() {
       /* if (singleThreadPool == null) {
            synchronized (ThreadPool.class) {
                if (singleThreadPool == null) {
                    singleThreadPool = Executors.newSingleThreadExecutor();
                }
            }
        }

        return singleThreadPool;*/
        return ThreadPoolFactory.getThreadPool();
    }

    public static ExecutorService getFunctionExecutorServiceInstance() {
//        if (functionThreadPool == null) {
//            int availableProcessors = Runtime.getRuntime().availableProcessors();
//            functionThreadPool = Executors.newFixedThreadPool(availableProcessors);
//        }
//        return functionThreadPool;
        return ThreadPoolFactory.getThreadPool();
    }

}
