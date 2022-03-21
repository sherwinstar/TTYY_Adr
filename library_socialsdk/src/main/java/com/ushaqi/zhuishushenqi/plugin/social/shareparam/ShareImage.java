package com.ushaqi.zhuishushenqi.plugin.social.shareparam;

import android.text.TextUtils;

import java.io.File;

/**
 * 
 * @author Andy.zhang
 * @date 2019/8/22
 */
public class ShareImage {
    public enum ImageType {
        UNKNOW, LOCAL, BITMAP, NET
    }

    private File mImageLocalFile;
    private byte[] mImageBytes;
    private String mImageUrl;

    public ShareImage() {
    }

    public ShareImage(String imageUrl) {
        this.mImageUrl = imageUrl;
    }

    public ShareImage(File imageLocalFile) {
        mImageLocalFile = imageLocalFile;
    }

    public ShareImage(byte[] imageBytes) {
        mImageBytes = imageBytes;
    }

    public File getImageLocalFile() {
        return mImageLocalFile;
    }

    public String getImageLocalPath() {
        final String path = mImageLocalFile == null ? null : mImageLocalFile.exists() ? mImageLocalFile.getAbsolutePath() : null;
        return path;
    }

    public void setLocalFile(File localFile) {
        mImageLocalFile = localFile;
    }

    public byte[] getImageBytes() {
        return mImageBytes;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageBytes(byte[] imageBytes) {
        mImageBytes = imageBytes;
    }

    public void setImageUrl(final String imageUrl) {
        mImageUrl = imageUrl;
    }
    
    public boolean isLocalImage() {
        return getImageType() == ImageType.LOCAL;
    }

    public boolean isBitmapImage() {
        return getImageType() == ImageType.BITMAP;
    }

    public boolean isNetImage() {
        return (getImageType() == ImageType.NET);
    }

    public ImageType getImageType() {
        if (mImageBytes != null && mImageBytes.length > 0) {
            return ImageType.BITMAP;
        } else if (mImageLocalFile != null && mImageLocalFile.exists()) {
            return ImageType.LOCAL;
        } else if (!TextUtils.isEmpty(mImageUrl)) {
            return ImageType.NET;
        } else {
            return ImageType.UNKNOW;
        }
    }

}