package com.ushaqi.zhuishushenqi.channel;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import androidx.core.app.ActivityCompat;


import com.signapk.walle.WalleChannelReader;

import com.ushaqi.zhuishushenqi.AppConstants;
import com.ushaqi.zhuishushenqi.BuildConfig;
import com.ushaqi.zhuishushenqi.HttpExceptionMessage;
import com.ushaqi.zhuishushenqi.MyApplication;

import com.ushaqi.zhuishushenqi.model.ChannelInfoResult;
import com.ushaqi.zhuishushenqi.model.CpsChannelInfo;

import com.ushaqi.zhuishushenqi.repository.GlobalPreference;
import com.ushaqi.zhuishushenqi.sensors.SensorsAnalysisConstants;
import com.ushaqi.zhuishushenqi.sensors.SensorsAnalysisManager;
import com.ushaqi.zhuishushenqi.sensors.SensorsUploadHelper;

import com.ushaqi.zhuishushenqi.util.AppHelper;

import com.ushaqi.zhuishushenqi.util.FileHelper;
import com.ushaqi.zhuishushenqi.util.GsonHelper;
import com.ushaqi.zhuishushenqi.util.LogUtil;


import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * <p>
 *
 * @ClassName: ChannelManager
 * @Date: 2019-11-01 13:33
 * @Author: jared
 * @Org: 上海元聚网络科技有限公司
 * @Description: 获取渠道和cpsId的管理类
 * 获取的渠道信息分两种：
 * 1、投放的渠道和cpsId,通过BI的接口下发后，存入本地文件，每次获取的时候从本地文件中获取getCpsChannel,getCpsId
 * 2、apk的市场渠道，通过打包工具或者gradle直接配置在apk包里，固定的接口需要的时候传递，获取当投放渠道去不到的时候获取
 * <p>
 * 未获取相关权限时候，拿不到设备信息，友盟不进行初始化，也不会调用Bi的接口获取投放渠道的信息；
 * 待获取到相关权限之后，通过调用Bi的接口获取投放的渠道信息并存入本地。
 * 下次启动先检查本地是否有投放渠道的信息，如果有直接获取，并且同步初始化友盟，并且传入相应的渠道信息，没有直接传入APK的市场渠道信息（考虑到友盟初始化的特殊性，初始化一次之后，后面再初始化并不会重置渠道信息，影响统计）
 * </p>
 */
public class ChannelManager {

    private static final String CPS_INFO_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/.Android/system/core";
    private static final String CPS_INFO_PATH_INNER_APK = MyApplication.getInstance().getApplicationContext().getFilesDir().getAbsolutePath() + "/core";
    private static final String CPS_INFO_FILE_NAME = "cpsInfo.txt";

    public static final String UMENG_KEY = "5c00fbe2f1f5564fd100005e";

    private static final String LAST_REAL_CHANNEL_KEY = "last_real_channel_key";
    private static final String LAST_REAL_CHANNEL_ID_KEY = "last_real_channel_id_key";

    private static final String PRE_ASSEMBLE_INFO_PATH = "/etc";
    private static final String PRE_ASSEMBLE_INFO_FILE_NAME = "zhuishu.txt";

    private static final String GET_CPS_INFO_FROM_SERVER_KEY = "get_cps_info_from_server_key";

    private static final String CPS_INFO_URL = "http://data.1391.com:60005/behavior/clicking/attribution/channel";

    private static final String REAL_TIME_CHANNEL_URL = "http://data.1391.com:60006/behavior/putin/channel";

    public static CpsChannelInfo sCpsChannelInfo;

    private static final String X_TOKEN = "9afb2dg61f7a71718e906d6698";

    private static String sRealChannel;
    private static String sRealChannelId;
    private static String sChildChannelId;
    private static String sChildChannelName;
    private static String sBusinessName;
    private static long sAdClickTime;

    private static Handler sHandler = new Handler(Looper.getMainLooper());

    /**
     * 是否代码设置过Umeng的channel
     */
    public static boolean mHasSetUmChannel = false;

    public static CpsChannelInfo getCpsChannelInfo() {
        if (sCpsChannelInfo == null) {
            sCpsChannelInfo = getCpsInfoFromLocalData();
        }
        return sCpsChannelInfo;
    }

    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019-11-01 15:48
     * @Description 获取渠道信息，本地有投放渠道直接获取投放渠道，没有就获取市场渠道
     */
    public static String getChannel() {
        if (!TextUtils.isEmpty(sRealChannel) && !"-1".equals(sRealChannel)){
            return sRealChannel;
        }
        String localRealChannel = GlobalPreference.getInstance().getString(LAST_REAL_CHANNEL_KEY, "-1");
        if (!TextUtils.isEmpty(localRealChannel) && !"-1".equals(localRealChannel)){
            sRealChannel = localRealChannel;
            return sRealChannel;
        }
        if (sCpsChannelInfo == null) {
            sCpsChannelInfo = getCpsInfoFromLocalData();
        }
        return getChannel(sCpsChannelInfo);
    }

    public static String getChannel(CpsChannelInfo info) {
        return TextUtils.isEmpty(getCpsChannel(info)) ? getApkMarketChannel() : getCpsChannel(info);
    }

    public static String getRealTimeChannel() {
        return TextUtils.isEmpty(sRealChannel) ? "" : sRealChannel;
    }
    public static String getRealTimeChannelId() {
        return TextUtils.isEmpty(sRealChannelId) ? "" : sRealChannelId;
    }

    public static String getChildChannelName() {
        return (!TextUtils.isEmpty(sChildChannelName) && !"-1".equals(sChildChannelName)) ? sChildChannelName : "";
    }

    public static String getChildChannelId() {
        return (!TextUtils.isEmpty(sChildChannelId) && !"-1".equals(sChildChannelId)) ? sChildChannelId : "";
    }

    public static String getBusinessName() {
        return (!TextUtils.isEmpty(sBusinessName) && !"-1".equals(sBusinessName)) ? sBusinessName : "";
    }

    /**
     * 灰度标识渠道
     *
     * @return
     */
    public static String getGrayTestMark() {
        return BuildConfig.VERSION_NAME;
    }

//    public static String getAdClickTime() {
//        if (sAdClickTime == 0) {
//            return "";
//        }
//        return DateHelper.forMatStamp(sAdClickTime);
//    }

    public static String getRealTimeChannelForProducts() {
        if (TextUtils.isEmpty(sRealChannel)) {
            return getChannel();
        }
        if (sRealChannel.equals("-1")) {
            return getChannel();
        }
        return sRealChannel;
    }


    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019-11-01 15:50
     * @Description 获取cpsId，本地如果有投放的cpsId直接获取，没有就获取市场的cpsId
     */
    public static String getChannelId() {
        if (!TextUtils.isEmpty(sRealChannelId) && !"-1".equals(sRealChannelId)){
            return sRealChannelId;
        }
        String localRealChannelId = GlobalPreference.getInstance().getString(LAST_REAL_CHANNEL_ID_KEY, "-1");
        if (!TextUtils.isEmpty(localRealChannelId) && !"-1".equals(localRealChannelId)){
            sRealChannelId = localRealChannelId;
            return sRealChannelId;
        }
        if (sCpsChannelInfo == null) {
            sCpsChannelInfo = getCpsInfoFromLocalData();
        }
        return getChannelId(sCpsChannelInfo);
    }

    public static String getChannelId(CpsChannelInfo info) {
        return TextUtils.isEmpty(getCpsId(info)) ? getApkMarketChannelId() : getCpsId(info);
    }


    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019-11-01 14:00
     * @Description 从本地获取cps信息放入数组
     */
    private static CpsChannelInfo getCpsInfoFromLocalData() {
        if (hasReadWritePermission()) {
            if (FileHelper.isExitFile(CPS_INFO_PATH + "/" + CPS_INFO_FILE_NAME)) {
                return CpsChannelInfo.fromJson(FileHelper.readTextFromLocalFile(CPS_INFO_PATH + "/" + CPS_INFO_FILE_NAME));
            } else {
                CpsChannelInfo info = CpsChannelInfo.fromJson(FileHelper.readTextFromLocalFile(CPS_INFO_PATH_INNER_APK + "/" + CPS_INFO_FILE_NAME));
                FileHelper.writeTextToLocalFile(CPS_INFO_PATH, CPS_INFO_FILE_NAME, CpsChannelInfo.toJson(info));
            }
        }
        return CpsChannelInfo.fromJson(FileHelper.readTextFromLocalFile(CPS_INFO_PATH_INNER_APK + "/" + CPS_INFO_FILE_NAME));
    }

    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019-11-01 15:10
     * @Description 将cps信息写入本地
     */
    private static void putCpsInfoToLocal(CpsChannelInfo info) {
        if (hasReadWritePermission()) {
            FileHelper.writeTextToLocalFile(CPS_INFO_PATH, CPS_INFO_FILE_NAME, CpsChannelInfo.toJson(info));
            return;
        }
        FileHelper.writeTextToLocalFile(CPS_INFO_PATH_INNER_APK, CPS_INFO_FILE_NAME, CpsChannelInfo.toJson(info));
    }


    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019-11-01 15:16
     * @Description 获取Cps渠道信息
     */
    private static String getCpsChannel(CpsChannelInfo info) {
        return info == null ? "" : info.getChannel();
    }

    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019-11-01 15:16
     * @Description 获取CpsId信息
     */
    private static String getCpsId(CpsChannelInfo info) {
        return info == null ? "" : info.getCpsId();
    }


    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019-11-01 15:31
     * @Description apk市场渠道
     */
    public static String getApkMarketChannel() {
        try {
            //获取是否有预装渠道
            String preAssembleChannel = FileHelper.readTextFromLocalFile(PRE_ASSEMBLE_INFO_PATH + "/" + PRE_ASSEMBLE_INFO_FILE_NAME);
            if (!TextUtils.isEmpty(preAssembleChannel)) {
                return preAssembleChannel;
            }
            return getToolChannel(MyApplication.getInstance().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getToolChannel(MyApplication.getInstance().getApplicationContext());
    }

    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019-11-01 15:40
     * @Description 获取打包工具的cpsId
     */
    public static String getApkMarketChannelId() {
        if (BuildConfig.DEBUG) {
            return AppConstants.PROMOTOTER_ID;
        }
        String channel = WalleChannelReader.getChannel(MyApplication.getInstance().getApplicationContext());
        if (TextUtils.isEmpty(channel)) {
            return "";
        }
        String[] split = channel.split("_");
        if (split != null && split.length >= 2) {
            return split[1];
        } else {
            return "";
        }
    }

    /**
     * @param
     * @return
     * @add by zengcheng
     * @add on 2019/2/13 16:27
     * @Description 打包工具打包获取渠道信息
     */
    private static String getToolChannel(Context context) {
        if (BuildConfig.DEBUG) {
            return AppConstants.CHANNEL_NAME;
        }
        String channel = WalleChannelReader.getChannel(context);
        if (TextUtils.isEmpty(channel)) {
            return "";
        }
        String[] split = channel.split("_");
        if (split != null && split.length >= 2) {
            return split[0];
        } else {
            return "";
        }
    }


    /**
     * @param
     * @return
     * @add by zengcheng
     * @add on 2019/2/13 16:27
     * @Description 打包工具打包获取配置的书籍Id
     */
    public static String getToolDirectBookId(Context context) {
        String channel = WalleChannelReader.getChannel(context);
        if (TextUtils.isEmpty(channel)) {
            return "";
        }
        String[] split = channel.split("_");
        if (split.length >= 3) {
            return split[2];
        } else {
            return "";
        }
    }

    /**
     * @param
     * @return
     * @add by zengcheng
     * @add on 2019/2/13 16:27
     * @Description 打包工具打包获取配置的书籍Id
     */
    public static String getToolDirectChapterIndex(Context context) {
        String channel = WalleChannelReader.getChannel(context);
        if (TextUtils.isEmpty(channel)) {
            return "";
        }
        String[] split = channel.split("_");
        if (split.length >= 4) {
            return split[3];
        } else {
            return "";
        }
    }

    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019-11-01 16:05
     * @Description 是否需要从服务器获取cpsInfo
     */
    public static boolean canGetCpsInfoFromServer() {
        return GlobalPreference.getInstance().getBoolean(GET_CPS_INFO_FROM_SERVER_KEY, true);
    }

    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019-11-01 16:08
     * @Description 设置是否还可以从服务器获取cps信息
     */
    private static void setServerCpsInfoSwitch(boolean canGetCpsInfoFromServer) {
        GlobalPreference.getInstance().saveBoolean(GET_CPS_INFO_FROM_SERVER_KEY, canGetCpsInfoFromServer);
    }



    public static boolean hasReadWritePermission() {
        Context context = MyApplication.getInstance().getApplicationContext();
        int checkSelfPermission1 = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int checkSelfPermission2 = ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        return checkSelfPermission1 == PackageManager.PERMISSION_GRANTED
                && checkSelfPermission2 == PackageManager.PERMISSION_GRANTED;
    }

    public interface CpsInfoCallBack {

        void onFail();

        void OnSuccess(CpsChannelInfo info);
    }

    /**
     * 由于okhttp header 中的 value 不支持 null, \n 和 中文这样的特殊字符,所以这里
     * 会首先替换 \n ,然后使用 okhttp 的校验方式,校验不通过的话,就返回 encode 后的字符串
     */
    private static String getValueEncoded(String value) {
        try {
            if (value == null) return "null";
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





}
