package com.ushaqi.zhuishushenqi.httpcore.interceptor;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.ushaqi.zhuishushenqi.MyApplication;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by JackHu on 2019/5/16
 */
public class HeaderInterceptor implements Interceptor {
    private static final String APP_NAME = "ZhuiShuShenQi";
    private static final String NOT_FOUND = "not-found";
    private String mValueEncoded;
    private String mUserAgent;

    public HeaderInterceptor() {
        mValueEncoded = getValueEncoded(getUserAgent().trim());
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        return chain.proceed(getRequestByUrl(chain.request()));
    }

    private Request getRequestByUrl(Request originalRequest) {
        String url = originalRequest.url().toString();
        Request.Builder builder = originalRequest.newBuilder().method(originalRequest.method(), originalRequest.body());
//                .header("X-Device-Id", getDeviceIdByUrl(url))
//                .header("X-User-Agent", getUserAgent(url))
//                .header("x-android-id", AppHelper.getAndroidId())
//                .header("B-Zssq", AppHelper.base64DeviceInfo().trim())
//                .header("User-Agent", getUserAgent(url));
//        if (!"androidMaster".equals(GlobalConstants.APP_IDENTIFY)) {
//            builder.header("x-app-name", GlobalConstants.APP_IDENTIFY);
//        }
        return builder.build();
    }

    /**
     * 针对接口需求调整deviceid方法
     *
     * @param
     * @return
     */
//    private String getDeviceIdByUrl(String url) {
//        try {
//            if (url.contains(HEADER_FILTER_URL_INTEGRAL)) {
//                return AppHelper.getDeviceInfo(GlobalConfig.getInstance().getContext()).trim();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return AppHelper.getDeviceInfo(GlobalConfig.getInstance().getContext()).trim();
//    }
//
//    /**
//     * 针对刀版源需求调整
//     *
//     * @param url
//     * @return
//     */
//    private String getUserAgent(String url) {
//        if (url.contains(HEADER_FILTER_URL_TOC_DUMMY[0])) {
//            return HttpConfig.ssUA;
//        } else if (url.contains(HEADER_FILTER_URL_TOC_DUMMY[1])) {
//            return HttpConfig.sgUA;
//        } else if (url.contains(HEADER_FILTER_URL_TOC_DUMMY[2])) {
//            return HttpConfig.ldUA;
//        } else if (url.contains(HEADER_FILTER_URL_TOC_DUMMY[3])) {
//            return HttpConfig.esUA;
//        }
//        if (TextUtils.isEmpty(mValueEncoded)) {
//            mValueEncoded = getValueEncoded(getUserAgent().trim());
//        }
//        return mValueEncoded;
//    }

    private static String getValueEncoded(String value) {
        try {
            if (value == null) {
                return "null";
            }
            String newValue = value.replace("\n", "");
            String reg = "[\u4e00-\u9fa5]";
            Pattern pat = Pattern.compile(reg);
            Matcher mat = pat.matcher(newValue);
            String repickStr = mat.replaceAll("");

            for (int i = 0, length = repickStr.length(); i < length; i++) {
                char c = repickStr.charAt(i);
                if (c <= '\u001f' || c >= '\u007f') {
                    return URLEncoder.encode(repickStr, "UTF-8");
                }
            }
            return repickStr.trim();
        } catch (Exception e) {
            return "null";
        }
    }

    public String getUserAgent() {
        if (mUserAgent == null) {
            synchronized (HeaderInterceptor.class) {
                try {
                    Context app = MyApplication.getInstance();
                    PackageInfo info = null;
                    try {
                        info = app.getPackageManager().getPackageInfo(app.getPackageName(), 0);
                    } catch (PackageManager.NameNotFoundException ignored) {
                        //
                    }
                    String versionName = NOT_FOUND;
                    if (info != null) {
                        versionName = info.versionName;
                    }
                    String sim = NOT_FOUND;
                    TelephonyManager telephonyManager = (TelephonyManager) app.getSystemService(Context.TELEPHONY_SERVICE);
                    try {
                        sim = telephonyManager.getSimOperatorName();
                    } catch (Exception e) {
                        //
                    }
                    mUserAgent = String.format("%s/%s (Android %s; %s %s / %s %s; %s)",
                            APP_NAME,
                            versionName,
                            Build.VERSION.RELEASE,
                            capitalize(Build.MANUFACTURER),
                            capitalize(Build.DEVICE),
                            capitalize(Build.BRAND),
                            capitalize(Build.MODEL),
                            capitalize(sim)
                    );
                    ArrayList<String> params = new ArrayList<>();
                    // Determine if this app was a preloaded app
                    params.add("preload=" + ((app.getApplicationInfo().flags & ApplicationInfo.FLAG_SYSTEM) == 1));
                    params.add("locale=" + Locale.getDefault());

                    // http://stackoverflow.com/questions/2641111/where-is-android-os-systemproperties
                    try {
                        Class SystemProperties = app.getClassLoader().loadClass("android.os.SystemProperties");
                        Method get = SystemProperties.getMethod("get", String.class);
                        params.add("clientidbase=" + get.invoke(SystemProperties, "ro.com.google.clientidbase"));
                    } catch (Exception ignored) {
                        //
                    }

                    if (params.size() > 0) {
                        mUserAgent += "[" + TextUtils.join(";", params) + "]";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } //--synchronized end
        }
        return mUserAgent;
    }

    private String capitalize(String line) {
        if (line == null) {
            return NOT_FOUND;
        }
        switch (line.length()) {
            case 0:
                return line;
            case 1:
                return line.toUpperCase();
            default:
                return Character.toUpperCase(line.charAt(0)) + line.substring(1);
        }
    }

}
