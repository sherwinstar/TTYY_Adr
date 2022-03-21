package com.ushaqi.zhuishushenqi.thread;



import com.ushaqi.zhuishushenqi.util.LogUtil;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class ZSThreadPoolExecutor extends ThreadPoolExecutor {
    public ZSThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public ZSThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public ZSThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public ZSThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
       // LogUtil.d("jackHu", "beforeExecute:" + t.getName() + " " + r.toString());
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        //LogUtil.d("jackHu", "afterExecute:" + " " + r.toString() + " " + (t != null ? t.getMessage() : ""));
    }

    @Override
    public boolean remove(Runnable task) {
        //LogUtil.d("jackHu", "remove:" + " " + task.toString());
        return super.remove(task);
    }

    @Override
    public List<Runnable> shutdownNow() {
        LogUtil.d("jackHu", "--------------shutdownNow---------------");
        return super.shutdownNow();
    }

    @Override
    public void shutdown() {
        super.shutdown();
        LogUtil.d("jackHu", "--------------shutdown---------------");
    }
}
