package com.ushaqi.zhuishushenqi.util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.tencent.bugly.crashreport.CrashReport;
import com.ushaqi.zhuishushenqi.MyApplication;
import com.ushaqi.zhuishushenqi.model.AppActionPostBody;
import com.ushaqi.zhuishushenqi.repository.GlobalPreference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by shijingxing on 2019/3/25
 * app行为日志上传帮助类
 */
public class AppActionHelper {

    /**
     * 上一次发送打开应用事件的时间
     */
    public static final String SP_LAST_TIME_POST_OPEN_APP_ACTION = "sp_last_time_post_open_app_action";

    /**
     * 上一次发送退出应用事件的时间
     */
    public static final String SP_LAST_TIME_POST_CLOSE_APP_ACTION = "sp_last_time_post_close_app_action";

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);

    /**
     * 启动app类型
     */
    public static final int OPERATE_TYPE_OPEN_APP = 1;

    /**
     * 注册类型
     */
    public static final int OPERATE_TYPE_SIGN_UP = 2;

    /**
     * 登录类型
     */
    public static final int OPERATE_TYPE_SIGN_IN = 3;

    /**
     * 退出类型
     */
    public static final int OPERATE_TYPE_OUT_APP = 7;

    /**
     * 添加事件
     * @param operateType
     * @param paramMap
     */
    public static void addAppActionRecord(final int operateType, final Map<String, String> paramMap) {
        if (operateType == OPERATE_TYPE_OPEN_APP && isSameDayValue(SP_LAST_TIME_POST_OPEN_APP_ACTION)) {
            return;
        }
        if (operateType == OPERATE_TYPE_OUT_APP && isSameDayValue(SP_LAST_TIME_POST_CLOSE_APP_ACTION)) {
            return;
        }


//        String deviceImei = AppEventCommonInfoHelper.getImei();
//        if(deviceImei == null || deviceImei.isEmpty()){
//            return;
//        }

        AppEventThreadPool.getAppEventThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    List<AppActionPostBody.AppActionBean> data = new ArrayList<>();
                    data.add(new AppActionPostBody.AppActionBean(String.valueOf(operateType), paramMap));
                    AppActionPostBody appActionPostBody = new AppActionPostBody(data);
                    AppActionPostHelper.postAppActionToService(new Gson().toJson(appActionPostBody), operateType);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public static Map<String, String> produceAppActionParam() {
        Map<String, String> map = new HashMap<>();
        map.put("param1", "-1");
        map.put("param2", "-1");
        try{
            map.put("param3", "");
        }catch (Exception e){
            map.put("param3", "-1");
            //主动上报crash至bugly
            CrashReport.postCatchedException(e);
            e.printStackTrace();
        }
        map.put("param4", AppEventCommonInfoHelper.getAndroidId());
        map.put("param5", AppEventCommonInfoHelper.getNetWorkType());
        String uploadType = SharedPreferencesUtil.get(MyApplication.getInstance(),
                "string_ad_group_type", "");
        map.put("param6", uploadType);
        return map;
    }
    public static int getToday() {
        Date date = new Date();
        String today = sdf.format(date);
        return StringUtils.toInt(today);
    }

    /**
     * 判断和保存的sp值是否是同一天
     * @param key
     * @return
     */
    public static boolean isSameDayValue(String key) {
        String today = String.valueOf(getToday());
        String lastTime = GlobalPreference.getInstance().getString(key,"");
        return TextUtils.equals(today, lastTime);
    }
}
