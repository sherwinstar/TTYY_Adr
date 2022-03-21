package com.ushaqi.zhuishushenqi.util;

import android.util.Base64;

import com.ushaqi.zhuishushenqi.httpcore.HttpCallBack;
import com.ushaqi.zhuishushenqi.httpcore.HttpExceptionMessage;
import com.ushaqi.zhuishushenqi.httpcore.HttpRequestMethod;
import com.ushaqi.zhuishushenqi.httpcore.RequestManager;
import com.ushaqi.zhuishushenqi.model.AddBookResponse;
import com.ushaqi.zhuishushenqi.repository.GlobalPreference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by shijingxing on 2019/3/25..
 * app log上传接口
 */
public class AppActionPostHelper {
    private static String sAppActionUrl = "http://d.1391.com:50207/v1.0.0/h/warehouse/log/applog/batch/receive?timestamp=%s&rn=%s&sign_type=MD5&sign=%s&product_line=%s";
    private static String beforeSign = "app_secret=b5f8fe5k59eb0c6524787b6d1ar91924&biz_content=%s&rn=%s&sign_type=MD5&timestamp=%s&product_line=26";
    private static SimpleDateFormat sEventCreatedTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);

    public static void postAppActionToService(String requestBody, final int operateType) {
        String actionUrl = getRequestUrl(requestBody);
        RequestManager.getInstance().sendRequest(HttpRequestMethod.HEADJSON, actionUrl, requestBody, AddBookResponse.class, new HttpCallBack() {
            @Override
            public void onFailure(HttpExceptionMessage obj) {
            }

            @Override
            public void onSuccess(Object obj) {
                AddBookResponse response = (AddBookResponse) obj;
                if (response != null && response.getCode() != null || !"200".equals(response.getCode())) {
                    //保存上一次的打开应用事件的上传时间（为降低BI服务器压力，启动事件客户端1天只传1次）
                    //保存上一次的打开应用事件的上传时间（为降低BI服务器压力，启动事件客户端1天只传1次）
                    if (operateType == AppActionHelper.OPERATE_TYPE_OPEN_APP) {
                        GlobalPreference.getInstance().saveString(AppActionHelper.SP_LAST_TIME_POST_OPEN_APP_ACTION, String.valueOf(getToday()));
                    }
                    if (operateType == AppActionHelper.OPERATE_TYPE_OUT_APP) {
                        GlobalPreference.getInstance().saveString(AppActionHelper.SP_LAST_TIME_POST_CLOSE_APP_ACTION, String.valueOf(getToday()));
                    }
                }
            }
        });
    }

    /**
     * 获取加密后的url
     *
     * @param requestBody
     * @return
     */
    private static String getRequestUrl(String requestBody) {
        try {
            String time = produceEventCreatedTime();
            String random = ((int) ((Math.random() * 9 + 1) * 100000)) + "";
            String needSignDate = String.format(beforeSign, requestBody, random, time);
            byte[] bytes = Md5Util.md5(needSignDate);
            String SignedDate = Base64.encodeToString(bytes, Base64.URL_SAFE | Base64.NO_PADDING);
            SignedDate = SignedDate.replaceAll("\n", "").trim();
            return String.format(sAppActionUrl, time, random, SignedDate, "26");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String produceEventCreatedTime() {
        return sEventCreatedTimeFormat.format(new Date(System.currentTimeMillis()));
    }
    public static int getToday() {
        Date date = new Date();
        String today = sdf.format(date);
        return StringUtils.toInt(today);
    }


}
