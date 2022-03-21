package com.ushaqi.zhuishushenqi.httpcore;

import java.io.IOException;

import okhttp3.Response;

/**
 * Http异常错误状态码处理
 * Created by fly on 2017/3/3 0003.
 */
public class HttpExceptionHandle {

    /**
     * 本地异常状态码 请求超时或者解析response返回字符失败
     * add fly
     */
    public final static String LOCAL_CODE = "666";

    /**
     * 无网络 或者网络不可用提醒用户开启网络
     * add fly
     */
    public final static String CONNECTED_CUT = "777";

    /**
     * 网络请求超时
     * add fly
     */
    public final static String REQUEST_TIME_OUT = "888";

    /**
     * 数据解析异常
     * add fly
     */
    public final static String JSON_TO_OBJECT_ERROR = "999";

    /**
     * 未知错误处理
     * add fly
     */
    private final static String LOCAL_MESSAGE = "未知异常错误信息";

    /**
     * 返回内容错误处理
     * add fly
     */
    private final static String CONTENT_MESSAGE = "返回内容解析有误: ";

    /**
     * 网络不可用 提示信息
     * add fly
     */
    private final static String CONNECTED_MESSAGE = "网络请求异常";

    /**
     * 网络请求超时
     * add fly
     */
    private final static String REQUEST_TIME_OUT_MESSAGE = "网络请求超时";

    /**
     * 服务器返回错误
     */
    private final static String REQUEST_NET_MESSAGE = "网络请求错误";

    /**
     * 处理客户端请求异常
     * add fly
     *
     * @param e IO异常
     * @return 错误model
     */
    public static HttpExceptionMessage handleRequestException(String actionUrl, Exception e) {

        //排除用户网络问题 其他请求不到全部要切换API轮询模式
        handleApiDistribute(actionUrl, REQUEST_TIME_OUT);
        if (e instanceof IOException){
            IOException ioException = (IOException) e;
            String message = ioException.getMessage();
            return new HttpExceptionMessage(REQUEST_TIME_OUT , message);
        }
        return new HttpExceptionMessage(REQUEST_TIME_OUT, e.getCause() == null ? "网络请求异常" :
                (e.getCause().getLocalizedMessage() != null && e.getCause().getLocalizedMessage().contains("timeout") ? "网络请求超时"
                        : e.getCause().getLocalizedMessage()));
    }

    /**
     * 处理http请求返回失败状态码
     * add fly
     *
     * @return 错误model
     */
    public static HttpExceptionMessage handleResponeException(String actionUrl, String code,String message) {
        handleApiDistribute(actionUrl, code);
        return new HttpExceptionMessage("ResponseCode = " + code + "," + message, REQUEST_NET_MESSAGE);
    }

    /**
     * 处理http请求返回body字符解析出错
     * add fly
     *
     * @return 错误model
     */
    public static HttpExceptionMessage handleResponeBodyException(String actionUrl, Response response) {
        String code = "200";
        String body = CONTENT_MESSAGE;
        try {
            code = response.code() + "";
            body = CONTENT_MESSAGE + response.body().string();
        } catch (Exception e) {
        }
        return new HttpExceptionMessage(code, body);
    }

    public static HttpExceptionMessage handleResponeBodyException(boolean isBody) {
        return isBody ? new HttpExceptionMessage("数据异常", "数据返回body为空") : new HttpExceptionMessage("解析类型异常", "请设置请求类型");
    }

    /**
     * json解析错误
     * add fly
     *
     * @return 错误model
     */
    public static HttpExceptionMessage handleJSONException() {
        return new HttpExceptionMessage(JSON_TO_OBJECT_ERROR, "json解析异常");
    }

    /**
     * 用户网络请求超时
     * add fly
     *
     * @return 错误model
     */
    public static HttpExceptionMessage handleTimeOutException() {
        return new HttpExceptionMessage(REQUEST_TIME_OUT, REQUEST_TIME_OUT_MESSAGE);
    }

    /**
     * 处理API轮询机制
     * add fly
     *
     * @param actionUrl 请求的Url
     * @param code      返回状态码
     */
    private static void handleApiDistribute(String actionUrl, String code) {


    }
}


