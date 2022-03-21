package com.ushaqi.zhuishushenqi.imageloader.glide;

import android.annotation.SuppressLint;


import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ushaqi.zhuishushenqi.imageloader.ImageOptions;
import com.ushaqi.zhuishushenqi.imageloader.ImageOptionsConfig;
import com.ushaqi.zhuishushenqi.imageloader.ImageSize;

/**
 * <p>
 * @ClassName: GlideImageOptions
 * @Date: 2019-11-19 18:13
 * @Author: jared
 * @Org: 上海元聚网络科技有限公司
 * @Description: glide对应的options
 * </p>
 */
public class GlideImageOptions implements ImageOptions<GlideImageOptions> {

    public RequestOptions options;

    public GlideImageOptions() {
        options = new RequestOptions();
    }

    @SuppressLint("CheckResult")
    @Override
    public GlideImageOptions diskCacheStrategy(int diskCacheStrategy) {
        if (diskCacheStrategy == ImageOptionsConfig.DISK_CACHE_STRATEGY_NONE) {
            options.diskCacheStrategy(DiskCacheStrategy.NONE);
        }
        return this;
    }

    @SuppressLint("CheckResult")
    @Override
    public GlideImageOptions memoryCacheStrategy(int memoryCacheStrategy) {
        if (memoryCacheStrategy == ImageOptionsConfig.MEMORY_CACHE_STRATEGY_NONE) {
            options.skipMemoryCache(true);
        }
        return this;
    }

    @SuppressLint("CheckResult")
    @Override
    public GlideImageOptions sizeStrategy(ImageSize size) {
        options.override(size.getWidth(),size.getHeight());
        return this;
    }
}
