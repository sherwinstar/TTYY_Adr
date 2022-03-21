package com.ushaqi.zhuishushenqi.httpcore;

import okhttp3.Call;
import okhttp3.Request;

/**
 * 监听 请求 运维需要
 * Created by fly on 2018/6/1.
 */

public class MonitoringManagerHelper {

    private static MonitoringManagerHelper instance;

    private MonitoringManagerHelper() {
    }

    public static MonitoringManagerHelper getInstance() {
        if (instance == null) {
            instance = new MonitoringManagerHelper();
        }
        return instance;
    }

    /**
     * 获取请求的URL
     *
     * @param call 执行器
     * @param url  host
     * @return 组织好的URL
     */
    public String getRequestCallUrl(Call call, String url) {
        try {
            Request request = call.request();
            if (request != null) {
                return request.url().url().toString();
            }

        } catch (Exception e) {
        }
        return url;
    }

    /**
     * 使用小米网络监控工具来记录请求错误
     * add fly
     *
     * @param url   请求的URL
     * @param start 开始请求的时间
     * @param code  返回状态码
     */
    public void recordHttpEvent(String url, long start, int code) {
//        try {
//            long end = new Date().getTime();
//            if (end > start) {
//                long l = end - start;
//                URLStatsRecorder.addHttpEvent(new HttpEvent(url, end - start, code, ""));
//                MobclickAgent.onEvent(GlobalConfig.getInstance().getContext(), AppConstants.API_HTTP_ERROR, "");
//            }
//        } catch (Exception e) {
//
//        }
    }


    public void recordWebErrorEvent(String url, long start, String exceptionName) {
//        try {
//            long end = new Date().getTime();
//            if (end > start) {
//                URLStatsRecorder.addHttpEvent(new HttpEvent(url, end - start));
//                MobclickAgent.onEvent(GlobalConfig.getInstance().getContext(), AppConstants.API_HTTP_ERROR, exceptionName);
//            }
//        } catch (Exception e) {
//
//        }
    }

    public void dealReportServerError(Call call, String url, long start, String e) {
    }
}
