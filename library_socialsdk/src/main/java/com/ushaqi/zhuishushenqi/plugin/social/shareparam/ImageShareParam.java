package com.ushaqi.zhuishushenqi.plugin.social.shareparam;

/**
 *
 * @author Andy.zhang
 * @date 2019/8/22
 */
public class ImageShareParam extends ShareParam {

    private ShareImage mImage;

    public ImageShareParam() {
    }

    /**
     *
     * @param title
     * @param content
     */
    public ImageShareParam(String title, String content) {
        super(title, content);
    }

    /**
     *
     * @param title
     * @param content
     * @param targetUrl
     */
    public ImageShareParam(String title, String content, String targetUrl) {
        super(title, content, targetUrl);
    }

    public ShareImage getImage() {
        return mImage;
    }

    public void setImage(ShareImage image) {
        mImage = image;
    }

}
