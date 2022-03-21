package com.ushaqi.zhuishushenqi.model;

import java.io.Serializable;

/**
 * @author chengwencan
 * @date 2021/7/15
 * Describe：
 */
public class UpdateApkConfig extends BaseModel{


    /**
     * data : {"name":"string","context":"string","version":0}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }


    public static class DataBean implements Serializable {
        /**
         * name : string
         * context : string
         * version : 0
         */

        private String name;
        private String context;
        private Integer version;
        private String  apkUrl;

        public String getApkUrl() {
            return apkUrl;
        }

        public void setApkUrl(String apkUrl) {
            this.apkUrl = apkUrl;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public Integer getVersion() {
            return version;
        }

        public void setVersion(Integer version) {
            this.version = version;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
