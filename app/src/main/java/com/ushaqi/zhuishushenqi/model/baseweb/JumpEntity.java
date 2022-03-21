package com.ushaqi.zhuishushenqi.model.baseweb;

import android.text.TextUtils;



/**
 * <p>
 *
 * @ClassName: JumpEntity
 * @Date: 2019-05-23 18:05
 * @Author: jared
 * @Org: 上海元聚网络科技有限公司
 * @Description: 跳转对象
 * </p>
 */
public class JumpEntity {

    private String link;
    private String jumpType;
    private String title;
    private String pageType;
    private String id;
    private String code;
    private String source;
    private String mobile;
    private boolean transparentTitle;//是否跳全屏的webview
    /**
     * web样式，可以自定义拓展
     * ""，默认走老的样式逻辑
     * 1，沉浸式样式-积分商城，福利社
     */
    private String screenStyle = "";

    private int pageStyle;



    private String type;
    private String url;
    private String goodsId;

    /**
     * H5给的搜索关键字，如果跳转搜索带有这个参数，则直接跳转搜索结果页
     */
    private String keyword;

    private String wechatGHID;
    private String wechatPath;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getJumpType() {
        return jumpType;
    }

    public void setJumpType(String jumpType) {
        this.jumpType = jumpType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPageType() {
        return pageType;
    }

    public void setPageType(String pageType) {
        this.pageType = pageType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public boolean isTransparentTitle() {
        return transparentTitle;
    }

    public void setTransparentTitle(boolean transparentTitle) {
        this.transparentTitle = transparentTitle;
    }


    public int getPageStyle() {
        return pageStyle;
    }

    public void setPageStyle(int pageStyle) {
        this.pageStyle = pageStyle;
    }


    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getScreenStyle() {
        return screenStyle;
    }

    public void setScreenStyle(String screenStyle) {
        this.screenStyle = screenStyle;
    }

    public boolean isNewFullScreenStyle() {
        return !TextUtils.isEmpty(screenStyle) && "1".equals(screenStyle);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getWechatGHID() {
        return wechatGHID;
    }

    public void setWechatGHID(String wechatGHID) {
        this.wechatGHID = wechatGHID;
    }

    public String getWechatPath() {
        return wechatPath;
    }

    public void setWechatPath(String wechatPath) {
        this.wechatPath = wechatPath;
    }


    public String toPddSmallAppString() {
        return "{" +
                "jumpType='" + jumpType + '\'' +
                ", pageType='" + pageType + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", wechatGHID='" + wechatGHID + '\'' +
                ", wechatPath='" + wechatPath + '\'' +
                '}';
    }
}

