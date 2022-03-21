package com.ushaqi.zhuishushenqi.imageloader;

import android.graphics.Bitmap;
import android.view.View;

import com.ushaqi.zhuishushenqi.util.LogUtil;


/**
* @author Andy.zhang
* @date 2018/8/9
*/
public abstract class ImageLoadingResult implements ImageLoadingListener {
    private static final String TAG = ImageLoadingResult.class.getSimpleName();

    @Override
    public void onLoadingStarted(String imageUri, View view) {
        LogUtil.d(TAG, "onLoadingStarted imageUri:" + imageUri);
    }

    @Override
    public void onLoadingCancelled(String imageUri, View view) {
        LogUtil.d(TAG, "onLoadingCancelled imageUri:" + imageUri);
    }

    public abstract void onLoadingFailed(String imageUri, View view, Throwable cause);

    public abstract void onLoadingComplete(String imageUri, View view, Bitmap loadedImage);
}
