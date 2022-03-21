package com.ushaqi.zhuishushenqi.sensors;

import android.content.Context;
import android.text.TextUtils;


import com.sensorsdata.analytics.android.sdk.SAConfigOptions;
import com.sensorsdata.analytics.android.sdk.SensorsAnalyticsAutoTrackEventType;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.SensorsDataDynamicSuperProperties;
import com.ushaqi.zhuishushenqi.AppConstants;
import com.ushaqi.zhuishushenqi.BuildConfig;
import com.ushaqi.zhuishushenqi.MyApplication;
import com.ushaqi.zhuishushenqi.channel.AppChannelManager;
import com.ushaqi.zhuishushenqi.local.UserHelper;
import com.ushaqi.zhuishushenqi.repository.GlobalPreference;
import com.ushaqi.zhuishushenqi.util.AppHelper;
import com.ushaqi.zhuishushenqi.util.TimeUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Andy.zhang
 * @date 2020/5/20
 */
public class SensorsAnalysisManager {

    private static final String TAG = "SensorsAnalysisManager";

    private static String sAppInstallTimeFormat;

    /**
     * 开启fragment自动埋点
     */
    public static void initFragmentTrack() {
        SensorsDataAPI.sharedInstance().trackFragmentAppViewScreen();
    }

    /**
     * 初始化神策SDK
     *
     * @param context
     */
    public static void initSensorsDataSdk(Context context) {
        //设置 SAConfigOptions，传入数据接收地址 SA_SERVER_URL
        SAConfigOptions saConfigOptions = new SAConfigOptions(SensorsAnalysisConstants.SD_SERVER_URL);
        saConfigOptions.enableVisualizedAutoTrack(true);//开启可视化埋点
        saConfigOptions.enableTrackAppCrash();
        //通过 SAConfigOptions 设置神策 SDK，每个条件都非必须，开发者可根据自己实际情况设置，更多设置可参考 SAConfigOptions 类中方法注释
        saConfigOptions.setAutoTrackEventType(SensorsAnalyticsAutoTrackEventType.APP_CLICK | // 开启全埋点点击事件
                SensorsAnalyticsAutoTrackEventType.APP_START |      //开启全埋点启动事件
                SensorsAnalyticsAutoTrackEventType.APP_END |        //开启全埋点退出事件
                SensorsAnalyticsAutoTrackEventType.APP_VIEW_SCREEN)     //开启全埋点浏览事件
                .enableLog(BuildConfig.DEBUG);        //开启神策调试日志，默认关闭(调试时，可开启日志)。
        //需要在主线程初始化神策 SDK
        SensorsDataAPI.startWithConfigOptions(context, saConfigOptions);
    }



    /**
     * 设置全局静态参数
     * 将应用名称作为事件公共属性，后续所有 track() 追踪的事件都会自动带上 "AppName" 属性
     */
    public static void initGlobalStaticParam(Context context) {
        SensorsParamBuilder paramBuilder = SensorsParamBuilder.create()
                .put(SensorsAnalysisConstants.PROP_PLATFORM, SensorsAnalysisConstants.PLATFORM)
                .put(SensorsAnalysisConstants.PROP_PRODUCT_LINE, SensorsAnalysisConstants.PRODUCT_LINE)
                .put(SensorsAnalysisConstants.PROP_ANDROIDID, AppHelper.getAndroidId())
                .put(SensorsAnalysisConstants.PROP_IMEI, AppHelper.getIMEI(context))
                .put(SensorsAnalysisConstants.PROP_OAID, AppHelper.getOAID())
                .put(SensorsAnalysisConstants.PROP_APK_CHANNEL, AppChannelManager.getApkMarketChannel());
        SensorsDataAPI.sharedInstance().registerSuperProperties(paramBuilder.build());
    }

    /**
     * 获取完OAID后重新设置公共静态属性OAID
     * @param oaid
     */
    public static void registerOaidStaticParam(String oaid){
        SensorsParamBuilder paramBuilder = SensorsParamBuilder.create();
        paramBuilder.put(SensorsAnalysisConstants.PROP_OAID, oaid);
        SensorsDataAPI.sharedInstance().registerSuperProperties(paramBuilder.build());
    }

    /**
     * 获取完设备权限后后重新设置公共静态属性imei
     */
    public static void registerImeiStaticParam(String imei){
        SensorsParamBuilder paramBuilder = SensorsParamBuilder.create();
        paramBuilder. put(SensorsAnalysisConstants.PROP_IMEI, imei);
        SensorsDataAPI.sharedInstance().registerSuperProperties(paramBuilder.build());
    }

    /**
     * 初始化 SDK 后，设置动态公共属性
     * Android SDK 2.0.1 及以后的版本可以通过 registerDynamicSuperProperties() 方法设置动态公共属性，
     * 设置之后 SDK 会自动获取 getDynamicSuperProperties 中的属性添加到触发的事件中。
     */
    public static void registerDynamicParam(final Context context) {
        SensorsDataAPI.sharedInstance().registerDynamicSuperProperties(() -> {
            try {
                SensorsParamBuilder dynamicPropertiesBuilder = getDynamicPropertiesBuilder();
                if (dynamicPropertiesBuilder == null) {
                    return null;
                }
                return dynamicPropertiesBuilder.build();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    /**
     * 可以调用 trackInstallation 方法记录激活事件，多次调用此方法只会触发一次激活事件（触发激活事件时会自动保存 $first_visit_time 首次访问时间属性到用户表）。
     * 触发激活事件时会尝试获取 IMEI 号，请动态申请 android.permission.READ_PHONE_STATE 权限后再调用 trackInstallation 。
     * <p>
     * 例如，在 Activity 的 onCreate 方法中申请权限：
     * 记录激活事件
     */
//    public static void trackInstallation() {
//        try {
//            JSONObject properties = new JSONObject();
//            //这里的 DownloadChannel 负责记录下载商店的渠道。这里传入具体应用商店包的标记。
//            properties.put(SensorsAnalysisConstants.PROP_DOWNLOAD_CHANNEL, AppChannelManager.getApkMarketChannel());
//            //记录 AppInstall 激活事件
//            SensorsDataAPI.sharedInstance().trackInstallation(SensorsAnalysisConstants.EVENT_APP_INSTALL, properties);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    /**
     * 更新用户属性到神策埋点
     * @param params 属性点
     */
    public static void userProfileEvent(HashMap<String, Object> params) {
        try {
            JSONObject properties = new JSONObject();
            Iterator iter = params.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                properties.put((String) entry.getKey(),entry.getValue());
            }
            SensorsDataAPI.sharedInstance().profileSet(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新用户属性到神策埋点
     * 针对单个属性
     */
    public static void userProfileEvent(String property, Object value) {
        SensorsDataAPI.sharedInstance().profileSet(property, value);
    }

    /**
     * 匿名 ID 和登录 ID 关联
     * 用户在登录前 ，SDK 会分配一个匿名 ID 来标识游客。当用户注册成功或登录成功时调用 login 方法，传入对应的登录 ID ；匿名 ID 会与对应的登录 ID 进行关联，关联成功之后视为同一个用户。
     * <p>
     * 调用时机：注册成功、登录成功 、初始化 SDK（如果能获取到登录 ID）都需要调用 login 方法传入登录 ID。
     * 注册成功、登录成功、初始化SDK后  调用 login 传入登录 ID
     * 你们服务端分配给用户具体的登录 ID
     */
    public static void login(String userId) {
        if(TextUtils.isEmpty(userId)){
            return;
        }
        SensorsDataAPI.sharedInstance().login(userId);

    }

    /**
     * 获取所有公共属性
     * @return
     */
    public static JSONObject getAllSuperProperties(){
        try {
            //动态公共属性
            SensorsParamBuilder dynamicPropertiesBuilder = getDynamicPropertiesBuilder();
            if(dynamicPropertiesBuilder == null){
                return null;
            }
            //静态公共属性
            JSONObject superProperties = SensorsDataAPI.sharedInstance().getSuperProperties();
            dynamicPropertiesBuilder.putJsonObject(superProperties);
            return dynamicPropertiesBuilder.build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 获取动态公共属性
     *
     * @return
     */
    private static SensorsParamBuilder getDynamicPropertiesBuilder() {
        try {
            SensorsParamBuilder paramBuilder = SensorsParamBuilder.create()
                    .put(SensorsAnalysisConstants.PROP_DEVICE_IMEI, AppHelper.getRealDeviceInfo(MyApplication.getInstance()))
                    .put(SensorsAnalysisConstants.PROP_ZS_LOGIN_ID, UserHelper.getInstance().getUserId())
                    .put(SensorsAnalysisConstants.PROP_BUSINESS_NAME, AppChannelManager.getBusinessName())
                    .put(SensorsAnalysisConstants.PROP_CHANNEL_NAME, getDynamicChannel())
                    .put(SensorsAnalysisConstants.PROP_CHANNEL_ID, getDynamicChannelId())
                    .put(SensorsAnalysisConstants.PROP_CHILD_CHANNEL_NAME, AppChannelManager.getChildChannel())
                    .put(SensorsAnalysisConstants.PROP_CHILD_CHANNEL_ID, AppChannelManager.getChildChannelId())
                    .put(SensorsAnalysisConstants.PROP_PUB_APP_FIRST_INSTALL_TIME, AppHelper.getInstallTime())
                    .put(SensorsAnalysisConstants.PROP_GRAYTEST_MARK, AppChannelManager.getGrayTestMark())
                    .put(SensorsAnalysisConstants.ACTIVE_TIME, AppChannelManager.getActiveTime())
                    .put(SensorsAnalysisConstants.APP_VERSION, BuildConfig.VERSION_NAME)
                    .put(SensorsAnalysisConstants.UA_CHANNEL_ID, AppChannelManager.getUaChannelId())
                    .put(SensorsAnalysisConstants.UA_CHANNEL_NAME, AppChannelManager.getUaChannelName());
//                    .put(SensorsAnalysisConstants.UA_AD_CLICK, AppChannelManager.getUaClickTime())
            return paramBuilder;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getDynamicChannel(){
        return AppChannelManager.getChannel();
    }

    public static String getDynamicChannelId(){
        return AppChannelManager.getChannelId();
    }

    public static String getAppInstallTimeFormat(){
        if(sAppInstallTimeFormat == null){
            long installTimeStamp = GlobalPreference.getInstance().getLong(AppConstants.PREF_FIRST_LAUNCH_TIME, 0L);
            if(installTimeStamp == 0){
                return null;
            }
            sAppInstallTimeFormat = TimeUtils.getTime(installTimeStamp);
        }
        return sAppInstallTimeFormat;
    }

}