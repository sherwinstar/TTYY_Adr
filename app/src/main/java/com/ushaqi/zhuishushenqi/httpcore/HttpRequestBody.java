package com.ushaqi.zhuishushenqi.httpcore;

import java.io.File;
import java.util.Map;

/**
 * builder模式构造参数
 * Created by fly on 2018/3/5.
 */

public class HttpRequestBody {

    private HttpRequestMethod requestMethod;
    /**
     * 请求参数
     * add fly
     */
    private Object object;
    /**
     * 数据回调
     * add fly
     */
    private HttpCallBack callBack;

    /**
     * 请求地址
     * add fly
     */
    private String actionUrl;

    /**
     * 返回model类型
     * add fly
     */
    private Class clz;
    /**
     * 请求头
     */
    private Map<String, String> mapHeaders;

    /**
     * 线程回调
     */
    private HttpUiThread httpUiThread;

    /**
     * 上传的文件
     */
    private File file;

    public HttpRequestBody(Builder builder) {
        requestMethod = builder.requestMethod;
        object = builder.object;
        callBack = builder.callBack;
        actionUrl = builder.actionUrl;
        mapHeaders = builder.mapHeaders;
        clz = builder.clz;
        file=builder.file;
        httpUiThread = builder.httpUiThread==null? HttpUiThread.MAINTHREAD: builder.httpUiThread;
    }

    public enum HttpUiThread {
        ISBACKGROUND, MAINTHREAD
    }

    public Class getClz() {
        return clz;
    }

    public HttpCallBack getCallBack() {
        return callBack;
    }

    public HttpRequestMethod getRequestMethod() {
        return requestMethod;
    }

    public Object getObject() {
        return object;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public Map<String, String> getMapHeaders() {
        return mapHeaders;
    }

    public HttpUiThread getHttpUiThread() {
        return httpUiThread;
    }

    public File getFile() {
        return file;
    }

    public void setMapHeaders(Map<String, String> mapHeaders) {
        this.mapHeaders = mapHeaders;
    }

    public static class Builder {

        private HttpRequestMethod requestMethod;
        /**
         * 请求参数
         * add fly
         */
        private Object object;
        /**
         * 数据回调
         * add fly
         */
        private HttpCallBack callBack;

        /**
         * 请求地址
         * add fly
         */
        private String actionUrl;

        /**
         * 返回model类型
         * add fly
         */
        private Class clz;
        /**
         * 请求头设置
         */
        private Map<String, String> mapHeaders;
        /**
         * 线程回调
         * add fly
         */

        private HttpUiThread httpUiThread;

        /**
         * 上传的文件
         */
        private File file;

        public Builder() {

        }

        public Builder(HttpRequestBody requestManager) {
            requestMethod = requestManager.requestMethod;
            object = requestManager.object;
            callBack = requestManager.callBack;
            mapHeaders = requestManager.mapHeaders;
            actionUrl = requestManager.actionUrl;
            clz = requestManager.clz;

            httpUiThread = requestManager.httpUiThread;
            file=requestManager.file;
        }

        public Builder mapHeaders(Map<String, String> mapHeaders) {
            this.mapHeaders = mapHeaders;
            return this;
        }

        public Builder requestMethod(HttpRequestMethod requestMethod) {
            this.requestMethod = requestMethod;
            return this;
        }

        public Builder object(Object object) {
            this.object = object;
            return this;
        }

        public Builder callBack(HttpCallBack callBack) {
            this.callBack = callBack;
            return this;
        }

        public Builder actionUrl(String actionUrl) {
            this.actionUrl = actionUrl;
            return this;
        }

        public Builder clazz(Class clz) {
            this.clz = clz;
            return this;
        }

        public Builder httpUiThread(HttpUiThread httpUiThread) {
            this.httpUiThread = httpUiThread;
            return this;
        }
        public Builder file(File file) {
            this.file = file;
            return this;
        }


        public HttpRequestBody build() {
            if (actionUrl == null) {
                throw new IllegalStateException("url == null");
            }
            return new HttpRequestBody(this);
        }
    }

}
