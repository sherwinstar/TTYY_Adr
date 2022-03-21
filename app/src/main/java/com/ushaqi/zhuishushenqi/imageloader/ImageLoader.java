package com.ushaqi.zhuishushenqi.imageloader;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * 图片下载契约类
 * Created By JackHu on 2018/8/9.
 */
public interface ImageLoader {
    /**
     * 加载url图片资源
     *
     * @param view
     * @param imageUrl
     */
    void display(ImageView view, String imageUrl);

    /**
     * 加载resource图片资源
     *
     * @param view
     * @param imageRes
     */
    void display(ImageView view, int imageRes);

    /**
     * @param view
     * @param imageUrl
     * @param listener
     */
    void display(ImageView view, String imageUrl, final ImageLoadingListener listener);

    /**
     * @param imageUrl
     * @param listener
     */
    void loadImage(String imageUrl, final ImageLoadingListener listener, int... def);

    /**
     * @param imageUrl
     * @param size
     * @param listener
     */
    void loadImage(String imageUrl, final ImageSize size, final ImageLoadingListener listener);
    /**
     *@desc
     *@param
     *@return
     */
    void loadRadiusImage(String imageUrl, int radius, final ImageLoadingListener listener, int... def);
    /**
     * @param imageUrl
     * @param size
     * @param listener
     */
    void loadImageWithFitInside(String imageUrl, final ImageSize size, final ImageLoadingListener listener);
    /**
     * 加载带默认图
     *
     * @param imgBG
     * @param url
     * @param imageDefault
     */
    void displayWithDefault(ImageView imgBG, String url, int imageDefault);

    void loadRoundImage(ImageView imageView, String url, int coverDefault);

    void loadRadiusImage(ImageView imageView, String url, int coverDefault, int radius);

    void loadRadiusImageWithHighPriority(ImageView imageView, String url, int coverDefault, int radius);

    void displayWithDefaultDrawable(ImageView mBodyView, String url, Drawable defDrawable);

    void displayLocalView(int id, ImageView imageView);

    void loadRadiusImage(ImageView imageView, String url, int defaultImageRes, int mRadius, ImageSize mImageSize);

    void loadImageWithOptions(String url, ImageOptions options, final ImageLoadingListener listener);

    /**
     * 加载GIF
     *
     * @param imageView
     * @param gifPath
     * @param placeHolderDrawable
     */
    void loadGif(ImageView imageView, final String gifPath, final Drawable placeHolderDrawable);
}
