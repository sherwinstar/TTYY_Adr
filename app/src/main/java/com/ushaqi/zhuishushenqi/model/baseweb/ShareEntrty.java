package com.ushaqi.zhuishushenqi.model.baseweb;

import android.text.TextUtils;



/**
 * @Author chengwencan
 * @Date 2016/9/7.
 */
public class ShareEntrty {

    /**
     * title : title test
     * content : content test
     * link : http://www.baidu.com
     * icon : http://upload.jianshu.io/users/upload_avatars/54009/44cad1e5208c?imageMogr/thumbnail/90x90/quality/100
     */

    private String title;
    private String content;
    private String link;
    private String icon;
    private String trackKey;
    private String from;
    private String type;
    private String share_position;
    private String share_id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTrackKey() {
        return trackKey;
    }

    public void setTrackKey(String trackKey) {
        this.trackKey = trackKey;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getShare_position() {
        return share_position;
    }

    public void setShare_position(String share_position) {
        this.share_position = share_position;
    }

    public String getShare_id() {
        return share_id;
    }

    public void setShare_id(String share_id) {
        this.share_id = share_id;
    }

    /**
     * 通过H5的type获取指定分享平台
     * @return
     */
    public int getSharePlatform(){
        int platform = -1;
        if(TextUtils.isEmpty(type)){
            return platform;
        }
//        switch (type){
//            case "friend":
//                platform = ShareHelper.SHARE_WX_MOMENT;
//                break;
//            case "wechat":
//                platform = ShareHelper.SHARE_WX_FRIEND;
//                break;
//            case "qq":
//                platform = ShareHelper.SHARE_QQ;
//                break;
//            case "qzone ":
//                platform = ShareHelper.SHARE_QZONE;
//                break;
//            case "sina":
//                platform = ShareHelper.SHARE_WEIBO;
//                break;
//            case "copy":
//                platform = ShareHelper.SHARE_COPY;
//                break;
//            default:
//                break;
//        }
        return platform;
    }
}
