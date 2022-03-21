package com.ushaqi.zhuishushenqi.imageloader;

import android.graphics.Bitmap;
import android.view.View;

/**
 * Created by mac on 2019/4/9.
 */

public abstract class SimpleImageLoader implements ImageLoadingListener {

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
