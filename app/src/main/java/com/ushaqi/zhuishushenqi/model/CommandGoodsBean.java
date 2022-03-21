package com.ushaqi.zhuishushenqi.model;


import java.util.List;


public  class CommandGoodsBean {


    /**
     * ok : true
     * msg :
     * data : [{"sType":"taobao","sTypeIcon":"https://www.jd.com/favicon.ico","merchantTitle":"小米官方旗舰店","payUser":6897,"goodsImgUrl":"https://img.alicdn.com/bao/uploaded/i3/1714128138/O1CN01FyV6MO29zFrNAXuDe_!!0-item_pic.jpg","goodsTitle":"小米无线蓝牙双模鼠标静音版游戏光电小巧便携小米官方旗舰店滑鼠","goodsId":"627825165768","coupons":0,"saveMoney":"0.00","price":69,"originalprice":69,"jumpUrl":"","searchId":null}]
     */

    private boolean ok;
    private String msg;
    private List<CommandGoods> data;

    public boolean isOk() {
        return ok;
    }

    public String getMsg() {
        return msg;
    }

    public List<CommandGoods> getData() {
        return data;
    }

    public static class  CommandGoods  {
        /**
         * sType : taobao
         * sTypeIcon : https://www.jd.com/favicon.ico
         * merchantTitle : 小米官方旗舰店
         * payUser : 6897
         * goodsImgUrl : https://img.alicdn.com/bao/uploaded/i3/1714128138/O1CN01FyV6MO29zFrNAXuDe_!!0-item_pic.jpg
         * goodsTitle : 小米无线蓝牙双模鼠标静音版游戏光电小巧便携小米官方旗舰店滑鼠
         * goodsId : 627825165768
         * coupons : 0
         * saveMoney : 0.00
         * price : 69
         * originalprice : 69
         * jumpUrl :
         * searchId : null
         */

        private String sType;
        private String sTypeIcon;
        private String merchantTitle;
        private String payUser;
        private String goodsImgUrl;
        private String goodsTitle;
        private String goodsId;
        private double coupons;
        private String saveMoney;
        private double price;
        private double originalprice;
        private String jumpUrl;
        private String searchId;

        public void setCoupons(double coupons) {
            this.coupons = coupons;
        }

        public String getsType() {
            return sType;
        }

        public String getsTypeIcon() {
            return sTypeIcon;
        }

        public String getMerchantTitle() {
            return merchantTitle;
        }

        public String  getPayUser() {
            return payUser;
        }

        public String getGoodsImgUrl() {
            return goodsImgUrl;
        }

        public String getGoodsTitle() {
            return goodsTitle;
        }

        public String getGoodsId() {
            return goodsId;
        }

        public double getCoupons() {
            return coupons;
        }

        public String getSaveMoney() {
            return saveMoney;
        }

        public double getPrice() {
            return price;
        }

        public double getOriginalprice() {
            return originalprice;
        }

        public String getJumpUrl() {
            return jumpUrl;
        }

        public String getSearchId() {
            return searchId;
        }

        @Override
        public String toString() {
            return "CommandGoods{" +
                    "sType='" + sType + '\'' +
                    ", sTypeIcon='" + sTypeIcon + '\'' +
                    ", merchantTitle='" + merchantTitle + '\'' +
                    ", payUser=" + payUser +
                    ", goodsImgUrl='" + goodsImgUrl + '\'' +
                    ", goodsTitle='" + goodsTitle + '\'' +
                    ", goodsId='" + goodsId + '\'' +
                    ", coupons=" + coupons +
                    ", saveMoney='" + saveMoney + '\'' +
                    ", price=" + price +
                    ", originalprice=" + originalprice +
                    ", jumpUrl='" + jumpUrl + '\'' +
                    ", searchId='" + searchId + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CommandGoodsBean{" +
                "ok=" + ok +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
