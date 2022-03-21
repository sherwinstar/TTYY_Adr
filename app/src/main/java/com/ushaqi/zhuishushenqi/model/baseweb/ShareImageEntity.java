package com.ushaqi.zhuishushenqi.model.baseweb;

/**
 * 分享邀请下载
 */

public class ShareImageEntity {

    /**
     * group : inviteDownload

     */

    private String group;


    private String src;

    private String imageName;

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }


}
