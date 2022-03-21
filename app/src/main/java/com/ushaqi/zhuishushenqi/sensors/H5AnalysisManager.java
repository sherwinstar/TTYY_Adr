package com.ushaqi.zhuishushenqi.sensors;

import android.text.TextUtils;


import com.ushaqi.zhuishushenqi.module.baseweb.helper.WebJsHelper;

import java.util.Map;
import java.util.Set;

/**
 * <p>
 * <p>
 * 1）神策h5埋点 setSensorsUserBehavior
 * 2）书籍曝光 upLoadBookExposure
 * 3）资源位曝光 upLoadResourceExposure
 * 4）某些事件需要额外处理，在具体事件的jsbridge下的sensors字段中
 *
 * @ClassName: H5AnalysisManager
 * @Date: 2020/5/22 下午2:54
 * @Author: jared
 * @Org: 上海元聚网络科技有限公司
 * @Description: H5的书籍曝光
 * </p>
 */
public class H5AnalysisManager {

    public final static int TYPE_ACTIVITY_SHOW = 1;
    public final static int TYPE_BOOK_EXPOSURE = 2;



    /**
     * H5页面Item曝光
     *
     * @param jsonString
     */
    public static void addH5KeyItemExpousure(String jsonString) {
        if (TextUtils.isEmpty(jsonString)) {
            return;
        }
        Map map = WebJsHelper.changeUrlToJavaBean(jsonString, Map.class);
        parseAndAddEventFromMap(map, null);
    }

    /**
     * H5埋点事件
     *
     * @param decodeUrl
     */
    public static void addH5SensorsEvent(String decodeUrl) {
        Map map = WebJsHelper.changeUrlToJavaBean(decodeUrl, Map.class);
        parseAndAddEventFromMap(map, null);
    }





    /**
     * 解析map获取H5传过来的神策埋点参数信息并上传
     *
     * @param map
     */
    public static void parseAndAddEventFromMap(Map map, SensorsParamBuilder builder) {
        if (map == null || map.size() == 0) {
            return;
        }
        try {
            if (builder == null) {
                builder = SensorsParamBuilder.create();
            }
            String eventValue = null;
            Set keySet = map.keySet();
            for (Object keyObj : keySet) {
                Object value = map.get(keyObj);
                if ("event".equals(keyObj)) {
                    eventValue = (String) value;
                } else {
                    builder.putProperty(keyObj.toString(), value);
                }
            }
            if (TextUtils.isEmpty(eventValue)) {
                return;
            }
            SensorsUploadHelper.addTrackEvent(eventValue, builder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }






}
