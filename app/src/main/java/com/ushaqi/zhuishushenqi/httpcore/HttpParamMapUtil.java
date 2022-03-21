package com.ushaqi.zhuishushenqi.httpcore;

import android.text.TextUtils;

import java.net.URLEncoder;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 组合请求参数
 * Created by fly on 2016/12/26 0026.
 */
public class HttpParamMapUtil {

    private static HttpParamMapUtil instance;

    private HttpParamMapUtil() {
    }

    public static synchronized HttpParamMapUtil getInstance() {
        if (instance == null) {
            instance = new HttpParamMapUtil();
        }
        return instance;
    }

    /**
     * 获取组装get 请求url
     *
     * @param actionUrl 请求地址
     * @param paramsMap 请求参数
     * @return url 组装好的url
     */
    public String getGetRuquestUrl(String actionUrl, Map<String, String> paramsMap) {
        StringBuilder tempParams = new StringBuilder();
        String requestUrl = "";
        try {
            int pos = 0;
            for (String key : paramsMap.keySet()) {
                if (pos > 0) {
                    tempParams.append("&");
                }
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8"))); //对参数进行URLEncoder
                pos++;
            }
            requestUrl = String.format("%s?%s", actionUrl, tempParams.toString());//补全请求地址
        } catch (Exception e) {

        }
        return requestUrl;
    }

    /**
     * 获取 组装请求头 表单
     *
     * @param paramsMap post参数
     * @return 表单
     */
    public Request.Builder getPostRequestBuilder(Map<String, String> paramsMap) {
        Request.Builder builder = null;
        try {
            builder = new Request.Builder();//创建一个Request.Builder
            for (String key : paramsMap.keySet()) {
                builder.addHeader(key, paramsMap.get(key));   //追加表单信息
            }
        } catch (Exception e) {
        }
        return builder;
    }

    /**
     * 获取 组装form 表单
     *
     * @param paramsMap post参数
     * @return 表单
     */
    public RequestBody getPostRequestBody(Map<String, String> paramsMap) {
        RequestBody formBody = null;
        try {
            FormBody.Builder builder = new FormBody.Builder();//创建一个FormBody.Builder
            for (String key : paramsMap.keySet()) {
                if (!TextUtils.isEmpty(paramsMap.get(key))) {
                    builder.add(key, paramsMap.get(key));   //追加表单信息
                }
            }
            formBody = builder.build(); //生成表单实体对象
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formBody;
    }
}
