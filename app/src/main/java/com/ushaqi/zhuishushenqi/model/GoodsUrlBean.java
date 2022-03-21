package com.ushaqi.zhuishushenqi.model;



public class GoodsUrlBean {

    /**
     * ok : true
     * msg : string
     * data : {"mobileUrl":"string","url":"string","shortUrl":"string"}
     */

    private boolean ok;
    private String msg;
    private GoodsUrl data;
    public boolean isOk() {
        return ok;
    }

    public String getMsg() {
        return msg;
    }

    public GoodsUrl getGoodsUrl() {
        return data;
    }

    public static class GoodsUrl {
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

        public String getUrl() {
            return url;
        }

        public String getShortUrl() {
            return shortUrl;
        }
    }


}
