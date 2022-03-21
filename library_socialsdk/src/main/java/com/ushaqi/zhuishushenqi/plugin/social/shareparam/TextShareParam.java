package com.ushaqi.zhuishushenqi.plugin.social.shareparam;

/**
 * @author Andy.zhang
 * @date 2019/8/22
 */
public class TextShareParam extends ShareParam {
    public TextShareParam(String title, String content) {
        super(title, content);
    }

    /**
     * @param title
     * @param content
     * @param targetUrl
     */
    public TextShareParam(String title, String content, String targetUrl) {
        super(title, content, targetUrl);
    }

}
