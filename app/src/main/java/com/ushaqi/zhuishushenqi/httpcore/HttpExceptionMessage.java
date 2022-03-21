package com.ushaqi.zhuishushenqi.httpcore;

import android.text.TextUtils;

/**
 * 错误信息类
 * add fly
 */
public class HttpExceptionMessage {
    /**
     * 返回code状态码和本地异常状态码
     */
    private String code;
    /**
     * 异常失败信息
     * add fly
     */
    private String message;

    public HttpExceptionMessage() {
    }

    public HttpExceptionMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getExceptionInfo() {
        return (TextUtils.isEmpty(code) ? "code is null" : code) + " message: " + (TextUtils.isEmpty(message) ? "message is null" : message);
    }
}