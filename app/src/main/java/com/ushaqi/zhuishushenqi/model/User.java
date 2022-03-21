package com.ushaqi.zhuishushenqi.model;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * 用户
 *
 * @author Shaojie
 * @Date 2013-10-18 下午1:15:07
 */

public class User implements Serializable {

    private static final long serialVersionUID = 4596333208470430366L;

    /**
     * token : string
     * userId : string
     * sex : string
     * nikeName : string
     * headImgurl : string
     * regTime : 2021-05-24T07:19:27.052Z
     * lastLoginTime : 2021-05-24T07:19:27.052Z
     * isBindingMobile : true
     * bindingMobile : string
     * isBindingIDCard : true
     * idCardNum : string
     * idCardName : string
     * isBindingAliPay : true
     * aliPayAccountUserName : string
     */

    private String token="";
    private String userId="";
    private String nickname="";
    private String sex="";
    private String headImgurl="";
    private String regTime="";
    private String lastLoginTime="";
    private boolean isBindingMobile;
    private String bindingMobile="";
    private boolean isBindingIDCard;
    private String idCardNum="";
    private String idCardName="";
    private boolean isBindingAliPay;
    private String aliPayAccountUserName="";
    private boolean isBindingPdd;
    private boolean isBindingTaobao;
    private int userType;
    /**
     * 邀请码
     */
    private String promoterId="";
    /**
       * 被邀请码
    */
    private String fpromoterId="";
    private boolean isFirstOrder=false;

    public boolean isFirstOrder() {
        return isFirstOrder;
    }

    public void setFirstOrder(boolean firstOrder) {
        isFirstOrder = firstOrder;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNikeName() {
        return nickname;
    }

    public void setNikeName(String nikeName) {
        this.nickname = nikeName;
    }

    public String getHeadImgurl() {
        return headImgurl;
    }

    public void setHeadImgurl(String headImgurl) {
        this.headImgurl = headImgurl;
    }

    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public boolean isBindingMobile() {
        return !TextUtils.isEmpty(bindingMobile);
    }



    public String getBindingMobile() {
        return bindingMobile;
    }

    public void setBindingMobile(String bindingMobile) {
        this.bindingMobile = bindingMobile;
    }

    public boolean isBindingIDCard() {
        return !TextUtils.isEmpty(idCardNum);
    }



    public String getIdCardNum() {
        return idCardNum;
    }

    public void setIdCardNum(String idCardNum) {
        this.idCardNum = idCardNum;
    }

    public String getIdCardName() {
        return idCardName;
    }

    public void setIdCardName(String idCardName) {
        this.idCardName = idCardName;
    }

    public boolean isBindingAliPay() {
        return !TextUtils.isEmpty(aliPayAccountUserName);
    }

    public String getAliPayAccountUserName() {
        return aliPayAccountUserName;
    }

    public void setAliPayAccountUserName(String aliPayAccountUserName) {
        this.aliPayAccountUserName = aliPayAccountUserName;
    }

    public void setBindingAliPay(boolean bindingAliPay) {
        isBindingAliPay = bindingAliPay;
    }

    public boolean isBindingPdd() {
        return isBindingPdd;
    }

    public void setBindingPdd(boolean bindingPdd) {
        isBindingPdd = bindingPdd;
    }

    public boolean isBindingTaobao() {
        return isBindingTaobao;
    }

    public void setBindingTaobao(boolean bindingTaobao) {
        isBindingTaobao = bindingTaobao;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getPromoterId() {
        return promoterId;
    }

    public void setPromoterId(String promoterId) {
        this.promoterId = promoterId;
    }

    public String getFpromoterId() {
        return fpromoterId;
    }

    public void setFpromoterId(String fpromoterId) {
        this.fpromoterId = fpromoterId;
    }

    @Override
    public String toString() {
        return "User{" +
                "token='" + token + '\'' +
                ", userId='" + userId + '\'' +
                ", sex='" + sex + '\'' +
                ", nikeName='" + nickname + '\'' +
                ", headImgurl='" + headImgurl + '\'' +
                ", regTime='" + regTime + '\'' +
                ", lastLoginTime='" + lastLoginTime + '\'' +
                ", isBindingMobile=" + isBindingMobile +
                ", bindingMobile='" + bindingMobile + '\'' +
                ", isBindingIDCard=" + isBindingIDCard +
                ", idCardNum='" + idCardNum + '\'' +
                ", idCardName='" + idCardName + '\'' +
                ", isBindingAliPay=" + isBindingAliPay +
                ", aliPayAccountUserName='" + aliPayAccountUserName + '\'' +
                '}';
    }
}
