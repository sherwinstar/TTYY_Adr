package com.ushaqi.zhuishushenqi.plugin.social.shareparam;


import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Andy.zhang
 * @date 2019/8/21
 */
public class ShareParam {
    /**
     * 标题
     */
    protected String mTitle;
    /**
     * 内容
     */
    protected String mContent;
    /**
     * 目标地址
     */
    protected String mTargetUrl;

    private Map<String, Object> mExtras = new HashMap<>(8);

    public ShareParam() {

    }

    public ShareParam(String title, String content) {
        mTitle = title;
        mContent = content;
    }

    public ShareParam(String title, String content, String targetUrl) {
        mTitle = title;
        mContent = content;
        mTargetUrl = targetUrl;
    }

    public String getTitle() {
        return mTitle != null ? mTitle : "";
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getContent() {
        return mContent != null ? mContent : "";
    }

    public void setContent(String content) {
        this.mContent = content;
    }

    public String getTargetUrl() {
        return mTargetUrl != null ? mTargetUrl : "";
    }

    public void setTargetUrl(String targetUrl) {
        this.mTargetUrl = targetUrl;
    }

    public void putExtra(String key, Object value) {
        mExtras.put(key, value);
    }

    public Object getExtra(String key) {
        return mExtras.get(key);
    }

    public void setExtraMap(Map<String, Object> extraMap) {
        mExtras = extraMap;
    }


}
