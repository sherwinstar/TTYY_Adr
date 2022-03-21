package com.ushaqi.zhuishushenqi.plugin.social;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.ushaqi.zhuishushenqi.plugin.BuildConfig;
import com.ushaqi.zhuishushenqi.plugin.social.wechat.WeChatCore;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Andy.zhang
 * @date 2019/4/24
 */
public final class SocialUtils {

    /**
     * @param context
     * @param msg
     */
    public static void showToast(final Context context, final String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * @param tag
     * @param msg
     */
    public static void printLog(final String tag, final String msg) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, msg);
        }
    }

    /**
     * @param context
     * @param permission
     * @return
     */
    public static boolean hasPermission(Context context, String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M
                || context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * @param info
     * @return
     */
    public static String getMD5(String info) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(info.getBytes("UTF-8"));
            byte[] encryption = md5.digest();
            StringBuilder strBuf = new StringBuilder();
            for (byte anEncryption : encryption) {
                if (Integer.toHexString(0xff & anEncryption).length() == 1) {
                    strBuf.append("0").append(Integer.toHexString(0xff & anEncryption));
                } else {
                    strBuf.append(Integer.toHexString(0xff & anEncryption));
                }
            }
            return strBuf.toString();
        } catch (NoSuchAlgorithmException e) {
            return "";
        } catch (UnsupportedEncodingException e) {
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 检测微信是否安装/是否支持微信支付
     *
     * @return
     */
    public static boolean checkWXSupport() {
        IWXAPI iwxapi = WeChatCore.getsInstance().getWxApi();
        return (iwxapi != null) && iwxapi.isWXAppInstalled() && (iwxapi.getWXAppSupportAPI() >= com.tencent.mm.opensdk.constants.Build.PAY_SUPPORTED_SDK_INT);
    }
    public static boolean isWeiXinInstalled(Context context) {
        WeChatCore.getsInstance().registerAppToWechat(context);
        IWXAPI iwxapi = WeChatCore.getsInstance().getWxApi();
        return iwxapi != null && iwxapi.isWXAppInstalled();
    }

}
