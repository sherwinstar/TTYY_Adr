package com.ushaqi.zhuishushenqi.model;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * @param
 * @return
 * @add by Jared.Bai
 * @add on 2019-11-01 14:04
 * @Description 服务端返回的投放渠道信息
 */
public class CpsChannelInfo {

    private String channelName;
    private String channelId;
    private int attributionCode;
    private String bookId;
    private String sceneType;
    private String typeValue;
    private String planNumber;
    private String planId;
    private long planClickTs;
    private String merchantId;
    private String merchantName;
    private long activeTime;
    private String uaChannelId;
    private String uaChannelName;
    private long uaClickTs;
    private String uaSceneType;
    private String uaBookId;
    private String uaTypeValue;

    public String getChannel() {
        return channelName;
    }

    public void setChannel(String channel) {
        this.channelName = channel;
    }

    public String getCpsId() {
        return channelId;
    }

    public void setCpsId(String cpsId) {
        this.channelId = cpsId;
    }

    public int getAttributionCode() {
        return attributionCode;
    }

    public void setAttributionCode(int attributionCode) {
        this.attributionCode = attributionCode;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getSceneType() {
        return sceneType;
    }

    public void setSceneType(String sceneType) {
        this.sceneType = sceneType;
    }

    public String getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue;
    }

    public String getPlanNumber() {
        return planNumber;
    }

    public void setPlanNumber(String planNumber) {
        this.planNumber = planNumber;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public long getPlanClickTs() {
        return planClickTs;
    }

    public void setPlanClickTs(long planClickTs) {
        this.planClickTs = planClickTs;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public long getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(long activeTime) {
        this.activeTime = activeTime;
    }

    public String getUaChannelId() {
        return uaChannelId;
    }

    public void setUaChannelId(String uaChannelId) {
        this.uaChannelId = uaChannelId;
    }

    public String getUaChannelName() {
        return uaChannelName;
    }

    public void setUaChannelName(String uaChannelName) {
        this.uaChannelName = uaChannelName;
    }

    public long getUaClickTs() {
        return uaClickTs;
    }

    public void setUaClickTs(long uaClickTs) {
        this.uaClickTs = uaClickTs;
    }

    public String getUaSceneType() {
        return uaSceneType;
    }

    public void setUaSceneType(String uaSceneType) {
        this.uaSceneType = uaSceneType;
    }

    public String getUaBookId() {
        return uaBookId;
    }

    public void setUaBookId(String uaBookId) {
        this.uaBookId = uaBookId;
    }

    public String getUaTypeValue() {
        return uaTypeValue;
    }

    public void setUaTypeValue(String uaTypeValue) {
        this.uaTypeValue = uaTypeValue;
    }

    public String getLatestBookId() {
        if (planClickTs == 0 && uaClickTs == 0) {
            return bookId;
        }
        if (planClickTs == 0 && uaClickTs > 0) {
            return uaBookId;
        }
        if (planClickTs > 0 && uaClickTs == 0) {
            return bookId;
        }
        if (planClickTs > 0 && uaClickTs > 0) {
            if (planClickTs > uaClickTs) {
                return bookId;
            } else {
                return uaBookId;
            }
        }
        return bookId;
    }

    public String getLatestSceneType() {
        if (planClickTs == 0 && uaClickTs == 0) {
            return sceneType;
        }
        if (planClickTs == 0 && uaClickTs > 0) {
            return uaSceneType;
        }
        if (planClickTs > 0 && uaClickTs == 0) {
            return sceneType;
        }
        if (planClickTs > 0 && uaClickTs > 0) {
            if (planClickTs > uaClickTs) {
                return sceneType;
            } else {
                return uaSceneType;
            }
        }
        return sceneType;
    }

    public String getLatestTypeValue() {
        if (planClickTs == 0 && uaClickTs == 0) {
            return typeValue;
        }
        if (planClickTs == 0 && uaClickTs > 0) {
            return uaTypeValue;
        }
        if (planClickTs > 0 && uaClickTs == 0) {
            return typeValue;
        }
        if (planClickTs > 0 && uaClickTs > 0) {
            if (planClickTs > uaClickTs) {
                return typeValue;
            } else {
                return uaTypeValue;
            }
        }
        return typeValue;
    }

    public String getLatestChannelId(){
        if (planClickTs == 0 && uaClickTs == 0) {
            return channelId;
        }
        if (planClickTs == 0 && uaClickTs > 0) {
            return uaChannelId;
        }
        if (planClickTs > 0 && uaClickTs == 0) {
            return channelId;
        }
        if (planClickTs > 0 && uaClickTs > 0) {
            if (planClickTs > uaClickTs) {
                return channelId;
            } else {
                return uaChannelId;
            }
        }
        return channelId;
    }

    public String getLatestChannelName(){
        if (planClickTs == 0 && uaClickTs == 0) {
            return channelName;
        }
        if (planClickTs == 0 && uaClickTs > 0) {
            return uaChannelName;
        }
        if (planClickTs > 0 && uaClickTs == 0) {
            return channelName;
        }
        if (planClickTs > 0 && uaClickTs > 0) {
            if (planClickTs > uaClickTs) {
                return channelName;
            } else {
                return uaChannelName;
            }
        }
        return channelName;
    }

    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019-11-01 14:07
     * @Description 将对象转成json字符串
     */
    public static String toJson(CpsChannelInfo cpsChannelInfo) {
        if (cpsChannelInfo == null) {
            return "";
        }
        try {
            Gson gson = new Gson();
            return gson.toJson(cpsChannelInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019-11-01 14:12
     * @Description 将json转成cpsInfo对象
     */
    public static CpsChannelInfo fromJson(String json) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        try {
            Gson gson = new Gson();
            return gson.fromJson(json, CpsChannelInfo.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isSceneTypeValid() {
        return !(TextUtils.isEmpty(getLatestSceneType()) || "-1".equals(getLatestSceneType()));
    }

    public boolean isChannelIdValid(){
        return !(TextUtils.isEmpty(channelId) || "-1".equals(channelId));
    }

    public boolean isUaChannelIdValid(){
        return !(TextUtils.isEmpty(uaChannelId) || "-1".equals(uaChannelId));
    }

}
