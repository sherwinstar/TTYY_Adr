package com.ushaqi.zhuishushenqi.sensors;

import android.text.TextUtils;

/**
 * @author chengwencan
 * @date 2020/6/2
 * Describe：错误信息神策上传
 */
public class ErrorAnalysisManager {
    /**
     * 事件：第三方SDK报错
     */
    private static final String THIRD_SDK_ERROR = "yy_ThirdSDKError";
    /**
     * 事件：追书接口报错
     */
    private static final String ZSSQ_API_ERROR = "yy_ApiError";



    /**
     * 接口类型
     */
    private static final String API_SOURCE = "api_source";
    /**
     * 接口url
     */
    private static final String API_URL = "api_url";
    /**
     * 错误类型
     */
    private static final String ERROR_TYPE = "error_type";
    /**
     * http错误码
     */
    private static final String HTTP_CODE = "http_code";
    /**
     * 错误描述
     */
    private static final String ERR_DESC = "err_desc";


    /**
     *
     * @param errorCode
     * @return
     */
    public static String getErrorType(String errorCode) {
        String errorType;
        if (TextUtils.isEmpty(errorCode)) {
            errorType = "网络错误";
        } else if ("200".equals(errorCode)) {
            errorType = "业务错误";
        } else {
            errorType = "http错误";
        }
        return errorType;
    }

    /**
     * @param sdkErrorSource
     * @param errorCode
     */
    public static void thirdSDKErrorEvent(String sdkErrorSource,  String errorCode, String errorMsg) {
        SensorsParamBuilder sensorsParamBuilder = SensorsParamBuilder.create()
                .put("sdk_error_source", sdkErrorSource)
                .put("error_code", errorCode)
                .put("error_detail_code", errorMsg);
        SensorsUploadHelper.addTrackEvent(THIRD_SDK_ERROR, sensorsParamBuilder);
    }



    /**
     * @param apiSource
     * @param apUirl
     * @param errorType
     * @param httpCode
     * @param errorDes
     */
    public static void zssqApiERRorEvent(String apiSource, String apUirl, String errorType, String httpCode, String errorDes) {
        SensorsParamBuilder sensorsParamBuilder = SensorsParamBuilder.create()
                .put(API_SOURCE, apiSource)
                .put(API_URL, apUirl)
                .put(ERROR_TYPE, errorType)
                .put(HTTP_CODE, httpCode)
                .put(ERR_DESC, errorDes);
        SensorsUploadHelper.addTrackEvent(ZSSQ_API_ERROR, sensorsParamBuilder);
    }




}
