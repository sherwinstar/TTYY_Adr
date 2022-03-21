package com.ushaqi.zhuishushenqi.imageloader.glide;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.ImageView;

import androidx.annotation.Nullable;


import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.ushaqi.zhuishushenqi.MyApplication;
import com.ushaqi.zhuishushenqi.imageloader.ImageLoader;
import com.ushaqi.zhuishushenqi.imageloader.ImageLoadingListener;
import com.ushaqi.zhuishushenqi.imageloader.ImageOptions;
import com.ushaqi.zhuishushenqi.imageloader.ImageSize;

import java.lang.ref.WeakReference;

public class GlideImageLoader implements ImageLoader {

    private static GlideImageLoader sInstance;

    public static GlideImageLoader getInstance() {
        if (sInstance == null) {
            synchronized (GlideImageLoader.class) {
                if (sInstance == null) {
                    sInstance = new GlideImageLoader();
                }
            }
        }
        return sInstance;
    }

    private RequestOptions radiusRoundRequestOptions;
    private RequestOptions defaultRequestOptions;
    private RequestOptions loadRequestOptions;
    private RequestOptions radiusRequestOptions;
    private RequestOptions resizeRequestOptions;
    private RequestOptions roundRequestOptions;

    private RequestOptions getLoadRequestOptions() {
        if (loadRequestOptions == null) {
            loadRequestOptions = new RequestOptions();
        }
        return loadRequestOptions;
    }

    private RequestOptions getRadiusRoundOptions() {
        if (radiusRoundRequestOptions == null) {
            radiusRoundRequestOptions = new RequestOptions();
        }
        return radiusRoundRequestOptions;
    }

    private RequestOptions getRoundOptions() {
        if (roundRequestOptions == null) {
            roundRequestOptions = new RequestOptions();
        }
        return roundRequestOptions;
    }

    private RequestOptions getResizeOptions() {
        if (resizeRequestOptions == null) {
            resizeRequestOptions = new RequestOptions();
        }
        return resizeRequestOptions;
    }

    private RequestOptions getRadiusOptions() {
        if (radiusRequestOptions == null) {
            radiusRequestOptions = new RequestOptions();
        }
        return radiusRequestOptions;
    }

    private RequestOptions getDefaultOptions() {
        if (defaultRequestOptions == null) {
            defaultRequestOptions = new RequestOptions();
        }
        return defaultRequestOptions;
    }

    @Override
    public void display(ImageView view, String imageUrl) {
        final WeakReference<ImageView> imageViewWeakReference = new WeakReference<>(view);
        final ImageView imageView = imageViewWeakReference.get();
        if (imageView == null) {
            return;
        }
        Glide.with(MyApplication.getInstance()).load(imageUrl).apply(getDefaultOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                .into(imageView);
    }

    @Override
    public void display(ImageView view, int imageRes) {
        final WeakReference<ImageView> imageViewWeakReference = new WeakReference<>(view);
        final ImageView imageView = imageViewWeakReference.get();
        if (imageView == null) {
            return;
        }
        Glide.with(MyApplication.getInstance()).load(imageRes).apply(getDefaultOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                .into(imageView);
    }

    @Override
    public void display(final ImageView view, final String imageUrl, final ImageLoadingListener listener) {
        final WeakReference<ImageView> imageViewWeakReference = new WeakReference<>(view);
        final ImageView imageView = imageViewWeakReference.get();
        if (imageView == null) {
            return;
        }
        Glide.with(MyApplication.getInstance())
                .asBitmap()
                .load(imageUrl)
                .apply(getDefaultOptions()
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        if (listener != null) {
                            listener.onLoadingFailed(imageUrl, null, null);
                        }

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        if (resource == null) {
                            if (listener != null) {
                                listener.onLoadingFailed(imageUrl, imageView, null);
                            }
                        } else {
                            if (listener != null) {
                                listener.onLoadingComplete(imageUrl, imageView, resource);
                            } else {
                                imageView.setImageBitmap(resource);
                            }
                        }
                        return false;
                    }
                }).submit();
    }

    @Override
    public void loadImage(final String imageUrl, final ImageLoadingListener listener, int... defaultImage) {
        int defImage = 0;
        if (defaultImage != null && defaultImage.length > 0) {
            defImage = defaultImage[0];
        }
        Glide.with(MyApplication.getInstance())
                .asBitmap()
                .load(imageUrl)
                .apply(getLoadRequestOptions()
                        .placeholder(defImage)
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        listener.onLoadingFailed(imageUrl, null, null);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        if (resource == null) {
                            listener.onLoadingFailed(imageUrl, null, null);
                        } else {
                            listener.onLoadingComplete(imageUrl, null, resource);
                        }
                        return false;
                    }
                }).submit();
    }

    @Override
    public void loadImage(final String imageUrl, ImageSize size, final ImageLoadingListener listener) {
        Glide.with(MyApplication.getInstance())
                .asBitmap()
                .load(imageUrl)
                .apply(getResizeOptions()
                        .override(size.getWidth(), size.getHeight())
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .centerCrop())
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        listener.onLoadingFailed(imageUrl, null, null);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        if (resource == null) {
                            listener.onLoadingFailed(imageUrl, null, null);
                        } else {
                            listener.onLoadingComplete(imageUrl, null, resource);
                        }
                        return false;
                    }
                }).submit();
    }

    @Override
    public void loadRadiusImage(final String imageUrl, int radius, final  ImageLoadingListener listener, int... defaultImage) {
        int defImage = 0;
        if (defaultImage != null && defaultImage.length > 0) {
            defImage = defaultImage[0];
        }
        Glide.with(MyApplication.getInstance())
                .asBitmap()
                .load(imageUrl)
                .apply(getLoadRequestOptions()
                        .placeholder(defImage)
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(radius)))
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        listener.onLoadingFailed(imageUrl, null, null);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        if (resource == null) {
                            listener.onLoadingFailed(imageUrl, null, null);
                        } else {
                            listener.onLoadingComplete(imageUrl, null, resource);
                        }
                        return false;
                    }
                }).submit();
    }

    @Override
    public void loadImageWithFitInside(final String imageUrl, ImageSize size, final ImageLoadingListener listener) {
        Glide.with(MyApplication.getInstance())
                .asBitmap()
                .load(imageUrl)
                .apply(getResizeOptions()
                        .override(size.getWidth(), size.getHeight())
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        )
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        listener.onLoadingFailed(imageUrl, null, null);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        if (resource == null) {
                            listener.onLoadingFailed(imageUrl, null, null);
                        } else {
                            listener.onLoadingComplete(imageUrl, null, resource);
                        }
                        return false;
                    }
                }).submit();
    }

    @Override
    public void loadImageWithOptions(final String url, ImageOptions options, final ImageLoadingListener listener) {
        GlideImageOptions glideImageOptions = (GlideImageOptions) options;
        if (options == null){
            return;
        }
        Glide.with(MyApplication.getInstance())
                .asBitmap()
                .load(url)
                .apply(glideImageOptions.options)
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        listener.onLoadingFailed(url, null, null);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        if (resource == null) {
                            listener.onLoadingFailed(url, null, null);
                        } else {
                            listener.onLoadingComplete(url, null, resource);
                        }
                        return false;
                    }
                }).submit();
    }


    @Override
    public void displayWithDefault(final ImageView view, String url, final int imageDefault) {
        final WeakReference<ImageView> imageViewWeakReference = new WeakReference<>(view);
        final ImageView imageView = imageViewWeakReference.get();
        if (imageView == null) {
            return;
        }
        imageView.setImageResource(imageDefault);
        Glide.with(MyApplication.getInstance())
                .asBitmap()
                .load(url)
                .apply(getDefaultOptions()
                        .placeholder(imageDefault)
                        .error(imageDefault)
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                )
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        imageView.setImageResource(imageDefault);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        if (resource == null) {
                            imageView.setImageResource(imageDefault);
                            return false;
                        }
                        imageView.setImageBitmap(resource);
                        return false;
                    }
                }).submit();
    }

    @Override
    public void loadRoundImage(final ImageView view, String url, final int coverDefault) {
        final WeakReference<ImageView> imageViewWeakReference = new WeakReference<>(view);
        final ImageView imageView = imageViewWeakReference.get();
        if (imageView == null) {
            return;
        }
        Glide.with(MyApplication.getInstance())
                .load(url)
                .apply(getRoundOptions()
                        .placeholder(coverDefault)
                        .error(coverDefault)
                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                )
                .into(imageView);

    }

    @Override
    public void loadRadiusImage(final ImageView view, String url, final int coverDefault, final int radius) {
        final WeakReference<ImageView> imageViewWeakReference = new WeakReference<>(view);
        final ImageView imageView = imageViewWeakReference.get();
        if (imageView == null) {
            return;
        }
        Glide.with(MyApplication.getInstance())
                .load(url)
                .apply(getRadiusOptions()
                        .placeholder(coverDefault)
                        .error(coverDefault)
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(radius)))
                )
                .into(imageView);
    }

    @Override
    public void loadRadiusImageWithHighPriority(ImageView view, String url, int coverDefault, int radius) {
        final WeakReference<ImageView> imageViewWeakReference = new WeakReference<>(view);
        final ImageView imageView = imageViewWeakReference.get();
        if (imageView == null) {
            return;
        }
        Glide.with(MyApplication.getInstance())
                .load(url)
                .apply(getRadiusOptions()
                        .placeholder(coverDefault)
                        .error(coverDefault)
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .priority(Priority.IMMEDIATE)
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(radius)))
                )
                .into(imageView);
    }

    @Override
    public void displayWithDefaultDrawable(final ImageView view, String url, final Drawable defDrawable) {
        final WeakReference<ImageView> imageViewWeakReference = new WeakReference<>(view);
        final ImageView imageView = imageViewWeakReference.get();
        if (imageView == null) {
            return;
        }
        imageView.setImageDrawable(defDrawable);
        Glide.with(MyApplication.getInstance())
                .load(url)
                .apply(getDefaultOptions()
                        .placeholder(defDrawable)
                        .error(defDrawable)
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .centerCrop())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        imageView.setImageDrawable(defDrawable);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        if (resource == null) {
                            imageView.setImageDrawable(defDrawable);
                            return false;
                        }
                        imageView.setImageDrawable(resource);
                        return false;
                    }
                }).submit();
    }

    @Override
    public void displayLocalView(int id, ImageView view) {
        final WeakReference<ImageView> imageViewWeakReference = new WeakReference<>(view);
        final ImageView imageView = imageViewWeakReference.get();
        if (imageView == null) {
            return;
        }
        Glide.with(MyApplication.getInstance()).load(id).apply(getDefaultOptions()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                .into(imageView);
    }

    @Override
    public void loadRadiusImage(final ImageView view, String url, final int coverDefault, final int radius, ImageSize mImageSize) {
        final WeakReference<ImageView> imageViewWeakReference = new WeakReference<>(view);
        final ImageView imageView = imageViewWeakReference.get();
        if (imageView == null) {
            return;
        }
        Glide.with(MyApplication.getInstance())
                .load(url)
                .apply(getRadiusRoundOptions()
                        .override(mImageSize.getWidth(), mImageSize.getHeight())
                        .placeholder(coverDefault)
                        .error(coverDefault)
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(radius)))
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                )
                .into(imageView);
    }



    public void loadImageWithContext(final Context context, final String imageUrl, final ImageLoadingListener listener) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (context == null) {
                return;
            }
            Activity activity;
            if (context instanceof Activity) {
                activity = (Activity) context;
                if (activity.isDestroyed() || activity.isFinishing()) {
                    return;
                }
            }
        }
        final WeakReference<Context> imageViewWeakReference = new WeakReference<>(context);
        final Context con = imageViewWeakReference.get();
        if (con == null) {
            return;
        }
        Glide.with(con)
                .asBitmap()
                .load(imageUrl)
                .apply(getLoadRequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        listener.onLoadingFailed(imageUrl, null, null);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        if (resource == null) {
                            listener.onLoadingFailed(imageUrl, null, null);
                        } else {
                            listener.onLoadingComplete(imageUrl, null, resource);
                        }
                        return false;
                    }
                }).submit();

    }

    /**
     *
     * @param imageView
     * @param gifPath
     * @param placeHolderDrawable
     */
    @Override
    public void loadGif(ImageView imageView, final String gifPath, final Drawable placeHolderDrawable) {
        Glide.with(MyApplication.getInstance()).load(gifPath)
                .apply(new RequestOptions().placeholder(placeHolderDrawable).diskCacheStrategy(DiskCacheStrategy.DATA))
                .into(imageView);
    }
}
