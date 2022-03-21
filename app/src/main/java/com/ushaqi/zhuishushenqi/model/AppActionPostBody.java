package com.ushaqi.zhuishushenqi.model;

import com.ushaqi.zhuishushenqi.local.UserHelper;
import com.ushaqi.zhuishushenqi.util.AppEventCommonInfoHelper;
import com.ushaqi.zhuishushenqi.util.AppHelper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by shijingxing on 2019/3/25..
 * app行为日志body类
 */
public class AppActionPostBody implements Serializable {
    private static final long serialVersionUID = 7848288000198528157L;

    private List<AppActionBean> dataInfos;

    public AppActionPostBody(List<AppActionBean> dataInfos) {
        this.dataInfos = dataInfos;
    }


    public static class AppActionBean implements Serializable {
        private static final long serialVersionUID = -2379489425883513539L;
        private String app_market_name;
        private String client_version;
        private String log_time;
        private String operate_type;
        private int platform = 2;
        private String device_imei;
        private String device_mac;
        private String user_id;
        /**
         * 19/11/05修改为上传OAID
         */
        private String user_name;
        /**
         * 19/11/05修改为上传投放渠道id
         */
        private String activity_code;
        private String device_type = "Android";
        private String phone_model;
        private String phone_resolution;
        private String os_version;
        private String param1;
        private String param2;
        private String param3;
        private String param4;
        private String param5;
        private String param6;
        private String param7;
        private String param8;

        public AppActionBean(String operateType, Map<String, String> paramMap){
            this.app_market_name = getDefaultValueIfEmpty(AppEventCommonInfoHelper.getDeliverChannel());
            this.client_version = getDefaultValueIfEmpty(AppEventCommonInfoHelper.getVersion());
            this.log_time = AppEventCommonInfoHelper.produceEventCreatedTime();
            this.operate_type = operateType;
            this.device_imei = getDefaultValueIfEmpty(AppEventCommonInfoHelper.getImei());
            this.device_mac = getDefaultValueIfEmpty(AppEventCommonInfoHelper.getMac());
            this.user_id = UserHelper.getInstance().getUserId();
            this.user_name = AppHelper.getOAID();
            this.phone_model = getDefaultValueIfEmpty(AppEventCommonInfoHelper.getPhoneModel());
            this.phone_resolution = getDefaultValueIfEmpty(AppEventCommonInfoHelper.getPhoneDisplay());
            this.activity_code = AppEventCommonInfoHelper.getDeliverChannelId();
            this.os_version = getDefaultValueIfEmpty(AppEventCommonInfoHelper.getPhoneVersion());

            if(paramMap == null){
                return;
            }
            this.param1 = paramMap.get("param1");
            this.param2 = paramMap.get("param2");
            this.param3 = paramMap.get("param3");
            this.param4 = paramMap.get("param4");
            this.param5 = paramMap.get("param5");
            this.param6 = paramMap.get("param6");
            this.param7 = paramMap.get("param7");
            this.param8 = paramMap.get("param8");
        }

        private String getDefaultValueIfEmpty(String oldValue){
            if(oldValue == null || oldValue.isEmpty()){
                return "-1";
            }
            return oldValue;
        }

        public String getApp_market_name() {
            return app_market_name;
        }

        public void setApp_market_name(String app_market_name) {
            this.app_market_name = app_market_name;
        }

        public String getClient_version() {
            return client_version;
        }

        public void setClient_version(String client_version) {
            this.client_version = client_version;
        }

        public String getLog_time() {
            return log_time;
        }

        public void setLog_time(String log_time) {
            this.log_time = log_time;
        }

        public String getOperate_type() {
            return operate_type;
        }

        public void setOperate_type(String operate_type) {
            this.operate_type = operate_type;
        }

        public int getPlatform() {
            return platform;
        }

        public void setPlatform(int platform) {
            this.platform = platform;
        }

        public String getDevice_imei() {
            return device_imei;
        }

        public void setDevice_imei(String device_imei) {
            this.device_imei = device_imei;
        }

        public String getDevice_mac() {
            return device_mac;
        }

        public void setDevice_mac(String device_mac) {
            this.device_mac = device_mac;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getDevice_type() {
            return device_type;
        }

        public void setDevice_type(String device_type) {
            this.device_type = device_type;
        }

        public String getPhone_model() {
            return phone_model;
        }

        public void setPhone_model(String phone_model) {
            this.phone_model = phone_model;
        }

        public String getPhone_resolution() {
            return phone_resolution;
        }

        public void setPhone_resolution(String phone_resolution) {
            this.phone_resolution = phone_resolution;
        }

        public String getActivity_code() {
            return activity_code;
        }

        public void setActivity_code(String activity_code) {
            this.activity_code=activity_code;
        }

        public String getOs_version() {
            return os_version;
        }

        public void setOs_version(String os_version) {
            this.os_version = os_version;
        }

        public String getParam1() {
            return param1;
        }

        public void setParam1(String param1) {
            this.param1 = param1;
        }

        public String getParam2() {
            return param2;
        }

        public void setParam2(String param2) {
            this.param2 = param2;
        }

        public String getParam3() {
            return param3;
        }

        public void setParam3(String param3) {
            this.param3 = param3;
        }

        public String getParam4() {
            return param4;
        }

        public void setParam4(String param4) {
            this.param4 = param4;
        }

        public String getParam5() {
            return param5;
        }

        public void setParam5(String param5) {
            this.param5 = param5;
        }

        public String getParam6() {
            return param6;
        }

        public void setParam6(String param6) {
            this.param6 = param6;
        }

        public String getParam7() {
            return param7;
        }

        public void setParam7(String param7) {
            this.param7 = param7;
        }

        public String getParam8() {
            return param8;
        }

        public void setParam8(String param8) {
            this.param8 = param8;
        }
    }


}
