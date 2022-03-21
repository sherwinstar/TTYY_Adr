package com.ushaqi.zhuishushenqi.model;

import java.io.Serializable;

/**
 * @author chengwencan
 * @date 2021/9/1
 * Describe：
 */
public class BindPddRelationBean implements Serializable {


    /**
     * ok : true
     * msg : string
     * data : {"mobileUrl":"string","url":"string","shortUrl":"string"}
     */

    private Boolean ok;
    private String msg;
    private DataBean data;

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * mobileUrl : string
         * url : string
         * shortUrl : string
         */

        private String mobileUrl;
        private String url;
        private String shortUrl;

        public String getMobileUrl() {
            return mobileUrl;
        }

        public void setMobileUrl(String mobileUrl) {
            this.mobileUrl = mobileUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getShortUrl() {
            return shortUrl;
        }

        public void setShortUrl(String shortUrl) {
            this.shortUrl = shortUrl;
        }
    }
}
