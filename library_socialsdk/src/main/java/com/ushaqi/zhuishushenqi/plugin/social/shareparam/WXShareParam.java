package com.ushaqi.zhuishushenqi.plugin.social.shareparam;

import android.graphics.Bitmap;

/**
 * Created by JackHu on 2019/4/28
 */
public class WXShareParam extends ShareParam {
    public static final int SHARE_TYPE_TEXT = 1;
    public static final int SHARE_TYPE_IMAGE = 2;
    public static final int SHARE_TYPE_MUSIC = 3;
    public static final int SHARE_TYPE_VIDEO = 4;
    public static final int SHARE_TYPE_APP = 5;
    public static final int SHARE_TYPE_WEBPAGE = 6;
    public static final int SHARE_TYPE_MINI = 7;

    /**
     * 朋友
     */
    public static final int WXSCENE_SESSION = 0;
    /**
     * 朋友圈
     */
    public static final int WXSCENE_TIMELINE = 1;

    /**
     * 微信分享缩略图
     */
    private Bitmap thumbnail;
    private int shareType;
    private int shareScene;
    private String imageUrl;

    public WXShareParam(int shareType, int shareScene, String imageUrl, Bitmap thumbnail) {
        super();
        this.shareType = shareType;
        this.shareScene = shareScene;
        this.thumbnail = thumbnail;
        this.imageUrl = imageUrl;
    }

    /**
     * 网页分享
     *
     * @param shareType
     * @param shareScene
     * @param pageUrl
     * @param title
     * @param content
     */
    public WXShareParam(int shareType, int shareScene, String pageUrl, String title, String content) {
        super(title, content, pageUrl);
        this.shareType = shareType;
        this.shareScene = shareScene;
    }

    /**
     * 网页分享
     *
     * @param shareType
     * @param shareScene
     * @param pageUrl
     * @param title
     * @param content
     */
    public WXShareParam(int shareType, int shareScene, String pageUrl, String title, String content, Bitmap thumbnail) {
        super(title, content, pageUrl);
        this.shareType = shareType;
        this.shareScene = shareScene;
        this.thumbnail = thumbnail;
    }

    public Bitmap getThumbnail() {
        return this.thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getShareType() {
        return this.shareType;
    }

    public int getShareScene() {
        return this.shareScene;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }
}
