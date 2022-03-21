package com.ushaqi.zhuishushenqi.httpcore;

import java.net.FileNameMap;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * okHttp 参数设置和获取client对象
 * Created by fly on 2016/10/31 0031.
 */
public class OkHttpUtil {

    /**
     * 单例对象
     * add fly
     */
    private static OkHttpUtil okHttpUtilInstace;

    private OkHttpUtil() {
    }

    public static synchronized OkHttpUtil getOkHttpUtilInstace() {
        if (okHttpUtilInstace == null) {
            okHttpUtilInstace = new OkHttpUtil();
        }
        return okHttpUtilInstace;
    }

    /**
     * 不同的请求进行封装call
     * add fly
     *
     * @param callRequestParam 参数对象
     * @return 执行请求器
     */
    public Call getRequestCall(CallRequestParam callRequestParam) {
        Call call = null;
        switch (callRequestParam.getRequestMethod()) {
            case GET:
                call = getRequestGetCall(callRequestParam);
                break;
            case POST:
                call = getRequestPostCall(callRequestParam);
                break;
            case JSON:
                call = getRequestPostJsonCall(callRequestParam);
                break;
            case JSON_POST_ERROR:
                call = getRequestPostErrorJsonCall(callRequestParam);
                break;
            case ADJSON:
                call = getRequestAdHeaderPostJsonCall(callRequestParam);
                break;
            case HEADJSON:
                call = getRequestHeaderPostJsonCall(callRequestParam);
                break;
            case PUT:
                call = getRequestPutCall(callRequestParam);
                break;
            case UPLOADIMG:
                call = getUpLoadRequestCall(callRequestParam);
                break;
            case DELETE:
                call = deleteRequestPostCall(callRequestParam);
                break;
        }
        return call;
    }

    /**
     * 设置阿里DNS
     * add fly
     *
     * @param ruquestUrl
     * @return
     */
    private String setOKHttpDNS(String ruquestUrl) {
        return null;
    }

    /**
     * 获取get请求执行
     * add fly
     *
     * @param callRequestParam 参数
     * @return 执行get请求
     */
    private Call getRequestGetCall(CallRequestParam callRequestParam) {
        try {
            Map<String, String> map = (Map<String, String>) callRequestParam.getParamObject();
            String getRuquestUrl = map == null ? callRequestParam.getActionUrl() : HttpParamMapUtil.getInstance().getGetRuquestUrl(callRequestParam.getActionUrl(), map);
            String requestUrl = setOKHttpDNS(getRuquestUrl);
            if (requestUrl != null) {
                getRuquestUrl = requestUrl;
            }
            String host = new URL(callRequestParam.getActionUrl()).getHost();
            Request.Builder builder = new Request.Builder().url(getRuquestUrl);
            Map<String, String> headerMap = callRequestParam.getHeaderMap();
            if (headerMap == null) {
                headerMap = new HashMap<>();
            }
            headerMap.put("Host", host);
            return setHeaderGetCall(builder, headerMap);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 设置请求头信息
     * add fly
     *
     * @param builder 参数
     * @return 请求call
     */
    private Call setHeaderGetCall(Request.Builder builder, Map<String, String> mapHeader) {
        //add fly 所有的http全部加入请求头
        //getValueEncoded(UserAgentProvider.getInstance().getUserAgentByUmeng().trim())  // TODO: 2018/6/7 注释 去掉umeng 配置UA 问题，和蜜罐无关，已确认
//        String valueEncoded = getValueEncoded(UserAgentProvider.getInstance().getUserAgent().trim());
//        mapHeader.put("User-Agent", valueEncoded);
//        mapHeader.put("X-User-Agent", valueEncoded);
//        if (!TextUtils.isEmpty(ActivityProcessor.getInstance().mClientUA)) {
//            mapHeader.put("Web-User-Agent", ActivityProcessor.getInstance().mClientUA);
//        }
//        if (!"androidMaster".equals(GlobalConstants.APP_IDENTIFY)) {
//            builder.header("x-app-name", GlobalConstants.APP_IDENTIFY);
//        }
//        mapHeader.put("x-android-id", AppHelper.getBase64AndroidId());
//        mapHeader.put("X-Device-Id", AppHelper.getBase64AndroidId());
//        mapHeader.put("B-Zssq", AppHelper.base64DeviceInfo().trim());
        for (Map.Entry<String, String> entry : mapHeader.entrySet()) {
            if (entry == null || entry.getKey() == null || entry.getValue() == null) {
                continue;
            }
            builder.header(entry.getKey(), entry.getValue());
        }
        return OkHttpConfigManager.getConfigManagerInstace().newCall(builder.build());
    }

    /**
     * 由于okhttp header 中的 value 不支持 null, \n 和 中文这样的特殊字符,所以这里
     * 会首先替换 \n ,然后使用 okhttp 的校验方式,校验不通过的话,就返回 encode 后的字符串
     */
    private static String getValueEncoded(String value) {
        try {
            if (value == null) return "null";
            String newValue = value.replace("\n", "");
            String reg = "[\u4e00-\u9fa5]";
            Pattern pat = Pattern.compile(reg);
            Matcher mat = pat.matcher(newValue);
            String repickStr = mat.replaceAll("");

            for (int i = 0, length = repickStr.length(); i < length; i++) {
                char c = repickStr.charAt(i);
                if (c <= '\u001f' || c >= '\u007f') {
                    return URLEncoder.encode(repickStr, "UTF-8");
                }
            }
            return repickStr.trim();
        } catch (Exception e) {
            return "null";
        }
    }

    /**
     * 获取DELETE请求执行
     *
     * @param callRequestParam
     * @return
     */
    private Call deleteRequestPostCall(CallRequestParam callRequestParam) {
        try {
            Map<String, String> map = (Map<String, String>) callRequestParam.getParamObject();
            RequestBody postRequestBody = HttpParamMapUtil.getInstance().getPostRequestBody(map);
            Map<String, String> headerMap = callRequestParam.getHeaderMap();
            if (headerMap == null) {
                headerMap = new HashMap<>();
            }
            String actionUrl = callRequestParam.getActionUrl();
            String requestUrl = setOKHttpDNS(actionUrl);
            if (requestUrl != null) {
                actionUrl = requestUrl;

            }
            String host = new URL(callRequestParam.getActionUrl()).getHost();
            Request.Builder builder = new Request.Builder()
                    .url(actionUrl)
                    .delete(postRequestBody);
            headerMap.put("Host", host);
            return setHeaderGetCall(builder, headerMap);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取Post请求执行
     *
     * @param callRequestParam 参数
     * @return 执行get请求
     */
    private Call getRequestPostCall(CallRequestParam callRequestParam) {
        try {
            Map<String, String> map = (Map<String, String>) callRequestParam.getParamObject();
            RequestBody postRequestBody = HttpParamMapUtil.getInstance().getPostRequestBody(map);
            Map<String, String> headerMap = callRequestParam.getHeaderMap();
            if (headerMap == null) {
                headerMap = new HashMap<>();
            }
            String actionUrl = callRequestParam.getActionUrl();
            String requestUrl = setOKHttpDNS(actionUrl);
            if (requestUrl != null) {
                actionUrl = requestUrl;

            }
            String host = new URL(callRequestParam.getActionUrl()).getHost();
            Request.Builder builder = new Request.Builder()
                    .url(actionUrl)
                    .post(postRequestBody);
            headerMap.put("Host", host);
            return setHeaderGetCall(builder, headerMap);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 获取Post请求执行
     * add fly
     *
     * @param callRequestParam 参数
     * @return 执行get请求
     */
    private Call getRequestPostErrorJsonCall(CallRequestParam callRequestParam) {
        try {
            String jsonStr = callRequestParam.getParamObject().toString();
            RequestBody postRequestBody = RequestBody.create(OkHttpConfigManager.JSON, jsonStr);
            Map<String, String> headerMap = callRequestParam.getHeaderMap();
            if (headerMap == null) {
                headerMap = new HashMap<>();
            }
            String actionUrl = callRequestParam.getActionUrl();
            String requestUrl = setOKHttpDNS(actionUrl);
            if (requestUrl != null) {
                actionUrl = requestUrl;
            }
            String host = new URL(callRequestParam.getActionUrl()).getHost();
            Request.Builder builder = new Request.Builder()
                    .url(actionUrl)
                    .post(postRequestBody);
            headerMap.put("Host", host);
            headerMap.put("Accept", "application/json;charset=UTF-8");
            headerMap.put("X-AuthToken", "9SyECk96oDsTmXfogIieDI0c");
            return setHeaderGetCall(builder, headerMap);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取Post请求执行
     * add fly
     *
     * @param callRequestParam 参数
     * @return 执行get请求
     */
    private Call getRequestPostJsonCall(CallRequestParam callRequestParam) {
        try {
            String jsonStr = callRequestParam.getParamObject().toString();
            RequestBody postRequestBody = RequestBody.create(OkHttpConfigManager.JSON, jsonStr);
            Map<String, String> headerMap = callRequestParam.getHeaderMap();
            if (headerMap == null) {
                headerMap = new HashMap<>();
            }
            String actionUrl = callRequestParam.getActionUrl();
            String requestUrl = setOKHttpDNS(actionUrl);
            if (requestUrl != null) {
                actionUrl = requestUrl;
            }
            String host = new URL(callRequestParam.getActionUrl()).getHost();
            Request.Builder builder = new Request.Builder()
                    .url(actionUrl)
                    .post(postRequestBody);
            headerMap.put("Host", host);
            return setHeaderGetCall(builder, headerMap);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取Post请求执行
     *
     * @param callRequestParam 参数
     */
    private Call getRequestHeaderPostJsonCall(CallRequestParam callRequestParam) {
        try {
            String jsonStr = callRequestParam.getParamObject().toString();
            RequestBody postRequestBody = RequestBody.create(OkHttpConfigManager.JSON, jsonStr);
            Map<String, String> headerMap = callRequestParam.getHeaderMap();
            if (headerMap == null) {
                headerMap = new HashMap<>();
            }
            String actionUrl = callRequestParam.getActionUrl();
            String requestUrl = setOKHttpDNS(actionUrl);
            if (requestUrl != null) {
                actionUrl = requestUrl;
            }
            String host = new URL(callRequestParam.getActionUrl()).getHost();
            Request.Builder builder = new Request.Builder()
                    .url(actionUrl)
                    .post(postRequestBody);
            headerMap.put("Host", host);
            headerMap.put("Accept", "application/json;charset=UTF-8");
            headerMap.put("X-AuthToken", "9SyECk96oDsTmXfogIieDI0c");
            return setHeaderGetCall(builder, headerMap);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Call getRequestAdHeaderPostJsonCall(CallRequestParam callRequestParam) {
        try {
            String jsonStr = callRequestParam.getParamObject().toString();
            RequestBody postRequestBody = RequestBody.create(OkHttpConfigManager.JSON, jsonStr);
            Map<String, String> headerMap = callRequestParam.getHeaderMap();
            if (headerMap == null) {
                headerMap = new HashMap<>();
            }
            String actionUrl = callRequestParam.getActionUrl();
            String requestUrl = setOKHttpDNS(actionUrl);
            if (requestUrl != null) {
                actionUrl = requestUrl;
            }
            String host = new URL(callRequestParam.getActionUrl()).getHost();
            Request.Builder builder = new Request.Builder()
                    .url(actionUrl)
                    .post(postRequestBody);
            headerMap.put("Host", host);
            headerMap.put("Accept", "application/json;charset=UTF-8");
            return setHeaderGetCall(builder, headerMap);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 获取Put请求执行
     *
     * @param callRequestParam 参数
     * @return 执行get请求
     */
    private Call getRequestPutCall(CallRequestParam callRequestParam) {
        try {
            Map<String, String> map = (Map<String, String>) callRequestParam.getParamObject();
            RequestBody postRequestBody = HttpParamMapUtil.getInstance().getPostRequestBody(map);
            Map<String, String> headerMap = callRequestParam.getHeaderMap();
            if (headerMap == null) {
                headerMap = new HashMap<>();
            }
            String actionUrl = callRequestParam.getActionUrl();
            String requestUrl = setOKHttpDNS(actionUrl);
            if (requestUrl != null) {
                actionUrl = requestUrl;
            }
            String host = new URL(callRequestParam.getActionUrl()).getHost();
            Request.Builder builder = new Request.Builder()
                    .url(actionUrl)
                    .put(postRequestBody);
            headerMap.put("Host", host);
            return setHeaderGetCall(builder, headerMap);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取文件MimeType
     *
     * @param filename 文件名
     * @return
     */
    private static String getMimeType(String filename) {
        FileNameMap filenameMap = URLConnection.getFileNameMap();
        String contentType = filenameMap.getContentTypeFor(filename);
        if (contentType == null) {
            contentType = "application/octet-stream"; //* exe,所有的可执行程序
        }
        return contentType;
    }

    /**
     * 获取上传截图请求执行
     * add fly
     */
    public Call getUpLoadRequestCall(CallRequestParam callRequestParam) {
        try {
            Map<String, String> params = (Map<String, String>) callRequestParam.getParamObject();
            //根据文件的后缀名，获得文件类型
            MediaType MEDIA_TYPE_PNG = MediaType.parse(getMimeType(callRequestParam.getFile().getName()));
            MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM);
            for (String key : params.keySet()) {
                builder.addFormDataPart(key, params.get(key));   //追加表单信息
            }
            builder.addFormDataPart("screenshot", callRequestParam.getFile().getName(),
                    RequestBody.create(MEDIA_TYPE_PNG, callRequestParam.getFile()));
            RequestBody postRequestBody = builder.build();

            Request.Builder requestBuilder = new Request.Builder();
            for (String key : callRequestParam.getHeaderMap().keySet()) {
                String value = callRequestParam.getHeaderMap().get(key);
                if (value == null) value = "";
                requestBuilder.addHeader(key, value);
            }
            Request request = requestBuilder.url(callRequestParam.getActionUrl())
                    .post(postRequestBody)
                    .build();
            return OkHttpConfigManager.getConfigManagerInstace().newCall(request);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}


