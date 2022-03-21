package com.ushaqi.zhuishushenqi.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.os.Process;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.ali.auth.third.core.util.Base64;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;


/**
 * @author Andy.zhang
 * @date 2018/8/1
 */
public final class AppUtils {

    private AppUtils() {
        throw new UnsupportedOperationException("AppUtils can't be instantiated");
    }

    /**
     * 获取应用信息
     *
     * @param context
     * @return
     */
    public static PackageInfo getPackageInfo(final Context context) {
        return getPackageInfo(context, context.getPackageName());
    }

    /**
     * @param context
     * @param packageName
     * @return
     */
    public static PackageInfo getPackageInfo(final Context context, final String packageName) {
        synchronized (AppUtils.class) {
            PackageInfo packInfo = null;
            try {
                PackageManager packageManager = context.getPackageManager();
                packInfo = packageManager.getPackageInfo(packageName, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return packInfo;
        }
    }

    /***
     * 是否是主线程
     * @return
     */
    public static boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    /**
     * 是否是主进程
     *
     * @param context
     * @return
     */
    public static boolean isMainProcess(final Context context) {
        final String packageName = context.getPackageName();
        return TextUtils.equals(packageName, getProcessName(context));
    }

    public static String getProcessName(Context context){
        String processName = getProcessNameByActivityThread();
        if(processName == null){
            processName = getProcessNameByPid(Process.myPid());
        }
        if(processName == null){
            processName = getProcessNameByAM(context);
        }
        return processName;
    }

    /**
     * Return the process name related to pid
     *
     * @param pid
     * @return
     */
    public static String getProcessNameByPid(final int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * 通过反射ActivityThread获取进程名，避免了ipc
     */
    public static String getProcessNameByActivityThread() {
        String processName = null;
        try {
            final Method declaredMethod = Class.forName("android.app.ActivityThread", false, Application.class.getClassLoader())
                    .getDeclaredMethod("currentProcessName", (Class<?>[]) new Class[0]);
            declaredMethod.setAccessible(true);
            final Object invoke = declaredMethod.invoke(null, new Object[0]);
            if (invoke instanceof String) {
                processName = (String) invoke;
            }
        } catch (Throwable e) {
        }
        return processName;
    }



    /**
     * 通过Application新的API获取进程名。需要Api28支持
     */
   /* public static String getProcessNameByApplication() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return Application.getProcessName();
        }
        return null;
    }*/

    /**
     * Return the process name
     *
     * @param context
     * @return
     */
    public static String getProcessNameByAM(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }

        for (ActivityManager.RunningAppProcessInfo proInfo : runningApps) {
            if (proInfo.pid == Process.myPid()) {
                if (proInfo.processName != null) {
                    return proInfo.processName;
                }
            }
        }

        return null;
    }

    /**
     * return whether this process is named with processName
     *
     * @param context
     * @param processName
     * @return
     */
    public static boolean hasNamedProcess(final Context context, final String processName) {
        if (context == null || TextUtils.isEmpty(processName)) {
            return false;
        }

        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfoList = manager.getRunningAppProcesses();
        if (processInfoList == null || processInfoList.isEmpty()) {
            return false;
        }

        for (ActivityManager.RunningAppProcessInfo processInfo : processInfoList) {
            if (processInfo != null && TextUtils.equals(processName, processInfo.processName)) {
                return true;
            }
        }

        return false;
    }


    /**
     * Return the version name of this package
     *
     * @param context
     * @return
     */
    public static String getVersionName(final Context context) {
        final PackageInfo packageInfo = getPackageInfo(context);
        return getVersionName(packageInfo);
    }

    /**
     * Return the version name of this package
     *
     * @param packageInfo
     * @return
     */
    public static String getVersionName(final PackageInfo packageInfo) {
        return (packageInfo != null) ? packageInfo.versionName : "";
    }

    /**
     * Return the version number of this package
     *
     * @param context
     * @return
     */
    public static int getVersionCode(final Context context) {
        final PackageInfo packageInfo = getPackageInfo(context);
        return getVersionCode(packageInfo);
    }

    /**
     * Return the version number of this package
     *
     * @param packageInfo
     * @return
     */
    public static int getVersionCode(final PackageInfo packageInfo) {
        return (packageInfo != null) ? packageInfo.versionCode : 0;
    }


    /**
     * 启动App
     *
     * @param context
     * @param packageName
     */
    public static void launchApp(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(packageName);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断是否安装应用
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAppInstalled(final Context context, final String packageName) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (Exception e) {
            packageInfo = null;
        } finally {
            return (packageInfo != null);
        }
    }

    public static boolean isActivityInValid(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return (activity == null || activity.isFinishing() || activity.isDestroyed());
        } else {
            return (activity == null || activity.isFinishing());
        }
    }


    /**
     * 获取剪切板上的内容
     */
    @Nullable
    public static String getClipboardContent(Context context) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        try {
            if (cm != null) {
                ClipData data = cm.getPrimaryClip();
                if (data != null && data.getItemCount() > 0) {
                    ClipData.Item item = data.getItemAt(0);
                    if (item != null) {
                        CharSequence sequence = item.coerceToText(context);
                        if (sequence != null) {
                            return sequence.toString();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


    public static void clearClipboardContent(Context context) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            if (cm != null) {
                cm.setPrimaryClip(cm.getPrimaryClip());
                cm.setText(null);
            }
    }


    public static String getApkFileName(String finalUrl) {
        if (finalUrl == null) {
            return "";
        }
        String fileName = Base64.encode(EncryptUtil.md5(finalUrl)) + ".apk";
        return fileName;
    }





}
