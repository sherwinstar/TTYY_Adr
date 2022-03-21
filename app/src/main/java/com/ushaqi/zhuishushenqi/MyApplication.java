package com.ushaqi.zhuishushenqi;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;

import androidx.multidex.MultiDex;

import com.alibaba.baichuan.trade.common.AlibcTradeCommon;
import com.baichuan.nb_trade.callback.AlibcTradeInitCallback;
import com.baichuan.nb_trade.core.AlibcTradeSDK;
import com.igexin.sdk.IUserLoggerInterface;
import com.igexin.sdk.PushManager;
import com.kepler.jd.Listener.AsyncInitListener;
import com.kepler.jd.login.KeplerApiManager;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.commonsdk.UMConfigure;
import com.ushaqi.zhuishushenqi.channel.AppChannelManager;
import com.ushaqi.zhuishushenqi.httpcore.callback.ClientCallBack;
import com.ushaqi.zhuishushenqi.httpcore.requester.CommonRequester;
import com.ushaqi.zhuishushenqi.local.UserHelper;
import com.ushaqi.zhuishushenqi.model.SwitchConfig;
import com.ushaqi.zhuishushenqi.repository.GlobalPreference;
import com.ushaqi.zhuishushenqi.sensors.SensorsAnalysisManager;
import com.ushaqi.zhuishushenqi.util.AppUtils;
import com.ushaqi.zhuishushenqi.util.LogUtil;
import com.ushaqi.zhuishushenqi.util.SharedPreferencesUtil;
import com.ushaqi.zhuishushenqi.util.miit.IdentifierProvider;
import com.xunmeng.duoduojinbao.JinbaoUtil;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import jfq.wowan.com.myapplication.PlayMeUtil;

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    private static MyApplication sAppInstance = null;
    public static String mClientUA;//UA


    public static MyApplication getInstance() {
        return sAppInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sAppInstance = this;
        initUmeng();
        try {
                IdentifierProvider.getMobileDeviceIdentifier(this);
                String processName =  AppUtils.getProcessName(this);
                if (!AppConstants.APPLICATION_ID.concat(":filedownloader").equals(processName)){
                    AppConstants.sIsColdLaunch = true;
                    ActivityLifecycleHandler.registerActivityLifecycleCallbacks(this);
                    GlobalPreference.getInstance().saveLong("long_enter_app_application_time", System.currentTimeMillis());
                    boolean hasShowPro= SharedPreferencesUtil.get(MyApplication.getInstance(),"boole_has_show_pro",false);
                    if (hasShowPro){
                        appStartInit(processName);
                    }
                    ActivityLifecycleHandler.addSensorAppStartEvent();
                }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initUmeng() {
//        if (BuildConfig.DEBUG) {
//            UMConfigure.setLogEnabled(true);
//        }
        String umengId = "609b93fdc9aacd3bd4d25e30";
        String channel = "umeng_channel";
        UMConfigure.preInit(this, umengId, channel);
        UMConfigure.init(this,umengId,channel, UMConfigure.DEVICE_TYPE_PHONE, "");
    }

    public void appStartInit(String processName) {
        AppChannelManager.syncCpsInfoFromChannel(false,null);
        requestSwitchConfig();
        PlayMeUtil.init(this, AppConstants.APPLICATION_ID+".fileProvider",false,true);
        initAlibaichuan();
        initJDUnion();
        initPDDJinBao();
        //更新app启动时间
        updateLaunchTime();
        initSa();
        initGetui();
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(getApplicationContext());
        strategy.setUploadProcess(processName == null || processName.equals(AppConstants.APPLICATION_ID) );
        CrashReport.initCrashReport(getInstance(), AppConstants.BUGLY_ID, true, strategy);
        String mWebClientUa=  GlobalPreference.getInstance().getString("string_web_user_agent","");
        if (TextUtils.isEmpty(mWebClientUa)||mWebClientUa.contains(Build.BOOTLOADER)){
            mClientUA = new WebView(this).getSettings().getUserAgentString();
            GlobalPreference.getInstance().saveString("string_web_user_agent",mClientUA);
        }else {
            mClientUA=mWebClientUa;
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

        try {
            String processName = AppUtils.getProcessName(this);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && !AppConstants.APPLICATION_ID.equals(processName)) {
                WebView.setDataDirectorySuffix(processName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // 注册三方扩展实现（使用可参见官方DEMO）
    private void registerThirdInstance() {
        // 注册分享实现（具体实现说明见下文）
//        ShareImpl share = new ShareImpl();
//        AlibcNavigateCenter.registerNavigateUrl(share);
//        // 注册图片库实现
//        ImageImpl image = new ImageImpl();
//        AlibcImageCenter.registerImage(image);
    }
    private  void initAlibaichuan(){
        Map<String, Object> params = new HashMap<>();
        AlibcTradeSDK.asyncInit(this,params, new AlibcTradeInitCallback() {
            @Override
            public void onSuccess() {
                LogUtil.e(TAG,"初始化成功");
                AlibcTradeCommon.setIsvVersion(BuildConfig.VERSION_NAME);

            }

            @Override
            public void onFailure(int code, String msg) {
                LogUtil.e(TAG,"初始化失败:"+code+","+msg);
            }
        });
    }

    private void initJDUnion(){
        KeplerApiManager.asyncInitSdk(this, AppConstants.JD_APPKEY, AppConstants.JD_SECRETKEY,
                new AsyncInitListener() {
                    @Override
                    public void onSuccess() {
                        LogUtil.e(TAG, "Kepler asyncInitSdk onSuccess ");
                    }
                    @Override
                    public void onFailure() {
                        LogUtil.e(TAG,
                                "Kepler asyncInitSdk 授权失败，请检查 lib 工程资源引用；包名,签名证书是否和注册一致");
                    }
                });
    }



    private void initPDDJinBao(){
        JinbaoUtil.init(MyApplication.getInstance(), b -> {
            LogUtil.e(TAG, " pddinit "+b);
        });
    }

    public void initSa() {
        SensorsAnalysisManager.initSensorsDataSdk(getApplicationContext());
        SensorsAnalysisManager.initGlobalStaticParam(getApplicationContext());
        SensorsAnalysisManager.registerDynamicParam(getApplicationContext());
        SensorsAnalysisManager.initFragmentTrack();
        //神策login事件
        SensorsAnalysisManager.login(UserHelper.getInstance().getUserId());

    }

    private void initGetui() {
        PushManager.getInstance().initialize(this);
        if (BuildConfig.DEBUG) {
            PushManager.getInstance().setDebugLogger(this, new IUserLoggerInterface() {
                @Override
                public void log(String s) {
                    Log.i("PUSH_LOG",s);
                }
            });
        }
    }

    private void updateLaunchTime() {
        try {
            long t = GlobalPreference.getInstance().getLong(AppConstants.PREF_FIRST_LAUNCH_TIME, 0L);
            if (t == 0) {
                GlobalPreference.getInstance().saveLong(AppConstants.PREF_FIRST_LAUNCH_TIME, Calendar.getInstance().getTimeInMillis());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断游戏是否优隐藏的开关
     */
    private void  requestSwitchConfig(){
        CommonRequester.getInstance().getApi().getAuditSwitchConfig("Android", AppChannelManager.getToolChannel(MyApplication.getInstance()),String.valueOf(BuildConfig.VERSION_CODE)).enqueue(new ClientCallBack<SwitchConfig>() {
            @Override
            public void onSuccess(SwitchConfig response) {
                //0是隐藏游戏，1是开启游戏
                if(response!=null&&response.isOk()&&response.data!=null){
                    SharedPreferencesUtil.put(MyApplication.getInstance(),"home_tab_game_need_show",response.data.status==0);
                }
            }

            @Override
            public void onFailed(HttpExceptionMessage exceptionMessage) {

            }
        });
    }





}
