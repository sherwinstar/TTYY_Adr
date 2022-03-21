package com.ushaqi.zhuishushenqi.plugin.social.shareparam;

/**
 * 
 * @author Andy.zhang
 * @date 2019/8/22
 */
public class ShareAudio {
    private ShareImage mThumb;
    private String mAudioSrcUrl;
    private String mAudioH5Url;
    private String mAudioDesc;

    public ShareAudio() {
    }

    public ShareAudio(String audioSrcUrl, String audioH5Url, String desc) {
        mAudioSrcUrl = audioSrcUrl;
        mAudioH5Url = audioH5Url;
        mAudioDesc = desc;
    }

    public ShareAudio(ShareImage thumb, String audioSrcUrl, String desc) {
        mThumb = thumb;
        mAudioSrcUrl = audioSrcUrl;
        mAudioDesc = desc;
    }

    public ShareImage getThumb() {
        return mThumb;
    }

    public void setThumb(ShareImage thumb) {
        mThumb = thumb;
    }

    public String getAudioSrcUrl() {
        return mAudioSrcUrl;
    }

    public void setAudioSrcUrl(String audioSrcUrl) {
        mAudioSrcUrl = audioSrcUrl;
    }

    public String getAudioDesc() {
        return mAudioDesc;
    }

    public void setAudioDesc(String desc) {
        mAudioDesc = desc;
    }

    public String getAudioH5Url() {
        return mAudioH5Url;
    }

    public void setAudioH5Url(String audioH5Url) {
        mAudioH5Url = audioH5Url;
    }

}
