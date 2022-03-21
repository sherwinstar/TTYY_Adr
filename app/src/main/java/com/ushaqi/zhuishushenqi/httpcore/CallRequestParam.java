package com.ushaqi.zhuishushenqi.httpcore;

import java.io.File;
import java.util.Map;

/**
 * 组织参数
 * Created by fly on 2018/3/5.
 */

public class CallRequestParam {

    private HttpRequestMethod requestMethod;
    private String actionUrl;
    private Object paramObject;
    private Map<String,String>headerMap;
    private File file;
    private CallRequestParam(Builder builder){
        this.actionUrl=builder.actionUrl;
        this.headerMap=builder.headerMap;
        this.paramObject =builder.paramObject;
        this.requestMethod=builder.requestMethod;
    }

    public HttpRequestMethod getRequestMethod() {
        return requestMethod;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    public Object getParamObject() {
        return paramObject;
    }

    public void setRequestMethod(HttpRequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }

    public File getFile() {
        return file;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    public void setHeaderMap(Map<String, String> headerMap) {
        this.headerMap = headerMap;
    }

    public void setParamObject(Object paramObject) {
        this.paramObject = paramObject;
    }
    public static class Builder {
        private HttpRequestMethod requestMethod;
        private String actionUrl;
        private Object paramObject;
        private Map<String,String> headerMap;
        private File file;

        public Builder() {

        }

        public Builder(CallRequestParam callRequestParam) {
            actionUrl = callRequestParam.actionUrl;
            paramObject = callRequestParam.paramObject;
            headerMap = callRequestParam.headerMap;
            file=callRequestParam.file;

        }
        public Builder requestMethod(HttpRequestMethod requestMethod){
            this.requestMethod=requestMethod;
            return this;
        }
        public Builder actionUrl(String actionUrl) {
            this.actionUrl = actionUrl;
            return this;
        }

        public Builder  paramObject(Object paramObject) {
            this.paramObject = paramObject;
            return this;
        }
        public Builder  file(File file) {
            this.file = file;
            return this;
        }
        public Builder headerMap(Map<String,String>headerMap) {
            this.headerMap = headerMap;
            return this;
        }


        public CallRequestParam build() {
            if (actionUrl == null) throw new IllegalStateException("url == null");
            return new CallRequestParam(this);
        }
    }
}
