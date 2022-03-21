package com.ushaqi.zhuishushenqi.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.ushaqi.zhuishushenqi.R;
import com.ushaqi.zhuishushenqi.util.DensityUtil;


/**
 * Created by mac on 2018/7/26.
 * <p>
 * <p>
 * 对于toolbar的简单封装
 * <p>
 * 由于collaspetoolbarlayout必须要toolbar或者直接子view,故继承与toolbar
 * addExtraChildView()对于任何子view的添加
 * <p>
 * 返回和标题默认都是true
 */

public class YJToolBar extends Toolbar implements View.OnClickListener {

    private TextView mTxTitle;
    private TextView mTxBack;



    private Context context;

    private RelativeLayout toolbar;



    public YJToolBar(@NonNull Context context) {
        super(context);
        init(context);
    }

    public YJToolBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public YJToolBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.yj_toolbar_layout, this);
        mTxTitle = findViewById(R.id.tv_toolbar_title);
        toolbar = findViewById(R.id.toolbar);
        mTxBack = findViewById(R.id.tv_toolbar_back);

        mTxBack.setOnClickListener(this);
    }

    public void setStyle(int color, int res, int backColor) {
        mTxTitle.setTextColor(color);
        mTxBack.setTextColor(backColor);
        Drawable drawableLeft = context.getResources().getDrawable(res);
        mTxBack.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, null, null);
        mTxBack.setCompoundDrawablePadding(1);
    }


    public YJToolBar addExtraChildView(View view) {

        if (view == null) {
            throw new NullPointerException("not view added");
        }

        if (view.getParent() != null) {
            throw new ClassCastException("this view has parent");
        }

        toolbar.addView(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        view.setLayoutParams(params);
        return this;
    }


    public void setGravityTitle(String title) {
        setTitleCenter();
        mTxTitle.setText(title);
    }

    public void setBackIconClickListener(OnClickListener onClickListener) {
        mTxBack.setOnClickListener(onClickListener);
    }

    private   void setTitleCenter(){
        setContentInsetsAbsolute(0,0);
        mTxBack.setPadding(DensityUtil.dp2px(16),0,0,0);
    }

    public YJToolBar hideGravityTitle() {

        mTxTitle.setVisibility(GONE);
        return this;
    }


    public com.ushaqi.zhuishushenqi.widget.YJToolBar hideBackIcon() {
        mTxBack.setVisibility(GONE);

        return this;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_toolbar_back) {
            if (context instanceof Activity) {
                if (!((Activity) context).isFinishing())
                    ((Activity) context).onBackPressed();
            }
        }
    }




    @Override
    public void setBackgroundColor(int color) {
        super.setBackgroundColor(color);
    }
}
