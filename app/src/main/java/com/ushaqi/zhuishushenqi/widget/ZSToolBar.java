package com.ushaqi.zhuishushenqi.widget;

import android.app.Activity;
import android.content.Context;



import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
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

public class ZSToolBar extends Toolbar implements View.OnClickListener {

    private TextView tv_left;
    private RelativeLayout rl_tool_bar_container;
    private TextView tv_right;
    private FrameLayout fl_right;
    private ImageView iv_left;
    private ImageView iv_right;
    private RelativeLayout toolbar;
    private View.OnClickListener leftListener;
    private Context mContext;
    private TextView mTitle;

    public ZSToolBar(@NonNull Context context) {
        super(context);
        init(context);
    }

    public ZSToolBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ZSToolBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.toolbar_layout, this);
        rl_tool_bar_container = findViewById(R.id.rl_tool_bar_container);
        tv_left = findViewById(R.id.tv_left);
        tv_right = findViewById(R.id.tv_right);
        toolbar = findViewById(R.id.toolbar);
        iv_left = findViewById(R.id.iv_left);
        fl_right = findViewById(R.id.fl_right);
        iv_right = findViewById(R.id.iv_right);
        mTitle = findViewById(R.id.title);
        iv_left.setOnClickListener(this);
    }

    public ZSToolBar addExtraChildView(View view) {
        rl_tool_bar_container.setVisibility(GONE);
        toolbar.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
        view.setLayoutParams(params);
        return this;
    }

    public ZSToolBar setTitle(String title) {
        setTitleCenter();
        if (TextUtils.isEmpty(title)) {
            return this;
        }
        mTitle.setVisibility(VISIBLE);
        mTitle.setText(title);
        return this;
    }
    private   void setTitleCenter(){
        setContentInsetsAbsolute(0,0);
        iv_left.setPadding(DensityUtil.dp2px(16),0,0,0);
    }

    public TextView getGravityTitle() {
        return mTitle;
    }


    public ZSToolBar removeExtraChildView(final View view) {
        if (view != null) {
            toolbar.removeView(view);
        }
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                if (mContext == null) {
                    return;
                }
                if (mContext instanceof Activity && !((Activity) mContext).isFinishing()) {
                    if (leftListener != null) {
                        leftListener.onClick(v);
                    } else {
                        ((Activity) getContext()).finish();
                    }
                }
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            findViewById(R.id.iv_left).performClick();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void initView(String leftTitle, String rightTitle, int leftIconRes, int rightIconRes) {
        if (!TextUtils.isEmpty(leftTitle)) {
            tv_left.setText(leftTitle);
        }
        if (leftIconRes > 0) {
            iv_left.setImageResource(leftIconRes);
        }
        if (rightIconRes > 0) {
            iv_right.setImageResource(rightIconRes);
        }
        if (!TextUtils.isEmpty(rightTitle)) {
            tv_right.setVisibility(VISIBLE);
            tv_right.setText(rightTitle);
        } else {
            tv_right.setVisibility(GONE);
        }
    }

    public ZSToolBar setLeftClickListener(View.OnClickListener leftBackClickCallback) {
        leftListener = leftBackClickCallback;
        return this;
    }

    public ZSToolBar setRightClickListener(View.OnClickListener rightBackClickCallback) {
        if (rightBackClickCallback != null) {
            fl_right.setOnClickListener(rightBackClickCallback);
        }
        return this;
    }
}
