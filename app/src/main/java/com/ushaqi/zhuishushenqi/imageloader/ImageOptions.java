package com.ushaqi.zhuishushenqi.imageloader;

/**
 * <p>
 *
 * @ClassName: ImageOptions
 * @Date: 2019-11-19 16:39
 * @Author: jared
 * @Org: 上海元聚网络科技有限公司
 * @Description: 图片加载的options, 目前只写了glide的option，后期可以扩展
 * </p>
 */
public interface ImageOptions<T> {

    T diskCacheStrategy(int diskCacheStrategy);

    T memoryCacheStrategy(int memoryCacheStrategy);

    T sizeStrategy(ImageSize size);
}
