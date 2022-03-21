package com.ushaqi.zhuishushenqi.model.baseweb;

import java.io.Serializable;

/**
 * @author chengwencan
 * @date 2021/9/1
 * Describe：
 */
public class ImagesBean implements Serializable {
    /**
     * imgs : url1,url2
     */
    private String imgs;

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }
}
