package com.ushaqi.zhuishushenqi.imageloader;

/**
* @author Andy.zhang
* @date 2018/8/13
*/
public class ImageSize {
    private final int mImageWidth;
    private final int mImageHeight;

    public ImageSize(final int width, final int height) {
        this.mImageWidth = width;
        this.mImageHeight = height;
    }

    public int getWidth() {
        return mImageWidth;
    }

    public int getHeight() {
        return mImageHeight;
    }
}
