package com.ushaqi.zhuishushenqi.plugin.social.shareparam;

/**
 * Created by JackHu on 2019/4/29
 */
public class WXMiniShareParam extends WXShareParam {
    private String wxMiniId;
    private String wxPath;
    private byte[] thumb;

    public WXMiniShareParam(int shareType, int shareScene, String pageUrl, String title, String content, String wxMiniId, String wxPath, byte[] thumbnail) {
        super(shareType, shareScene, pageUrl, title, content, null);
        this.thumb = thumbnail;
        this.wxMiniId = wxMiniId;
        this.wxPath = wxPath;
    }

    public String getWxMiniId() {
        return wxMiniId;
    }

    public String getWxPath() {
        return wxPath;
    }

    public byte[] getThumb() {
        return thumb;
    }

}
