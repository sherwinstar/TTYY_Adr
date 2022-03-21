package com.ushaqi.zhuishushenqi.plugin.social.shareparam;

/**
 * 
 * @author Andy.zhang
 * @date 2019/8/22
 */
public class WebPageShareParam extends ShareParam {
    private ShareImage mThumb;

    public WebPageShareParam() {
    }

    public WebPageShareParam(String title, String content) {
        super(title, content);
    }

    public WebPageShareParam(String title, String content, String targetUrl) {
        super(title, content, targetUrl);
    }

    public ShareImage getThumb() {
        return mThumb;
    }

    public void setThumb(ShareImage thumb) {
        mThumb = thumb;
    }


}
