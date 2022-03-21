package com.ushaqi.zhuishushenqi.channel;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import androidx.core.app.ActivityCompat;

import com.bytedance.hume.readapk.HumeSDK;
import com.signapk.walle.WalleChannelReader;
import com.ushaqi.zhuishushenqi.AppConstants;
import com.ushaqi.zhuishushenqi.BuildConfig;
import com.ushaqi.zhuishushenqi.MyApplication;
import com.ushaqi.zhuishushenqi.httpcore.HttpCallBack;
import com.ushaqi.zhuishushenqi.httpcore.HttpExceptionMessage;
import com.ushaqi.zhuishushenqi.httpcore.HttpRequestBody;
import com.ushaqi.zhuishushenqi.httpcore.HttpRequestMethod;
import com.ushaqi.zhuishushenqi.httpcore.RequestManager;
import com.ushaqi.zhuishushenqi.model.ChannelInfoResult;
import com.ushaqi.zhuishushenqi.model.CpsChannelInfo;
import com.ushaqi.zhuishushenqi.model.CpsDeviceInfo;
import com.ushaqi.zhuishushenqi.sensors.SensorsAnalysisConstants;
import com.ushaqi.zhuishushenqi.sensors.SensorsAnalysisManager;
import com.ushaqi.zhuishushenqi.sensors.SensorsUploadHelper;
import com.ushaqi.zhuishushenqi.util.AppActionHelper;
import com.ushaqi.zhuishushenqi.util.AppHelper;
import com.ushaqi.zhuishushenqi.util.FileHelper;
import com.ushaqi.zhuishushenqi.util.SharedPreferencesUtil;
import com.ushaqi.zhuishushenqi.util.TimeUtils;

import java.util.HashMap;

/**
 * @ProjectName: zhuishushenqi
 * @Package: com.ushaqi.zhuishushenqi.channel
 * @ClassName: AppChannelManager
 * @Author: Jared.Bai
 * @Org: 上海元聚网络科技有限公司
 * @CreateDate: 2020/10/9 下午2:54
 * @Description: 应用渠道管理类
 * 1.调用点击归因的接口，内存缓存，本地缓存
 * 2.getChannel的方法
 */
public class AppChannelManager {

    /**
     * Bi接口返回的渠道信息对象
     */
    private static CpsChannelInfo sCpsChannelInfo;
    /**
     * 渠道对象缓存到本地的路径（开启读写权限后）
     */
    private static final String CPS_INFO_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/.Android/system/core";
    /**
     * 渠道对象缓存到本地的路径（未开启读写权限）
     */
    private static final String CPS_INFO_PATH_INNER_APK = MyApplication.getInstance().getApplicationContext().getFilesDir().getAbsolutePath() + "/core";
    /**
     * 本地缓存的文件名称
     */
    private static final String CPS_INFO_FILE_NAME = "cpsInfo.txt";
    /**
     * 友盟的key
     */
    public static final String UMENG_KEY = "607646bd5844f15425d293cb";
    /**
     * 预装渠道的获取方法
     */
    private static final String PRE_ASSEMBLE_INFO_PATH = "/etc";
    /**
     * 预装渠道的文件
     */
    private static final String PRE_ASSEMBLE_INFO_FILE_NAME = "zhuishu.txt";
    /**
     * 获取渠道的接口
     */
    //todo 测试地址，上线要换
    private static final String CPS_INFO_URL = "http://data.1391.com:60005/behavior/clicking/attribution/channel";
    private static final String TEST_CPS_INFO_URL = "http://test.zhuishushenqi.com:62001/behavior/clicking/attribution/channel";
    /**
     * 接口参数的token
     */
    //todo 测试地址，上线要换
    private static final String X_TOKEN = "9afb2dg61f7a71718e906d6698";

    private static final String TEST_X_TOKEN = "50af89f89036fa601b6fddwc";

    private static final String GET_CPS_INFO_FROM_SERVER_KEY = "get_cps_info_from_server_key";

    private static Handler sHandler = new Handler(Looper.getMainLooper());

    /**
     * 是否代码设置过Umeng的channel
     */
    public static boolean mHasSetUmChannel = false;

    private static boolean isRequesting = false;
    public static final String FIRST_TRUST_NEW_USER_WEALFARE = "first_trust_new_user_walfare";



    private static void updateChannelInfoCache(CpsChannelInfo info) {
        //先取本地缓存
        CpsChannelInfo cpsInfoFromLocalData = getCpsInfoFromLocalData();
        if (cpsInfoFromLocalData == null){
            //更新内存缓存
            sCpsChannelInfo = info;
            //更新本地文件缓存
            putCpsInfoToLocal(info);
            return;
        }
        //解析对应字段是否有效,有效就更新，没有就不更新
        if (info.isChannelIdValid()){
            cpsInfoFromLocalData.setActiveTime(info.getActiveTime());
            cpsInfoFromLocalData.setCpsId(info.getCpsId());
            cpsInfoFromLocalData.setChannel(info.getChannel());
            cpsInfoFromLocalData.setPlanNumber(info.getPlanNumber());
            cpsInfoFromLocalData.setPlanId(info.getPlanId());
            cpsInfoFromLocalData.setPlanClickTs(info.getPlanClickTs());
            cpsInfoFromLocalData.setMerchantId(info.getMerchantId());
            cpsInfoFromLocalData.setMerchantName(info.getMerchantName());
            cpsInfoFromLocalData.setBookId(info.getBookId());
            cpsInfoFromLocalData.setSceneType(info.getSceneType());
            cpsInfoFromLocalData.setTypeValue(info.getTypeValue());
        }
        if (info.isUaChannelIdValid()){
            cpsInfoFromLocalData.setUaChannelId(info.getUaChannelId());
            cpsInfoFromLocalData.setUaChannelName(info.getUaChannelName());
            cpsInfoFromLocalData.setUaClickTs(info.getUaClickTs());
            cpsInfoFromLocalData.setUaSceneType(info.getUaSceneType());
            cpsInfoFromLocalData.setUaTypeValue(info.getUaTypeValue());
            cpsInfoFromLocalData.setUaBookId(info.getUaBookId());
        }
        //更新内存缓存
        sCpsChannelInfo = cpsInfoFromLocalData;
        //更新本地文件缓存
        putCpsInfoToLocal(cpsInfoFromLocalData);
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
            CpsChannelInfo cpsChannelInfo = getCpsChannelInfo();
            String humChannel= HumeSDK.getChannel(MyApplication.getInstance());
            if (!TextUtils.isEmpty(humChannel)){
                return humChannel;
            }
            if (cpsChannelInfo!=null){
                String channel = cpsChannelInfo.getChannel();
                if (!isInvalidString(channel)){
                    return channel;
                }
            }

            //获取是否有预装渠道
//            String preAssembleChannel = FileHelper.readTextFromLocalFile(PRE_ASSEMBLE_INFO_PATH + "/" + PRE_ASSEMBLE_INFO_FILE_NAME);
//            if (!TextUtils.isEmpty(preAssembleChannel)) {
//                return preAssembleChannel;
//            }
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
    public static String getToolChannel(Context context) {
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
     * @add on 2019-11-01 14:00
     * @Description 从本地获取cps信息放入数组
     */
    private static CpsChannelInfo getCpsInfoFromLocalData() {

        return CpsChannelInfo.fromJson(FileHelper.readTextFromLocalFile(CPS_INFO_PATH_INNER_APK + "/" + CPS_INFO_FILE_NAME));
    }

    public static boolean hasReadWritePermission() {
        Context context = MyApplication.getInstance().getApplicationContext();
        int checkSelfPermission1 = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int checkSelfPermission2 = ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        return checkSelfPermission1 == PackageManager.PERMISSION_GRANTED
                && checkSelfPermission2 == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 获取渠道，先取内存缓存，内存缓存为空去本地缓存，本地缓存为空，取apkChannel
     *
     * @return
     */
    public static String getChannel() {
        CpsChannelInfo cpsChannelInfo = getCpsChannelInfo();
        if (cpsChannelInfo == null){
            return getApkMarketChannel();
        }
        String channel = cpsChannelInfo.getChannel();
        if (isInvalidString(channel)) {
            return getApkMarketChannel();
        }
        return channel;
    }

    /**
     * 获取渠道ID
     *
     * @return
     */
    public static String getChannelId() {
        CpsChannelInfo cpsChannelInfo = getCpsChannelInfo();
        if (cpsChannelInfo == null){
            return getApkMarketChannelId();
        }
        String channelId = cpsChannelInfo.getCpsId();
        if (isInvalidString(channelId)) {
            return getApkMarketChannelId();
        }
        return channelId;
    }

    /**
     * 获取子渠道
     *
     * @return
     */
    public static String getChildChannel() {
        CpsChannelInfo cpsChannelInfo = getCpsChannelInfo();
        if (cpsChannelInfo == null){
            return "";
        }
        String childChannel = cpsChannelInfo.getPlanNumber();
        if (isInvalidString(childChannel)) {
            return "";
        }
        return childChannel;
    }

    /**
     * 获取商户名称
     *
     * @return
     */
    public static String getBusinessName() {
        CpsChannelInfo cpsChannelInfo = getCpsChannelInfo();
        if (cpsChannelInfo == null){
            return "";
        }
        String businessName = cpsChannelInfo.getMerchantName();
        if (isInvalidString(businessName)) {
            return "";
        }
        return businessName;
    }
    /**
     * 子渠道ID
     *
     * @return
     */
    public static String getChildChannelId() {
        CpsChannelInfo cpsChannelInfo = getCpsChannelInfo();
        if (cpsChannelInfo == null){
            return "";
        }
        String childChannelId = cpsChannelInfo.getPlanId();
        if (isInvalidString(childChannelId)) {
            return "";
        }
        return childChannelId;
    }

    /**
     * 获取商户名称
     *
     * @return
     */
//    public static String getBusinessName() {
//        CpsChannelInfo cpsChannelInfo = getCpsChannelInfo();
//        if (cpsChannelInfo == null){
//            return "";
//        }
//        String businessName = cpsChannelInfo.getMerchantName();
//        if (isInvalidString(businessName)) {
//            return "";
//        }
//        return businessName;
//    }

    /**
     * 获取广告点击时间
     *
     * @return
     */
//    public static String getAdClickTime() {
//        CpsChannelInfo cpsChannelInfo = getCpsChannelInfo();
//        if (cpsChannelInfo == null){
//            return "";
//        }
//        long adClickTime = cpsChannelInfo.getPlanClickTs();
//        if (adClickTime == 0) {
//            return "";
//        }
//        return DateHelper.forMatStamp(adClickTime);
//    }

    /**
     * 获取设备入库的时间
     *
     * @return
     */
    public static String getActiveTime() {
        CpsChannelInfo cpsChannelInfo = getCpsChannelInfo();
        if (cpsChannelInfo == null){
            return "";
        }
        long adClickTime = cpsChannelInfo.getActiveTime();
        if (adClickTime == 0) {
            return "";
        }
        return TimeUtils.getTime(adClickTime);
    }

    /**
     * @return
     */
    public static String getUaClickTime() {
        CpsChannelInfo cpsChannelInfo = getCpsChannelInfo();
        if (cpsChannelInfo == null){
            return "";
        }
        long uaClickTime = cpsChannelInfo.getUaClickTs();
        if (uaClickTime == 0) {
            return "";
        }
        return TimeUtils.getTime(uaClickTime);
    }

    public static String getUaChannelId(){
        CpsChannelInfo cpsChannelInfo = getCpsChannelInfo();
        if (cpsChannelInfo == null){
            return getApkMarketChannelId();
        }
        String uaChannelId = cpsChannelInfo.getUaChannelId();
        if (isInvalidString(uaChannelId)) {
            return getApkMarketChannelId();
        }
        return uaChannelId;
    }

    public static String getUaChannelName(){
        CpsChannelInfo cpsChannelInfo = getCpsChannelInfo();
        if (cpsChannelInfo == null){
            return getApkMarketChannel();
        }
        String uaChannelName = cpsChannelInfo.getUaChannelName();
        if (isInvalidString(uaChannelName)) {
            return getApkMarketChannel();
        }
        return uaChannelName;
    }

    private static boolean isInvalidString(String s) {
        return TextUtils.isEmpty(s) || "-1".equals(s);
    }

    public static CpsChannelInfo getCpsChannelInfo() {
        if (sCpsChannelInfo == null) {
            sCpsChannelInfo = getCpsInfoFromLocalData();
        }
        return sCpsChannelInfo;
    }



    private static void updateFirstParams(CpsChannelInfo result) {
        if (result == null) {
            HashMap<String, Object> params = new HashMap<>();
            params.put("first_channel_name", getApkMarketChannel());
            params.put("first_channel_id", getApkMarketChannelId());
            params.put("first_platform", SensorsAnalysisConstants.PLATFORM);
            params.put("first_product_line", SensorsAnalysisConstants.PRODUCT_LINE);
            SensorsAnalysisManager.userProfileEvent(params);
            return;
        }
        HashMap<String, Object> params = new HashMap<>();
        params.put("first_channel_name", getChannel());
        params.put("first_channel_id", getChannelId());
        params.put("first_platform", SensorsAnalysisConstants.PLATFORM);
        params.put("first_product_line", SensorsAnalysisConstants.PRODUCT_LINE);
        SensorsAnalysisManager.userProfileEvent(params);
    }

    /**
     * 灰度标识渠道
     *
     * @return
     */
    public static String getGrayTestMark() {
        return BuildConfig.VERSION_NAME;
    }

    public interface CpsInfoCallBack {

        void onFail();

        void OnSuccess(CpsChannelInfo info);
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

    public static void syncCpsInfoFromChannel(final boolean forceUpdatePermission,final CpsInfoCallBack callBack) {
        if (isRequesting) {
            return;
        }
        String jsonParam = CpsDeviceInfo.toJson(CpsDeviceInfo.init(AppHelper.getIMEI(MyApplication.getInstance()), AppHelper.getOAID(), AppHelper.getAndroidId()));
        if (jsonParam == null) {
            return;
        }
        isRequesting = true;
        HttpRequestBody body = new HttpRequestBody.Builder()
                .actionUrl(CPS_INFO_URL)
                .httpUiThread(HttpRequestBody.HttpUiThread.ISBACKGROUND)
                .requestMethod(HttpRequestMethod.JSON)
                .clazz(ChannelInfoResult.class)
                .object(jsonParam)
                .callBack(new HttpCallBack<ChannelInfoResult>() {
                    @Override
                    public void onFailure(HttpExceptionMessage obj) {
                        isRequesting = false;
                        onBiCpsInfoReceivedFail(obj.toString(), forceUpdatePermission);
                        onCallBackFail(callBack);
                    }

                    @Override
                    public void onSuccess(ChannelInfoResult obj) {
                        isRequesting = false;
                        if (obj == null) {
                            onBiCpsInfoReceivedFail("result == null", forceUpdatePermission);
                            onCallBackFail(callBack);
                            return;
                        }
                        if (obj.getCode() != 200) {
                            onBiCpsInfoReceivedFail(obj.getMessage(), forceUpdatePermission);
                            onCallBackFail(callBack);
                            return;
                        }
                        final CpsChannelInfo channelInfo = obj.getResult();
                        if (channelInfo == null) {
                            onBiCpsInfoReceivedFail(obj.getMessage(), forceUpdatePermission);
                            onCallBackFail(callBack);
                            return;
                        }
//                        if (channelInfo.getAttributionCode() != 2){
//                            onBiCpsInfoReceivedFail(obj.getMessage(), forceUpdatePermission);
//                            onCallBackFail(callBack);
//                            return;
//                        }
                        onCallBackSuccess(callBack,channelInfo);
                        onBiCpsInfoReceivedSuccess(channelInfo, forceUpdatePermission);
                    }
                }).build();
        HashMap<String, String> mapHeaders = new HashMap<>();
        mapHeaders.put("x-token", X_TOKEN);
        mapHeaders.put("Web-User-Agent", MyApplication.mClientUA != null ? MyApplication.mClientUA : "");
        body.setMapHeaders(mapHeaders);
        RequestManager.getInstance().sendRequest(body);
    }

    private static void onCallBackSuccess(final CpsInfoCallBack callBack, final CpsChannelInfo channelInfo) {
        if (sHandler != null && callBack != null){
            sHandler.post(new Runnable() {
                @Override
                public void run() {
                    callBack.OnSuccess(channelInfo);
                }
            });
        }
    }

    private static void onBiCpsInfoReceivedSuccess(CpsChannelInfo info, boolean forceUpdatePermission) {
        try {
            if (!isInvalidString(info.getChannel()) || !isInvalidString(info.getUaChannelName())){
                SensorsUploadHelper.addTrackEvent("MarketActivateFromAd");
            }
            //更新渠道的缓存信息
            updateChannelInfoCache(info);
            //如果首次安装延迟初始化友盟
            updateUserSensorChannelInfo(info, forceUpdatePermission);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void onBiCpsInfoReceivedFail(String msg, boolean forceUpdatePermission) {
        try {
            //如果首次安装延迟初始化友盟
            AppActionHelper.addAppActionRecord(AppActionHelper.OPERATE_TYPE_OPEN_APP, AppActionHelper.produceAppActionParam());
            updateUserSensorChannelInfo(null, forceUpdatePermission);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void onCallBackFail(final CpsInfoCallBack callBack) {
        if (sHandler != null && callBack != null){
            sHandler.post(new Runnable() {
                @Override
                public void run() {
                    callBack.onFail();
                }
            });
        }
    }
    private static void updateUserSensorChannelInfo(CpsChannelInfo result, boolean isForceUpDatePermission) {
        //强制更新权限后需要更新first相关参数
        if (isForceUpDatePermission) {
            updateFirstParams(result);
            return;
        }
        boolean isFirstLunch = SharedPreferencesUtil.get(MyApplication.getInstance(), FIRST_TRUST_NEW_USER_WEALFARE, true);
        if (!isFirstLunch) {
            return;
        }
        updateFirstParams(result);
    }

}
