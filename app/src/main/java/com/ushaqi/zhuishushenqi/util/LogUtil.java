/*
 * File Name: LogUtils.java
 * History:
 * Created by Siyang.Miao on 2012-2-20
 */
package com.ushaqi.zhuishushenqi.util;

import android.text.TextUtils;
import android.util.Log;


import com.ushaqi.zhuishushenqi.BuildConfig;

import java.io.PrintWriter;
import java.io.StringWriter;

public class LogUtil {
    // ==========================================================================
    // Constants
    // ==========================================================================
    // Max value is 3984
    private static final int MAX_LOG_LINE_LENGTH = 2048;

    // ==========================================================================
    // Fields
    // ==========================================================================
    private static String sTag = "zhuishushenqi----";

    public static boolean sDebuggable =true;//BuildConfig.DEBUG;
    private static long sTimestamp = 0;

    // ==========================================================================
    // Constructors
    // ==========================================================================

    // ==========================================================================
    // Getters
    // =================

    // ==========================================================================
    // Setters
    // ==========================================================================

    // ==========================================================================
    // Methods
    // ==========================================================================
    public static void setTag(String tag) {
        sTag = tag;
    }

    public static void i(String tag, String msg) {
        if (sDebuggable) {
            if (null != msg && msg.length() > 0) {
                int start = 0;
                int end = 0;
                int len = msg.length();
                while (true) {
                    start = end;
                    end = start + MAX_LOG_LINE_LENGTH;
                    if (end >= len) {
                        Log.i(sTag + tag, msg.substring(start, len));
                        break;
                    } else {
                        Log.i(sTag + tag, msg.substring(start, end));
                    }
                }
            } else {
                Log.i(sTag, msg);
            }
        }
    }

    public static void v(String tag, String msg) {
        if (sDebuggable) {
            if (null != msg && msg.length() > 0) {
                int start = 0;
                int end = 0;
                int len = msg.length();
                while (true) {
                    start = end;
                    end = start + MAX_LOG_LINE_LENGTH;
                    if (end >= len) {
                        Log.v(sTag + tag, msg.substring(start, len));
                        break;
                    } else {
                        Log.v(sTag + tag, msg.substring(start, end));
                    }
                }
            } else {
                Log.v(sTag, msg);
            }
        }
    }

    public static void d(String tag, String msg) {
        if (sDebuggable) {
            if (null != msg && msg.length() > 0) {
                int start = 0;
                int end = 0;
                int len = msg.length();
                while (true) {
                    start = end;
                    end = start + MAX_LOG_LINE_LENGTH;
                    if (end >= len) {
                        Log.d(sTag + tag, msg.substring(start, len));
                        break;
                    } else {
                        Log.d(sTag + tag, msg.substring(start, end));
                    }
                }
            } else {
                Log.d(sTag, msg);
            }
        }
    }

    public static void w(String tag, String msg) {
        if (sDebuggable) {
            if (null != msg && msg.length() > 0) {
                int start = 0;
                int end = 0;
                int len = msg.length();
                while (true) {
                    start = end;
                    end = start + MAX_LOG_LINE_LENGTH;
                    if (end >= len) {
                        Log.w(sTag + tag, msg.substring(start, len));
                        break;
                    } else {
                        Log.w(sTag + tag, msg.substring(start, end));
                    }
                }
            } else {
                Log.w(sTag, msg);
            }
        }
    }

    public static void w(String tag, Throwable tr) {
        if (sDebuggable) {
            Log.w(sTag + tag, "", tr);
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (sDebuggable && null != msg) {
            Log.w(sTag + tag, msg, tr);
        }
    }

    public static void e(String tag, String msg) {
        if (sDebuggable) {
            if (null != msg && msg.length() > 0) {
                int start = 0;
                int end = 0;
                int len = msg.length();
                while (true) {
                    start = end;
                    end = start + MAX_LOG_LINE_LENGTH;
                    if (end >= len) {
                        Log.e(sTag + tag, msg.substring(start, len));
                        break;
                    } else {
                        Log.e(sTag + tag, msg.substring(start, end));
                    }
                }
            } else {
                Log.e(sTag, msg);
            }
        }
    }

    public static void e(String tag, Throwable tr) {
        if (sDebuggable) {
            Log.e(sTag + tag, "", tr);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (sDebuggable) {
            Log.e(sTag + tag, msg, tr);
        }
    }

    public static void markStart(String msg) {
        sTimestamp = System.currentTimeMillis();
        if (!TextUtils.isEmpty(msg)) {
            e("", "[Started|" + sTimestamp + "]" + msg);
        }
    }

    public static void elapsed(String msg) {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - sTimestamp;
        sTimestamp = currentTime;
        e("", "[Elapsed|" + elapsedTime + "]" + msg);
    }

    public static boolean isDebugable() {
        return sDebuggable;
    }

    public static void setDebugable(boolean debugable) {
        sDebuggable = debugable;
    }

    public static String getStackTraceString(Throwable tr) {
        if (tr == null) {
            return "";
        }
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        return sw.toString();
    }

    // ==========================================================================
    // Inner/Nested Classes
    // ==========================================================================
}
