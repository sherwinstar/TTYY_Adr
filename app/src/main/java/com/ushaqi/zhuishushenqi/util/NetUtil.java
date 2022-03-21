package com.ushaqi.zhuishushenqi.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;

import com.ushaqi.zhuishushenqi.MyApplication;

/**
 * 网络辅助类
 * Created by ccheng on 4/23/14.
 */
public class NetUtil {

    public static final int NETTYPE_WIFI = 0x01;
    public static final int NETTYPE_CMWAP = 0x02;
    public static final int NETTYPE_CMNET = 0x03;

    public static final String NETWORK_TYPE_NAME_2G = "2G";
    public static final String NETWORK_TYPE_NAME_3G = "3G";
    public static final String NETWORK_TYPE_NAME_4G = "4G";
    public static final String NETWORK_TYPE_NAME_WIFI = "WIFI";

    public static String curentNet;


    /**
     * 获取当前的运营商(1: 中国移动, 2: 中国联通, 3: 中国电信, 4: 其它运营商)
     *
     * @return 运营商
     */
    public static int getOperator() {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) MyApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
            @SuppressLint("MissingPermission") String IMSI = telephonyManager.getSubscriberId();
            if (IMSI != null) {
                if (IMSI.startsWith("46000") || IMSI.startsWith("46002") || IMSI.startsWith("46007")) {
                    return 1;
                } else if (IMSI.startsWith("46001")  || IMSI.startsWith("46006")) {
                    return 2;
                } else if (IMSI.startsWith("46003")) {
                    return 3;
                }
            } else {
                return 4;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 4;
    }



    /**
     * Get IP address from first non-localhost interface
     *
     * @return address or empty string
     */
    public static String getIPAddress(Context context) {
        WifiManager systemService = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return Formatter.formatIpAddress(systemService.getConnectionInfo().getIpAddress());
    }


    /**
     * 获取当前网络类型
     *
     * @return 0：没有网络 1：WIFI网络 2：WAP网络 3：NET网络
     */
    public static int getNetworkType(Context context) {
        int netType = 0;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo == null) {
                return netType;
            }
            int nType = networkInfo.getType();
            if (nType == ConnectivityManager.TYPE_MOBILE) {
                String extraInfo = networkInfo.getExtraInfo();
                if (!StringUtils.isEmpty(extraInfo)) {
                    if (extraInfo.toLowerCase().equals("cmnet")) {
                        netType = NETTYPE_CMNET;
                    } else {
                        netType = NETTYPE_CMWAP;
                    }
                }
            } else if (nType == ConnectivityManager.TYPE_WIFI) {
                netType = NETTYPE_WIFI;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return netType;
    }

    public static boolean isWifiConnect(Context context) {
        return getNetworkType(context) == 1;
    }

    /**
     * 检测当前网络状态
     *
     * @return 当且仅当已经连接或者正在连接网络时返回true, 否则返回false
     */
    public static boolean isNetworkConnect(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        }
        NetworkInfo info = cm.getActiveNetworkInfo();
        return (info != null) && info.isAvailable();
    }

    public static String getMacAddress(Context context) {
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wInfo = wifiManager.getConnectionInfo();
            return wInfo.getMacAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getNetworkTypeValue(final Context context) {
        final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            return "";
        }

        String strNetworkType = "";
        if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            strNetworkType = NETWORK_TYPE_NAME_WIFI;
        } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            String _strSubTypeName = networkInfo.getSubtypeName();
            // TD-SCDMA   networkType is 17
            final int networkType = networkInfo.getSubtype();
            switch (networkType) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                    /**
                     * api<8 : replace by 11
                     */
                case TelephonyManager.NETWORK_TYPE_IDEN:
                    strNetworkType = NETWORK_TYPE_NAME_2G;
                    break;
                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                    /**
                     * api<9 : replace by 14
                     */
                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    /**
                     * api<11 : replace by 12
                     */
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                    /**
                     * api<13 : replace by 15
                     */
                case TelephonyManager.NETWORK_TYPE_HSPAP:
                    strNetworkType = NETWORK_TYPE_NAME_3G;
                    break;
                /**
                 * api<11 : replace by 13
                 */
                case TelephonyManager.NETWORK_TYPE_LTE:
                    strNetworkType = NETWORK_TYPE_NAME_4G;
                    break;
                default:
                    if (_strSubTypeName != null && (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") ||
                            _strSubTypeName.equalsIgnoreCase("WCDMA") ||
                            _strSubTypeName.equalsIgnoreCase("CDMA2000"))) {
                        strNetworkType = NETWORK_TYPE_NAME_3G;
                    } else {
                        strNetworkType = _strSubTypeName;
                    }

                    break;
            }
        }

        return strNetworkType;
    }




}
