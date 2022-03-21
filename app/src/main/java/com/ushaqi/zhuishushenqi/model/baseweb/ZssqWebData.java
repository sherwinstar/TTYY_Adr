package com.ushaqi.zhuishushenqi.model.baseweb;

import android.text.TextUtils;


import com.ushaqi.zhuishushenqi.module.baseweb.helper.WebLoadUtil;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *
 * @ClassName: ZssqWebData
 * @Date: 2019-05-22 10:26
 * @Author: jared
 * @Org: 上海元聚网络科技有限公司
 * @Description: web打开传递的data
 * </p>
 */
public class ZssqWebData implements Serializable {

    private String url;
    private String title;
    private String fullScreenType;
    private String bookId;
    private boolean isFromInner;
    private boolean isFromSplash;
    private boolean isFromSearch;
    private boolean isFromAdvert;

    private String orderId;

    private String productName;

    private int statusBarColor;
    /**
     * 来源位置
     * 0:书架
     * 2:阅读器任务页
     */
    private int fromPosition = -1;

    /**
     * 屏幕显示类型
     * 0*：默认
     *      01：默认不显示title，上滑显示title和icon，
     * 4*：全屏显示，中间显示标题，上滑中间标题变色，同transparentTitle，full_screen_common
     *      01：全屏不显示title，上滑中间显示title
     * 5*：全屏显示，中间显示标题，上滑标题栏变成黑色，同full_screen_special
     * 6*：全屏显示 同full_screen_heart_mode
     *
     */
    private int pageStyle;

    /**
     * 跟神策埋点中运营资源位中destId绑定的，用于获取运营资源位曝光信息
     */
    private String destId;





    /**
     * 支付的产品类型 包月 冲书币  gift
     */
    private String payProductType;

    /**
     * 选择的支付方式 支付宝/微信等
     */
    private String payType;



    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFullScreenType() {
        return fullScreenType;
    }

    public void setFullScreenType(String fullScreenType) {
        this.fullScreenType = fullScreenType;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public boolean isFromInner() {
        return isFromInner;
    }

    public void setFromInner(boolean fromInner) {
        isFromInner = fromInner;
    }

    public boolean isFromSplash() {
        return isFromSplash;
    }

    public void setFromSplash(boolean fromSplash) {
        isFromSplash = fromSplash;
    }

    public boolean isFromSearch() {
        return isFromSearch;
    }

    public void setFromSearch(boolean fromSearch) {
        isFromSearch = fromSearch;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public boolean isFromAdvert() {
        return isFromAdvert;
    }

    public void setFromAdvert(boolean fromAdvert) {
        isFromAdvert = fromAdvert;
    }

    public int getPageStyle() {
        return pageStyle;
    }

    public void setPageStyle(int pageStyle) {
        this.pageStyle = pageStyle;
    }

    public String getDestId() {
        return destId;
    }

    public void setDestId(String destId) {
        this.destId = destId;
    }

    public boolean filterHeartMode() {
        boolean value = false;
        if (TextUtils.isEmpty(url)) {
            return value;
        }
        try {
            HashMap<String, String> map = WebLoadUtil.getUrlParams(url);
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (entry.getKey().equals("fullScreenMode")) {
                    value = Boolean.parseBoolean(entry.getValue());
                }
            }
            return value;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public int getStatusBarColor() {
        return statusBarColor;
    }

    public void setStatusBarColor(int statusBarColor) {
        this.statusBarColor = statusBarColor;
    }

    public int getFromPosition() {
        return fromPosition;
    }

    public void setFromPosition(int fromPosition) {
        this.fromPosition = fromPosition;
    }




}

