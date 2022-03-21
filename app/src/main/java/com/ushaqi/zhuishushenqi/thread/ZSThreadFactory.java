package com.ushaqi.zhuishushenqi.thread;

import android.os.Process;


import androidx.annotation.NonNull;

import com.ushaqi.zhuishushenqi.util.LogUtil;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

public class ZSThreadFactory extends AtomicLong implements ThreadFactory {

    private static final long serialVersionUID = -5415202000145026292L;
    private String mName;
    private int mPriority;

    /**
     * 数据库操作单线程即可
     * @return
     */
    public static ZSThreadFactory db() {
        return new ZSThreadFactory("db", Process.THREAD_PRIORITY_BACKGROUND);
    }

    /**
     * app启动配置单独线程池
     * @return
     */
    public static ZSThreadFactory appStart() {
        return new ZSThreadFactory("start", Process.THREAD_PRIORITY_BACKGROUND);
    }

    public static ZSThreadFactory scan() {
        return new ZSThreadFactory("local", Process.THREAD_PRIORITY_DISPLAY);
    }
    public static ZSThreadFactory scanControl() {
        return new ZSThreadFactory("local-control", Process.THREAD_PRIORITY_DEFAULT);
    }
    public static ZSThreadFactory bookshelf() {
        return new ZSThreadFactory("shelf", Process.THREAD_PRIORITY_BACKGROUND);
    }

    private ZSThreadFactory(String zsName, int priority) {
        this.mName = zsName;
        this.mPriority = priority;
    }

    public static ThreadFactory common() {
        return new ZSThreadFactory("common", Process.THREAD_PRIORITY_BACKGROUND);
    }

    public static ThreadFactory fix() {
        return new ZSThreadFactory("fix", Process.THREAD_PRIORITY_DEFAULT);
    }

    @Override
    public Thread newThread(@NonNull Runnable runnable) {
        Thread t = new Thread(runnable, "ZS_T_" + mName + "_" + incrementAndGet());
        LogUtil.d("ZSThreadFactory newThread", "ZS_Thread_" + mName + "_" + get());
        if (mPriority > 0) {
            t.setPriority(mPriority);
        }
        return t;
    }

}
