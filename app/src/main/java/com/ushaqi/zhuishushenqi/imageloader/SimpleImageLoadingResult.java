package com.ushaqi.zhuishushenqi.imageloader;

import android.graphics.Bitmap;
import android.view.View;

/**
 * 图片加载结果
 *
 * @author Andy.zhang
 * @date 2020/8/14
 */
public class SimpleImageLoadingResult implements ImageLoadingListener {
    @Override
    public void onLoadingStarted(String imageUri, View view) {

    }

    @Override
    public void onLoadingFailed(String imageUri, View view, Throwable cause) {

    }

    @Override
    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

    }

    @Override
    public void onLoadingCancelled(String imageUri, View view) {

    }
}
