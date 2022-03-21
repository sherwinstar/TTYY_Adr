package com.ushaqi.zhuishushenqi.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.ushaqi.zhuishushenqi.R;
import com.ushaqi.zhuishushenqi.imageloader.ImageHelper;
import com.ushaqi.zhuishushenqi.imageloader.ImageSize;


/**
 * The type Cover view.(包含了加载图片,圆角,修圆)
 */
public class NewCoverView extends androidx.appcompat.widget.AppCompatImageView {

    private int radius; //圆角

    private boolean isCircle;
    private int mImageRes = 0;//默认src
    Context context;

    public NewCoverView(Context context) {
        super(context);
        this.context = context;
    }

    public NewCoverView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NewCoverView);
        mImageRes = typedArray.getResourceId(R.styleable.NewCoverView_new_cover, 0);
        radius = typedArray.getDimensionPixelOffset(R.styleable.NewCoverView_new_rad, 0);
        isCircle = typedArray.getBoolean(R.styleable.NewCoverView_new_circle, false);
        typedArray.recycle();
    }


    /**
     * Sets image url. 正常加载,圆角,切圆都包括了
     *
     * @param fullCover    the full cover
     * @param coverDefault the cover default
     */
    public void setImageUrl(String fullCover, int coverDefault) {
        if (isCircle) {
            ImageHelper.getInstance().loadRoundImage(this,fullCover,coverDefault);
            return;
        }
        if (radius != 0) {
            ImageHelper.getInstance().loadRadiusImage(this,fullCover,coverDefault,radius);
        } else {
            ImageHelper.getInstance().displayWithDefault(this,fullCover,coverDefault);
        }

    }

    public void setImageUrlWithSize(String fullCover, int coverDefault, ImageSize imageSize) {
        ImageHelper.getInstance().loadRadiusImage(this, fullCover, coverDefault, 10 , imageSize);
    }

    public void setlocalImageUrl(int id) {
        if (isCircle) {
//            GlideUtils.loadLocalCircleImgView(id, this);
            ImageHelper.getInstance().displayLocalView(id,this);
            return;
        }
    }







    public ImageView getSimpleDraweeView() {
        return this;
    }
}
