package com.ushaqi.zhuishushenqi;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.text.TextUtils;

import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.ushaqi.zhuishushenqi.repository.GlobalPreference;
import com.ushaqi.zhuishushenqi.sensors.SensorsParamBuilder;
import com.ushaqi.zhuishushenqi.sensors.SensorsUploadHelper;
import com.ushaqi.zhuishushenqi.util.AppActionHelper;
import com.ushaqi.zhuishushenqi.util.LogUtil;

/**
 * 监听Activity对应的生命周期
 *
 * @author Andy.zhang
 * @date 2019/5/10
 */
public class ActivityLifecycleHandler {
    private static final String TAG = "ActivityLifecycleHandler";

    private static final String OUT_APP_LAST_TIME_STAMP = "out_app_last_time_stamp";


    /**
     * 启动的Activity个数
     */
    private static int sActivityStartCount;


    private static class ActivityLifecycleImpl implements Application.ActivityLifecycleCallbacks {

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(final Activity activity) {
//            if (sActivityStartCount == 0) {
//                if (activity != null ) {
//                    addSensorAppStartEvent();
//                }
//            }
            sActivityStartCount++;

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {


        }

        @Override
        public void onActivityStopped(Activity activity) {
            sActivityStartCount--;
            if (sActivityStartCount == 0) {
                AppActionHelper.addAppActionRecord(AppActionHelper.OPERATE_TYPE_OUT_APP, AppActionHelper.produceAppActionParam());
                addSensorAppEndEvent();
                MobclickAgent.onKillProcess(activity);
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            LogUtil.d(TAG, "onActivitySaveInstanceState");
        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }

    }

    /**
     * @param application
     * @param
     */
    public static void registerActivityLifecycleCallbacks(final Application application) {
        if (application != null) {
            application.registerActivityLifecycleCallbacks(new ActivityLifecycleImpl());
        }
    }

    /**
     * 是否是主进程
     *
     * @param processNameName
     * @return
     */
    private static boolean isMainProcess(final String processNameName) {
        return TextUtils.equals(AppConstants.APPLICATION_ID, processNameName);
    }




    public static void addSensorAppStartEvent() {
        try {
            int timeInterval = 0;
            long lastOutStamp = GlobalPreference.getInstance().getLong(OUT_APP_LAST_TIME_STAMP, 0L);
            if (lastOutStamp != 0) {
                timeInterval = (int) (System.currentTimeMillis() - lastOutStamp) / 1000;
            }

            if (AppConstants.sIsColdLaunch) {
                addCustomAppStartEvent(false, timeInterval);
                AppConstants.sIsColdLaunch = false;
            } else {
                addCustomAppStartEvent(true, timeInterval);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static  void  addCustomAppStartEvent(boolean isHotStart,int time_interval ){
        SensorsParamBuilder paramBuilder = SensorsParamBuilder.create()
                .put("is_hotstart", isHotStart)
                .put("time_interval", time_interval);
        SensorsUploadHelper.addTrackEvent("yy_CustomAppStart", paramBuilder);

    }

    private static  void  addCustomAppEndEvent(){
        SensorsParamBuilder paramBuilder = SensorsParamBuilder.create()
                .put("end_type", "退出至后台");
        SensorsUploadHelper.addTrackEvent("yy_CustomAppEnd", paramBuilder);

    }



    private static void addSensorAppEndEvent() {
        try {
            GlobalPreference.getInstance().saveLong(OUT_APP_LAST_TIME_STAMP, System.currentTimeMillis());
           addCustomAppEndEvent();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }









}
