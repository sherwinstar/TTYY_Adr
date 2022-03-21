package com.ushaqi.zhuishushenqi.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

/**
 * 获取设备的相关信息，如IMEI, Model
 */
public final class DeviceUtils {

    /**
     * mac地址
     */
    private static String mDeviceMac;

    private DeviceUtils() {

    }

    /**
     * Return the version name of device's system.
     */
    public static String getSdkVersionName() {
        return VERSION.RELEASE;
    }

    /**
     * @return version code of device's system
     */
    public static int getSdkVersionCode() {
        return VERSION.SDK_INT;
    }

    /**
     * @return the android id of device
     */
    @SuppressLint("HardwareIds")
    public static String getAndroidId(final Context context) {
        String id = Settings.Secure.getString(
                context.getContentResolver(),
                Settings.Secure.ANDROID_ID
        );

        return id == null ? "" : id;
    }

    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * @return the model of device
     */
    public static String getModel() {
        String model = Build.MODEL;
        if (model != null) {
            model = model.trim().replaceAll("\\s*", "");
        } else {
            model = "";
        }
        return model;
    }

    /**
     * Returns the unique device ID, for example, the IMEI for GSM and the MEID
     * or ESN for CDMA phones. Return null if device ID is not available
     *
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission")
    public static String getIMEI(final Context context) {
        try {
            if (VERSION.SDK_INT < 29){
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                return telephonyManager.getDeviceId();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission")
    public static String getIMSI(final Context context) {
        try {
            TelephonyManager tm =
                    (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tm != null ? tm.getSubscriberId() : "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取本机手机号码
     *
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission")
    public static String getPhoneNumber(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getLine1Number();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 获取内存中保存的mac地址
     *
     * @return
     */
    public static String getMacInMemory() {
//        if (mDeviceMac == null || mDeviceMac.isEmpty()) {
//            mDeviceMac = getMac();
//        }
//        return mDeviceMac;
        return "";
    }

    /**
     * 从系统文件中获取mac地址
     *
     * @return
     */
    private static String getMac() {
        String macSerial = "";
        try {
            Process pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String line;
            while ((line = input.readLine()) != null) {
                macSerial += line.trim();
            }
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return macSerial;
    }

    /**
     * 获取当前设备cpu核数
     */
    public static int getCpuCores() {
        int cores = 1;
        try {
            cores = new File("/sys/devices/system/cpu/").listFiles(CPU_FILTER).length;
        } catch (SecurityException e) {
        } catch (NullPointerException e) {
        }
        return cores;
    }

    private static final FileFilter CPU_FILTER = new FileFilter() {
        @Override
        public boolean accept(File pathname) {
            String path = pathname.getName();
            //regex is slow, so checking char by char.
            if (path.startsWith("cpu")) {
                for (int i = 3; i < path.length(); i++) {
                    if (path.charAt(i) < '0' || path.charAt(i) > '9') {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }
    };
}
