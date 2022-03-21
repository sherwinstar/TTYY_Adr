package com.ushaqi.zhuishushenqi.imageloader;

import com.ushaqi.zhuishushenqi.imageloader.glide.GlideImageLoader;

/**
 * 图片加载契约实现类，作为三方框架的中转机构
 * 添加新的方法请在契约类中添加接口方法实现之
 * Created By JackHu on 2018/8/9.
 */
public class ImageHelper{
    private static volatile ImageLoader sInstance;


    public static ImageLoader getInstance() {
        if (sInstance == null) {
            synchronized (ImageHelper.class) {
                if (sInstance == null) {
                    sInstance = GlideImageLoader.getInstance();
                }
            }
        }
        return sInstance;
    }




}
