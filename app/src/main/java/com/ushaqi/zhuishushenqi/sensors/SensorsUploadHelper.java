package com.ushaqi.zhuishushenqi.sensors;


import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.ushaqi.zhuishushenqi.util.LogUtil;

/**
 * @author shijingxing
 * @date 2020/5/20
 */
public class SensorsUploadHelper {

    /**
     * 业务埋点
     */
    public static void addTrackEvent(String key, SensorsParamBuilder paramBuilder) {
        LogUtil.i("SensorsUpload", "addTrackEvent key=" + key + ", param=" + paramBuilder.build().toString());
        SensorsDataAPI.sharedInstance().track(key, paramBuilder.build());
    }

    public static void addTrackEvent(String key) {
        SensorsDataAPI.sharedInstance().track(key);
    }

}
